import { useEffect, useState } from 'react'

const money = new Intl.NumberFormat('vi-VN', {
  style: 'currency',
  currency: 'VND',
  maximumFractionDigits: 0,
})

function App() {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [authLoading, setAuthLoading] = useState(false)
  const [authUser, setAuthUser] = useState(null)
  const [userId, setUserId] = useState('')
  const [tours, setTours] = useState([])
  const [tourLoading, setTourLoading] = useState(true)
  const [tourError, setTourError] = useState('')
  const [loadingTourId, setLoadingTourId] = useState(null)
  const [notice, setNotice] = useState(null)

  const userInfo = authUser?.user ?? authUser?.data ?? authUser?.payload ?? authUser

  const login = async (event) => {
    event.preventDefault()

    const trimmedUsername = username.trim()

    if (!trimmedUsername || !password.trim()) {
      setNotice({ type: 'error', text: 'Vui lòng nhập username và password.' })
      return
    }

    setAuthLoading(true)
    setNotice(null)

    try {
      const response = await fetch('http://172.16.68.10:8081/auth/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          username: trimmedUsername,
          password,
        }),
      })

      const data = await response.json().catch(() => null)
      const payload = data?.user ?? data?.data ?? data?.payload ?? data

      if (
        response.ok &&
        (data?.status === 'SUCCESS' || data?.status === 'LOGIN_SUCCESS' || data?.token || payload)
      ) {
        const resolvedUserId = String(payload?.id ?? data?.userId ?? data?.id ?? trimmedUsername)
        setAuthUser(payload)
        setUserId(resolvedUserId)
        setNotice({
          type: 'success',
          text: `SUCCESS: ${data?.message ?? 'Đăng nhập thành công'}`,
        })
      } else if (response.status === 401) {
        setAuthUser(null)
        setNotice({
          type: 'error',
          text: 'FAILED: Sai username hoặc password',
        })
      } else {
        setAuthUser(null)
        setNotice({
          type: 'error',
          text: `FAILED: ${data?.reason ?? data?.message ?? 'Đăng nhập không thành công'}`,
        })
      }
    } catch {
      setAuthUser(null)
      setNotice({ type: 'error', text: 'FAILED: Lỗi kết nối API login' })
    } finally {
      setAuthLoading(false)
    }
  }

  useEffect(() => {
    const loadTours = async () => {
      try {
        setTourLoading(true)
        setTourError('')

        const response = await fetch('http://172.16.68.10:8082/tours')
        const data = await response.json()
        const list = Array.isArray(data) ? data : data?.data ?? data?.tours ?? []

        setTours(list)
      } catch {
        setTourError('Không thể tải danh sách tour từ tour-service.')
      } finally {
        setTourLoading(false)
      }
    }

    loadTours()
  }, [])

  const bookTour = async (tour) => {
    const trimmedUserId = userId.trim()

    if (!trimmedUserId) {
      setNotice({ type: 'error', text: 'Vui lòng nhập userId trước khi đặt tour.' })
      return
    }

    setLoadingTourId(tour.id)
    setNotice(null)

    try {
      const response = await fetch('http://172.16.67.218:8080/book-tour', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          userId: trimmedUserId,
          tourId: tour.id,
          amount: tour.price,
        }),
      })

      const data = await response.json().catch(() => null)

      if (response.ok && data?.status === 'SUCCESS') {
        setNotice({
          type: 'success',
          text: `SUCCESS: ${data.transactionId ?? 'Không có transactionId'}`,
        })
      } else {
        setNotice({
          type: 'error',
          text: `FAILED: ${data?.reason ?? data?.message ?? 'Không thể đặt tour'}`,
        })
      }
    } catch {
      setNotice({ type: 'error', text: 'FAILED: Lỗi kết nối API' })
    } finally {
      setLoadingTourId(null)
    }
  }

  return (
    <main className="min-h-screen bg-slate-950 text-slate-100">
      <div className="mx-auto flex min-h-screen w-full max-w-6xl flex-col px-4 py-8 sm:px-6 lg:px-8">
        <section className="flex flex-1 flex-col justify-center gap-8">
          <div className="space-y-3">
            <p className="text-xs font-semibold uppercase tracking-[0.35em] text-cyan-400">
              Travel Booking
            </p>
            <h1 className="text-3xl font-semibold tracking-tight text-white sm:text-5xl">
              Đặt tour nhanh, gọn, tối giản
            </h1>
            <p className="max-w-2xl text-sm leading-6 text-slate-400 sm:text-base">
              Nhập userId, chọn tour và gửi yêu cầu đặt chỗ ngay trên một màn hình duy nhất.
            </p>
          </div>

          <div className="grid gap-4 lg:grid-cols-[1.2fr_0.8fr]">
            <form
              onSubmit={login}
              className="grid gap-4 rounded-2xl border border-slate-800 bg-slate-900/60 p-4 shadow-2xl shadow-black/30 backdrop-blur sm:p-6"
            >
              <div className="flex items-center justify-between gap-3">
                <div>
                  <p className="text-xs uppercase tracking-[0.3em] text-slate-500">Login</p>
                  <h2 className="mt-2 text-xl font-semibold text-white">Đăng nhập user service</h2>
                </div>
                {authUser ? (
                  <span className="rounded-full border border-emerald-500/20 bg-emerald-500/10 px-3 py-1 text-xs font-medium text-emerald-300">
                    Logged in
                  </span>
                ) : null}
              </div>

              <label className="space-y-2">
                <span className="text-sm font-medium text-slate-200">username</span>
                <input
                  value={username}
                  onChange={(event) => setUsername(event.target.value)}
                  placeholder="Nhập username"
                  className="w-full rounded-xl border border-slate-700 bg-slate-950 px-4 py-3 text-sm text-white outline-none transition placeholder:text-slate-500 focus:border-cyan-500 focus:ring-2 focus:ring-cyan-500/30"
                />
              </label>

              <label className="space-y-2">
                <span className="text-sm font-medium text-slate-200">password</span>
                <input
                  type="password"
                  value={password}
                  onChange={(event) => setPassword(event.target.value)}
                  placeholder="Nhập password"
                  className="w-full rounded-xl border border-slate-700 bg-slate-950 px-4 py-3 text-sm text-white outline-none transition placeholder:text-slate-500 focus:border-cyan-500 focus:ring-2 focus:ring-cyan-500/30"
                />
              </label>

              <button
                type="submit"
                disabled={authLoading}
                className="inline-flex items-center justify-center rounded-xl bg-cyan-400 px-4 py-3 text-sm font-semibold text-slate-950 transition hover:bg-cyan-300 disabled:cursor-not-allowed disabled:bg-slate-700 disabled:text-slate-300"
              >
                {authLoading ? 'Đang đăng nhập...' : 'Đăng nhập'}
              </button>

              {userInfo ? (
                <div className="grid gap-3 rounded-2xl border border-slate-800 bg-slate-950/70 p-4 text-sm text-slate-200">
                  <div className="flex items-center justify-between gap-3">
                    <p className="text-xs uppercase tracking-[0.3em] text-slate-500">User Info</p>
                    <span className="rounded-full border border-emerald-500/20 bg-emerald-500/10 px-3 py-1 text-xs font-medium text-emerald-300">
                      LOGIN_SUCCESS
                    </span>
                  </div>
                  <div className="grid gap-2 sm:grid-cols-2">
                    <div>
                      <span className="block text-xs uppercase tracking-[0.25em] text-slate-500">id</span>
                      <span className="mt-1 block text-white">{userInfo?.id ?? 'N/A'}</span>
                    </div>
                    <div>
                      <span className="block text-xs uppercase tracking-[0.25em] text-slate-500">name</span>
                      <span className="mt-1 block text-white">{userInfo?.name ?? 'N/A'}</span>
                    </div>
                    <div>
                      <span className="block text-xs uppercase tracking-[0.25em] text-slate-500">username</span>
                      <span className="mt-1 block text-white">{userInfo?.username ?? 'N/A'}</span>
                    </div>
                  </div>
                </div>
              ) : null}
            </form>

            <div className="grid gap-4 rounded-2xl border border-slate-800 bg-slate-900/60 p-4 shadow-2xl shadow-black/30 backdrop-blur sm:p-6">
              <div className="space-y-2">
                <p className="text-xs uppercase tracking-[0.3em] text-slate-500">Booking</p>
                <h2 className="text-xl font-semibold text-white">Thông tin đặt tour</h2>
              </div>

              {notice ? (
                <div
                  className={`rounded-xl border px-4 py-3 text-sm font-medium ${
                    notice.type === 'success'
                      ? 'border-emerald-500/30 bg-emerald-500/10 text-emerald-300'
                      : 'border-rose-500/30 bg-rose-500/10 text-rose-300'
                  }`}
                >
                  {notice.text}
                </div>
              ) : null}
            </div>
          </div>

          {tourLoading ? (
            <div className="rounded-2xl border border-slate-800 bg-slate-900/70 px-4 py-6 text-sm text-slate-300">
              Đang tải danh sách tour...
            </div>
          ) : tourError ? (
            <div className="rounded-2xl border border-rose-500/30 bg-rose-500/10 px-4 py-6 text-sm text-rose-300">
              {tourError}
            </div>
          ) : null}

          <div className="grid gap-4 md:grid-cols-3">
            {tours.map((tour) => (
              <article
                key={tour.id}
                className="flex flex-col justify-between rounded-2xl border border-slate-800 bg-slate-900/70 p-5 shadow-lg shadow-black/20"
              >
                <div className="space-y-4">
                  <div className="flex items-start justify-between gap-3">
                    <div>
                      <p className="text-xs uppercase tracking-[0.3em] text-slate-500">
                        Tour #{tour.id}
                      </p>
                      <h2 className="mt-2 text-xl font-semibold text-white">{tour.name}</h2>
                    </div>
                    {tour.duration ? (
                      <span className="rounded-full border border-cyan-500/20 bg-cyan-500/10 px-3 py-1 text-xs font-medium text-cyan-300">
                        {tour.duration}
                      </span>
                    ) : null}
                  </div>

                  <p className="text-2xl font-semibold text-emerald-300">
                    {money.format(Number(tour.price ?? 0))}
                  </p>
                </div>

                <button
                  type="button"
                  onClick={() => bookTour(tour)}
                  disabled={loadingTourId === tour.id}
                  className="mt-6 inline-flex items-center justify-center rounded-xl bg-white px-4 py-3 text-sm font-semibold text-slate-950 transition hover:bg-cyan-300 disabled:cursor-not-allowed disabled:bg-slate-700 disabled:text-slate-300"
                >
                  {loadingTourId === tour.id ? 'Loading...' : 'Đặt Tour'}
                </button>
              </article>
            ))}
          </div>
        </section>
      </div>
    </main>
  )
}

export default App

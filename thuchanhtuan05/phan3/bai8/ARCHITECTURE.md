# Django + Celery + Redis Architecture

```
┌─────────────────────────────────────────┐
│         Django Web App                  │ Port 8000
│     - REST API endpoints                │
│     - Task creation                     │
│     - Task status checking              │
│     - Admin panel                       │
└────────────────┬────────────────────────┘
                 │ POST /api/tasks/add/
                 │ (create async task)
                 ▼
┌─────────────────────────────────────────┐
│       Redis Broker & Backend            │ Port 6379
│  - Message Queue (celery)               │
│  - Result Backend (task results)        │
│  - Persistence (AOF)                    │
└────────────────┬────────────────────────┘
                 │ (task notification)
                 ▼
┌─────────────────────────────────────────┐
│       Celery Worker(s)                  │
│  - 4 concurrent workers                 │
│  - Execute tasks                        │
│  - Send results to Redis                │
│  - Report progress                      │
└─────────────────────────────────────────┘
```

## Task Flow

1. **API Request**

   ```
   POST /api/tasks/add/ {"a": 5, "b": 3}
   ```

2. **Django Creates Task**
   - Save to Task model
   - Generate Celery task ID
   - Return task_id to client

3. **Celery Worker Picks Up**
   - Read from Redis queue
   - Execute task
   - Update progress in Redis

4. **Client Polls Status**

   ```
   GET /api/status/<task_id>/
   ```

5. **Task Completes**
   - Result stored in Redis
   - Status updated
   - Model saved

## Environment Variables

```yaml
CELERY_BROKER_URL: redis://redis:6379/0
CELERY_RESULT_BACKEND: redis://redis:6379/1
DEBUG: False
```

## Directory Structure

```
app/                          # Django project root
├── config/                   # Django config
│   ├── __init__.py          # Celery initialization
│   ├── celery.py            # Celery config
│   ├── settings.py          # Django settings
│   ├── urls.py              # URL routing
│   └── wsgi.py              # WSGI app
├── tasks/                   # Tasks Django app
│   ├── models.py            # Task model (DB)
│   ├── tasks.py             # Celery tasks
│   ├── views.py             # API views
│   ├── serializers.py       # DRF serializers
│   ├── admin.py             # Django admin
│   └── apps.py              # App config
├── manage.py                # Django CLI
├── requirements.txt         # Python packages
├── Dockerfile               # Docker image
├── init.sh                  # Init script
└── .dockerignore           # Docker ignore
```

## Database Schema

```
Task Model
├── id (PK)
├── title (CharField)
├── description (TextField)
├── celery_task_id (CharField, unique)
├── status (Choice: pending, processing, completed, failed)
├── result (JSONField)
├── created_at (DateTimeField)
└── updated_at (DateTimeField)
```

## Celery Task Types

1. **Simple Math**
   - add_numbers
   - multiply_numbers

2. **Long Running**
   - long_running_task (progress tracking)

3. **Data Processing**
   - process_data (DB interaction)

4. **External Services**
   - send_email (simulated)

## Performance Considerations

- 4 concurrent workers (tunable)
- Redis persistence enabled (AOF)
- Task timeout: 30 minutes
- Health checks on all services
- Volume mounting for live code updates (dev)

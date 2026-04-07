from flask import Flask, jsonify

app = Flask(__name__)

@app.route('/')
def index():
    return jsonify({"message": "Hello from CI/CD Pipeline!"})

@app.route('/health')
def health():
    return jsonify({"status": "UP"}), 200

@app.route('/api/add/<int:a>/<int:b>')
def add(a, b):
    result = a + b
    return jsonify({"a": a, "b": b, "result": result}), 200

@app.route('/api/multiply/<int:a>/<int:b>')
def multiply(a, b):
    result = a * b
    return jsonify({"a": a, "b": b, "result": result}), 200

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)

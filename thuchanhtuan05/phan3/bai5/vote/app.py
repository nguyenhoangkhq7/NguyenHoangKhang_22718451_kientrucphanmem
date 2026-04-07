from flask import Flask, render_template, request, jsonify
import redis
import logging
import os

app = Flask(__name__)

# Redis configuration
redis_client = redis.Redis(
    host=os.environ.get('REDIS_HOST', 'redis'),
    port=int(os.environ.get('REDIS_PORT', 6379)),
    db=0,
    decode_responses=True
)

# Logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/vote', methods=['POST'])
def vote():
    try:
        data = request.json
        choice = data.get('choice')
        
        if choice not in ['vote_a', 'vote_b']:
            return jsonify({'success': False, 'error': 'Invalid choice'}), 400
        
        # Increment vote count in Redis
        redis_client.incr(choice)
        logger.info(f'Vote recorded: {choice}')
        
        return jsonify({'success': True, 'message': 'Vote recorded'}), 200
    except Exception as e:
        logger.error(f'Error recording vote: {str(e)}')
        return jsonify({'success': False, 'error': str(e)}), 500

@app.route('/api/votes', methods=['GET'])
def get_votes():
    try:
        votes_a = int(redis_client.get('vote_a') or 0)
        votes_b = int(redis_client.get('vote_b') or 0)
        
        return jsonify({
            'a': votes_a,
            'b': votes_b
        }), 200
    except Exception as e:
        logger.error(f'Error fetching votes: {str(e)}')
        return jsonify({'error': str(e)}), 500

@app.route('/health', methods=['GET'])
def health():
    return jsonify({'status': 'UP'}), 200

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)

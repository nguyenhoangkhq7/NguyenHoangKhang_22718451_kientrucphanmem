from celery import shared_task
import time
import json
from datetime import datetime
from .models import Task

@shared_task(bind=True)
def add_numbers(self, a, b):
    """Simple task to add two numbers"""
    self.update_state(state='PROGRESS', meta={'current': 0, 'total': 3})
    
    time.sleep(1)
    result = a + b
    
    self.update_state(state='PROGRESS', meta={'current': 3, 'total': 3})
    return {'result': result, 'a': a, 'b': b}

@shared_task(bind=True)
def multiply_numbers(self, a, b):
    """Task to multiply two numbers with progress"""
    self.update_state(state='PROGRESS', meta={'current': 0})
    time.sleep(1)
    
    result = a * b
    
    self.update_state(state='PROGRESS', meta={'current': 100})
    return {'result': result, 'a': a, 'b': b}

@shared_task(bind=True)
def long_running_task(self, duration=10):
    """Simulates a long-running task"""
    steps = 10
    for i in range(steps):
        self.update_state(
            state='PROGRESS',
            meta={'current': i + 1, 'total': steps, 'status': f'Step {i+1}/{steps}'}
        )
        time.sleep(duration / steps)
    
    return {'status': 'completed', 'duration': duration}

@shared_task(bind=True)
def process_data(self, task_id, data):
    """Process data and save result to database"""
    try:
        task = Task.objects.get(celery_task_id=task_id)
        task.status = 'processing'
        task.save()
        
        self.update_state(state='PROGRESS', meta={'status': 'Processing data...'})
        time.sleep(2)
        
        # Simulate processing
        result = {
            'input_data': data,
            'processed_at': datetime.now().isoformat(),
            'items_count': len(data) if isinstance(data, list) else 1
        }
        
        task.status = 'completed'
        task.result = result
        task.save()
        
        return result
    except Task.DoesNotExist:
        return {'error': 'Task not found'}
    except Exception as e:
        if 'task' in locals():
            task.status = 'failed'
            task.result = {'error': str(e)}
            task.save()
        raise

@shared_task
def send_email(email, subject, message):
    """Simulated email sending task"""
    # This would normally send an email
    time.sleep(2)
    return {
        'status': 'sent',
        'email': email,
        'subject': subject
    }

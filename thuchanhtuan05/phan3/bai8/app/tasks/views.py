from rest_framework import viewsets, status
from rest_framework.decorators import action
from rest_framework.response import Response
from rest_framework.views import APIView
from celery.result import AsyncResult
from .models import Task
from .serializers import TaskSerializer
from .tasks import add_numbers, multiply_numbers, long_running_task, send_email

class TaskViewSet(viewsets.ModelViewSet):
    queryset = Task.objects.all()
    serializer_class = TaskSerializer
    
    @action(detail=False, methods=['post'])
    def add(self, request):
        """Create an add task"""
        a = request.data.get('a', 0)
        b = request.data.get('b', 0)
        
        task = Task.objects.create(
            title=f"Add {a} + {b}",
            description=f"Adding two numbers: {a} and {b}"
        )
        
        celery_task = add_numbers.delay(a, b)
        task.celery_task_id = celery_task.id
        task.save()
        
        return Response({
            'task_id': task.id,
            'celery_task_id': celery_task.id,
            'status': task.status
        }, status=status.HTTP_201_CREATED)
    
    @action(detail=False, methods=['post'])
    def multiply(self, request):
        """Create a multiply task"""
        a = request.data.get('a', 1)
        b = request.data.get('b', 1)
        
        task = Task.objects.create(
            title=f"Multiply {a} × {b}",
            description=f"Multiplying two numbers: {a} and {b}"
        )
        
        celery_task = multiply_numbers.delay(a, b)
        task.celery_task_id = celery_task.id
        task.save()
        
        return Response({
            'task_id': task.id,
            'celery_task_id': celery_task.id,
            'status': task.status
        }, status=status.HTTP_201_CREATED)
    
    @action(detail=False, methods=['post'])
    def long_task(self, request):
        """Create a long-running task"""
        duration = request.data.get('duration', 10)
        
        task = Task.objects.create(
            title="Long Running Task",
            description=f"Running for {duration} seconds"
        )
        
        celery_task = long_running_task.delay(duration)
        task.celery_task_id = celery_task.id
        task.save()
        
        return Response({
            'task_id': task.id,
            'celery_task_id': celery_task.id,
            'status': task.status
        }, status=status.HTTP_201_CREATED)
    
    @action(detail=False, methods=['post'])
    def send_email_task(self, request):
        """Create an email sending task"""
        email = request.data.get('email')
        subject = request.data.get('subject', 'Test Email')
        message = request.data.get('message', 'Test message')
        
        task = Task.objects.create(
            title=f"Send Email to {email}",
            description=f"Subject: {subject}"
        )
        
        celery_task = send_email.delay(email, subject, message)
        task.celery_task_id = celery_task.id
        task.save()
        
        return Response({
            'task_id': task.id,
            'celery_task_id': celery_task.id,
            'status': task.status
        }, status=status.HTTP_201_CREATED)

class TaskStatusView(APIView):
    """Check task status by Celery task ID"""
    def get(self, request, task_id):
        celery_result = AsyncResult(task_id)
        
        response = {
            'task_id': task_id,
            'state': celery_result.state,
            'current': 0,
            'total': 100,
            'status': 'Pending...',
            'result': None
        }
        
        if celery_result.state == 'PENDING':
            response['status'] = 'Task waiting to be executed...'
        elif celery_result.state != 'FAILURE':
            response.update(celery_result.info)
        else:
            response['status'] = str(celery_result.info)
            response['result'] = str(celery_result.traceback)
        
        return Response(response)

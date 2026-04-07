from rest_framework import serializers
from .models import Task

class TaskSerializer(serializers.ModelSerializer):
    class Meta:
        model = Task
        fields = ['id', 'title', 'description', 'celery_task_id', 'status', 'result', 'created_at', 'updated_at']
        read_only_fields = ['id', 'created_at', 'updated_at', 'celery_task_id']

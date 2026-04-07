from django.contrib import admin
from .models import Task

@admin.register(Task)
class TaskAdmin(admin.ModelAdmin):
    list_display = ['id', 'title', 'status', 'celery_task_id', 'created_at']
    list_filter = ['status', 'created_at']
    search_fields = ['title', 'celery_task_id']
    readonly_fields = ['celery_task_id', 'created_at', 'updated_at']

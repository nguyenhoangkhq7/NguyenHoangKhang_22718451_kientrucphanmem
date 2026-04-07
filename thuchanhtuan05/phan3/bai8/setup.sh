#!/bin/bash

# Django + Celery Setup Script
# This script initializes the Django application

set -e

echo "========================================"
echo "Django + Celery + Redis Setup"
echo "========================================"

echo ""
echo "1️⃣ Running migrations..."
python manage.py makemigrations --noinput
python manage.py migrate --noinput

echo ""
echo "2️⃣ Creating superuser..."
python manage.py shell << END
from django.contrib.auth.models import User
if not User.objects.filter(username='admin').exists():
    User.objects.create_superuser('admin', 'admin@example.com', 'admin123')
    print('✅ Superuser created: admin:admin123')
else:
    print('✅ Superuser already exists')
END

echo ""
echo "3️⃣ Collecting static files..."
python manage.py collectstatic --noinput --clear

echo ""
echo "✅ Setup complete!"
echo ""
echo "========================================"
echo "Access Points:"
echo "--------------------------------------"
echo "🌐 API:        http://localhost:8000/api/"
echo "👤 Admin:      http://localhost:8000/admin/"
echo "📊 Username:   admin"
echo "🔑 Password:   admin123"
echo "========================================"
echo ""
echo "Available API Endpoints:"
echo "- POST   /api/tasks/add/"
echo "- POST   /api/tasks/multiply/"
echo "- POST   /api/tasks/long_task/"
echo "- POST   /api/tasks/send_email_task/"
echo "- GET    /api/tasks/"
echo "- GET    /api/status/<task_id>/"
echo ""
echo "Start creating tasks!"

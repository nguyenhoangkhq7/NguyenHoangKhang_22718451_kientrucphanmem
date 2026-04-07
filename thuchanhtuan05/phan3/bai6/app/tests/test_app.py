import pytest
import sys
import os

# Add the app directory to path
sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from app import app

@pytest.fixture
def client():
    app.config['TESTING'] = True
    with app.test_client() as client:
        yield client

def test_index(client):
    response = client.get('/')
    assert response.status_code == 200
    assert 'message' in response.json

def test_health(client):
    response = client.get('/health')
    assert response.status_code == 200
    assert response.json['status'] == 'UP'

def test_add(client):
    response = client.get('/api/add/5/3')
    assert response.status_code == 200
    data = response.json
    assert data['a'] == 5
    assert data['b'] == 3
    assert data['result'] == 8

def test_multiply(client):
    response = client.get('/api/multiply/4/5')
    assert response.status_code == 200
    data = response.json
    assert data['a'] == 4
    assert data['b'] == 5
    assert data['result'] == 20

def test_add_negative(client):
    response = client.get('/api/add/-2/3')
    assert response.status_code == 200
    data = response.json
    assert data['result'] == 1

def test_multiply_zero(client):
    response = client.get('/api/multiply/10/0')
    assert response.status_code == 200
    data = response.json
    assert data['result'] == 0

pipeline {
    agent any

    tools {
        jdk 'JDK 21'
        maven 'Maven_3'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat 'mvn -q clean package'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn -q test'
            }
        }

        stage('Docker Compose Up') {
            steps {
                bat 'docker compose up -d --build'
            }
        }

        stage('Containers Running') {
            steps {
                bat 'docker ps'
            }
        }
    }

    post {
        always {
            bat 'docker compose down'
        }
    }
}
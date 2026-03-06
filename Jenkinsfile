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

        stage('Cleanup Old Containers') {
            steps {
                bat 'docker compose down -v || exit /b 0'
                bat 'docker rm -f calculator-db calculator-app || exit /b 0'
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
            bat 'docker compose down -v || exit /b 0'
        }
    }
}
pipeline {
    agent any

    environment {
        IMAGE_NAME = "rest-api-with-docker"
        CONTAINER_NAME = "rest-api-with-docker-container"
        HOST_PORT = "8081"
        CONTAINER_PORT = "8080"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build JAR') {
            steps {
                bat 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t %IMAGE_NAME% .'
            }
        }

        stage('Cleanup Old Containers') {
            steps {
                bat '''
                FOR /f "tokens=*" %%i IN ('docker ps -q --filter "publish=%HOST_PORT%"') DO docker rm -f %%i
                docker rm -f %CONTAINER_NAME% 2>NUL
                '''
            }
        }

        stage('Run Container') {
            steps {
                bat 'docker run -d -p %HOST_PORT%:%CONTAINER_PORT% --name %CONTAINER_NAME% %IMAGE_NAME%'
            }
        }
    }

    post {
        success {
            echo "Application deployed at http://localhost:%HOST_PORT%"
        }
    }
}

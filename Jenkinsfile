pipeline {
    agent any

    environment {
        IMAGE_NAME = "rest-api-with-docker"
        CONTAINER_NAME = "rest-api-with-docker-container"
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

       stage('Stop Old Container') {
    steps {
        bat '''
        docker rm -f rest-api-with-docker-container || exit 0
        '''
    }
}

        stage('Run Container') {
            steps {
                bat 'docker run -d -p 8080:8080 --name %CONTAINER_NAME% %IMAGE_NAME%'
            }
        }
    }

    post {
        success {
            echo 'Application deployed successfully on Docker'
        }
    }
}

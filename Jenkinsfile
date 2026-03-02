pipeline {
    agent any

    environment {
        IMAGE_NAME = "rest-api-with-docker"
        DOCKER_USER = "tapanaws155"
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
        stage('Maven Build') {
            steps {
                sh 'mvn clean package -DskipTests'
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

        stage('Docker Login') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'DOCKERHUB_USER',
                    passwordVariable: 'DOCKERHUB_PASS'
                )]) {
                    bat 'docker login -u %DOCKERHUB_USER% -p %DOCKERHUB_PASS%'
                }
            }
        }
stage('Tag Image') {
    steps {
        bat 'docker tag %IMAGE_NAME% %DOCKER_USER%/%IMAGE_NAME%:%BUILD_NUMBER%'
        bat 'docker tag %IMAGE_NAME% %DOCKER_USER%/%IMAGE_NAME%:latest'
    }
}

stage('Push Image') {
    steps {
        bat 'docker push %DOCKER_USER%/%IMAGE_NAME%:%BUILD_NUMBER%'
        bat 'docker push %DOCKER_USER%/%IMAGE_NAME%:latest'
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
        bat 'docker rm -f %CONTAINER_NAME% 2>NUL'
        bat 'docker pull %DOCKER_USER%/%IMAGE_NAME%:%BUILD_NUMBER%'
        bat 'docker run -d -p %HOST_PORT%:%CONTAINER_PORT% --name %CONTAINER_NAME% %DOCKER_USER%/%IMAGE_NAME%:%BUILD_NUMBER%'
    }
}
    }

    post {
        success {
            echo "Application deployed at http://localhost:%HOST_PORT%"
        }
    }
}

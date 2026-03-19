pipeline {
    agent any

    environment {
        AWS_ACCOUNT_ID = "0607-0433-1174"
        AWS_REGION = "ap-south-1"
        ECR_REPO = "first-rest-api-deployment"
        IMAGE_TAG = "latest"
        CLUSTER_NAME = "my-cluster"
    }

    stages {

        stage('Clone Code') {
            steps {
                git branch: 'develop', url: 'https://github.com/tapanshikari9620/rest-api-with-docker.git'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh '''
                docker build -t $ECR_REPO:$IMAGE_TAG .
                '''
            }
        }

        stage('Login to ECR') {
            steps {
                sh '''
                aws ecr get-login-password --region $AWS_REGION | docker login --username AWS --password-stdin $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com
                '''
            }
        }

        stage('Tag Image') {
            steps {
                sh '''
                docker tag $ECR_REPO:$IMAGE_TAG $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com/$ECR_REPO:$IMAGE_TAG
                '''
            }
        }

        stage('Push to ECR') {
            steps {
                sh '''
                docker push $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com/$ECR_REPO:$IMAGE_TAG
                '''
            }
        }

        stage('Deploy to EKS') {
            steps {
                sh '''
                aws eks --region $AWS_REGION update-kubeconfig --name $CLUSTER_NAME

                kubectl set image deployment/rest-api-deployment rest-api-container=$AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com/$ECR_REPO:$IMAGE_TAG
                '''
            }
        }
    }
}

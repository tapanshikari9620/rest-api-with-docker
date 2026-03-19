pipeline {
    agent any

    environment {
       ACCOUNT_ID = '060704331174'
        AWS_REGION = 'ap-south-1'
        ECR_REPO = 'first-rest-api-deployment'
        IMAGE_TAG = 'latest'
        CLUSTER_NAME = 'my-cluster'
    }

     stages {

        stage('Checkout Code') {
            steps {
                git branch: 'develop', url: 'https://github.com/tapanshikari9620/rest-api-with-docker.git'
            }
        }

        stage('Build JAR') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t first-rest-api:latest .'
            }
        }

        stage('Login to ECR') {
            steps {
                sh '''
                aws ecr get-login-password --region $AWS_REGION | \
                docker login --username AWS --password-stdin $ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com
                '''
            }
        }

        stage('Tag Image') {
            steps {
                sh '''
                docker tag first-rest-api:latest \
                $ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com/$ECR_REPO:$IMAGE_TAG
                '''
            }
        }

        stage('Push to ECR') {
            steps {
                sh '''
                docker push $ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com/$ECR_REPO:$IMAGE_TAG
                '''
            }
        }

        stage('Deploy to EKS') {
    steps {
        sh '''
        aws eks --region $AWS_REGION update-kubeconfig --name $CLUSTER_NAME
        kubectl apply -f k8s/deployment.yaml
        '''
    }
}
    }
}

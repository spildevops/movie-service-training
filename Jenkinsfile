pipeline {
    agent any

    tools {
        maven 'Maven 3.9.5'
        jdk 'JDK 17'
    }

    environment {
        DOCKER_CONFIG = "/temp/.docker"
        REPO_URI = "975050033181.dkr.ecr.ap-northeast-1.amazonaws.com/johan-movie-service"
        REPO_REGISTRY_URL = "https://975050033181.dkr.ecr.ap-northeast-1.amazonaws.com"
        ECR_REGISTRY_CREDENTIAL = 'ecr:ap-northeast-1:aws-credentials'
        REGION = 'ap-northeast-1'
        CLUSTER_NAME = 'movie-cluster'
        SERVICE_NAME = 'movie-service'
        TASK_DEFINITION_FAMILY = 'movie-task-def'
        CONTAINER_NAME = 'movie-container'
    }

    stages {
        stage('Build and Test') {
            steps {
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                script {
                    env.DATE_TAG = sh(script: "date +%Y-%m-%d", returnStdout: true).trim()
                    sh """
                        docker build --platform=linux/amd64 -t ${REPO_URI}:${env.DATE_TAG} .
                        docker tag ${REPO_URI}:${env.DATE_TAG} ${REPO_URI}:latest
                    """
                }
            }
        }

        stage('Push Docker Image to ECR') {
            steps {
                script {
                    docker.withRegistry("${REPO_REGISTRY_URL}", "${ECR_REGISTRY_CREDENTIAL}") {
                        sh "docker push ${REPO_URI}:${env.DATE_TAG}"
                        sh "docker push ${REPO_URI}:latest"
                    }
                }
            }
        }
    }
}
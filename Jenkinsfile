pipeline {
    agent any

    tools {
        maven 'Maven 3.9.5'
        jdk 'JDK 17'
    }

    environment {
        REPO_URI = "975050033181.dkr.ecr.ap-northeast-1.amazonaws.com/johan-movie-service"
        REPO_REGISTRY_URL = "https://975050033181.dkr.ecr.ap-northeast-1.amazonaws.com"
        ECR_REGISTRY_CREDENTIAL = 'ecr:ap-northeast-1:aws-credentials'
        REGION = 'ap-northeast-1'
        CLUSTER_NAME = 'johan-prod'
        SERVICE_NAME = 'johan-movie-service'
        CONTAINER_NAME = 'johan-movie-service'
        SLACK_CHANNEL = 'learn-jenkins'
    }

    stages {
        stage('Build and Test') {
            steps {
                slackSend(channel: "${SLACK_CHANNEL}", message: "📦 Starting build for *${env.JOB_NAME}* #${env.BUILD_NUMBER}")
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
                    slackSend(channel: "${SLACK_CHANNEL}", message: "🐳 Docker image built with tag `${env.DATE_TAG}`")
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
                    slackSend(channel: "${SLACK_CHANNEL}", message: "📤 Pushed Docker image `${env.DATE_TAG}` and `latest` to ECR")
                }
            }
        }

        stage('Deploying Image to ECS') {
            steps {
                script {
                    sh """
                        aws ecs update-service \
                          --cluster ${CLUSTER_NAME} \
                          --service ${SERVICE_NAME} \
                          --force-new-deployment \
                          --region ${REGION}
                    """
                }
                slackSend(channel: "${SLACK_CHANNEL}", message: "🚀 Deployed to ECS service *${SERVICE_NAME}*")
            }
        }
    }

    post {
        always {
            slackSend(
                channel: "${SLACK_CHANNEL}",
                message: ":information_source: Build *${env.JOB_NAME}* #${env.BUILD_NUMBER} finished with status: *${currentBuild.currentResult}*.\nURL: ${env.BUILD_URL}",
            )
        }
    }
}
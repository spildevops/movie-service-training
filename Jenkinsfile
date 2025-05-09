pipeline {
    agent any

    tools {
        maven 'Maven 3.9.5'
        jdk 'JDK 17'
    }

    environment {
        REPO_URI = "ronald-movie-service-ecr"
        REPO_REGISTRY_URL = "https://975050033181.dkr.ecr.ap-northeast-1.amazonaws.com"
        REGION = "ap-northeast-1"
    }
       
    stages {
        stage('Compile Application') {
            steps {
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                script {
                    env.DATE_TAG = sh(script: "date +%Y-%m-%d".returnStdout: true).trim()
                    sh """
                        docker build -t ${REPO_URI}:${env.DATE_TAG} .
                        docker tag ${REPO_URI}:${env.DATE_TAG} ${REPO_URI}:latest
                    """
                }
            }
        }

        stage('Docker Push to Amazon ECR') {
            environment {
                ECR_REGISTRY_CREDENTIALS = 'ecr:ap-northeast-1:aws-credentials'
            }
            steps {
                script {
                    docker.withRegistry("${REPO_REGISTRY_URL}", "${ECR_REGISTRY_CREDENTIALS}") {
                        sh """
                            docker push ${REPO_URI}:{env.DATE_TAG}
                            docker push ${REPO_URI}:latest
                        """
                    }
                }
            }
        }
    }
    
    post {
        always {
            echo 'This is always'
        }
        success {
            echo 'This is only when successful'
        }
        failure {
            echo 'This is only when failed'
        }
    }
}

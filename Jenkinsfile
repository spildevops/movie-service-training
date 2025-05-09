pipeline {
    agent any

    tools {
        maven 'Maven 3.9.5'
        jdk 'JDK 17'
    }
       
    stages {
        stage('Test') {
            steps {
                sh './mvnw clean package -DskipTests'
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

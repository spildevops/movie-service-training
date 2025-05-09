pipeline {
    agent any
       
    stages {
        stage('Test') {
            steps {
                echo "Hello"
                echo "Hello again..."
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

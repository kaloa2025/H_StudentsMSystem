pipeline {
    agent any

    environment {
        EC2_USER = 'ec2-user' // Update with your EC2 username
        EC2_HOST = '13.61.13.30' // Public IP of EC2
        PEM_FILE = '/Desktop/honors.pem' // Full path to your SSH private key
        APP_NAME = 'student-management-system'
        JAR_NAME = "${APP_NAME}-0.0.1-SNAPSHOT.jar"
    }

    stages {
        stage('Checkout Code') {
            steps {
                echo 'Checking out code from GitHub...'
                checkout scm
            }
        }

        stage('Build Application') {
            steps {
                echo 'Building the Spring Boot application...'
                sh 'mvn clean package'
            }
        }

        stage('Upload to EC2') {
            steps {
                echo 'Uploading the JAR file to EC2...'
                sh """
                scp -i ${PEM_FILE} target/${JAR_NAME} ${EC2_USER}@${EC2_HOST}:/home/${EC2_USER}/${JAR_NAME}
                """
            }
        }

        stage('Deploy Application') {
            steps {
                echo 'Starting the application on EC2...'
                sh """
                ssh -i ${PEM_FILE} ${EC2_USER}@${EC2_HOST} << EOF
                pkill -f ${JAR_NAME} || true
                nohup java -jar /home/${EC2_USER}/${JAR_NAME} > /home/${EC2_USER}/${APP_NAME}.log 2>&1 &
                EOF
                """
            }
        }
    }

    post {
        success {
            echo 'Deployment Successful!'
        }
        failure {
            echo 'Deployment Failed!'
        }
    }
}

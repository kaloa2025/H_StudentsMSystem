pipeline {
    agent any

    environment {
        EC2_USER = 'ec2-user' // Update with your EC2 username
        EC2_HOST = '13.61.13.30' // Public IP of EC2
        PEM_FILE = '/home/aalok2025/honors.pem' // Full path to your SSH private key
        APP_NAME = 'student'
        JAR_NAME = "${APP_NAME}-0.0.1-SNAPSHOT.jar"
    }
    tools {
        maven 'Maven' // Use the name you provided in Global Tool Configuration
    }
    tools {
        jdk 'Java21'
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
        stage('Check Environment') {
            steps {
                sh 'java -version'
                sh 'mvn -version'
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

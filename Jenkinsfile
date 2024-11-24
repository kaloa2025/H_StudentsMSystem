pipeline {
    agent any

    environment {
        EC2_USER = 'ubuntu' // Update with your EC2 username
        EC2_HOST = '13.61.13.30' // Public IP of EC2
        PEM_FILE = 'C:/Users/Lenovo/Downloads/honors.pem' // Full path to your SSH private key
        APP_NAME = 'student'
        JAR_NAME = "${APP_NAME}-0.0.1-SNAPSHOT.jar"
    }
    tools {
        maven 'Maven'
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
                sh '''
                scp -i C:/Users/Lenovo/Downloads/honors.pem C:/Users/Lenovo/.jenkins/workspace/H_SMS/target/aalok_honors-0.0.1-SNAPSHOT.jar ubuntu@13.61.13.30:/home/ubuntu/student-0.0.1-SNAPSHOT.jar
                '''
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

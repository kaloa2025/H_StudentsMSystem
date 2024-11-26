pipeline {
    agent any

    tools {
        maven "Maven"  // Ensure Maven is installed
    }

    environment {
        EC2_USER = 'ubuntu'
        EC2_HOST = 'ec2-13-60-201-40.eu-north-1.compute.amazonaws.com'
        PEM_FILE_PATH = 'C:/Users/Lenovo/Downloads/honors.pem'
        JAR_NAME = 'aalok_honors-0.0.1-SNAPSHOT.jar'
    }

    stages {
        stage('Checkout') {
            steps {
                // Fetch Github Repository
                git branch: 'main', url: 'https://github.com/kaloa2025/H_StudentsMSystem'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean install'
            }
            post {
                success {
                    archiveArtifacts 'target/*.jar'
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    // Copy JAR file to the EC2 instance
                    bat "scp -i \"${PEM_FILE_PATH}\" target/${JAR_NAME} ${EC2_USER}@${EC2_HOST}:/home/ubuntu/"

                    // Start the application on the EC2 server
                    bat "ssh -i \"${PEM_FILE_PATH}\" ${EC2_USER}@${EC2_HOST} java -jar /home/ubuntu/${JAR_NAME}"
                }
            }
        }
    }

    post {
        success {
            archiveArtifacts 'target/*.jar'
            echo 'Deployment successful!'
        }
        failure {
            echo 'Build or deployment failed!'
        }
    }
}
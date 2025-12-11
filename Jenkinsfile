pipeline {
    agent any

    tools {
        maven "M3"
    }

    environment {
        DOCKER_HUB_USER = "shenbagalakshmi6"
        IMAGE_NAME = "demo-app"
        DOCKER_CREDENTIALS = "Docker-Id"   // <---- updated Docker Hub credential ID
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/ShenbagaLakshmi-A/tcs-devops-architect-demo.git',
                    credentialsId: 'git'   // <---- your Git credential ID
            }
        }

        stage('Build with Maven') {
            steps {
                bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }
            post {
                success {
                    junit '**/target/surefire-reports/*.xml'
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                bat "docker build -t %DOCKER_HUB_USER%/%IMAGE_NAME%:latest ."
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'Docker-Id',      // <--- updated
                    usernameVariable: 'USER',
                    passwordVariable: 'PASS'
                )]) {
                    bat "docker login -u %USER% -p %PASS%"
                    bat "docker push %DOCKER_HUB_USER%/%IMAGE_NAME%:latest"
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                bat "kubectl apply -f deployment.yaml"
            }
        }
    }
}

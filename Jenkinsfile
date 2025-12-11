pipeline {
    agent any

    tools {
        maven "M3"
    }

    environment {
        DOCKER_HUB_USER = "shenbagalakshmi6"
        IMAGE_NAME = "demo-app"
        DOCKER_CREDENTIALS = "Docker-Id"      // Docker Hub creds
        KUBECONFIG_CRED = "kubeconfig"        // Kubeconfig file credential ID
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/ShenbagaLakshmi-A/tcs-devops-architect-demo.git',
                    credentialsId: 'git'
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
                    credentialsId: 'Docker-Id',
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
                withCredentials([file(credentialsId: 'kubeconfig', variable: 'KCFG')]) {
                    bat """
                        set KUBECONFIG=%KCFG%
                        kubectl apply -f deployment.yaml --validate=false
                    """
                }
            }
        }
    }
}

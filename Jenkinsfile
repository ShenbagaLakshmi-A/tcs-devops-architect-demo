pipeline {
    agent any

    tools {
        maven "M3"
    }

    environment {
        DOCKER_HUB_USER = "shenbagalakshmi6"
        IMAGE_NAME = "demo-app"
        DOCKER_CREDENTIALS = "docker-hub-creds"
    }

    stages {

        stage('Build') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/ShenbagaLakshmi-A/tcs-devops-architect-demo.git',
                    credentialsId: 'git'      // <--- YOUR CREDENTIAL ID
               
                sh "mvn -Dmaven.test.failure.ignore=true clean package"
            }
            post {
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    bat "docker build -t ${DOCKER_HUB_USER}/${IMAGE_NAME}:latest ."
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    withCredentials([usernamePassword(
                        credentialsId: DOCKER_CREDENTIALS,
                        usernameVariable: 'USER',
                        passwordVariable: 'PASS'
                    )]) {
                        bat "docker login -u %USER% -p %PASS%"
                        bat "docker push ${DOCKER_HUB_USER}/${IMAGE_NAME}:latest"
                    }
                }
            }
        }

        stage('Deploy the application in k8s') {
            steps {
                sh "kubectl apply -f deployment.yaml"
            }
        }
    }
}

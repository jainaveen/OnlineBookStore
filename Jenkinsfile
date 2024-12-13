    pipeline {
        agent any
        environment {
            mavenHome = tool 'maven3'
            BUILD_VERSION = "1.0.${BUILD_NUMBER}-${new Date().format('yyyyMMdd-HHmmss')}"
            DOCKER_IMAGE = "adhadhi/onlinebookstore:${BUILD_VERSION}"
        }
        tools {
            jdk 'jdk17'
        }
        stages {
            stage('Build'){
                steps {
                    bat "mvn clean install -DskipTests"
                }
            }
            stage('Test'){
                steps{
                    bat "mvn test"
                }
            }

            stage('Build and Tag Docker Image') {
                steps {
                    bat """
                    docker build -t ${DOCKER_IMAGE} .
                    """
                }
            }

            stage('Push Docker Image') {
                        steps {
                            script {
                                withDockerRegistry([credentialsId: 'docker-credentials', url: 'https://index.docker.io/v1/']) {
                                    bat """
                                    docker push ${DOCKER_IMAGE}
                                    """
                                }
                            }
                        }
            }

            stage('Deploy') {
                steps {
                    bat "mvn deploy"
                }
            }
            stage('Archive Artifacts') {
                steps {
                    archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
                }
            }

        }
    }
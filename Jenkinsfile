    pipeline {
        agent any
        environment {
            mavenHome = tool 'maven3'
            BUILD_VERSION = "1.0.${BUILD_NUMBER}-${new Date().format('yyyyMMdd-HHmmss')}"
            DOCKER_IMAGE = "adhadhi/onlinebookstore:${BUILD_VERSION}"
            DOCKER_IMAGE_LATEST = "adhadhi/onlinebookstore:latest"
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
                    docker tag ${DOCKER_IMAGE} ${DOCKER_IMAGE_LATEST}
                    """
                }
            }

            stage('Push Docker Image') {
                        steps {
                            script {
                                withDockerRegistry([credentialsId: 'docker-credentials', url: 'https://index.docker.io/v1/']) {
                                  script {
                                          // Use Docker Hub credentials (with access token)
                                          docker.withRegistry('https://index.docker.io/v1/', 'docker-credentials') {
                                              bat "docker push ${DOCKER_IMAGE}"
                                              bat "docker push ${DOCKER_IMAGE_LATEST}"
                                          }
                                  }
                                }
                            }
                        }
            }

            stage('Archive Artifacts') {
                steps {
                    archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
                }
            }

        }
    }
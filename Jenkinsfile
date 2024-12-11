pipeline {
	agent any
	environment {
		mavenHome = tool 'maven3'
		BUILD_VERSION = "1.0.${BUILD_NUMBER}-${env.BUILD_TIMESTAMP}"
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
		stage('Set Dynamic Version') {
             steps {
                  script {
                            // Generate the dynamic version using build number and timestamp
                            env.BUILD_TIMESTAMP = new Date().format("yyyyMMddHHmmss", TimeZone.getTimeZone('UTC'))
                            env.BUILD_VERSION = "1.0.${BUILD_NUMBER}-${env.BUILD_TIMESTAMP}"

                            // Replace the version in pom.xml
                            sh "sed -i 's|<version>.*</version>|<version>${BUILD_VERSION}</version>|' pom.xml"
                  }
             }
        }


		stage('Deploy') {
			steps {
			    bat "mvn jar:jar deploy:deploy"
			}
		}
		stage('Archive Artifacts') {
            steps {
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            }
        }

	}
}
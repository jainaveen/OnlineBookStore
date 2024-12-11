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
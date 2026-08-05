pipeline {

    agent any

    environment {

        PROJECT_ID = "YOUR_GCP_PROJECT"

        REGION = "asia-south2"

        REPOSITORY = "YOUR_ARTIFACT_REPO"

        IMAGE = "YOUR_IMAGE_NAME"

        CLUSTER = "flumpland-cluster"

        ZONE = "asia-south2-b"

        IMAGE_TAG = "${BUILD_NUMBER}"

    }

    stages {

        stage('Checkout') {

            steps {

                checkout scm

            }

        }

        stage('Test') {

            steps {

                sh 'mvn test'

            }

        }

        stage('Package') {

            steps {

                sh 'mvn clean package -DskipTests'

            }

        }

        stage('Configure Docker') {

            steps {

                sh '''

                gcloud auth configure-docker ${REGION}-docker.pkg.dev -q

                '''

            }

        }

        stage('Build Image') {

            steps {

                sh '''

                docker build -t ${REGION}-docker.pkg.dev/${PROJECT_ID}/${REPOSITORY}/${IMAGE}:${IMAGE_TAG} .

                '''

            }

        }

        stage('Push Image') {

            steps {

                sh '''

                docker push ${REGION}-docker.pkg.dev/${PROJECT_ID}/${REPOSITORY}/${IMAGE}:${IMAGE_TAG}

                '''

            }

        }

        stage('Connect GKE') {

            steps {

                sh '''

                gcloud container clusters get-credentials ${CLUSTER} --zone ${ZONE}

                '''

            }

        }

        stage('Deploy') {

            steps {

                sh '''

                sed -i "s|IMAGE_TAG|${IMAGE_TAG}|g" k8s/deployment.yaml

                kubectl apply -f k8s/

                '''

            }

        }

        stage('Verify') {

            steps {

                sh '''

                kubectl rollout status deployment/razorpay

                kubectl get pods

                kubectl get svc

                '''

            }

        }

    }

}
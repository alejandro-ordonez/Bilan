pipeline {
    agent any
    tools {
        maven 'maven-3.6.3'
        jdk 'jdk-17.0.4.1'
    }
    environment {
        VERSION_TAG = ''
    }
    parameters {
        choice(
            choices: ['CERTIFICACION', 'PRODUCCION'],
            description: 'Seleccione el entorno para compilar',
            name: 'ENTORNO'
        )
    }
    stages {
        stage('Preparation') {
            steps {
                script {
                    VERSION_TAG = sh(script: "git describe --tags --abbrev=0", returnStdout: true).trim()
                    if (params.ENTORNO == 'CERTIFICACION') {
                        VERSION_TAG = "${VERSION_TAG}-SNAPSHOT"
                    }
                    echo "Building APP310 backend version: ${VERSION_TAG}"
                }
            }
        }
        stage('Build Backend') {
            steps {
                dir('bilan-backend') {
                    sh "mvn clean install -DskipTests"
                }
            }
        }
        stage('Package Backend') {
            steps {
                dir('bilan-backend') {
                    sh "mvn package -DskipTests"
                }
            }
        }
        stage('Upload to Nexus') {
            steps {
                script {
                    dir('bilan-backend') {
                        def repositorio = params.ENTORNO == 'PRODUCCION' ? "APP310-releases" : "APP310-snapshots"
                        def groupIdPath = 'org/bilan/co/backend'
                        def artifactId = 'APP310-backend'

                        def artefactos = findFiles(glob: '**/target/*.jar')
                        if (artefactos.length > 0) {
                            def paths = artefactos.collect { "'" + it.path + "'" }.join(' ')
                            echo "Artefactos encontrados: ${paths}"

                            sh "tar -cvf ${artifactId}-${VERSION_TAG}.tar ${paths}"

                            nexusArtifactUploader(
                                credentialsId: 'nexus',
                                groupId: groupIdPath,
                                nexusUrl: 'nexus.mineducacion.gov.co',
                                nexusVersion: 'nexus3',
                                protocol: 'https',
                                repository: repositorio,
                                version: VERSION_TAG,
                                artifacts: [
                                    [
                                        artifactId: artifactId,
                                        classifier: '',
                                        file: "${artifactId}-${VERSION_TAG}.tar",
                                        type: 'tar'
                                    ]
                                ]
                            )
                            sh "rm -rf ${artifactId}-${VERSION_TAG}.tar"
                        } else {
                            error "No se encontraron archivos .jar en los submódulos"
                        }
                    }
                }
            }
        }
    }
}

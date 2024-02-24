#!/usr/bin/env groovy

/**
 Example how to use:

 pipeline {
    agent any
    stages {
        stage('') {
            steps {
                git branch: 'main', url: 'https://github.com/example/image.git' 
                script {
                    // with params
                    buildContainerImageUsingDocker('teste', 'latest', '.', ['executableDockerBin': '/usr/local/bin/docker', 'contextPath': './'])
                    // simple mode
                    buildContainerImageUsingDocker('teste', 'latest', '.')
                }
            }
        }
    }
}
 */
def call(String image, String tag, Map<String, String> params = [:]) {
    // param 'executableDockerBin' -> docker binary executable
    String baseCommand = params.any{k -> k.key == 'executableDockerBin'} ? "${params['executableDockerBin']} build -t ${image}:${tag} " : "docker build -t ${image}:${tag} "
    
    // param 'contextPath' -> working directory to run build
    if (params.any{k -> k.key == 'contextPath'}) {
        baseCommand += "${params['contextPath']} "
    } else {
        baseCommand += '.'
    }
    
    
    // execute command 
    if (isUnix()) {
        // linux or mac os
        sh "${baseCommand}"
    } else {
        // windows
        bat "${baseCommand}"
    }
}
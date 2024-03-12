#!/usr/bin/env groovy

def call(String registryUrl, String credentialsId, String executablePath = 'docker') {
    withCredentials([usernamePassword(credentialsId: credentialsId, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
        def host = params.containsKey('host') ? "-H ${params.get('host')}" : ''
        def command = "echo \$PASSWORD | $executablePath ${host} login $registryUrl --username \$USERNAME --password-stdin"
        if (isUnix()) { 
            sh command
        } else { 
            bat command
        }
    }
}

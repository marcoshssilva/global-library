#!/usr/bin/env groovy

def call(String registryUrl, String credentialsId, String executablePath = 'docker') {
    withCredentials([usernamePassword(credentialsId: credentialsId, usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
        def host = params.containsKey('host') ? "-H ${params['host']}" : ''
        def useTls = params.containsKey('useTls') && params['useTls'] ? ' --tls' : ''
        def tlsCertificate = params.containsKey('tlsCertificate') ? " --tlskey=${params['tlsCertificate']}" : ''
        def tlsPrivateKey  = params.containsKey('tlsPrivateKey')  ? " --tlskey=${params['tlsPrivateKey']}"  : ''
        def command = "echo \$PASSWORD | $executablePath ${host}${useTls}${tlsCertificate}${tlsPrivateKey} login $registryUrl --username \$USERNAME --password-stdin"
        if (isUnix()) { 
            sh command
        } else { 
            bat command
        }
    }
}

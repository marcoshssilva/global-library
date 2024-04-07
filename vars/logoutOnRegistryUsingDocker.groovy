#!/usr/bin/env groovy

def call(String registryUrl, String executablePath = 'docker') {
    def host = params.containsKey('host') ? "-H ${params['host']}" : ''
    def useTls = params.containsKey('useTls') && params['useTls'] ? ' --tls' : ''
    def tlsCertificate = params.containsKey('tlsCertificate') ? " --tlskey=${params['tlsCertificate']}" : ''
    def tlsPrivateKey  = params.containsKey('tlsPrivateKey')  ? " --tlskey=${params['tlsPrivateKey']}"  : ''
    def command = "$executablePath ${host}${useTls}${tlsCertificate}${tlsPrivateKey} logout $registryUrl"
    if (isUnix()) { 
        sh command
    } else { 
        bat command
    }
    
}

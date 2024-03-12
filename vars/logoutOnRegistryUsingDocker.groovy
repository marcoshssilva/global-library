#!/usr/bin/env groovy

def call(String registryUrl, String executablePath = 'docker') {
    def host = params.containsKey('host') ? "-H ${params['host']}" : ''
    def command = "$executablePath ${host} logout $registryUrl"
    if (isUnix()) { 
        sh command
    } else { 
        bat command
    }
    
}

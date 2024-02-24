#!/usr/bin/env groovy

def call(String registryUrl, String executablePath = 'docker') {
    
    def command = "$executablePath logout $registryUrl"
    if (isUnix()) { 
        sh command
    } else { 
        bat command
    }
    
}
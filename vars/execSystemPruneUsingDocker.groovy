#!/usr/bin/env groovy

def call(Boolean deleteAll, Boolean deleteVolumes, Map<String, String> params = [:]) {
    
    def executableDockerBin = params.containsKey('executableDockerBin') ? params['executableDockerBin'] : 'docker'
    def paramDeleteAll = deleteAll ? ' --all' : ''
    def paramDeleteVolumes = deleteVolumes ? ' --volumes' : ''

    def commandToRun = "${executableDockerBin} system prune${paramDeleteAll}${paramDeleteVolumes}"
    if (isUnix()) {
        sh "${commandToRun}"
    } else {
        bat "${commandToRun}"
    }

}
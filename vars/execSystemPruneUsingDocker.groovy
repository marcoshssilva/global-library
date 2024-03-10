#!/usr/bin/env groovy

def call(Boolean deleteAll, Boolean deleteVolumes, Map<String, String> params = [:]) {
    
    def executableDockerBin = params.containsKey('executableDockerBin') ? params['executableDockerBin'] : 'docker'
    def paramDeleteAll = deleteAll ? ' --all' : ''
    def paramDeleteVolumes = deleteVolumes ? ' --volumes' : ''
    def becomeSudo = params.getOrDefault('becomeSudo', 'false') == 'true' ? 'sudo ' : ''

    def commandToRun = "echo Y | ${becomeSudo}${executableDockerBin} system prune${paramDeleteAll}${paramDeleteVolumes}"
    def commandPrintUsages = "${becomeSudo}${executableDockerBin} system df"
    if (isUnix()) {
        sh "${commandToRun}"
    } else {
        bat "${commandToRun}"
    }

}

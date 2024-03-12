#!/usr/bin/env groovy

def call(Boolean deleteAll, Boolean deleteVolumes, Map<String, String> params = [:]) {
    
    def executableDockerBin = params.containsKey('executableDockerBin') ? params['executableDockerBin'] : 'docker'
    def paramDeleteAll = deleteAll ? ' --all' : ''
    def paramDeleteVolumes = deleteVolumes ? ' --volumes' : ''
    def becomeSudo = params.getOrDefault('becomeSudo', 'false') == 'true' ? 'sudo ' : ''
    def host = params.containsKey('host') ? '-H ${host}' : ''

    def commandToRun = "echo Y | ${becomeSudo}${executableDockerBin} ${host} system prune${paramDeleteAll}${paramDeleteVolumes}"
    def commandPrintUsages = "${becomeSudo}${executableDockerBin} ${host} system df"
    if (isUnix()) {
        sh "${commandToRun}"
    } else {
        bat "${commandToRun}"
    }

}

#!/usr/bin/env groovy

def call(Boolean deleteAll, Boolean deleteVolumes, Map<String, String> params = [:]) {
    
    def executableDockerBin = params.containsKey('executableDockerBin') ? params['executableDockerBin'] : 'docker'
    def paramDeleteAll = deleteAll ? ' --all' : ''
    def paramDeleteVolumes = deleteVolumes ? ' --volumes' : ''
    def becomeSudo = params.getOrDefault('becomeSudo', 'false') == 'true' ? 'sudo ' : ''
    def host = params.containsKey('host') ? "-H ${params['host']}" : ''
    def useTls = params.containsKey('useTls') && params['useTls'] ? ' --tls' : ''
    def tlsCertificate = params.containsKey('tlsCertificate') ? " --tlskey=${params['tlsCertificate']}" : ''
    def tlsPrivateKey  = params.containsKey('tlsPrivateKey')  ? " --tlskey=${params['tlsPrivateKey']}"  : ''

    def commandToRun = "echo Y | ${becomeSudo}${executableDockerBin}${useTls}${tlsCertificate}${tlsPrivateKey} ${host} system prune${paramDeleteAll}${paramDeleteVolumes}"
    def commandPrintUsages = "${becomeSudo}${executableDockerBin}${useTls}${tlsCertificate}${tlsPrivateKey} ${host} system df"
    if (isUnix()) {
        sh "${commandToRun}"
    } else {
        bat "${commandToRun}"
    }

}

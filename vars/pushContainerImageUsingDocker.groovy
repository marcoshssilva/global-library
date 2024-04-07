#!/usr/bin/env groovy

def call(String image, String tag, Map<String, String> params = [:]) {
    def executableDockerBin = params.containsKey('executableDockerBin') ? params['executableDockerBin'] : 'docker'
    def mirror = params.containsKey('mirror') ? params['mirror'] + '/' : ''
    def becomeSudo = params.getOrDefault('becomeSudo', 'false') == 'true' ? 'sudo ' : ''
    def host = params.containsKey('host') ? "-H ${params['host']}" : ''
    def useTls = params.containsKey('useTls') && params['useTls'] ? ' --tls' : ''
    def tlsCertificate = params.containsKey('tlsCertificate') ? " --tlskey=${params['tlsCertificate']}" : ''
    def tlsPrivateKey  = params.containsKey('tlsPrivateKey')  ? " --tlskey=${params['tlsPrivateKey']}"  : ''

    def baseCommandTag  = "${becomeSudo}${executableDockerBin}${useTls}${tlsCertificate}${tlsPrivateKey} ${host} tag ${image}:${tag} ${mirror}${image}:${tag}"
    def baseCommandPull = "${becomeSudo}${executableDockerBin}${useTls}${tlsCertificate}${tlsPrivateKey} ${host} push ${mirror}${image}:${tag}"
    def baseCommandRmi  = "${becomeSudo}${executableDockerBin}${useTls}${tlsCertificate}${tlsPrivateKey} ${host} rmi ${mirror}${image}:${tag}"

    if (isUnix()) {
        if (mirror != '') {
            sh "${baseCommandTag}"
        }
        sh "${baseCommandPull}"
        sh "${baseCommandRmi}"
    } else {
        if (mirror != '') {
            bat "${baseCommandTag}"
        }
        bat "${baseCommandPull}"
        bat "${baseCommandRmi}"
    }
}

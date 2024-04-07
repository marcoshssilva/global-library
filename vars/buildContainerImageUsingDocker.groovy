#!/usr/bin/env groovy

def call(String image, String tag, Map<String, String> params = [:]) {
    def executableDockerBin = params.getOrDefault('executableDockerBin', 'docker')
    def contextPath = params.getOrDefault('contextPath', '.')
    def platform = params.containsKey('platform') ? "--platform ${params['platform']}" : ''
    def becomeSudo = params.getOrDefault('becomeSudo', 'false') == 'true' ? 'sudo ' : ''
    def options = params.getOrDefault('options', '')
    def host = params.containsKey('host') ? "-H ${params['host']}" : ''
    def useTls = params.containsKey('useTls') && params['useTls'] ? ' --tls' : ''
    def tlsCertificate = params.containsKey('tlsCertificate') ? " --tlskey=${params['tlsCertificate']}" : ''
    def tlsPrivateKey  = params.containsKey('tlsPrivateKey')  ? " --tlskey=${params['tlsPrivateKey']}"  : ''

    def baseCommand = "${becomeSudo}${executableDockerBin}${useTls}${tlsCertificate}${tlsPrivateKey} ${host} build ${options} -t ${image}:${tag} ${platform} ${contextPath}"

    if (isUnix()) {
        sh "${baseCommand}"
    } else {
        bat "${baseCommand}"
    }
}

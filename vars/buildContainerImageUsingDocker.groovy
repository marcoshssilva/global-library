#!/usr/bin/env groovy

def call(String image, String tag, Map<String, String> params = [:]) {
    def executableDockerBin = params.getOrDefault('executableDockerBin', 'docker')
    def contextPath = params.getOrDefault('contextPath', '.')
    def platform = params.containsKey('platform') ? "--platform ${params['platform']}" : ''
    def becomeSudo = params.getOrDefault('becomeSudo', 'false') == 'true' ? 'sudo ' : ''
    def options = params.getOrDefault('options', '')
    def host = params.containsKey('host') ? '-H ${host}' : ''

    def baseCommand = "${becomeSudo}${executableDockerBin} ${host} build ${options} -t ${image}:${tag} ${platform} ${contextPath}"

    if (isUnix()) {
        sh "${baseCommand}"
    } else {
        bat "${baseCommand}"
    }
}

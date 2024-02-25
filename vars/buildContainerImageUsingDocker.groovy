#!/usr/bin/env groovy

def call(String image, String tag, Map<String, String> params = [:]) {
    def executableDockerBin = params.containsKey('executableDockerBin') ? params['executableDockerBin'] : 'docker'
    def contextPath = params.containsKey('contextPath') ? params['contextPath'] : '.'
    def platform = params.containsKey('platform') ? "--platform ${params['platform']}" : ''

    def baseCommand = "${executableDockerBin} build -t ${image}:${tag} ${platform} ${contextPath}"

    if (isUnix()) {
        sh "${baseCommand}"
    } else {
        bat "${baseCommand}"
    }
}

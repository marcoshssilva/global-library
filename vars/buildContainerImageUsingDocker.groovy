#!/usr/bin/env groovy

def call(String image, String tag, Map<String, String> params = [:]) {
    def executableDockerBin = params.containsKey('executableDockerBin') ? params['executableDockerBin'] : 'docker'
    def contextPath = params.containsKey('contextPath') ? params['contextPath'] : '.'

    def baseCommand = "${executableDockerBin} build -t ${image}:${tag} ${contextPath}"

    if (isUnix()) {
        sh "${baseCommand}"
    } else {
        bat "${baseCommand}"
    }
}
#!/usr/bin/env groovy

def call(String image, String tag, Map<String, String> params = [:]) {
    def executableDockerBin = params.containsKey('executableDockerBin') ? params['executableDockerBin'] : 'docker'
    def mirror = params.containsKey('mirror') ? params['mirror'] + '/' : ''

    def baseCommandTag  = "${executableDockerBin} tag ${image}:${tag} ${mirror}${image}:${tag}"
    def baseCommandPull = "${executableDockerBin} push ${mirror}${image}:${tag}"
    def baseCommandRmi  = "${executableDockerBin} rmi ${mirror}${image}:${tag}"

    if (isUnix()) {
        sh "${baseCommandTag}"
        sh "${baseCommandPull}"
        sh "${baseCommandRmi}"
    } else {
        bat "${baseCommandTag}"
        bat "${baseCommandPull}"
        bat "${baseCommandRmi}"
    }
}

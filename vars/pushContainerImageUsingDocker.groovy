#!/usr/bin/env groovy

def call(String image, String tag, Map<String, String> params = [:]) {
    def executableDockerBin = params.containsKey('executableDockerBin') ? params['executableDockerBin'] : 'docker'
    def mirror = params.containsKey('mirror') ? params['mirror'] + '/' : ''
    def becomeSudo = params.getOrDefault('becomeSudo', 'false') == 'true' ? 'sudo ' : ''

    def baseCommandTag  = "${becomeSudo}${executableDockerBin} tag ${image}:${tag} ${mirror}${image}:${tag}"
    def baseCommandPull = "${becomeSudo}${executableDockerBin} push ${mirror}${image}:${tag}"
    def baseCommandRmi  = "${becomeSudo}${executableDockerBin} rmi ${mirror}${image}:${tag}"

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

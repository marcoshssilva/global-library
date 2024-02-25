#!/usr/bin/env groovy

def call(String image, String tag, Map<String, String> params = [:]) {
    def executableDockerBin = params.containsKey('executableDockerBin') ? params['executableDockerBin'] : 'docker'
    def mirror = params.containsKey('mirror') ? params['mirror'] + '/' : ''

    if(mirror != '') {
        def baseCommandTag  = "${executableDockerBin} tag ${image}:${tag} ${mirror}${image}:${tag}"
        def baseCommandPull = "${executableDockerBin} push ${mirror}${image}:${tag}"
        def baseCommandRmi  = "${executableDockerBin} rmi ${mirror}${image}:${tag}"
    } else {
        def baseCommandTag  = "echo 'SKIP TAG'"
        def baseCommandPull = "${executableDockerBin} push ${image}:${tag}"
        def baseCommandRmi  = "${executableDockerBin} rmi ${image}:${tag}"
    }
    

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

#!/usr/bin/groovy
def call(Map parameters = [:], body) {

    def label = parameters.get('label', 'maven')

    def mavenImage = parameters.get('mavenImage', 'maven')

    // 0.13 introduces a breaking change when defining pod env vars so check version before creating build pod
   
    echo "building using the docker socket"

    podTemplate(label: label, inheritFrom: 'base',
            containers: [
                    [name: 'maven', image: "${mavenImage}", command: '/bin/sh -c', args: 'cat', ttyEnabled: true,
                     envVars: [
                             [key: 'MAVEN_OPTS', value: '-Duser.home=/root/']]]],
            volumes: [persistentVolumeClaim(claimName: 'maven-repo', mountPath: '/root/.m2/repository'),
                      hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')],
            envVars: [[key: 'DOCKER_HOST', value: 'unix:/var/run/docker.sock'], [key: 'DOCKER_CONFIG', value: '/home/jenkins/.docker/']]
    ) {

        body(

        )
    }
}

#!/usr/bin/groovy
def call(Map parameters = [:], body) {

    def label = parameters.get('label', 'maven')
    def mavenImage = parameters.get('mavenImage', 'maven')
    def nameImage = parameters.get('nameImage','maven')
    podTemplate(label: label, inheritFrom: 'base',
            containers: [
                    [name: "${nameImage}", image: "${mavenImage}", command: '/bin/sh -c', args: 'cat', ttyEnabled: true, workingDir: '/home/jenkins/',
                     envVars: [
                             [key: 'MAVEN_OPTS', value: '-Duser.home=/root/']]]],
            volumes: [hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')],
            envVars: [[key: 'DOCKER_HOST', value: 'unix:/var/run/docker.sock'], [key: 'DOCKER_CONFIG', value: '/home/jenkins/.docker/']]) 
    {
        body()
    }
}

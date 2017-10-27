#!/usr/bin/groovy
def call(Map parameters = [:], body) {
    
    def label = parameters.get('label', 'maven')
    def nameImage = parameters.get('nameImage','maven')
    def inheritFrom = parameters.get('inheritFrom','base')
    def mavenImage = parameters.get('mavenImage', 'maven')

    podTemplate(label: label, inheritFrom: inheritFrom,
            containers: [containerTemplate(name: nameImage, image: mavenImage, command: '/bin/sh -c', args: 'cat', ttyEnabled: true, workingDir: '/home/jenkins/',
                     envVars: [
                             [key: 'MAVEN_OPTS', value: '-Duser.home=/root/']])],
            volumes: [hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')],
            envVars: [[key: 'DOCKER_HOST', value: 'unix:/var/run/docker.sock']]) 
    {
        body()
    }
}

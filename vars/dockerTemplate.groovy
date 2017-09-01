#!/usr/bin/groovy
def call(Map parameters = [:], body) {

    def label = parameters.get('label', 'docker')
    def dockerImage = parameters.get('dockerImage', 'docker:1.11')

    podTemplate(label: label, inheritFrom: 'base',
            containers: [
                     [name: 'docker', image: "${dockerImage}", command: '/bin/sh -c', args: 'cat', ttyEnabled: true,  workingDir: '/home/jenkins/',
                     envVars: [[key: 'DOCKER_CONFIG', value: '/home/jenkins/.docker/'],[key: 'DOCKER_API_VERSION', value: '1.23']]]],
            volumes: [hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')],
            envVars: [[key: 'DOCKER_HOST', value: 'unix:/var/run/docker.sock'], [key: 'DOCKER_CONFIG', value: '/home/jenkins/.docker/'],[key: 'DOCKER_API_VERSION', value: '1.23']]) 
    {
        body()
    }

}

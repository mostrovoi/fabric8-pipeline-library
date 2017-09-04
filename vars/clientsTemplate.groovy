#!/usr/bin/groovy
def call(Map parameters = [:], body) {

    def label = parameters.get('label', "clients")
    def clientsImage = parameters.get('clientsImage', 'fabric8/builder-clients:0.11')

    podTemplate(label: label, inheritFrom: "base",
            containers: [
                            [name: 'clients', image: "${clientsImage}", command: '/bin/sh -c', args: 'cat', privileged: true,  workingDir: '/home/jenkins/', ttyEnabled: true, workingDir: '/home/jenkins/']
                        ],
            volumes: [         
                    hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')],
            envVars: [[key: 'DOCKER_HOST', value: 'unix:/var/run/docker.sock']]) {
        body()
    }
}
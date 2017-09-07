#!/usr/bin/groovy
def call(Map parameters = [:], body) {

    def label = parameters.get('label', "performance")
    def image = parameters.get('performanceImage', 'cirit/jmeter')

    podTemplate(label: label, inheritFrom: "base",
            containers: [
                            [name: 'performance', image: "${image}", command: '/bin/sh -c', args: 'cat', privileged: true,  workingDir: '/home/jenkins/', ttyEnabled: true, workingDir: '/home/jenkins/']
                        ],
            volumes: [         
                    hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')],
            envVars: [[key: 'DOCKER_HOST', value: 'unix:/var/run/docker.sock']]) {
        body()
    }
}
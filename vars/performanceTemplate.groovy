#!/usr/bin/groovy
def call(Map parameters = [:], body) {

    def label = parameters.get('label', "performance")
    def nameImage = parameters.get('nameImage','performance')
    def inheritFrom = parameters.get('inheritFrom','base')
    def image = parameters.get('performanceImage', 'blazemeter/taurus')
    
    podTemplate(label: label, inheritFrom: inheritFrom,
            containers: [containerTemplate(name: nameImage, image: image, command: '/bin/sh -c', args: 'cat', privileged: true, ttyEnabled: true, workingDir: '/home/jenkins/')
                        ],
            volumes: [         
                    hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')],
            envVars: [[key: 'DOCKER_HOST', value: 'unix:/var/run/docker.sock']]) {
        body()
    }
}
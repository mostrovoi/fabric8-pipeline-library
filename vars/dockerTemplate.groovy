#!/usr/bin/groovy
def call(Map parameters = [:], body) {
    
    def label = parameters.get('label', 'docker')
    def nameImage = parameters.get('nameImage','docker')
    def inheritFrom = parameters.get('inheritFrom','base')
    def dockerImage = parameters.get('dockerImage', 'docker:1.11')

    podTemplate(label: label, serviceAccount: 'jenkins', inheritFrom: inheritFrom,
            containers: [containerTemplate(name: nameImage, image: dockerImage, command: '/bin/sh -c', args: 'cat', ttyEnabled: true,  workingDir: '/home/jenkins/')
                        ],
            volumes: [hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')],
            envVars: [[key: 'DOCKER_HOST', value: 'unix:/var/run/docker.sock']]) 
    {
        body()
    }

}

#!/usr/bin/groovy
def call(Map parameters = [:], body) {

    def label = parameters.get('label', "clients")
    def clientsImage = parameters.get('clientsImage', 'fabric8/builder-clients:v703b6d9')

    podTemplate(cloud: cloud, label: label, serviceAccount: 'jenkins', inheritFrom: "base",
            containers: [
                            [name: 'clients', image: "${clientsImage}", command: '/bin/sh -c', args: 'cat', privileged: true,  workingDir: '/home/jenkins/', ttyEnabled: true, workingDir: '/home/jenkins/', envVars: [[key: 'DOCKER_CONFIG', value: '/home/jenkins/.docker/']]]
                        ],
            volumes: [         
                    hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')],
            envVars: [[key: 'DOCKER_HOST', value: 'unix:/var/run/docker.sock'], [key: 'DOCKER_CONFIG', value: '/home/jenkins/.docker/']]) {
        body()
    }
}

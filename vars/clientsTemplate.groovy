#!/usr/bin/groovy
//def call(Map parameters = [:], body) {
def call() {
    //def label = parameters.get('label', "clients")
    //def clientsImage = parameters.get('clientsImage', 'fabric8/builder-clients:v703b6d9')

    podTemplate(label: "clients", inheritFrom: "base",
            containers: [containerTemplate(name: 'clients', image: 'fabric8/builder-clients:v703b6d9', command: '/bin/sh -c', args: 'cat', privileged: true, ttyEnabled: true, workingDir: '/home/jenkins/', envVars: [[key: 'DOCKER_CONFIG', value: '/home/jenkins/.docker/']])
                        ],
            volumes: [         
                    hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')],
            envVars: [[key: 'DOCKER_HOST', value: 'unix:/var/run/docker.sock'], [key: 'DOCKER_CONFIG', value: '/home/jenkins/.docker/']]) {
        //body()
    }
}

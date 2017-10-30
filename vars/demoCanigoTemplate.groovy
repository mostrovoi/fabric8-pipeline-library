#!/usr/bin/groovy
def call(Map parameters = [:], body) {

    def label = parameters.get('label', 'democanigopod')
    def nameDockerContainer = parameters.get('nameDockerContainer','docker')
    def nameClientsContainer = parameters.get('nameClientsContainer','clients')
    def namePerformanceContainer = parameters.get('namePerformanceContainer','performance')
    def nameMavenContainer = parameters.get('nameMavenContainer', 'maven')

    def dockerImage = parameters.get('dockerImage', 'docker:1.11')
    def clientsImage = parameters.get('clientsImage', 'fabric8/builder-clients:0.14')
    def performanceImage = parameters.get('performanceImage', 'blazemeter/taurus')
    def mavenImage = parameters.get('mavenImage', 'maven')
	
podTemplate(label: label, containers: [
              containerTemplate(name: nameMavenContainer, image: mavenImage, command: '/bin/sh -c', args: 'cat', privileged: true, ttyEnabled: true, workingDir: '/home/jenkins/'),
              envVars: [[key: 'MAVEN_OPTS', value: '-Duser.home=/root/ -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn']],
                 
              containerTemplate(name: nameClientsContainer, image: clientsImage, command: '/bin/sh -c', args: 'cat', privileged: true, ttyEnabled: true, workingDir: '/home/jenkins/'),
 			  containerTemplate(name: nameDockerContainer, image: dockerImage, command: '/bin/sh -c', args: 'cat', privileged: true, ttyEnabled: true, workingDir: '/home/jenkins/'),
              containerTemplate(name: namePerformanceContainer, image: performanceImage, command: '/bin/sh -c', args: 'cat', privileged: true, ttyEnabled: true, workingDir: '/home/jenkins/')
      		],
            volumes: [hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')
                      persistenVolumeClaim(claimName: 'maven-repo',mountPath: '/root/.m2')],
            envVars: [[key: 'DOCKER_HOST', value: 'unix:/var/run/docker.sock']])         
    {
        body()
    }
}



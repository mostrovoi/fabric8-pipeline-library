#!/usr/bin/groovy

def call() {

    def mavenImage =  'maven'
  
    podTemplate(label: label,
            containers: [
                     [name: 'maven', image: "${mavenImage}", command: '/bin/sh -c', args: 'cat', ttyEnabled: true,
                     envVars: [
                             [key: 'MAVEN_OPTS', value: '-Duser.home=/root/']]]],
            volumes: [secretVolume(secretName: 'jenkins-maven-settings', mountPath: '/root/.m2'),
                      persistentVolumeClaim(claimName: 'maven-local-repo', mountPath: '/root/.m2nrepo'),
                      secretVolume(secretName: 'jenkins-hub-api-token', mountPath: '/home/jenkins/.apitoken'),
                      secretVolume(secretName: 'jenkins-ssh-config', mountPath: '/root/.ssh'),
                      secretVolume(secretName: 'jenkins-git-ssh', mountPath: '/root/.ssh-git'),
                      hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')],
            envVars: [[key: 'DOCKER_HOST', value: 'unix:/var/run/docker.sock'], [key: 'DOCKER_CONFIG', value: '/home/jenkins/.docker/']]
    ) {
        body()
      }
}

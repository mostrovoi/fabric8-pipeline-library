#!/usr/bin/groovy
def call(Map parameters = [:], body) {

    def label = parameters.get('label', 'maven')

    def mavenImage = parameters.get('mavenImage', 'maven')

    // 0.13 introduces a breaking change when defining pod env vars so check version before creating build pod
   
    echo "building using the docker socket"

    podTemplate(label: label, inheritFrom: 'base',
            containers: [
                    [name: 'maven', image: "${mavenImage}", command: '/bin/sh -c', args: 'cat', ttyEnabled: true]]]
    ) {

        body(

        )
    }
}

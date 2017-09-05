#!/usr/bin/groovy
def call(body) {
  // evaluate the body block, and collect configuration into the object
  def config = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = config
  body()

  def projectName = config.stagedProject[0]
  def releaseVersion = config.stagedProject[1]

  def rc = readFile config.resourceLocation
  
  kubernetesApply(file: rc, environment: config.environment, registry: config.registry)
}
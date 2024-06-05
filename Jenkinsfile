#!/usr/bin/env groovy
if (BRANCH_NAME == "master" || BRANCH_NAME.startsWith("build/")) {
  grailsPlugin { useJava8 = true }
  return
}

def config = [
    useJava8 : true,
    testRunEnv : "dev"
]

//all other branches, e.g. PR branches
node("slave") {
  stage("Checkout") {
    checkout(scm)
    stash(excludes: 'target/', includes: '**', name: 'source')
  }

  stage("Delete untracked files") {
    sh('git clean -f -d')
  }
  stage('Grails tests') {
    echo "running tests"
    def name = pluginUtils.getPluginName()
    grailsTestsPassed = testUtils.grailsTest(name, config)
    if (!grailsTestsPassed) 
      error("Grails tests failed")
  }
}


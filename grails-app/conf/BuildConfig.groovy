grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.7
grails.project.source.level = 1.7

import grails.util.Environment;
String mvnRepo = System.getProperty("grails.project.repos.ipsyLocal.url") ?: "file:///mvn-repo/ipsy3_0/${Environment.current.name}"
grails.project.repos.ipsyLocal.url = mvnRepo
String nexusRepo = System.getProperty("grails.project.repos.ipsyNexus.url") ?: "http://nexus.staging.ipsy.com:8081/nexus/content/groups/public/"
grails.project.repos.ipsyNexus.url = nexusRepo

grails.project.fork = [
  // configure settings for compilation JVM, note that if you alter the Groovy version forked compilation is required
  //  compile: [maxMemory: 256, minMemory: 64, debug: false, maxPerm: 256, daemon:true],

  // configure settings for the test-app JVM, uses the daemon by default
  test: false,
  // configure settings for the run-app JVM
  run: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
  // configure settings for the run-war JVM
  war: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
  // configure settings for the Console UI JVM
  console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]
]
grails.project.dependency.resolver = "maven" // or ivy

grails.project.dependency.resolution = {

	inherits 'global'
	log 'warn'

	repositories {
		mavenLocal()
    mavenRepo(mvnRepo) {
      updatePolicy "always"
    }
    mavenRepo(nexusRepo) {
      updatePolicy "always"
    }
    grailsPlugins()
    grailsHome()
    mavenRepo "https://grails.jfrog.io/artifactory/plugins"
    mavenRepo "https://repo1.maven.org/maven2/"
    // to access Spring Security RC builds
    mavenRepo "http://repo.spring.io/milestone/"
    // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
    //mavenRepo "http://repository.codehaus.org"
    //mavenRepo "http://download.java.net/maven/2/"
    mavenRepo "http://repository.jboss.com/maven2/"
	}

	dependencies {
		String tomcatVersion = '8.0.30'

		compile "org.apache.tomcat.embed:tomcat-embed-core:$tomcatVersion"
		['el', 'jasper', 'logging-log4j', 'logging-juli', 'websocket'].each {
			runtime "org.apache.tomcat.embed:tomcat-embed-$it:$tomcatVersion"
		}

		provided 'javax.servlet:javax.servlet-api:3.1.0'
	}

	plugins {
		build ':release:3.1.2', ':rest-client-builder:2.1.1', {
			export = false
		}
	}
}

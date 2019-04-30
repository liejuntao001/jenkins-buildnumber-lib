#!/usr/bin/env groovy

@Library("buildnumber") _

pipeline {
  agent none
  stages {
    stage('Get build number') {
      steps {
        script {
          withCredentials([string(credentialsId: 'build_number_server', variable: 'server'),
                           string(credentialsId: 'test_job_uuid', variable: 'uuid')]) {
            def build_number = buildnumber.get(server, uuid)
            echo "$build_number"
            currentBuild.displayName = "Demo-${build_number}"
            assert build_number != 0
          }
        }
      }
    }
  }
}

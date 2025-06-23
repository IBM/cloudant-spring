#!groovy

/*
 * Copyright © 2017, 2023 IBM Corp. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

pipeline {
  agent {
    kubernetes {
      yaml kubePodTemplate(name: 'full_jnlp.yaml')
    }
  }
  environment {
    ARTIFACTORY_CREDS = credentials('artifactory')
    ARTIFACTORY_URL = "${Artifactory.server('taas-artifactory').getUrl()}"
  }
  stages {
    stage('Build') {
      steps {
        // build and assemble the source and doc
        sh 'gradle clean assemble'
      }
    }
    
    stage('QA') {
      steps {
        sh 'gradle spotbugsMain'
        sh 'gradle test'
      }
      post {
        always {
          recordIssues enabledForFailure: true, tool: spotBugs(pattern: '**/build/reports/spotbugs/*.xml')
          junit (
            testResults: '**/build/test-results/test/*.xml'
          )
        }
      }
    }
    
    // Publish the primary branch
    stage('Publish') {
      steps {
        script {
          if (env.BRANCH_IS_PRIMARY) {
            // read the version name and determine if it is a release build
            version = readFile('VERSION').trim()
            isReleaseVersion = !version.toUpperCase(Locale.ENGLISH).contains("SNAPSHOT")

            withCredentials([usernamePassword(credentialsId: 'central-portal', passwordVariable: 'CP_PASSWORD', usernameVariable: 'CP_USER'), 
                            certificate(credentialsId: 'cldtsdks-signing-cert', keystoreVariable: 'CODE_SIGNING_PFX_FILE', passwordVariable: 'CODE_SIGNING_P12_PASSWORD')]) {
              sh '''
                #!/bin/bash -e
                # Configure the signing client
                setup-garasign-client
                # Get the key ID
                SIGNING_KEYID=$(grep 'Key ID' $HOME/.gnupggrs/keysinfo.txt | awk 'NR==1{print $5}')
                # Do a gradle publish that signs and uploads using the OSSRH creds
                # upload destination logic is in build.gradle
                gradle -Psigning.gnupg.keyName=$SIGNING_KEYID -Psigning.gnupg.executable=/opt/Garantir/bin/gpg -Psigning.gnupg.homeDir=$HOME/.gnupggrs publish
              '''
            }
            // if it is a release build then do extra work
            if (isReleaseVersion) {
              // Upload from OSSRH staging API to central portal and publish
              httpRequest(
                authentication: 'central-portal',
                httpMode: 'POST',
                responseHandle: 'NONE',
                url: 'https://ossrh-staging-api.central.sonatype.com/manual/upload/defaultRepository/com.ibm.cloud?publishing_type=automatic',
                validResponseCodes: '200',
                wrapAsMultipart: false
              )

              // Create a git tag and a draft release
              gitTagAndPublish {
                isDraft=true
                releaseApiUrl='https://api.github.com/repos/IBM/cloudant-spring/releases'
              }
            }
          }
        }
      }
      post {
        always {
          sh "git config --unset credential.username || true"
          sh "git config --unset credential.helper || true"
        }
      }
    }
  }  
}

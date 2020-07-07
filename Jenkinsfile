#!groovy
import com.liaison.jenkins.common.e2etest.*
import com.liaison.jenkins.common.kubernetes.*
import com.liaison.jenkins.common.releasenote.ReleaseNote
import com.liaison.jenkins.common.servicenow.ServiceNow
import com.liaison.jenkins.common.slack.*
import com.liaison.jenkins.common.sonarqube.QualityGate
import com.liaison.jenkins.common.testreport.TestResultsUploader
@Library('visibilityLibs')
import com.liaison.jenkins.visibility.Utilities

def deployments = new Deployments()
def k8sDocker = new Docker()
def kubectl = new Kubectl()
def serviceNow = new ServiceNow()
def slack = new Slack()

def utils = new Utilities()
def uploadUtil = new TestResultsUploader()
def qg = new QualityGate()
def e2etest = new E2eTestDeployer()
def testSessions = new TestSessions()
def release = new ReleaseNote()

def deployment
def otbpDeployment
def dockerImageName = "bpdockerhub/test-automation-lens-cmd-auto"
def otbpDockerImageName = "bpdockerhub/test-automation-lens-cmd-auto"


timestamps {
    node {
        checkout scm
        env.PACKAGE_VERSION = utils.runSh('grep -oP "\\[\\K[0-9.]*" CHANGELOG.md | head -1')
        echo "Current version is ${env.PACKAGE_VERSION}"
        currentBuild.displayName = "${env.PACKAGE_VERSION}-${env.BUILD_NUMBER}"

        env.JAVA_HOME = "${tool 'jdk11'}"
        env.PATH = "${env.JAVA_HOME}/bin:${env.PATH}"
        sh 'java -version'

        env.RELEASE_NOTES = utils.runSh('cat CHANGELOG.md | awk "NR==2" RS="\\n\\n"')
        echo "Release notes: ${env.RELEASE_NOTES}"

        env.GIT_COMMIT = utils.runSh('git rev-parse HEAD')
        env.GIT_URL = utils.runSh("git config remote.origin.url | sed -e 's/\\(.git\\)*\$//g' ")
        env.REPO_NAME = "test-automation-lens-cmd-auto"
    }

    node('at4d-c3-agent') {
        stage('Checkout') {
            def scmVars = checkout scm
            env.VERSION = utils.runSh("grep '## \\[[0-9]' CHANGELOG.md | head -n 1 | cut -d '[' -f2 | cut -d ']' -f1").trim()
            env.GIT_COMMIT = scmVars.GIT_COMMIT
            env.GIT_URL = scmVars.GIT_URL
            env.REPO_NAME = utils.runSh("basename -s .git ${env.GIT_URL}")
            currentBuild.displayName = "${env.VERSION}-${env.BUILD_NUMBER}"
        }

        stage('Copy changes') {
            stash name: 'workspace', includes: '**'
        }

        stage('Build Docker image (OTBP)') {
            unstash name: 'workspace'
            otbpDeployment = deployments.create(
                    name: 'test-automation-lens-cmd-auto',
                    version: env.VERSION,
                    description: 'Deployment of test-automation-lens-cmd-auto',
                    dockerImageName: otbpDockerImageName,
                    dockerImageTag: env.VERSION,
                    yamlFile: 'K8sfile.yaml',   // optional, defaults to 'K8sfile.yaml'
                    gitUrl: env.GIT_URL,        // optional, defaults to env.GIT_URL
                    gitCommit: env.GIT_COMMIT,  // optional, defaults to env.GIT_COMMIT
                    gitRef: env.VERSION,        // optional, defaults to env.GIT_COMMIT
                    kubectl: kubectl
            )

            k8sDocker.build(imageName: otbpDockerImageName)
            milestone label: 'OTBP Docker image built', ordinal: 101

            if (utils.isMasterBuild()) {
                k8sDocker.push(imageName: dockerImageName, imageTag: env.PACKAGE_VERSION)
                k8sDocker.push(imageName: otbpDockerImageName, imageTag: env.VERSION, registry: Registry.BROOKPARK)
            }
        }
    }

    if (utils.isPRBuild()) {
        echo "This was a pull request, not deploying to DEV!"
    }

    if (utils.isMasterBuild()) {
        stage('Run Docker file') {
            try {
                deployments.deploy(
                    deployment: deployment,
                    kubectl: kubectl,
                    namespace: Namespace.DEVELOPMENT,
                    rollingUpdate: false,
                    clusters: [Cluster.OTBP]
                )
            } catch (err) {
                currentBuild.result = "FAILURE"
                println err
            }
        }
    }
}
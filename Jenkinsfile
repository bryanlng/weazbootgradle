properties([[$class: 'BuildDiscarderProperty',
                strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

node {
    stage 'Clean Workspace'
    deleteDir()

    stage 'Checkout'
    checkout scm

    stage 'Build'

    if(isUnix()) {
        sh './gradlew clean check --console=plain --no-daemon --info --stacktrace'
    } else{
        bat './gradle clean check --console=plain --no-daemon --info --stacktrace'
    }
}
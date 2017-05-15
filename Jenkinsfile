properties([[$class: 'BuildDiscarderProperty', strategy: [$class: 'LogRotator', numToKeepStr: '10']]])

node {
    ansiColor('xterm') {
        stage 'Clean Workspace'
        deleteDir()

        stage 'Checkout'
        checkout scm

        stage 'Build'

        if (isUnix()) {
            wrap([$class: 'Xvfb']) {
                env
                try {
                    sh './gradlew build --console=plain --no-daemon --info --stacktrace'
                } catch (Exception e) {
                    throw e
                } finally {
                    step([$class: 'JUnitResultArchiver', testResults: 'build/test-results/**/*.xml', allowEmptyResults: true])
                }
            }
        } else {
            try {
                bat 'gradlew build --console=plain --no-daemon --info --stacktrace'
            } catch (Exception e) {
                throw e
            } finally {
                step([$class: 'JUnitResultArchiver', testResults: 'build/test-results/**/*.xml', allowEmptyResults: true])
            }
        }

        stage 'Archive'
        step([$class: 'JacocoPublisher'])
        step([$class: 'ArtifactArchiver', artifacts: '**/build/libs/*.jar*', fingerprint: true])
    }
}

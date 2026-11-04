def call() {
    call([:])
}

def call(Map config) {
    def image = config.image ?: 'zricethezav/gitleaks:v8.30.1'

    stage('Gitleaks Secret Scan') {
        echo 'Starting centralized Gitleaks scan'

        if (isUnix()) {
            sh """
                docker run --rm \
                  -v "${env.WORKSPACE}:/repo" \
                  -w /repo \
                  ${image} \
                  git --redact --no-banner .
            """
        } else {
            bat """
                docker run --rm ^
                  -v "%WORKSPACE%:/repo" ^
                  -w /repo ^
                  ${image} ^
                  git --redact --no-banner .
            """
        }

        echo 'Gitleaks scan completed'
    }
}
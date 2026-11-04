def call() {
    call([:])
}

def call(Map config) {
    def image = config.image ?: 'zricethezav/gitleaks:v8.24.2'

    echo 'Starting centralized Gitleaks secret scan'

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
            docker run --rm -v "%WORKSPACE%:/repo" -w /repo ${image} git --redact --no-banner .
        """
    }

    echo 'Gitleaks scan completed successfully'
}

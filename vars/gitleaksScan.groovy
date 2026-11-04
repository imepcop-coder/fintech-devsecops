def call(Map config = [:]) {
    def image = config.image ?: 'zricethezav/gitleaks:v8.30.1'

    echo 'Starting centralized Gitleaks scan'

    if (isUnix()) {
        sh """
            docker run --rm \
              -v "${env.WORKSPACE}:/repo" \
              -w /repo \
              ${image} \
              git --pre-commit --staged --redact .
        """
    } else {
        bat """
            docker run --rm -v "%WORKSPACE%:/repo" -w /repo ${image} git --pre-commit --staged --redact .
        """
    }

    echo 'Gitleaks scan completed'
}
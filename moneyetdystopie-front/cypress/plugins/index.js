const cucumber = require('cypress-cucumber-preprocessor').default

module.exports = (on, config) => {
    on('file:preprocessor', cucumber())
    require('@cypress/code-coverage/task')(on, config)
    return config
}

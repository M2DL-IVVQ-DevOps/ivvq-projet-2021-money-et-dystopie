module.exports = {
  presets: [
    '@vue/cli-plugin-babel/preset'
  ],
  plugins: [
    ['babel-plugin-istanbul', {
      "extension": ['.vue'],
      "exclude": ['.js']
    }]
  ]
}

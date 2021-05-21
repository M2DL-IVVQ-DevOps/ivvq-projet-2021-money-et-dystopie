import Vue from 'vue'
import App from './App.vue'
import { MdButton, MdContent } from 'vue-material/dist/components'
import VueMaterial from 'vue-material'
import 'vue-material/dist/vue-material.min.css'
import 'vue-material/dist/theme/default.css'

Vue.config.productionTip = false
Vue.use(VueMaterial);

new Vue({
  render: h => h(App),
  component: {MdButton,MdContent
  }
}).$mount('#app')


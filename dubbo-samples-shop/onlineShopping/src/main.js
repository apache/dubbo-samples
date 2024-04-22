import '@/styles/common.scss'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
// 引入全局组件插件

import App from './App.vue'
import router from './router'
import {lazyPlugin} from "@/directives/index.js";


const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(lazyPlugin)
app.mount('#app')


import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'

import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import axios from "axios";
import router from "./router";
//import {createRouter} from "vue-router";


const app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

app.config.globalProperties.$http=axios
app.config.globalProperties.$httpUrl='http://localhost:8080'
app.use(ElementPlus)
app.use(router)
app.mount('#app')

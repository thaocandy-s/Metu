import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import route from './routes/route.ts';
import Antd from "ant-design-vue";
import * as AllIcons from "oh-vue-icons/icons";
import { OhVueIcon, addIcons } from "oh-vue-icons";
import "ant-design-vue/dist/reset.css";
import Vue3Toastify, { type ToastContainerOptions } from "vue3-toastify";
import "vue3-toastify/dist/index.css";
import { VueQueryPlugin } from "@tanstack/vue-query";
import { createPinia } from "pinia";
import "../index.css";
import 'primeicons/primeicons.css'

const AllIcon = Object.values({ ...AllIcons });
addIcons(...AllIcon);


const app = createApp(App);

app.use(route);
app.use(Antd);
app.use(Vue3Toastify, {
    autoClose: 3000,
  } as ToastContainerOptions);
app.use(VueQueryPlugin);
app.use(createPinia());
app.component("v-icon", OhVueIcon);

app.mount('#app');


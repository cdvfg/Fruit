import Vue from 'vue'
import VueRouter from 'vue-router'
import Tabel from "../views/Tabel"
import Edit from "../views/Edit";
import Add from '../views/Add'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: Tabel
  },
  {
    path: '/table',
    component: Tabel
  },
  {
    path: '/edit',
    component: Edit
  },
  {
    path: '/add',
    component: Add
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router

import { createRouter, createWebHistory } from 'vue-router';

const routes=[
    {
        path:'/',
        name:'login',
        component:()=>import('../components/LoginView.vue')

    },
    {
        path:'/Index',
        name:'index',
        component:()=>import('../components/IndexPage.vue')

    }
]
const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router
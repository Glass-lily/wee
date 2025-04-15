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
        component:()=>import('../components/IndexPage.vue'),
        children:[
            {
                path:'/Home',
                name:'home',
                meta:{
                    title:'首页'

                },
                component:()=>import('../components/HomePage.vue')
            },
            {
                path:'/Admin',
                name:'admin',
                meta:{
                    title:'管理员管理'

                },
                component:()=>import('@/components/admin/AdminManage.vue')
            },
            {
                path:'/User',
                name:'user',
                meta:{
                    title:'用户管理'

                },
                component:()=>import('@/components/user/UserManage.vue')
            },
            {
                path:'/Storage',
                name:'storage',
                meta:{
                    title:'知识仓库管理'

                },
                component:()=>import('@/components/storage/StorageManage.vue')
            },
            {
                path:'/Goodstype',
                name:'goodstype',
                meta:{
                    title:'知识分类管理'

                },
                component:()=>import('@/components/goodstype/GoodstypeManage.vue')
            },
            {
                path:'/Goods',
                name:'goods',
                meta:{
                    title:'知识'

                },
                component:()=>import('@/components/goods/GoodsManage.vue')
            },
            {
                path:'/Record',
                name:'record',
                meta:{
                    title:'知识分类管理'

                },
                component:()=>import('@/components/goodstype/GoodstypeManage.vue')
            },
            {
                path:'/PersonCenter',
                name:'personCenter',
                meta:{
                    title:'个人中心'

                },
                component:()=>import('@/components/personcenter/PersonCenterPage.vue')
            },





        ]

    }
]
const router = createRouter({
    history: createWebHistory(),
    routes
});
// 保存原始的 push 方法
const originalPush = router.push;

// 重写 push 方法
router.push = function push(location) {
    return originalPush.call(this, location).catch((err) => {
        // 如果是重复路由跳转的错误，则忽略该错误
        if (err.name === 'NavigationDuplicated') {
            return err;
        }
        // 其他错误则继续抛出
        throw err;
    });
};

export default router
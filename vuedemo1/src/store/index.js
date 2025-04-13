import {createStore} from 'vuex';
import router from "@/router";

// 封装 commit 的辅助函数
export function commit(type, payload) {
    return store.commit(type, payload);
}

// 创建路由实例
function addNewRoute(menuList) {
    console.log(menuList)
    let routes = router.options.routes
    console.log(routes)
    routes.forEach(routeItem => {
        if (routeItem.path == "/Index") {
            menuList.forEach(menu => {
                let childRoute = {
                    path: '/'+menu.menuclick,
                    name: menu.menuname,
                    meta: {
                        title: menu.menuname
                    },
                    component: () => import('../components/'+menu.menucomponent)
                }
                    routeItem.children.push(childRoute)
            })
        }
    })
router.addRoute(routes)

}

//const routerInstance = useRouter();
const store = createStore({
    state: {
        menu: []
    },
    mutations: {
        setMenu(state, menuList) {
            state.menu = menuList;
            addNewRoute(menuList)
        }
    },
    getters: {
        getMenu(state) {
            return state.menu;
        }
    }
});

export default store;

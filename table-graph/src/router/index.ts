import {createWebHistory, createRouter, RouteRecordRaw} from 'vue-router'

const routes: RouteRecordRaw[] = [
    {
        path: "",
        name: "list",
        component: () => import("../components/tableMenu/TableGroupMenu.vue")
    },
    {
        path: "",
        name: "graph",
        component: () => import("../components/tableMenu/TableGroupMenu.vue")
    },
]

export const router = createRouter({
    history: createWebHistory(),
    routes,
    scrollBehavior(to, from, savedPosition) {
        if (savedPosition) {
            return savedPosition
        } else {
            return { top: 0 }
        }
    },
});
import {createRouter, createWebHistory, RouteRecordRaw} from 'vue-router';
import {ROUTES_CONSTANTS} from "@/constants/path.ts";
import {ROLES} from "@/constants/role.ts";
import {useAuthStore} from "@/stores/auth.ts";

const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        name: 'home',
        component: () => import('@/views/home/Home.vue'),
        alias: "/home",
        redirect: '/instroduce',
        children: [
            {
                path: '/instroduce',
                name: 'instroduce',
                component: () => import('@/views/home/Introduce.vue'),
            },
            {
                path: '/instruction',
                name: 'instruction',
                component: () => import('@/views/home/Instruction.vue'),
            },
        ],
    },
    {
        path: ROUTES_CONSTANTS.REDIRECT.path,
        name: ROUTES_CONSTANTS.REDIRECT.name,
        component: () => import("@/routes/guard/Redirect.vue"),
    },
    {
        path: ROUTES_CONSTANTS.NOT_FOUND.path,
        name: ROUTES_CONSTANTS.NOT_FOUND.name,
        component: () => import("@/views/exception/404/NotFound.vue"),
    },
    {
        path: ROUTES_CONSTANTS.ADMIN.path,
        name: ROUTES_CONSTANTS.ADMIN.name,
        component: () => import("@/views/admin/AdminSider.vue"),
        meta: { requiresAuth: true },
        redirect: ROUTES_CONSTANTS.ADMIN.children.BUSINESS_TYPE,
        children: [
            {
                path: ROUTES_CONSTANTS.ADMIN.children.BUSINESS.path,
                name: ROUTES_CONSTANTS.ADMIN.children.BUSINESS.name,
                component: () => import('@/views/admin/business/Business.vue'),
                meta: {
                    requiresRole: ROLES.ADMIN,
                    requiresAuth: true
                },
            },
            {
                path: ROUTES_CONSTANTS.ADMIN.children.BUSINESS_TYPE.path,
                name: ROUTES_CONSTANTS.ADMIN.children.BUSINESS_TYPE.name,
                component: () => import('@/views/admin/businesstype/BusinessType.vue'),
                meta: {
                    requiresRole: ROLES.ADMIN,
                    requiresAuth: true
                },
            },
            {
                path: ROUTES_CONSTANTS.ADMIN.children.PLAN_ORDER.path,
                name: ROUTES_CONSTANTS.ADMIN.children.PLAN_ORDER.name,
                component: () => import('@/views/admin/plan/PlanOrder.vue'),
                meta: {
                    requiresRole: ROLES.ADMIN,
                    requiresAuth: true
                },
            },
            {
                path: ROUTES_CONSTANTS.ADMIN.children.USER.path,
                name: ROUTES_CONSTANTS.ADMIN.children.USER.name,
                component: () => import('@/views/admin/user/User.vue'),
                meta: {
                    requiresRole: ROLES.ADMIN,
                    requiresAuth: true
                },
            },
            {
                path: ROUTES_CONSTANTS.ADMIN.children.WIDGET.path,
                name: ROUTES_CONSTANTS.ADMIN.children.WIDGET.name,
                component: () => import("@/views/admin/widget_admin/AdWidget.vue"),
                meta: {
                    requiresRole: ROLES.ADMIN,
                    requiresAuth: true
                },
            },
            {
                path: ROUTES_CONSTANTS.ADMIN.children.SHAPE.path,
                name: ROUTES_CONSTANTS.ADMIN.children.SHAPE.name, 
                component: () => import('@/views/admin/menu_style_config/Shape.vue'),
                meta: {
                    requiresRole: ROLES.ADMIN,
                    requiresAuth: true,
                },
            },
            {
                path: ROUTES_CONSTANTS.ADMIN.children.POSITION.path,
                name: ROUTES_CONSTANTS.ADMIN.children.POSITION.name, 
                component: () => import('@/views/admin/menu_style_config/Position.vue'),
                meta: {
                    requiresRole: ROLES.ADMIN,
                    requiresAuth: true,
                },
            },
        ],
    },
    {
        path: ROUTES_CONSTANTS.MANAGER.path,
        name: ROUTES_CONSTANTS.MANAGER.name,
        component: () => import("@/views/manager/ManagerSider.vue"),
        meta: { requiresAuth: true },
        redirect: ROUTES_CONSTANTS.MANAGER.children.DASHBOARD,
        children: [
            {
                path: ROUTES_CONSTANTS.MANAGER.children.DASHBOARD.path,
                name: ROUTES_CONSTANTS.MANAGER.children.DASHBOARD.name,
                component: () => import('@/views/employee/Dashboard.vue'),
                meta: {
                    requiresRole: ROLES.MANAGER,
                    requiresAuth: true
                },
            },
            {
                path: ROUTES_CONSTANTS.MANAGER.children.MENU.path,
                name: ROUTES_CONSTANTS.MANAGER.children.MENU.name,
                component: () => import('@/views/manager/menu/Menu.vue'),
                meta: {
                    requiresRole: ROLES.MANAGER,
                    requiresAuth: true
                },
            },
            {
                path: ROUTES_CONSTANTS.MANAGER.children.CREATE_MENU.path,
                name: ROUTES_CONSTANTS.MANAGER.children.CREATE_MENU.name,
                component: () => import('@/views/manager/menu/CreateMenu.vue'),
                meta: {
                    requiresRole: ROLES.MANAGER,
                    requiresAuth: true
                },
            },
            {
                path: ROUTES_CONSTANTS.MANAGER.children.WIDGET.path,
                name: ROUTES_CONSTANTS.MANAGER.children.WIDGET.name,
                component: () => import('@/views/manager/menu/CreateWidgetMenu.vue'),
                meta: {
                    requiresRole: ROLES.MANAGER,
                    requiresAuth: true
                },
            },
            {
                path: ROUTES_CONSTANTS.MANAGER.children.POPUP.path,
                name: ROUTES_CONSTANTS.MANAGER.children.POPUP.name,
                component: () => import('@/views/employee/Dashboard.vue'),
                meta: {
                    requiresRole: ROLES.MANAGER,
                    requiresAuth: true
                },
            },
        ],
    },
    {
        path: ROUTES_CONSTANTS.MANAGER.children.COMPUTER_SCREEN.path,
        name: ROUTES_CONSTANTS.MANAGER.children.COMPUTER_SCREEN.name,
        component: () => import("@/views/manager/menu/ComputerScreen.vue"),
    },
    {
        path: ROUTES_CONSTANTS.MANAGER.children.PHONE_SCREEN.path,
        name: ROUTES_CONSTANTS.MANAGER.children.PHONE_SCREEN.name,
        component: () => import("@/views/manager/menu/PhoneScreen.vue"),
    },
    {
        path: ROUTES_CONSTANTS.EMPLOYEE.path,
        name: ROUTES_CONSTANTS.EMPLOYEE.name,
        component: () => import("@/views/employee/EmployeeSider.vue"),
        meta: { requiresAuth: true },
        redirect: ROUTES_CONSTANTS.EMPLOYEE.children.SOMETHING,
        children: [
            {
                path: ROUTES_CONSTANTS.EMPLOYEE.children.SOMETHING.path,
                name: ROUTES_CONSTANTS.EMPLOYEE.children.SOMETHING.name,
                component: () => import('@/views/employee/Dashboard.vue'),
                meta: {
                    requiresRole: ROLES.MANAGER,
                    requiresAuth: true
                },
            },
        ],
    },
    {
        path: ROUTES_CONSTANTS.AUTHENTICATION.path,
        name: ROUTES_CONSTANTS.AUTHENTICATION.name,
        component: () => import("@/views/home/auth/Authentication.vue"),
        redirect: ROUTES_CONSTANTS.AUTHENTICATION.children.LOGIN,
        children: [
            {
                path: ROUTES_CONSTANTS.AUTHENTICATION.children.LOGIN.path,
                name: ROUTES_CONSTANTS.AUTHENTICATION.children.LOGIN.name,
                component: () => import('@/views/home/auth/Login.vue'),
            },
            {
                path: ROUTES_CONSTANTS.AUTHENTICATION.children.REGISTER.path,
                name: ROUTES_CONSTANTS.AUTHENTICATION.children.REGISTER.name,
                component: () => import('@/views/home/auth/Register.vue'),
            },
        ],
    },
];

const route = createRouter({
    history: createWebHistory(),
    routes,
});

route.beforeEach((to, from, next) => {
    const authStore = useAuthStore();
    const requiresAuth = to.matched.some((record) => record.meta.requiresAuth);
    const requiresRole = to.matched.some((record) => record.meta.requiresRole);
    const userRole = authStore?.user?.rolesCodes;

    if (userRole === null && requiresAuth) {
        next({ name: ROUTES_CONSTANTS.AUTHENTICATION.children.LOGIN.name });
    } else if (requiresAuth && !authStore.isAuthenticated) {
        next({ name: ROUTES_CONSTANTS.AUTHENTICATION.children.LOGIN.name });
    } else if (requiresRole && (!userRole || userRole !== to.meta.requiresRole)) {
        next({ name: ROUTES_CONSTANTS.AUTHENTICATION.children.LOGIN.name });
    } else {
        next();
    }

});

export default route;

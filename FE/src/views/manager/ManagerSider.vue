<template>

    <a-layout>
      <a-layout-sider theme="light" width="250" v-model:collapsed="collapsed" :trigger="null" collapsible>
        <div class="logo" style="width:100%; height: 70px;">LOGO</div>
        <a-menu v-model:selectedKeys="selectedKeys" theme="light" mode="inline">
  
          <template v-for="item in menuItems">
            <a-menu-item :key="item.key" v-if="!item.children" @click="handleMenuClick(item.key)">
              <component :is="item.icon"/>
              <span>{{ item.label }}</span>
            </a-menu-item>
  
            <a-sub-menu :key="`${item.key}`" v-else>
              <template #title>
                              <span>
                                  <component :is="item.icon"/>
                                  <span>{{ item.label }}</span>
                              </span>
              </template>
  
              <a-menu-item v-for="child in item.children" :key="child.key"
                           @click="handleMenuClick(child.key)">
                {{ child.label }}
              </a-menu-item>
            </a-sub-menu>
          </template>
  
        </a-menu>
      </a-layout-sider>
  
      <a-layout>
        <a-layout-header class="pl-3 mt-1" style="background-color: white">
          <div class="user-info flex items-center justify-between">
            <div class="cursor-pointer" @click="collapsed = !collapsed">
              <component
                  :is="collapsed ? MenuUnfoldOutlined : MenuFoldOutlined"
                  class="text-xl"
              />
            </div>
            <a-dropdown placement="bottomRight" arrow>
              <div class="flex items-center cursor-pointer">
                <a-avatar
                    v-if="userInfo?.pictureUrl"
                    :src="userInfo?.pictureUrl"
                    size="large"
                >
                  {{ userInfo?.fullName[0] }}
                </a-avatar>
                <span class="ml-2 truncate">
                  {{ userInfo?.fullName }}
                </span>
              </div>
              <template #overlay>
                <a-menu>
                  <a-menu-item key="logout" @click="handleLogout">
                    Đăng xuất
                  </a-menu-item>
                </a-menu>
              </template>
            </a-dropdown>
          </div>
        </a-layout-header>
        <a-layout-content class="mx-4">
          <div class="min-h-[calc(100vh-9.5rem)] bg-white">
            <router-view/>
          </div>
        </a-layout-content>
        <a-layout-footer class="text-center">
          FPL - UDPM ©2021 Created by BIT
        </a-layout-footer>
      </a-layout>
    </a-layout>
  
  </template>
  
  
  <script lang="ts" setup>
  import {computed, ref} from 'vue';
  import {useRouter} from 'vue-router';
  import {MenuFoldOutlined, MenuUnfoldOutlined, UserOutlined,} from '@ant-design/icons-vue';
  import {ROUTES_CONSTANTS} from "@/constants/path.ts";
  import {useAuthStore} from "@/stores/auth.ts";
  
  const auth = useAuthStore();
  
  const userInfo = computed(() => auth.user);
  const handleLogout = () => {
    auth.logout();
    router.push(ROUTES_CONSTANTS.AUTHENTICATION.children.LOGIN);
  };
  
  const collapsed = ref<boolean>(false);
  const selectedKeys = ref<string[]>(['1']);
  const router = useRouter();
  
  const baseMenuItems = [
  { icon: UserOutlined, label: 'Tổng quan', path: ROUTES_CONSTANTS.MANAGER.children.DASHBOARD },
  { icon: UserOutlined, label: 'Quản lý menu', path: ROUTES_CONSTANTS.MANAGER.children.MENU },
  {
    label: 'Thống kê',
    icon: UserOutlined,
    children: [
      { label: 'Chi tiết tương tác', path: '/auth/statistical-interaction' },
      { label: 'Theo menu', path: '/auth/statistical-menu' },
      { label: 'Theo nút', path: '/auth/statistical-button' },
      { label: 'Campagn', path: '/auth/statistical-button' },
    ],
  },
  {
    label: 'Quản lý popup',
    icon: UserOutlined,
    path: ROUTES_CONSTANTS.MANAGER.children.POPUP,
  },
];

// Hàm để tự động thêm key cho từng mục
const generateMenuItems = (items) => {
  let counter = 1;
  const addKeys = (item) => {
    const newItem = { ...item, key: String(counter++) };
    if (newItem.children) {
      newItem.children = newItem.children.map(addKeys);
    }
    return newItem;
  };
  return items.map(addKeys);
};

const menuItems = ref(generateMenuItems(baseMenuItems));
  
  const handleMenuClick = (key: string) => {
    selectedKeys.value = [key];
    const item = menuItems.value.find((menuItem) =>
        menuItem.key === key || menuItem.children?.some((child) => child.key === key)
    );
    if (item) {
      const target = item.children?.find((child) => child.key === key) || item;
      if (target?.path) {
        router.push(target.path);
      }
    }
  };
  
  </script>
  
  <script lang="ts">
  export default {
    name: "AdminPage",
  }
  </script>
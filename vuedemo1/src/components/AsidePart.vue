<script>
import { HomeFilled, Menu, Message, Setting } from "@element-plus/icons-vue";
import store from "@/store";

export default {
  name: "AsidePart",
  components: { HomeFilled, Menu, Message, Setting },
  computed: {
    menu() {
      return store.state.menu;
    },
  },
  props: {
    isCollapse: Boolean,
  },
};
</script>

<template>
  <div class="aside-container">
    <el-menu
        default-active="/Home"
        :collapse="isCollapse"
        router
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#ffffff"
    >
      <el-menu-item index="/Home">
        <el-icon>
          <HomeFilled />
        </el-icon>
        <template #title>
          <span>首页</span>
        </template>
      </el-menu-item>
      <el-menu-item
          :index="`/${item.menuclick}`"
          v-for="item in menu"
          :key="item.id"
      >
        <el-icon>
          <component :is="item.menuicon" />
        </el-icon>
        <template #title>
          <span>{{ item.menuname }}</span>
        </template>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<style scoped>
.aside-container {
  height: 100vh;
  background: #304156;
}

.el-menu--collapse span {
  display: none;
}

@media (max-width: 768px) {
  .aside-container {
    border-right: none;
  }

}
</style>
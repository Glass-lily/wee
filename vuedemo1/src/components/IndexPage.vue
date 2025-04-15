<script>
import AsidePart from "@/components/AsidePart.vue";
import HeaderPart from "@/components/HeaderPart.vue";

export default {
  name: "IndexPage",
  components: {
    HeaderPart,
    AsidePart,
  },
  methods: {
    doCollapse() {
      this.isCollapse = !this.isCollapse;
      this.aside_width = this.isCollapse ? '64px' : '200px';
      this.icon = this.isCollapse ? 'Expand' : 'Fold';
    },
  },
  data() {
    return {
      isCollapse: false,
      aside_width: '200px',
      icon: 'Fold',
    };
  },
};
</script>

<template>
  <el-container class="layout-container">
    <el-aside :width="aside_width">
      <el-scrollbar>
        <AsidePart :isCollapse="isCollapse" />
      </el-scrollbar>
    </el-aside>
    <el-container>
      <el-header>
        <HeaderPart @doCollapse="doCollapse" :icon="icon" />
      </el-header>
      <el-main>
        <el-scrollbar>
          <div class="main-content">
            <router-view />
          </div>
        </el-scrollbar>
      </el-main>
    </el-container>
  </el-container>
</template>

<style scoped>
.layout-container {
  height: 100vh;
  background: linear-gradient(135deg, #eceff5, #f7f9fc);
}

.el-aside {
  background: #304156;
  transition: width 0.3s ease;
}

.el-header {
  background: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.08);
  padding: 0;
  display: flex;
  align-items: center;
  height: 60px;
}

.el-main {
  padding: 20px;
}

.main-content {
  max-width: 1000px;
  margin: 0 auto;
}

@media (max-width: 768px) {
  .el-aside {
    width: 64px !important;
  }

  .el-header {
    height: 50px;
  }

  .el-main {
    padding: 10px;
  }
}
</style>
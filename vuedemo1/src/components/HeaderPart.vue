<script>
import { Expand, Fold, Setting } from "@element-plus/icons-vue";
import { ElMessage } from "element-plus";

export default {
  name: "HeaderPart",
  data() {
    return {
      user: JSON.parse(sessionStorage.getItem('CurUser')) || { name: '用户' },
      currentTime: '',
      timer: null,
      imgUrl: require('../assets/lover1.png'),
      icon: 'Expand'  // 设置默认值
    };
  },
  components: {
    Expand,
    Fold,
    Setting,
  },
  methods: {
    toUser() {
      console.log("toUser");
      this.$router.push("/Home");
    },
    toGoOut() {
      console.log("toGoOut");
      this.$confirm('您确定要退出吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true,
      })
          .then(() => {
            ElMessage({
              type: 'success',
              message: '退出登录成功',
            });
            this.$router.push("/");
            sessionStorage.clear();
          })
          .catch(() => {
            ElMessage({
              type: 'info',
              message: '已取消退出登录',
            });
          });
    },
    collapse() {
      this.$emit('doCollapse');
      // 切换图标
      this.icon = this.icon === 'Fold' ? 'Expand' : 'Fold';
    },
    updateTime() {
      const now = new Date();
      this.currentTime = now.getFullYear() + '-' +
          String(now.getMonth() + 1).padStart(2, '0') + '-' +
          String(now.getDate()).padStart(2, '0') + ' ' +
          String(now.getHours()).padStart(2, '0') + ':' +
          String(now.getMinutes()).padStart(2, '0') + ':' +
          String(now.getSeconds()).padStart(2, '0');
    },
  },
  created() {
    this.updateTime();
    this.timer = setInterval(this.updateTime, 1000);
  },
  beforeUnmount() {
    if (this.timer) {
      clearInterval(this.timer);
    }
  },
};
</script>

<template>
  <div class="header-container">
    <div class="header-content">
      <div class="header-left">
        <el-icon size="20" color="#303133" class="collapse-icon" @click="collapse">
          <!-- 根据 icon 值动态切换 Fold 和 Expand -->
          <fold v-if="icon === 'Fold'" />
          <expand v-else />
        </el-icon>
        <img :src="imgUrl" alt="Logo" class="logo" />
        <span class="header-title">知识库管理系统</span>
      </div>
      <div class="header-right">
        <span class="user-name">{{ user.name }}</span>
        <span class="current-time">{{ currentTime }}</span>
        <el-dropdown trigger="click">
          <el-icon size="20" color="#303133" class="setting-icon">
            <setting />
          </el-icon>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="toUser">个人中心</el-dropdown-item>
              <el-dropdown-item @click="toGoOut">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
  </div>
</template>

<style scoped>
.header-container {
  width: 100%;
  background: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  padding: 12px 20px;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  max-width: 1200px;
  margin: 0 auto;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 12px;
}

.logo {
  width: 40px;
  height: 40px;
  object-fit: cover;
  border-radius: 50%;
  background: #f5f7fa;
  padding: 2px;
}

.logo:hover {
  transform: scale(1.05);
  transition: transform 0.3s;
}

.collapse-icon {
  cursor: pointer;
  transition: transform 0.3s;
}

.collapse-icon:hover {
  transform: scale(1.1);
}

.header-title {
  font-size: 18px;
  font-weight: 600;
  color: #303133;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 10px;
}

.user-name {
  font-size: 14px;
  color: #606266;
}

.current-time {
  font-size: 14px;
  color: #606266;
  background: #f5f7fa;
  padding: 4px 8px;
  border-radius: 4px;
}

.setting-icon {
  cursor: pointer;
  transition: transform 0.3s;
}

.setting-icon:hover {
  transform: scale(1.1);
}

:deep(.el-dropdown-menu__item) {
  font-size: 14px;
  padding: 8px 20px;
}

:deep(.el-dropdown-menu__item:hover) {
  background-color: #e6f7ff;
}

@media (max-width: 768px) {
  .header-container {
    padding: 10px;
  }

  .header-content {
    flex-direction: column;
    align-items: flex-start;
    gap: 8px;
  }

  .header-left {
    width: 100%;
  }

  .logo {
    width: 32px;
    height: 32px;
  }

  .header-title {
    font-size: 16px;
  }

  .header-right {
    width: 100%;
    justify-content: flex-end;
  }

  .user-name,
  .current-time {
    font-size: 12px;
  }
}
</style>

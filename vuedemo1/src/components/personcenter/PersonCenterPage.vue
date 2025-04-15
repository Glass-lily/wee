<template>
  <div class="home-container">
    <h1 class="welcome-title">欢迎你，{{ user.name || '用户' }}！</h1>
    <el-descriptions
        title="个人中心"
        :column="column"
        border
        class="user-info"
    >
      <el-descriptions-item>
        <template #label>
          <el-icon><User /></el-icon>
          账号
        </template>
        {{ user.no || '-' }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <el-icon><Iphone /></el-icon>
          电话
        </template>
        {{ user.phone || '-' }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <el-icon><Location /></el-icon>
          性别
        </template>
        <el-tag
            :type="user.sex === '1' ? 'primary' : 'danger'"
            size="small"
            effect="light"
        >
          <el-icon>
            <Male v-if="user.sex === '1'" />
            <Female v-else />
          </el-icon>
          {{ user.sex === '1' ? '男' : '女' }}
        </el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <el-icon><Tickets /></el-icon>
          角色
        </template>
        <el-tag
            type="success"
            size="small"
            effect="light"
        >
          {{ user.roleId == 0 ? '超级管理员' : user.roleId == 1 ? '管理员' : '用户' }}
        </el-tag>
      </el-descriptions-item>
    </el-descriptions>
    <div class="action-buttons">
      <el-button type="primary" round>修改个人信息</el-button>
    </div>
    <div class="date-utils-wrapper">
      <DateUtils />
    </div>
  </div>
</template>

<script>
import DateUtils from "../DateUtils.vue";
import { User, Iphone, Location, Tickets, Male, Female } from "@element-plus/icons-vue";

export default {
  name: "HomePage",
  components: { DateUtils, User, Iphone, Location, Tickets, Male, Female },
  data() {
    return {
      user: {},
    };
  },
  computed: {
    column() {
      return this.$isMobile ? 1 : 2;
    },
  },
  methods: {
    init() {
      const curUser = sessionStorage.getItem('CurUser');
      this.user = curUser ? JSON.parse(curUser) : {};
    },
  },
  created() {
    this.init();
  },
};
</script>

<style scoped>
.home-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #eceff5, #f7f9fc);
  padding: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
}

.welcome-title {
  font-size: 28px;
  font-weight: 600;
  color: #303133;
  text-align: center;
  animation: fadeIn 0.8s ease-in-out;
}

.user-info {
  width: 100%;
  max-width: 800px;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.08);
  padding: 20px;
  animation: slideIn 0.8s ease-in-out;
}

.action-buttons {
  display: flex;
  gap: 10px;
}

.el-button {
  transition: transform 0.2s;
}

.el-button:hover {
  transform: scale(1.05);
}

.date-utils-wrapper {
  width: 100%;
  max-width: 800px;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.08);
  padding: 20px;
  animation: slideIn 0.8s ease-in-out 0.2s;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(-20px); }
  to { opacity: 1; transform: translateY(0); }
}

@keyframes slideIn {
  from { opacity: 0; transform: translateY(20px); }
  to { opacity: 1; transform: translateY(0); }
}

@media (max-width: 768px) {
  .home-container {
    padding: 10px;
  }

  .welcome-title {
    font-size: 22px;
  }

  .user-info,
  .date-utils-wrapper {
    padding: 15px;
  }
}
</style>
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
        <el-icon><Edit /></el-icon>
        姓名
      </template>
      {{ user.name || '-' }}
    </el-descriptions-item>
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
          手机号码
        </template>
        {{ user.phone || '-' }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <el-icon><Lock /></el-icon>
          密码
        </template>
        {{ maskPassword(user.password) || '****' }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <el-icon><Tickets /></el-icon>
          身份
        </template>
        <el-tag
            type="success"
            size="small"
            effect="light"
        >
          {{ user.roleId == 0 ? '超级管理员' : user.roleId == 1 ? '管理员' : '用户' }}
        </el-tag>
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
    </el-descriptions>
    <div class="action-buttons">
      <el-button type="primary" round @click="openEditDialog">修改个人信息</el-button>
    </div>
    <div class="date-utils-wrapper">
      <DateUtils />
    </div>

    <!-- 编辑个人信息对话框 -->
    <el-dialog
        title="修改个人信息"
        v-model="editDialogVisible"
        width="90%"
        :max-width="600"
        :close-on-click-modal="false"
        :before-close="handleClose"
        class="edit-dialog"
    >
      <el-form
          ref="editForm"
          :model="editFormData"
          :rules="editRules"
          label-width="100px"
      >
        <el-form-item label="姓名" prop="name" class="form-item">
          <el-input v-model="editFormData.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="手机号码" prop="phone" class="form-item">
          <el-input v-model="editFormData.phone" placeholder="请输入手机号码" />
        </el-form-item>
        <el-form-item label="新密码" prop="password" class="form-item">
          <el-input
              v-model="editFormData.password"
              type="password"
              placeholder="请输入新密码（留空不修改）"
              show-password
          />
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword" class="form-item">
          <el-input
              v-model="editFormData.confirmPassword"
              type="password"
              placeholder="请再次输入密码"
              show-password
          />
        </el-form-item>
        <el-form-item label="性别" prop="sex" class="form-item">
          <el-radio-group v-model="editFormData.sex">
            <el-radio label="1">男</el-radio>
            <el-radio label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="handleClose">取消</el-button>
          <el-button type="primary" :loading="saving" @click="saveChanges">保存</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import DateUtils from "../DateUtils.vue";
import {User, Iphone, Tickets, Male, Female, Edit, Lock, Location} from "@element-plus/icons-vue";
import axios from 'axios';

export default {
  name: "HomePage",
  components: {DateUtils, User, Iphone, Tickets, Male, Female, Edit, Lock, Location},
  data() {
    return {
      user: {},
      editDialogVisible: false,
      saving: false,
      editFormData: {
        name: '',
        phone: '',
        password: '',
        confirmPassword: '',
        sex: '1',
      },
      editRules: {
        name: [
          {required: true, message: '请输入姓名', trigger: 'blur'},
          {min: 2, max: 100, message: '姓名长度应为2到100个字符', trigger: 'blur'},
        ],
        phone: [
          {required: true, message: '请输入手机号码', trigger: 'blur'},
          {
            pattern: /^1[3-9]\d{9}$/,
            message: '请输入有效的手机号码',
            trigger: 'blur',
          },
        ],
        password: [
          {min: 6, max: 20, message: '密码长度应为6到20个字符', trigger: 'blur'},
        ],
        confirmPassword: [
          {
            validator: (rule, value, callback) => {
              if (this.editFormData.password && !value) {
                callback(new Error('请再次输入密码'));
              } else if (value && value !== this.editFormData.password) {
                callback(new Error('两次输入的密码不一致'));
              } else {
                callback();
              }
            },
            trigger: 'blur',
          },
        ],
        sex: [
          {required: true, message: '请选择性别', trigger: 'change'},
        ],
      },
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
      console.log('Current user:', this.user);
      if (!this.user.id) {
        this.$message.error('用户ID缺失，请重新登录');
      }
    },
    maskPassword(password) {
      if (!password) return null;
      return '*'.repeat(Math.min(password.length, 8)); // 掩码处理
    },
    openEditDialog() {
      this.editFormData = {
        name: this.user.name || '',
        phone: this.user.phone || '',
        password: '',
        confirmPassword: '',
        sex: this.user.sex || '1',
      };
      this.editDialogVisible = true;
    },
    handleClose() {
      this.$refs.editForm.resetFields();
      this.editDialogVisible = false;
    },
    async saveChanges() {
      this.$refs.editForm.validate(async (valid) => {
        if (valid) {
          this.saving = true;
          try {
            const payload = {
              id: this.user.id,
              name: this.editFormData.name,
              phone: this.editFormData.phone,
              sex: this.editFormData.sex,
            };
            if (this.editFormData.password) {
              payload.password = this.editFormData.password;
            }
            console.log('Sending request to: /api/user/update');
            console.log('Payload:', payload);
            const response = await axios.post('/api/user/update', payload);
            console.log('Response:', response.data);
            if (response.data.code === 200) {
              this.user = {
                ...this.user,
                name: this.editFormData.name,
                phone: this.editFormData.phone,
                sex: this.editFormData.sex,
                password: this.editFormData.password || this.user.password,
              };
              sessionStorage.setItem('CurUser', JSON.stringify(this.user));
              this.$message.success(response.data.data || '个人信息更新成功');
              this.editDialogVisible = false;
            } else {
              this.$message.error(response.data.msg || '更新失败');
            }
          } catch (error) {
            console.error('Request failed:', error.response?.data, error.message);
            this.$message.error(error.response?.data?.msg || '更新失败，请稍后重试');
          } finally {
            this.saving = false;
          }
        } else {
          this.$message.error('请检查表单输入');
        }
      });
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
  background: linear-gradient(135deg, #e6f0fa, #f0f7ff);
  padding: 30px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 25px;
}

.welcome-title {
  font-size: 32px;
  font-weight: 700;
  color: #1f2a44;
  text-align: center;
  animation: fadeIn 1s ease-in-out;
  letter-spacing: 1px;
}

.user-info {
  width: 100%;
  max-width: 900px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 16px;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.1);
  padding: 25px;
  animation: slideIn 0.8s ease-in-out;
  transition: transform 0.3s ease;
}

.user-info:hover {
  transform: translateY(-5px);
}

.action-buttons {
  display: flex;
  gap: 15px;
}

.el-button {
  padding: 12px 24px;
  font-size: 16px;
  transition: all 0.3s ease;
}

.el-button:hover {
  transform: scale(1.08);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.date-utils-wrapper {
  width: 100%;
  max-width: 900px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 16px;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.1);
  padding: 25px;
  animation: slideIn 0.8s ease-in-out 0.2s;
  transition: transform 0.3s ease;
}

.date-utils-wrapper:hover {
  transform: translateY(-5px);
}

.edit-dialog {
  animation: fadeIn 0.5s ease-in-out;
}

.form-item {
  animation: slideIn 0.5s ease-in-out;
}

.el-input {
  transition: all 0.3s ease;
}

.el-input:hover,
.el-input:focus {
  border-color: #409EFF;
  box-shadow: 0 0 8px rgba(64, 158, 255, 0.3);
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(-20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes slideIn {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@media (max-width: 768px) {
  .home-container {
    padding: 15px;
  }

  .welcome-title {
    font-size: 24px;
  }

  .user-info,
  .date-utils-wrapper {
    padding: 20px;
    max-width: 100%;
  }

  .el-button {
    padding: 10px 20px;
    font-size: 14px;
  }

  .el-descriptions {
    font-size: 14px;
  }
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 15px;
}
</style>
<script>
import { ElMessage } from "element-plus";
import { commit } from "@/store";

export default {
  name: "LoginView",
  data() {
    return {
      confirm_disabled: false,
      loginForm: {
        no: '',
        password: ''
      },
      rules: {
        no: [
          { required: true, message: '请输入账号', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    confirm() {
      this.confirm_disabled = true;
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.$http
              .post(this.$httpUrl + '/user/login', this.loginForm)
              .then(res => res.data)
              .then(res => {
                console.log(res);
                if (res.code === 200) {
                  sessionStorage.setItem("CurUser", JSON.stringify(res.data.user));
                  commit("setMenu", res.data.menu);
                  console.log(res.data.menu);
                  this.$router.replace('/Home');
                } else {
                  this.confirm_disabled = false;
                  ElMessage.error('校验失败，用户名或密码错误！');
                }
              })
              .catch(() => {
                this.confirm_disabled = false;
                ElMessage.error('网络错误，请稍后重试！');
              });
        } else {
          this.confirm_disabled = false;
          ElMessage.error('校验失败！');
        }
      });
    }
  }
};
</script>

<template>
  <div class="login-body">
    <div class="login-container">
      <div class="login-content">
        <h1 class="login-title">用户登录</h1>
        <el-form
            :model="loginForm"
            :rules="rules"
            ref="loginForm"
            label-position="top"
            class="login-form"
        >
          <el-form-item label="账号" prop="no">
            <el-input
                v-model="loginForm.no"
                placeholder="请输入账号"
                autocomplete="off"
                clearable
                @keyup.enter="confirm"
            />
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input
                v-model="loginForm.password"
                type="password"
                placeholder="请输入密码"
                show-password
                autocomplete="off"
                clearable
                @keyup.enter="confirm"
            />
          </el-form-item>
          <el-form-item>
            <el-button
                type="primary"
                :disabled="confirm_disabled"
                :loading="confirm_disabled"
                @click="confirm"
                class="login-btn"
            >
              登录
            </el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<style scoped>
.login-body {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #6e8efb, #a777e3);
  overflow: hidden;
}

.login-container {
  width: 100%;
  max-width: 400px;
  background: #ffffff;
  border-radius: 12px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.15);
  padding: 30px;
  animation: fadeIn 0.5s ease-in-out;
}

.login-content {
  width: 100%;
}

.login-title {
  font-size: 24px;
  font-weight: bold;
  color: #333;
  text-align: center;
  margin-bottom: 20px;
}

.login-form {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.el-form-item {
  margin-bottom: 20px;
}

.el-input {
  width: 100%;
  transition: all 0.3s;
}

.el-input :deep(.el-input__wrapper) {
  border-radius: 8px;
  padding: 2px 10px;
}

.el-input :deep(.el-input__wrapper:hover),
.el-input :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 0 0 1px var(--el-color-primary);
}

.login-btn {
  width: 100%;
  padding: 12px;
  font-size: 16px;
  border-radius: 8px;
  transition: all 0.3s;
}

.login-btn:hover {
  background-color: var(--el-color-primary-dark-2);
  transform: translateY(-2px);
}

.login-btn:active {
  transform: translateY(0);
}

.login-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@media (max-width: 480px) {
  .login-container {
    max-width: 90%;
    padding: 20px;
  }

  .login-title {
    font-size: 20px;
  }

  .login-btn {
    font-size: 14px;
    padding: 10px;
  }
}
</style>
<template>
  <div class="home-container">
    <!-- 欢迎信息 -->
    <div class="welcome-message">
      <h1>{{ welcomeMessage }}</h1>
    </div>

    <!-- 搜索区域 -->
    <div class="search-bar">
      <el-input
          v-model="searchQuery"
          placeholder="请输入文章名进行搜索"
          clearable
          class="search-input"
      >
        <template #suffix>
          <el-icon>
            <Search/>
          </el-icon>
        </template>
      </el-input>
      <el-button type="primary" @click="searchArticles" class="search-btn">搜索</el-button>
    </div>

    </div>

</template>

<script>
export default {
  name: "HomePage",
  data() {
    return {
      user: JSON.parse(sessionStorage.getItem('CurUser')) || { name: '用户' },
      welcomeMessage: '欢迎来到知识库管理系统！',
      searchQuery: '',
      selectedArticle: null,
      dialogVisible: false,
    };
  },
  computed: {
  },
  created() {
    if (this.user.name === '用户') {
      this.welcomeMessage = '请先登录';
    } else {
      this.welcomeMessage = `欢迎回来, ${this.user.name}`;
    }
  },
  methods: {

  },
};
</script>

<style scoped>
.home-container {
  padding: 20px;
  font-family: Arial, sans-serif;
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.welcome-message {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
}

.recent-activity ul {
  list-style-type: none;
  padding: 0;
}

.recent-activity li {
  padding: 5px 0;
}

.search-bar {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.search-input {
  width: 220px;
  border-radius: 5px;
  border: 1px solid #dcdfe6;
  padding: 10px;
  font-size: 14px;
}

.search-btn {
  margin-left: 10px;
  padding: 10px 20px;
  font-size: 14px;
  background-color: #409eff;
  color: white;
}

.search-btn:hover {
  background-color: #66b1ff;
}

.full-width-table th, .full-width-table td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

.full-width-table th {
  background-color: #f2f2f2;
  font-weight: bold;
}

</style>

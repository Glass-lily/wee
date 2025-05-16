<template>
  <div v-if="shareData" class="share-container">
    <el-card class="content-card">
      <h2>{{ shareData.name }}</h2>
      <p>{{ shareData.content }}</p>
      <div v-if="shareData.createdAt">
        <span>创建时间：{{ formatDate(shareData.createdAt) }}</span>
      </div>
      <div v-if="shareData.updatedAt">
        <span>更新时间：{{ formatDate(shareData.updatedAt) }}</span>
      </div>
    </el-card>
  </div>
  <div v-else class="loading-message">
    <el-message>内容加载失败或未找到</el-message>
  </div>
</template>

<script>
export default {
  data() {
    return {
      shareData: null,  // 存储分享的内容
    };
  },
  created() {
    const id = this.$route.params.id;  // 获取分享链接中的id参数
    this.fetchShareData(id);  // 调用方法获取分享内容
  },
  methods: {
    // 获取分享内容
    async fetchShareData(id) {
      try {
        const response = await this.$http.get(`/text/share/${id}`);
        if (response.code === 200) {
          this.shareData = response.data;  // 设置分享内容
        } else {
          this.$message.error("获取分享内容失败");
        }
      } catch (error) {
        console.error("请求失败：" + error.message);
        this.$message.error("内容加载失败");
      }
    },
    // 格式化日期
    formatDate(date) {
      const options = { year: 'numeric', month: 'long', day: 'numeric' };
      return new Date(date).toLocaleDateString(undefined, options);
    }
  }
};
</script>

<style scoped>
.share-container {
  padding: 20px;
}

.content-card {
  padding: 20px;
  background-color: #ffffff;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
  margin: 10px 0;
}

h2 {
  font-size: 24px;
  color: #333;
}

p {
  font-size: 16px;
  color: #666;
}

span {
  font-size: 14px;
  color: #999;
}

.loading-message {
  margin-top: 20px;
  text-align: center;
}
</style>

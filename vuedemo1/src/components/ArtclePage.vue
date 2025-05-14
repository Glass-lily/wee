<template>
  <div class="article-container">
    <h1>{{ article.name }}</h1>
    <p>{{ article.content }}</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: "ArticlePage",
  data() {
    return {
      article: {}
    };
  },
  async created() {
    const articleId = this.$route.params.id;
    try {
      const response = await axios.get(`/articles/${articleId}`);
      this.article = response.data;
    } catch (error) {
      console.error('获取文章详情失败:', error);
    }
  }
};
</script>

<style scoped>
.article-container {
  padding: 20px;
  font-family: Arial, sans-serif;
}

h1 {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
}
</style>
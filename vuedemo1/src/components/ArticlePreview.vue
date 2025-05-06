<template>
  <el-dialog
      v-model="visible"
      :title="article?.name"
      width="60%"
      top="5vh"
  >
    <div class="preview-content">
      <div class="meta-info">
        <el-tag type="info">创建时间：{{ article?.createdAt }}</el-tag>
        <el-tag type="info" style="margin-left: 10px">
          最后更新：{{ article?.updatedAt }}
        </el-tag>
      </div>
      <el-divider />
      <pre class="content">{{ article?.content }}</pre>
    </div>
    <template #footer>
      <el-button @click="visible = false">关闭</el-button>
      <el-button type="primary" @click="$emit('export', article)">导出PDF</el-button>
    </template>
  </el-dialog>
</template>

<script>
export default {
  props: {
    modelValue: Boolean,
    article: Object
  },
  emits: ['update:modelValue', 'export'],
  computed: {
    visible: {
      get() { return this.modelValue },
      set(value) { this.$emit('update:modelValue', value) }
    }
  }
}
</script>

<style scoped>
.preview-content {
  padding: 10px;
}

.meta-info {
  margin-bottom: 15px;
}

.content {
  white-space: pre-wrap;
  font-family: '微软雅黑',serif;
  line-height: 1.6;
  padding: 15px;
  background: #f8f9fa;
  border-radius: 4px;
}
</style>
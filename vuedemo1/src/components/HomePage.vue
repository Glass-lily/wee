<script>
export default {
  name: "HomePage",
  data() {
    return {
      user: JSON.parse(sessionStorage.getItem('CurUser')) || { name: '用户' },
      welcomeMessage: '欢迎来到知识库管理系统！',
      recentActivity: ['修改资料', '查看文档', '参与讨论'],
      tableData: [
        { name: '文档A', description: '描述A', date: '2025-04-15' },
        { name: '文档B', description: '描述B', date: '2025-04-14' },
        { name: '文档C', description: '描述C', date: '2025-04-13' },
      ],
    };
  },
  created() {
    if (this.user.name === '用户') {
      this.welcomeMessage = '请先登录';
    } else {
      this.welcomeMessage = `欢迎回来, ${this.user.name}`;
    }
  },
};
</script>

<template>
  <div class="home-container">
    <div class="welcome-message">
      <h1>{{ welcomeMessage }}</h1>
    </div>

    <div class="recent-activity">
      <h3>最近活动：</h3>
      <ul>
        <li v-for="(activity, index) in recentActivity" :key="index">{{ activity }}</li>
      </ul>
    </div>


    <div class="table-container">
      <h3>文档列表</h3>
      <table class="full-width-table">
        <thead>
        <tr>
          <th>名称</th>
          <th>描述</th>
          <th>日期</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(item, index) in tableData" :key="index">
          <td>{{ item.name }}</td>
          <td>{{ item.description }}</td>
          <td>{{ item.date }}</td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

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

.recent-activity {
  font-size: 16px;
}

.recent-activity ul {
  list-style-type: none;
  padding: 0;
}

.recent-activity li {
  padding: 5px 0;
}

.table-container {
  flex: 1; /* Ensures the table container fills the available space */
  overflow: auto;
}

.full-width-table {
  width: 100%; /* Ensures the table fills the container width */
  border-collapse: collapse; /* Makes table borders collapse */
  table-layout: fixed; /* Ensures consistent column width */
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

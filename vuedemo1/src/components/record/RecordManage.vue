<script>
import {ElMessage} from "element-plus";
import {List} from "@element-plus/icons-vue";

export default {
  name: "RecordManagePart",
  // eslint-disable-next-line vue/no-unused-components
  components: {List},
  data() {
    return {
      user: JSON.parse(sessionStorage.getItem('CurUser')),
      goodstypeData: [],
      storageData: [],
      tableData: [],
      pageSize: 10,
      pageNum: 1,
      total: 0,
      name: '',
      storage: '',
      goodstype: '',
      centerDialogVisible: false,
      form: {
        id: '',
        name: '',
        storage: '',
        goodstype: '',
        count: '',
        remark: ''
      },

    };
  },
  methods: {

    handleSizeChange(val) {
      this.pageNum = 1;
      this.pageSize = val;
      this.loadPost();
    },
    handleCurrentChange(val) {
      this.pageNum = val;
      this.loadPost();
    },
    loadPost() {
      this.$http
          .post(this.$httpUrl + '/record/listPage', {
            pageSize: this.pageSize,
            pageNum: this.pageNum,
            param: {
              name: this.name,
              storage: this.storage + '',
              goodstype: this.goodstype + '',
              roleId:this.user.roleId+ '',
              userId:this.user.id+ ''
            }
          })
          .then(res => res.data)
          .then(res => {
            if (res.code === 200) {
              this.tableData = res.data;
              this.total = res.total;
            } else {
              ElMessage.error('获取数据失败');
            }
          });
    }, loadStorage() {
      this.$http
          .get(this.$httpUrl + '/storage/list')
          .then(res => res.data)
          .then(res => {
            if (res.code === 200) {
              this.storageData = res.data;

            } else {
              ElMessage.error('获取数据失败');
            }
          });
    },
    loadGoodstype() {
      this.$http
          .get(this.$httpUrl + '/goodstype/list')
          .then(res => res.data)
          .then(res => {
            if (res.code === 200) {
              this.goodstypeData = res.data;

            } else {
              ElMessage.error('获取数据失败');
            }
          });
    },

    resetParam() {
      this.name = ''
      this.storage = ''
      this.goodstype = ''
    },

  },

  beforeMount() {
    this.loadGoodstype()
    this.loadStorage()
    this.loadPost();
  }
};
</script>
<template>
  <div class="goods-container">
    <!-- 搜索区域 -->
    <div class="search-bar">
      <el-input
          v-model="name"
          placeholder="请输入商品名称"
          clearable
          @keyup.enter="loadPost"
          class="search-input"
      >
        <template #suffix>
          <el-icon>
            <Search />
          </el-icon>
        </template>
      </el-input>

      <el-select v-model="storage" placeholder="请选择知识大类" class="select-input">
        <el-option
            v-for="item in storageData"
            :key="item.id"
            :label="item.name"
            :value="item.id"
            clearable
        />
      </el-select>

      <el-select v-model="goodstype" placeholder="请选择标签" class="select-input">
        <el-option
            v-for="item in goodstypeData"
            :key="item.id"
            :label="item.name"
            :value="item.id"
            clearable
        />
      </el-select>

      <el-button type="primary" @click="loadPost" class="search-btn">查询</el-button>
      <el-button type="default" @click="resetParam" class="reset-btn">重置</el-button>
    </div>

    <!-- 表格区域 -->
    <div class="table-wrapper">
      <el-table
          :data="tableData"
          stripe
          border
          highlight-current-row
          @current-change="selectCurrentChange"
          class="goods-table"
      >
        <el-table-column prop="id" label="ID" width="100" align="center" sortable />
        <el-table-column prop="goodsname" label="名字" width="200" align="center" sortable />
        <el-table-column prop="storagename" label="知识大类" width="200" align="center" />
        <el-table-column prop="goodstypename" label="标签" width="200" align="center" />
        <el-table-column prop="adminname" label="管理人" width="200" align="center" />
        <el-table-column prop="username" label="操作人" width="200" align="center" />
        <el-table-column prop="createtime" label="操作时间" width="200" align="center" />
        <el-table-column prop="remark" label="备注" min-width="200" show-overflow-tooltip />
      </el-table>
    </div>

    <!-- 分页区域 -->
    <div class="pagination-wrapper">
      <el-pagination
          v-model:current-page="pageNum"
          v-model:page-size="pageSize"
          :page-sizes="[5, 10, 20, 30]"
          :background="true"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          class="pagination"
      />
    </div>
  </div>
</template>

<style scoped>
.goods-container {
  padding: 20px;
  background-color: #f5f7fa;
  min-height: 100vh;
  box-sizing: border-box;
  font-family: 'Arial', sans-serif;
}

.search-bar {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
  flex-wrap: wrap;
  gap: 10px;
  background: #fff;
  padding: 15px;
  border-radius: 8px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
}

.search-input,
.select-input {
  width: 220px;
  border-radius: 8px;
}

.search-btn,
.reset-btn {
  margin-left: 10px;
  border-radius: 8px;
}

.reset-btn {
  background-color: #f4f6f9;
  border-color: #e0e0e0;
}

.search-btn {
  background-color: #409eff;
  color: white;
  border-color: #409eff;
}

.table-wrapper {
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.goods-table {
  width: 100%;
  padding: 10px;
}

.el-table th {
  background-color: #f4f6f9;
  color: #333;
  font-weight: 500;
}

.el-table tr:hover {
  background-color: #f9f9f9;
}

.pagination-wrapper {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}

.pagination {
  display: flex;
  justify-content: center;
}

.el-pagination {
  margin-top: 10px;
}

@media (max-width: 768px) {
  .search-bar {
    flex-direction: column;
    align-items: flex-start;
  }

  .search-input,
  .select-input {
    width: 100%;
  }

  .search-btn,
  .reset-btn {
    width: 100%;
    margin-left: 0;
    margin-top: 10px;
  }

  .pagination-wrapper {
    justify-content: center;
  }
}
</style>

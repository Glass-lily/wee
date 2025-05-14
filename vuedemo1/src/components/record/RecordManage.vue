<script>
import {ElMessage} from "element-plus";
import {List} from "@element-plus/icons-vue";

export default {
  name: "RecordManagePart",
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
          placeholder="请输入名字"
          clearable
          @keyup.enter="loadPost"
          class="search-input"
      >
        <template #suffix>
          <el-icon>
            <Search/>
          </el-icon>
        </template>
      </el-input>

      <el-select
          v-model="storage"
          placeholder="请选择知识大类">
        <el-option
            v-for="item in storageData"
            :key="item.id"
            :label="item.name"
            :value="item.id"
            clearable
            @keyup.enter="loadPost"

        >
          <template #suffix>
            <el-icon>
              <List/>
            </el-icon>
          </template>
        </el-option>
      </el-select>
      <el-select
          v-model="goodstype"
          placeholder="请选择标签">
        <el-option
            v-for="item in goodstypeData"
            :key="item.id"
            :label="item.name"
            :value="item.id"
            clearable
            @keyup.enter="loadPost"
        >
          <template #suffix>
            <el-icon>
              <List/>
            </el-icon>
          </template>
        </el-option>
      </el-select>
      <el-button type="primary" @click="loadPost" class="search-btn">查询</el-button>
      <el-button type="primary" @click="resetParam" class="add-btn">重置</el-button>
    </div>

    <!-- 表格区域 -->
    <div class="table-wrapper">
      <el-table
          :data="tableData"
          style="width: 100%"
          height="500px"
          stripe
          border
      >
       <el-table-column prop="id" label="ID" width="100" align="center" sortable/>
        <el-table-column prop="goodsname" label="名字" width="200" align="center" sortable/>
        <el-table-column prop="storagename" label="知识大类" width="200" align="center"/>
        <el-table-column prop="goodstypename" label="标签" width="200" align="center"/>
        <el-table-column prop="adminname" label="管理人" width="200" align="center"/>
        <el-table-column prop="username" label="操作人" width="200" align="center"/>
        <el-table-column prop="createtime" label="操作时间" width="200" align="center">
        </el-table-column>
        <el-table-column prop="remark" label="备注" min-width="200" show-overflow-tooltip/>

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
}

.search-bar {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
  flex-wrap: wrap;
  gap: 10px;
}

.search-input {
  width: 220px;
}

.search-btn,
.add-btn {
  margin-left: 10px;
}

.table-wrapper {
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.pagination-wrapper {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}

@media (max-width: 768px) {
  .search-bar {
    flex-direction: column;
    align-items: flex-start;
  }

  .search-input {
    width: 100%;
  }

  .search-btn,
  .add-btn {
    width: 100%;
    margin-left: 0;
    margin-top: 10px;
  }

  .pagination-wrapper {
    justify-content: center;
  }

}

</style>
<script>
import {ElMessage} from "element-plus";

export default {
  name: "GoodsManagePart",
  components: { },
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
      count:'',
      storage: '',
      goodstype: '',
      centerDialogVisible: false,
      inDialogVisible: false,
      innerVisible: false,
      currentRow: {},
      tempUser:{},
      form: {
        id: '',
        name: '',
        storage: '',
        goodstype: '',
        count: '',
        remark: ''
      },
      form1: {
        goods: '',
        goodsname: '',
        count: '',
        userid: '',
        username: '',
        admin_id: '',
        remark: '',
        action:'1'
      },
      rules: {
        name: [
          {required: true, message: '请输入姓名', trigger: 'blur'},
          {min: 2, max: 8, message: '长度应该在2-8个字符之间', trigger: 'blur'},
        ],
        goodstype: [
          {required: true, message: '请选择标签', trigger: 'change'}
        ],
        storage: [
          {required: true, message: '请选择知识大类', trigger: 'change'}
        ],
        count: [
          {required: true, message: '请输入', trigger: 'blur'}
        ],
      },
      rules1: {}
    };
  },
  methods: {
    // 选择当前行
    selectCurrentChange(val) {
      this.currentRow = val;
    },

    // 通过按钮的操作，点击后 count 改为 1
    GoodsTongguo() {
      if (!this.currentRow.id) {
        ElMessage.warning('请先点击选择记录');
        return;
      }

      this.setAuditStatus(this.currentRow.id, 1);  // 设置为通过状态
    },

    // 不通过按钮的操作，点击后 count 改为 2
    GoodsButongguo() {
      if (!this.currentRow.id) {
        ElMessage.warning('请先点击选择记录');
        return;
      }

      this.setAuditStatus(this.currentRow.id, 2);  // 设置为未通过状态

    },
    // 设置知识的审核状态
    setAuditStatus(id, count) {
      // 查找当前选中的知识

      const item = this.tableData.find(good => good.id === id);

      if (item) {
        item.count = count; // 更新知识的审核状态

        // 发送请求更新后端数据库
        this.$http
            .post(this.$httpUrl + '/goods/updateAuditStatus', {
              id: item.id,
              count: item.count
            }, {
              headers: {
                'Content-Type': 'application/x-www-form-urlencoded'  // 明确设置请求头
              }})
            .then(response => {
              if (response.data.success) {
                ElMessage.success('状态更新成功');
                this.loadPost();  // 刷新数据
              } else {
                ElMessage.success('状态更新成功');
               // ElMessage.error('状态更新失败');
              }
            })
            .catch(() => {
              ElMessage.error('请求失败');
            });
      } else {
        ElMessage.warning('未找到该知识');
      }
    },

    doSelectUser(val){
   //   console.log(val)
      this.tempUser=val
    },
    formatStorage(row) {
      let temp = this.storageData.find(item => {
        return item.id == row.storage
      })
      return temp && temp.name
    },
    formatGoodstype(row) {
      let temp = this.goodstypeData.find(item => {
        return item.id == row.goodstype
      })
      return temp && temp.name
    },
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
          .post(this.$httpUrl + '/goods/listPage', {
            pageSize: this.pageSize,
            pageNum: this.pageNum,
            param: {name: this.name, storage: this.storage + '', goodstype: this.goodstype + ''}
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
    },
    resetForm() {
      this.$refs.form.resetFields();
    },
    resetInForm() {
      this.$refs.form1.resetFields();
    },
    resetParam() {
      this.name = ''
      this.storage = ''
      this.goodstype = ''
    },
    mod(row) {
      this.centerDialogVisible = true;
      this.$nextTick(() => {
        this.form.id = row.id;
        this.form.name = row.name;
        this.form.storage = row.storage;
        this.form.goodstype = row.goodstype;
        this.form.count = row.count;
        this.form.remark = row.remark;
      });
    },
    del(id) {
      this.$http
          .get(this.$httpUrl + '/goods/del?id=' + id)
          .then(res => res.data)
          .then(res => {
            if (res.code === 200) {
              ElMessage({
                message: '操作成功！',
                type: 'success'
              });
              this.loadPost();
            } else {
              ElMessage.error('操作失败！');
            }
          });
    },
    add() {
      this.centerDialogVisible = true;
      this.$nextTick(() => {
        this.resetForm();
      });
    },
    save() {
      this.$refs.form.validate(valid => {
        if (valid) {
          if (this.form.id) {
            this.doMod();
          } else {
            this.doSave();
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    doMod() {
      this.$http
          .post(this.$httpUrl + '/goods/update', this.form)
          .then(res => res.data)
          .then(res => {
            if (res.code === 200) {
              ElMessage({
                message: '更新成功！',
                type: 'success'
              });
              this.centerDialogVisible = false;
              this.loadPost();
            } else {
              ElMessage.error('更新失败！');
            }
          });
    },
    doSave() {
      this.$http
          .post(this.$httpUrl + '/goods/save', this.form)
          .then(res => res.data)
          .then(res => {
            if (res.code === 200) {
              ElMessage({
                message: '新增成功！',
                type: 'success'
              });
              this.centerDialogVisible = false;
              this.loadPost();
            } else {
              ElMessage.error('新增失败！');
            }
          });
    },
    loadStorage() {
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
  },

  beforeMount() {
    this.loadStorage()
    this.loadGoodstype()
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
          placeholder="请输入知识名称"
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
      <el-button type="success" @click="add" class="add-btn" v-if="user.roleId != 2">新增</el-button>
      <el-button type="success" @click="GoodsTongguo" class="pass-btn" v-if="user.roleId != 2">通过</el-button>
      <el-button type="danger" @click="GoodsButongguo" class="reject-btn" v-if="user.roleId != 2">不通过</el-button>
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
        <el-table-column prop="name" label="名字" width="200" align="center" sortable />
        <el-table-column prop="storage" label="知识大类" width="200" align="center" :formatter="formatStorage" />
        <el-table-column prop="goodstype" label="标签" width="200" align="center" :formatter="formatGoodstype" />
        <el-table-column prop="count" label="状态" width="140" align="center">
          <template #default="scope">
            <el-tag
                :type="scope.row.count === 0 ? 'info' : scope.row.count === 1 ? 'success' : 'danger'"
                size="small"
                effect="light"
            >
              {{ scope.row.count === 0 ? '未审核' : scope.row.count === 1 ? '通过' : '未通过' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="remark" label="备注" min-width="200" show-overflow-tooltip />
        <el-table-column label="操作" width="180" align="center" v-if="user.roleId != 2">
          <template #default="scope">
            <el-button type="primary" size="small" @click="mod(scope.row)">编辑</el-button>
            <el-popconfirm
                title="确定删除这条记录吗？"
                @confirm="del(scope.row.id)"
            >
              <template #reference>
                <el-button type="danger" size="small">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
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

    <!-- 对话框 -->
    <el-dialog
        v-model="centerDialogVisible"
        title="存储信息"
        width="500px"
        center
        :close-on-click-modal="false"
    >
      <el-form ref="form" :rules="rules" :model="form" label-width="80px" class="dialog-form">
        <el-form-item label="名字" prop="name">
          <el-input v-model="form.name" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="知识大类" prop="storage">
          <el-select v-model="form.storage" placeholder="请选择知识大类">
            <el-option v-for="item in storageData" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="标签" prop="goodstype">
          <el-select v-model="form.goodstype" placeholder="请选择标签">
            <el-option v-for="item in goodstypeData" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="count">
          <el-input v-model="form.count" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" :rows="4" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="centerDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="save">确认</el-button>
        </div>
      </template>
    </el-dialog>
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

.search-input,
.select-input {
  width: 220px;
}

.search-btn,
.reset-btn,
.add-btn,
.pass-btn,
.reject-btn {
  margin-left: 10px;
}

.table-wrapper {
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  margin-bottom: 20px;
}

.goods-table {
  width: 100%;
  padding: 10px;
}

.pagination-wrapper {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}

.pagination {
  display: flex;
  justify-content: flex-end;
}

.dialog-form {
  margin: 0 auto;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.el-button--primary {
  background-color: #409eff;
  border-color: #409eff;
}

.el-button--default {
  background-color: #f4f6f9;
  border-color: #dcdfe6;
}

.el-button--success {
  background-color: #67c23a;
  border-color: #67c23a;
}

.el-button--danger {
  background-color: #f56c6c;
  border-color: #f56c6c;
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
  .add-btn,
  .pass-btn,
  .reject-btn {
    width: 100%;
    margin-left: 0;
    margin-top: 10px;
  }

  .pagination-wrapper {
    justify-content: center;
  }
}
</style>

<script>
import { ElMessage } from "element-plus";

export default {
  name: "GoodstypeManagePart",
  data() {
    return {
      tableData: [],
      pageSize: 10,
      pageNum: 1,
      total: 0,
      name: '',
      centerDialogVisible: false,
      form: {
        id: '',
        name: '',
        remark: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' },
          { min: 2, max: 8, message: '长度应该在2-8个字符之间', trigger: 'blur' },
        ]
      }
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
          .post(this.$httpUrl + '/goodstype/listPage', {
            pageSize: this.pageSize,
            pageNum: this.pageNum,
            param: { name: this.name }
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
    mod(row) {
      this.centerDialogVisible = true;
      this.$nextTick(() => {
        this.form.id = row.id;
        this.form.name = row.name;
        this.form.remark = row.remark;
      });
    },
    del(id) {
      this.$http
          .get(this.$httpUrl + '/goodstype/del?id=' + id)
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
          .post(this.$httpUrl + '/goodstype/update', this.form)
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
          .post(this.$httpUrl + '/goodstype/save', this.form)
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
    }
  },
  beforeMount() {
    this.loadPost();
  }
};
</script>

<template>
  <div class="goodstype-container">
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
          <el-icon><Search /></el-icon>
        </template>
      </el-input>
      <el-button type="primary" @click="loadPost" class="search-btn">查询</el-button>
      <el-button type="success" @click="add" class="add-btn">新增</el-button>
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
        <el-table-column prop="id" label="ID" width="100" align="center" sortable />
        <el-table-column prop="name" label="名字" width="200" align="center" sortable />
        <el-table-column prop="remark" label="备注" min-width="200" show-overflow-tooltip />
        <el-table-column label="操作" width="180" align="center" >
          <template #default="scope" >
            <el-button
                type="primary"
                size="small"
                @click="mod(scope.row)"
            >
              编辑
            </el-button>
            <el-popconfirm
                title="确定删除这条记录吗？"
                @confirm="del(scope.row.id)"
            >
              <template #reference>
                <el-button
                    type="danger"
                    size="small"
                >
                  删除
                </el-button>
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
      <el-form
          ref="form"
          :rules="rules"
          :model="form"
          label-width="80px"
          class="dialog-form"
      >
        <el-form-item label="名字" prop="name">
          <el-input v-model="form.name" placeholder="请输入名字" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input
              v-model="form.remark"
              type="textarea"
              :rows="4"
              placeholder="请输入备注"
          />
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
.goodstype-container {
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

.el-table {
  border-radius: 8px;
}

.el-table .cell {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 8px;
}

.pagination-wrapper {
  margin-top: 20px;
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

  .el-table {
    height: auto !important;
  }

  .pagination-wrapper {
    justify-content: center;
  }

  .el-pagination {
    --el-pagination-button-width: 28px;
  }
}

:deep(.el-table__row--striped) {
  background-color: #fafafa;
}

:deep(.el-table__row:hover) {
  background-color: #e6f7ff;
}
</style>
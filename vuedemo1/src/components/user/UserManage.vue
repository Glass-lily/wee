<script>
import { ElMessage } from "element-plus";

export default {
  name: "UserManagePart",
  data() {
    let checkAge = (rule, value, callback) => {
      if (value > 150) {
        callback(new Error('年龄输入过大'));
      } else {
        callback();
      }
    };
    let checkDuplicate = (rule, value, callback) => {
      if (this.form.id) {
        return callback();
      }
      this.$http
          .get(this.$httpUrl + "/user/findByNo?no=" + value)
          .then(res => res.data)
          .then(res => {
            if (res.code !== 200) {
              callback();
            } else {
              callback(new Error('账号已经存在'));
            }
          })
          .catch(() => {
            callback(new Error('检查账号失败'));
          });
    };
    return {
      tableData: [],
      pageSize: 10,
      pageNum: 1,
      total: 0,
      name: '',
      sex: '',
      roleId: '',
      sexs: [
        { label: '男', value: '1' },
        { label: '女', value: '0' },
      ],
      centerDialogVisible: false,
      form: {
        id: '',
        no: '',
        name: '',
        sex: '1',
        age: '',
        password: '',
        roleId: '2',
        phone: '',
        isValid: 'Y',  // 默认状态为有效
      },
      rules: {
        no: [
          { required: true, message: '请输入账号', trigger: 'blur' },
          { min: 2, max: 16, message: '长度应为2-16个字符', trigger: 'blur' },
          { validator: checkDuplicate, trigger: 'blur' },
        ],
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' },
          { min: 2, max: 8, message: '长度应为2-8个字符', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 16, message: '长度应为6-16个字符', trigger: 'blur' },
        ],
        age: [
          { required: true, message: '请输入年龄', trigger: 'blur' },
          { pattern: /^[1-9][0-9]*$/, message: '年龄必须为正整数', trigger: 'blur' },
          { validator: checkAge, trigger: 'blur' },
        ],
        phone: [
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' },
        ],
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
    async loadGet() {
      try {
        const res = await this.$http.get(this.$httpUrl + '/user/list').then(res => res.data);
        console.log('loadGet:', res);
      } catch (error) {
        ElMessage.error('获取列表失败');
      }
    },
    async loadPost() {
      try {
        const res = await this.$http
            .post(this.$httpUrl + '/user/listPageC1', {
              pageSize: this.pageSize,
              pageNum: this.pageNum,
              param: {
                name: this.name,
                sex: this.sex,
                roleId: '2',
              },
            })
            .then(res => res.data);
        if (res.code === 200) {
          this.tableData = res.data;
          this.total = res.total;
        } else {
          ElMessage.error('获取数据失败');
        }
      } catch (error) {
        ElMessage.error('获取数据失败');
      }
    },
    resetParam() {
      this.name = '';
      this.sex = '';
    },
    resetForm() {
      this.$refs.form.resetFields();
    },
    mod(row) {
      this.centerDialogVisible = true;
      this.$nextTick(() => {
        this.form.id = row.id;
        this.form.no = row.no;
        this.form.name = row.name;
        this.form.password = row.password;
        this.form.sex = row.sex;
        this.form.age = row.age;
        this.form.roleId = row.roleId + '';
        this.form.phone = row.phone;
        this.form.isValid=row.isValid;
      });
    },
    del(id) {
      this.$http
          .get(this.$httpUrl + '/user/del?id=' + id)
          .then(res => res.data)
          .then(res => {
            if (res.code === 200) {
              ElMessage({
                message: '删除成功！',
                type: 'success',
              });
              this.loadPost();
            } else {
              ElMessage.error('删除失败！');
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
          console.log('Form validation failed');
          return false;
        }
      });
    },
    doMod() {
      this.$http
          .post(this.$httpUrl + '/user/update', this.form)
          .then(res => res.data)
          .then(res => {
            if (res.code === 200) {
              ElMessage({
                message: '更新成功！',
                type: 'success',
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
          .post(this.$httpUrl + '/user/save', this.form)
          .then(res => res.data)
          .then(res => {
            if (res.code === 200) {
              ElMessage({
                message: '新增成功！',
                type: 'success',
              });
              this.centerDialogVisible = false;
              this.loadPost();
            } else {
              ElMessage.error('新增失败！');
            }
          });
    },
    filterTag(value, row) {
      return row.sex === value || row.isValid === value;
    },
  },
  beforeMount() {
    this.loadGet();
    this.loadPost();
  },
};
</script>

<template>
  <div class="user-container">
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
      <el-select
          v-model="sex"
          placeholder="请选择性别"
          clearable
          class="search-select"
      >
        <el-option
            v-for="item in sexs"
            :key="item.value"
            :label="item.label"
            :value="item.value"
        />
      </el-select>
      <el-button type="primary" @click="loadPost" class="search-btn">查询</el-button>
      <el-button type="success" @click="resetParam" class="reset-btn">重置</el-button>
      <el-button type="primary" @click="add" class="add-btn">新增</el-button>
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
        <el-table-column prop="id" label="ID" width="80" align="center" sortable />
        <el-table-column prop="no" label="账号" width="180" align="center" sortable />
        <el-table-column prop="name" label="名字" width="140" align="center" sortable />
        <el-table-column prop="sex" label="性别" width="100" align="center">
          <template #default="scope">
            <el-tag
                :type="scope.row.sex === '1' ? 'primary' : 'danger'"
                size="small"
                effect="light"
            >
              {{ scope.row.sex === '1' ? '男' : '女' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="age" label="年龄" width="100" align="center" sortable />
        <el-table-column prop="password" label="密码" width="180" align="center" />
        <el-table-column prop="phone" label="手机号" width="160" align="center" />
        <el-table-column prop="roleId" label="身份" width="140" align="center">
          <template #default="scope">
            <el-tag
                :type="scope.row.roleId === '0' ? 'danger' : scope.row.roleId === '1' ? 'success' : 'primary'"
                size="small"
                effect="light"
            >
              {{ scope.row.roleId === '0' ? '超级管理员' : scope.row.roleId === '1' ? '管理员' : '普通用户' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="isValid" label="是否有效" width="120" align="center"
                         :filters="[{ text: '有效', value: 'Y' }, { text: '无效', value: 'N' }]"
                         :filter-method="filterTag">
          <template #default="scope">
            <el-tag
                :type="scope.row.isValid === 'Y' ? 'success' : 'info'"
                size="small"
                effect="light"
            >
              {{ scope.row.isValid === 'Y' ? '有效' : '无效' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template #default="scope">
            <el-button
                type="primary"
                size="small"
                @click="mod(scope.row)"
            >
              编辑
            </el-button>
            <el-popconfirm
                title="确定删除此用户？"
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
        title="用户信息"
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
        <el-form-item label="账号" prop="no">
          <el-input v-model="form.no" placeholder="请输入账号" />
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="form.sex">
            <el-radio value="1">男</el-radio>
            <el-radio value="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="身份" prop="roleId">
          <el-radio-group v-model="form.roleId">
            <el-radio value="0">超级管理员</el-radio>
            <el-radio value="1">管理员</el-radio>
            <el-radio value="2">普通用户</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model="form.age" placeholder="请输入年龄" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password" show-password placeholder="请输入密码" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="是否有效" prop="isValid">
          <el-switch v-model="form.isValid" :active-value="'Y'" :inactive-value="'N'"></el-switch>
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
.user-container {
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

.search-select {
  width: 150px;
}

.search-btn,
.reset-btn,
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

  .search-input,
  .search-select {
    width: 100%;
  }

  .search-btn,
  .reset-btn,
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
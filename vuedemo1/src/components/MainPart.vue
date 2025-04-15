<script>
import { ElMessage } from "element-plus";

export default {
  name: "MainPart",
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
      this.$http.get(this.$httpUrl + "/user/findByNo?no=" + this.form.no).then(res => res.data).then(res => {
        if (res.code != 200) {
          callback();
        } else {
          callback(new Error('账号已经存在'));
        }
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
        { label: '男', value: 'M' },
        { label: '女', value: 'F' },
      ],
      centerDialogVisible: false,
      form: {
        id: '',
        no: '',
        name: '',
        sex: 'M',
        age: '',
        password: '',
        roleId: '2',
        phone: '',
      },
      rules: {
        no: [
          { required: true, message: '请输入账号', trigger: 'blur' },
          { min: 2, max: 16, message: '长度应该在2-16个字符之间', trigger: 'blur' },
          { validator: checkDuplicate, trigger: 'blur' },
        ],
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' },
          { min: 2, max: 8, message: '长度应该在2-8个字符之间', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 16, message: '长度应该在6-16个字符之间', trigger: 'blur' },
        ],
        age: [
          { required: true, message: '请输入年龄', trigger: 'blur' },
          { min: 1, max: 3, message: '长度在 1 到 3 个位', trigger: 'blur' },
          { pattern: /^([1-9][0-9]*){1,3}$/, message: '年龄必须为正整数字', trigger: "blur" },
          { validator: checkAge, trigger: 'blur' },
        ],
        phone: [
          { required: true, message: "手机号不能为空", trigger: "blur" },
          { pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: "请输入正确的手机号码", trigger: "blur" },
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
      this.$http.get(this.$httpUrl + '/user/list').then(res => res.data);
    },
    async loadPost() {
      this.$http.post(this.$httpUrl + '/user/listPageC', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          name: this.name,
          sex: this.sex,
        },
      }).then(res => res.data).then(res => {
        if (res.code === 200) {
          this.tableData = res.data;
          this.total = res.total;
        } else {
          ElMessage.error('获取数据失败');
        }
      });
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
      });
    },
    del(id) {
      this.$http.get(this.$httpUrl + '/user/del?id=' + id).then(res => res.data).then(res => {
        if (res.code === 200) {
          ElMessage({
            message: '操作成功！',
            type: 'success',
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
      this.$refs.form.validate((valid) => {
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
      this.$http.post(this.$httpUrl + '/user/update', this.form).then(res => res.data).then(res => {
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
      this.$http.post(this.$httpUrl + '/user/save', this.form).then(res => res.data).then(res => {
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
  <div class="main-container">
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
      <el-button type="primary" @click="loadPost" round>查询</el-button>
      <el-button type="success" @click="resetParam" round>重置</el-button>
      <el-button type="primary" @click="add" round>新增</el-button>
    </div>
    <div class="table-wrapper">
      <el-table
          :data="tableData"
          style="width: 100%"
          height="calc(100vh - 260px)"
          stripe
          border
      >
        <el-table-column prop="id" label="ID" width="80" sortable/>
        <el-table-column prop="no" label="帐号" width="180" sortable/>
        <el-table-column prop="name" label="名字" width="140" sortable/>
        <el-table-column prop="sex" label="性别" width="100">
          <template #default="scope">
            <el-tag
                :type="scope.row.sex === 'M' ? 'success' : 'primary'"
            >{{ scope.row.sex === 'M' ? '男' : '女' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="age" label="年龄" width="100" sortable/>
        <el-table-column prop="password" label="密码" width="180"/>
        <el-table-column prop="phone" label="手机号" width="160"/>
        <el-table-column prop="roleId" label="身份" width="140">
          <template #default="scope">
            <el-tag
                :type="scope.row.roleId === '0' ? 'danger' : scope.row.roleId === '1' ? 'success' : 'primary'"
            >{{ scope.row.roleId === '0' ? '超级管理员' : scope.row.roleId === '1' ? '管理员' : '普通用户' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="isValid" label="是否有效" width="100"
                         :filters="[{ text: '有效', value: 'Y' }, { text: '无效', value: 'N' }]"
                         :filter-method="filterTag"
        >
          <template #default="scope">
            <el-tag
                :type="scope.row.isValid === 'Y' ? 'primary' : 'info'"
            >{{ scope.row.isValid === 'Y' ? '有效' : '无效' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="scope">
            <el-button type="success" size="small" round @click="mod(scope.row)">编辑</el-button>
            <el-popconfirm title="确定删除？" @confirm="del(scope.row.id)">
              <template #reference>
                <el-button type="danger" size="small" round>删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="pagination-wrapper">
      <el-pagination
          v-model:current-page="pageNum"
          v-model:page-size="pageSize"
          :page-sizes="[5, 10, 20, 30]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          background
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      />
    </div>
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
          <el-input v-model="form.no" placeholder="请输入账号"/>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名"/>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="form.sex">
            <el-radio value="M">男</el-radio>
            <el-radio value="F">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="身份">
          <el-radio-group v-model="form.roleId">
            <el-radio value="0">超级管理员</el-radio>
            <el-radio value="1">管理员</el-radio>
            <el-radio value="2">普通用户</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model="form.age" placeholder="请输入年龄"/>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password" placeholder="请输入密码"/>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入手机号"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button type="primary" round @click="save">确认</el-button>
        <el-button round @click="centerDialogVisible = false">取消</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>
.main-container {
  background: #fff;
  border-radius: 12px;
  personallybox-shadow: 0 4px 16px rgba(0, 0, 0, 0.08);
  padding: 20px;
  max-width: 1000px;
  margin: 20px auto;
}

.search-bar {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
  flex-wrap: wrap;
}

.search-input {
  width: 200px;
}

.search-select {
  width: 150px;
}

.table-wrapper {
  border-radius: 8px;
  overflow: hidden;
}

.el-table {
  background: #fff;
}

.el-table th {
  background: #fafafa;
  color: #303133;
}

.el-table tr:hover {
  background: #e6f7ff;
}

.el-button {
  transition: transform 0.2s;
}

.el-button:hover {
  transform: scale(1.05);
}

.pagination-wrapper {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}

.dialog-form {
  margin: 0 auto;
}

@media (max-width: 768px) {
  .main-container {
    margin: 10px;
    padding: 15px;
  }

  .search-bar {
    flex-direction: column;
    align-items: flex-start;
  }

  .search-input,
  .search-select {
    width: 100%;
  }

  .el-table {
    height: auto;
  }

  .pagination-wrapper {
    justify-content: center;
  }
}
</style>
<script >

import {ElMessage} from "element-plus";

export  default {
  name:"MainPart",
  data(){
    let checkAge = (rule, value, callback) => {
      if(value>150){
        callback(new Error('年龄输入过大'));
      }else{
        callback();
      }
    };
    let checkDuplicate =(rule,value,callback)=>{
      if(this.form.id){
        return callback();
      }
      this.$http.get(this.$httpUrl+"/user/findByNo?no="+this.form.no).then(res=>res.data).then(res=>{
        if(res.code!=200){

          callback()
        }else{
          callback(new Error('账号已经存在'));
        }
      })
    };
    return {
      tableData:[],
      pageSize:10,
      pageNum:1,
      total:0,
      name:'',
      sex:'',
      roleId:'',
      sexs:[
        { label: '男', value: 'M' },
        { label: '女', value: 'F' },
      ],
      centerDialogVisible:false,
      form:{
        id:'',
        no:'',
        name:'',
        sex:'M',
        age:'',
        password:'',
        roleId:'2',
        phone:'',
      },
      rules:{
        no: [
          { required: true, message: '请输入账号', trigger: 'blur' },
          { min: 2, max: 16, message: '长度应该在2-16个字符之间', trigger: 'blur' },
          {validator:checkDuplicate,trigger: 'blur'}
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
          {required: true, message: '请输入年龄', trigger: 'blur'},
          {min: 1, max: 3, message: '长度在 1 到 3 个位', trigger: 'blur'},
          {pattern: /^([1-9][0-9]*){1,3}$/,message: '年龄必须为正整数字',trigger: "blur"},
          {validator:checkAge,trigger: 'blur'}
        ],
        phone: [
          {required: true,message: "手机号不能为空",trigger: "blur"},
          {pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: "请输入正确的手机号码", trigger: "blur"}
        ]
      }

    }
  },
  methods:{

    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageNum=1  //每次换页面条数时 默认转到第一页
      this.pageSize=val
      this.loadPost()
    },
    handleCurrentChange(val) {
      console.log(`当前页 : ${val}`);
      this.pageNum=val
      this.loadPost()
    },

    async loadGet(){
      this.$http.get(this.$httpUrl+'/user/list').then(res=>res.data)
       //   .then(res=>{console.log(res)})
    },
    async  loadPost(){
      this.$http.post(this.$httpUrl+'/user/listPageC',{
        pageSize:this.pageSize,
        pageNum:this.pageNum,
        param:{
          name:this.name,
          sex:this.sex
        }
      }).then(res=>res.data).then(res=>{
        //console.log(res)
        if (res.code===200){
          this.tableData=res.data
          this.total=res.total
        }else {
          alert('获取数据失败')
        }
      })
    },

    resetParam(){
      this.name='';
      this.sex='';
    },
    resetForm(){
      this.$refs.form.resetFields();
    },
    mod(row){
      this.centerDialogVisible=true;
      this.$nextTick(()=>{
        this.form.id=row.id
        this.form.no=row.no
        this.form.name=row.name
        this.form.password=row.password
        this.form.sex=row.sex
        this.form.age=row.age
        this.form.roleId=row.roleId+''
        this.form.phone=row.phone
        
      })

    },
    del(id){

      this.$http.get(this.$httpUrl+'/user/del?id='+id).then(res=>res.data).then(res=>{
        if (res.code===200){
          ElMessage({
            message: '操作成功！',
            type: 'success',
          })
          this.loadPost()
        }else {
          ElMessage.error('操作失败！')
        }
      })

    },
    add(){

      this.centerDialogVisible=true;
      this.$nextTick(()=>{
        this.resetForm();
      })

    },
    save(){
      this.$refs.form.validate((valid) => {
        if (valid) {
          if(this.form.id){
            this.doMod();
          }else{
            this.doSave();
          }

        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    doMod() {
      this.$http.post(this.$httpUrl+'/user/update',this.form ).then(res=>res.data).then(res=>{
        if (res.code===200){
          ElMessage({
            message: '更新成功！',
            type: 'success',
          })
          this.centerDialogVisible=false
          this.loadPost()
        }else {
          ElMessage.error('更新失败！')
        }
      })
    },
    doSave() {
      this.$http.post(this.$httpUrl+'/user/save',this.form ).then(res=>res.data).then(res=>{
        if (res.code===200){
          ElMessage({
            message: '新增成功！',
            type: 'success',
          })
          this.centerDialogVisible=false
          this.loadPost()
        }else {
          ElMessage.error('新增失败！')
        }
      })
    },

    filterTag(value, row) {
      return row.sex === value||row.isValid===value;
    }
  },
  beforeMount() {
    this.loadGet();
    this.loadPost();
  },

}

</script>

<template>


  <div style="height: 100%">
    <div style="margin:10px;">
      <el-input v-model="name" placeholder="请输入名字" style="width: 200px" @keyup.enter="loadPost">
        <template #suffix>
        <el-icon><Search /></el-icon>
        </template>
      </el-input>
      <el-select v-model="sex" filterable placeholder="请选择性别" style="margin-left: 5px;width: 150px">
        <el-option
           v-for="item in sexs"
           :key="item.value"
           :label="item.label"
           :value="item.value">
        </el-option>

      </el-select>

      <el-button type="primary" style="margin-left: 5px" @click="loadPost">查询</el-button>
      <el-button type="success" @click="resetParam">重置</el-button>
      <el-button type="primary" @click="add">新增</el-button>
    </div>

  <el-table :data="tableData" style="width: 100%" height="100%">
    <el-table-column prop="id" label="ID" width="100" sortable/>
    <el-table-column prop="no" label="帐号" width="200" sortable/>
    <el-table-column prop="name" label="名字" width="160" sortable/>

      <el-table-column prop="sex" label="性别" width="120" sortable>
        <template #default="scope">
          <el-tag
              :type="scope.row.sex === 'M' ? 'success' : 'primary'"
              disable-transitions
          >{{scope.row.sex === 'M' ? '男' : '女' }}</el-tag>
        </template>

      </el-table-column>
    <el-table-column prop="age" label="年龄"  width="120" sortable/>
    <el-table-column prop="password" label="密码"  width="200" />
    <el-table-column prop="phone" label="手机号"  width="200" />
    <el-table-column prop="roleId" label="身份"  width="160" sortable>
      <template #default="scope">
        <el-tag
            :type="scope.row.roleId === 0 ? 'danger' : (scope.row.roleId === 1 ? 'success' : 'primary')"
            disable-transitions
        >{{scope.row.roleId === 0 ? '超级管理员' : (scope.row.roleId === 1 ? '管理员' : '普通用户')}}</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="isValid" label="是否有效" width="160" sortable
                     :filters="[
        { text: '有效', value: 'Y' },
        { text: '无效', value: 'N' },
      ]"
                     :filter-method="filterTag"
                     filter-placement="bottom-end">
      <template #default="scope">
        <el-tag
            :type="scope.row.isValid === 'Y' ? 'primary' : 'info'"
            disable-transitions
        >{{scope.row.isValid === 'Y' ? '有效' : '无效' }}</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="operate" label="操作" width="250">
      <template #default="scope">
        <el-button type="success" @click="mod(scope.row)">编辑</el-button>
          <el-popconfirm title="Are you sure to delete this?" @confirm="del(scope.row.id)" >
            <template #reference>
              <el-button type="danger" >删除</el-button>
            </template>
          </el-popconfirm>
      </template>
    </el-table-column>


  </el-table>

    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[5, 10, 20, 30]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
    ></el-pagination>
    <el-dialog v-model="centerDialogVisible" title="表单" width="500" center>
      <el-form ref="form" :rules="rules" :model="form" label-width="auto" style="width: 400px;" >
        <el-form-item label="账号" prop="no">
          <el-input v-model="form.no" />
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="性别" >
          <el-radio-group v-model="form.sex">
            <el-radio value="M">男</el-radio>
            <el-radio value="F">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="身份" >
          <el-radio-group v-model="form.roleId">
            <el-radio value="0">超级管理员</el-radio>
            <el-radio value="1">管理员</el-radio>
            <el-radio value="2">普通用户</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model="form.age" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="form.phone" />
        </el-form-item>

        </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="save">确认</el-button>
          <el-button  @click="centerDialogVisible = false">取消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>

</style>
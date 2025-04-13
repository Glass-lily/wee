<script>

import {Expand, Fold, Setting} from "@element-plus/icons-vue";

export default {
  name:"HeaderPart",
  data(){
    return{
      user: JSON.parse(sessionStorage.getItem('CurUser'))
    }
  },
  components: {
    Expand,
    Fold,
    Setting

  },
props:{
  icon:String
},
  methods:{

    toUser(){
      console.log("toUser")
      this.$router.push("/Home")
    },
    toGoOut(){
      console.log("toGoOut")
      this.$confirm('您确定要退出吗?','提示',{
        confirmButtonText:'确定',
        cancelButtonText:'取消',
        type:'warning',
        center:true
      })
          .then(()=>{
            this.$message({
              type:'success',
              message:'退出登录成功'
            })
            this.$router.push("/")
            sessionStorage.clear()
          })
          .catch(()=>{
            this.$message({
              type:'info',
              message:'已取消退出登录'
            })
          })


    },
    collapse(){
      this.$emit('doCollapse')
    }
  },
  created() {
    this.$router.push('/Home')
  }
}
</script>

<template >
  <div style="display: flex;width: 100%;align-content: center; justify-content: space-between" >
    <div style="display: flex; width: 50%; justify-content: space-between; align-items: center;">
      <div style="display: flex; align-items: center;">
        <el-icon size="large" color="#1e1f22" @click="collapse">
          <fold v-if="icon === 'Fold'" />
          <expand v-else />
        </el-icon>
      </div>
      <span style="font-size: 26px; font-weight: 600">欢迎来到知识库！！！</span>
    </div>

  <div style="align-content: center;">
    <span>{{ user.name }}</span>
    <el-dropdown >

      <el-icon style="margin-right: 20px; margin-left: 10px;" size="large" color="#1e1f22">
        <setting />
      </el-icon>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item @click="toUser">个人中心</el-dropdown-item>
          <el-dropdown-item @click="toGoOut">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </div>


  </div>
</template>

<style scoped>

</style>
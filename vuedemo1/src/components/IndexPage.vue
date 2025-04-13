<script >

import AsidePart from "@/components/AsidePart.vue";
import HeaderPart from "@/components/HeaderPart.vue";
// import MainPart from "@/components/MainPart.vue";

export default {
  name:"IndexPage",
  components: {
    // MainPart,
    HeaderPart,
    AsidePart
  },
  methods:{
    doCollapse(){

      this.isCollapse=!this.isCollapse
      if(!this.isCollapse){//展开
          this.aside_width='200px';
        this.icon='Fold'

      }else {//收起
        this.aside_width='64px';
        this.icon='Expand'
      }
    }
  },
  data(){
    return {
      isCollapse:false,
      aside_width:'200px',
      icon:'Fold'

    }
  }


}


</script>
  <template>
    <el-container class="layout-container-demo" style="height: 100vh">
      <el-aside :width="aside_width" >
        <el-scrollbar>

          <AsidePart :isCollapse="isCollapse"></AsidePart>

        </el-scrollbar>
      </el-aside>

      <el-container>
        <el-header style=" font-size: 12px;">
          <div class="toolbar">
            <HeaderPart @doCollapse="doCollapse" :icon="icon"></HeaderPart>
          </div>
        </el-header>
        <el-main>
          <el-scrollbar>
            <router-view/>
          </el-scrollbar>
        </el-main>
      </el-container>
    </el-container>
  </template>



  <style scoped>
    .layout-container-demo .el-header {
      position: relative;
      background-color:#c4e0fd ;
      color: #000000;

    }
    .layout-container-demo .el-aside {
      color: #cd16ff;
      background: #465357;

    }

    .layout-container-demo .el-menu {
      border-right: none;
    }
    .layout-container-demo .el-main {
      padding: 0;
    }
    .layout-container-demo .toolbar {
      display: flex;
      align-items: center;
      justify-content: center;
      height: 100%;
      right: 20px;
    }
  </style>


<script>
import {HomeFilled, Menu, Message,Setting} from "@element-plus/icons-vue";

export default {
  name: "AsidePart",
  components: {HomeFilled, Menu,Message, Setting},
  data() {
    return {
      //isCollapse:false
      menu: [
        {
          menuClick: 'Admin',
          menuName: '管li员管理',
          menuIcon: Message
        }, {
          menuClick: 'User',
          menuName: '用户管理',
          menuIcon: Setting
        }
      ]
    }
  },
  props: {
    isCollapse: Boolean
  }
}
</script>

<template>

  <div>
    <el-menu default-active="/Home" :collapse="isCollapse" router>
      <el-menu-item index="/Home">
        <el-icon>
          <HomeFilled/>
        </el-icon>
        <template #title>
          <span>首页</span>
        </template>
      </el-menu-item>
      <el-menu-item
          :index="`/${item.menuClick}`"
          v-for="item in menu"
          :key="item.id"
      >
        <template #title>
          <el-icon>
            <component :is="item.menuIcon" />
          </el-icon>
          <span> {{ item.menuName }}</span>
        </template>
      </el-menu-item>
<!--      <el-menu-item index="/User">-->
<!--        <template #title>-->
<!--          <el-icon>-->
<!--            <setting/>-->
<!--          </el-icon>-->
<!--          <span> 导航2</span>-->
<!--        </template>-->
<!--      </el-menu-item>-->
<!--      <el-menu-item index="/User">-->
<!--        <template #title>-->
<!--          <el-icon>-->
<!--            <Menu/>-->
<!--          </el-icon>-->
<!--          <span> 导航3</span>-->
<!--        </template>-->
<!--      </el-menu-item>-->

      <!--      <el-sub-menu index="/UserManage">-->
      <!--        <template #title>-->
      <!--          <el-icon>-->
      <!--            <Menu/>-->
      <!--          </el-icon>-->
      <!--          <span> Navigator Two</span>-->
      <!--        </template>-->
      <!--        <el-menu-item-group>-->
      <!--          <template #title>Group 1</template>-->
      <!--          <el-menu-item index="2-1">Option 1</el-menu-item>-->
      <!--          <el-menu-item index="2-2">Option 2</el-menu-item>-->
      <!--        </el-menu-item-group>-->
      <!--        <el-menu-item-group title="Group 2">-->
      <!--          <el-menu-item index="2-3">Option 3</el-menu-item>-->
      <!--        </el-menu-item-group>-->

      <!--      </el-sub-menu>-->


      <!--      <el-sub-menu index="3">-->
      <!--        <template #title>-->
      <!--          <el-icon>-->
      <!--            <setting/>-->
      <!--          </el-icon>-->
      <!--          <span> Navigator Three</span>-->
      <!--        </template>-->
      <!--        <el-menu-item-group>-->
      <!--          <template #title>Group 1</template>-->
      <!--          <el-menu-item index="3-1">Option 1</el-menu-item>-->
      <!--          <el-menu-item index="3-2">Option 2</el-menu-item>-->
      <!--        </el-menu-item-group>-->
      <!--        <el-menu-item-group title="Group 2">-->
      <!--          <el-menu-item index="3-3">Option 3</el-menu-item>-->
      <!--        </el-menu-item-group>-->
      <!--        <el-sub-menu index="3-4">-->
      <!--          <template #title>Option 4</template>-->
      <!--          <el-menu-item index="3-4-1">Option 4-1</el-menu-item>-->
      <!--        </el-sub-menu>-->
      <!--      </el-sub-menu>-->
    </el-menu>
  </div>

</template>

<style scoped>

</style>
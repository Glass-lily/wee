<template>
  <div class="page-container">
    <!-- 查询区域 -->
    <el-card class="search-card">
      <el-form inline>
        <el-form-item label="名字">
          <el-input v-model="name" placeholder="请输入名字" clearable></el-input>
        </el-form-item>
        <el-form-item label="知识大类">
          <el-select v-model="storage" placeholder="请选择">
            <el-option v-for="item in storageData" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="标签">
          <el-select v-model="goodstype" placeholder="请选择">
            <el-option v-for="item in goodstypeData" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadPost">查询</el-button>
          <el-button @click="resetParam">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 表格区域 -->
    <el-card class="table-card">
      <el-button type="success" @click="add">新增知识</el-button>
      <el-table
          :data="tableData"
          border
          stripe
          highlight-current-row
          style="width: 100%; margin-top: 15px"
          @current-change="selectCurrentChange"
      >
        <el-table-column prop="id" label="ID" width="80"></el-table-column>
        <el-table-column prop="name" label="名字"></el-table-column>
        <el-table-column prop="content" label="内容"></el-table-column>
        <el-table-column prop="storagename" label="知识大类" ></el-table-column>
        <el-table-column prop="goodstypename" label="标签" ></el-table-column>
        <el-table-column label="操作" width="240">
          <template #default="scope">
            <el-button size="small" @click="mod(scope.row)" v-if="user.roleId!=2">编辑</el-button>
            <el-button size="small" type="danger" @click="del(scope.row.id)" v-if="user.roleId!=2">删除</el-button>
            <el-button size="small" type="success" @click="see(scope.row.id)">预览</el-button>
            <el-button size="small" type="primary" @click="exportSingleToExcel(scope.row)">导出Excel</el-button>
            <el-button size="small" type="warning" @click="exportSingleToPDF(scope.row)">导出PDF</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination-container">
        <el-pagination
            background
            layout="prev, pager, next, jumper"
            :page-size="pageSize"
            :total="total"
            @current-change="handleCurrentChange"
        />
      </div>
    </el-card>

    <!-- 知识弹窗表单 -->
    <el-dialog v-model="centerDialogVisible" title="知识信息" width="500px">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="名字" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="知识大类" prop="storage">
          <el-select v-model="form.storage" placeholder="请选择">
            <el-option v-for="item in storageData" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="标签" prop="goodstype">
          <el-select v-model="form.goodstype" placeholder="请选择">
            <el-option v-for="item in goodstypeData" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-input v-model="form.content" type="textarea"></el-input>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="centerDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save">保存</el-button>
      </template>
    </el-dialog>

    <!-- 用户选择弹窗 -->
    <SelectUserManagePart v-model:visible="innerVisible" @select="doSelectUser" @confirm="confirmUser" />
  </div>
</template>

<script>
import { ElMessage } from "element-plus";

import * as XLSX from "xlsx";
import jsPDF from "jspdf";
import html2canvas from "html2canvas";

export default {
  name: "HomePage",
  components: {  },
  data() {
    return {
      user: JSON.parse(sessionStorage.getItem("CurUser")),
      goodstypeData: [],
      storageData: [],
      tableData: [],
      pageSize: 10,
      pageNum: 1,
      total: 0,
      name: "",
      storage: "",
      goodstype: "",
      centerDialogVisible: false,
      inDialogVisible: false,
      innerVisible: false,
      currentRow: {},
      tempUser: {},
      form: {
        id: "",
        name: "",
        storage: "",
        content: "",
        goodstype: "",
        storagename:"",
        goodstypename:"",
        count: "",
        remark: ""
      },
      form1: {
        goods: "",
        goodsname: "",
        count: "",
        userid: "",
        username: "",
        admin_id: "",
        remark: "",
        action: "1"
      },
      rules: {
        name: [
          { required: true, message: "请输入姓名", trigger: "blur" },
          { min: 2, max: 8, message: "长度应该在2-8个字符之间", trigger: "blur" }
        ],
        goodstype: [
          { required: true, message: "请选择标签", trigger: "change" }
        ],
        storage: [
          { required: true, message: "请选择知识大类", trigger: "change" }
        ],
        count: [{ required: true, message: "请输入", trigger: "blur" }]
      },
      rules1: {}
    };
  },
  methods: {
    doSelectUser(val) {
      this.tempUser = val;
    },
    confirmUser() {
      this.form1.username = this.tempUser.name;
      this.form1.userid = this.tempUser.id;
      this.innerVisible = false;
    },
    selectCurrentChange(val) {
      this.currentRow = val;
    },
    // formatStorage(row) {
    //   let temp = this.storageData.find(item => item.id == row.storage);
    //   return temp && temp.name;
    // },
    // formatGoodstype(row) {
    //   let temp = this.goodstypeData.find(item => item.id == row.goodstype);
    //   return temp && temp.name;
    // },
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
          .post(this.$httpUrl + "/text/listPage", {
            pageSize: this.pageSize,
            pageNum: this.pageNum,
            param: {
              name: this.name,
              storage: this.storage + "",
              goodstype: this.goodstype + ""
            }
          })
          .then(res => res.data)
          .then(res => {
            if (res.code === 200) {
              this.tableData = res.data;
              this.total = res.total;
            } else {
              ElMessage.error("获取数据失败");
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
      this.name = "";
      this.storage = "";
      this.goodstype = "";
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
          .get(this.$httpUrl + "/goods/del?id=" + id)
          .then(res => res.data)
          .then(res => {
            if (res.code === 200) {
              ElMessage({ message: "操作成功！", type: "success" });
              this.loadPost();
            } else {
              ElMessage.error("操作失败！");
            }
          });
    },
    add() {
      this.centerDialogVisible = true;
      this.$nextTick(() => {
        this.resetForm();
      });
    },
    doInGoods() {
      this.$http
          .post(this.$httpUrl + "/record/save", this.form1)
          .then(res => res.data)
          .then(res => {
            if (res.code === 200) {
              ElMessage({ message: "新增成功！", type: "success" });
              this.inDialogVisible = false;
              this.loadPost();
              this.resetInForm();
            } else {
              ElMessage.error("新增失败！");
            }
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
          console.log("error submit!!");
          return false;
        }
      });
    },
    doMod() {
      this.$http
          .post(this.$httpUrl + "/goods/update", this.form)
          .then(res => res.data)
          .then(res => {
            if (res.code === 200) {
              ElMessage({ message: "更新成功！", type: "success" });
              this.centerDialogVisible = false;
              this.loadPost();
            } else {
              ElMessage.error("更新失败！");
            }
          });
    },
    selectUser() {
      this.innerVisible = true;
    },
    doSave() {
      this.$http
          .post(this.$httpUrl + "/goods/save", this.form)
          .then(res => res.data)
          .then(res => {
            if (res.code === 200) {
              ElMessage({ message: "新增成功！", type: "success" });
              this.centerDialogVisible = false;
              this.loadPost();
            } else {
              ElMessage.error("新增失败！");
            }
          });
    },
    loadStorage() {
      this.$http
          .get(this.$httpUrl + "/storage/list")
          .then(res => res.data)
          .then(res => {
            if (res.code === 200) {
              this.storageData = res.data;
            } else {
              ElMessage.error("获取数据失败");
            }
          });
    },
    loadGoodstype() {
      this.$http
          .get(this.$httpUrl + "/goodstype/list")
          .then(res => res.data)
          .then(res => {
            if (res.code === 200) {
              this.goodstypeData = res.data;
            } else {
              ElMessage.error("获取数据失败");
            }
          });
    },
    exportSingleToExcel(row) {
      const data = [
        ["ID", "名字", "内容", "知识大类", "标签", "创建时间", "更新时间"],
        [
          row.id,
          row.name,
          row.content,
          row.storagename,
          row.goodstypename,
          row.createdAt,
          row.updatedAt
        ]
      ];
      const worksheet = XLSX.utils.aoa_to_sheet(data);
      const workbook = XLSX.utils.book_new();
      XLSX.utils.book_append_sheet(workbook, worksheet, "知识详情");
      XLSX.writeFile(workbook, `${row.name}_知识详情.xlsx`);
    },
    exportSingleToPDF(row) {
      const tempDiv = document.createElement("div");
      tempDiv.style.padding = "20px";
      tempDiv.style.background = "#fff";
      tempDiv.innerHTML = `
        <h2>知识详情</h2>
        <p><strong>ID:</strong> ${row.id}</p>
        <p><strong>名字:</strong> ${row.name}</p>
        <p><strong>内容:</strong> ${row.content}</p>
        <p><strong>知识大类:</strong> ${this.formatStorage(row)}</p>
        <p><strong>标签:</strong> ${this.formatGoodstype(row)}</p>
        <p><strong>创建时间:</strong> ${row.createdAt}</p>
        <p><strong>更新时间:</strong> ${row.updatedAt}</p>
      `;
      document.body.appendChild(tempDiv);

      html2canvas(tempDiv).then(canvas => {
        const imgData = canvas.toDataURL("image/png");
        const pdf = new jsPDF();
        const imgProps = pdf.getImageProperties(imgData);
        const pdfWidth = pdf.internal.pageSize.getWidth();
        const pdfHeight = (imgProps.height * pdfWidth) / imgProps.width;
        pdf.addImage(imgData, "PNG", 0, 0, pdfWidth, pdfHeight);
        pdf.save(`${row.name}_知识详情.pdf`);
        document.body.removeChild(tempDiv);
      });
    }
  },

  beforeMount() {
    this.loadStorage();
    this.loadGoodstype();
    this.loadPost();
  }
};
</script>

<style scoped>
.page-container {
  padding: 20px;
  background: #f5f7fa;
}

.search-card,
.table-card {
  margin-bottom: 20px;
}

.pagination-container {
  text-align: right;
  margin-top: 15px;
}

.el-dialog__body {
  padding: 20px 30px;
}
</style>

<template>
  <div class="page-container">
    <!-- 查询区域 -->
    <el-card class="search-card">
      <el-form inline>
        <el-form-item label="名字">
          <el-input v-model="name" placeholder="请输入名字" clearable @keyup.enter="loadPost"></el-input>
        </el-form-item>
        <el-form-item label="内容">
          <el-input v-model="content" placeholder="请输入内容" clearable @keyup.enter="loadPost"></el-input>
        </el-form-item>
        <el-form-item label="知识大类">
          <el-select v-model="storage" placeholder="请选择" clearable class="wide-select">
            <el-option v-for="item in storageData" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="标签">
          <el-select v-model="goodstype" placeholder="请选择" clearable class="wide-select">
            <el-option v-for="item in goodstypeData" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadPost" :loading="loading">查询</el-button>
          <el-button @click="resetParam">重置</el-button>
          <el-button type="warning" @click="openSensitiveWordsDialog"  v-if="user.roleId != 2">管理敏感词</el-button>
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
        <el-table-column prop="name" label="名字" show-overflow-tooltip></el-table-column>
        <el-table-column
            prop="content"
            label="内容"
            width="200"
            class-name="ellipsis-cell"
        ></el-table-column>
        <el-table-column prop="storagename" label="知识大类"></el-table-column>
        <el-table-column prop="goodstypename" label="标签"></el-table-column>
        <el-table-column prop="remark" label="备注"></el-table-column>

        <el-table-column label="操作" width="240">
          <template #default="scope">
            <el-button size="small" @click="mod(scope.row)" v-if="user.roleId != 2">编辑</el-button>
            <el-button size="small" type="danger" @click="del(scope.row.id)" v-if="user.roleId != 2">删除</el-button>
            <el-button size="small" type="success" @click="see(scope.row.id)">预览</el-button>
            <el-button size="small" type="primary" @click="exportSingleToExcel(scope.row)">导出Excel</el-button>
            <el-button size="small" type="warning" @click="exportSingleToPDF(scope.row)">导出PDF</el-button>
            <el-button size="small" type="success" @click="shareK(scope.row)">分享</el-button>
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
          <el-input v-model="form.content" type="textarea" :rows="5"></el-input>
        </el-form-item>


          <el-form-item label="图片" prop="remark">
            <el-upload
                v-model="form.remark"
                class="upload-demo"
                :action="uploadUrl"
                :data="{ id: form.id }"
                list-type="picture-card"
                :file-list="fileList"
                :before-upload="beforeUpload"
                :on-success="handleUploadSuccess"
                :on-error="handleUploadError"
                :on-preview="handlePreview"
            >
              <i class="el-icon-plus"></i>
            </el-upload>
          </el-form-item>

          <!-- 预览弹窗 -->
          <el-dialog v-model:visible="previewVisible" width="40%">
            <img width="100%" :src="previewImage" alt="图片预览" />
          </el-dialog>

      </el-form>

      <template #footer>
        <el-button @click="centerDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save">保存</el-button>
      </template>
    </el-dialog>

    <!-- 预览弹窗 -->
    <el-dialog v-model="previewDialogVisible" title="知识内容预览" width="1200px">
      <div class="previewImage">
        <el-descriptions>
          <el-descriptions-item label="图片"  v-model:visible="previewVisible" width="40%"  >
            <el-form-item label="图片" prop="remark">
              <el-upload
                  v-model="form.remark"
                  class="upload-demo"
                  :action="uploadUrl"
                  :data="{ id: form.id }"
                  list-type="picture-card"
                  :file-list="fileList"
              >
                <i class="el-icon-plus"></i>
              </el-upload>
            </el-form-item>
          </el-descriptions-item>
        </el-descriptions>
      </div>
      <div class="preview-content">
        <el-descriptions>
          <el-descriptions-item label="内容">
            <div class="content-scroll">{{ previewData.content }}</div>
          </el-descriptions-item>
        </el-descriptions>
      </div>
      <template #footer>
        <el-button @click="previewDialogVisible = false">关闭</el-button>
      </template>
    </el-dialog>



    <!-- 敏感词管理弹窗 -->
    <el-dialog v-model="sensitiveWordsDialogVisible" title="管理敏感词" width="500px">
      <el-table :data="sensitiveWords" border style="width: 100%">
        <el-table-column prop="word" label="敏感词" width="200"></el-table-column>
        <el-table-column label="操作" width="120">
          <template #default="scope">
            <el-button size="small" type="danger" @click="deleteSensitiveWord(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-form :model="newSensitiveWord" label-width="80px" style="margin-top: 20px">
        <el-form-item label="添加新敏感词">
          <el-input v-model="newSensitiveWord.word" placeholder="请输入敏感词"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="addSensitiveWord">添加</el-button>
          <el-button @click="sensitiveWordsDialogVisible = false">关闭</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>


    <!-- 分享链接弹窗 -->
    <el-dialog v-model="shareDialogVisible" title="分享链接" width="500px">
      <el-form>
        <el-form-item label="分享链接">
          <el-input v-model="shareLink" readonly></el-input>
        </el-form-item>
        <template #footer>
          <el-button @click="shareDialogVisible = false">关闭</el-button>
        </template>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { ElMessage } from "element-plus";
import * as XLSX from "xlsx";
import jsPDF from "jspdf";
import html2canvas from "html2canvas";
// import {ref} from "vue";

// const imageUrl = ref(""); // 用于存储选择的图片的URL
export default {
  name: "HomePage",

  data() {
    return {
      user: JSON.parse(sessionStorage.getItem("CurUser")),
      shareDialogVisible: false, // 控制分享弹窗的显示
      shareLink: '', // 存储生成的分享链接
      uploadUrl: 'http://localhost:8080/goods/uploadImage', // 后端接口 URL
      fileList: [],          // 上传组件的列表
      previewImage: '',    // 预览时的图片 URL
      previewVisible: false,
      sensitiveWords: [], // 敏感词列表（从后端获取）
      sensitiveWordsDialogVisible: false, // 控制敏感词管理对话框的显示
      newSensitiveWord: {
        word: "" // 用于输入新的敏感词
      },
      goodstypeData: [],
      storageData: [],
      tableData: [],
      pageSize: 10,
      pageNum: 1,
      total: 0,
      name: "",
      content: "",
      remark:'', // 保存上传后的图片 URL
      storage: "",
      goodstype: "",
      previewDialogVisible: false,
      previewData: {},
      centerDialogVisible: false,
      currentRow: {},
      loading: false,
      form: {
        id: "",
        name: "",
        storage: "",
        content: "",
        goodstype: "",
        storagename: "",
        goodstypename: "",
        remark: ''
      },
      rules: {
        name: [
          { required: true, message: "请输入姓名", trigger: "blur" },
        ],
        goodstype: [
          { required: true, message: "请选择标签", trigger: "change" }
        ],
        storage: [
          { required: true, message: "请选择知识大类", trigger: "change" }
        ],
        content: [
          { required: true, message: "请输入内容", trigger: "blur" }
        ]
      }
    };
  },
  computed: {
    // 构造后端 uploadImage 接口地址
    // eslint-disable-next-line vue/no-dupe-keys
    uploadUrl() {
      return `${this.$httpUrl}/goods/uploadImage`;
    }
  },
  methods: {
    // 分享功能
    shareK(row) {
      const shareUrl = `${window.location.origin}/share/${row.id}`;  // 生成分享链接
      console.log("分享链接：", shareUrl);  // 可以在控制台查看生成的分享链接
      // 你可以在这里弹出分享链接的弹窗或直接显示
      this.$message.success(`分享链接: ${shareUrl}`);
    },
    // 限制格式与大小
    beforeUpload(file) {
      const isImg = file.type.startsWith('image/');
      const okSize = file.size / 1024 / 1024 < 2;
      if (!isImg) this.$message.error('只能上传图片');
      if (!okSize) this.$message.error('请上传小于 2MB 的图片');
      return isImg && okSize;
    },
    // 上传成功时，将后端返回的 URL 写入 form.remark，并更新 file.url
    handleUploadSuccess(res, file) {
      if (res.code === 200) {
        // 后端返回的 res.data = "/images/xxx.jpg"
        const url = res.data;
        // 存到表单里，后续 save 会把它一起提交
        this.form.remark = url;
        // 拼成完整 URL，给 el-upload 预览用
        // 假设 this.$httpUrl = 'http://127.0.0.1:8080'
        file.url = `${this.$httpUrl}${url}`;
        this.$message.success('上传成功');
      } else {
        this.$message.error('上传失败');
      }
    },
    handleUploadError() {
      this.$message.error('上传出错');
    },
    // 点击预览时
    handlePreview(file) {
      this.previewImage = file.url || this.form.remark;
      this.previewVisible = true;
    },
    // 打开敏感词管理弹窗
    openSensitiveWordsDialog() {
      this.sensitiveWordsDialogVisible = true;
      this.fetchSensitiveWords(); // 获取敏感词列表
    },

    // 获取敏感词列表
    async fetchSensitiveWords() {
      try {
        const res = await this.$http.get(this.$httpUrl + '/sensitive/list');
        if (res.data.code === 200) {
          this.sensitiveWords = res.data.data;
        } else {
          ElMessage.error("获取敏感词失败");
        }
      } catch (error) {
        ElMessage.error("请求失败：" + error.message);
      }
    },

    // 添加新的敏感词
    async addSensitiveWord() {
      if (!this.newSensitiveWord.word) {
        ElMessage.error("敏感词不能为空！");
        return;
      }

      try {
        const res = await this.$http.post(this.$httpUrl + "/sensitive/add", {
          word: this.newSensitiveWord.word
        });

        if (res.data.code === 200) {
          ElMessage.success("敏感词添加成功！");
          this.newSensitiveWord.word = ""; // 清空输入框
          await this.fetchSensitiveWords(); // 重新加载敏感词列表
        } else {
          ElMessage.error("添加敏感词失败：" + res.data.msg);
        }
      } catch (error) {
        ElMessage.error("请求失败：" + error.message);
      }
    },

    // 删除敏感词
    async deleteSensitiveWord(sensitiveWord) {
      try {
        const res = await this.$http.delete(this.$httpUrl + "/sensitive/delete/" + sensitiveWord.id);

        if (res.data.code === 200) {
          ElMessage.success("敏感词删除成功！");
          await this.fetchSensitiveWords(); // 重新加载敏感词列表
        } else {
          ElMessage.error("删除敏感词失败：" + res.data.msg);
        }
      } catch (error) {
        ElMessage.error("请求失败：" + error.message);
      }
    },

    containsSensitiveWords(text) {
      return this.sensitiveWords.some(word => text.includes(word));
    },

    see(id) {
      this.$http.get(this.$httpUrl + "/text/detail?id=" + id)
          .then(res => res.data)
          .then(res => {
            if (res.code === 200) {
              this.previewData = res.data;
              this.previewDialogVisible = true;
            } else {
              ElMessage.error("获取详情失败");
            }
          });
    },
    selectCurrentChange(val) {
      this.currentRow = val;
    },
    handleCurrentChange(val) {
      this.pageNum = val;
      this.loadPost();
    },
    async loadPost() {
      this.loading = true;
      try {
        const res = await this.$http.post(this.$httpUrl + "/text/listPage", {
          pageSize: this.pageSize,
          pageNum: this.pageNum,
          param: {
            name: this.name,
            content: this.content,
            storage: this.storage || "",
            goodstype: this.goodstype || "",
            remark:this.remark
          }
        }).then(res => res.data);
        if (res.code === 200) {
          this.tableData = res.data;
          this.total = res.total;
        } else {
          ElMessage.error("获取数据失败");
        }
      } catch (err) {
        ElMessage.error("请求失败：" + err.message);
      } finally {
        this.loading = false;
      }
    },
    resetForm() {
      this.form = {
        id: null,
        name: "",
        storage: "",
        content: "",
        goodstype: "",
        storagename: "",
        goodstypename: "",
        remark: ""
      };
      this.$nextTick(() => {
        if (this.$refs.form) {
          this.$refs.form.clearValidate();
        }
      });
    },
    resetParam() {
      this.name = "";
      this.content = "";
      this.storage = "";
      this.goodstype = "";
      this.pageNum = 1;
      this.loadPost();
    },
    async mod(row) {
      // 分类/标签加载逻辑
      if (!this.storageData.length || !this.goodstypeData.length) {
        await Promise.all([this.loadStorage(), this.loadGoodstype()]);
      }

      // 打开弹窗
      this.centerDialogVisible = true;
      this.$nextTick(() => {
        this.form.id = row.id || "";
        this.form.name = row.name || "";
        // 找到并赋 storage
        const storageItem = this.storageData.find(item => item.id == row.storage || item.name === row.storagename);
        this.form.storage = storageItem ? storageItem.id : "";
        // 找到并赋 goodstype
        const goodstypeItem = this.goodstypeData.find(item => item.id == row.goodstype || item.name === row.goodstypename);
        this.form.goodstype = goodstypeItem ? goodstypeItem.id : "";
        this.form.content = row.content || "";
        this.form.remark = row.remark || "";

        // 清空并初始化 fileList
        this.fileList = [];
        if (this.form.remark) {
          this.fileList.push({
            name: this.form.remark.split('/').pop(),
            url: `${this.$httpUrl}${this.form.remark}`
          });
        }
        // 校验提示
        if (!this.form.storage || !this.form.goodstype) {
          ElMessage.warning("知识大类或标签数据无效，请检查！");
        }
      });

    },
    async add() {
      if (!this.storageData.length || !this.goodstypeData.length) {
        await Promise.all([this.loadStorage(), this.loadGoodstype()]);
      }
      this.centerDialogVisible = true;
      this.$nextTick(() => {
        this.resetForm();
      });
    },
    async del(id) {
      try {
        const [goodsRes, textRes] = await Promise.all([
          this.$http.get(this.$httpUrl + "/goods/del?id=" + id).then(res => res.data),
          this.$http.get(this.$httpUrl + "/text/del?id=" + id).then(res => res.data)
        ]);
        if (goodsRes.code === 200 && textRes.code === 200) {
          ElMessage({message: "操作成功！", type: "success"});
          this.loadPost();
        } else {
          ElMessage({message: "操作成功！", type: "success"});
          //ElMessage.error("操作失败！");
          this.loadPost();
        }
      } catch (error) {
        console.error(error);
        ElMessage.error("请求出错！");
      }
    },
    // save() {
    //   if (this.containsSensitiveWords(this.form.name) || this.containsSensitiveWords(this.form.content)) {
    //     this.$message.error('包含敏感词，无法提交');
    //     return;
    //   }
    //   this.$refs.form.validate(valid => {
    //     if (valid) {
    //       if (this.form.id) {
    //         this.doMod();
    //       } else {
    //         this.doSave();
    //       }
    //     } else {
    //       ElMessage.error("请填写所有必填字段！");
    //       return false;
    //     }
    //   });
    // },
    // 覆盖 save 方法，确保 remark 带上图片地址
    save() {
      this.$refs.form.validate(valid => {
        if (!valid) return;
        // doSave 内会把 form.remark 一并提交到 /goods/save
        this.doSave();
      });
    },
    doMod() {
      const updateData = {
        id: this.form.id,
        name: this.form.name,
        storage: this.form.storage,
        goodstype: this.form.goodstype,
        content: this.form.content,
        remark: this.form.remark || ""
      };
      if (!updateData.id || !updateData.name || !updateData.storage || !updateData.goodstype || !updateData.content) {
        ElMessage.error("请填写所有必填字段！");
        return;
      }
      Promise.all([
        this.$http.post(this.$httpUrl + "/goods/update", {
          id: updateData.id,
          name: updateData.name,
          storage: updateData.storage,
          goodstype: updateData.goodstype,
          remark: updateData.remark
        }).then(res => res.data),
        this.$http.post(this.$httpUrl + "/text/update", updateData).then(res => res.data)
      ])
          .then(([goodsRes, textRes]) => {
            if (goodsRes.code === 200 && textRes.code === 200) {
              ElMessage({message: "更新成功！", type: "success"});
              this.centerDialogVisible = false;
              this.loadPost();
            } else {
              const errorMsg = [];
              if (goodsRes.code !== 200) {
                errorMsg.push(`更新 goods 失败: ${goodsRes.msg || '未知错误'}`);
              }
              if (textRes.code !== 200) {
                errorMsg.push(`更新 text 失败: ${textRes.msg || '未知错误'}`);
              }
              ElMessage.error(errorMsg.join('；'));
            }
          })
          .catch(err => {
            ElMessage.error("请求失败：" + err.message);
          });
    },
    doSave() {
      const saveData = {
        name: this.form.name,
        storage: this.form.storage,
        goodstype: this.form.goodstype,
        content: this.form.content,
        remark: this.form.remark || ""
      };
      Promise.all([
        this.$http.post(this.$httpUrl + "/goods/save", saveData).then(res => res.data),
        this.$http.post(this.$httpUrl + "/text/save", saveData).then(res => res.data)
      ])
          .then(([goodsRes, textRes]) => {
            if (goodsRes.code === 200 && textRes.code === 200) {
              ElMessage({message: "新增成功！", type: "success"});
              this.centerDialogVisible = false;
              this.loadPost();
            } else {
              ElMessage.error("新增失败！请检查数据是否重复或服务器异常。");
            }
          })
          .catch(err => {
            if (err.message.includes("Duplicate entry")) {
              ElMessage.error("新增失败：记录已存在，请检查输入数据！");
            } else {
              ElMessage.error("请求失败：" + err.message);
            }
          });
    },
    loadStorage() {
      return this.$http
          .get(this.$httpUrl + "/storage/list")
          .then(res => res.data)
          .then(res => {
            if (res.code === 200) {
              this.storageData = res.data;
            } else {
              ElMessage.error("获取知识大类失败");
            }
          });
    },
    loadGoodstype() {
      return this.$http
          .get(this.$httpUrl + "/goodstype/list")
          .then(res => res.data)
          .then(res => {
            if (res.code === 200) {
              this.goodstypeData = res.data;
            } else {
              ElMessage.error("获取标签失败");
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
      tempDiv.style.width = "800px";
      tempDiv.style.fontFamily = "Arial, sans-serif";
      tempDiv.innerHTML = `
        <h2 style="font-size: 18px; margin-bottom: 10px;">知识详情</h2>
        <div style="display: flex; margin-bottom: 10px;">
          <strong style="width: 100px; flex-shrink: 0;">ID:</strong>
          <div style="flex: 1; font-size: 14px;">${row.id}</div>
        </div>
        <div style="display: flex; margin-bottom: 10px;">
          <strong style="width: 100px; flex-shrink: 0;">名字:</strong>
          <div style="flex: 1; font-size: 14px;">${row.name}</div>
        </div>
        <div style="display: flex; margin-bottom: 10px;">
          <strong style="width: 100px; flex-shrink: 0;">内容:</strong>
          <div style="flex: 1; white-space: pre-wrap; word-break: break-all; font-size: 14px;">
            ${row.content.replace(/</g, '&lt;').replace(/>/g, '&gt;')}
          </div>
        </div>
        <div style="display: flex; margin-bottom: 10px;">
          <strong style="width: 100px; flex-shrink: 0;">知识大类:</strong>
          <div style="flex: 1; font-size: 14px;">${row.storagename}</div>
        </div>
        <div style="display: flex; margin-bottom: 10px;">
          <strong style="width: 100px; flex-shrink: 0;">标签:</strong>
          <div style="flex: 1; font-size: 14px;">${row.goodstypename}</div>
        </div>
        <div style="display: flex; margin-bottom: 10px;">
          <strong style="width: 100px; flex-shrink: 0;">创建时间:</strong>
          <div style="flex: 1; font-size: 14px;">${row.createdAt || ''}</div>
        </div>
        <div style="display: flex; margin-bottom: 10px;">
          <strong style="width: 100px; flex-shrink: 0;">更新时间:</strong>
          <div style="flex: 1; font-size: 14px;">${row.updatedAt || ''}</div>
        </div>
      `;
      document.body.appendChild(tempDiv);

      html2canvas(tempDiv, {scale: 2}).then(canvas => {
        const imgData = canvas.toDataURL("image/png");
        const pdf = new jsPDF('p', 'mm', 'a4');
        const pdfWidth = pdf.internal.pageSize.getWidth();
        const pdfHeight = pdf.internal.pageSize.getHeight();
        const imgProps = pdf.getImageProperties(imgData);
        const imgHeight = (imgProps.height * pdfWidth) / imgProps.width;

        let heightLeft = imgHeight;
        let position = 0;

        pdf.addImage(imgData, "PNG", 0, position, pdfWidth, imgHeight);
        heightLeft -= pdfHeight;

        while (heightLeft > 0) {
          position = heightLeft - imgHeight;
          pdf.addPage();
          pdf.addImage(imgData, "PNG", 0, position, pdfWidth, imgHeight);
          heightLeft -= pdfHeight;
        }

        pdf.save(`${row.name}_知识详情.pdf`);
        document.body.removeChild(tempDiv);
      });
    }
  },
  beforeMount() {
    this.loadStorage();
    this.loadGoodstype();
    this.loadPost();
    this.fetchSensitiveWords(); // 拉取敏感词
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

::v-deep .ellipsis-cell .cell {
  max-width: 150px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.preview-content {
  max-height: 400px;
  overflow-y: auto;
  padding: 10px;
  border: 1px solid #ebeef5;
}

.content-scroll {
  white-space: pre-wrap;
  word-break: break-all;
}

.wide-select {
  width: 100px;
}

.sensitive-table {
  margin-top: 20px;
}

.el-dialog__body {
  padding: 20px;
}

.el-button--primary {
  margin-left: 10px;
}

</style>
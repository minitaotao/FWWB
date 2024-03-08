<script setup lang="ts">
import { http } from "/@/utils/http";
import { ref, onMounted } from "vue";
import { errorMessage } from "/@/utils/message";
let bankbalance = ref(0);
onMounted(() => {
  http
    .get("/api/admin/bankbalance")
    .then(({ data }) => {
      bankbalance.value = data;
    })
    .catch((err: any) => {
      errorMessage(err.response.data.message);
    });
});
</script>
<template>
  <div>
    <el-row :gutter="24">
      <el-col
        :xs="24"
        :sm="24"
        :md="12"
        :lg="12"
        :xl="12"
        style="margin-bottom: 20px"
      >
        <el-card>
          银行账户
          <el-form
            ><el-form-item label="资产">{{ bankbalance }}</el-form-item>
          </el-form>
        </el-card> </el-col
      ><el-col
        :xs="24"
        :sm="24"
        :md="12"
        :lg="12"
        :xl="12"
        style="margin-bottom: 20px"
      >
        <el-card>流水</el-card></el-col
      ></el-row
    >
  </div>
</template>

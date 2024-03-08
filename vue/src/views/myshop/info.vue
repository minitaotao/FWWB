<script setup lang="ts">
import { http } from "/@/utils/http";
import { errorMessage, successMessage } from "/@/utils/message";
import { ref, computed } from "vue";
let name = ref("123");
let age = ref(0);
let phone = ref(0);
let id_card = ref("");
let income = ref(0);
let defaulter = ref(0);
let working_status = ref(0);
let balance = ref(0);
http
  .get("/api/user/information")
  .then(({ data }) => {
    name.value = data.name;
    phone.value = data.phone_num;
    age.value = data.age;
    id_card.value = data.id_card;
    income.value = data.income;
    defaulter.value = data.defaulter;
    working_status.value = data.working_status;
    balance.value = data.balance;
  })
  .catch(err => {
    errorMessage(err.response.data.message);
  });
const submit = () => {
  let params = new URLSearchParams();
  params.append("age", age.value.toString());
  params.append("income", income.value.toString());
  params.append("working_status", working_status.value.toString());
  http
    .post("/api/user/completeInfo", {
      data: params.toString()
    })
    .then(({ status, message }) => {
      if (status == 200) {
        successMessage("修改成功:" + message);
      } else {
        errorMessage("修改失败:" + message);
      }
    })
    .catch(err => {
      errorMessage(err.response.data.message);
    });
};
let Formatdefaulter = computed(() => {
  if (defaulter.value != 0) {
    return "是";
  } else {
    return "否";
  }
});
</script>
<template>
  <div>
    <el-card>
      <el-descriptions title="个人信息" border :column="2">
        <el-descriptions-item>
          <template #label>
            <div>
              <IconifyIconOffline icon="fa-user" />
              姓名
            </div>
          </template>
          {{ name }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label
            ><div>
              <IconifyIconOffline icon="fa-calendar" />
              年龄
            </div></template
          >
          <el-input v-model="age" />
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label
            ><div>
              <IconifyIconOffline icon="fa-address-card" />
              身份证
            </div></template
          >
          {{ id_card }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label
            ><div>
              <IconifyIconOffline icon="fa-phone" />
              电话
            </div></template
          >
          {{ phone }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label> 月收入 </template>
          <el-input v-model="income" />
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>失信人员</template>
          {{ Formatdefaulter }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>工作状态</template>
          <el-select v-model="working_status">
            <el-option key="1" :value="1" label="就业" /><el-option
              key="0"
              :value="0"
              label="失业"
          /></el-select>
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>账户余额</template>
          {{ balance }}</el-descriptions-item
        ></el-descriptions
      >
      <el-button @click="submit" style="margin-top: 20px" type="primary"
        >提交更改</el-button
      >
    </el-card>
  </div>
</template>
<style lang="scss" scoped></style>

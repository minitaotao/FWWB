<script setup lang="ts">
import { http } from "/@/utils/http";
import { useRoute } from "vue-router";
import { ref, computed } from "vue";
const route = useRoute();
const order_number = route.query?.order_number;
let ord: any = ref({});
http
  .get("/api/dashboard/order?order_number=" + order_number)
  .then(({ data }) => {
    ord.value = data;
  });
let buyTime = computed(() => {
  var t = new Date(ord.value.date * 1000);
  return t.toLocaleString();
});
</script>
<template>
  <div>
    <el-card>
      <el-form>
        <el-form-item label="订单号">
          {{ order_number }}
        </el-form-item>
        <el-form-item label="理财名称">
          {{ ord.name }}
        </el-form-item>
        <el-form-item label="购买时间">
          {{ buyTime }}
        </el-form-item>
        <el-form-item label="数量">
          {{ ord.count }}
        </el-form-item>
        <el-form-item label="单价">
          {{ ord.price }}
        </el-form-item>
        <el-form-item label="合计">
          {{ ord.price * ord.count }}
        </el-form-item>
        <el-form-item label="商品简述">
          {{ ord.description }}
        </el-form-item>
        <el-form-item label="商品详细信息" />
        <div v-html="ord.detail" />
      </el-form>
    </el-card>
  </div>
</template>

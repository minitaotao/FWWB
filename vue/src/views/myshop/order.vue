<script setup lang="ts">
import { http } from "/@/utils/http";
import { ref } from "vue";
import { useMultiTagsStoreHook } from "/@/store/modules/multiTags";
import { useRouter, useRoute } from "vue-router";
const router = useRouter();
const route = useRoute();

interface order {
  date: number;
  orderNumber: number;
  name: string;
  price: number;
  description: string;
  itemId: number;
  count: number;
}
let orders = ref([]);
let form = ref([]);
http.get("/api/dashboard/orderlist").then(({ data }) => {
  orders.value = data;
  form.value = [];
  orders.value.forEach((ord: order) => {
    let t = new Date(ord.date * 1000);
    form.value.push({
      date: t.toLocaleString(),
      order_number: ord.orderNumber,
      name: ord.name,
      price: ord.price,
      description: ord.description,
      itemId: ord.itemId,
      count: ord.count,
      total: ord.count * ord.price
    });
  });
});
function OrderDetail(row) {
  useMultiTagsStoreHook().handleTags("push", {
    path: `/myshop/orderdetail`,
    parentPath: route.matched[0].path,
    name: "myorderdetail",
    query: { order_number: row.order_number },
    meta: {
      title: {
        zh: `${row.name} - 我的理财详情`,
        en: `${row.name} - My Order Detail`
      },
      showLink: false,
      i18n: true,
      dynamicLevel: 3
    }
  });
  router.push({
    name: "myorderdetail",
    query: { order_number: row.order_number }
  });
}
</script>
<template>
  <div class="orderlist">
    <el-card>
      <el-table :data="form" border stripe @row-dblclick="OrderDetail">
        <el-table-column prop="date" label="购买日期" sortable />
        <el-table-column
          prop="order_number"
          width="200px"
          label="流水号"
          sortable
        />
        <el-table-column prop="name" label="商品名称" />
        <el-table-column prop="price" label="单价" />
        <el-table-column prop="count" label="数量" />
        <el-table-column prop="total" label="合计" />
      </el-table>
    </el-card>
  </div>
</template>

<style type="scss">
.orderlist {
  height: 100%;
}
</style>

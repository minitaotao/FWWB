<script setup lang="ts">
import { http } from "/@/utils/http";
import { ref, computed } from "vue";
import { errorMessage } from "/@/utils/message";

let orders = ref([]);
let itemlist = ref([]);
let selectedItem = ref();
let form = computed(() => {
  let f: Array<any> = [];
  for (var ord of orders.value) {
    var it = getItem(ord.item_id);
    var t = new Date(ord.date * 1000);
    f.push({
      order_number: ord.order_number,
      name: it.name,
      price: it.price,
      buyer_id: ord.buyer_id,
      description: it.description,
      date: t.toLocaleString()
    });
  }
  return f;
});
let timerange = ref([]);
http
  .get("/api/admin/orderlist")
  .then(({ data }) => {
    orders.value = data;
  })
  .catch(err => {
    errorMessage(err.response.data.message);
  });
http
  .get("/api/shop/itemlist")
  .then(({ data }) => {
    itemlist.value = data;
  })
  .catch(err => {
    errorMessage(err.response.data.message);
  });
function getItem(id: number) {
  for (let it of itemlist.value) {
    if (it.id == id) {
      return it;
    }
  }
  return null;
}
const query = () => {
  let params = {};
  if (selectedItem.value != 0) {
    params["item_id"] = selectedItem.value;
  }
  if (timerange.value != null && timerange.value.length == 2) {
    params["beginDate"] = Math.floor(timerange.value[0].valueOf() / 1000);
    params["endDate"] = Math.floor(timerange.value[1].valueOf() / 1000);
  }
  http
    .get("/api/admin/orderlist", {
      params: params
    })
    .then(({ data }) => {
      orders.value = data;
    })
    .catch(err => {
      errorMessage("查询失败：" + err.response.data.message);
    });
};
function OrderDetail() {
  console.log(this);
}
</script>
<template>
  <div class="orderlist">
    <el-card>
      <div class="selectBar">
        <span style="margin-right: 20px">商品</span
        ><el-select v-model="selectedItem" clearable>
          <el-option
            v-for="item in itemlist"
            :key="item.id"
            :label="item.name"
            :value="item.id"
        /></el-select>
        <span
          style="margin-right: 20px; margin-left: 20px"
          range-separator="To"
          start-placeholder="开始时间"
          end-placeholder="结束时间"
          format="x"
          >查询时间区间</span
        >
        <el-date-picker type="datetimerange" v-model="timerange" />
        <div>
          <el-button type="primary" @click="query" style="margin-top: 20px"
            >查询</el-button
          >
        </div>
      </div>
      <el-divider />
      <el-table :data="form" border stripe @row-click="OrderDetail">
        <el-table-column prop="date" label="购买日期" sortable />
        <el-table-column
          prop="order_number"
          width="200px"
          label="流水号"
          sortable
        />
        <el-table-column prop="name" label="商品名称" />
        <el-table-column prop="price" label="价值" />
        <el-table-column prop="discription" label="简述" />
        <el-table-column prop="buyer_id" label="用户ID" /> </el-table
    ></el-card>
  </div>
</template>

<style type="scss">
.orderlist {
  height: 100%;
}
</style>

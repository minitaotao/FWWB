<script setup lang="ts">
import { useRouter, useRoute } from "vue-router";
import { http } from "/@/utils/http";
import { formatTimeDuring, FormatTime } from "/@/utils/shop";
import { ref, computed } from "vue";
import { errorMessage, successMessage } from "/@/utils/message";

const router = useRouter();
const route = useRoute();
const id = route.query?.id;

let good = ref({
  price: 1,
  id: 1,
  name: "1",
  stock: 1,
  totalstock: 1,
  description: "1",
  detail: "1",
  beginDate: 1,
  endDate: 1
});
let TimeOfShop = ref("");

http.get("/api/shop/item?id=" + id).then(({ data }) => {
  good.value = data;
});
setInterval(() => {
  let t = new Date();
  let dur1 = good.value.beginDate.valueOf() - t.valueOf() / 1000;
  let dur2 = good.value.endDate.valueOf() - t.valueOf() / 1000;
  if (dur1 > 0) {
    TimeOfShop.value = "距离秒杀开始还有:" + formatTimeDuring(dur1);
  } else if (dur1 <= 0 && dur2 >= 0) {
    TimeOfShop.value = "秒杀还有" + formatTimeDuring(dur2) + "结束";
  } else {
    TimeOfShop.value = "秒杀已经结束";
  }
}, 1000);

function buy() {
  http
    .get("/api/shop/item/getUrl?id=" + id)
    .then(({ data, status, message }) => {
      if (status == 200) {
        http
          .post("/api/shop/item/execution/", { params: { md5: data.md5 } })
          .then(({ data, status, message }) => {
            if (status != 200) {
              errorMessage("秒杀失败" + message);
              return;
            }
            successMessage(data);
          })
          .catch(error => {
            errorMessage("秒杀失败:" + error.response.data.message);
            return;
          });
      } else if (status == 520) {
        errorMessage("秒杀失败:" + message);
        router.push("/myshop/info");
      } else {
        errorMessage("秒杀失败:" + message);
      }
    });
  //刷新商品
  http.get("/api/shop/item?id=" + id).then(({ data }) => {
    good.value = data;
  });
}
let disabled = computed(() => {
  let t = new Date();
  if (
    t.valueOf() / 1000 < good.value.beginDate ||
    t.valueOf() / 1000 > good.value.endDate
  ) {
    return true;
  }
  return false;
});
</script>

<template>
  <div class="itemDetail">
    <el-card>
      <h1>{{ good.name }}</h1>
      <el-divider />
      <el-progress
        type="circle"
        :percentage="
          Math.floor(100 * ((good.totalstock - good.stock) / good.totalstock))
        "
      >
        {{ good.totalstock - good.stock }} / {{ good.totalstock }}
      </el-progress>
      <div>理财单价：{{ good.price }}</div>
      <div v-html="good.detail" />
      <div>
        秒杀时间:{{ FormatTime(good.beginDate) }} -
        {{ FormatTime(good.endDate) }}
      </div>
      <div>{{ TimeOfShop }}</div>
      <el-button @click="buy" type="primary" size="large" :disabled="disabled"
        >秒杀</el-button
      >
    </el-card>
  </div>
</template>

<style lang="scss" scoped>
.itemDetail {
  height: 100%;
}
</style>

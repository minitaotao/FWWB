<script setup lang="ts">
import { http } from "/@/utils/http";
import { useRouter, useRoute } from "vue-router";
import { ref, onMounted } from "vue";
import { item, FormatTime } from "/@/utils/shop";
import { useMultiTagsStoreHook } from "/@/store/modules/multiTags";
import { errorMessage } from "/@/utils/message";

const router = useRouter();
const route = useRoute();

let form = ref([]);
let items: Array<item> = [];
interface tablerow {
  id: number;
  name: string;
  stock: number;
  description: string;
  beginDate: string;
  endDate: string;
}
onMounted(() => {
  http
    .get("/api/shop/itemlist")
    .then(({ data }) => {
      items = data;
      form.value = [];
      data.forEach(item => {
        let tablerow: tablerow = {
          id: item.id,
          name: item.name,
          stock: item.stock,
          description: item.description,
          beginDate: FormatTime(item.beginDate),
          endDate: FormatTime(item.endDate)
        };
        form.value.push(tablerow);
      });
    })
    .catch(err => {
      errorMessage(err.response.data.message);
    });
});

const editItem = (item: item, newItem: boolean) => {
  let id, name;
  if (newItem) {
    (id = -1), (name = "新商品");
  } else {
    id = item.id;
    name = item.name;
  }
  useMultiTagsStoreHook().handleTags("push", {
    path: `/admin/item/edit`,
    parentPath: route.matched[0].path,
    name: "itemedit",
    query: { id: String(id) },
    meta: {
      title: {
        zh: `${name} - 编辑`,
        en: `${name} - Edit`
      },
      showLink: false,
      i18n: false,
      dynamicLevel: 3
    }
  });
  router.push({ name: "itemedit", query: { id: String(id) } });
};
function getItembyId(id: number) {
  for (var it of items) {
    if (it.id != id) {
      continue;
    }
    return it;
  }
}
const gotoEdit = row => {
  editItem(getItembyId(row.id), false);
};
</script>
<template>
  <div class="itemadmin">
    <el-card>
      <el-button type="primary" class="item-add" @click="editItem(null, true)"
        >添加商品</el-button
      >
      <el-table border stripe :data="form" @row-dblclick="gotoEdit">
        <el-table-column label="商品ID" prop="id" />
        <el-table-column label="商品名称" prop="name" />
        <el-table-column label="库存" prop="stock" />
        <el-table-column label="开始抢购时间" prop="beginDate" />
        <el-table-column label="结束抢购时间" prop="endDate" />
      </el-table>
    </el-card>
  </div>
</template>
<style lang="scss" scoped>
.item-add {
  margin-bottom: 20px;
  float: right;
}
</style>

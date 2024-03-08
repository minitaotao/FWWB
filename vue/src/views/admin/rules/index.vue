<script lang="ts" setup>
import { useRouter, useRoute } from "vue-router";
import { useMultiTagsStoreHook } from "/@/store/modules/multiTags";
import { http } from "/@/utils/http";
import { ref } from "vue";
const router = useRouter();
const route = useRoute();
const addrule = () => {
  useMultiTagsStoreHook().handleTags("push", {
    path: `/admin/rules/add`,
    parentPath: route.matched[0].path,
    name: "ruleadd",
    meta: {
      title: {
        zh: `规则 - 添加`,
        en: `Rule - Add`
      },
      showLink: false,
      i18n: false,
      dynamicLevel: 3
    }
  });
  router.push({ name: "ruleadd" });
};
let form = ref([]);
http.get("/api/admin/rule/list").then(({ data }) => {
  form.value = [];
  for (var rule of data) {
    form.value.push({
      id: rule.id,
      name: rule.name,
      type: rule.type
    });
  }
});
const deleterule = rule => {
  return rule;
};
</script>

<template>
  <div class="card">
    <el-card>
      <el-button type="primary" class="rules-add" @click="addrule">
        添加规则
      </el-button>
      <el-table border stripe :data="form" @row-dblclick="deleterule">
        <el-table-column label="id" prop="id" />
        <el-table-column label="类型" prop="type" />
        <el-table-column label="名称" prop="name" /> </el-table
    ></el-card>
  </div>
</template>
<style type="scss" scoped>
.card {
  height: 100%;
}

.rules-add {
  float: right;
  margin-bottom: 15px;
}
</style>

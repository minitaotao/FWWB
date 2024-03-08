<script setup lang="ts">
import { useRoute } from "vue-router";
import { http } from "/@/utils/http";
import { ref, onMounted } from "vue";
import { successMessage, errorMessage } from "/@/utils/message";
const route = useRoute();
let id = route.query?.id;
let good = ref({
  price: 0,
  id: -1,
  name: "",
  totalstock: 0,
  description: "",
  detail: "",
  beginDate: 0,
  endDate: 0,
  rule_age: 1,
  rule_income: 1,
  purlimnum: 1
});
let rules = ref([
  {
    name: "age",
    show: "年龄",
    children: [],
    chosen: -1
  },
  {
    name: "income",
    show: "月收入",
    children: [],
    chosen: -1
  }
]);
let activeRule = ref("");
onMounted(() => {
  if (id == "-1") {
    good.value.name = "新商品";
  } else {
    http
      .get("/api/shop/item?id=" + id)
      .then(({ data }) => {
        good.value = data;
        good.value.beginDate *= 1000;
        good.value.endDate *= 1000;
      })
      .catch(err => {
        errorMessage(err.response.data.message);
      });
  }
  rules.value.forEach(rule => {
    rule.children = [];
    http
      .get("/api/admin/rule/list", { params: { type: rule.name } })
      .then(({ data }) => {
        rule.children = data;
      })
      .catch(err => {
        errorMessage(err.response.data.message);
      });
    rule.chosen = good.value["rule_" + rule.name];
  });
});
const FormatId = (id: number) => {
  if (id == -1) {
    return "暂未分配";
  }
  return id;
};
const submit = () => {
  let params = new URLSearchParams({
    id: good.value.id.toString(),
    price: good.value.price.toString(),
    name: good.value.name.toString(),
    totalstock: good.value.totalstock.toString(),
    description: good.value.description,
    detail: good.value.detail,
    beginDate: Math.floor(good.value.beginDate / 1000).toString(),
    endDate: Math.floor(good.value.endDate / 1000).toString(),
    purlimnum: good.value.purlimnum.toString()
  });
  rules.value.forEach(rule => {
    params.append("rule_" + rule.name, rule.chosen + "");
  });
  var path;
  if (id == "-1") {
    path = "/api/admin/additem";
  } else {
    path = "/api/admin/changeitem";
  }
  http
    .post(path, { data: params.toString() })
    .then(({ message }) => {
      successMessage(message);
    })
    .catch(err => {
      errorMessage(err.response.data.message);
    });
};
</script>
<template>
  <div>
    <el-card>
      <el-form label-width="120px" :column="2">
        <el-form-item label="理财ID">
          {{ FormatId(good.id) }}
        </el-form-item>
        <el-form-item label="理财名称">
          <el-input v-model="good.name" />
        </el-form-item>
        <el-form-item label="理财库存">
          <el-input v-model="good.totalstock" />
        </el-form-item>
        <el-form-item label="理财单价">
          <el-input v-model="good.price">
            <template #prepend>￥</template>
          </el-input>
        </el-form-item>
        <el-form-item label="限购数量">
          <el-input v-model="good.purlimnum" />
        </el-form-item>
        <el-form-item label="理财简述">
          <el-input
            v-model="good.description"
            type="textarea"
            autosize
            span="2"
          />
        </el-form-item>
        <el-form-item label="理财详细信息">
          <el-input v-model="good.detail" type="textarea" autosize span="2" />
        </el-form-item>
        <el-form-item label="秒杀开始时间">
          <el-date-picker
            type="datetime"
            value-format="x"
            v-model="good.beginDate"
          />
        </el-form-item>
        <el-form-item label="秒杀截止时间">
          <el-date-picker
            type="datetime"
            value-format="x"
            v-model="good.endDate"
          />
        </el-form-item>
        <el-form-item label="规则配置"
          ><el-collapse v-model="activeRule" accordion class="rules">
            <el-collapse-item
              v-for="rule in rules"
              :title="rule.show"
              :name="rule.name"
              :key="rule.name"
            >
              <el-radio-group v-model="rule.chosen">
                <el-radio
                  v-for="child in rule.children"
                  :key="child.id"
                  :label="child.id"
                  border
                  class="rules-item"
                  >{{ child.name }}</el-radio
                >>
              </el-radio-group>
            </el-collapse-item>
          </el-collapse></el-form-item
        >
        <el-form-item>
          <el-button type="primary" @click="submit">提交</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>
<style lang="scss" scoped>
.rules {
  width: 100%;
}

.rules-item {
  margin-top: 20px;
}
</style>

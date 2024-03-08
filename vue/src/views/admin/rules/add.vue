<script lang="ts" setup>
import { ref, watch } from "vue";
import { http } from "/@/utils/http";
import { successMessage, errorMessage } from "/@/utils/message";
import qs from "qs";
import { loadEnv } from "@build/index";
const { VITE_PROXY_DOMAIN, VITE_PROXY_DOMAIN_REAL } = loadEnv();
const requestConfig = {
  baseURL:
    process.env.NODE_ENV === "production"
      ? VITE_PROXY_DOMAIN_REAL
      : VITE_PROXY_DOMAIN,
  timeout: 10000,
  headers: {
    Accept: "application/json, text/plain, */*",
    "Content-Type": "application/json",
    "X-Requested-With": "XMLHttpRequest"
  },
  paramsSerializer: params => qs.stringify(params, { indices: false })
};
const ruleTypeParams = [
  {
    name: "age",
    show: "年龄",
    params: [
      {
        name: "max",
        type: "number",
        default: 0,
        null: -1,
        show: "最大年龄(包含)"
      },
      {
        name: "min",
        show: "最小年龄(包含)",
        type: "number",
        default: 0,
        null: -1
      }
    ]
  },
  {
    name: "income",
    show: "月收入",
    params: [
      {
        name: "min",
        type: "number",
        default: 0,
        null: -1,
        show: "最低月入"
      }
    ]
  }
];
let name = ref("");
let seleted = ref("");
let form = ref([]);
watch(seleted, () => {
  let res = [];
  for (var rule of ruleTypeParams) {
    if (rule.name != seleted.value) {
      continue;
    }
    for (var params of rule.params) {
      res.push({
        key: params.name,
        value: params.default,
        label: params.show,
        active: true
      });
    }
    form.value = res;
    return;
  }
});
const add = () => {
  var requestData = {
    RuleType: seleted.value,
    name: name.value
  };

  var rule = (() => {
    for (var r of ruleTypeParams) {
      if (r.name != requestData.RuleType) {
        continue;
      }
      return r;
    }
    throw "unable to find rule " + requestData.RuleType;
  })();

  for (var params of form.value) {
    var paramsdetail = (() => {
      for (var p of rule.params) {
        if (p.name != params.key) {
          continue;
        }
        return p;
      }
      throw "unable to find params " + params;
    })();

    if (params.active) {
      if (paramsdetail.type == "number") {
        requestData[params.key] = parseInt(params.value);
      } else {
        requestData[params.key] = params.value;
      }
    } else {
      requestData[params.key] = paramsdetail.null;
    }
  }
  console.log(requestData);
  http
    .post("/api/admin/rule/add", { data: requestData }, requestConfig)
    .then(({ data }) => {
      successMessage(data);
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
        <el-form-item label="规则类型">
          <el-select v-model="seleted">
            <el-option
              v-for="rule in ruleTypeParams"
              :key="rule.name"
              :label="rule.show"
              :value="rule.name"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="规则名">
          <el-input v-model="name" style="width: 200px" />
        </el-form-item>
        <el-form-item v-for="item in form" :label="item.label" :key="item.key">
          <el-input
            v-model="item.value"
            :disabled="!item.active"
            style="width: 200px"
          />
          <el-switch v-model="item.active" class="paramsswitch" />
        </el-form-item>
        <el-button @click="add" type="primary" class="addrule">添加</el-button>
      </el-form>
    </el-card>
  </div>
</template>
<style type="scss" scoped>
.addrule {
  margin-left: 20px;
}

.paramsswitch {
  margin-left: 20px;
}
</style>

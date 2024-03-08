<script setup lang="ts"></script>
<template>
  <div class="welcome">
    <el-row :gutter="24" style="margin: 20px">
      <el-col
        :xs="24"
        :sm="24"
        :md="12"
        :lg="12"
        :xl="12"
        style="margin-bottom: 20px"
        v-motion
        :initial="{
          opacity: 0,
          y: 100
        }"
        :enter="{
          opacity: 1,
          y: 0,
          transition: {
            delay: 200
          }
        }"
        ><el-card class="greeting card"> 欢迎 </el-card></el-col
      >
      <el-col
        :xs="24"
        :sm="24"
        :md="12"
        :lg="12"
        :xl="12"
        style="margin-bottom: 20px"
        v-motion
        :initial="{
          opacity: 0,
          y: 100
        }"
        :enter="{
          opacity: 1,
          y: 0,
          transition: {
            delay: 200
          }
        }"
        ><el-card class="hit card">
          <div class="hit-label">热销商品</div>
          <el-table border>
            <el-table-column label="商品名称" />
            <el-table-column label="存货" />
            <el-table-column label="介绍" />
          </el-table> </el-card
      ></el-col>
    </el-row>
  </div>
</template>
<style lang="scss" scoped>
.greeting {
  height: 200px;
}

.card {
  min-width: 400px;
  min-height: 200px;
  margin-bottom: 20px, 20px, 20px, 20px;
}

.hit-label {
  margin-bottom: 10px;
  position: relative;
}
</style>

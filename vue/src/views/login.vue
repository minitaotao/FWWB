<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";
import { addClass, removeClass } from "/@/utils/operate";
import bg from "/@/assets/login/bg.png";
import avatar from "/@/assets/login/avatar.png";
import illustration from "/@/assets/login/illustration.svg?component";
import { getLogin } from "../api/user";
import { successMessage, errorMessage, warnMessage } from "/@/utils/message";
import { initRouter } from "/@/router/utils";
import { storageSession } from "/@/utils/storage";
import { http } from "/@/utils/http";
const router = useRouter();

let phone_num = ref("");
let pwd = ref("");
let isAdmin = ref(false);

let loginHandler = ({ message, data, status }) => {
  if (status == 200) {
    successMessage("登陆成功");
    storageSession.setItem("info", {
      username: data
    });
    initRouter().then(() => {});
    router.push("/");
  } else {
    warnMessage("登陆失败" + message);
  }
};

const jumpRegister = (): void => {
  router.push("/register");
};

const onLogin = (): void => {
  //管理员登陆
  if (isAdmin.value) {
    let params = new URLSearchParams();
    params.append("phone_num", phone_num.value);
    params.append("password", pwd.value);
    http
      .post("/api/loginadmin", {
        data: params.toString()
      })
      .then(loginHandler)
      .catch(err => {
        errorMessage(err.response.data.message);
      });
  } else {
    //用户登陆
    getLogin({ phone_num: phone_num.value, password: pwd.value })
      .then(loginHandler)
      .catch(error => {
        errorMessage(error.response.data.message);
      });
  }
};

function onPhoneFocus() {
  addClass(document.querySelector(".phone"), "focus");
}

function onPhoneBlur() {
  if (phone_num.value.length === 0)
    removeClass(document.querySelector(".phone"), "focus");
}

function onPwdFocus() {
  addClass(document.querySelector(".pwd"), "focus");
}

function onPwdBlur() {
  if (pwd.value.length === 0)
    removeClass(document.querySelector(".pwd"), "focus");
}
</script>

<template>
  <img :src="bg" class="wave" />
  <div class="login-container">
    <div class="img">
      <illustration />
    </div>
    <div class="login-box">
      <div class="login-form">
        <img :src="avatar" class="avatar" />
        <h2
          v-motion
          :initial="{
            opacity: 0,
            y: 100
          }"
          :enter="{
            opacity: 1,
            y: 0,
            transition: {
              delay: 100
            }
          }"
        >
          理财秒杀
        </h2>
        <div
          class="input-group phone focus"
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
        >
          <div class="icon">
            <IconifyIconOffline icon="fa-phone" width="14" height="14" />
          </div>
          <div>
            <h5>手机号</h5>
            <input
              type="text"
              class="input"
              v-model="phone_num"
              @focus="onPhoneFocus"
              @blur="onPhoneBlur"
            />
          </div>
        </div>
        <div
          class="input-group pwd focus"
          v-motion
          :initial="{
            opacity: 0,
            y: 100
          }"
          :enter="{
            opacity: 1,
            y: 0,
            transition: {
              delay: 300
            }
          }"
        >
          <div class="icon">
            <IconifyIconOffline icon="fa-lock" width="14" height="14" />
          </div>
          <div>
            <h5>密码</h5>
            <input
              type="password"
              class="input"
              v-model="pwd"
              @focus="onPwdFocus"
              @blur="onPwdBlur"
            />
          </div>
        </div>
        <div class="login-admin">
          <a class="register-link" @click="jumpRegister">注册</a>
          <el-checkbox
            label="管理员登陆"
            v-model="isAdmin"
            class="login-button"
          />
        </div>
        <div>
          <button
            class="btn"
            v-motion
            :initial="{
              opacity: 0,
              y: 10
            }"
            :enter="{
              opacity: 1,
              y: 0,
              transition: {
                delay: 400
              }
            }"
            @click="onLogin"
          >
            登录
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import url("/@/style/login.css");
</style>

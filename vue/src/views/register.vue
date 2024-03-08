<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";
import { addClass, removeClass } from "/@/utils/operate";
import bg from "/@/assets/login/bg.png";
import avatar from "/@/assets/login/avatar.png";
import illustration from "/@/assets/login/illustration.svg?component";
import { http } from "/@/utils/http";
import { errorMessage, successMessage } from "../utils/message";
const router = useRouter();

let user = ref("");
let pwd = ref("");
let phone = ref("");
let id_card = ref("");

const onRegister = (): void => {
  const params = new URLSearchParams({
    name: user.value,
    phone_num: phone.value,
    id_card: id_card.value,
    password: pwd.value
  });
  http
    .request("post", "/api/register", { data: params.toString() })
    .then(() => {
      successMessage("注册成功");
      router.push("/login");
    })
    .catch(err => {
      errorMessage(err.response.data.message);
    });
};

function onUserFocus() {
  addClass(document.querySelector(".user"), "focus");
}

function onUserBlur() {
  if (user.value.length === 0)
    removeClass(document.querySelector(".user"), "focus");
}

function onIdCardFocus() {
  addClass(document.querySelector(".id-card"), "focus");
}

function onIdCardBlur() {
  if (id_card.value.length === 0)
    removeClass(document.querySelector(".id-card"), "focus");
}

function onPwdFocus() {
  addClass(document.querySelector(".pwd"), "focus");
}

function onPwdBlur() {
  if (pwd.value.length === 0)
    removeClass(document.querySelector(".pwd"), "focus");
}

function onPhoneFocus() {
  addClass(document.querySelector(".phone"), "focus");
}

function onPhoneBlur() {
  if (phone.value.length === 0)
    removeClass(document.querySelector(".phone"), "focus");
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
          注册
        </h2>
        <div
          class="input-group user focus"
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
            <IconifyIconOffline icon="fa-user" width="14" height="14" />
          </div>
          <div>
            <h5>姓名</h5>
            <input
              type="text"
              class="input"
              v-model="user"
              @focus="onUserFocus"
              @blur="onUserBlur"
            />
          </div>
        </div>
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
              delay: 300
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
              v-model="phone"
              @focus="onPhoneFocus"
              @blur="onPhoneBlur"
            />
          </div>
        </div>
        <div
          class="input-group id-card focus"
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
            <IconifyIconOffline icon="fa-address-card" width="14" height="14" />
          </div>
          <div>
            <h5>身份证号</h5>
            <input
              type="text"
              class="input"
              v-model="id_card"
              @focus="onIdCardFocus"
              @blur="onIdCardBlur"
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
          @click="onRegister"
        >
          注册
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import url("/@/style/login.css");
</style>

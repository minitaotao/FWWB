import { http } from "../utils/http";

interface userType extends Promise<any> {
  svg?: string;
  code?: number;
  info?: object;
}

// 获取验证码
export const getVerify = (): userType => {
  return http.request("get", "/captcha");
};

// 登录
export const getLogin = ({ phone_num, password }) => {
  const params = new URLSearchParams({
    phone_num: phone_num,
    password: password
  });
  return http.request("post", "/api/loginuser", { data: params.toString() });
};

// 刷新token
export const refreshToken = (data: object) => {
  return http.request("post", "/refreshToken", { data });
};

// export const searchVague = (data: object) => {
//   return http.request("post", "/searchVague", { data });
// };

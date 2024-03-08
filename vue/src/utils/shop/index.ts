export interface item {
  price: number;
  id: number;
  name: string;
  stock: number;
  description: string;
  detail: string;
  beginDate: number;
  endDate: number;
}

export function formatTimeDuring(dur: number) {
  let str = "";
  if (dur > 86400) {
    str += parseInt(dur / 86400) + "天";
    dur %= 86400;
  }
  if (dur > 3600) {
    str += parseInt(dur / 3600) + "小时";
    dur %= 3600;
  }
  if (dur > 60) {
    str += parseInt(dur / 60) + "分钟";
    dur %= 60;
  }
  str += parseInt(dur) + "秒";
  return str;
}

export function FormatTime(t: number) {
  const time = new Date(t * 1000);
  return time.toLocaleString();
}

export interface ListItem {
  avatar: string;
  title: string;
  datetime: string;
  type: string;
  description: string;
  status?: "" | "success" | "warning" | "info" | "danger";
  extra?: string;
}

export interface TabItem {
  key: string;
  name: string;
  list: ListItem[];
}

export const noticesData: TabItem[] = [
  {
    key: "1",
    name: "通知",
    list: [
      {
        avatar:
          "https://gw.alipayobjects.com/zos/rmsportal/ThXAXghbEsBCCSDihZxY.png",
        title: "新商品发布",
        datetime: "一天前",
        description: "",
        type: "1"
      }
    ]
  },
  {
    key: "2",
    name: "消息",
    list: [
      {
        avatar:
          "https://gw.alipayobjects.com/zos/rmsportal/fcHMVNCjPOsbUGdEduuv.jpeg",
        title: "购买成功",
        description: "您成功购买了一份理财",
        datetime: "刚刚",
        type: "2"
      }
    ]
  },
  {
    key: "3",
    name: "理财秒杀提醒",
    list: [
      {
        avatar: "",
        title: "理财秒杀",
        description: "您的理财 2022-11-16 20:00 开始秒杀",
        datetime: "",
        extra: "未开始",
        status: "info",
        type: "3"
      }
    ]
  }
];

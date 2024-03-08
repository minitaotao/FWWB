import { $t } from "/@/plugins/i18n";
const Layout = () => import("/@/layout/index.vue");

const myshopRouter = {
  path: "/myshop",
  component: Layout,
  redirect: "/myshop/myinfo",
  meta: {
    icon: "fa-user",
    title: $t("menus.hsmyshop"),
    i18n: true,
    rank: 4
  },
  children: [
    {
      path: "/myshop/info",
      name: "myinfo",
      component: () => import("/@/views/myshop/info.vue"),
      meta: {
        title: $t("menus.hsinfo"),
        i18n: true
      }
    },
    {
      path: "/myshop/order",
      component: () => import("../../views/myshop/order.vue"),
      name: "myorder",
      meta: {
        title: $t("menus.hsmyorder"),
        i18n: true
      }
    },
    {
      path: "/myshop/orderdetail",
      component: () => import("../../views/myshop/orderdetail.vue"),
      name: "myorderdetail",
      meta: {
        title: $t("menus.hsmyorderdetail"),
        i18n: true,
        showLink: false
      }
    }
  ]
};

export default myshopRouter;

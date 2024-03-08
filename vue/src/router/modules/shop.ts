import { $t } from "/@/plugins/i18n";
const Layout = () => import("/@/layout/index.vue");

const shopRouter = {
  path: "/shop",
  component: Layout,
  name: "shop",
  redirect: "/shop/index",
  meta: {
    icon: "shoppingbag",
    title: $t("menus.hsshop"),
    i18n: true,
    rank: 3
  },
  children: [
    {
      path: "/shop/detail",
      component: () => import("/@/views/shop/detail.vue"),
      name: "itemDetail",
      meta: {
        title: $t("menus.hsitemdetail"),
        showLink: false,
        i18n: true
      }
    },
    {
      path: "/shop/index",
      component: () => import("/@/views/shop/index.vue"),
      name: "items",
      meta: {
        title: $t("menus.hsshoplist"),
        showLink: false,
        i18n: true
      }
    }
  ]
};

export default shopRouter;

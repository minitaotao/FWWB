import { $t } from "/@/plugins/i18n";
const Layout = () => import("/@/layout/index.vue");

const adminRouter = {
  path: "/admin",
  component: Layout,
  redirect: "/admin/dashboard",
  meta: {
    icon: "lollipop",
    title: $t("menus.hsadmin"),
    i18n: true,
    rank: 9
  },
  children: [
    {
      path: "/admin/dashboard",
      name: "dashborad",
      component: () => import("/@/views/admin/dashboard.vue"),
      meta: {
        title: $t("menus.hsdashboard"),
        i18n: true
      }
    },
    {
      path: "/admin/orderquery",
      name: "orderquery",
      component: () => import("/@/views/admin/orderquery.vue"),
      meta: {
        title: $t("menus.hsorderquery"),
        i18n: true
      }
    },
    {
      path: "/admin/item",
      name: "itemadmin",
      component: () => import("../../views/admin/item/index.vue"),
      meta: {
        title: $t("menus.hsitemadmin"),
        i18n: true
      }
    },
    {
      path: "/admin/item/edit",
      name: "itemedit",
      component: () => import("../../views/admin/item/edit.vue"),
      meta: {
        title: $t("menus.hsitemedit"),
        i18n: true,
        showLink: false
      }
    },
    {
      path: "/admin/rules",
      name: "rules",
      component: () => import("../../views/admin/rules/index.vue"),
      meta: {
        title: $t("menus.hsrules"),
        i18n: true,
        showLink: true
      }
    },
    {
      path: "/admin/rules/add",
      name: "ruleadd",
      component: () => import("../../views/admin/rules/add.vue"),
      meta: {
        title: $t("menus.hsruleadd"),
        i18n: true,
        showLink: false
      }
    }
  ]
};

export default adminRouter;

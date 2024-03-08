import { $t } from "/@/plugins/i18n";
const Layout = () => import("/@/layout/index.vue");

const remainingRouter = [
  {
    path: "/login",
    name: "login",
    component: () => import("/@/views/login.vue"),
    meta: {
      title: $t("menus.hslogin"),
      showLink: false,
      i18n: true,
      rank: 101
    }
  },
  {
    path: "/register",
    name: "register",
    component: () => import("/@/views/register.vue"),
    meta: {
      title: $t("menus.hsregister"),
      showLink: false,
      i18n: true,
      rank: 102
    }
  },
  {
    path: "/redirect",
    component: Layout,
    meta: {
      icon: "home-filled",
      title: $t("menus.hshome"),
      i18n: true,
      showLink: false,
      rank: 104
    },
    children: [
      {
        path: "/redirect/:path(.*)",
        name: "redirect",
        component: () => import("/@/layout/redirect.vue")
      }
    ]
  },
  {
    path: "/error",
    component: Layout,
    redirect: "/error/403",
    meta: {
      icon: "information-line",
      showLink: false,
      title: $t("menus.hserror"),
      i18n: true,
      rank: 9
    },
    children: [
      {
        path: "/error/403",
        name: "403",
        component: () => import("/@/views/error/403.vue"),
        meta: {
          title: $t("menus.hsfourZeroOne"),
          showLink: false,
          i18n: true
        }
      },
      {
        path: "/error/404",
        name: "404",
        component: () => import("/@/views/error/404.vue"),
        meta: {
          title: $t("menus.hsfourZeroFour"),
          showLink: false,
          i18n: true
        }
      },
      {
        path: "/error/500",
        name: "500",
        component: () => import("/@/views/error/500.vue"),
        meta: {
          title: $t("menus.hsFive"),
          showLink: false,
          i18n: true
        }
      }
    ]
  }
];

export default remainingRouter;

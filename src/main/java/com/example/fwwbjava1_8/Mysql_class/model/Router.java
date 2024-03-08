package com.example.fwwbjava1_8.Mysql_class.model;

import java.util.List;

public class Router {
    public Router(String path, String redirect, Meta1 meta, List<Children> children) {
        this.path = path;
        this.redirect = redirect;
        this.meta = meta;
        this.children = children;
    }

    private String path;
    private String redirect;
    private Meta1 meta;
    private List<Children> children;

    public void setPath(String path) {
        this.path = path;
    }
    public String getPath() {
        return path;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }
    public String getRedirect() {
        return redirect;
    }

    public void setMeta(Meta1 meta) {
        this.meta = meta;
    }
    public Meta1 getMeta() {
        return meta;
    }

    public void setChildren(List<Children> children) {
        this.children = children;
    }
    public List<Children> getChildren() {
        return children;
    }

    public static class Meta1 {
        public Meta1(String title, String icon, boolean i18n, int rank) {
            this.title = title;
            this.icon = icon;
            this.i18n = i18n;
            this.rank = rank;
        }

        private String title;
        private String icon;
        private boolean i18n;
        private int rank;
        public void setTitle(String title) {
            this.title = title;
        }
        public String getTitle() {
            return title;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
        public String getIcon() {
            return icon;
        }

        public void setI18n(boolean i18n) {
            this.i18n = i18n;
        }
        public boolean getI18n() {
            return i18n;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }
        public int getRank() {
            return rank;
        }

    }
    public static class Meta2{
        public String title;

        public boolean getShowLink() {
            return showLink;
        }

        public void setShowLink(boolean showLink) {
            this.showLink = showLink;
        }

        public boolean showLink;
        public Meta2(String title, boolean i18n, List<String> authority,boolean showLink) {
            this.title = title;
            this.i18n = i18n;
            this.authority = authority;
            this.showLink = showLink;
        }
        public Meta2(String title, boolean i18n, List<String> authority) {
            this.title = title;
            this.i18n = i18n;
            this.authority = authority;
            this.showLink = true;
        }
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isI18n() {
            return i18n;
        }

        public void setI18n(boolean i18n) {
            this.i18n = i18n;
        }

        public List<String> getAuthority() {
            return authority;
        }

        public void setAuthority(List<String> authority) {
            this.authority = authority;
        }

        public boolean i18n;
        public List<String> authority;
    }
    public static class Children {
        public Children(String path, String name, Meta2 meta) {
            this.path = path;
            this.name = name;
            this.meta = meta;
        }

        private String path;
        private String name;
        private Meta2 meta;
        public void setPath(String path) {
            this.path = path;
        }
        public String getPath() {
            return path;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

        public void setMeta(Meta2 meta) {
            this.meta = meta;
        }
        public Meta2 getMeta() {
            return meta;
        }

    }
}
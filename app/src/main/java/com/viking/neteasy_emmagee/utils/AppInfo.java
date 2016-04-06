package com.viking.neteasy_emmagee.utils;

import android.graphics.drawable.Drawable;

/**
 * Created by Viking Den on 2016/4/4.
 */
public class AppInfo {

    private Drawable icon ;

    private String processName ;

    private String pkgName ;

    private int pid ;

    private int uid ;

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}

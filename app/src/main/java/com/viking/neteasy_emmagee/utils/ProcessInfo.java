package com.viking.neteasy_emmagee.utils;


import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import com.viking.neteasy_emmagee.R;

public class ProcessInfo {

	private final String LOG_TAG = "Emmagee-" + ProcessInfo.class.getSimpleName();

	private final String PACKAGE_NAME = "com.viking.neteasy_emmagee";

	/**
	 * get information of all running processes,including package name ,process
	 * name ,icon ,pid and uid
	 * 
	 * @param context
	 *            context of activity
	 * @return running processes list
	 */
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public List<AppInfo> getRunningProcess(Context context) {
		Log.i(LOG_TAG, "get running processes");

		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> run = am.getRunningAppProcesses();
		PackageManager pm = context.getPackageManager();
		List<AppInfo> progressList = new ArrayList<>();
		boolean launchTag;

		for (ApplicationInfo appinfo : getPackagesInfo(context)) {
			launchTag = false;
			AppInfo app = new AppInfo();
			if (((appinfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0)
					|| ((appinfo.processName != null) && (appinfo.processName.equals(PACKAGE_NAME)))) {
				continue;
			}
			for (RunningAppProcessInfo runningProcess : run) {
				if ((runningProcess.processName != null)
						&& runningProcess.processName .equals(appinfo.processName)) {
					launchTag = true;
					app.setPid(runningProcess.pid);
					app.setUid(runningProcess.uid);
					break;
				}
			}
			app.setPkgName(appinfo.processName);
			app.setProcessName(appinfo.loadLabel(pm).toString());
			if (launchTag) {
				app.setIcon(appinfo.loadIcon(pm));
			}else{
				app.setIcon(context.getDrawable(R.mipmap.ic_launcher));
			}
			progressList.add(app);
		}
		return progressList;
	}

	/**
	 * get information of all applications
	 * 
	 * @param context
	 *            context of activity
	 * @return packages information of all applications
	 */
	private List<ApplicationInfo> getPackagesInfo(Context context) {
		PackageManager pm = context.getApplicationContext().getPackageManager();
		List<ApplicationInfo> appList = pm
				.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
		return appList;
	}
}

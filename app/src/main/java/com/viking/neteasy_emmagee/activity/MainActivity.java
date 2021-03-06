package com.viking.neteasy_emmagee.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.viking.neteasy_emmagee.R;
import com.viking.neteasy_emmagee.utils.AppInfo;
import com.viking.neteasy_emmagee.utils.ProcessInfo;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "NetEasy-Emmagee";

    private ListView appsList;
    private Button btnTest;

    private ProcessInfo processInfo ;

    private boolean isRadioChecked = false ;

    private int pid, uid;
    private String processName, packageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        processInfo = new ProcessInfo() ;
        btnTest = (Button) findViewById(R.id.btn_test);
        appsList = (ListView) findViewById(R.id.processList);
        appsList.setAdapter(new AppListAdapter(getApplicationContext() ,
                processInfo.getRunningProcess(getApplicationContext()))) ;
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleTestBtnActon() ;
            }
        });
    }

    private void handleTestBtnActon() {
        Toast.makeText(MainActivity.this, "Not implement yet.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void finish() {
        super.finish();
    }

    /**
     * override return key to show a dialog
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            showExitDialog();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * set menu options
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, Menu.FIRST, 0, R.string.exit).setIcon(
                android.R.drawable.ic_menu_delete);
        menu.add(0, Menu.FIRST, 1, R.string.setting).setIcon(
                android.R.drawable.ic_menu_directions);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getOrder()) {
            case 0:
                Log.i(TAG , "Enter onOptionsItemSelected ...exit") ;
                showExitDialog();
                break;
            case 1:
                /*Intent intent = new Intent();
                intent.setClass(MainPageActivity.this, SettingsActivity.class);
                intent.putExtra("settingTempFile", settingTempFile);
                startActivityForResult(intent, Activity.RESULT_FIRST_USER);*/
                break;
            default:
                break;
        }
        return false;
    }

    /**
     * Show application's exit dialog
     */
    private void showExitDialog(){
        new AlertDialog.Builder(this)
                .setTitle(R.string.exit_dialog_title)
                .setPositiveButton(R.string.exit_dialog_ok,
                        new android.content.DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                        /*if (MonitorService != null) {
                                            Log.d(LOG_TAG, "stop service");
                                            stopService(MonitorService);
                                        }*/

//                                        EmmageeService.closeOpenedStream();
                                finish();
                                System.exit(0);
                            }
                        })
                .setNegativeButton(R.string.exit_dialog_cancel, null).create().show();
    }

    private class AppListAdapter extends BaseAdapter {
        int tempPosition = -1 ;
        List<AppInfo> appInfos;
        LayoutInflater layoutInflater ;
        Context context ;

        public AppListAdapter(Context context ,List<AppInfo> appInfos) {
            this.context = context ;
            layoutInflater = LayoutInflater.from(context) ;
            this.appInfos = appInfos;
        }

        @Override
        public int getCount() {
            return appInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return appInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = null ;
            if (convertView == null){
                convertView = layoutInflater.inflate(R.layout.app_list_item , null) ;
                viewHolder = new ViewHolder() ;
                viewHolder.appIcon = (ImageView) convertView.findViewById(R.id.appicon) ;
                viewHolder.appName = (TextView) convertView.findViewById(R.id.appname) ;
                viewHolder.radioButton = (RadioButton) convertView.findViewById(R.id.rdBtn) ;
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final int i = position ;
            viewHolder.radioButton.setId(position);
            viewHolder.radioButton.setChecked(false);
            viewHolder.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        isRadioChecked = true;
                        // Radio function
                        if (tempPosition != -1) {
                            RadioButton tempButton = (RadioButton) findViewById(tempPosition);
                            if ((tempButton != null) && (tempPosition != i)) {
                                tempButton.setChecked(false);
                            }
                        }

                        tempPosition = buttonView.getId();
                        packageName = appInfos.get(tempPosition).getPkgName();
                        processName = appInfos.get(tempPosition).getProcessName();
                    }
                }
            });
            if (tempPosition == position) {
                if (!viewHolder.radioButton.isChecked())
                    viewHolder.radioButton.setChecked(true);
            }
            AppInfo appInfo = appInfos.get(position) ;
            viewHolder.appIcon.setImageDrawable(appInfo.getIcon());
            viewHolder.appName.setText(appInfo.getProcessName());
            return convertView;
        }
    }

    private class ViewHolder{
        public TextView appName;
        public ImageView appIcon;
        public RadioButton radioButton;
    }


}

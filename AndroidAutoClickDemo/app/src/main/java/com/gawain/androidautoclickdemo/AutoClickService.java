package com.gawain.androidautoclickdemo;

import android.accessibilityservice.AccessibilityService;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class AutoClickService extends AccessibilityService {
    private static final String TAG = "GK";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        ztLog("===start search===");
        try {
            AccessibilityNodeInfo rootInfo = getRootInActiveWindow();
            if (rootInfo != null) {
                DFS(rootInfo);
            }
        } catch (Exception e) {
            ztLog("Exception:" + e.getMessage(), true);
        }
    }

    @Override
    public void onInterrupt() {
    }

    /**
     * 深度优先遍历寻找目标节点
     */
    private void DFS(AccessibilityNodeInfo rootInfo) {
        if (rootInfo == null || TextUtils.isEmpty(rootInfo.getClassName())) {
            return;
        }

        if(rootInfo.getChildCount()==0){
            System.out.println("rootInfo.getText()   " +rootInfo.getText());
            if (rootInfo.getText()!=null && rootInfo.getText().toString().trim().equals("淘金币")){
                performClick(rootInfo);
                ArrayList<String> adbString = new ArrayList<>();
                adbString.add("input tap 500 700\n");
                adbString.add("input swipe 500 500 100 100\n");
                adbString.add("input text 1234567890\n");
                adbString.add("input keyevent 4\n");
                runADB(adbString,2);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        String AdbTap = "input tap 500 700\n";
//                        ADB.execAdb(AdbTap);
//                        System.out.println("@@@@@@@@@@@@@@ input tap 500 700input tap 500 700");
//                        String AdbSwipe = "input swipe 500 500 100 100\n";
//                        ADB.execAdb(AdbSwipe);
//                        String AdbText = "input text 1234567890\n";
//                        ADB.execAdb(AdbText);
//                        String AdbKeyevent = "input keyevent 4\n";
//                        ADB.execAdb(AdbKeyevent);
//                    }
//                },10000);

//                String AdbTap = "input tap 500 700\n";
//                ADB.execAdb(AdbTap);
//                System.out.println("@@@@@@@@@@@@@@ input tap 500 700input tap 500 700");
//                String AdbSwipe = "input swipe 500 500 100 100\n";
//                ADB.execAdb(AdbSwipe);
//                String AdbText = "input text 1234567890\n";
//                ADB.execAdb(AdbText);
//                String AdbKeyevent = "input keyevent 4\n";
//                ADB.execAdb(AdbKeyevent);
            }

        }else {
            for (int i = 0; i < rootInfo.getChildCount(); i++) {
                if(rootInfo.getChild(i)!=null){
                    DFS(rootInfo.getChild(i));
                }
            }
        }

//        if (!"android.widget.GridView".equals(rootInfo.getClassName())) {
//            ztLog(rootInfo.getClassName().toString());
//            for (int i = 0; i < rootInfo.getChildCount(); i++) {
//                DFS(rootInfo.getChild(i));
//            }
//        } else {
//            ztLog("==find gridView==");
//            final AccessibilityNodeInfo GridViewInfo = rootInfo;
//            System.out.println("GridViewInfo.getChildCount() " + GridViewInfo.getChildCount());
//            for (int i = 0; i <= GridViewInfo.getChildCount(); i++) {
//                final AccessibilityNodeInfo frameLayoutInfo = GridViewInfo.getChild(i);
//                final AccessibilityNodeInfo childInfo = frameLayoutInfo.getChild(0);
//                String text = childInfo.getText().toString();
//                if (text.equals("item1")) {
//                    performClick(frameLayoutInfo);
//                } else {
//                    ztLog(text);
//                }
//            }
//        }
    }

    private void performClick(AccessibilityNodeInfo targetInfo) {
        System.out.println("Ok Click~!~!~!");
//        targetInfo = targetInfo.getParent();
        targetInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
    }

    private void ztLog(String str) {
        ztLog(str, false);
    }

    private void ztLog(String str, boolean showToast) {
        Log.i(TAG, str);
        if (showToast) {
            Toast.makeText(this, str, Toast.LENGTH_LONG).show();
        }
    }

    ArrayList<String> execAdb;
    private void runADB(ArrayList<String> tempExecAdb, final int logTime){
        execAdb = tempExecAdb;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < execAdb.size(); i++){
                    ADB.execAdb(execAdb.get(i));
                    System.out.println("execAdb.get(i) : " + execAdb.get(i));
                    Reminder(logTime);
                }
            }
        },logTime);
    }

    Timer timer;
    public void Reminder(int seconds) {
        timer = new Timer();
        timer.schedule(new RemindTask(), seconds*1000);
    }

    class RemindTask extends TimerTask {
        public void run() {
            System.out.println("Time's up!");
            timer.cancel(); //Terminate the timer thread
        }
    }
}

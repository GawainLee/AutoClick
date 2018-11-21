package com.gawain.androidautoclickdemo;

import android.accessibilityservice.AccessibilityService;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

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
}

package com.gawain.androidautoclickdemo;

import java.io.File;
import java.io.OutputStream;

public class CheckRoot {

    /**
     * 应用程序运行命令获取 Root权限，设备必须已破解(已Root过，获得ROOT权限)
     * @return 应用程序是/否获取Root权限
     */
    public static boolean getRootAhth() {
        OutputStream os = null;
        try {
            //请求进入su账户，类似PC端adb shell之后的su命令。同时，获取与之相关的输出流
            os = Runtime.getRuntime().exec("su").getOutputStream();
            //退出su
            os.write(("exit\n").getBytes());
            os.flush();
            //上述命令执行成功，则进入su账户成功，具备进入su的能力，说明已经获取到了root权限
            return true;
        } catch (Exception e) {
            System.out.println("adb命令执行失败，错误原因：" + e.getMessage());
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     * 判断当前手机是否有ROOT权限
     * @return
     */
    public static boolean isRoot(){
        boolean bool = false;

        try{
            if ((!new File("/system/bin/su").exists()) && (!new File("/system/xbin/su").exists())){
                bool = false;
            } else {
                bool = true;
            }
            System.out.println("bool = " + bool);
        } catch (Exception e) {

        }
        return bool;
    }
}

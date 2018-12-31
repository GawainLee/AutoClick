package com.gawain.androidautoclickdemo;

import java.io.OutputStream;

public class ADB {

    //在IO流中输入命令，需要加"\n"，因为见到回车才会执行命令；
//如果直接用Runtime.getRuntime().exec(adbCommand)来执行，则不必加回车符；

    //点击屏幕上的一点，eg：这点的像素坐标是（100,100）
    public String AdbTap = "input tap 100 100\n";
    /**
     * 实现滑动操作，前两个参数是开始坐标，接下来两个是终点坐标，最后一个是持续时间。
     * 解释参考：http://blog.csdn.net/u012912435/article/details/51483309
     * 可以用来模拟长按，原理：在小的距离内，在较长的持续时间内进行滑动，最后表现出来的结果就是长按动作。
     */
    public String AdbSwipe = "input swipe 500 500 501 501 2000\n";
    //按下按键，eg：该按键的按键值是4（系统的返回键）。按键值参考https://www.cnblogs.com/sharecenter/p/5621048.html
    public String AdbKeyevent = "input keyevent 4\n";
    //输入文本，eg：文本内容是1234567890
    public String AdbText = "input text 1234567890\n";



    /**执行adb命令，需要已经为应用分配过root权限
     * @param adbCommand
     */
    public static void execAdb(String adbCommand) {
        //简单有效，直接执行三条条adb命令
//      try {
//          Runtime.getRuntime().exec("/system/xbin/su");
//          Runtime.getRuntime().exec(adbCommand);
//          Runtime.getRuntime().exec("exit\n");
//      } catch (Exception e1) {
//          e1.printStackTrace();
//      }

//        //或者用下面方式
        OutputStream os = null;
        try {
            //获取与之相关的输出流
            os =  Runtime.getRuntime().exec("su").getOutputStream();
            os.write(adbCommand.getBytes());
            //退出su
            os.write(("exit\n").getBytes());
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (Exception e) {
            }
        }
    }
}

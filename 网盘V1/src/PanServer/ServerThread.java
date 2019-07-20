/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: ServerThread
 * Author:   ITryagain
 * Date:     2018/12/6 0:13
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package PanServer;

import java.io.*;
import java.math.RoundingMode;
import java.net.Socket;
import java.text.DecimalFormat;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author ITryagain
 * @create 2018/12/6
 * @since 1.0.0
 */

public class ServerThread extends Thread {

    private Socket client;//线程中处理的客户对象
    private OutputStream ous;//输出流对象
    private InputStream ins;
    private String User_name;
    private boolean upLoad = false;
    private boolean downLoad = false;

    ServerThread(Socket client){
        this.client=client;
    }

    public void run() {
        processSocket();
    }

    public void sendMsg2Me(String msg) {
        try {
            msg+="\r\n";
            ous.write(msg.getBytes());
            ous.flush();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            System.out.println(User_name+"断开连接");
        }
    }

    //读取客户机发来的消息
    private void processSocket() {
        try {
            Socket sc=this.client;
            ins=sc.getInputStream();
            ous=sc.getOutputStream();
            //将输入流ins封装为可以读取一行字符串也就是以\r\n结尾的字符串
            BufferedReader brd=new BufferedReader(new InputStreamReader(ins));
            sendMsg2Me("欢迎您使用！请输入你的用户名：");
            User_name=brd.readLine();
            System.out.println(User_name);
            //创建文件夹
            File directory = new File("D:\\"+User_name);
            if(!directory.exists()){
                directory.mkdir();
            }
            String input=brd.readLine();//一行一行的读取客户机发来的消息
            while(true) {
                    System.out.println("服务器收到的是"+input);
                    if((!this.upLoad)&&(!this.downLoad)){
                        check(input);
                    }
                    if(this.upLoad){//上传中
                        UpLoad(input);
                    }
                    if(this.downLoad){//下载中
                        DownLoad(input);
                    }
                    input=brd.readLine();//读取下一条
                    System.out.println(input);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            this.closeMe();
        }
        System.out.println("结束");
    }

    //命令解码
    private void check(String input){
        if(input.charAt(0)=='~'){
            String tmp=input.substring(input.indexOf("~")+1,input.indexOf("#"));
            System.out.println(tmp);
            if(tmp.equals("downLoad")){
                this.downLoad=true;
            }else if(tmp.equals("upLoad")){
                this.upLoad=true;
            }else if(tmp.equals("new")){
                System.out.println(input.substring(input.indexOf("#")+1));
                File directory = new File(input.substring(input.indexOf("#")+1));
                if(!directory.exists()){
                    directory.mkdir();
                }
            }else if(tmp.equals("delete")){
                System.out.println(input.substring(input.indexOf("#")+1));
                boolean success = (new File(input.substring(input.indexOf("#")+1))).delete();
                if(success){
                    System.out.println("删除成功");
                }else{
                    System.out.println("删除失败");
                }
            }else if(tmp.equals("change")){
                String tmp_name=input.substring(input.indexOf("#")+1);
                System.out.println(tmp_name);
                String newName=tmp_name.substring(0,tmp_name.indexOf("@"));
                File directory = new File(tmp_name.substring(tmp_name.indexOf("@")+1));
                directory.renameTo(new File(newName));
            }
        }
    }

    private void UpLoad(String input){
        System.out.println("上传文件");
        UpLoadThread upLoadThread = new UpLoadThread(8889,input);
        upLoadThread.start();
        this.upLoad=false;
    }

    private void DownLoad(String input){
        System.out.println("下载文件");
        DownLoadThread downLoadThread = new DownLoadThread(8890,input);
        downLoadThread.start();
        this.downLoad=false;
    }

    //关闭这个线程
    public void closeMe() {
        try {
            client.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean isConnected(){
        try{
            client.sendUrgentData(0xFF);
            return true;
        }catch(Exception e){
            return false;
        }

    }
}

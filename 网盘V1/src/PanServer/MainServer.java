/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: MainServer
 * Author:   ITryagain
 * Date:     2018/12/5 21:01
 * Description: 服务端
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package PanServer;


/**
 * 〈一句话功能简述〉<br> 
 * 〈服务端〉
 *
 * @author ITryagain
 * @create 2018/12/5
 * @since 1.0.0
 */

public class MainServer {

    private PanServer panServer;//服务器对象


    public static void main(String[] args){
        MainServer ms =new MainServer();
        ms.actionServer();
    }

    // 响应启动/停止按钮事件
    public void actionServer() {
        // 1.要得到服务器状态
        if (null == panServer) {
           // String sPort = jta_port.getText();// 得到输入的端口
            //int port = Integer.parseInt(sPort);
            panServer = new PanServer(8888);
            panServer.start();
           // jf.setTitle("QQ服务器管理程序 :正在运行中");
           // bu_control_chat.setText("Stop!");
        } else if (panServer.isRunning()) {// 己经在运行
            // 清除所有己在运行的客户端处理线程
          //  ChatTools.removeAllClient();
            panServer.stopPanServer();
            panServer = null;
           // jf.setTitle("QQ服务器管理程序 :己停止");
            //SwingUtilities.updateComponentTreeUI(table_onlineUser);
          //  bu_control_chat.setText("Start!");
        }

    }
}

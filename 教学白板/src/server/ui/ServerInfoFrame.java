/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: ServerInfoFrame
 * Author:   ITryagain
 * Date:     2019/5/15 18:30
 * Description: 服务器信息窗体
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package server.ui;

import client.ui.ChatFrame;
import client.util.ClientUtil;
import common.model.entity.*;
import common.model.entity.Shape;
import server.DataBuffer;
import server.controller.RequestProcessor;
import server.model.service.UserService;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

/**
 * 〈一句话功能简述〉<br>
 * 〈服务器信息窗体〉
 *
 * @author ITryagain
 * @create 2019/5/15
 * @since 1.0.0
 */

public class ServerInfoFrame extends JFrame {
    private static final long serialVersionUID = 6274443611957724780L;
    /**聊天信息列表区域*/
    private static JTextArea msgListArea;
    /**要发送的信息区域*/
    private static JTextArea sendArea;
    private JPanel paint;
    private JLabel otherInfoLbl;
    private JTable onlineUserTable ;
    private JTable registedUserTable ;
    /** 准备发送的文件 */
    public static FileInfo sendFile;

    public ServerInfoFrame() {
        setLookAndFeel();
        init();
        loadData();
        setVisible(true);
    }
    public static void main(String[] args){
        new ServerInfoFrame();
    }

    public void init() {  //初始化窗体
        this.setTitle("教师端");//设置服务器启动标题
        this.setSize(1400, 800);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        /*
         * 设计组件放置位置
         *
         * @param NorthJPanel 画图板上方属性选项区域,流式布局
         *
         * @param InNorthJPanel 再选项区域中设置一个指定大小的面板,流式布局
         */
        JPanel NorthJPanel = new JPanel();
        NorthJPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 0));
        NorthJPanel.setBorder(BorderFactory.createEtchedBorder(new Color(0, 0, 0), new Color(0, 255, 0)));
        NorthJPanel.setBackground(new Color(240, 240, 240));
        this.add(NorthJPanel, BorderLayout.NORTH);
        JPanel InNorthJPanel = new JPanel();
        InNorthJPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 0));
        InNorthJPanel.setPreferredSize(new Dimension(1400, 150));
        InNorthJPanel.setBackground(new Color(240, 240, 240));
        NorthJPanel.add(InNorthJPanel);

        /*
         * 形状区域
         *
         * @param ShapeJPanel 形状区域的面板,界面布局
         *
         * @param InShapeJPanel 形状区域中放置形状选项的面板,放在ShapeJPanel中,流式布局
         *
         * @param InShapeLabel 形状区域中标识区域的标签,放在ShapeJPanel中
         */
        JPanel ShapeJPanel = null;
        ShapeJPanel = createJPanel(InNorthJPanel);
        ShapeJPanel.setPreferredSize(new Dimension(350, 150));
        JPanel InShapeJPanel = new JPanel();
        InShapeJPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        InShapeJPanel.setBackground(null);// 设置背景色透明
        InShapeJPanel.setOpaque(false);
        InShapeJPanel.setPreferredSize(new Dimension(300, 110));
        ShapeJPanel.add(InShapeJPanel, BorderLayout.NORTH);
        JLabel InShapeLabel = null;
        InShapeLabel = createJLabel("形状", ShapeJPanel);

        /*
         * 颜色区域
         *
         * @param ColorJPanel 颜色区域面板,界面布局
         *
         * @param InColorJPanel 颜色区域中放置颜色选项的面板，放在ColorJPanel中，流式布局
         *
         * @param InColorLabel 颜色区域中标识区域的标签，放在ColorJPanel中
         */
        JPanel ColorJPanel = null;
        ColorJPanel = createJPanel(InNorthJPanel);
        JPanel IncolorJPanel = new JPanel();
        IncolorJPanel.setPreferredSize(new Dimension(200, 110));
        IncolorJPanel.setBackground(null);// 设置背景色透明
        IncolorJPanel.setOpaque(false);
        IncolorJPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        ColorJPanel.add(IncolorJPanel, BorderLayout.NORTH);
        JLabel InColorLabel = null;
        InColorLabel = createJLabel("颜色", ColorJPanel);

        /*
         * 粗细设置区域
         *
         * @param StrokeJPanel 粗细设置区域面板，界面布局
         *
         * @param InStrokeJPanel 粗细设置区域中放置粗细选项的面板，放在StrokeJPanel中，流式布局
         *
         * @param InStrokeLabel 粗细设置区域的标签，放在StrokeJPanel中
         */
        JPanel StrokeJPanel = null;
        StrokeJPanel = createJPanel(InNorthJPanel);
        StrokeJPanel.setPreferredSize(new Dimension(50, 150));
        JPanel InStrokeJPanel = new JPanel();
        InStrokeJPanel.setPreferredSize(new Dimension(50, 110));
        InStrokeJPanel.setBackground(null);
        InStrokeJPanel.setOpaque(false);
        InStrokeJPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        StrokeJPanel.add(InStrokeJPanel, BorderLayout.NORTH);
        JLabel InStrokeLabel = null;
        InStrokeLabel = createJLabel("粗细", StrokeJPanel);

        /*
         * 用户查看区域
         *
         *
         */
        JPanel UserJPanel = null;
        UserJPanel = createJPanel(InNorthJPanel);
        UserJPanel.setPreferredSize(new Dimension(500,150));
        //使用服务器缓存中的TableModel
        onlineUserTable = new JTable(DataBuffer.onlineUserTableModel);
        registedUserTable = new JTable(DataBuffer.registedUserTableModel);

        // 取得表格上的弹出菜单对象,加到表格上
        JPopupMenu pop = getTablePop();
        onlineUserTable.setComponentPopupMenu(pop);

        //选项卡
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("在线用户列表", new JScrollPane(onlineUserTable));
        tabbedPane.addTab("已注册用户列表", new JScrollPane(registedUserTable));
        tabbedPane.setTabComponentAt(0, new JLabel("在线用户列表"));
        tabbedPane.setPreferredSize(new Dimension(500,115));
        UserJPanel.add(tabbedPane, BorderLayout.CENTER);

        final JLabel stateBar = new JLabel("", SwingConstants.RIGHT);
        stateBar.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        //用定时任务来显示当前时间
        new java.util.Timer().scheduleAtFixedRate(
                new TimerTask(){
                    DateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                    public void run() {
                        stateBar.setText("当前时间：" + df.format(new Date()) + "  ");
                    }
                }, 0, 1000);
        stateBar.setPreferredSize(new Dimension(500,35));
        UserJPanel.add(stateBar, BorderLayout.SOUTH); //把状态栏添加到窗体的南边

        /*
         * 画图板
         */
        paint = new JPanel();
        paint.setBackground(Color.white);
        paint.setPreferredSize(new Dimension(1100,620));
        this.add(paint, BorderLayout.CENTER);

        /*
         * 聊天窗口
         */
        //左上边信息显示面板
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());
        //右下连发送消息面板
        JPanel sendPanel = new JPanel();
        sendPanel.setLayout(new BorderLayout());
        //创建一个分隔窗格
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                infoPanel, sendPanel);
        splitPane.setPreferredSize(new Dimension(300,550));
        splitPane.setDividerLocation(400);
        splitPane.setDividerSize(10);
        splitPane.setOneTouchExpandable(true);
        this.add(splitPane, BorderLayout.EAST);

        otherInfoLbl = new JLabel("当前状态：");
        infoPanel.add(otherInfoLbl, BorderLayout.NORTH);

        msgListArea = new JTextArea();
        msgListArea.setLineWrap(true);
        infoPanel.add(new JScrollPane(msgListArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));

        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new BorderLayout());
        sendPanel.add(tempPanel, BorderLayout.NORTH);

        // 聊天按钮面板
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        tempPanel.add(btnPanel, BorderLayout.CENTER);

        //发送文件按钮
        JButton sendFileBtn = new JButton(new ImageIcon("images/sendPic.png"));
        sendFileBtn.setMargin(new Insets(0,0,0,0));
        sendFileBtn.setToolTipText("向对方发送文件");
        btnPanel.add(sendFileBtn);

        //要发送的信息的区域
        sendArea = new JTextArea();
        sendArea.setLineWrap(true);
        sendPanel.add(new JScrollPane(sendArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));

        // 聊天按钮面板
        JPanel btn2Panel = new JPanel();
        btn2Panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.add(btn2Panel, BorderLayout.SOUTH);
        JButton closeBtn = new JButton("关闭");
        closeBtn.setToolTipText("退出整个程序");
        btn2Panel.add(closeBtn);
        JButton submitBtn = new JButton("发送");
        submitBtn.setToolTipText("按Enter键发送消息");
        btn2Panel.add(submitBtn);
        sendPanel.add(btn2Panel, BorderLayout.SOUTH);

        /*
         * 放置按钮
         */
        String[] typeArray = { "Line", "Oval", "Rect", "RoundRect", "fill3DRect", "fillArc", "Image", "Text", "Pencil",
                "iso_Tri", "Polygon","喷枪", "Erase" };
        Color[] colorArray = { Color.red, Color.black, Color.green, Color.BLUE, new Color(255, 255, 255) };
        String[] widthArray = { "1", "3", "5" };
        JTextField text = new JTextField();
        text.setPreferredSize(new Dimension(100, 30));

        DrawListener dl = new DrawListener(paint, text, list);

        for (int i = 0; i < typeArray.length; i++) {
            if(i<12) {
                JButton button = new JButton(typeArray[i]);
                InShapeJPanel.add(button);
                button.addActionListener(dl);
            }
            else
            {
                JButton button1 = new JButton(typeArray[i]);
                InNorthJPanel.add(button1);
                button1.addActionListener(dl);
            }
        }
        for (int i = 0; i < colorArray.length; i++) {
            JButton button = new JButton();
            button.setBackground(colorArray[i]);
            button.setPreferredSize(new Dimension(30, 30));
            IncolorJPanel.add(button);
            button.addActionListener(dl);

        }
        /*
         * 添加颜色自定义按钮
         * 未完成
         */
        JButton changeButton=new JButton();
        changeButton.setBackground(Color.WHITE);
        changeButton.setPreferredSize(new Dimension(30,30));
        IncolorJPanel.add(changeButton);
        changeButton.addActionListener(dl);



        for (int i = 0; i < widthArray.length; i++) {
            JButton button = new JButton(widthArray[i]);
            InStrokeJPanel.add(button);
            button.addActionListener(dl);
        }
        InNorthJPanel.add(text);
        this.setVisible(true);

        paint.addMouseListener(dl);
        paint.addMouseMotionListener(dl);

        //发送文本消息
        sendArea.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == Event.ENTER){
                    try {
                        System.out.println("广播");
                        sendAllMsg();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    sendArea.setText("");
                }
            }
        });

        submitBtn.addActionListener(event -> {
            try {
                System.out.println("广播");
                sendAllMsg();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //发送文件
        sendFileBtn.addActionListener(event -> {
                sendFile();
        });
        //关闭窗口
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                logout();
            }
        });

    }

    /**
     * 为主面板设置皮肤
     */
    public void setLookAndFeel() {

        try {

            UIManager

                    .setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private List<Shape> list = new ArrayList<Shape>();

    public void paint(Graphics gr) {
        super.paint(gr);
        Graphics2D g = (Graphics2D) gr;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (int i = 0; i < list.size(); i++) {
            Shape shape = list.get(i);
            shape.draw((Graphics2D)paint.getGraphics());
        }
//        repaint();
    }

    /*
     * 创建表格上的弹出菜单对象，实现发信，踢人功能
     */
    private JPopupMenu getTablePop() {
        JPopupMenu pop = new JPopupMenu();// 弹出菜单对象
        JMenuItem mi_send = new JMenuItem("发信");
        // 菜单项对象
        mi_send.setActionCommand("send");// 设定菜单命令关键字
        JMenuItem mi_del = new JMenuItem("踢掉");// 菜单项对象
        mi_del.setActionCommand("del");// 设定菜单命令关键字
        // 弹出菜单上的事件监听器对象
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String s = e.getActionCommand();
                // 哪个菜单项点击了，这个s就是其设定的ActionCommand
                popMenuAction(s);
            }
        };
        mi_send.addActionListener(al);
        mi_del.addActionListener(al);// 给菜单加上监听器
        pop.add(mi_send);
        pop.add(mi_del);
        return pop;
    }

    // 处理弹出菜单上的事件
    private void popMenuAction(String command) {
        // 得到在表格上选中的行
        final int selectIndex = onlineUserTable.getSelectedRow();
        String usr_id = (String)onlineUserTable.getValueAt(selectIndex,0);
        System.out.println(usr_id);
        if (selectIndex == -1) {
            JOptionPane.showMessageDialog(this, "请选中一个用户");
            return;
        }

        if (command.equals("del")) {
            // 从线程中移除处理线程对象
            try {
                RequestProcessor.remove(DataBuffer.onlineUsersMap.get(Long.valueOf(usr_id)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (command.equals("send")) {
            final JDialog jd = new JDialog(this, true);// 发送对话框
            jd.setLayout(new FlowLayout());
//			jd.setTitle("您将对" + user.getNickname() + "发信息");
            jd.setSize(200, 100);
            final JTextField jtd_m = new JTextField(20);
            JButton jb = new JButton("发送!");
            jd.add(jtd_m);
            jd.add(jb);
            // 发送按钮的事件实现
            jb.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("发送了一条消息啊...");
                    String msg = jtd_m.getText();
//					ChatTools.sendMsg2One(selectIndex, msg);
                    try {
//                        System.out.println(DataBuffer.onlineUsersMap.get((long) onlineUserTable.get));
                        RequestProcessor.chat_sys(msg,DataBuffer.onlineUsersMap.get(Long.valueOf(usr_id)));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    jtd_m.setText("");// 清空输入框
                    jd.dispose();
                }
            });
            jd.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "未知菜单:" + command);
        }
        // 刷新表格
        SwingUtilities.updateComponentTreeUI(onlineUserTable);
    }

    // 按下发送服务器消息的按钮，给所有在线用户发送消息
    private void sendAllMsg() throws IOException {
        RequestProcessor.board(sendArea.getText());
        sendArea.setText("");
    }

    /** 把所有已注册的用户信息加载到RegistedUserTableModel中 */
    private void loadData(){
        List<User> users = new UserService().loadAllUser();
        for (User user : users) {
            DataBuffer.registedUserTableModel.add(new String[]{
                    String.valueOf(user.getId()),
                    user.getPassword(),
                    user.getNickname(),
                    String.valueOf(user.getSex())
            });
        }
    }

    /** 关闭服务器 */
    private void logout() {
        int select = JOptionPane.showConfirmDialog(ServerInfoFrame.this,
                "确定关闭吗？\n\n关闭服务器将中断与所有客户端的连接!",
                "关闭服务器",
                JOptionPane.YES_NO_OPTION);
        //如果用户点击的是关闭服务器按钮时会提示是否确认关闭。
        if (select == JOptionPane.YES_OPTION) {
            System.exit(0);//退出系统
        }else{
            //覆盖默认的窗口关闭事件动作
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        }
    }

    private JPanel createJPanel(JPanel InNorthJPanel) {
        JPanel jp = new JPanel();
        jp.setBorder(BorderFactory.createEtchedBorder(new Color(0, 0, 0), new Color(0, 255, 0)));
        jp.setPreferredSize(new Dimension(250, 150));
        jp.setBackground(new Color(240, 240, 240));
        InNorthJPanel.add(jp);
        return jp;
    }

    private JLabel createJLabel(String s, JPanel jp) {
        JLabel jl = new JLabel(s);
        jl.setHorizontalAlignment(JLabel.CENTER);// 设置对其格式剧中
        jl.setFont(new Font("楷体", Font.BOLD, 20));// 设置字体 样式 大小
        jp.add(jl, BorderLayout.SOUTH);
        return jl;
    }

    /** 把指定文本添加到消息列表文本域中 */
    public static void appendTxt2MsgListArea(String txt) {
        msgListArea.append(txt);
        //把光标定位到文本域的最后一行
        msgListArea.setCaretPosition(msgListArea.getDocument().getLength());
    }

    /** 发送文件 */
    private void sendFile() {
        for(Long id : DataBuffer.onlineUserIOCacheMap.keySet()) {
            User selectedUser = DataBuffer.onlineUsersMap.get(id);
            JFileChooser jfc = new JFileChooser();
            if (jfc.showOpenDialog(ServerInfoFrame.this) == JFileChooser.APPROVE_OPTION) {
                File file = jfc.getSelectedFile();
                User user = new User(1,"admin");
                sendFile = new FileInfo();
                sendFile.setFromUser(user);
                sendFile.setToUser(selectedUser);
                try {
                    sendFile.setSrcName(file.getCanonicalPath());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                sendFile.setSendTime(new Date());

                Request request = new Request();
                request.setAction("toSendFile");
                request.setAttribute("file", sendFile);
                try {
                    RequestProcessor.toSendFile_(request);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                appendTxt2MsgListArea("【文件消息】向 "
                        + selectedUser.getNickname() + "("
                        + selectedUser.getId() + ") 发送文件 ["
                        + file.getName() + "]，等待对方接收...\n");
            }
        }
    }
}

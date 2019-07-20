/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: MainClient
 * Author:   ITryagain
 * Date:     2018/12/4 21:31
 * Description: 客户端界面
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package PanClient;

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.Socket;

/**
 * 〈一句话功能简述〉<br> 
 * 〈客户端界面〉
 *
 * @author ITryagain
 * @create 2018/12/4
 * @since 1.0.0
 */

public class MainClient extends JFrame implements ActionListener, TreeModelListener {

    private JTree tree;
    private int ServerIP = 8888;
    private JLabel statusLabel;
    private DefaultTreeModel treeModel;
    private String oldNodeName;
    private OutputStream ous;
    private Socket client;
    private String name;
    private String stress = "D:\\";
    private String downLoadStress="D:\\下载\\";

    public static void main(String[] args){
        MainClient mc=new MainClient();
        mc.showLoginUI();
    }


    public void showLoginUI(){

        name = JOptionPane.showInputDialog("请输入用户名");
        System.out.println(name);
        loginAction();
    }

    // 登陆事件处理
    private void loginAction() {

        try {
            this.client = new Socket("localhost",ServerIP);
            if(loginServer()){
                showMainUI();
            }else{
                JOptionPane.showMessageDialog(null,"登陆失败","确定",JOptionPane.WARNING_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"连接失败","确定",JOptionPane.WARNING_MESSAGE);
        }

    }

    private boolean loginServer(){
        try{
            this.ous = this.client.getOutputStream();
            String _name=name+"\r\n";
            this.ous.write(_name.getBytes());
            this.ous.flush();
            return true;
        }catch(IOException e){
            return false;
        }
    }

    //显示操作窗口
    private void showMainUI() {
        JFrame frame=new JFrame("网盘");
        Container contentPane = frame.getContentPane();

        DefaultMutableTreeNode root = new DefaultMutableTreeNode(name);
        tree = new JTree(root);
        tree.addMouseListener(new MyTreeMouseListener(){
            public void mousePressed(MouseEvent e) {
                try {
                    JTree tree = (JTree) e.getSource();
                    int rowLocation = tree.getRowForLocation(e.getX(), e.getY());
                    TreePath treepath = tree.getPathForRow(rowLocation);
                    TreeNode treenode = (TreeNode) treepath.getLastPathComponent();
                    oldNodeName = treenode.toString();
                    String path = treepath.toString();
                    path=path.replaceAll(", ","\\\\");
                    path=path.substring(1,path.length()-1);
                    System.out.println("Listener"+" "+oldNodeName+" "+path+" "+treepath);
                } catch (NullPointerException ne) {

                }
            }
        });
        treeModel = (DefaultTreeModel)tree.getModel();
        treeModel.addTreeModelListener(this);
        tree.setEditable(true);
        tree.getCellEditor().addCellEditorListener(new MyTreeCellEditorListener(tree));
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(tree);

        JPanel toolBarPanel = new JPanel();
        JButton b = new JButton("新建文件夹");
        b.addActionListener(this);
        toolBarPanel.add(b);
        b = new JButton("删除文件夹/文件");
        b.addActionListener(this);
        toolBarPanel.add(b);
        b = new JButton("上传文件");
        b.addActionListener(this);
        toolBarPanel.add(b);
        b = new JButton("下载文件");
        b.addActionListener(this);
        toolBarPanel.add(b);

        statusLabel = new JLabel("Action");
        contentPane.add(toolBarPanel, BorderLayout.NORTH);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(statusLabel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
        frame.requestFocus();
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tree.setRootVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("新建文件夹")) {
            String fileName = JOptionPane.showInputDialog("请输入文件夹名");
            DefaultMutableTreeNode parentNode = null;
            DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(fileName);
            newNode.setAllowsChildren(true);

            TreePath parentPath = tree.getSelectionPath();

            // 取得新节点的父节点
            if(parentPath==null){
                parentNode = (DefaultMutableTreeNode)tree.getModel().getRoot();
                parentPath = new TreePath(this.name);
            }else{
                parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
            }
            //判断选中对象是否为文件夹
            String selectName = parentNode.toString();
            if(selectName.contains(".")){
                parentNode=(DefaultMutableTreeNode)parentNode.getParent();
                parentPath=parentPath.getParentPath();
            }

            //获取文件夹保存路径
            String path = getSendPath(parentPath);
            newFile(path+"\\"+fileName);

            //System.out.println("parentNode:"+parentNode.toString());
            // 由DefaultTreeModel的insertNodeInto（）方法增加新节点
            treeModel.insertNodeInto(newNode, parentNode, parentNode.getChildCount());

            // tree的scrollPathToVisible()方法在使Tree会自动展开文件夹以便显示所加入的新节点。若没加这行则加入的新节点
            // 会被 包在文件夹中，你必须自行展开文件夹才看得到。
            tree.scrollPathToVisible(new TreePath(newNode.getPath()));
            tree.setSelectionPath(new TreePath(newNode.getPath()));
            statusLabel.setText("新增节点成功");
        }
        if (e.getActionCommand().equals("删除文件夹/文件")) {
            TreePath treepath = tree.getSelectionPath();
            if (treepath != null) {
                // 下面两行取得选取节点的父节点.
                DefaultMutableTreeNode selectionNode = (DefaultMutableTreeNode) treepath.getLastPathComponent();
                TreeNode parent = selectionNode.getParent();
                if (parent != null) {
                    // 由DefaultTreeModel的removeNodeFromParent()方法删除节点，包含它的子节点。
                    treeModel.removeNodeFromParent(selectionNode);
                    statusLabel.setText("删除节点成功");
                }
                String path=getSendPath(treepath);
                deleteFile(path);
            }
        }
        if (e.getActionCommand().equals("上传文件")) {
            chooseSendFile();
           /* // 下面一行，由DefaultTreeModel的getRoot()方法取得根节点.
            DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) treeModel.getRoot();

            // 下面一行删除所有子节点.
            rootNode.removeAllChildren();

            // 删除完后务必运行DefaultTreeModel的reload()操作，整个Tree的节点才会真正被删除.
            treeModel.reload();
            statusLabel.setText("清除所有节点成功");*/
        }
        if(e.getActionCommand().equals("下载文件")){
            chooseDownLoadFile();
        }
    }

    @Override
    /*
     *  修改文件名字
     */
    public void treeNodesChanged(TreeModelEvent e) {
        TreePath treePath = e.getTreePath();
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) treePath.getLastPathComponent();

        try {
            int[] index = e.getChildIndices();
            node = (DefaultMutableTreeNode) node.getChildAt(index[0]);
        } catch (NullPointerException exc) {
        }
        statusLabel.setText(oldNodeName + "更改数据为:" + node.getUserObject());
        String path = treePath.toString();
        path=path.replaceAll(", ","\\\\");
        String NewName=path.substring(1,path.length()-1)+"\\"+node.toString();
        path=path.substring(1,path.length()-1)+"\\"+oldNodeName;

        System.out.println(treePath+"\\"+node.toString()+"  "+path);

        ChangeFileName(NewName,path);
    }

    //选择上传文件
    public void chooseSendFile(){
        JFileChooser jfc=new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jfc.showDialog(new JLabel(),"选择");
        File file = jfc.getSelectedFile();

        System.out.println(jfc.getSelectedFile().getName());

        DefaultMutableTreeNode parentNode = null;
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(jfc.getSelectedFile().getName());
        newNode.setAllowsChildren(true);
        TreePath parentPath = tree.getSelectionPath();

        // 取得新节点的父节点
        if(parentPath==null){
            parentNode = (DefaultMutableTreeNode)tree.getModel().getRoot();
            parentPath = new TreePath(this.name);
        }else{
            parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
        }
        //判断选中对象是否为文件夹
        String selectName = parentNode.toString();
        if(selectName.contains(".")){
            parentNode=(DefaultMutableTreeNode)parentNode.getParent();
            parentPath=parentPath.getParentPath();
        }

        //获取文件保存路径
        String path = getSendPath(parentPath);
        if(file.isDirectory()){
            System.out.println("文件夹:"+file.getAbsolutePath());
        }else if(file.isFile()){
            System.out.println("文件:"+file.getAbsolutePath());
           try {
                sendFile(file,path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 由DefaultTreeModel的insertNodeInto（）方法增加新节点
        treeModel.insertNodeInto(newNode, parentNode, parentNode.getChildCount());

        // tree的scrollPathToVisible()方法在使Tree会自动展开文件夹以便显示所加入的新节点。若没加这行则加入的新节点
        // 会被 包在文件夹中，你必须自行展开文件夹才看得到。
        tree.scrollPathToVisible(new TreePath(newNode.getPath()));
        tree.setSelectionPath(new TreePath(newNode.getPath()));
        statusLabel.setText("新增节点成功");
    }

    public void sendFile(File file,String path) throws IOException{
        String fileName = "~upLoad#"+path+"\\"+file.getName()+"\r\n";
        System.out.println(fileName);
        this.ous.write(fileName.getBytes());
        this.ous.flush();
        FileSendThread send_socket = new FileSendThread(8889,file,path,statusLabel);
        send_socket.start();
    }

    private String getSendPath(TreePath parentPath){
        String path = parentPath.toString();
        path=path.replaceAll(", ","\\\\");
        path=path.substring(1,path.length()-1);
        return this.stress+path;
    }

    //选择下载文件
    public void chooseDownLoadFile() {
        TreePath parentPath = tree.getSelectionPath();
        System.out.println("path:"+parentPath.toString());

        String path = getDownLoadPath(parentPath);
        try {
            downloadFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //下载文件
    public void downloadFile(String path) throws IOException{
        String file = "~downLoad#"+this.stress+path+"\r\n";
        System.out.println(file);
        this.ous.write(file.getBytes());
        this.ous.flush();

        JFileChooser jfc=new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY );
        jfc.showDialog(new JLabel(),"选择");
        String pathName = jfc.getSelectedFile().getAbsolutePath();
        System.out.println("pathName:"+pathName);

        //String fileName = this.downLoadStress+path.substring(path.lastIndexOf("\\")+1);
        String fileName = pathName+"\\"+path.substring(path.lastIndexOf("\\")+1);
        System.out.println(fileName);
        FileDownLoadThread downLoad_socket = new FileDownLoadThread(8890,fileName);
        downLoad_socket.start();
    }

    private String getDownLoadPath(TreePath parentPath){
        String path = parentPath.toString();
        path=path.replaceAll(", ","\\\\");
        path=path.substring(1,path.length()-1);
        System.out.println("下载文件路径:"+path);
        return path;
    }

    private void newFile(String path){
        String fileName = "~new#"+path+"\r\n";
        System.out.println(fileName);
        try {
            this.ous.write(fileName.getBytes());
            this.ous.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteFile(String path){
        String fileName = "~delete#"+path+"\r\n";
        System.out.println(fileName);
        try {
            this.ous.write(fileName.getBytes());
            this.ous.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ChangeFileName(String NewName,String path){

        String fileName = "~change#"+this.stress+NewName+"@"+this.stress+path+"\r\n";
        System.out.println(fileName);
        try {
            this.ous.write(fileName.getBytes());
            this.ous.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void treeNodesInserted(TreeModelEvent e) {
    }

    @Override
    public void treeNodesRemoved(TreeModelEvent e) {
    }

    @Override
    public void treeStructureChanged(TreeModelEvent e) {
    }

}

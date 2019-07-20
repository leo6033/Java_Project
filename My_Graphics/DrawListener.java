package My_Graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;

public class DrawListener extends MouseAdapter implements ActionListener {

	private Color color = Color.black;// 声明颜色属性，存储用户选择的颜色
	private int width = 1;// 声明粗细属性，存储用户选择的粗细
	private String type = "Line";// 声明图形属性，存储用户选择的图形
	private int x1, y1, x2, y2, x3 = 0, y3 = 0, x4 = 0, y4 = 0, x5, y5;// 声明坐标值属性，存储鼠标按下和释放的坐标值
	private Graphics2D g;// 声明画笔类属性，组件是画出来的，现在要在组件上画图形，Graphics从组件上获取
	private DrawMain dm;// 声明画图程序窗体组件属性
	private JTextField text;// 获取文本框内容
	private double H = 100;// 等腰三角形的高度
	private int num = 0;
	private List<Shape> list;
	private ImageIcon i = new ImageIcon("C:\\Users\\long452a\\Desktop\\a1.jpg");

	/**
	 * 构造方法
	 * 
	 * @param dm画图程序的窗体组件对象
	 */
	public DrawListener(DrawMain dm, JTextField text, List<Shape> list) {
		this.dm = dm;
		this.text = text;
		this.list = list;
	}

	/**
	 * 点击按钮时执行的事件处理方法
	 * 
	 * @param e对象中存储了事件源对象的信息和动作信息
	 */
	public void actionPerformed(ActionEvent e) {
		String text = e.getActionCommand();
		if (text.equals("")) {
			JButton button = (JButton) e.getSource();
			color = button.getBackground();
		} else if (text.equals("1") || text.equals("3") || text.equals("5")) {
			width = Integer.parseInt(text);
		} else {
			type = text;
		}
		// System.out.println(color + ">>" + width + ">>" + type);
	}

	/**
	 * Invoked when the mouse button has been clicked (pressed and released) on a
	 * component.
	 */
	public void mouseClicked(MouseEvent e) {
		x4 = x2;
		y4 = y2;
	}

	/**
	 * Invoked when a mouse button has been pressed on a component.
	 */
	public void mousePressed(MouseEvent e) {
		x1 = e.getX() + 7;
		y1 = e.getY() + 183;
		if (y1 < 183)
			y1 = 183;
		g = (Graphics2D) dm.getGraphics();// 从窗体上获取画笔对象
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);// 设置画笔抗锯齿
		g.setColor(color);// 设置画笔颜色
		g.setStroke(new BasicStroke(width));// 设置画笔线条粗细

	}

	/**
	 * Invoked when a mouse button has been released on a component.
	 */
	public void mouseReleased(MouseEvent e) {
		x2 = e.getX() + 7;
		y2 = e.getY() + 183;
		if (y2 < 183)
			y2 = 183;
		if (type.equals("iso_Tri")) {
			if (x1 == x2) {
				g.drawLine(x1, y1, x2, y2);
				g.drawLine(x1, y1, x1 + (int) H, (y2 + y1) / 2);
				g.drawLine(x2, y2, x1 + (int) H, (y2 + y1) / 2);
				list.add(new Shape(x1, y1, x2, y2, width, color, type, i, dm, text));
				list.add(new Shape(x1, y1, x1 + (int) H, (y2 + y1) / 2, width, color, type, i, dm, text));
				list.add(new Shape(x2, y2, x1 + (int) H, (y2 + y1) / 2, width, color, type, i, dm, text));
			} else if (y1 == y2) {
				g.drawLine(x1, y1, x2, y2);
				g.drawLine(x1, y1, (x1 + x2) / 2, y1 + (int) H);
				g.drawLine(x2, y2, (x1 + x2) / 2, y1 + (int) H);
				list.add(new Shape(x1, y1, x2, y2, width, color, type, i, dm, text));
				list.add(new Shape(x1, y1, x1 + (x1 + x2) / 2, y1 + (int) H, width, color, type, i, dm, text));
				list.add(new Shape(x2, y2, x1 + (x1 + x2) / 2, y1 + (int) H, width, color, type, i, dm, text));
			} else {
				double a = Math.atan((double) (x2 - x1) / (double) (y2 - y1));
				double x3 = (double) (x1 + x2) / 2 + H * Math.cos(a);
				double y3 = (double) (y1 + y2) / 2 - H * Math.sin(a);
				g.drawLine(x1, y1, x2, y2);
				g.drawLine(x1, y1, (int) x3, (int) y3);
				g.drawLine(x2, y2, (int) x3, (int) y3);
				list.add(new Shape(x1, y1, x2, y2, width, color, type, i, dm, text));
				list.add(new Shape(x1, y1, x1 + (int) x3, (int) y3, width, color, type, i, dm, text));
				list.add(new Shape(x2, y2, (int) x3, (int) y3, width, color, type, i, dm, text));
			}
		} else if (type.equals("Polygon")) {
			if (num == 0) {
				g.drawLine(x1, y1, x2, y2);
				list.add(new Shape(x1, y1, x2, y2, width, color, type, i, dm, text));
				x5 = x2;
				y5 = y2;
			}
			num++;
			if (num == 1) {
				x3 = x1;
				y3 = y1;
			}
			if (x2 == x4 && y2 == y4) {
				g.drawLine(x1, y1, x3, y3);
				list.add(new Shape(x1, y1, x3, y3, width, color, type, i, dm, text));
				num = 0;
			} else {
				g.drawLine(x2, y2, x5, y5);
				list.add(new Shape(x2, y2, x5, y5, width, color, type, i, dm, text));
				x5 = x2;
				y5 = y2;
			}
		} else {
			Shape s = new Shape(x1, y1, x2, y2, width, color, type, i, dm, text);
			s.draw(g);
			list.add(s);
		}
	}

	public void mouseDragged(MouseEvent e) {
		if (type.equals("Pencil")) {
			x2 = e.getX() + 7;
			y2 = e.getY() + 183;
			if (y2 < 183)
				y2 = 183;
			Shape s = new Shape(x1, y1, x2, y2, width, color, type, i, dm, text);
			s.draw(g);
			list.add(s);
			x1 = x2;
			y1 = y2;
		} else if (type.equals("Erase")) {
			x2 = e.getX() + 7;
			y2 = e.getY() + 183;
			if (y2 < 183)
				y2 = 183;
			Shape s = new Shape(x1, y1, x2, y2, width, Color.WHITE, type, i, dm, text);
			s.draw(g);
			list.add(s);
			x1 = x2;
			y1 = y2;
		} else if (type.equals("喷枪")) // 难点
		{
			Random rand = new Random();// 实例化一个随机数类的对象
			int size = rand.nextInt(50);// 随机决定要画的点数
			x2 = e.getX() + 7;
			y2 = e.getY() + 183;
			for (int j = 0; j < size; j++) {
				// 在0-7之间可以取50次
				int x = rand.nextInt(10);
				int y = rand.nextInt(10);
				// 不断改变（x1,y1）的坐标值，实现在(x1,y1)的周围画点
				Shape s = new Shape(x2 + x, y2 + y, x2 + x, y2 + y, width, color, type, i, dm, text);
				s.draw(g);
				list.add(s);
				x1 = x2;
				y1 = y2;
			}
		}
	}

}

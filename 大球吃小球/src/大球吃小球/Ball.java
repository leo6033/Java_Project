package 大球吃小球3;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Ball {
	public int size;
	public int x;
	public int y;
	public int speedX;
	public int speedY;
	public Color c;
	public JPanel jf;
	public ArrayList<Ball> li;
	public boolean flag;
	public Bool bool;
	public boolean life=true;

	public Ball(int size, int x, int y, int speedX, int speedY, Color c, JPanel jf, ArrayList<Ball> li,Bool bool) {
		this.size = size;
		this.x = x;
		this.y = y;
		this.speedX = speedX;
		this.speedY = speedY;
		this.c = c;
		this.jf = jf;
		this.li = li;
		this.flag = true;
		this.bool=bool;
	}

	public Ball(int size, int x, int y, int speedX, int speedY, Color c, JPanel jf, ArrayList<Ball> li) {
		this.size = size;
		this.x = x;
		this.y = y;
		this.speedX = speedX;
		this.speedY = speedY;
		this.c = c;
		this.jf = jf;
		this.li = li;
		this.flag = true;
	}
	
	public void draw(Graphics g) {
		g.setColor(c);
		g.fillOval(x, y, size, size);
	}

	public void move() {
		x += speedX;
		y += speedY;
		if (size < 100) {
			if (x + size > jf.getWidth()) {
				speedX = -speedX;
				x = jf.getWidth() - size;
			} else if (x <= 0) {
				speedX = -speedX;
				x = 0;
			}
			if (y + size > jf.getHeight()) {
				speedY = -speedY;
				y = jf.getHeight() - size;
			} else if (y <= 0) {
				speedY = -speedY;
				y = 0;
			}
		}
	}

	public void crash() {
		for (int i = 0; i < li.size(); i++) {
			if (this != li.get(i)) {
				Ball bl = li.get(i);
				if (((this.x + this.size / 2 - bl.x - bl.size / 2) * (this.x + this.size / 2 - bl.x - bl.size / 2)
						+ (this.y + this.size / 2 - bl.y - bl.size / 2)
								* (this.y + this.size / 2 - bl.y - bl.size / 2)) < (this.size / 2 + bl.size / 2)
										* (this.size / 2 + bl.size / 2)) {
					if (this.size < bl.size) {

						bl.size += this.size / 10;
						this.flag = false;
					} else if ((this.size > bl.size)) {
						bl.flag = false;
						this.size += bl.size / 10;
					}
				}
				if (this.flag == false) {
					li.remove(this);
				} else if (bl.flag == false) {
					li.remove(bl);
				}

			}
		}
	}

	public void crash(Ball ball) {
		for (int i = 0; i < li.size(); i++) {
			Ball bl = li.get(i);
			if (((ball.x + ball.size / 2 - bl.x - bl.size / 2) * (ball.x + ball.size / 2 - bl.x - bl.size / 2)
					+ (ball.y + ball.size / 2 - bl.y - bl.size / 2)
							* (ball.y + ball.size / 2 - bl.y - bl.size / 2)) < (ball.size / 2 + bl.size / 2)
									* (ball.size / 2 + bl.size / 2)) {
				if (ball.size < bl.size) {

					bl.size += ball.size / 10;
					ball.flag = false;
				} else if ((ball.size > bl.size)) {
					bl.flag = false;
					ball.size += bl.size / 10;
				}
			}
			if (ball.flag == false) {			
				this.life=false;
				JOptionPane.showMessageDialog(null, "你被吃了");
				System.exit(0);
			} else if (bl.flag == false) {
				li.remove(bl);
			}
		}
	}

	public void move1() {
		// 左
		if (bool.getbool(0) && !bool.getbool(1) && !bool.getbool(2) && !bool.getbool(3)) {
			this.x -= 4;
		} // 右
		else if (!bool.getbool(0) && bool.getbool(1) && !bool.getbool(2) && !bool.getbool(3)) {
			this.x += 4;
		} // 上
		else if (!bool.getbool(0) && !bool.getbool(1) && bool.getbool(2) && !bool.getbool(3)) {
			this.y -= 4;
		} // 下
		else if (!bool.getbool(0) && !bool.getbool(1) && !bool.getbool(2) && bool.getbool(3)) {
			this.y += 4;
		} // 左上
		else if (bool.getbool(0) && !bool.getbool(1) && bool.getbool(2) && !bool.getbool(3)) {
			this.x -= 4;
			this.y -= 4;
		} // 左下
		else if (bool.getbool(0) && !bool.getbool(1) && !bool.getbool(2) && bool.getbool(3)) {
			this.y += 4;
			this.x -= 4;
		} // 右上
		else if (!bool.getbool(0) && bool.getbool(1) && bool.getbool(2) && !bool.getbool(3)) {
			this.y -= 4;
			this.x += 4;
		} // 右下
		else if (!bool.getbool(0) && bool.getbool(1) && !bool.getbool(2) && bool.getbool(3)) {
			this.y += 4;
			this.x += 4;
		}
		if (this.x + size > jf.getWidth()) {
			//bool[1]=false;
			bool.change_bool(1, false);
		} else if (this.x <= 0) {
			//bool[0]=false;
			bool.change_bool(0, false);
		}
		if (this.y + size > jf.getHeight()) {
			//bool[3]=false;
			bool.change_bool(3, false);
		} else if (this.y <= 0) {
			//bool[2]=false;
			bool.change_bool(2, false);
		}
	}
}

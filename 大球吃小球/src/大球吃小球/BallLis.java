package ¥Û«Ú≥‘–°«Ú3;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class BallLis extends Thread {
	private ArrayList<Ball> li = new ArrayList<Ball>();
	private JPanel jp;
	private Ball ball;

	public BallLis(ArrayList<Ball> li, JPanel jp,Ball ball) {
		this.li = li;
		this.jp = jp;
		this.ball=ball;
	}

	public void run() {
		while (ball.life) {
			int size;
			if (20 - li.size() > 0) {
				size = new Random().nextInt(20 - li.size()) + 10;
			} else {
				size = 10;
			}
			int x = new Random().nextInt(jp.getWidth());
			int y = 0;
			int speedX = new Random().nextInt(10) + 10;
			int speedY = new Random().nextInt(10) + 5;
			Color c = new Color(new Random().nextInt(256), new Random().nextInt(256), new Random().nextInt(256));

			Ball bl = new Ball(size, x, y, speedX, speedY, c, jp, li);
			li.add(bl);

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}

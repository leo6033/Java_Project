package ¥Û«Ú≥‘–°«Ú3;

import java.util.ArrayList;

import javax.swing.JPanel;

public class ThreadBall extends Thread {

	private ArrayList<Ball> li;
	private JPanel jp;
	private Ball ball;

	public ThreadBall(ArrayList<Ball> li,JPanel jp,Ball ball) {
		this.li = li;
		this.jp=jp;
		this.ball=ball;
	}

	public void run() {
		while(ball.life) {
			jp.repaint();
			try {
				Thread.sleep(80);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}

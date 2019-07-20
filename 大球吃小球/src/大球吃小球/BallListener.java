package 大球吃小球3;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class BallListener extends KeyAdapter {

	private Ball ball;
	private ArrayList<Ball> li;

	public BallListener(Ball ball, ArrayList<Ball> li) {
		this.ball = ball;
		this.li = li;
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			// System.out.println("向左移动");
			//ball.move(2);
			ball.bool.change_bool(0, true);
			break;
		case KeyEvent.VK_RIGHT:
			// System.out.println("向上移动");
			//ball.move(4);
			ball.bool.change_bool(1, true);
			break;
		case KeyEvent.VK_UP:
			// System.out.println("向右移动");
			//ball.move(1);
			ball.bool.change_bool(2, true);
			break;
		case KeyEvent.VK_DOWN:
			// System.out.println("向下移动");
			ball.bool.change_bool(3, true);
			break;
		}
		//ball.move1();
	}
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode())
		{
		case KeyEvent.VK_LEFT:
			// System.out.println("向左移动");
			//ball.move(2);
			ball.bool.change_bool(0, false);
			break;
		case KeyEvent.VK_RIGHT:
			// System.out.println("向上移动");
			//ball.move(4);
			ball.bool.change_bool(1, false);
			break;
		case KeyEvent.VK_UP:
			// System.out.println("向右移动");
			//ball.move(1);
			ball.bool.change_bool(2, false);
			break;
		case KeyEvent.VK_DOWN:
			// System.out.println("向下移动");
			ball.bool.change_bool(3, false);
			break;
		}
		//ball.move1(bool);
	}
}

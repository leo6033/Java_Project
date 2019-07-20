package ¥Û«Ú≥‘–°«Ú3;

public class Myball extends Thread{
	
	public Ball ball;
	
	public Myball(Ball ball)
	{
		this.ball=ball;
	}
	public void run() {
		while(true) {
			ball.move1();
			try {
				Thread.sleep(80);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

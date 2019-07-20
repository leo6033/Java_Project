package 大球吃小球3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BallUI extends JPanel {

	private ArrayList<Ball> li = new ArrayList<Ball>();
	private Ball ba;

	public static void main(String[] args) {
		BallUI bu = new BallUI();
		bu.UI();
	}

	public void UI() {
		JFrame jf = new JFrame();
		jf.setLocationRelativeTo(null); 
		jf.setResizable(false);
		jf.setSize(700, 700);
		this.setSize(jf.getWidth(), jf.getHeight());
		jf.setTitle("大球吃小球");
		this.setFocusable(true);

		this.setPreferredSize(new Dimension(jf.getWidth(), jf.getHeight()));
		jf.add(this);

		
		Bool bool=new Bool();
		ba=new Ball(30,this.getWidth()/2-15,this.getHeight()-30,0, 0, Color.BLACK, this, li,bool);
		
		BallLis ball = new BallLis(li, this,ba);
		ball.start();
		ThreadBall tb = new ThreadBall(li, this,ba);
		tb.start();
		
		BallListener bl=new BallListener(ba,li);
		this.addKeyListener(bl);
		//ba.move1();
		Myball mb=new Myball(ba);
		mb.start();


		jf.setVisible(true);
	}

	private Image iImage = createImage(this.getWidth(), this.getWidth());

	public void paint(Graphics g) {
		super.paint(g);
		if (iImage == null)
			iImage = this.createImage(this.getWidth(), this.getWidth());
		Graphics gBuffer = iImage.getGraphics();
		gBuffer.setColor(getBackground());
		gBuffer.fillRect(0, 0, this.getWidth(), this.getHeight());
		for (int i = 0; i < li.size(); i++) {
			Ball ball = li.get(i);
			ball.crash();
			ball.move();
			ball.draw(gBuffer);	
			ba.draw(gBuffer);
			ba.move();
			ba.crash(ba);
		}
		//run();
		g.drawImage(iImage, 0, 0, this);
	}

	public void update(Graphics g) {
		paint(g);

	}
	
	public void run()
	{
		ba.move1();
	}

}

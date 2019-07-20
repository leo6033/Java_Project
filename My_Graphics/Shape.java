package My_Graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JTextField;

public class Shape {

	private int x1, y1, x2, y2, width;
	private Color color;
	private String type;
	private ImageIcon i;
	private DrawMain dm;
	private JTextField text;

	public Shape(int x1, int y1, int x2, int y2, int width, Color color, String type, ImageIcon i, DrawMain dm,
			JTextField text) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.width = width;
		this.color = color;
		this.type = type;
		this.i = i;
		this.dm = dm;
		this.text = text;
	}


	public void draw(Graphics2D g) {
		g.setColor(color);
		g.setStroke(new BasicStroke(width));
		if (type.equals("Line") || type.equals("Pencil") || type.equals("iso_Tri") || type.equals("Polygon")
				|| type.equals("Erase") || type.equals("≈Á«π")) {
			g.drawLine(x1, y1, x2, y2);
		} else if (type.equals("Oval")) {
			g.fillOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
		} else if (type.equals("Rect")) {
			g.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
		} else if (type.equals("RoundRect")) {
			g.drawRoundRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1),
					Math.abs(x2 - x1) / 3, Math.abs(y2 - y1) / 3);
		} else if (type.equals("fill3DRect")) {
			g.fill3DRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1), true);
		} else if (type.equals("fillArc")) {
			double r = ((x2 - x1) * (x2 - x1) + y1 * y1) / (2 * y1);
			double m = Math.asin((double) (x2 - x1) / r);
			g.fillArc(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), (int) r, 0, (int) (m * 180 / Math.PI));
		} else if (type.equals("Image")) {
			g.drawImage(i.getImage(), Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1), dm);
		} else if (type.equals("Text")) {
			g.drawString(text.getText(), x1, y1);
		}
	}

}

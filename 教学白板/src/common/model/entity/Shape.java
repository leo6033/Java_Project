/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: Shape
 * Author:   ITryagain
 * Date:     2019/5/19 1:03
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package common.model.entity;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author ITryagain
 * @create 2019/5/19
 * @since 1.0.0
 */

public class Shape implements Serializable {
    private static final long serialVersionUID = -2457158398616290054L;
    private int x1, y1, x2, y2, width;
    private  Color color;
    private String type;
    private  ImageIcon i;
    private transient JPanel dm;
    private JTextField text;

    public Shape(int x1, int y1, int x2, int y2, int width, Color color, String type, ImageIcon i, JPanel dm,
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

    public void print(){
        System.out.println(x1+" "+x2+" "+" "+y1+" " +y2);
    }

    public void draw(Graphics2D g) {
        g.setColor(color);
        g.setStroke(new BasicStroke(width));
        if (type.equals("Line") || type.equals("Pencil") || type.equals("iso_Tri") || type.equals("Polygon")
                || type.equals("Erase") || type.equals("喷枪")) {
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
            double r = ((double)(x2 - x1) * (x2 - x1) + (double)y1 * y1) / (double)(2 * y1);
            double m = Math.asin((double) (x2 - x1) / r);
            g.fillArc(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), (int) r, 0, (int) (m * 180 / Math.PI));
        } else if (type.equals("Image")) {
            g.drawImage(i.getImage(), Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1), dm);
//            System.out.println("draw");
        } else if (type.equals("Text")) {
            g.drawString(text.getText(), x1, y1);
            System.out.println(text.getText());
        }
    }

    @Override
    public String toString() {
        return this.getClass().getName()
                + "[x1=" + this.x1
                + ",x2=" + this.x2
                + ",y1=" + this.y1
                + ",y2=" + this.y2
                + ",width=" + this.width
                + ",color=" + this.color
                + ",type=" + this.type
                + ",i=" + this.i
                + ",dm=" + this.dm
                + ",text=" + this.text
                + "]";
    }
}
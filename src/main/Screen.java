package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JComponent;
import tools.Line;

public class Screen extends JComponent{
	
	private static final long serialVersionUID = 2019890073364171997L;
	private ArrayList<Line> linesToDraw = null;
	private final int width;
	private final int height;
	
	public Screen(int x, int y){
		width = x;
		height = y;
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(width, height);
	}
	
	public synchronized void setLinesToDraw(ArrayList<Line> newLines){
		linesToDraw = newLines;
	}

	public synchronized void paint(Graphics g){
		if (linesToDraw != null){
			Graphics2D g2d = (Graphics2D) g;
			
			// flip y axis (because of awt graphics axis system)
			g2d.scale(1, -1);
			g2d.translate(0, -height);

			for (Line temp : linesToDraw) {
				double x1 = temp.data.getX1();
				double x2 = temp.data.getX2();
				double y1 = temp.data.getY1();
				double y2 = temp.data.getY2();

				g.setColor(temp.color);
				g2d.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
			}
		}
		notify(); // render thread is waiting
	}
}

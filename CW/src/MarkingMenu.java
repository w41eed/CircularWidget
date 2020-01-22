import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;

public class MarkingMenu extends JPopupMenu {
	
	
	private int x[] = new int[3];
	private int y[] = new int[3];
	private JMenu m[] = new JMenu[3];
	private Component[] comps;
	private int menuc = 0;
	
	public MarkingMenu() {
		
		this.setPopupSize(150, 150);
		this.setBorderPainted(false);
		
		getLinePoints(75,75, 75);
		
	}
	
	/*
	public JMenu add(JMenu menu) {
		
		m[menuc] = menu;
		menuc++;
		
		
		
		return menu;
		
	}
	*/
	
	//Code implemented using help from https://stackoverflow.com/questions/18610350/android-dividing-circle-into-n-equal-parts-and-know-the-coordinates-of-each-divi
	public void getLinePoints(int cx, int cy, int r) {
		double angle = 0;
		
		for(int i =0; i < 3; i++) {
			
			angle = i * (360/3);
			
			x[i] = (int) (cx + r * Math.cos(Math.toRadians(angle)));
			y[i] = (int) (cy + r * Math.sin(Math.toRadians(angle)));
			
		}
		
	}
	
	//change position of menus
	private void posMenu() {
		
		comps = this.getComponents();
		
		comps[0].setLocation(60, 15);
		comps[0].setSize(60, 50);
		
		comps[1].setLocation(15, 40);
		comps[1].setSize(40, 70);
		
		
		comps[2].setLocation(65, 70);
		comps[2].setSize(60, 60);
	}
	
	

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		
		g2.setColor(Color.WHITE);
		g2.fillOval(0,0, this.getWidth(), this.getWidth());
		g2.setColor(Color.BLACK);
		g2.drawOval(0,0, this.getWidth(), this.getWidth());
		
		for(int i = 0; i < 3; i++) {
			g2.drawLine(x[i], y[i], 65, 65);
		}
		
		posMenu();
		
		/*
		g2.drawString(m[0].getText(), 90, 45);
		g2.drawString(m[1].getText(), 20, 75);
		g2.drawString(m[2].getText(), 75, 110);
		*/
		
	}
	
	
	
	

}

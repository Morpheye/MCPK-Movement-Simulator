package mcpk.visualiser;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import mcpk.Parser;
import mcpk.Player;

@SuppressWarnings("serial")
public class VisualiserPanel extends JPanel {
	
	Player player;
	Parser parser;
	
	public VisualiserPanel() {
		this.setBackground(Color.BLACK);
		
		Timer timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
		});
		
		timer.start();
		
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(Color.WHITE);
		
		float f = 48.07f;
		float[] facings = {91.71f, f, f, f, f, f, f, f, f, f, f, f}; //angles
		Blockage[] blockages = {new Blockage(-1.1375, 1.2, -0.725, 1.7),
				new Blockage(-0.7, 1.7, -1.6, 1)}; //butterfly block blockage
		
		try {
			parser = new Parser();
			player = new Player();
			parser.parse(player, "f(131.22) wa.d(2) sta(9) stop | wj.sa(12) w.sa | sj.wd sneaksprintair.d wa.wd sa.wd(9) s.wd");
			player.x = -0.545;
			player.z = 1.7625;
			System.out.println(player.x + " " + player.z);
			
			double lastX = player.x;
			double lastZ = player.z;
			
			for (int i=0; i<facings.length; i++) {
				if (i==0) {
					parser.parse(player, "sprintjump(1," + facings[i] + ")");
				} else {
					parser.parse(player,  "sprintair.wa(1," + facings[i] + ")");
				}
				
				System.out.println(player.x + " " + player.z);
				int lastdrawx = this.getPreferredSize().width/2 - (int) (lastX*50);
				int lastdrawz = this.getPreferredSize().height/2 - (int) (lastZ*50);
				int drawx = this.getPreferredSize().width/2 - (int) (player.x*50);
				int drawz = this.getPreferredSize().height/2 - (int) (player.z*50);
				g2d.setColor(Color.WHITE);
				for (Blockage b : blockages) {
					if (b.collidedX(player.x) && (b.collidedZ(lastZ) || b.collidedZ(player.z))) {
						g2d.setColor(Color.RED);
						System.out.println("Hit blockage");
						break;
					}
				}
				
				g2d.drawLine(lastdrawx, lastdrawz, drawx, drawz);
				lastX = player.x;
				lastZ = player.z;
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	class Blockage {
		public double x, z, xw, zw;
		public Blockage(double x, double z, double xwidth, double zwidth) {
			this.x = x;
			this.z = z;
			this.xw = xwidth;
			this.zw = zwidth;
		}
		
		boolean collidedX(double xPos) {
			return (xPos > x && xPos < x+xw) || (xPos < x && xPos > x+xw);
		}
		boolean collidedZ(double zPos) {
			
			return (zPos > z && zPos < z+zw) || (zPos < z && zPos > z+zw);
		}
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(600, 600);
	}
	
}

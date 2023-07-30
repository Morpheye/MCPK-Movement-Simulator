package mcpk;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

import mcpk.visualiser.VisualiserPanel;

final class Visualiser {

	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		frame.setTitle("Visualiser");
		frame.setVisible(true);
		frame.setBackground(Color.black);
		frame.getContentPane().setPreferredSize(new Dimension(600, 600));
		frame.getContentPane().add(new VisualiserPanel());
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		
	}

}

package view;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class AnotherPanel extends JPanel {
	
	private static final long serialVersionUID = 7439880643820569658L;
	private JButton button;
	private int X;
	private int Y;
	public AnotherPanel() {
		X = 200;
		Y = 200;
		button = new JButton("Button");
		button.setSize(100, 100);
		button.setLocation(400, 400);
		button.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {
				Thread t = new Thread(new Runnable() {
					
					@Override
					public void run() {
						while(Y < 301){
							Y+=2;
							r();
							try {
								Thread.sleep(5);
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}
					}
				});
				t.start();
			}
		});
		this.add(button);
		
	}
	
	@Override 
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.fillRect(X, Y, 50, 50);
		
	}
	
	public void r() {
		repaint();
	}
}

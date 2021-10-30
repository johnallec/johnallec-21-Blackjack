package view;

import javax.swing.JFrame;

import controller.MyTable;
import model.Deck;
import model.GenericThreadGenerator;
import out_pat.StandardValues;

public class FrameHandler extends JFrame{
	
	private static final long serialVersionUID = -1815755081794429492L;

	private static FrameHandler instance = null;
	
	private MyTable table;
	private Deck d;
	
	private FrameHandler() {
		d = new Deck();
		table = new MyTable(d);
	}
	
	public static FrameHandler getInstance() {
		if(instance == null)
			instance = new FrameHandler();
		return instance;
	}
	
	public void start() {
		add(table);
		//add(new AnotherPanel());
		setSize(table.getPreferredSize());
		setResizable(false);
		setVisible(true);
		setLocation(StandardValues.FRAME_LOCATION_X, StandardValues.FRAME_LOCATION_Y);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GenericThreadGenerator.getInstance(d, table).startSoundtrack();
	}
	
}

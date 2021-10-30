package out_pat;

import java.awt.Toolkit;

public class StandardValues {
	public static final int WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final int HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	public static final int FRAME_LOCATION_X = 0;
	public static final int FRAME_LOCATION_Y = 0;
	public static final int CARD_WIDTH = 100;
	public static final int CARD_HEIGHT = 170;
	public static final int DEALER_POINTS_LOCATION_X = 1500;
	public static final int DEALER_POINTS_LOCATION_Y = 100;
	public static final int PLAYER_POINTS_LOCATION_X = 1500;
	public static final int PLAYER_POINTS_LOCATION_Y = 130;
	public static final int DEALER_CARDS_LOCATION_X = (WIDTH / 2) - CARD_WIDTH;
	public static final int DEALER_CARDS_LOCATION_Y = (HEIGHT / 3) - (CARD_HEIGHT * (3 / 2));
	public static final int PLAYER_CARDS_LOCATION_X = DEALER_CARDS_LOCATION_X;
	public static final int PLAYER_CARDS_LOCATION_Y = ((HEIGHT * 2) / 3);
	public static final int DECK_LOCATION_X = (WIDTH / 3) - CARD_WIDTH;
	public static final int DECK_LOCATION_Y = (PLAYER_CARDS_LOCATION_Y - DEALER_CARDS_LOCATION_Y) - (CARD_HEIGHT / 2);
	public static final int HIT_BUTTON_LOCATION_X = ((WIDTH * 2) / 3);
	public static final int HIT_BUTTON_LOCATION_Y = DECK_LOCATION_Y - (CARD_HEIGHT / 2);
	public static final int MIN_NUM_CARDS_DECK = 25;
}

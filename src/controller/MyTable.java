package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import model.Blackjack;
import model.Card;
import model.Deck;
import out_pat.StandardValues;

public class MyTable extends JPanel{

	private static final long serialVersionUID = -7941654996230843518L;
	
	private boolean secondCardDealerHidden = true;
	private Deck deck;
	private Image greenTable;
	private Image deckImage;
	private ArrayList<ArrayList<Image>> imagesCards;
	private ArrayList<Image> heartsImages;
	private ArrayList<Image> clubsImages;
	private ArrayList<Image> spadesImages;
	private ArrayList<Image> diamondsImages;
	
	private Point playerCardsLocation;
	private Point dealerCardsLocation;
	
	private final String pathImages = "/resources/img/";
	private final String pathPlayingCardsImages = pathImages + "playing-cards/";
	private final String pathClubsImages = pathPlayingCardsImages + "Clubs/";
	private final String pathDiamondsImages = pathPlayingCardsImages + "Diamonds/";
	private final String pathHeartsImages = pathPlayingCardsImages + "Hearts/";
	private final String pathSpadesImages = pathPlayingCardsImages + "Spades/";
	
	private final String clubsCardImagesSuffix = "C.png";
	private final String diamondsCardImagesSuffix = "D.png";
	private final String heartsCardImagesSuffix = "H.png";
	private final String spadesCardImagesSuffix = "S.png";
	
	//private final String errorImages = "error.png";
	
	private JButton hitCardButton;
	private JButton stayButton;
	
	public MyTable(Deck deck) {
		this.setLayout(null);
		this.deck = deck;
		
		imagesCards = new ArrayList<ArrayList<Image>>();
		clubsImages = new ArrayList<Image>();
		diamondsImages = new ArrayList<Image>();
		heartsImages = new ArrayList<Image>();
		spadesImages = new ArrayList<Image>();
		
		imagesCards.add(clubsImages);
		imagesCards.add(diamondsImages);
		imagesCards.add(heartsImages);
		imagesCards.add(spadesImages);
		
		dealerCardsLocation = new Point(StandardValues.DEALER_CARDS_LOCATION_X, StandardValues.DEALER_CARDS_LOCATION_Y);
		playerCardsLocation = new Point(StandardValues.PLAYER_CARDS_LOCATION_X, StandardValues.PLAYER_CARDS_LOCATION_Y);
	
		initializeListsImagesCards();
		initializeButtons();
		
		this.setSize(new Dimension(StandardValues.WIDTH, StandardValues.HEIGHT));
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		try {
			initializeImages();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override 
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(greenTable.getScaledInstance(StandardValues.WIDTH, StandardValues.HEIGHT, Image.SCALE_SMOOTH), 0, 0, null);
		showCurrentCards(g, Blackjack.getInstance(deck, this).playerCards, playerCardsLocation);
		showCurrentCards(g, Blackjack.getInstance(deck, this).dealerCards, dealerCardsLocation);
		if(secondCardDealerHidden) {
			try {
				g.drawImage(ImageIO.read(getClass().getResourceAsStream(pathPlayingCardsImages + "otherPNG/purple_back.png")).getScaledInstance(100, 170, Image.SCALE_SMOOTH), dealerCardsLocation.x+55, dealerCardsLocation.y, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.setColor(Color.BLACK);
		//g.drawString("Dealer points: " +  Blackjack.getInstance(deck, this).getDealerPoints(), StandardValues.DEALER_POINTS_LOCATION_X, StandardValues.DEALER_POINTS_LOCATION_Y);
		//g.drawString("Player points: " +  Blackjack.getInstance(deck, this).getPlayerPoints(), StandardValues.PLAYER_POINTS_LOCATION_X, StandardValues.PLAYER_POINTS_LOCATION_Y);
		g.drawImage(deckImage.getScaledInstance(StandardValues.CARD_WIDTH, StandardValues.CARD_HEIGHT, Image.SCALE_SMOOTH), StandardValues.DECK_LOCATION_X, StandardValues.DECK_LOCATION_Y, null);
	}
	
	private void initializeImages() throws IOException {
		greenTable = ImageIO.read(getClass().getResourceAsStream(pathImages + "greenTable.jpg"));
		this.add(hitCardButton);
		this.add(stayButton);
	}
	
	private void initializeListsImagesCards() {
		int k = 0;
		for(ArrayList<Image> list : imagesCards) {
			String tmpNameCard = null;
			String tmpPath = null;
			switch(k) {
				case 0: 
					tmpPath = pathClubsImages;
					tmpNameCard = clubsCardImagesSuffix;
					break;
						
				case 1:	
					tmpPath = pathDiamondsImages;
					tmpNameCard = diamondsCardImagesSuffix;
					break;
						
				case 2: 
					tmpPath = pathHeartsImages;
					tmpNameCard = heartsCardImagesSuffix;
					break;
						
				case 3: 
					tmpPath = pathSpadesImages;
					tmpNameCard = spadesCardImagesSuffix;
					break;
			}
			for(int i=1; i<=13; i++) {
				String nameCard = i + tmpNameCard;
				Image tmp = null;
				try {
					tmp = ImageIO.read(getClass().getResourceAsStream(tmpPath + nameCard));
				} catch (IOException e) {
					e.printStackTrace();
				}
				list.add(tmp);
			}
			k++;
		}
		
		try {
			deckImage = ImageIO.read(getClass().getResourceAsStream(pathPlayingCardsImages + "otherPNG/purple_back.png"));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/*private void showDeck(Graphics g) {
		ArrayList<Integer> listSeeds = new ArrayList<Integer>();
		ArrayList<Integer> listValues = new ArrayList<Integer>();
		int indexSeed = 0;
		int indexValue = 0;
		for(Card card : deck.getCards()) {
			switch(card.getSeed()){
			case CLUBS:
				indexSeed = 0;
				break;
			case DIAMONDS:	
				indexSeed = 1;
					break;
							
			case HEARTS:	
				indexSeed = 2;
					break;
							
			case SPADES:	
				indexSeed = 3;
				break;
			}
			listSeeds.add(indexSeed);
		}
		for(Card card : deck.getCards()) {
			switch(card.getValue()) {
			case ACE:
				indexValue = 0;
				break;
			case TWO:
				indexValue = 1;
				break;
			case THREE:
				indexValue = 2;
				break;
			case FOUR:
				indexValue = 3;
				break;
			case FIVE:
				indexValue = 4;
				break;
			case SIX:
				indexValue = 5;
				break;
			case SEVEN:
				indexValue = 6;
				break;
			case EIGHT:
				indexValue = 7;
				break;
			case NINE:
				indexValue = 8;
				break;
			case TEN:
				indexValue = 9;
				break;
			case JACK:
				indexValue = 10;
				break;
			case QUEEN:
				indexValue = 11;
				break;
			case KING:
				indexValue = 12;
				break;
			}
			listValues.add(indexValue);
		}
		int h = 0;
		for(int i = 10, countRows = 0; countRows < 4; countRows++, i+=200)
			for(int j = 10, countColumns = 0; countColumns < 13; countColumns++, j+=80) {
				g.drawImage(imagesCards.get(listSeeds.get(h)).get(listValues.get(h)).getScaledInstance(50, 80, Image.SCALE_SMOOTH), j, i, null);
				h++;
			}
	} */
	
	private void showCurrentCards(Graphics g, ArrayList<Card> list, Point point) {
		ArrayList<Integer> listSeeds = new ArrayList<Integer>();
		ArrayList<Integer> listValues = new ArrayList<Integer>();
		int indexSeed = 0;
		int indexValue = 0;
		for(Card card : list) {
			switch(card.getSeed()){
			case CLUBS:
				indexSeed = 0;
				break;
			case DIAMONDS:	
				indexSeed = 1;
					break;
			case HEARTS:	
				indexSeed = 2;
					break;
			case SPADES:	
				indexSeed = 3;
				break;
			}
			listSeeds.add(indexSeed);
		}
		for(Card card : list) {
			switch(card.getValue()) {
			case ACE:
				indexValue = 0;
				break;
			case TWO:
				indexValue = 1;
				break;
			case THREE:
				indexValue = 2;
				break;
			case FOUR:
				indexValue = 3;
				break;
			case FIVE:
				indexValue = 4;
				break;
			case SIX:
				indexValue = 5;
				break;
			case SEVEN:
				indexValue = 6;
				break;
			case EIGHT:
				indexValue = 7;
				break;
			case NINE:
				indexValue = 8;
				break;
			case TEN:
				indexValue = 9;
				break;
			case JACK:
				indexValue = 10;
				break;
			case QUEEN:
				indexValue = 11;
				break;
			case KING:
				indexValue = 12;
				break;
			}
			listValues.add(indexValue);
		}
		int h = 0;
		Point tmpPoint = new Point(point.x, point.y);
		for(int i=0; i<list.size(); i++) {
			g.drawImage(imagesCards.get(listSeeds.get(h)).get(listValues.get(h)).getScaledInstance(100, 170, Image.SCALE_SMOOTH), tmpPoint.x, tmpPoint.y, null);
			tmpPoint.x+=55;
			h++;
		}
	}
	
	public void initializeButtons() {
		hitCardButton = new JButton();
		hitCardButton.setSize(200, 200);
		hitCardButton.setContentAreaFilled(false);
		hitCardButton.setBorderPainted(false);
		hitCardButton.setToolTipText("Click to hit a card");
		try {
			hitCardButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream(pathPlayingCardsImages + "otherPNG/aces.png")).getScaledInstance(150, 100, Image.SCALE_SMOOTH)));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		hitCardButton.setLocation(StandardValues.HIT_BUTTON_LOCATION_X, StandardValues.HIT_BUTTON_LOCATION_Y);
		hitCardButton.addMouseListener(new MouseListener() {
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
				hitForCard();
			}
		});
		
		stayButton = new JButton();
		stayButton.setSize(200, 200);
		stayButton.setContentAreaFilled(false);
		stayButton.setBorderPainted(false);
		stayButton.setToolTipText("Click to stay");
		try {
			stayButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResourceAsStream(pathPlayingCardsImages + "otherPNG/stay.png")).getScaledInstance(150, 120, Image.SCALE_SMOOTH)));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		stayButton.setLocation(StandardValues.HIT_BUTTON_LOCATION_X, StandardValues.HIT_BUTTON_LOCATION_Y + hitCardButton.getHeight());
		stayButton.addMouseListener(new MouseListener() {
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
				playerStay();
			}
		});
	}
	
	private void hitForCard(){
		Blackjack.getInstance(deck, this).hitForCard();
	}
	
	private void playerStay() {
		Blackjack.getInstance(deck, this).playerStay();
	}

	public void setSecondCardDealerHidden(boolean secondCardDealerHidden) {
		this.secondCardDealerHidden = secondCardDealerHidden;
	}
	
}

package model;

import java.util.ArrayList;
import controller.MyTable;
import model.Deck.VALUE;

public class Blackjack {
	
	private static Blackjack instance = null;
	private Deck deck;
	private int playerPoints;
	private int dealerPoints;
	//private int currentBet; 
	public ArrayList<Card> playerCards;
	public ArrayList<Card> dealerCards;
	private boolean playerStay;
	private boolean dealerStopped;
	//private static final int upperBoundPoints = 21;
	private MyTable table;
	
	private Blackjack(Deck deck, MyTable table) {
		this.table = table;
		this.deck = deck;
		deck.mixDeck();
		reset();
	}
	
	public void reset() {
		playerPoints = 0;
		dealerPoints = 0;
		//currentBet = 0;
		playerStay = false;
		dealerStopped = false;
		
		playerCards = new ArrayList<Card>();
		dealerCards = new ArrayList<Card>();
		for(int i = 0; i < 4; i++) {
			Card currentCard = deck.back();
			if(i<2) {
				playerCards.add(0, currentCard);
				playerPoints+=getRealValue(currentCard.getValue());
			}
			else {
				dealerCards.add(0, currentCard);
				dealerPoints+=getRealValue(currentCard.getValue());
			}
			deck.pop();
			deck.pushOutCard(currentCard);
		}
		table.repaint();
	}
	
	public static Blackjack getInstance(Deck deck, MyTable table) {
		if(instance == null)
			instance = new Blackjack(deck, table);
		return instance;
	}
	
	public int getRealValue(VALUE value) {
		int realValue = 0;
		switch(value) {
		case ACE:
			realValue = 1;
			break;
		case TWO:
			realValue = 2;
			break;
		case THREE:
			realValue = 3;
			break;
		case FOUR:
			realValue = 4;
			break;
		case FIVE:
			realValue = 5;
			break;
		case SIX:
			realValue = 6;
			break;
		case SEVEN:
			realValue = 7;
			break;
		case EIGHT:
			realValue = 8;
			break;
		case NINE:
			realValue = 9;
			break;
		default:
			realValue = 10;
		}
		return realValue;
	}
	
	public boolean containAce(ArrayList<Card> list){
		for(Card card: list)
			if(card.getValue()==VALUE.ACE)
				return true;
		return false;
	}
	
	/*public void bet(int theBet) {
		this.currentBet += theBet;
	}*/
	
	public void hitForCard() {
		GenericThreadGenerator.getInstance(deck, table).playerHitCard();
	}

	public void playerStay() {
		playerStay = true;
		GenericThreadGenerator.getInstance(deck, table).dealerHitCards();
	}

	public ArrayList<Card> getPlayerCards() {
		return playerCards;
	}

	public ArrayList<Card> getDealerCards() {
		return dealerCards;
	}

	public boolean isPlayerStay() {
		return playerStay;
	}

	public void setPlayerStay(boolean playerStay) {
		this.playerStay = playerStay;
	}

	public boolean isDealerStopped() {
		return dealerStopped;
	}

	public void setDealerStopped(boolean dealerStopped) {
		this.dealerStopped = dealerStopped;
	}

	public int getPlayerPoints() {
		return playerPoints;
	}

	public int getDealerPoints() {
		return dealerPoints;
	}

	public void setPlayerPoints(int playerPoints) {
		this.playerPoints = playerPoints;
	}

	public void setDealerPoints(int dealerPoints) {
		this.dealerPoints = dealerPoints;
	}
	
	public void addPlayerPoints(int playerPoints) {
		this.playerPoints += playerPoints;
	}

	public void addDealerPoints(int dealerPoints) {
		this.dealerPoints += dealerPoints;
	}
	
}

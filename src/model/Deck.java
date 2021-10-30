package model;

import java.util.ArrayList;
import java.util.Random;

public class Deck {
	
	public enum SEED{ HEARTS, DIAMONDS, CLUBS, SPADES };
	public enum VALUE{	ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN,
						EIGHT, NINE, TEN, JACK, QUEEN, KING	}

	private ArrayList<Card> cards;
	private ArrayList<Card> outCards;
	
	public Deck() {
		cards = new ArrayList<Card>();
		outCards = new ArrayList<Card>();
		for(SEED seed : SEED.values()) 
			for(VALUE value : VALUE.values())
				cards.add(new Card(value, seed));
		for(SEED seed : SEED.values()) 
			for(VALUE value : VALUE.values())
				cards.add(new Card(value, seed));
	}
	
	public void mixDeck() {
		Random random = new Random();
		int number = random.nextInt(8)+1;
		for(int i = 0; i < number; i++) {
			number = random.nextInt(cards.size())+1;
			ArrayList<Card> firstHalf = new ArrayList<Card>();
			ArrayList<Card> secondHalf = new ArrayList<Card>();
			int k = 0;
			while(k  < number) {
				firstHalf.add(cards.get(k));
				k++;
			}
			while(k  < cards.size()) {
				secondHalf.add(cards.get(k));
				k++;
			}
			int maxSize;
			if(firstHalf.size() >= secondHalf.size())
				maxSize = firstHalf.size();
			else 
				maxSize = secondHalf.size();
			cards.clear();
			for(int j = 0; j < maxSize; j++) {
				if(j<firstHalf.size() && j<secondHalf.size()) {
					cards.add(secondHalf.get(j));
					cards.add(firstHalf.get(j));
				}
				else if(j<firstHalf.size())
					cards.add(firstHalf.get(j));
				else
					cards.add(secondHalf.get(j));
			}
			
		}
	}
	
	public void mergeDeck(){
		for(Card card : outCards) {
			cards.add(0, card);
		}
		outCards.clear();
	}
	
	public void pushOutCard(Card card) {
		outCards.add(0, card);
	}
	
	public void pop() {
		if(!cards.isEmpty())
			cards.remove(0);
	}
	
	public Card back() {
		if(!cards.isEmpty())
			return cards.get(0);
		return null;
	}
	
	public int sizeDeck() {
		return cards.size();
	}
	
	public int sizeOutCards() {
		return outCards.size();
	}
	
	public void printDeck() {
		int i=1;
		for(Card card : cards) {
			if(i>13) {
				System.out.println();
				i=1;
			}
			System.out.print(card.toString() + " - " );
			i++;
		}
	}
	
}

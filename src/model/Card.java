package model;

import model.Deck.SEED;
import model.Deck.VALUE;

public class Card {

	private SEED seed;
	private VALUE value;
	
	public Card() {
		value = VALUE.TWO;
		seed = SEED.SPADES;
	}
	
	public Card(VALUE value, SEED seed) {
		this.value = value;
		this.seed = seed;
	}

	public SEED getSeed() {
		return seed;
	}

	public void setSeed(SEED seed) {
		this.seed = seed;
	}

	public VALUE getValue() {
		return value;
	}

	public void setValue(VALUE value) {
		this.value = value;
	}
	
	public String toString() {
		return value + " of " + seed;
	}
	
}

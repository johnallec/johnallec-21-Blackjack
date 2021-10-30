package model;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;
import controller.MyTable;
import model.Deck.VALUE;
import out_pat.StandardValues;

public class GenericThreadGenerator {
	
	private static GenericThreadGenerator instance = null;
	private final URL cardShuffleSoundUrl = getClass().getClassLoader().getResource("cardShuffleSound.wav");
	private final URL epicWinSoundUrl = getClass().getClassLoader().getResource("epicWinSound.wav");
	private final URL failLoseSoundUrl = getClass().getClassLoader().getResource("failLoseSound.wav");
	private final URL jazzSoundtrackSoundUrl = getClass().getClassLoader().getResource("jazzSoundtrack.wav");
	private final URL takeCardSoundUrl = getClass().getClassLoader().getResource("takeCardSound.wav");
	
	private ExecutorService executor;
	private Deck deck;
	private MyTable table;
	
	private GenericThreadGenerator(Deck deck, MyTable table) {
		executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		this.deck = deck;
		this.table = table;
	}
	
	private GenericThreadGenerator(Deck deck, MyTable table, int numThreads) {
		executor = Executors.newFixedThreadPool(numThreads);
		this.deck = deck;
		this.table = table;
	}
	
	public static GenericThreadGenerator getInstance(Deck deck, MyTable table) {
		if(instance == null)
			instance = new GenericThreadGenerator(deck, table);
		return instance;
	}
	
	public static GenericThreadGenerator getInstance(Deck deck, MyTable table, int numThreads) {
		if(instance == null)
			instance = new GenericThreadGenerator(deck, table, numThreads);
		return instance;
	}
	
	public void checkStatusGame() {
		Runnable checher = new Runnable() {
			@Override
			public void run() {
				int dealerPoints= Blackjack.getInstance(deck, table).getDealerPoints();
				int playerPoints= Blackjack.getInstance(deck, table).getPlayerPoints();
				table.repaint();
				if(Blackjack.getInstance(deck, table).isPlayerStay() && Blackjack.getInstance(deck, table).isDealerStopped() || playerPoints>21) {
					table.setSecondCardDealerHidden(false);
					table.repaint();
					if((dealerPoints >= playerPoints && dealerPoints <= 21) || playerPoints>21) {
						startFailLoseSound();
						JOptionPane.showMessageDialog(table, "Dealer points: " + dealerPoints + "\nPlayer points: " + playerPoints + "\nYOU LOSE");
					}
					else {
						startEpicWinSound();
						JOptionPane.showMessageDialog(table, "Dealer points: " + dealerPoints + "\nPlayer points: " + playerPoints + "\nYOU WIN");
					}
					table.setSecondCardDealerHidden(true);
					Blackjack.getInstance(deck, table).reset();
					if(haveToMerge()){
						deck.mergeDeck();
						deck.mixDeck();
						try {
							AudioInputStream audio = AudioSystem.getAudioInputStream(cardShuffleSoundUrl);
							Clip clip = AudioSystem.getClip();
							clip.open(audio);
							clip.start();
						} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
							e.printStackTrace();
						}
					}
				}
			}
		};
		executor.execute(checher);
	}
	
	public void dealerHitCards() {
		Runnable hitter = new Runnable() {
			@Override
			public void run() {
				table.setSecondCardDealerHidden(false);
				int dealerPoints = Blackjack.getInstance(deck, table).getDealerPoints();
				int playerPoints = Blackjack.getInstance(deck, table).getPlayerPoints();
				while((dealerPoints <= playerPoints && dealerPoints <= 21)){
					Card currentCard = deck.back();
					Blackjack.getInstance(deck, table).dealerCards.add(currentCard);
					if(currentCard.getValue()==VALUE.ACE) {
						if(!Blackjack.getInstance(deck, table).containAce(Blackjack.getInstance(deck, table).dealerCards))
							Blackjack.getInstance(deck, table).addDealerPoints(10);
					}
					Blackjack.getInstance(deck, table).addDealerPoints(Blackjack.getInstance(deck, table).getRealValue(currentCard.getValue()));
					deck.pop();
					deck.pushOutCard(currentCard);
					table.repaint();
					dealerPoints = Blackjack.getInstance(deck, table).getDealerPoints();
					playerPoints = Blackjack.getInstance(deck, table).getPlayerPoints();
					try { Thread.sleep(750); }
					catch(Exception e) { e.printStackTrace(); }
				}
				Blackjack.getInstance(deck, table).setDealerStopped(true);
				checkStatusGame();
			}
		};
		executor.execute(hitter);
	}
	
	public void playerHitCard() {
		Runnable hitter = new Runnable() {
			@Override
			public void run() {
				Card currentCard = deck.back();
				if(currentCard.getValue()==VALUE.ACE)
					if(!Blackjack.getInstance(deck, table).containAce(Blackjack.getInstance(deck, table).playerCards))
						Blackjack.getInstance(deck, table).addPlayerPoints(10);
				Blackjack.getInstance(deck, table).addPlayerPoints(Blackjack.getInstance(deck, table).getRealValue(currentCard.getValue()));
				Blackjack.getInstance(deck, table).playerCards.add(currentCard);
				deck.pushOutCard(currentCard);
				deck.pop();
				try {
					AudioInputStream audio = AudioSystem.getAudioInputStream(takeCardSoundUrl);
					Clip clip = AudioSystem.getClip();
					clip.open(audio);
					clip.start();
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
					e.printStackTrace();
				}
				table.repaint();
				checkStatusGame();
			}
		};
		executor.execute(hitter);
	}
	public void startSoundtrack() {
		System.out.println(getClass().getClassLoader().getResourceAsStream("jazzSoundtrack.wav"));
		Runnable dj = new Runnable() {
			@Override
			public void run() {
				try {
					AudioInputStream audio = AudioSystem.getAudioInputStream(jazzSoundtrackSoundUrl);
					Clip clip = AudioSystem.getClip();
					clip.open(audio);
					clip.loop(Clip.LOOP_CONTINUOUSLY);
					clip.start();					
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
					e.printStackTrace();
				}
			}
		};
		executor.execute(dj);
	}
	
	public void startEpicWinSound() {
		Runnable dj = new Runnable() {
			@Override
			public void run() {
				try {
					AudioInputStream audio = AudioSystem.getAudioInputStream(epicWinSoundUrl);
					Clip clip = AudioSystem.getClip();
					clip.open(audio);
					clip.start();
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
					e.printStackTrace();
				}
			}
		};
		executor.execute(dj);
	}
	
	public void startFailLoseSound() {
		Runnable dj = new Runnable() {
			@Override
			public void run() {
				try {
					AudioInputStream audio = AudioSystem.getAudioInputStream(failLoseSoundUrl);
					Clip clip = AudioSystem.getClip();
					clip.open(audio);
					clip.start();
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
					e.printStackTrace();
				}
			}
		};
		executor.execute(dj);
	}
	
	public boolean haveToMerge() {
		return deck.sizeDeck() < StandardValues.MIN_NUM_CARDS_DECK;
	}

}

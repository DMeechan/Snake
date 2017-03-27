import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;

import java.util.ArrayList;

public class Game {
	
	//public Player[] players;
	public ArrayList<Player> players;
	public Food food;
	public int stepSize;
	private int livingPlayers;
	private String[] colours;
	private BooleanProperty running = new SimpleBooleanProperty();
	
	public Game(int stepSize, Scene scene) {
		createColours();
		this.stepSize = stepSize;
		players = new ArrayList<>();
		
		players.add(new Human(stepSize, colours[0], 1, scene, 0));
		players.add(new AI(stepSize, colours[1], 2));
		//players.add(new Human(stepSize, colours[2], 3, scene, 1));
		
		start();
	}
	
	public void createColours() {
		colours = new String[] {
				"#2ecc71", "#1abc9c", "#3498db", "#9b59b6",
				"#f1c40f", "#e67e22", "#e74c3c", "#ecf0f1",
				"#16a085", "#27ae60", "#2980b9", "#8e44ad",
				"#f39c12", "#d35400", "#c0392b", "#bdc3c7"
		};
	}
	
	private void checkLiving() {
		for(Player player : players) {
			player.snake.aliveProperty().addListener(v -> {
				if(!player.snake.getAlive() && player.getClass().toString().equals("class Human")) {
					// Dead human :(
					livingPlayers -= 1;
					System.out.println("Living: " + livingPlayers);
				}
			});
		}
	}
	
	public void timerTick() {
		
		if(livingPlayers > 0) {
			for (Player player : players) {
				if(player.snake.getAlive()) {
					// Alive :)
					player.snake.move();
				}
			}
		} else {
			// All humans are dead. Game over. GG WP.
			setRunning(false);
		}
		
		checkCollissions();
	}
	
	public void checkCollissions() {
		for (Player original : players) {
			for (Player checkee : players) {
				if (!original.equals(checkee)) { // make sure they're not the same
					for (SnakePiece piece : checkee.snake) {
						SnakePiece originalHead = original.snake.getFirst();
						if(originalHead.getPosX() == piece.getPosX() && originalHead.getPosY() == piece.getPosY()) {
							original.snake.setAlive(false);
							checkee.snake.setAlive(false);
						}
					}
				}
			}
		}
	}
	
	public void start() {
		setRunning(true);
		livingPlayers = 0;
		for(Player player : players) {
			if(player.getClass().toString().equals("class Human")) {
				livingPlayers += 1;
			}
		}
		checkLiving();
		System.out.println("Living: " + livingPlayers);
	}
	
	public boolean isRunning() {
		return running.get();
	}
	
	public BooleanProperty runningProperty() {
		return running;
	}
	
	public void setRunning(boolean running) {
		this.running.set(running);
	}
}
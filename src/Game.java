import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;

import java.util.ArrayList;

public class Game {

	public ArrayList<Player> players;
	public Food food;
	public int stepSize;
	private int livingPlayers;
	private boolean AIonly;
	public static final String[] colours = new String[] {
			"#2ecc71", "#1abc9c", "#3498db", "#9b59b6",
			"#f1c40f", "#e67e22", "#e74c3c", "#ecf0f1",
			"#16a085", "#27ae60", "#2980b9", "#8e44ad",
			"#f39c12", "#d35400", "#c0392b", "#bdc3c7"
	};
	private BooleanProperty running = new SimpleBooleanProperty();
	
	public Game(int stepSize, Scene scene, int numHumans, int numAIs) {
		AIonly = false;
		if(numHumans == 0) {
			AIonly = true;
		}
		this.stepSize = stepSize;
		players = new ArrayList<>(numHumans + numAIs);

		for(int i = 0; i < numHumans; i++) {
			players.add(new Human(stepSize, colours[i], i + 1, scene, i));
		}

		for(int i = numHumans; i < (numAIs + numHumans); i++) {
			players.add(new AI(stepSize, colours[i], i + 1, players));
		}

		start();
	}

	public void generateFood() {
		food = new Food();
		for (Player player : players) {
			if(player.getClass().toString().equals("class AI")) {
				((AI) player).setFood(food);
			}
		}
		food.eatenProperty().addListener(v -> {
			if(food.isEaten()) {
				generateFood();
			}
		});
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
		generateFood();
		//System.out.println("Living: " + livingPlayers);
	}


	private void checkLiving() {
		for(Player player : players) {
			if(player.getClass().toString().equals("class Human")) {
				player.snake.aliveProperty().addListener(v -> {
					if(!player.snake.getAlive() && !player.snake.updatedDead) {
						// Dead human :(
						player.snake.updatedDead = true;
						livingPlayers -= 1;
						System.out.println("Living: " + livingPlayers);
					}
				});
			}

		}
	}
	
	public void timerTick() {

		if(livingPlayers > 0 || AIonly == true) {
			for (Player player : players) {
				if(player.snake.getAlive()) {
					// Alive :)
					player.snake.move();
					if(player.getClass().toString().equals("class AI")) {
						((AI) player).timerTick();
					}
				}
			}
		} else {
			// All humans are dead. Game over. GG WP.
			setRunning(false);
		}
		
		checkCollisions();
	}
	
	private void checkCollisions() {
		for (Player original : players) {
			if(original.snake.getAlive()) {
				for (Player checkee : players) {
					if (checkee.snake.getAlive()) {
						for (SnakePiece piece : checkee.snake) {
							SnakePiece originalHead = original.snake.getFirst();
							if (!originalHead.equals(piece)) { // make sure it isn't colliding with its own head...
								if (originalHead.getPosX() == piece.getPosX() && originalHead.getPosY() == piece.getPosY()) {
									original.snake.setAlive(false);
									if (piece.getStatus() == 0) { // only kill the other snake if its head collided too
										checkee.snake.setAlive(false);
									}
								}
							}
						}
					}
				}
				SnakePiece head = original.snake.getFirst();
				if(head.getPosX() == food.getPosX() && head.getPosY() == food.getPosY()) {
					// it has eaten the food! now feed the man!
					food.setEaten(true);
					original.snake.addPiece();
					original.setPoints(original.getPoints() + 10);
				}

			}
		}
	}
	
	public boolean isRunning() {
		return running.get();
	}
	
	public BooleanProperty runningProperty() {
		return running;
	}

	private void setRunning(boolean running) {
		this.running.set(running);
	}
}
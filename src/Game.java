import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;

public class Game {
	
	public Player[] players;
	public Food food;
	public int stepSize;
	private int livingPlayers;
	private String[] colours;
	private BooleanProperty running = new SimpleBooleanProperty();
	
	public Game(int stepSize, Scene scene) {
		createColours();
		this.stepSize = stepSize;
		
		players = new Player[] {
				new Human(stepSize, colours[0], scene, 0),
				new AI(stepSize, colours[1]),
				//new Human(stepSize, colours[2], scene, 1),
		};
		
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
	
	public void timerTick() {
		
		if(livingPlayers > 0) {
			for (Player player : players) {
				if(player.snake.getAlive()) {
					// Alive :)
					player.snake.move();
				} else if (player.getClass().toString().equals("class Human")){
					// Dead human :(
					livingPlayers -= 1;
				}
			}
		} else {
			// All humans are dead. Game over. GG WP.
			setRunning(false);
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
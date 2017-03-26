import javafx.scene.Scene;

public class Game {
	
	public Player[] players;
	public Food food;
	public int stepSize;
	
	public Game(int stepSize, Scene scene) {
		this.stepSize = stepSize;
		players = new Player[] {
				new Human(stepSize, scene, 0)
				// ,new AI(stepSize),
		};
		
		start();
	}
	
	public void timerTick() {
		for (Player player : players) {
			if(player.snake.getAlive()) {
				// Alive :)
				player.snake.move();
			} else {
				// Dead :(
			}
		}
	}
	
	public void start() {
		
		for (Player player : players) {
			player.snake.aliveProperty().addListener(v -> {
				if (!player.snake.getAlive()) {
					// game over
					stop();
				}
			});
		}
	}
	
	public void stop() {
		System.out.println("lol oops");
	}
	
}
import javafx.animation.Timeline;

public class Game {
	private Timeline timer;
	public Player player;
	public Food food;
	
	public Game(int stepSize) {
		player = new Player(stepSize);
	}
	
	public void start() {
		
		player.snake.aliveProperty().addListener(v -> {
			if(!player.snake.getAlive()) {
				// game over
				stop();
			}
		});
	}
	
	public void stop() {
		
	}

	public Timeline getTimer() {
		return this.timer;
	}

	public void setTimer(Timeline timer) {
		this.timer = timer;
	}
}
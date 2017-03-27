public class Player {
	public Snake snake;
	private int points;
	
	public Player(int stepSize, String colour, int playerNum) {
		snake = new Snake(stepSize, colour, playerNum);
		points = 0;
	}

	public Snake getSnake() {
		return snake;
	}

	public void setSnake(Snake snake) {
		this.snake = snake;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
}
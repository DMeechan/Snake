public class Player {
	public Snake snake;
	private int points;
	
	public Player(int stepSize, String colour) {
		snake = new Snake(stepSize, colour);
		points = 0;
	}
	
}
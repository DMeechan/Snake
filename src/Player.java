public class Player {
	public Snake snake;
	private int points;
	
	public Player(int stepSize, String colour, int playerNum) {
		snake = new Snake(stepSize, colour, playerNum);
		points = 0;
	}
	
}
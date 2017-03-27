import javafx.scene.Scene;

public class Human extends Player {
	
	public Human(int stepSize, String colour, int playerNum, Scene scene, int keyboardControlsLayout) {
		super(stepSize, colour, playerNum);
		// keyboardControlsLayout(0) = WASD
		// keyboardControlsLayout(1) = ARROWS
		
		if (keyboardControlsLayout == 0) {
			// use WASD
			scene.setOnKeyPressed(event -> {
				switch (event.getCode()) {
					case W:
						snake.setDirection(0);
						break;
					case S:
						snake.setDirection(1);
						break;
					case A:
						snake.setDirection(3);
						break;
					case D:
						snake.setDirection(2);
						break;
					default:
						break;
				}
				//System.out.println(event.getCode().toString());
			});
		} else {
			// use arrow keys
			scene.setOnKeyPressed(event -> {
				switch (event.getCode()) {
					case UP:
						snake.setDirection(0);
						break;
					case DOWN:
						snake.setDirection(1);
						break;
					case LEFT:
						snake.setDirection(3);
						break;
					case RIGHT:
						snake.setDirection(2);
						break;
					default:
						break;
				}
			});
		}
	}
	
}
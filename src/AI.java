import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.Random;

public class AI extends Player {
	Timeline timer;
	
	public AI(int stepSize) {
		super(stepSize);
		
		timer = new Timeline((new KeyFrame(
				Duration.millis(100),
				event -> timerTick())));
		timer.setCycleCount(Animation.INDEFINITE);
		
		timer.play();
	}
	
	public void timerTick() {
		Random rng = new Random();
		snake.setDirection(rng.nextInt(3));
	}
}
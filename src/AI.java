import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.Random;

public class AI extends Player {
	Timeline timer;
	Food food;
	boolean foodIsUp, foodIsRight;
	
	public AI(int stepSize, String colour, int playerNum) {
		super(stepSize, colour, playerNum);
		// randomAI();

	}

	public void findFoodDirection() {
		SnakePiece head = getSnake().getFirst();
		if(food.getPosX() != head.getPosX() && food.getPosY() != head.getPosY()) {
			// negative; go right
// positive; go left
			foodIsRight = head.getPosX() - food.getPosY() < 0;

			foodIsUp = head.getPosY() - food.getPosY() >= 0;
		}



	}

	public void timerTick() {
		findFoodDirection();

		if(foodIsUp) {
			turn(0);
		} else {
			turn(1);
		}

		if(foodIsRight) {
			turn(2);
		} else {
			turn(3);
		}

	}

	public void turn(int direction) {
		// 0 = up; 1 = down; 2 = right; 3 = left
		switch(direction) {
			case 0: // up
				if(direction != 0) {
					getSnake().setDirection(0);
				}
				setPoints(0);
				break;

			case 1: // down
				if(direction != 1) {
				if(direction == 0) {

				} else {
					getSnake().setDirection(1);

				}
				}
				break;

			case 2: // right
				if(direction != 2) {
					if(direction == 3) {
						getSnake().setDirection(1);
					} else {
						getSnake().setDirection(2);
					}
				}
				break;

			case 3: // left
				if(direction != 3) {
					if (direction == 2) {
						// need to go up or down first, and then left
						getSnake().setDirection(1); // ????
					} else {
						getSnake().setDirection(3);
					}
				}
				break;

		}
	}

	public void randomAI() {
		timer = new Timeline((new KeyFrame(
				Duration.millis(100),
				event -> randomAITimerTick())));
		timer.setCycleCount(Animation.INDEFINITE);

		timer.play();
	}
	
	public void randomAITimerTick() {
		Random rng = new Random();
		snake.setDirection(rng.nextInt(3));
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}
}
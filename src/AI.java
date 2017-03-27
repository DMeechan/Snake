import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class AI extends Player {
	Timeline timer;
	Food food;
	ArrayList<Player> players;
	//boolean foodIsUp, foodIsRight;
	
	public AI(int stepSize, String colour, int playerNum, ArrayList<Player> players) {
		super(stepSize, colour, playerNum);
		this.players = players;
		// randomAI();

	}

	public int findFoodDirectionY() {
		SnakePiece head = getSnake().getFirst();
		if(food.getPosY() != head.getPosY()) {
			if(head.getPosY() - food.getPosY() < 0) {
				// negative ; go down
				return 1;
			} else {
				// positive; go up
				return 0;
			}
		} else {
			// return -1 means it is on the correct axis already
			return -1;
		}
	}
	
	public int findFoodDirectionX() {
		SnakePiece head = getSnake().getFirst();
		if(food.getPosX() != head.getPosX()) {
			if(head.getPosX() - food.getPosX() < 0) {
				// negative; go right
				return 2;
			} else {
				// positive; go left
				return 3;
			}
		} else {
			// return -1 means it is on the correct axis already
			return -1;
		}
	}

	public void timerTick() {
		if(!wallCollisionDetection()) {
			// continue with whatever direction the wallCollisionDetection() set if it was true
			if(!snakeCollisionDetection()) {
				int directionX = findFoodDirectionX();
				if(directionX == -1) {
					turn(findFoodDirectionY());
				} else {
					turn(directionX);
				}
			}
		}
	}
	
	public boolean snakeCollisionDetection() {
		SnakePiece head = getSnake().getFirst();
		int direction = getSnake().getDirection();
		int[] result = expectedPosition(head.getPosX(), head.getPosY(), direction);
		int newPosX = result[0];
		int newPosY = result[1];
		
		for (Player checkee : players) {
			if (checkee.snake.getAlive()) {
				for (SnakePiece piece : checkee.snake) {
					if (!head.equals(piece)) { // make sure it isn't colliding with its own head...
						if (newPosX == piece.getPosX() && newPosY == piece.getPosY()) {
							// they may collide; take evasive action
							if(direction == 2 || direction == 3) {
								// move up or down
								turn(findFoodDirectionY());
							} else {
								// move left or right
								turn(findFoodDirectionX());
							}
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}
	
	private int[] expectedPosition(int currentPosX, int currentPosY, int direction) {
		int increment = 10;
		int newPosX = 0; int newPosY = 0;
		switch(direction) {
			case 0:
				newPosY = currentPosY - increment;
				newPosX = currentPosX;
				break;
			case 1:
				newPosY = currentPosY + increment;
				newPosX = currentPosX;
				break;
			case 2:
				newPosX = currentPosX + increment;
				newPosY = currentPosY;
				break;
			case 3:
				newPosX = currentPosX - increment;
				newPosY = currentPosY;
				break;
			default:
				break;
		}
		
		int[] result = {newPosX, newPosY};
		return result;
	}
	
	public boolean wallCollisionDetection() {
		int currentDirection = getSnake().getDirection();
		int posX = getSnake().getFirst().getPosX();
		int posY = getSnake().getFirst().getPosY();
		
		if((posX == 390 && currentDirection == 2) || (posX == 10 && currentDirection == 3)) {
			// about to smash into wall
			// need to move up or down
			getSnake().setDirection(0); // at least try to go up
			turn(findFoodDirectionY());
			return true;
		} else if ((posY == 390 && currentDirection == 1) || (posY == 10 && currentDirection == 0)) {
			// need to move left or right
			getSnake().setDirection(3);
			turn(findFoodDirectionX());
			return true;
		} else {
			return false;
		}
	}

	public void turn(int newDirection) {
		// 0 = up; 1 = down; 2 = right; 3 = left
		int oldDirection = getSnake().getDirection();
		switch(newDirection) {
			case 0: // up
				if(oldDirection != 0) { // not already up
					if(oldDirection == 1) {
						// down - need to move on X axis first instead
						turn(findFoodDirectionX());
					} else {
						// left or right
						getSnake().setDirection(0);
					}
				}
				break;

			case 1: // down
				if(oldDirection != 1) { // not already down
					if(oldDirection == 0) {
						// up - need to move on X axis first instead
						turn(findFoodDirectionX());
					} else {
						// left or right
						getSnake().setDirection(1);
					}
				}
				break;

			case 2: // right
				if(oldDirection != 2) { // not already right
					if(oldDirection == 3) {
						// left - need to move on Y axis first instead
						turn(findFoodDirectionY());
					} else {
						// up or down
						getSnake().setDirection(2);
					}
				}
				break;

			case 3: // left
				if(oldDirection != 3) { // not already left
					if (oldDirection == 2) {
						// right - need to move on Y axis first instead
						turn(findFoodDirectionY());
					} else {
						// up or down
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
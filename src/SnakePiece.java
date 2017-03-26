public class SnakePiece {
	private int posX;
	private int posY;
	private int status; // 0 = head; 1 = body; 2 = tail
	public Snake snake;
	
	public SnakePiece() {
		setStatus(2);
	}
	
	public SnakePiece(int status, int posX, int posY) {
		setStatus(status);
		setPosX(posX);
		setPosY(posY);
	}
	
	private void moveHead() {
		switch(snake.getDirection()) {
			case 0: // north
				posY = posY - snake.stepSize;
				break;
			case 1: // south
				posY = posY + snake.stepSize;
				break;
			case 2: // east
				posX = posX + snake.stepSize;
				break;
			case 3: // west
				posX = posX - snake.stepSize;
				break;
			
			default:
				Main.outputError("Error, attempting to move in invalid direction: " + snake.getDirection());
				break;
		}
	}
	
	public boolean tryMoveHead() {
		moveHead();
		// may need to make these values dynamic in the future to accommodate different canvas sizes
		if(
			getPosX() > 0 &&
			getPosX() < 400 &&
			getPosY() > 0 &&
			getPosY() < 400
		) {
			return true;
		}
		return false;
	}

	public int getPosX() {
		return this.posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return this.posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		if(status >= 0 && status <= 2) {
			this.status = status;
		} else {
			Main.outputError("Snake piece status out of bounds. Status: " + Integer.toString(status));
		}
	}
}
public class SnakePiece {
	private int posX;
	private int posY;
	private int status; // 0 = head; 1 = body; 2 = tail
	private int stepSize;
	
	public SnakePiece() {
		setStatus(2);
	}
	
	public SnakePiece(int status, int posX, int posY) {
		setStatus(status);
		setPosX(posX);
		setPosY(posY);
	}
	
	public SnakePiece(int status, int posX, int posY, int stepSize) {
		setStatus(status);
		setPosX(posX);
		setPosY(posY);
		this.stepSize = stepSize;
	}
	
	private void moveHead(int snakeDirection) {
		switch(snakeDirection) {
			case 0: // north
				posY = posY - stepSize;
				break;
			case 1: // south
				posY = posY + stepSize;
				break;
			case 2: // east
				posX = posX + stepSize;
				break;
			case 3: // west
				posX = posX - stepSize;
				break;
			
			default:
				Main.outputError("Error, attempting to move in invalid snakeDirection: " + snakeDirection);
				break;
		}
		
	}
	
	public boolean tryMoveHead(int snakeDirection) {
		moveHead(snakeDirection);
		// may need to make these values dynamic in the future to accommodate different canvas sizes
		//System.out.println("Head position X: "  + getPosX() + " Y: " + getPosY());
		return getPosX() > 0 &&
				getPosX() < 400 &&
				getPosY() > 0 &&
				getPosY() < 400;
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
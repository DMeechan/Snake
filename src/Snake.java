import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.paint.Color;

import java.util.Iterator;
import java.util.LinkedList;

public class Snake extends LinkedList<SnakePiece> {
	private int direction;
	public int stepSize;
	public BooleanProperty alive = new SimpleBooleanProperty();
	public Color colour = new Color(0,0,0,0);
	
	public Snake(int stepSize, String colour, int playerNum) {
		super();
		this.setColour(Color.web(colour));
		setAlive(true);
		setStepSize(stepSize);
		setDirection(2);
		
		int pos = (30 + (playerNum * 20));
		
		this.add(new SnakePiece(0, pos, pos, stepSize)); // head
		this.add(new SnakePiece(1, pos - getStepSize(), pos)); // body
		this.add(new SnakePiece(1, pos - getStepSize() - getStepSize(), pos));
		this.add(new SnakePiece(2, pos - getStepSize() - getStepSize() - getStepSize(), pos)); // tail
		//this.add(new SnakePiece(1, pos - getStepSize() - getStepSize()- getStepSize(), pos));
		//this.add(new SnakePiece(1, pos - getStepSize() - getStepSize()- getStepSize()- getStepSize(), pos));
		//this.add(new SnakePiece(1, pos - getStepSize() - getStepSize()- getStepSize()- getStepSize()- getStepSize(), pos));
		
	}
	
	public void addPiece() {
		// change old tail to body
		this.getLast().setStatus(1);
		
		// create new tail
		this.add(new SnakePiece());
	}
	
	public void move() {
	
		Iterator<SnakePiece> position = this.descendingIterator();
		
		SnakePiece current = this.getLast(); // starts at the end
		SnakePiece next = null;
		
		// move whole body and tail except head
		
		while (position.hasNext() && (current.getStatus() > 0)) {
				next = position.next(); // hopefully position.next will increment it to the next one
				current.setPosX(next.getPosX());
				current.setPosY(next.getPosY());
				current = next;
		}
		
		// now move head
		if(isAlive()) {
			if (!this.getFirst().tryMoveHead(getDirection())) {
				// collided with wall
				setAlive(false);
			}
		}
		
	}
	
	public int getStepSize() {
		return stepSize;
	}
	
	public void setStepSize(int stepSize) {
		this.stepSize = stepSize;
	}
	
	public boolean getAlive() {
		return alive.get();
	}
	
	public BooleanProperty aliveProperty() {
		return alive;
	}
	
	public void setAlive(boolean alive) {
		this.alive.set(alive);
	}
	
	public int getDirection() {
		return this.direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public boolean isAlive() {
		return alive.get();
	}
	
	public Color getColour() {
		return colour;
	}
	
	public void setColour(Color colour) {
		this.colour = colour;
	}
}
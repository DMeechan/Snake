import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class Snake extends LinkedList<SnakePiece> {
	private int direction;
	public int stepSize;
	public BooleanProperty alive = new SimpleBooleanProperty();
	
	public Snake(int stepSize) {
		super();
		setAlive(true);
		setStepSize(stepSize);
		setDirection(2);
		
		this.add(new SnakePiece(0, 200, 200, stepSize)); // head
		this.add(new SnakePiece(1, 200 - getStepSize(), 200)); // body
		this.add(new SnakePiece(1, 200 - getStepSize() - getStepSize(), 200));
		this.add(new SnakePiece(2, 200 - getStepSize() - getStepSize() - getStepSize(), 200)); // tail
		
	}
	
	public void addPiece() {
		// change old tail to body
		this.getLast().setStatus(1);
		
		// create new tail
		this.add(new SnakePiece());
	}
	
	public void move() {
		//ListIterator<SnakePiece> position = this.listIterator(this.size()); // set position to pos of end
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
		if (this.getFirst().tryMoveHead(getDirection())) {
			// all fine
			
		} else {
			// collided with wall
			setAlive(false);
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
}
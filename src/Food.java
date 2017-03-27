import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Random;

public class Food {
	int posX, posY;
	BooleanProperty eaten = new SimpleBooleanProperty();
	public Food() {
		setEaten(false);
		Random rng = new Random();

		//posX = rng.nextInt(395);
		posX = rng.nextInt(390 - 10 + 1) + 10;
		posX = ((posX + 5) / 10)*10;
		posY = rng.nextInt(390 - 10 + 1) + 10;
		posY = ((posY + 5) / 10)*10;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public boolean isEaten() {
		return eaten.get();
	}

	public BooleanProperty eatenProperty() {
		return eaten;
	}

	public void setEaten(boolean eaten) {
		this.eaten.set(eaten);
	}
}
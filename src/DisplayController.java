import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

public class DisplayController extends Stage implements Initializable {
	@FXML private BorderPane pane;
	@FXML private Canvas canvas;
	private GraphicsContext gc;
	private Game game;
	private int stepSize;
	private ArrayList<Player> players;
	private DropShadow headGlow;
	
	public DisplayController() {

	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gc = canvas.getGraphicsContext2D();
		headGlow = new DropShadow();
		headGlow.setOffsetX(0f);
		headGlow.setOffsetY(0f);
	}
	
	public void setUpDisplay(Game game) {
		setGame(game);
		players = game.players;
		draw();
		
		for (Player player : players) {
			player.snake.aliveProperty().addListener(v -> {
				if (!player.snake.getAlive()) {
					// player DIED MATE
					playerDied(player);
				}
			});
		}

		headGlow.setWidth(stepSize * 1.2);
		headGlow.setHeight(stepSize * 1.2);
		
	}
	
	public void displayGameOver() {
		gc.setFill(Color.web("#e74c3c"));
		headGlow.setColor(Color.web("#e74c3c"));

		gc.setGlobalAlpha(0.7);
		gc.setEffect(headGlow);
		gc.fillText("GAME OVER", 20, 150);
		gc.setEffect(null);
		gc.setGlobalAlpha(1);
	}
	
	public void playerDied(Player player) {
		
		Timeline timer = new Timeline((new KeyFrame(
				Duration.millis(300),
				event -> {
					setDeadColour(player.snake);
				}
		)));
		
		timer.setCycleCount(10);
		timer.play();
		
		timer.setOnFinished(v -> {
			players.remove(player);
			players.trimToSize();
			// FIX!
			//players.add(new AI(stepSize, Game.colours[0], 1));
			//
		});
	}
	
	public void setDeadColour(Snake snake) {
		Color deadColourLight = Color.web("#e74c3c");
		Color deadColourDark = Color.web("#c0392b");
		
		if(snake.getColour().equals(deadColourLight)) {
			snake.setColour(deadColourDark);
		} else {
			snake.setColour(deadColourLight);
		}
	}
	
	public void draw() {
		clear();
		
		int counter = 0;
		Color colour;
		for (Player player : players) {
			drawSnake(player.snake);
			
			counter++;
		}

		drawFood();
		
		if(!game.isRunning()) {
			gc.setFont(new Font("Arial Rounded MT Bold", 58));
			displayGameOver();
		}
		
	}
	
	private void drawSnake(Snake snake) {
		drawSnake(snake, snake.getColour());
	}
	
	public void drawSnake(Snake snake, Color colour) {
		gc.setFill(colour);
		Iterator<SnakePiece> position = snake.descendingIterator();
		SnakePiece current = snake.get(snake.size() - 1);
		position.next(); // move position from tale to piece before tail
		
		// draw whole body except head and tail
		while (position.hasNext() && (current.getStatus() > 0)) {
			drawBody(current.getPosX(), current.getPosY());
			current = position.next();
		}

		headGlow.setColor(colour);
		gc.setEffect(headGlow);
		drawHead(snake.getFirst().getPosX(), snake.getFirst().getPosY());
		gc.setEffect(null);
		drawTail(snake.getLast().getPosX(), snake.getLast().getPosY());
	}
	
	public void clear() {
		gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
		gc.setFill(Color.web("#2c3e50"));
		//gc.fill();
		gc.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
		
	}

	public void drawFood() {
		gc.setFill(Color.web("#ecf0f1"));
		gc.fillOval(game.food.getPosX(), game.food.getPosY(), stepSize, stepSize);
	}

	public void drawHead(int posX, int posY) {
		gc.fillOval(posX, posY, stepSize, stepSize);

		gc.fillRect(posX,posY,stepSize,stepSize);

	}

	public void drawBody(int posX, int posY) {
		gc.fillRect(posX,posY,stepSize,stepSize);
	}

	public void drawTail(int posX, int posY) {
		gc.fillRect(posX,posY,stepSize,stepSize);
	}
	
	
	/////////////////////////////////////
	
	
	public GraphicsContext getGc() {
		return gc;
	}
	
	public void setGc(GraphicsContext gc) {
		this.gc = gc;
	}
	
	public Canvas getCanvas() {
		return this.canvas;
	}
	
	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}
	
	public BorderPane getPane() {
		return pane;
	}
	
	public void setPane(BorderPane pane) {
		this.pane = pane;
	}
	
	public Game getGame() {
		return game;
	}
	
	public void setGame(Game game) {
		this.game = game;
		stepSize = game.stepSize;
	}
}
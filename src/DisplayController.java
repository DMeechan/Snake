import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.ResourceBundle;

public class DisplayController extends Stage implements Initializable {
	@FXML private BorderPane pane;
	@FXML private Canvas canvas;
	private GraphicsContext gc;
	private Game game;
	private int stepSize;
	private Player[] players;
	
	public DisplayController() {
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gc = canvas.getGraphicsContext2D();
		
	}
	
	public void setUpDisplay(Game game) {
		setGame(game);
		players = game.players;
		draw();
	}
	
	public void draw() {
		clear();
		
		for (Player player : players) {
			Snake snake = player.snake;
			if(snake.getAlive()) {
				// Alive
				Iterator<SnakePiece> position = snake.descendingIterator();
				SnakePiece current = snake.get(snake.size() - 1);
				position.next(); // move position from tale to piece before tail
				
				// draw whole body except head and tail
				while (position.hasNext() && (current.getStatus() > 0)) {
					drawBody(current.getPosX(), current.getPosY());
					current = position.next();
				}
				
				drawHead(snake.getFirst().getPosX(), snake.getFirst().getPosY());
				drawTail(snake.getFirst().getPosX(), snake.getFirst().getPosY());
			} else {
				// Dead
			}
			
		}
		
	}
	
	public void clear() {
		gc.clearRect(0,0,400,400);
		
		gc.setFill(Color.web("#2c3e50"));
		gc.fillRect(0,0,canvas.getWidth(),canvas.getWidth());
		
		gc.setFill(Color.web("#2ecc71"));
	}

	public void drawHead(int posX, int posY) {
		gc.fillRect(posX,posY,stepSize,stepSize);
		
		/*
		gc.beginPath();
		gc.moveTo(50, 50);
		gc.bezierCurveTo(150, 20, 150, 150, 75, 150);
		gc.closePath();
		*/
	}

	public void drawBody(int posX, int posY) {
		gc.fillRect(posX,posY,stepSize,stepSize);
	}

	public void drawTail(int posX, int posY) {
		gc.fillRect(posX,posY,stepSize,stepSize);
	}
	
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
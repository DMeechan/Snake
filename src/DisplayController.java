import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.net.URL;
import java.util.ResourceBundle;

public class DisplayController implements Initializable {
	@FXML private Canvas canvas;
	private GraphicsContext gc;
	private Game game;
	
	public DisplayController(Game game) {
		this.game = game;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gc = canvas.getGraphicsContext2D();
		//gc.fillr
	}
	
	public Canvas getCanvas() {
		return this.canvas;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public void draw() {
		drawBody(0,0);
		drawBody(0,0);
		drawTail(0,0);
	}

	public void drawHead(int posX, int posY) {
		
	}

	public void drawBody(int posX, int posY) {
		
	}

	public void drawTail(int posX, int posY) {
		
	}
}
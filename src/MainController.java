import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class MainController extends Stage {
	private Game game;
	private DisplayController displayController;
	private Scene scene;
	private Timeline timer;
	private int numHumans, numAIs;
	
	public MainController(int numHumans, int numAIs) {
		if(numHumans > -1 && numHumans < 3 && numAIs > -1 && numAIs < 15 && (numHumans + numAIs) > 0) {
			this.numHumans = numHumans;
			this.numAIs = numAIs;

			loadDisplayFXMLLoader();
			loadScene();
			newGame();

		} else {
			Main.outputError("Error: invalid number of players entered. Maximum of: 2 humans and 14 AIs");
		}
	}

	public void newGame() {
		System.out.println("<----- NEW GAME ----->");
		game = new Game(10, scene, numHumans, numAIs);
		displayController.setUpDisplay(game);

		timer = new Timeline((new KeyFrame(
				Duration.millis(60),
				event -> timerTick())));
		timer.setCycleCount(Animation.INDEFINITE);

		start();
	}
	
	private void start() {
		game.start();
		timer.play();
		
		Timeline stopCountdown = new Timeline((new KeyFrame(
				Duration.millis(3000),
				event -> stop())));
		stopCountdown.setCycleCount(1);
		
		game.runningProperty().addListener(v -> {
			if(!game.isRunning()) {
				stopCountdown.play();
			}
		});
	}
	
	public void stop() {
		timer.stop();
		game = null;
		newGame();
	}
	
	private void timerTick() {
		displayController.draw();
		game.timerTick();
	}
	
	
	private void loadDisplayFXMLLoader() {
		FXMLLoader displayFXMLLoader = new FXMLLoader(getClass().getResource("DisplayView.fxml"));
		
		try {
			scene = new Scene(displayFXMLLoader.load(), 400, 425);
		} catch (IOException e) {
			Main.outputError(e);
		}
		
		displayController = displayFXMLLoader.getController();
		
	}
	
	private void loadScene() {
		this.setScene(scene);
		this.show();
		
		// ensure the window closes correctly
		this.setOnCloseRequest(v -> {
			Platform.exit();
			System.exit(0);
		});
		
		this.setTitle("Snake!");
		this.setResizable(false);
		
		// try to load application icon
		// this implementation makes the file handling platform-agnostic
		// so the icon should work on different platforms
		// (however, setting the icon Dock icon on Mac requires making additional calls)
		try {
			this.getIcons().add(new Image(this.getClass().getResourceAsStream("icon.png")));
			//Icon made by freepik.com and flaticon.com
			// Flaticon is licensed by Creative Commons 3.0 BY
			
		} catch (Exception e) {
			System.out.println("Error: application icon not found");
			Main.outputError(e);
		}
	}

	
}
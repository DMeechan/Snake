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
	
	public MainController() {
		loadDisplayFXMLLoader();
		loadScene();
		
		game = new Game(10, scene);
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
		
		game.runningProperty().addListener(v -> {
			if(!game.isRunning()) {
				stop();
			}
		});
	}
	
	public void stop() {
		timer.stop();
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
	
	public Timeline getTimer() {
		return this.timer;
	}
	
	public void setTimer(Timeline timer) {
		this.timer = timer;
	}
	
}
import javafx.stage.Stage;

public class MainController extends Stage {
	private Game game;
	public DisplayController displayController;
	
	public MainController() {
		setTitle("Snake");
		//setScene(new Scene(pane, 350, 400));
		show();
		
		run();
	}

	public void run() {
		Game game = new Game(10);
	}

}
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Main extends Application {
    
    @Override
    public void start(Stage window) throws Exception {
        // set up basic window - all activity will happen inside the window object
	    // max: 2 human players; 14 AI players
        window = new MainController(1,1);

	    // TODO: Fix multiple Human bug: keyboard input conflict
        // TODO: Fix square (coloured like the latest snake) appearing in top left corner when food spawns
        
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public static void outputError(Exception e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        alert.setContentText(e.getMessage());
        
        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String exceptionText = sw.toString();
        
        Label label = new Label("The exception stacktrace was:");
        
        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        
        textArea.setMinWidth(550);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);
        
        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);
        
        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);
        alert.showAndWait();
        
    }
    
    public static void outputError(String e) {
        System.out.println(e);
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        alert.setContentText(e);
        alert.showAndWait();
        
    }
    
    public static void outputWarning(String e) {
        new Alert(Alert.AlertType.WARNING, e).showAndWait();
    }
}
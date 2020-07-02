/* This program is a game that replicates
 * the original rules to the NIM math game.  Uses 'coins' to visually keep track
 * of what moves can be made.  Creates a very simplistic AI that takes turns after
 * the user.  creates a gui for the user to use to play the game.
 * created by ethan infelice
 */
package nim.game;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class NIMGAME extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("src\\nim\\game\\FXMLnim.fxml"));
        Scene scene = new Scene(root, 800, 600);
        
        stage.setTitle("NIM Math Game");
        stage.setScene(scene);
        stage.show();
        

        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
   
    
}

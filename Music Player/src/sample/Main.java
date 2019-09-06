package sample;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

/***
 * @author Abhishek Kumar
 * Github - iamabhishek229313
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Music Player cum Horrible Thing"); // :)
        primaryStage.setScene(new Scene(root, 732, 463));
        Slider volume = (Slider) root.lookup("#volumeSlider");
        Slider track = (Slider) root.lookup("#trackSlider");
        track.setMin(0.0);
        track.setMax(100.0);
        volume.setMin(0);
        volume.setMax(100);
        volume.setValue(43);
        volume.setShowTickLabels(true);
        volume.setShowTickMarks(true);
        volume.setMajorTickUnit(50);
        volume.setMinorTickCount(5);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}

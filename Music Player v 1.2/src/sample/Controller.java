package sample;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration ;
import java.io.File;

public class Controller {
    public MediaPlayer mediaPlayer ;
    public Media media ;

    @FXML public Button playButton ;
    public void playClicked() {
        if (playButton.getText().contentEquals(" Play ")) {
            if (mediaPlayer == null) {
                openAndPlay();
                playButton.setText("Pause");
            }else if(mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED){
                mediaPlayer.play();
                playButton.setText("Pause");
            }
        }else {
                playButton.setText(" Play ");
                mediaPlayer.pause();
            }
    }
    @FXML public  Button stopButton ;
    public void stopClicked(){
        if((mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) ||(mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING)){
            mediaPlayer.stop();
            trackName.setText("Not Playing Anything");
            artistName.setText("Artist : ?");
            albumName.setText("Album : ?");
            genreName.setText("Genre : ?");
            playButton.setText(" Play ");
        }
    }
    @FXML public Button muteButton ;
    public void muteClicked(){
        if(muteButton.getText().contentEquals(" Mute ")){
            mediaPlayer.setMute(true);
            muteButton.setText("Unmute");
        }else{
            mediaPlayer.setMute(false);
            muteButton.setText("  Mute  ");
        }

    }

    @FXML public Slider volumeSlider ;
    @FXML public Slider trackSlider ;
    @FXML public Button fileButton ;
    @FXML public Text totalDuration;
    @FXML public Text currentDuration ;
    @FXML public Text trackName ;
    @FXML public Text artistName ;
    @FXML public Text albumName;
    @FXML public Text genreName ;
    public void openAndPlay(){
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Media File");
            File mediaFile = fileChooser.showOpenDialog(new Stage());
            media = new Media(mediaFile.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setOnReady(()->{
                trackName.setText((String) media.getMetadata().get("title"));
                artistName.setText("Artist : " +(String)media.getMetadata().get("artist"));
                albumName.setText("Album : " +(String)media.getMetadata().get("album"));
                genreName.setText("Genre : " +(String)media.getMetadata().get("genre"));
                volumeSlider.setValue(100);
                volumeSlider.valueProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable observable) {
                        mediaPlayer.setVolume(volumeSlider.getValue() / 100);
                    }
                });

                    trackSlider.valueProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                            if(Math.abs(t1.doubleValue()-number.doubleValue()) > 0.1){
                                mediaPlayer.seek(Duration.seconds(mediaPlayer.getTotalDuration().toSeconds() * t1.doubleValue() / 100.0));
                            }
                            trackSlider.lookup(".track")
                                .setStyle(String.format(
                                    "-fx-background-color: linear-gradient(to right, #50c878 %d%%, #969696 %d%%)",
                                        t1.intValue(), t1.intValue()));
                        }
                    });
                mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable observable) {
                        updatesValue();
                    }
                });
                totalDuration.setText(getTimeString(mediaPlayer.getTotalDuration()));
                mediaPlayer.play();
            });
    }

    public void updatesValue(){
        trackSlider.setValue((mediaPlayer.getCurrentTime().toMillis() / mediaPlayer.getTotalDuration().toMillis()) * 100);
        currentDuration.setText(getTimeString(mediaPlayer.getCurrentTime()));
    }

    private String getTimeString(Duration totalDuration) {
        if((int)totalDuration.toHours() == 0 ){
            return String.format("%02d:%02d" ,(int)totalDuration.toMinutes(),(int)totalDuration.toSeconds()%60);
        }else{
            return String.format("%02d:%02d:%02d",(int)totalDuration.toHours(),(int)totalDuration.toMinutes()%60,(int)totalDuration.toSeconds()%3600);
        }
    }



}





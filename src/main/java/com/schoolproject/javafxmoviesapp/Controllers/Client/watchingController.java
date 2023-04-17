package com.schoolproject.javafxmoviesapp.Controllers.Client;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class watchingController implements Initializable {
    @FXML
    private FlowPane watchingLayout;
    @FXML
    private MediaView mediaViewLayout;
    @FXML
    private Button playMedia, pauseMedia ,resetMedia,preMedia,nextMedia,playMeidaIcon,zoomFullScreen,report;
    @FXML
    private  ProgressBar progressBar;
    @FXML
    private ComboBox<String> speedMedia;
    private File file;
    private  File[] files;
    private ArrayList<File> songs;
    private int songNumber;
    private int[ ] speeds={25,50,75,100,125,150,175,200,500};
    private TimerTask task;
    private Timer  timer;
    private boolean running;
    private  Media media;
    private MediaPlayer mediaPlayer;
    private Timeline timeline;
    @FXML
    private Slider volumeSlider;
    @FXML
    private  Label nameMovie;
    @FXML
    private Pane handlerActionMedia;

    public void initialize(URL agr0, ResourceBundle arg1) {
        playMeidaIcon.setVisible(false);
        songs = new ArrayList<File>();
        file = new File("D:/video");
        files = file.listFiles();
        if(files != null) {
            for (File file:files) {
                songs.add(file);
                System.out.println(file);
            }
        }
        media =  new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer= new MediaPlayer(media);
        mediaViewLayout.setMediaPlayer(mediaPlayer);
        nameMovie.setText(songs.get(songNumber).getName().substring(0, songs.get(songNumber).getName().lastIndexOf('.')));
        speedMedia.setValue("100");
        for(int i=0; i < speeds.length; i++) {
            speedMedia.getItems().add(Integer.toString(speeds[i]));
        }
        speedMedia.setOnAction(this::speedMedia);

        mediaViewLayout.setOnMouseEntered(event -> {
            handlerActionMedia.setVisible(true);
            System.out.println("hover video");
        });
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            handlerActionMedia.setVisible(false);
        }));
        mediaViewLayout.setOnMouseExited(event -> {
            timeline.play();
            System.out.println("end hover video");
        });
    }
    public void playMedia(){
        nameMovie.setText(songs.get(songNumber).getName().substring(0, songs.get(songNumber).getName().lastIndexOf('.')));
        beginTimer();
        speedMedia(null);
       mediaPlayer.play();
        playMedia.setVisible(false);
        pauseMedia.setVisible(true);
        playMeidaIcon.setVisible(false);
    }
    public void pauseMedia(){
        cancelTime();
        mediaPlayer.pause();
        playMedia.setVisible(true);
        pauseMedia.setVisible(false);
        playMeidaIcon.setVisible(true);
    }
    public void resetMedia(){
        progressBar.setProgress(0);
    mediaPlayer.seek(Duration.seconds(0));
    playMedia();
    }
    public  void preMedia(){
        if( songNumber > 0){
            songNumber --;
            mediaPlayer.stop();
            if(running) {
                cancelTime();
            }
            media =  new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer= new MediaPlayer(media);
            mediaViewLayout.setMediaPlayer(mediaPlayer);
            playMedia();
        } else {
            songNumber = songs.size()  -1 ;
            mediaPlayer.stop();
            if(running) {
                cancelTime();
            }
            media =  new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer= new MediaPlayer(media);
            mediaViewLayout.setMediaPlayer(mediaPlayer);
            playMedia();
        }
    }
    public void nextMedia(){
        if(songNumber < songs.size() -1 ){
            songNumber ++;
            mediaPlayer.stop();
            if(running) {
                cancelTime();
            }
            media =  new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer= new MediaPlayer(media);
            mediaViewLayout.setMediaPlayer(mediaPlayer);
            playMedia();
        } else {
            songNumber =0 ;
            mediaPlayer.stop();
            if(running) {
                cancelTime();
            }
            media =  new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer= new MediaPlayer(media);
            mediaViewLayout.setMediaPlayer(mediaPlayer);
            playMedia();
        }
    }
    public void speedMedia(ActionEvent event){
        if(speedMedia.getValue() == null){
            mediaPlayer.setRate(1);
        } else{
            mediaPlayer.setRate(Integer.parseInt(speedMedia.getValue()) * 0.01);
        }
    }
    public void volume(){
        mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
}
    public  void beginTimer(){
        timer =  new Timer();
        task = new TimerTask(){
            public void  run() {
                running = true;
                double current = mediaPlayer.getCurrentTime().toSeconds();
                double end = media.getDuration().toSeconds();
                System.out.println(current/end);
                progressBar.setProgress(current/end);
                if(current/end == 1){
                    cancelTime();
                    nextMedia();
                }
            }
        };
        timer.scheduleAtFixedRate(task,1000,1000);
    }
    public void cancelTime() {
        running = false;
        timer.cancel();
    }
    //report handler function
//    @FXML
    public void handleReport() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Fxml/Client/report.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
    public void handleNext () throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Fxml/Client/nextEpisode.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}

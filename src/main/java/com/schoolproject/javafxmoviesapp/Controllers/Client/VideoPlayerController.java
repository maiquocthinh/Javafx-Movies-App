package com.schoolproject.javafxmoviesapp.Controllers.Client;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.ResourceBundle;

public class VideoPlayerController implements Initializable {

    // Checks if the video is at the end.
    private boolean atEndOfVideo = false;
    // Video is not playing when GUI starts.
    private boolean isPlaying = false;
    // Checks if the video is muted or not.
    private boolean isMuted = true;

    // ikonli: icon of the buttons and labels
    private final FontIcon playIcon = new FontIcon("fas-play");
    private final FontIcon pauseIcon = new FontIcon("fas-pause");
    private final FontIcon restartIcon = new FontIcon("fas-undo");
    private final FontIcon fullScreenIcon = new FontIcon("fas-expand-alt");
    private final FontIcon exitFullScreenIcon = new FontIcon("fas-compress-alt");
    private final FontIcon fullVolumeIcon = new FontIcon("fas-volume-up");
    private final FontIcon miniVolumeIcon = new FontIcon("fas-volume-down");
    private final FontIcon muteVolumeIcon = new FontIcon("fas-volume-mute");

    private double totalTimeVideo = 0;


    // The main container of the application that holds everything.
    @FXML
    private AnchorPane videoPlayerAnchorPane;

    @FXML
    private MediaView mediaViewVideo;
    private MediaPlayer mediaPlayerVideo;
    private Media mediaVideo;

    @FXML
    private BorderPane controlBorderPane;

    @FXML
    private Button PPRButton;

    @FXML
    private Button fullscreenButton;

    @FXML
    private Label timeIndicatorLabel;

    @FXML
    private Slider timeSlider;

    @FXML
    private Label volumeLabel;

    @FXML
    private Slider volumeSlider;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set size for Icons
        playIcon.setIconSize(32);
        pauseIcon.setIconSize(32);
        restartIcon.setIconSize(32);
        fullScreenIcon.setIconSize(32);
        exitFullScreenIcon.setIconSize(32);
        fullVolumeIcon.setIconSize(32);
        miniVolumeIcon.setIconSize(32);
        muteVolumeIcon.setIconSize(32);

        // Set color for Icons
        playIcon.setFill(Color.web("#ffffffcc"));
        pauseIcon.setFill(Color.web("#ffffffcc"));
        restartIcon.setFill(Color.web("#ffffffcc"));
        fullScreenIcon.setFill(Color.web("#ffffffcc"));
        exitFullScreenIcon.setFill(Color.web("#ffffffcc"));
        fullVolumeIcon.setFill(Color.web("#ffffffcc"));
        miniVolumeIcon.setFill(Color.web("#ffffffcc"));
        muteVolumeIcon.setFill(Color.web("#ffffffcc"));

        // Set defaults aka originally static content
        PPRButton.setGraphic(playIcon);
        volumeLabel.setGraphic(muteVolumeIcon);
        fullscreenButton.setGraphic(fullScreenIcon);
    }

    @FXML
    void PPRButtonHandleAction(ActionEvent event) {
        // If it is the end of the video then reset the slider to 0 and restart the video.
        if (atEndOfVideo) {
            timeSlider.setValue(0.0);
            atEndOfVideo = false;
            isPlaying = false;
        }
        // If the video is playing and the button is clicked pause the video and change the image on the button to play.
        if (isPlaying) {
            PPRButton.setGraphic(playIcon);
            mediaPlayerVideo.pause();
            // The video is now paused so change it to false.
            isPlaying = false;
        } else {
            // The video was paused so when the button is clicked change the image to stop and play video.
            PPRButton.setGraphic(pauseIcon);
            mediaPlayerVideo.play();
            // The video is now playing so isPlaying is true.
            isPlaying = true;
        }
    }

    // When click switch between mute volume & unmute volume
    @FXML
    void volumeLabelHandleMouseClicked(MouseEvent event) {
        if (isMuted) {
            volumeLabel.setGraphic(miniVolumeIcon);
            volumeSlider.setValue(25.0);
            isMuted = false;
        } else {
            volumeLabel.setGraphic(muteVolumeIcon);
            volumeSlider.setValue(0.0);
            isMuted = true;
        }
    }

    // Handle fullscreen & exit fullscreen
    @FXML
    void fullscreenButtonHandleAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        if (stage.isFullScreen()) {
            fullscreenButton.setGraphic(fullScreenIcon);
            exitFullScreenAction(stage);
        } else {
            fullscreenButton.setGraphic(exitFullScreenIcon);
            fullScreenAction(stage);
            stage.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
                if (keyEvent.getCode() == KeyCode.ESCAPE) {
                    fullscreenButton.setGraphic(fullScreenIcon);
                }
            });
        }
    }


    /**
     * Set data of video player
     *
     * @param urlVideo
     */
    public void setData(String urlVideo) {
        stopVideoPlayer();

        videoPlayerAnchorPane.requestFocus();

        mediaVideo = new Media(urlVideo);
        mediaPlayerVideo = new MediaPlayer(mediaVideo);
        mediaViewVideo.setMediaPlayer(mediaPlayerVideo);
        initBindingsVideoPlayer();
    }

    public void stopVideoPlayer() {
        if (isPlaying) {
            isPlaying = false;
            mediaPlayerVideo.pause();
            timeSlider.setValue(0);
            timeIndicatorLabel.setText("00:00 | 00:00");
            PPRButton.setGraphic(playIcon);
        }
    }

    private void initBindingsVideoPlayer() {

        // Bind the volume of the video to the volume of the slider.
        // Because this is bindBidirectional it will bind both ways.
        mediaPlayerVideo.volumeProperty().bindBidirectional(volumeSlider.valueProperty());
        // Bind the value of the slider to the volume of the video.
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if (mediaPlayerVideo.getVolume() != 0.0) {
                    if (mediaPlayerVideo.getVolume() <= 40.0) {
                        volumeLabel.setGraphic(miniVolumeIcon);
                    } else {
                        volumeLabel.setGraphic(fullVolumeIcon);
                    }
                    isMuted = false;
                } else {
                    volumeLabel.setGraphic(muteVolumeIcon);
                    isMuted = true;
                }
            }
        });

        // Bind the width of the video player to the width of the videoPlayerAnchorPane.
        Platform.runLater(() -> {
            BorderPane root = (BorderPane) videoPlayerAnchorPane.getScene().getRoot();
            ScrollPane navbar = (ScrollPane) root.lookup("#NavbarScrollPane");
            mediaViewVideo.setFitWidth(root.getWidth() - navbar.getWidth() - 20);
            root.widthProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    double width = root.getWidth() - navbar.getWidth() - 20;
                    mediaViewVideo.setFitWidth(width);
                }
            });

        });

        // Get total time video & assign to totalTimeVideo when total time video change
        mediaPlayerVideo.totalDurationProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                timeSlider.setMax(newValue.toSeconds());
                totalTimeVideo = newValue.toSeconds();
            }
        });

        // Synchronize timeSlider with mediaPlayerVideo.getCurrentTime() when seek video
        final Node[] trackOfTimeSlider = {null};
        timeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                // Get the current time of the video in seconds.
                double currentTime = mediaPlayerVideo.getCurrentTime().toSeconds();
                if (Math.abs(currentTime - newValue.doubleValue()) > 0.5) {
                    mediaPlayerVideo.seek(Duration.seconds(newValue.doubleValue()));
                }

                // Style for track
                int percentage = (int) (newValue.doubleValue() / timeSlider.getMax() * 100);
                String style = String.format("-fx-background-color: linear-gradient(to right, #2D819D %d%%, #ffffffcc %d%%);", percentage, percentage);
                if (trackOfTimeSlider[0] == null) trackOfTimeSlider[0] = timeSlider.lookup(".track");
                trackOfTimeSlider[0].setStyle(style);
            }
        });

        // Synchronize current time when video play for timeSlider & timeIndicatorLabel
        mediaPlayerVideo.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                if (!timeSlider.isValueChanging()) {
                    timeSlider.setValue(newValue.toSeconds());
                }
                timeIndicatorLabel.setText(getTime(newValue) + " | " + getTime(Duration.seconds(totalTimeVideo)));
            }
        });

        // Listen end of video then set restartIcon & atEndOfVideo = true
        mediaPlayerVideo.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                PPRButton.setGraphic(restartIcon);
                atEndOfVideo = true;
            }
        });

        // Listen exit fullscreen
        Platform.runLater(() -> {
            Stage stage = (Stage) mediaViewVideo.getScene().getWindow();
            stage.fullScreenProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) exitFullScreenAction(stage);
            });
        });

        // Listen hover to show/hidden controlBorderPane
        {
            // Set up a Timeline to automatically hide the controlBorderPane after a few seconds of inactivity
            Timeline hideTimer = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
                controlBorderPane.setVisible(false);
                videoPlayerAnchorPane.requestFocus();
            }));

            // When mouse exit hidden controlBorderPane after a few seconds
            mediaViewVideo.setOnMouseExited(event -> {
                hideTimer.playFromStart();
            });

            // Reset the timer whenever the mouse moves over the media view or the control bar
            mediaViewVideo.setOnMouseMoved(event -> {
                controlBorderPane.setVisible(true);
                hideTimer.playFromStart();
            });

            controlBorderPane.setOnMouseMoved(event -> {
                controlBorderPane.setVisible(true);
                hideTimer.playFromStart();
            });

        }


    }


    private Parent root = null;
    private VBox parentOfVideoplayerVBox = null;

    // Handle of fullscreen action
    private void fullScreenAction(Stage stage) {
        if (root == null)
            root = stage.getScene().getRoot();
        if (parentOfVideoplayerVBox == null)
            parentOfVideoplayerVBox = (VBox) root.lookup("#videoPlayerAnchorPane").getParent();

        parentOfVideoplayerVBox.getChildren().remove(videoPlayerAnchorPane);

        stage.setFullScreen(true);
        stage.getScene().setRoot(videoPlayerAnchorPane);

        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();

        double videoRatio = (double) mediaVideo.getWidth() / mediaVideo.getHeight();
        double screenRatio = screenWidth / screenHeight;

        if (videoRatio >= screenRatio) mediaViewVideo.setFitWidth(screenWidth);
        else mediaViewVideo.setFitHeight(screenHeight);

    }

    // Handle of exit fullscreen action
    private void exitFullScreenAction(Stage stage) {
        if (root.getScene() != stage.getScene() && root.getScene() == null)
            stage.getScene().setRoot(root);
        if (!parentOfVideoplayerVBox.getChildren().contains(videoPlayerAnchorPane))
            parentOfVideoplayerVBox.getChildren().add(0, videoPlayerAnchorPane);
        stage.setFullScreen(false);
        mediaViewVideo.setFitWidth(parentOfVideoplayerVBox.getWidth());
    }


    /**
     * This function takes the time of the video and calculates the seconds, minutes, and hours.
     *
     * @param time - The time of the video.
     * @return Corrected seconds, minutes, and hours.
     */
    public String getTime(Duration time) {

        int hours = (int) time.toHours();
        int minutes = (int) time.toMinutes();
        int seconds = (int) time.toSeconds();

        // Fix the issue with the timer going to 61 and above for seconds, minutes, and hours.
        if (seconds > 59) seconds = seconds % 60;
        if (minutes > 59) minutes = minutes % 60;
        if (hours > 59) hours = hours % 60;

        // Don't show the hours unless the video has been playing for an hour or longer.
        if (hours > 0) return String.format("%d:%02d:%02d",
                hours,
                minutes,
                seconds);
        else return String.format("%02d:%02d",
                minutes,
                seconds);
    }


}

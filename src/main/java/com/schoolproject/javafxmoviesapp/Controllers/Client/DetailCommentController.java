package com.schoolproject.javafxmoviesapp.Controllers.Client;

import com.schoolproject.javafxmoviesapp.Entity.Comment;
import com.schoolproject.javafxmoviesapp.Entity.User;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class DetailCommentController {
    @FXML
    private ImageView avatarImageView;

    @FXML
    private Label commentContentLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label timeCommentedLabel;

    public void setData(Comment comment, User user){
        nameLabel.setText(user.getName());
        timeCommentedLabel.setText(formatRelativeTime(comment.getDate()));
        commentContentLabel.setText(comment.getContent());

        Task<Image> imageTask = new Task<Image>() {
            @Override
            protected Image call() throws Exception {
                return new Image(user.getAvatar());
            }
        };
        imageTask.setOnSucceeded(event -> {
            avatarImageView.setImage(imageTask.getValue());
        });
        new Thread(imageTask).start();
    }

    private String formatRelativeTime(Date date) {
        Date now = Calendar.getInstance().getTime();
        long inSeconds = TimeUnit.MILLISECONDS.toSeconds(Math.abs(now.getTime() - date.getTime()));
        if (inSeconds < 60) {
            return "a few seconds ago";
        } else if (inSeconds < 3600) {
            long minutes = TimeUnit.SECONDS.toMinutes(inSeconds);
            return minutes + " minutes ago";
        } else if (inSeconds < 86400) {
            long hours = TimeUnit.SECONDS.toHours(inSeconds);
            return hours + " hours ago";
        } else if (inSeconds < 604800) {
            long days = TimeUnit.SECONDS.toDays(inSeconds);
            return days + " days ago";
        } else if (inSeconds < 2592000) {
            long weeks = TimeUnit.SECONDS.toDays(inSeconds) / 7;
            return weeks + " weeks ago";
        } else if (inSeconds < 31536000) {
            long months = TimeUnit.SECONDS.toDays(inSeconds) / 30;
            return months + " months ago";
        } else {
            long years = TimeUnit.SECONDS.toDays(inSeconds) / 365;
            return years + " years ago";
        }
    }

}

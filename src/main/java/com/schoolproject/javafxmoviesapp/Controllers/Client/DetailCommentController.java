package com.schoolproject.javafxmoviesapp.Controllers.Client;

import com.schoolproject.javafxmoviesapp.Entity.Comment;
import com.schoolproject.javafxmoviesapp.Entity.User;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


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
        timeCommentedLabel.setText(comment.getDate().toString());
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

}

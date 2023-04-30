package com.schoolproject.javafxmoviesapp.Controllers.Client;

import com.schoolproject.javafxmoviesapp.DAO.Concrete.CommentDAOImpl;
import com.schoolproject.javafxmoviesapp.DAO.Concrete.UserDAOImpl;
import com.schoolproject.javafxmoviesapp.Entity.Comment;
import com.schoolproject.javafxmoviesapp.Entity.User;
import com.schoolproject.javafxmoviesapp.Utils.AppSessionUtil;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class CommentsController implements Initializable {
    @FXML
    private Label titleLabel;

    @FXML
    private TextArea commentTextArea;

    @FXML
    private VBox detailCommentsVBox;

    private IntegerProperty filmId = new SimpleIntegerProperty();

    private int totalComment = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        filmId.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                try {
                    // load comments
                    List<Comment> comments = CommentDAOImpl.getInstance().selectByCondition("WHERE `filmId`=" + filmId.get() + " ORDER BY `date` DESC;");
                    for (Comment comment : comments) {
                        User user = UserDAOImpl.getInstance().findById(comment.getUserId());
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/DetailComment.fxml"));
                        HBox detailCommentHBox = fxmlLoader.load();
                        DetailCommentController controller = fxmlLoader.getController();
                        controller.setData(comment, user);
                        detailCommentsVBox.getChildren().add(detailCommentHBox);
                    }
                    // load total comment
                    totalComment = comments.size();
                    titleLabel.setText("COMMENTS (" + totalComment + ")");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    @FXML
    void handleSendComment(ActionEvent event) throws IOException {
        if(AppSessionUtil.getInstance().getUser() == null){
            Alert alertWarning = new Alert(Alert.AlertType.WARNING);
            alertWarning.setContentText("Please login to comment!");
            alertWarning.showAndWait();
            return;
        }

        String commentContent = commentTextArea.getText();
        User user = AppSessionUtil.getInstance().getUser();
        Date now = Calendar.getInstance().getTime();
        Comment comment = new Comment(commentContent, now, user.getId(), filmId.get());
        CommentDAOImpl.getInstance().insert(comment);

        // add new comment to ui
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/DetailComment.fxml"));
        HBox detailCommentHBox = fxmlLoader.load();
        DetailCommentController controller = fxmlLoader.getController();
        controller.setData(comment, user);
        detailCommentsVBox.getChildren().add(0, detailCommentHBox);

        // clear commentTextArea
        commentTextArea.clear();

        // increase total comment
        totalComment++;
        titleLabel.setText("COMMENTS (" + totalComment + ")");
    }


    @FXML
    void handleReloadComments(ActionEvent event) throws IOException {
        // clear comments
        detailCommentsVBox.getChildren().clear();
        // load comments
        List<Comment> comments = CommentDAOImpl.getInstance().selectByCondition("WHERE `filmId`=" + filmId.get() + " ORDER BY `date` DESC;");
        for (Comment comment : comments) {
            User user = UserDAOImpl.getInstance().findById(comment.getUserId());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Client/DetailComment.fxml"));
            HBox detailCommentHBox = fxmlLoader.load();
            DetailCommentController controller = fxmlLoader.getController();
            controller.setData(comment, user);
            detailCommentsVBox.getChildren().add(detailCommentHBox);
        }
        // load total comment
        totalComment = comments.size();
        titleLabel.setText("COMMENTS (" + totalComment + ")");
    }

    public void setFilmId(int filmId) {
        this.filmId.setValue(filmId);
    }

}

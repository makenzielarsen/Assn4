package cs2410.assn4;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Optional;

/**
 *
 */
public class MultiImageView extends Application {

    private ImageController imageController = new ImageController();

    Button nextButton = new Button("Next");
    Button previousButton = new Button("Previous");
    Button deleteButton = new Button("Delete");
    ImageView imageView = new ImageView();
    Text imageTitle = new Text();

    @Override
    public void init(){
        imageController.loadFromFile();
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane rootPane = new BorderPane();

        HBox imagePane = new HBox();
        imageTitle.setText(imageController.getCurrentImage().getTitle());

        imageView = new javafx.scene.image.ImageView();

        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setFitHeight(300);
        imageView.setFitWidth(300);

        imagePane.getChildren().add(imageView);

        Pane bottomPane = new Pane();
        previousButton.setOnAction(e -> pressedPrevious());
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> pressedAdd());
        deleteButton.setOnAction(e -> pressedDelete());
        nextButton.setOnAction(e -> pressedNext());

        HBox buttons = new HBox(previousButton, deleteButton, addButton, nextButton);
        buttons.setSpacing(5);

        bottomPane.getChildren().add(buttons);
        bottomPane.setMinWidth(500);
        bottomPane.setMinHeight(500);

        rootPane.setTop(imageTitle);

        rootPane.setCenter(imagePane);

        rootPane.setBottom(bottomPane);
        Scene scene1 = new Scene(rootPane);

        primaryStage.setScene(scene1);
        primaryStage.setTitle("Image Viewer");
        updateCurrentImage();
        primaryStage.show();
    }

    public void pressedPrevious() {
        imageController.previousImage();
        updateCurrentImage();
    }


    public void pressedAdd() {
        TextInputDialog getUrl = new TextInputDialog();
        getUrl.setContentText("Enter an image URL:");
        Optional<String> urlResult = getUrl.showAndWait();
        TextInputDialog getTitle = new TextInputDialog();
        getTitle.setContentText("Enter an image title:");
        Optional<String> titleResult = getTitle.showAndWait();
        if (urlResult.isPresent() && titleResult.isPresent()) {
            imageController.insertNewImage(urlResult.get(), titleResult.get());
        }

        updateCurrentImage();
    }

    public void pressedDelete() {
        imageController.deleteCurrentImage();
        updateCurrentImage();
    }

    public void pressedNext() {
        imageController.nextImage();
        updateCurrentImage();
    }

    private void updateCurrentImage() {
        if (imageController.hasCurrentImage()) {
            ImageModel model = imageController.getCurrentImage();
            Image image = new Image(model.getUrl());
            imageView.setImage(image);
            imageTitle.setText(model.getTitle());
        } else {
            imageView.setImage(null);
            imageTitle.setText("");
        }
        previousButton.setDisable(!imageController.hasPreviousImage());
        nextButton.setDisable(!imageController.hasNextImage());
        deleteButton.setDisable(!imageController.hasCurrentImage());
    }

    @Override
    public void stop(){
        imageController.saveToFile();
    }
}

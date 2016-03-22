package main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.time.LocalTime;

/**
 * Created by akichi on 16/03/15.
 */
public class Main extends Application {

    public static Label label;
    public static Label ti;
    public static int minute = 25;
    public static int second = 0;
    public static LocalTime local;
    public static int times = 0;
    public static boolean mode = false;
    //FALSE = 25 TRUE = 5


    public static void main(String[] args){
        Application.launch();
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("ポモドーロテクニック");
        stage.setWidth(300);
        stage.setHeight(300);

        label = new Label();
        label.setText(minute + ":" + String.format("%1$02d", second));
        label.setFont(new Font(50));
        label.setTextFill(Color.BLACK);
        Button begin = new Button("スタート");
        ti = new Label();
        ti.setText("現在"+times+"ポモドーロ");
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10,10,10,10));
        root.setSpacing(10);
        root.getChildren().addAll(label, begin, ti);

        Timeline timer = new Timeline(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                if(minute == 0 && second == 0 && !mode){
                    java.awt.Toolkit.getDefaultToolkit().beep();
                    label.setTextFill(Color.YELLOWGREEN);
                    minute = 5;
                    mode = true;
                    label.setText(minute + ":" + String.format("%1$02d", second));
                }else if(minute == 0 && second == 0 && mode) {
                    label.setTextFill(Color.BLACK);
                    minute = 25;
                    times++;
                    mode = false;
                    label.setText(minute + ":" + String.format("%1$02d", second));
                    ti.setText("現在"+times+"ポモドーロ");
                }else if(second == 0){
                    minute--;
                    second = 59;
                    label.setText(minute + ":" + String.format("%1$02d", second));
                }else{
                    second--;
                    label.setText(minute + ":" + String.format("%1$02d", second));
                }

            }
        }));

        begin.setOnAction(event -> {
            for(int i = 0;i < 10;i++) {
                java.awt.Toolkit.getDefaultToolkit().beep();
            }
            timer.setCycleCount(Timeline.INDEFINITE);
            timer.play();
        });

        stage.setScene(new Scene(root));
        stage.show();

    }
}
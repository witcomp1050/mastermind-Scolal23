package mastermind;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;

import static javafx.scene.layout.AnchorPane.setLeftAnchor;
import static javafx.scene.layout.AnchorPane.setTopAnchor;

public class Graphics {

    public int[] nums;
    int index;
    int gessNum;
    Game game;
    int[] code;

    Graphics() {
        nums = new int[] {0, 0,0,0};
        index = 13;
        game = new Game();
        code = game.generateSecretCode();
        gessNum = 1;
    }

    public void gameScreen() {
        //Setting up the Stage and the Scene
        Stage st = new Stage();
        st.setTitle("Mastermind");
        AnchorPane mastermindGame = new AnchorPane();
        Scene s = new Scene(mastermindGame, 1000, 650);
        HBox h = new HBox();
        h.setSpacing(30.0);

        //Checking for an event of a Mouse Click on the circle sending the code to the next color method
        for (int i=0; i < 4; i++) {
            int dummy = i;
            Circle c = new Circle(20.0);
            h.getChildren().add(c);
            c.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    nextColor(c, dummy);
                }
            });
        }
        //making sure everything is in the right place
        setTopAnchor(h, 30.0);
        setLeftAnchor(h, 270.0);

        //Checking for the Action Event of the click of the button
        Button submit = new Button("Submit");
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (index>3) {//seeing if this is the last guess and if not gose to gessCircle method
                    gessCircles(mastermindGame, st);
                }
                else {//if it is the end of the game it will go to the end game method
                    endGame(false, st);
                }
            }
        });

        submit.setPrefHeight(50.0);
        submit.setPrefWidth(120.0);
        setTopAnchor(submit, 25.0);
        setLeftAnchor(submit, 560.0);

        mastermindGame.getChildren().addAll(h, submit);
        st.setScene(s);
        st.show();
    }

    public void nextColor(Circle c, int index) {
        if (nums[index]==5) { //checking if this is the last color so a nullpointer isn't thrown, and so the colors can
            //rotate through before going into change color
            nums[index]=0;
        }
        else {
            nums[index]++;
        }
        changeColor(c, index);
    }

    public void changeColor(Shape c, int index)  {
        switch (nums[index]) {
            case 0: {
                c.setFill(Color.BLUE); break;
            }
            case 1: {
                c.setFill(Color.GREEN); break;
            }
            case 2: {
                c.setFill(Color.ORANGE); break;
            }
            case 3: {
                c.setFill(Color.PURPLE); break;
            }
            case 4: {
                c.setFill(Color.RED); break;
            }
            case 5: {
                c.setFill(Color.YELLOW); break;
            }
            default:{
                throw new IllegalArgumentException("Unknown Color");
            }
        }
    }

    public void gessCircles(AnchorPane a, Stage stage) {
        //When the submit buton is pressed it will come down here and determin what color rectangles are going to be
        // output and checking if the player got the code right
        HBox gess = new HBox();
        gess.setSpacing(40.0);
        gessNum++;

        for (int i=0; i<4; i++) {
            Circle c = new Circle(20.0);
            changeColor(c, i);
            gess.getChildren().add(c);
        }

        int[] feedback = game.getDots(code, nums);

        if (feedback[0]==4) {
            endGame(true, stage);
        }

        for (int i=0; i < 4; i++) {
            Rectangle r = new Rectangle(30.0, 30.0);
            gess.getChildren().add(r);
            if (feedback[0] > 0) {
                r.setFill(Color.BLACK);
                feedback[0]--;
            }
            else if (feedback[1] > 0) {
                r.setFill(Color.WHITE);
                feedback[1]--;
            }
            else {
                r.setFill(Color.PINK);
            }
        }

        if (gessNum!=11) {
            setLeftAnchor(gess, 220.0);
        }
        else {
            setLeftAnchor(gess, 214.0);
        }

        index--;
        setTopAnchor(gess, index * 50.0);
        a.getChildren().add(gess);
    }

    public void endGame(boolean won, Stage s) {//endgame program saying if you won or lost
        Stage stage = new Stage();
        AnchorPane gameOver = new AnchorPane();
        Scene scene = new Scene(gameOver, 300, 100);

        Label message = new Label();

        if (won) {
            stage.setTitle("You Won!");
            message.setText("Congrats! You Won!");
        }
        else {
            stage.setTitle("You Lost!");
            message.setText("Sorry! You lose!\n");
        }
            stage.setScene(scene);
            stage.show();
    }
}
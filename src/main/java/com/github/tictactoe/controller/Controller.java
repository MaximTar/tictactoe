package com.github.tictactoe.controller;

import com.github.tictactoe.view.GameBox;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

/**
 * Created by maxtar.
 */
@SuppressWarnings("unused")
public class Controller {

    private Pane pane = new Pane();
    private Timeline timeline = new Timeline();

    private double lineWidth = 5;
    private Color lineColor = Color.RED;

    public void drawLine(int startX, int startY, int endX, int endY) {
        Line line = new Line();

        line.setStartX(startX + lineWidth / 2);
        line.setStartY(startY + lineWidth / 2);
        line.setEndX(startX);
        line.setEndY(startY);

        line.setStrokeWidth(lineWidth);
        line.setStroke(lineColor);

        pane.getChildren().add(line);

        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100),
                new KeyValue(line.endXProperty(), endX),
                new KeyValue(line.endYProperty(), endY)));
        timeline.play();
    }

    public Parent createContent() {
        pane.getChildren().add(new GameBox(this));
        return pane;
    }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public double getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    public Color getLineColor() {
        return lineColor;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

}

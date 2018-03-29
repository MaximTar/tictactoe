package com.github.tictactoe.view;

import com.github.tictactoe.controller.Controller;
import com.github.tictactoe.model.Game;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

/**
 * Created by maxtar.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class GameBox extends Pane {

    private static final int DEFAULT_SIZE = 3;
    private static int size = DEFAULT_SIZE;
    private static int tileSpacing = 5;
    private final Tile[][] tiles;
    private final Game game;
    private Timeline timeline = new Timeline();
    private double lineWidth = 5;
    private Color lineColor = Color.RED;

    @SuppressWarnings("UnnecessaryLocalVariable")
    public GameBox() {

        game = new Game(this);
        Controller controller = new Controller(this);

        tiles = new Tile[size][size];
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(tileSpacing));
        vBox.setSpacing(tileSpacing);
        for (int i = 0; i < size; i++) {
            Tile[] row = new Tile[size];
            HBox hBox = new HBox(tileSpacing);
            for (int j = 0; j < size; j++) {
                Tile tile = new Tile();
                tile.setOnMouseClicked(event -> controller.onTileClicked(tile));
                hBox.getChildren().add(tile);
                row[j] = tile;
            }
            vBox.getChildren().add(hBox);
            tiles[i] = row;
        }
        getChildren().add(vBox);
    }

    public static int getSize() {
        return size;
    }

    public static void setSize(int size) {
        if (size < DEFAULT_SIZE) {
            // todo say to user
            GameBox.size = DEFAULT_SIZE;
        } else {
            GameBox.size = size;
        }
    }

    public static int getTileSpacing() {
        return tileSpacing;
    }

    public static void setTileSpacing(int tileSpacing) {
        GameBox.tileSpacing = tileSpacing;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public Game getGame() {
        return game;
    }

    public void drawLine(int startX, int startY, int endX, int endY) {
        Line line = new Line();

        line.setStartX(startX + lineWidth / 2);
        line.setStartY(startY + lineWidth / 2);
        line.setEndX(startX);
        line.setEndY(startY);

        line.setStrokeWidth(lineWidth);
        line.setStroke(lineColor);

        getChildren().add(line);

        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100),
                new KeyValue(line.endXProperty(), endX),
                new KeyValue(line.endYProperty(), endY)));
        timeline.play();
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

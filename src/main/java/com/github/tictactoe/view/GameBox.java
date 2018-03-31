package com.github.tictactoe.view;

import com.github.tictactoe.controller.Controller;
import com.github.tictactoe.model.Game;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
    private static int MENU_BAR_HEIGHT = 30;
    private static int tileSpacing = 5;
    private static int lineWidth = 5;
    private static Color lineColor = Color.RED;
    private final Tile[][] tiles;
    private final Game game;
    private Timeline timeline = new Timeline();
    private MenuBar menuBar;

    @SuppressWarnings("UnnecessaryLocalVariable")
    public GameBox() {

        game = new Game(this, Controller.isFirstIsCross());
        Controller controller = new Controller(this);
        tiles = new Tile[size][size];
        VBox vBox = new VBox();
        vBox.getChildren().add(createMenuBar(controller));

        vBox.setPadding(new Insets(0, tileSpacing, tileSpacing, tileSpacing));
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

        if (game.getLineType() == Game.LineType.ROW) {
            line.setStartX(startX + (double) lineWidth / 2);
            line.setStartY(startY + (double) lineWidth / 2 + MENU_BAR_HEIGHT);
            line.setEndX(startX + (double) lineWidth / 2);
            line.setEndY(startY + (double) lineWidth / 2 + MENU_BAR_HEIGHT);

            line.setStrokeWidth(lineWidth);
            line.setStroke(lineColor);

            getChildren().add(line);

            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100),
                    new KeyValue(line.endXProperty(), endX),
                    new KeyValue(line.endYProperty(), endY + (double) lineWidth / 2 + MENU_BAR_HEIGHT)));
        } else if (game.getLineType() == Game.LineType.COLUMN) {
            line.setStartX(startX + (double) lineWidth / 2);
            line.setStartY(startY + (double) lineWidth / 2 + MENU_BAR_HEIGHT);
            line.setEndX(startX + (double) lineWidth / 2);
            line.setEndY(startY + (double) lineWidth / 2 + MENU_BAR_HEIGHT);

            line.setStrokeWidth(lineWidth);
            line.setStroke(lineColor);

            getChildren().add(line);

            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100),
                    new KeyValue(line.endXProperty(), endX + (double) lineWidth / 2),
                    new KeyValue(line.endYProperty(), endY + MENU_BAR_HEIGHT)));
        } else {
            line.setStartX(startX + (double) lineWidth / 2);
            line.setStartY(startY + (double) lineWidth / 2 + MENU_BAR_HEIGHT);
            line.setEndX(startX + (double) lineWidth / 2);
            line.setEndY(startY + (double) lineWidth / 2 + MENU_BAR_HEIGHT);

            line.setStrokeWidth(lineWidth);
            line.setStroke(lineColor);

            getChildren().add(line);

            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100),
                    new KeyValue(line.endXProperty(), endX),
                    new KeyValue(line.endYProperty(), endY + MENU_BAR_HEIGHT)));
        }
        timeline.play();
    }

    private MenuBar createMenuBar(Controller controller) {
        menuBar = new MenuBar();
        Menu menuParameters = new Menu("Настройки");
        MenuItem gameParameters = new MenuItem("Настройки игры");
        MenuItem viewParameters = new MenuItem("Настройки внешнего вида");

        gameParameters.setOnAction(event -> controller.onGameParameters());
        viewParameters.setOnAction(event -> controller.onViewParameters());

        menuParameters.getItems().addAll(gameParameters, viewParameters);
        menuBar.getMenus().add(menuParameters);
        menuBar.setMinHeight(MENU_BAR_HEIGHT);
        return menuBar;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public static int getLineWidth() {
        return lineWidth;
    }

    public static void setLineWidth(int lineWidth) {
        GameBox.lineWidth = lineWidth;
    }

    public static Color getLineColor() {
        return lineColor;
    }

    public static void setLineColor(Color lineColor) {
        GameBox.lineColor = lineColor;
    }

    public int getGameBoxWidth() {
        return getSize() * Tile.getSize() + (getSize() + 1) * getTileSpacing() + getSize();
    }

    public int getGameBoxHeight() {
        return getSize() * Tile.getSize() + (getSize() + 1) * getTileSpacing() + getSize() + MENU_BAR_HEIGHT;
    }

    public static int getMenuBarHeight() {
        return MENU_BAR_HEIGHT;
    }

}

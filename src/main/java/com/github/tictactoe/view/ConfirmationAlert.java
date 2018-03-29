package com.github.tictactoe.view;

import com.github.tictactoe.TicTacToe;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.Optional;

/**
 * Created by maxtar.
 */
@SuppressWarnings("WeakerAccess")
public class ConfirmationAlert extends Alert {

    public ConfirmationAlert(String header, Window owner) {
        super(AlertType.CONFIRMATION);
        setTitle("Игра законена");
        setHeaderText(header);
        setContentText("Сыграем еще раз?");
        initOwner(owner);
        initModality(Modality.WINDOW_MODAL);
        setResizable(false);
//        initStyle(StageStyle.UNDECORATED); // style makes alert resizable, don't know why

        ButtonType btnAgain = new ButtonType("Сыграть ещё раз!");
        ButtonType btnCancel = new ButtonType("Закончить");
        getButtonTypes().setAll(btnAgain, btnCancel);
        Optional<ButtonType> result = showAndWait();
        if (result.isPresent()) {
            Stage stage = (Stage) owner;
            if (result.get() == btnAgain) {
                int width = TicTacToe.getWidth();
                int height = TicTacToe.getHeight();
                stage.setScene(new Scene(new GameBox(), width, height));
            } else if (result.get() == btnCancel) {
                close();
                stage.close();
            }
        }
    }
}

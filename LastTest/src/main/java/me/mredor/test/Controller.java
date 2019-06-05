package me.mredor.test;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;


/** Special class with controller for Pairs game */
public class Controller {
    private Button[][] gameboard;
    private Logic logic;
    private int size;
    private int lastX = -1;
    private int lastY = -1;

    /** Creates controller working with given gameboard  */
    public Controller(Button[][] gameboard) {
        this.gameboard = gameboard;
        size = gameboard.length;
        logic = new Logic(size);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                gameboard[i][j].setDisable(false);
                final int ii = i;
                final int jj = j;
                gameboard[i][j].setOnMouseClicked(event -> onClick(ii, jj));
            }
        }
    }

    private synchronized void onClick(int x, int y) {
        gameboard[x][y].setText( String.valueOf(logic.getCell(x, y)));
        if (lastX == -1) {
            lastX = x;
            lastY = y;
            return;
        }
        var current = gameboard[x][y];
        var last = gameboard[lastX][lastY];
        if (current == last) {
            current.setText("");
            lastX = -1;
            lastY = -1;
            return;
        }
        if (logic.open(x, y, lastX, lastY)) {
            current.setDisable(true);
            last.setDisable(true);
            if (logic.checkWin()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("WIN");
                alert.showAndWait();
            }
        } else {
            /*for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    gameboard[i][j].setDisable(true);
                }
            }*/
            current.setDisable(true);
            last.setDisable(true);
            var hide = new Thread(() -> {
                synchronized (this) {
                    try {
                        wait(1000);
                    } catch (InterruptedException ignored) {

                    }
                    Platform.runLater(() -> {
                        current.setText("");
                        current.setDisable(false);
                        last.setText("");
                        last.setDisable(false);
                        /*for (int i = 0; i < size; i++) {
                            for (int j = 0; j < size; j++) {
                                gameboard[i][j].setDisable(logic.isOpened(i, j));
                            }
                        }*/
                    });
                }
            });
            hide.start();
        }
        lastX = -1;
        lastY = -1;
    }
}

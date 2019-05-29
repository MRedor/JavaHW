package me.mredor.test;

import java.util.Random;

/** Special class with logic of Pairs game */
public class Logic {
    private int[][] answer;
    private int size;
    private int openedNumber = 0;
    private boolean[][] opened;


    /** Creates board of given size. Fill it with numbers in range 0..size^2/2 -- 2 copies of each number */
    public Logic(int size) {
        this.size = size;
        answer = new int[size][size];
        opened = new boolean[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                answer[i][j] = -1;
                opened[i][j] = false;
            }
        }
        var random = new Random();
        int maxNumber = size*size/2;
        for (int i = 0; i < maxNumber; i++) {
            for (int j = 0; j < 2; j++) {
                var x = random.nextInt(size);
                var y = random.nextInt(size);
                while (answer[x][y] != -1) {
                    x = random.nextInt(size);
                    y = random.nextInt(size);
                }
                answer[x][y] = i;
            }
        }
    }

    /** Gets the number from the cell (x, y) */
    public int getCell(int x, int y) {
        return answer[x][y];
    }

    private void updateOpened(int number) {
        openedNumber += number;
    }

    /** Checks if the game ended */
    public boolean checkWin() {
        return (openedNumber == size * size);
    }

    /** Marks 2 cells as opened
     * @return 'true' if given cells have the same number and 'false' otherwise
     * */
    public boolean open(int firstX, int firstY, int secondX, int secondY) {
        if (answer[firstX][firstY] == answer[secondX][secondY]) {
            updateOpened(2);
            opened[firstX][firstY] = true;
            opened[secondX][secondY] = true;
            return true;
        } else {
            return false;
        }
    }

    /** Checks if the cell (x, y) is opened */
    public boolean isOpened(int x, int y) {
        return opened[x][y];
    }

    /** Gets size of the board*/
    public int getSize() {
        return size;
    }
}

package model;

public class Board {
    public final int ROW_AMOUNT = 6;
    public final int COLUMN_AMOUNT = 7;
    private char[][] boardArray;
    public final char RED_SIGNIFIER = 'r';
    public final char YELLOW_SIGNIFIER = 'y';

    public Board() {
        boardArray = new char[ROW_AMOUNT][COLUMN_AMOUNT];
        for (int i = 0; i < ROW_AMOUNT; i++) {
            for (int a = 0; a < COLUMN_AMOUNT; a++) {
                boardArray[i][a] = 'O';
            }
        }
    }

    public char[][] getBoardArray() {
        return boardArray;
    }

    public void setBoardArray(char[][] boardArray) {
        this.boardArray = boardArray;
    }

    @Override
    public String toString() {
        String boardStr = "";
        for (int i = 0; i < ROW_AMOUNT; i++) {
            for (int a = 0; a < COLUMN_AMOUNT; a++) {
                boardStr += boardArray[i][a] + " ";
            }
            boardStr += "\n";
        }
        return boardStr;
    }

    public void playTurn(Player player, int playerDecision){
        int decidedColumn = playerDecision - 1;
        int currentRow = 0;
        if(boardArray[currentRow][decidedColumn] != 'O') throw new IllegalArgumentException();
        try {
            while(boardArray[currentRow + 1][decidedColumn] == 'O'){
                currentRow++;
            }
        } catch (IndexOutOfBoundsException ex) {}
        boardArray[currentRow][decidedColumn] = player.getSignifier();
    }

    public boolean checkForPlayerWin(Player player){
        boolean win = false;
        for (int i = 0; i < ROW_AMOUNT; i++) {
            for (int a = 0; a < COLUMN_AMOUNT; a++) {
                try {
                    if (boardArray[i][a] == player.getSignifier() &&
                            boardArray[i + 1][a] == player.getSignifier() &&
                            boardArray[i + 2][a] == player.getSignifier() &&
                            boardArray[i + 3][a] == player.getSignifier()) {
                        boardArray[i][a] = Character.toUpperCase(player.getSignifier());
                        boardArray[i + 1][a] = Character.toUpperCase(player.getSignifier());
                        boardArray[i + 2][a] = Character.toUpperCase(player.getSignifier());
                        boardArray[i + 3][a] = Character.toUpperCase(player.getSignifier());
                        win = true;
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {}
                try {
                    if (boardArray[i][a] == player.getSignifier() &&
                            boardArray[i][a + 1] == player.getSignifier() &&
                            boardArray[i][a + 2] == player.getSignifier() &&
                            boardArray[i][a + 3] == player.getSignifier()) {
                        boardArray[i][a] = Character.toUpperCase(player.getSignifier());
                        boardArray[i][a + 1] = Character.toUpperCase(player.getSignifier());
                        boardArray[i][a + 2] = Character.toUpperCase(player.getSignifier());
                        boardArray[i][a + 3] = Character.toUpperCase(player.getSignifier());
                        win = true;
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {}
                try {
                    if (boardArray[i][a] == player.getSignifier() &&
                            boardArray[i + 1][a + 1] == player.getSignifier() &&
                            boardArray[i + 2][a + 2] == player.getSignifier() &&
                            boardArray[i + 3][a + 3] == player.getSignifier()) {
                        boardArray[i][a] = Character.toUpperCase(player.getSignifier());
                        boardArray[i + 1][a + 1] = Character.toUpperCase(player.getSignifier());
                        boardArray[i + 2][a + 2] = Character.toUpperCase(player.getSignifier());
                        boardArray[i + 3][a + 3] = Character.toUpperCase(player.getSignifier());
                        win = true;
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {}
                try {
                    if (boardArray[i][a] == player.getSignifier() &&
                            boardArray[i - 1][a + 1] == player.getSignifier() &&
                            boardArray[i - 2][a + 2] == player.getSignifier() &&
                            boardArray[i - 3][a + 3] == player.getSignifier()) {
                        boardArray[i][a] = Character.toUpperCase(player.getSignifier());
                        boardArray[i - 1][a + 1] = Character.toUpperCase(player.getSignifier());
                        boardArray[i - 2][a + 2] = Character.toUpperCase(player.getSignifier());
                        boardArray[i - 3][a + 3] = Character.toUpperCase(player.getSignifier());
                        win = true;
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {}
            }
        }
        return win;
    }

    public boolean checkForTie(){
        int availableSpace = ROW_AMOUNT * COLUMN_AMOUNT;
        for (int i = 0; i < ROW_AMOUNT; i++) {
            for (int a = 0; a < COLUMN_AMOUNT; a++) {
                if (boardArray[i][a] != 'O') availableSpace--;
            }
        }
        if (availableSpace <= 0) return true;
        else return false;
    }
}

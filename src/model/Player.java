package model;

public abstract class Player {
    private String name;
    private Board currentBoard;
    private char signifier;
    private boolean winner;

    public String getName() {
        return name;
    }

    public Board getCurrentBoard() {
        return currentBoard;
    }

    public char getSignifier() {
        return signifier;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public Player(String name, Board currentBoard, char signifier) {
        this.name = name;
        this.currentBoard = currentBoard;
        this.signifier = signifier;
    }

    public abstract int getDecision() throws IllegalArgumentException;

}

package model;

public class Natural extends Player {

    public Natural(String name, Board currentBoard, char signifier) {
        super(name, currentBoard, signifier);
    }

    public int getDecision() throws IllegalArgumentException {
        throw new IllegalArgumentException();
    }

}

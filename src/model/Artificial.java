package model;

import java.util.ArrayList;
import java.util.Random;

public class Artificial extends Player {
    private Board simulatedBoard;

    public Artificial(String name, Board currentBoard, char signifier) {
        super(name, currentBoard, signifier);
    }

    public int getDecision() {
        int win = getSimulatedGameWinningDecisions();
        int plan = getSimulatedPlannedMove();
        if (win != -1) return win;
        else if (plan != -1) return plan;
        else return getSimulatedPossibleDecision();
    }

    private int getSimulatedGameWinningDecisions() {
        simulatedBoard = new Board();
        int bestChoice = -1;
        for (int i = 1; i < getCurrentBoard().COLUMN_AMOUNT; i++) {
            simulatedBoard.setBoardArray(simulatedArray());
            try {
                simulatedBoard.playTurn(this, i);
            } catch (IllegalArgumentException ex) {}
            if (simulatedBoard.checkForPlayerWin(this)) bestChoice = i;
        }
        char opposing;
        if (getSignifier() == getCurrentBoard().RED_SIGNIFIER) opposing = getCurrentBoard().YELLOW_SIGNIFIER;
        else opposing = getCurrentBoard().RED_SIGNIFIER;
        if (bestChoice == -1) {
            Player simulatedPlayer = new Natural("", getCurrentBoard(), opposing);
            for (int i = 1; i < getCurrentBoard().COLUMN_AMOUNT; i++) {
                simulatedBoard.setBoardArray(simulatedArray());
                try {
                    simulatedBoard.playTurn(simulatedPlayer, i);
                } catch (IllegalArgumentException ex) {}
                if (simulatedBoard.checkForPlayerWin(simulatedPlayer)) bestChoice = i;
            }
        }
        return bestChoice;
    }

    private int getSimulatedPlannedMove() {
        int plannedMove = -1;
        int[] plans = new int[3];
        for (int i = 1; i < getCurrentBoard().COLUMN_AMOUNT; i++) {
            try {
                simulatedBoard.setBoardArray(simulatedArray());
                simulatedBoard.playTurn(this, i);
                simulatedBoard.playTurn(this, i + 1);
                if (simulatedBoard.checkForPlayerWin(this)) plans[0] = i;
            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ex) {}
            try {
                simulatedBoard.setBoardArray(simulatedArray());
                simulatedBoard.playTurn(this, i);
                simulatedBoard.playTurn(this, i - 1);
                if (simulatedBoard.checkForPlayerWin(this)) plans[1] = i;
            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ex) {}
            try {
                simulatedBoard.setBoardArray(simulatedArray());
                simulatedBoard.playTurn(this, i);
                simulatedBoard.playTurn(this, i);
                if (simulatedBoard.checkForPlayerWin(this)) plans[2] = i;
            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ex) {}
        }
        ArrayList<Integer> possiblePlans = new ArrayList<>();
        for (int i : plans){
            if (i != 0) possiblePlans.add(i);
        }
        if (possiblePlans.size() > 1) plannedMove = possiblePlans.get(randomNum(0, possiblePlans.size() - 1));
        return plannedMove;
    }

    private int getSimulatedPossibleDecision() {
        simulatedBoard = new Board();
        simulatedBoard.setBoardArray(simulatedArray());
        int acceptableChoice = 0;
        boolean replay;
        do {
            try {
                acceptableChoice = randomNum(1, getCurrentBoard().COLUMN_AMOUNT);
                simulatedBoard.playTurn(this, acceptableChoice);
                replay = false;
            } catch (IllegalArgumentException ex) {
                replay = true;
            }
        } while (replay);
        return acceptableChoice;
    }

    private char[][] simulatedArray() {
        char[][] simulation = new char[getCurrentBoard().ROW_AMOUNT][getCurrentBoard().COLUMN_AMOUNT];
        for (int i = 0; i < getCurrentBoard().ROW_AMOUNT; i++) {
            for (int a = 0; a < getCurrentBoard().COLUMN_AMOUNT; a++) {
                simulation[i][a] = getCurrentBoard().getBoardArray()[i][a];
            }
        }
        return simulation;
    }

    private int randomNum(int min, int max) {
        if (min >= max) throw new IllegalStateException("min can never be higher than max");
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

}

package controller;

import model.Artificial;
import model.Board;
import model.Natural;
import model.Player;
import view.Display;

import java.util.Random;

public class ConnectFour {
    private Display display = new Display();
    private Player[] players = new Player[2];
    private static final String DEFAULT_PLAYER_1_NAME = "Player 1";
    private static final String DEFAULT_PLAYER_2_NAME = "Player 2";
    private static final char FIRST_SIGNIFIER = 'y';
    private Board board;
    private MainMenu menu[] = MainMenu.values();

    public static void main(String[] args) {
        new ConnectFour().connectFour();
    }

    private void connectFour(){
        int input;
        do{
            display.sendMessage(getEnumMainMenu());
            input = display.askForUserInputInt();
            switch (input){
                case 1:
                    board = new Board();
                    NaturalVsNatural();
                    game();
                    break;
                case 2:
                    board = new Board();
                    NaturalVsArtificial();
                    game();
                    break;
                case 3:
                    board = new Board();
                    ArtificialVsArtificial();
                    game();
                    break;
                case 4:
                    display.sendMessage("Goodbye.");
                    break;
                default:
                    display.sendMessage("Please try again.");
                    break;
            }
        } while (input != 4);
    }

    private String getEnumMainMenu(){
        String output = "";
        for (int i = 0; i < menu.length; i++) {
            output += String.format("%d: %s\n", i + 1, menu[i].getName());
        }
        return output;
    }

    private String getMainMenu(){
        return "1: Human vs Human\n" +
                "2: Human vs Computer\n" +
                "3: Computer vs Computer\n" +
                "4: Exit";
    }

    private String decideName(int player, String defaultName){
        display.sendMessage(String.format("Do you wish to name player %d? Yes, or No.", player));
        String input = display.askForUserInputStr();
        while (!input.equalsIgnoreCase("Yes") && !input.equalsIgnoreCase("No")){
            display.sendMessage("Please try again.");
            input = display.askForUserInputStr();
        }
        if (input.equalsIgnoreCase("Yes")){
            display.sendMessage(String.format("Please enter a name for player %d", player));
        } else return defaultName;
        return display.askForUserInputStr();
    }

    private void NaturalVsNatural(){
        switch (randomNum(0,1)){
            case 0:
                players[0] = new Natural(decideName(1, DEFAULT_PLAYER_1_NAME), board, board.YELLOW_SIGNIFIER);
                players[1] = new Natural(decideName(2, DEFAULT_PLAYER_2_NAME), board, board.RED_SIGNIFIER);
                break;
            case 1:
                players[0] = new Natural(decideName(1, DEFAULT_PLAYER_1_NAME), board, board.RED_SIGNIFIER);
                players[1] = new Natural(decideName(2, DEFAULT_PLAYER_2_NAME), board, board.YELLOW_SIGNIFIER);
        }
    }

    private void NaturalVsArtificial(){
        switch (randomNum(0,1)){
            case 0:
                players[0] = new Natural(decideName(1, DEFAULT_PLAYER_1_NAME), board, board.YELLOW_SIGNIFIER);
                players[1] = new Artificial(decideName(2, DEFAULT_PLAYER_2_NAME), board, board.RED_SIGNIFIER);
                break;
            case 1:
                players[0] = new Natural(decideName(1, DEFAULT_PLAYER_1_NAME), board, board.RED_SIGNIFIER);
                players[1] = new Artificial(decideName(2, DEFAULT_PLAYER_2_NAME), board, board.YELLOW_SIGNIFIER);
        }
    }

    private void ArtificialVsArtificial(){
        switch (randomNum(0,1)){
            case 0:
                players[0] = new Artificial(decideName(1, DEFAULT_PLAYER_1_NAME), board, board.YELLOW_SIGNIFIER);
                players[1] = new Artificial(decideName(2, DEFAULT_PLAYER_2_NAME), board, board.RED_SIGNIFIER);
                break;
            case 1:
                players[0] = new Artificial(decideName(1, DEFAULT_PLAYER_1_NAME), board, board.RED_SIGNIFIER);
                players[1] = new Artificial(decideName(2, DEFAULT_PLAYER_2_NAME), board, board.YELLOW_SIGNIFIER);
        }
    }

    private void game(){
        int currentPlayer;
        if (players[0].getSignifier() == FIRST_SIGNIFIER) {
            currentPlayer = 0;
        } else {
            currentPlayer = 1;
        }
        display.sendMessage(String.format("%s's piece is \"%c\"\n%s's piece is \"%c\"", players[0].getName(),
                players[0].getSignifier(), players[1].getName(), players[1].getSignifier()));
        while (!players[0].isWinner() && !players[1].isWinner() && !board.checkForTie()){
            if (currentPlayer == 0) {
                playPlayerTurn(0);
                currentPlayer = 1;
            }
            else {
                playPlayerTurn(1);
                currentPlayer = 0;
            }
            decideWhoWon();
        }
        display.sendMessage(board.toString());
        declareOutcome();
    }

    private void decideWhoWon(){
        if (board.checkForPlayerWin(players[0])) players[0].setWinner(true);
        else if (board.checkForPlayerWin(players[1])) players[1].setWinner(true);
    }

    private void playPlayerTurn(int playerNum){
        Player player = players[playerNum];
        boolean replay;
        int input;
        try{
            board.playTurn(player, player.getDecision());
        } catch (IllegalArgumentException exception){
            do{
                display.sendMessage(board.toString());
                display.sendMessage(String.format("Please enter %s's play", player.getName()));
                input = display.askForUserInputInt();
                if (input < 1 || input > board.COLUMN_AMOUNT) replay = true;
                else replay = false;
            } while (replay);
                do{
                    try {
                        board.playTurn(player, input);
                        replay = false;
                    } catch (IllegalArgumentException ex){
                        display.sendMessage("Please try Again.");
                        replay = true;
                        input = display.askForUserInputInt();
                    }
                } while (replay);

        }
    }

    private void declareOutcome(){
        String finalStr;
        if (players[0].isWinner()) finalStr = String.format("%s Wins.", players[0].getName());
        else if (players[1].isWinner()) finalStr = String.format("%s Wins.", players[1].getName());
        else finalStr = "It's a draw.";
        display.sendMessage(String.format("%s\nGame Over.\n", finalStr));
    }

    private int randomNum(int min, int max){
        if(min >= max){
            throw new IllegalStateException("min can never be higher than max");
        }
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}

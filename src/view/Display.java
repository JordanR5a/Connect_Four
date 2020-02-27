package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Display {
    private boolean replay;

    public boolean isReplay() {
        return replay;
    }

    public void setReplay(boolean replay) {
        this.replay = replay;
    }

    public String askForUserInputStr(){
        BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
        String input = null;
        do {
            try {
                input = br.readLine();
                if(input.isEmpty() || input == null){
                    throw new IllegalStateException("User input unacceptable");
                }
                setReplay(false);
            } catch (IOException | IllegalStateException exception) {
                sendMessage("Please try again.");
                setReplay(true);
            }
        } while (isReplay());
        return input;
    }

    public int askForUserInputInt(){
        int input = -1;
        do {
            try {
                input = Integer.parseInt(askForUserInputStr());
                setReplay(false);
            } catch (IllegalStateException exception) {
                sendMessage("Please try again.");
                setReplay(true);
            }
        } while (isReplay());
        return input;
    }

    public int askForUserInputIntWithinRange(int min, int max){
        int input = -1;
        do {
            try {
                input = Integer.parseInt(askForUserInputStr());
                if (input < min || input > max) throw new IllegalStateException();
                return input;
            } catch (IllegalStateException exception) {
                sendMessage("Please try again.");
            }
        } while (true);
    }

    public void sendMessage(String request){
        System.out.println(request);
    }
}

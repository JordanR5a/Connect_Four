package controller;

public enum MainMenu {
    HUMAN_VS_HUMAN ("Human vs Human"),
    HUMAN_VS_COMPUTER ("Human vs Computer"),
    COMPUTER_VS_COMPUTER ("Computer vs Computer"),
    EXIT ("Exit");

    private final String name;

    private MainMenu(String str){
        name = str;
    }

    public String getName() {
        return name;
    }
}

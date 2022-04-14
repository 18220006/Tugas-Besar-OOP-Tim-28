public class Interface {
    public static void loadingGame(){
        try {
            Thread.sleep(1000);
            System.out.println("");
            System.out.println("=========== WELCOME TRAINERS ===========\n");
            Thread.sleep(2000);
            System.out.println("Welcome to Monster Saku");
            Thread.sleep(2000);
            System.out.println("Hello, I'm Professor Ash, Your Monster Saku Master");
            Thread.sleep(2000);
            System.out.println("Please Wait A Few Moments For System To Load The Game");
            Thread.sleep(2000);
            System.out.printf("Loading Game Info");
            for (int i = 0; i < 5; i++){
                Thread.sleep(1000);
                System.out.printf(".");
            }
            System.out.println("");
            Thread.sleep(2000);
            System.out.printf("Game Info Loaded");
            System.out.println("\n");
            Thread.sleep(2000);
            System.out.println(" \\^o^/  ENJOY THE GAME  \\^o^/");
            Thread.sleep(2000);
            System.out.print("\033[H\033[2J");
            System.out.flush(); 
        }
        catch(InterruptedException e) {

        }                                                                     
    }

    public static void newGame(){
        try{
            Thread.sleep(1000);
            System.out.println("============================================================================================================\n");
            System.out.println("    ███    ███  ██████  ███    ██ ███████ ████████ ███████ ██████      ███████  █████  ██   ██ ██    ██ ");
            System.out.println("    ████  ████ ██    ██ ████   ██ ██         ██    ██      ██   ██     ██      ██   ██ ██  ██  ██    ██ ");
            System.out.println("    ██ ████ ██ ██    ██ ██ ██  ██ ███████    ██    █████   ██████      ███████ ███████ █████   ██    ██ ");
            System.out.println("    ██  ██  ██ ██    ██ ██  ██ ██      ██    ██    ██      ██   ██          ██ ██   ██ ██  ██  ██    ██ ");
            System.out.println("    ██      ██  ██████  ██   ████ ███████    ██    ███████ ██   ██     ███████ ██   ██ ██   ██  ██████  ");
            System.out.println("");
            System.out.println("============================================================================================================");
            Thread.sleep(1000);
        }
        catch(InterruptedException e){

        }
    }

    public static void menuDisplay(){
        try{
            Thread.sleep(1000);
            System.out.println("");
            System.out.println(ANSI_CYAN + "Welcome To Monster Saku\n" + ANSI_RESET);
            System.out.println(ANSI_RED + "1" + ANSI_RESET + ". START GAME ");
            System.out.println(ANSI_RED + "2" + ANSI_RESET + ". HOW TO PLAY ");
            System.out.println(ANSI_RED + "3" + ANSI_RESET + ". EXIT GAME \n");
            System.out.println("Select Option");
            System.out.printf("\033[1;33m>\033[0m ");
        }
        catch(InterruptedException e){

        }
    }

    public static void startGame(){
        try{
            Thread.sleep(1000);
            System.out.print("\033[H\033[2J");
            System.out.flush(); 
            System.out.println("=========================================================================================\n");
            System.out.println("    ███████ ████████  █████  ██████  ████████      ██████   █████  ███    ███ ███████ ");
            System.out.println("    ██         ██    ██   ██ ██   ██    ██        ██       ██   ██ ████  ████ ██      ");
            System.out.println("    ███████    ██    ███████ ██████     ██        ██   ███ ███████ ██ ████ ██ █████   ");
            System.out.println("         ██    ██    ██   ██ ██   ██    ██        ██    ██ ██   ██ ██  ██  ██ ██      ");
            System.out.println("    ███████    ██    ██   ██ ██   ██    ██         ██████  ██   ██ ██      ██ ███████ ");
            System.out.println("");
            System.out.println("=========================================================================================");
            System.out.println("\n");
        }
        catch(InterruptedException e){

        }
    }

    public static void roundStart(){
        System.out.println(ANSI_CYAN + "What Do You Want To Do\n" + ANSI_RESET);
        System.out.println(ANSI_RED + "1" + ANSI_RESET + ". MOVE ");
        System.out.println(ANSI_RED + "2" + ANSI_RESET + ". SWITCH ");
        System.out.println(ANSI_RED + "3" + ANSI_RESET + ". VIEW MONSTER INFO ");
        System.out.println(ANSI_RED + "4" + ANSI_RESET + ". VIEW GAME INFO\n");
        System.out.println("Select Option");
        System.out.printf("\033[1;33m>\033[0m ");
    }

    public static void help(){
        System.out.printf(ANSI_RED + "\n> " + ANSI_RESET);
        System.out.println(ANSI_CYAN + "What is Monster Saku ?" + ANSI_RESET);
        System.out.println(" Monster Saku is a 2 player PvP game where each player will fight their 6 Monsters. ");
        System.out.println(" Each Monster are randomly obtained by the system and have a different abbilities and elements.");
        
        System.out.printf(ANSI_RED + "\n> " + ANSI_RESET);
        System.out.println(ANSI_CYAN + "How To Play The Game ?" + ANSI_RESET);
        System.out.println(" 1. Type 'START GAME' in the Main Menu");
        System.out.println(" 2. Enter your name and your friend's name");
        System.out.println("    All players will get 6 Monsters randomly");
        System.out.println(" 3. You will be selected 1 active monster for the first turn");
        System.out.println(" 4. At every turn you can" + ANSI_YELLOW + " Move " + ANSI_RESET + "or" + ANSI_YELLOW + " Switch " + ANSI_RESET + "your monster");
        System.out.println(" 5. Kill all of your enemy's monsters to win the game!!");

        System.out.println("\nType " + ANSI_YELLOW + "BACK" + ANSI_RESET + " to return to Main Menu");
        System.out.printf("> ");
    }

    public static final String ANSI_RESET = "\u001B[1;0m";
    public static final String ANSI_BLACK = "\u001B[1;30m";
    public static final String ANSI_RED = "\u001B[1;31m";
    public static final String ANSI_GREEN = "\u001B[1;32m";
    public static final String ANSI_YELLOW = "\u001B[1;33m";
    public static final String ANSI_BLUE = "\u001B[1;34m";
    public static final String ANSI_PURPLE = "\u001B[1;35m";
    public static final String ANSI_CYAN = "\u001B[1;36m";
    public static final String ANSI_WHITE = "\u001B[1;37m";

    public static void main(String[] args){
        //help();
        //loadingGame();
        newGame();
        //menuDisplay();
    }
}


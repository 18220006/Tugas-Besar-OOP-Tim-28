import util.CSVReader;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.*;

public class Main {
    private static final List<String> CSV_FILE_PATHS = Collections.unmodifiableList(Arrays.asList(
            "configs/monsterpool.csv",
            "configs/movepool.csv",
            "configs/element-type-effectivity-chart.csv"));

    public static List<Move> readMovePool(String file){
        List<Move> moveList = new ArrayList<Move>();

        try {
            CSVReader readerMoves = new CSVReader(new File(Main.class.getResource(file).toURI()), ";");
            readerMoves.setSkipHeader(true);
            List<String[]> listOfMoves = readerMoves.read();

            for(String[] line : listOfMoves){
                int id = Integer.valueOf(line[0]);
                String moveType = line[1];
                String name = line[2];
                ElementType elementType = ElementType.valueOf(line[3]);
                int accuracy = Integer.valueOf(line[4]);
                int priority = Integer.valueOf(line[5]);
                int ammunition = Integer.valueOf(line[6]);
                String target = line[7];

                if (line.length == 9) {
                    int powerBase = Integer.valueOf(line[8]);
                    if (moveType.equals("NORMAL")) {
                        NormalMoves normalMove = new NormalMoves(id,moveType, name, elementType, accuracy,
                                priority, ammunition, target, powerBase);
                        moveList.add(normalMove);
                    }
                    else if (moveType.equals("SPECIAL")) {
                        SpecialMoves specialMove = new SpecialMoves(id,moveType, name, elementType, accuracy,
                                priority, ammunition, target, powerBase);
                        moveList.add(specialMove);
                    }
                }
                else {
                    String statusEffect = line[8];
                    String[] stats = (line[9]).split(",");
                    int[] effectPoint = { 0, 0, 0, 0, 0, 0 };
                    for (int i = 0; i < 6; i++) {
                        effectPoint[i] = Integer.valueOf(stats[i]);
                    }
                    StatusMoves statusMove = new StatusMoves(id,moveType, name, elementType, accuracy,
                            priority, ammunition, target, statusEffect,
                            effectPoint);
                    moveList.add(statusMove);
                }
            }
        }
        catch (Exception e) {
            System.out.println("Failed to Load Moves...");
        }
        return moveList;
    }

    public static List<Monster> readConfig(String file){
        List<Move> movePool = new ArrayList<Move>();
        movePool = readMovePool(CSV_FILE_PATHS.get(1));
        List<Monster> monsterList = new ArrayList<Monster>();

        try{
            CSVReader readerMonsters = new CSVReader(new File(Main.class.getResource(file).toURI()), ";");
            readerMonsters.setSkipHeader(true);
            List<String[]> listOfMonsters = readerMonsters.read();

            for(String[] line : listOfMonsters){
                // Create ID
                int id = Integer.valueOf(line[0]);
                // Create Name
                String name = line[1];
                // Create ElementType
                ArrayList<ElementType> elementTypes = new ArrayList<ElementType>();
                String[] arrOfEltypesTemp = line[2].split(",", 7);
                for(String eltype : arrOfEltypesTemp){
                    if(eltype.equals("FIRE")){
                        elementTypes.add(ElementType.FIRE);
                    }
                    if(eltype.equals("NORMAL")){
                        elementTypes.add(ElementType.NORMAL);
                    }
                    if(eltype.equals("GRASS")){
                        elementTypes.add(ElementType.GRASS);
                    }
                    if(eltype.equals("WATER")){
                        elementTypes.add(ElementType.WATER);
                    }
                }

                // Create Monster Stats
                String[] tempBS = line[3].split(",");
                ArrayList<Double> arrayStats = new ArrayList<Double>();

                for(String baseStats : tempBS) {
                    Double eachStat = Double.parseDouble(baseStats);
                    arrayStats.add(eachStat);
                }
                Stats monStats = new Stats(arrayStats.get(0), arrayStats.get(1), arrayStats.get(2),
                        arrayStats.get(3), arrayStats.get(4), arrayStats.get(5));

                // SET UP MOVE MILIK MONSTER
                String move = line[4];
                String[] arrOfMove = move.split(",");
                List<Move> monsMove = new ArrayList<Move>();
                DefaultMoves defaultMove = new DefaultMoves();
                monsMove.add(defaultMove);
                for(int i = 0; i < arrOfMove.length; i++){
                    Move originMove = movePool.get(Integer.valueOf(arrOfMove[i]) - 1);
                    monsMove.add(originMove);
                }

                // Create Monster Object
                Monster monsterTemp = new Monster(id, name, elementTypes, monStats, monsMove);
                monsterList.add(monsterTemp);
            }
        }
        catch (Exception e) {
            System.out.println("Failed to Load Monster...");
        }
        return monsterList;
    }

    public static void main(String[] args) {
        List<Monster> monsterList = readConfig(CSV_FILE_PATHS.get(0));
        Effectivity listOfEffectivity = new Effectivity();
        try {
            CSVReader readerEffectivity = new CSVReader(new File(Main.class.getResource(CSV_FILE_PATHS.get(2)).toURI()), ";");
            readerEffectivity.setSkipHeader(true);
            List<String[]> linesOfEffectivity = readerEffectivity.read();
            for (String [] line : linesOfEffectivity){
                String source = line[0];
                String target = line[1];
                Double effectivity = Double.parseDouble(line[2]);
                ElementType s = ElementType.NORMAL;
                ElementType t = ElementType.NORMAL;
                switch (source){
                    case ("NORMAL"):
                        s = ElementType.NORMAL;
                        break;
                    case ("FIRE"):
                        s = ElementType.FIRE;
                        break;
                    case ("WATER"):
                        s = ElementType.WATER;
                        break;
                    case ("GRASS"):
                        s = ElementType.GRASS;
                        break;
                }
                switch (target){
                    case ("NORMAL"):
                        t = ElementType.NORMAL;
                        break;
                    case ("FIRE"):
                        t = ElementType.FIRE;
                        break;
                    case ("WATER"):
                        t = ElementType.WATER;
                        break;
                    case ("GRASS"):
                        t = ElementType.GRASS;
                        break;
                }
                ElementEffectivityKey source_target = new ElementEffectivityKey(s,t);
                listOfEffectivity.add(source_target, effectivity);
            }
        }
        catch (Exception e) {
            System.out.println("Failed to Load Effectivity...");
        }

        boolean menu = true;
        int inputMenu;
        Scanner scan = new Scanner(System.in);

        while (menu){
            System.out.println("\nSelamat datang di Monster Saku!\n");
            System.out.println("[1] Start Game\n[2] Help\n[3] Exit\n");

            System.out.printf("> ");
            inputMenu = scan.nextInt();

            if (inputMenu == 2) {
                System.out.println("HELP\n");
            } else if (inputMenu == 3) {
                menu = false;
                System.out.println("\nTerima kasih telah bermain Monster Saku!\nSampai jumpa Player!");
            } else{
                boolean game = true;

                while (game){
                    System.out.printf("\nMasukkan nama Player 1: ");
                    String namaPlayer1 = scan.next();
                    System.out.printf("\nMasukkan nama Player 2: ");
                    String namaPlayer2 = scan.next();

                    Random random = new Random();
                    Integer countMonster = monsterList.size();

                    ArrayList<Monster> player1Monster = new ArrayList<Monster>();
                    boolean tmp = false;
                    while(!tmp) {
                        Integer randomMonster = random.nextInt(countMonster);
                        Monster monsterP1 = monsterList.get(randomMonster);
                        while (player1Monster.contains(monsterP1)){
                            randomMonster = random.nextInt(countMonster);
                            monsterP1 = monsterList.get(randomMonster);
                        }
                        player1Monster.add(monsterP1);
                        if (player1Monster.size() == 6) {
                            tmp = true;
                        }
                    }

                    ArrayList<Monster> player2Monster = new ArrayList<Monster>();
                    tmp = false;
                    while (!tmp) {
                        Integer randomMonster = random.nextInt(countMonster);
                        Monster monsterP2 = monsterList.get(randomMonster);
                        while (player2Monster.contains(monsterP2)){
                            randomMonster = random.nextInt(countMonster);
                            monsterP2 = monsterList.get(randomMonster);
                        }
                        player2Monster.add(monsterP2);
                        if (player2Monster.size() == 6) {
                            tmp = true;
                        }
                    }

                    Player p1 = new Player(namaPlayer1, player1Monster);
                    Player p2 = new Player(namaPlayer2, player2Monster);

                    int curMons1 = random.nextInt(6);
                    p1.setCurrentMonster(player1Monster.get(curMons1));
                    p1.printMonstersName();

                    int curMons2 = random.nextInt(6);
                    p2.setCurrentMonster(player2Monster.get(curMons2));
                    p2.printMonstersName();

                    System.out.printf("\nPertandingan antara %s dan %s dimulai!%n", p1.getName(), p2.getName());
                    System.out.printf("\n%s mengeluarkan %s!%n", p1.getName(), p1.getCurrentMonster().getName());
                    System.out.printf("\n%s mengeluarkan %s!%n", p2.getName(), p2.getCurrentMonster().getName());

                    int countMons1 = 6;
                    int countMons2 = 6;

                    boolean turn = true;
                    int curTurn = 0;

                    while (turn){
                        curTurn += 1;

                        // Check Monster Status Condition Burn
                        if (p1.getCurrentMonster().getStatusCondition() == StatusCondition.BURN) {
                            System.out.printf("%s terkena damage burn\n", p1.getCurrentMonster().getName());
                            double newHealth1 = (p1.getCurrentMonster().getStats().getHealthPoint())
                                    - Math.floor(p1.getCurrentMonster().getStats().getMaxHealth() * 1/8);
                            p1.getCurrentMonster().getStats().setHealthPoint(newHealth1);
                        }
                        if (p2.getCurrentMonster().getStatusCondition() == StatusCondition.BURN) {
                            System.out.printf("%s terkena damage burn\n", p2.getCurrentMonster().getName());
                            double newHealth2 = (p2.getCurrentMonster().getStats().getHealthPoint())
                                    - Math.floor(p2.getCurrentMonster().getStats().getMaxHealth() * 1/8);
                            p2.getCurrentMonster().getStats().setHealthPoint(newHealth2);
                        }

                        // Check Monster Status Condition Poison
                        if (p1.getCurrentMonster().getStatusCondition() == StatusCondition.POISON) {
                            System.out.printf("%s terkena damage poison\n", p1.getCurrentMonster().getName());
                            double newHealth1 = (p1.getCurrentMonster().getStats().getHealthPoint())
                                    - Math.floor(p1.getCurrentMonster().getStats().getMaxHealth() * 1/16);
                            p1.getCurrentMonster().getStats().setHealthPoint(newHealth1);
                        }
                        if (p2.getCurrentMonster().getStatusCondition() == StatusCondition.POISON) {
                            System.out.printf("%s terkena damage poison\n", p2.getCurrentMonster().getName());
                            double newHealth2 = (p2.getCurrentMonster().getStats().getHealthPoint())
                                    - Math.floor(p2.getCurrentMonster().getStats().getMaxHealth() * 1/16);
                            p2.getCurrentMonster().getStats().setHealthPoint(newHealth2);
                        }

                        // Check Monster Status Condition Paralyze
                        if (p1.getCurrentMonster().getStatusCondition() == StatusCondition.PARALYZE) {
                            int chanceParalyze1 = random.nextInt(4);
                            if (chanceParalyze1 == 1) {
                                p1.getCurrentMonster().setIsMoveable(false);
                            }
                            else{
                                // Then IsMoveable = true
                                continue;
                            }
                        }
                        if (p2.getCurrentMonster().getStatusCondition() == StatusCondition.PARALYZE) {
                            int chanceParalyze2 = random.nextInt(4);
                            if (chanceParalyze2 == 1) {
                                p2.getCurrentMonster().setIsMoveable(false);
                            }
                            else{
                                // Then IsMoveable = true
                                continue;
                            }
                        }

                        // Check Monster Status Condition Sleep
                        if(curTurn == 1) {
                            continue;
                        }
                        else {
                            for (Monster monster1 : player1Monster) {
                                if (monster1.getStats().getHealthPoint() > 0) {
                                    monster1.setSleepTime(monster1.getSleepTime() - 1);
                                    if (monster1.getSleepTime() <= 0) {
                                        if (monster1.getStatusCondition() == StatusCondition.SLEEP) {
                                            System.out.printf("%s terbangun\n", monster1.getName());
                                            monster1.setStatusCondition(StatusCondition.NONE);
                                        }
                                    }
                                    else{
                                        System.out.printf("%s tertidur lelap\n", monster1.getName());
                                    }
                                }
                            }
                            for (Monster monster2 : player2Monster) {
                                if (monster2.getStats().getHealthPoint() > 0) {
                                    monster2.setSleepTime(monster2.getSleepTime() - 1);
                                    if (monster2.getSleepTime() <= 0) {
                                        if (monster2.getStatusCondition() == StatusCondition.SLEEP) {
                                            System.out.printf("%s terbangun\n", monster2.getName());
                                            monster2.setStatusCondition(StatusCondition.NONE);
                                        }
                                    }
                                    else{
                                        System.out.printf("%s tertidur lelap\n", monster2.getName());
                                    }
                                }
                            }
                        }
                        int inputP1 = 0;
                        int inputP2 = 0;

                        boolean turn1 = true;

                        while (turn1){
                            System.out.println("\n[Player 1]");
                            System.out.printf("Apa yang akan %s lakukan?\n", p1.getCurrentMonster().getName());
                            System.out.println("\n[1] Move\n[2] Switch\n[3] View Monster Info\n[4] View Game Info\n");

                            System.out.print("> ");
                            inputP1 = scan.nextInt();

                            if (inputP1 == 3){
                                p1.printMonsters();
                            } else if (inputP1 == 4){
                                System.out.println("\nGame Info:\n");
                                System.out.printf("Pertandingan antara %s dan %s%n", p1.getName(), p2.getName());
                                System.out.printf("%nTurn ke-%d%n", curTurn);
                                System.out.println("\nMonsters yang bertarung:\n");
                                System.out.println(p1.getCurrentMonster().getName() + " [" + p1.getName() + "]");
                                System.out.println(p2.getCurrentMonster().getName() + " [" + p2.getName() + "]\n");
                            } else if (inputP1 == 2){
                                if (countMons1 > 1){
                                    boolean isSwitch = true;
                                    while (isSwitch) {
                                        p1.printMonsters();
                                        System.out.printf("Pilih monster: ");
                                        int s = scan.nextInt();
                                        if(p1.getMonsters().get(s-1).getStats().getHealthPoint() <= 0){
                                            System.out.printf("Monster sudah mati\n");
                                        }
                                        else{
                                            p1.setCurrentMonster(p1.getMonsters().get(s-1));
                                            isSwitch = false;
                                        }
                                    }
                                    turn1 = false;
                                }
                                else{
                                    System.out.println("\nKamu hanya memiliki 1 monster!\n");
                                }
                            } else {
                                System.out.println("Moves milik " + p1.getCurrentMonster().getName() + ":\n");
                                int index1 = 1;
                                for (Move move : p1.getCurrentMonster().getMoves()) {
                                    System.out.println("Id Move     : " + index1);
                                    System.out.println("Move Name   : " + move.getName());
                                    System.out.println("\n");
                                    index1++;
                                }
                                boolean outOfAmmunition = false;
                                while(!outOfAmmunition) {
                                    System.out.printf("Pilih move: ");
                                    int movePlayer1 = scan.nextInt();
                                    Move newMove1 = p1.getCurrentMonster().getMoves().get(movePlayer1 - 1);
                                    p1.getCurrentMonster().setCurrentMove(newMove1);
                                    if (p1.getCurrentMonster().getCurrentMove().getAmmunition() <= 0){
                                        System.out.printf("Amunisi habis!\n");
                                    }
                                    else {
                                        outOfAmmunition = true;
                                        String nowMove1 = p1.getCurrentMonster().getCurrentMove().getName();
                                        System.out.printf("%s menggunakan %s\n\n", p1.getCurrentMonster().getName(), nowMove1);
                                    }
                                }
                                turn1 = false;
                            }
                        }
                        boolean turn2 = true;

                        while (turn2){
                            System.out.println("\n[Player 2]");
                            System.out.printf("Apa yang akan %s lakukan?\n", p2.getCurrentMonster().getName());
                            System.out.println("\n[1] Move\n[2] Switch\n[3] View Monster Info\n[4] View Game Info\n");

                            System.out.print("> ");
                            inputP2 = scan.nextInt();

                            if (inputP2 == 3){
                                p2.printMonsters();
                            } else if (inputP2 == 4){
                                System.out.println("\nGame Info:\n");
                                System.out.printf("Pertandingan antara %s dan %s%n", p1.getName(), p2.getName());
                                System.out.printf("%nTurn ke-%d%n", curTurn);
                                System.out.println("\nMonsters yang bertarung:\n");
                                System.out.println(p1.getCurrentMonster().getName() + " [" + p1.getName() + "]");
                                System.out.println(p2.getCurrentMonster().getName() + " [" + p2.getName() + "]\n");
                            } else if (inputP2 == 2){
                                if (countMons2 > 1){
                                    boolean isSwitch = true;
                                    while (isSwitch) {
                                        p2.printMonsters();
                                        System.out.printf("Pilih monster: ");
                                        int s = scan.nextInt();
                                        if(p2.getMonsters().get(s-1).getStats().getHealthPoint() <= 0){
                                            System.out.printf("Monster sudah mati\n");
                                        }
                                        else{
                                            p2.setCurrentMonster(p2.getMonsters().get(s-1));
                                            isSwitch = false;
                                        }
                                    }
                                    turn1 = false;
                                }
                                else{
                                    System.out.println("\nKamu hanya memiliki 1 monster!\n");
                                }
                            } else {
                                System.out.println("Moves milik " + p2.getCurrentMonster().getName() + ":\n");
                                int index1 = 1;
                                for (Move move : p2.getCurrentMonster().getMoves()) {
                                    System.out.println("Id Move     : " + index1);
                                    System.out.println("Move Name   : " + move.getName());
                                    System.out.println("\n");
                                    index1++;
                                }
                                boolean outOfAmmunition = false;
                                while(!outOfAmmunition) {
                                    System.out.printf("Pilih move: ");
                                    int movePlayer1 = scan.nextInt();
                                    Move newMove1 = p2.getCurrentMonster().getMoves().get(movePlayer1 - 1);
                                    p2.getCurrentMonster().setCurrentMove(newMove1);
                                    if (p2.getCurrentMonster().getCurrentMove().getAmmunition() <= 0){
                                        System.out.printf("Amunisi habis!\n");
                                    }
                                    else {
                                        outOfAmmunition = true;
                                        String nowMove1 = p2.getCurrentMonster().getCurrentMove().getName();
                                        System.out.printf("%s menggunakan %s\n\n", p2.getCurrentMonster().getName(), nowMove1);
                                    }
                                }
                                turn2 = false;
                            }
                        }

                        if ((inputP1 == 1) && (inputP2 == 1)) {
                            int p1Priority = p1.getCurrentMonster().getCurrentMove().getPriority();
                            int p2Priority = p2.getCurrentMonster().getCurrentMove().getPriority();
                            Double p1Speed = p1.getCurrentMonster().getStats().getSpeed();
                            Double p2Speed = p2.getCurrentMonster().getStats().getSpeed();
                            if (p1.getCurrentMonster().getIsMoveable() == false || p1.getCurrentMonster().getIsMoveable() == false) {
                                if (p2.getCurrentMonster().getIsMoveable() == true) {
                                    // Player 1 Move
                                    System.out.printf("%s tidak bisa bergerak\n", p2.getCurrentMonster().getName());
                                    System.out.printf("%s's %s Attack %s's %s\n", p1.getName(), p1.getCurrentMonster().getName(),
                                            p2.getName(), p2.getCurrentMonster().getName());
                                    p1.getCurrentMonster().getCurrentMove().applyMove(p1.getCurrentMonster(), p2.getCurrentMonster(), listOfEffectivity);
                                    if (p2.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                        countMons2 -= 1;
                                        System.out.printf("%s mati\n", p2.getCurrentMonster().getName());
                                        if (countMons2==0) {
                                            turn = false;
                                        }
                                        else {
                                            System.out.printf("\nPilih monster: ");
                                            int nextMonster = scan.nextInt();
                                            while (p2.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                                System.out.printf("%s sudah mati\n", p2.getMonsters().get(nextMonster - 1).getName());
                                                System.out.printf("Pilih monster: ");
                                                nextMonster = scan.nextInt();
                                            }
                                            Monster newMonster = p2.getMonsters().get(nextMonster - 1);
                                            p2.setCurrentMonster(newMonster);
                                            System.out.printf("%s mengeluarkan %s!\n", p2.getName(), p2.getCurrentMonster().getName());
                                        }
                                    }
                                    else if (p1.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                        countMons1 -= 1;
                                        System.out.printf("%s mati\n", p1.getCurrentMonster().getName());
                                        if (countMons1==0) {
                                            turn = false;
                                        }
                                        else {
                                            System.out.printf("Pilih monster: ");
                                            int nextMonster = scan.nextInt();
                                            while (p1.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                                System.out.printf("%s sudah mati\n", p1.getMonsters().get(nextMonster - 1).getName());
                                                System.out.printf("Pilih monster: ");
                                                nextMonster = scan.nextInt();
                                            }
                                            Monster newMonster = p1.getMonsters().get(nextMonster - 1);
                                            p1.setCurrentMonster(newMonster);
                                            System.out.printf("%s mengeluarkan %s!\n", p1.getName(),p1.getCurrentMonster().getName());
                                        }
                                    }
                                }
                                else if (p1.getCurrentMonster().getIsMoveable() == true) {
                                    // Player 2 Move
                                    System.out.printf("%s's %s Attack %s's %s\n", p2.getName(), p2.getCurrentMonster().getName(),
                                            p1.getName(), p1.getCurrentMonster().getName());
                                    p2.getCurrentMonster().getCurrentMove().applyMove(p2.getCurrentMonster(), p1.getCurrentMonster(), listOfEffectivity);
                                    if (p2.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                        countMons2 -= 1;
                                        System.out.printf("%s mati\n", p2.getCurrentMonster().getName());
                                        if (countMons2==0) {
                                            turn = false;
                                        }
                                        else {
                                            System.out.printf("\nPilih monster: ");
                                            int nextMonster = scan.nextInt();
                                            while (p2.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                                System.out.printf("%s sudah mati\n", p2.getMonsters().get(nextMonster - 1).getName());
                                                System.out.printf("Pilih monster: ");
                                                nextMonster = scan.nextInt();
                                            }
                                            Monster newMonster = p2.getMonsters().get(nextMonster - 1);
                                            p2.setCurrentMonster(newMonster);
                                            System.out.printf("%s mengeluarkan %s!\n", p2.getName(), p2.getCurrentMonster().getName());
                                        }
                                    }
                                    else if (p1.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                        countMons1 -= 1;
                                        System.out.printf("%s mati\n", p1.getCurrentMonster().getName());
                                        if (countMons1==0) {
                                            turn = false;
                                        }
                                        else {
                                            System.out.printf("Pilih monster: ");
                                            int nextMonster = scan.nextInt();
                                            while (p1.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                                System.out.printf("%s sudah mati\n", p1.getMonsters().get(nextMonster - 1).getName());
                                                System.out.printf("Pilih monster: ");
                                                nextMonster = scan.nextInt();
                                            }
                                            Monster newMonster = p1.getMonsters().get(nextMonster - 1);
                                            p1.setCurrentMonster(newMonster);
                                            System.out.printf("%s mengeluarkan %s!\n", p1.getName(),p1.getCurrentMonster().getName());
                                        }
                                    }
                                }
                                else {
                                    System.out.printf("%s tidak bisa bergerak\n", p1.getCurrentMonster().getName());
                                    System.out.printf("%s tidak bisa bergerak\n", p2.getCurrentMonster().getName());
                                }
                            }
                            else {
                                // Adu Priority
                                if((p1Priority > p2Priority) || ((p1Priority == p2Priority) && (p1Speed > p2Speed))) {
                                    // Player 1 Move First
                                    System.out.printf("%s's %s Attack %s's %s\n", p1.getName(), p1.getCurrentMonster().getName(),
                                            p2.getName(), p2.getCurrentMonster().getName());
                                    p1.getCurrentMonster().getCurrentMove().applyMove(p1.getCurrentMonster(), p2.getCurrentMonster(), listOfEffectivity);
                                    if (p2.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                        countMons2 -= 1;
                                        System.out.printf("%s mati\n", p2.getCurrentMonster().getName());
                                        if (countMons2==0) {
                                            turn = false;
                                        }
                                        else {
                                            System.out.printf("\nPilih monster: ");
                                            int nextMonster = scan.nextInt();
                                            while (p2.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                                System.out.printf("%s sudah mati\n", p2.getMonsters().get(nextMonster - 1).getName());
                                                System.out.printf("Pilih monster: ");
                                                nextMonster = scan.nextInt();
                                            }
                                            Monster newMonster = p2.getMonsters().get(nextMonster - 1);
                                            p2.setCurrentMonster(newMonster);
                                            System.out.printf("%s mengeluarkan %s!\n", p2.getName(), p2.getCurrentMonster().getName());
                                        }
                                    }
                                    else if (p1.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                        countMons1 -= 1;
                                        System.out.printf("%s mati\n", p1.getCurrentMonster().getName());
                                        if (countMons1==0) {
                                            turn = false;
                                        }
                                        else {
                                            System.out.printf("Pilih monster: ");
                                            int nextMonster = scan.nextInt();
                                            while (p1.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                                System.out.printf("%s sudah mati\n", p1.getMonsters().get(nextMonster - 1).getName());
                                                System.out.printf("Pilih monster: ");
                                                nextMonster = scan.nextInt();
                                            }
                                            Monster newMonster = p1.getMonsters().get(nextMonster - 1);
                                            p1.setCurrentMonster(newMonster);
                                            System.out.printf("%s mengeluarkan %s!\n", p1.getName(),p1.getCurrentMonster().getName());
                                        }
                                    }
                                    else {
                                        System.out.printf("%s's %s Attack %s's %s\n", p2.getName(), p2.getCurrentMonster().getName(),
                                                p1.getName(), p1.getCurrentMonster().getName());
                                        p2.getCurrentMonster().getCurrentMove().applyMove(p2.getCurrentMonster(), p1.getCurrentMonster(), listOfEffectivity);
                                        if (p2.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                            countMons2 -= 1;
                                            System.out.printf("%s mati\n", p2.getCurrentMonster().getName());
                                            if (countMons2==0) {
                                                turn = false;
                                            }
                                            else {
                                                System.out.printf("\nPilih monster: ");
                                                int nextMonster = scan.nextInt();
                                                while (p2.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                                    System.out.printf("%s sudah mati\n", p2.getMonsters().get(nextMonster - 1).getName());
                                                    System.out.printf("Pilih monster: ");
                                                    nextMonster = scan.nextInt();
                                                }
                                                Monster newMonster = p2.getMonsters().get(nextMonster - 1);
                                                p2.setCurrentMonster(newMonster);
                                                System.out.printf("%s mengeluarkan %s!\n", p2.getName(), p2.getCurrentMonster().getName());
                                            }
                                        }
                                        else if (p1.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                            countMons1 -= 1;
                                            System.out.printf("%s mati\n", p1.getCurrentMonster().getName());
                                            if (countMons1==0) {
                                                turn = false;
                                            }
                                            else {
                                                System.out.printf("Pilih monster: ");
                                                int nextMonster = scan.nextInt();
                                                while (p1.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                                    System.out.printf("%s sudah mati\n", p1.getMonsters().get(nextMonster - 1).getName());
                                                    System.out.printf("Pilih monster: ");
                                                    nextMonster = scan.nextInt();
                                                }
                                                Monster newMonster = p1.getMonsters().get(nextMonster - 1);
                                                p1.setCurrentMonster(newMonster);
                                                System.out.printf("%s mengeluarkan %s!\n", p1.getName(),p1.getCurrentMonster().getName());
                                            }
                                        }
                                    }
                                }

                                else if((p1Priority < p2Priority) || ((p1Priority == p2Priority) && (p1Speed < p2Speed))) {
                                    // Player 2 Move
                                    System.out.printf("%s's %s Attack %s's %s\n", p2.getName(), p2.getCurrentMonster().getName(),
                                            p1.getName(), p1.getCurrentMonster().getName());
                                    p2.getCurrentMonster().getCurrentMove().applyMove(p2.getCurrentMonster(), p1.getCurrentMonster(), listOfEffectivity);
                                    if (p2.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                        countMons2 -= 1;
                                        System.out.printf("%s mati\n", p2.getCurrentMonster().getName());
                                        if (countMons2==0) {
                                            turn = false;
                                        }
                                        else {
                                            System.out.printf("\nPilih monster: ");
                                            int nextMonster = scan.nextInt();
                                            while (p2.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                                System.out.printf("%s sudah mati\n", p2.getMonsters().get(nextMonster - 1).getName());
                                                System.out.printf("Pilih monster: ");
                                                nextMonster = scan.nextInt();
                                            }
                                            Monster newMonster = p2.getMonsters().get(nextMonster - 1);
                                            p2.setCurrentMonster(newMonster);
                                            System.out.printf("%s mengeluarkan %s!\n", p2.getName(), p2.getCurrentMonster().getName());
                                        }
                                    }
                                    else if (p1.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                        countMons1 -= 1;
                                        System.out.printf("%s mati\n", p1.getCurrentMonster().getName());
                                        if (countMons1==0) {
                                            turn = false;
                                        }
                                        else {
                                            System.out.printf("Pilih monster: ");
                                            int nextMonster = scan.nextInt();
                                            while (p1.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                                System.out.printf("%s sudah mati\n", p1.getMonsters().get(nextMonster - 1).getName());
                                                System.out.printf("Pilih monster: ");
                                                nextMonster = scan.nextInt();
                                            }
                                            Monster newMonster = p1.getMonsters().get(nextMonster - 1);
                                            p1.setCurrentMonster(newMonster);
                                            System.out.printf("%s mengeluarkan %s!\n", p1.getName(),p1.getCurrentMonster().getName());
                                        }
                                    }
                                    else {
                                        // Player 1 Move First
                                        System.out.printf("%s's %s Attack %s's %s\n", p1.getName(), p1.getCurrentMonster().getName(),
                                                p2.getName(), p2.getCurrentMonster().getName());
                                        p1.getCurrentMonster().getCurrentMove().applyMove(p1.getCurrentMonster(), p2.getCurrentMonster(), listOfEffectivity);
                                        if (p2.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                            countMons2 -= 1;
                                            System.out.printf("%s mati\n", p2.getCurrentMonster().getName());
                                            if (countMons2==0) {
                                                turn = false;
                                            }
                                            else {
                                                System.out.printf("\nPilih monster: ");
                                                int nextMonster = scan.nextInt();
                                                while (p2.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                                    System.out.printf("%s sudah mati\n", p2.getMonsters().get(nextMonster - 1).getName());
                                                    System.out.printf("Pilih monster: ");
                                                    nextMonster = scan.nextInt();
                                                }
                                                Monster newMonster = p2.getMonsters().get(nextMonster - 1);
                                                p2.setCurrentMonster(newMonster);
                                                System.out.printf("%s mengeluarkan %s!\n", p2.getName(), p2.getCurrentMonster().getName());
                                            }
                                        }
                                        else if (p1.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                            countMons1 -= 1;
                                            System.out.printf("%s mati\n", p1.getCurrentMonster().getName());
                                            if (countMons1==0) {
                                                turn = false;
                                            }
                                            else {
                                                System.out.printf("Pilih monster: ");
                                                int nextMonster = scan.nextInt();
                                                while (p1.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                                    System.out.printf("%s sudah mati\n", p1.getMonsters().get(nextMonster - 1).getName());
                                                    System.out.printf("Pilih monster: ");
                                                    nextMonster = scan.nextInt();
                                                }
                                                Monster newMonster = p1.getMonsters().get(nextMonster - 1);
                                                p1.setCurrentMonster(newMonster);
                                                System.out.printf("%s mengeluarkan %s!\n", p1.getName(),p1.getCurrentMonster().getName());
                                            }
                                        }
                                    }
                                }
                                else {
                                    int firstMove = random.nextInt(2);
                                    if (firstMove == 0) {
                                        // Player 1 Move First
                                        System.out.printf("%s's %s Attack %s's %s\n", p1.getName(), p1.getCurrentMonster().getName(),
                                                p2.getName(), p2.getCurrentMonster().getName());
                                        p1.getCurrentMonster().getCurrentMove().applyMove(p1.getCurrentMonster(), p2.getCurrentMonster(), listOfEffectivity);
                                        if (p2.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                            countMons2 -= 1;
                                            System.out.printf("%s mati\n", p2.getCurrentMonster().getName());
                                            if (countMons2==0) {
                                                turn = false;
                                            }
                                            else {
                                                System.out.printf("\nPilih monster: ");
                                                int nextMonster = scan.nextInt();
                                                while (p2.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                                    System.out.printf("%s sudah mati\n", p2.getMonsters().get(nextMonster - 1).getName());
                                                    System.out.printf("Pilih monster: ");
                                                    nextMonster = scan.nextInt();
                                                }
                                                Monster newMonster = p2.getMonsters().get(nextMonster - 1);
                                                p2.setCurrentMonster(newMonster);
                                                System.out.printf("%s mengeluarkan %s!\n", p2.getName(), p2.getCurrentMonster().getName());
                                            }
                                        }
                                        else if (p1.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                            countMons1 -= 1;
                                            System.out.printf("%s mati\n", p1.getCurrentMonster().getName());
                                            if (countMons1==0) {
                                                turn = false;
                                            }
                                            else {
                                                System.out.printf("Pilih monster: ");
                                                int nextMonster = scan.nextInt();
                                                while (p1.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                                    System.out.printf("%s sudah mati\n", p1.getMonsters().get(nextMonster - 1).getName());
                                                    System.out.printf("Pilih monster: ");
                                                    nextMonster = scan.nextInt();
                                                }
                                                Monster newMonster = p1.getMonsters().get(nextMonster - 1);
                                                p1.setCurrentMonster(newMonster);
                                                System.out.printf("%s mengeluarkan %s!\n", p1.getName(),p1.getCurrentMonster().getName());
                                            }
                                        }
                                        else {
                                            System.out.printf("%s's %s Attack %s's %s\n", p2.getName(), p2.getCurrentMonster().getName(),
                                                    p1.getName(), p1.getCurrentMonster().getName());
                                            p2.getCurrentMonster().getCurrentMove().applyMove(p2.getCurrentMonster(), p1.getCurrentMonster(), listOfEffectivity);
                                            if (p2.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                                countMons2 -= 1;
                                                System.out.printf("%s mati\n", p2.getCurrentMonster().getName());
                                                if (countMons2==0) {
                                                    turn = false;
                                                }
                                                else {
                                                    System.out.printf("\nPilih monster: ");
                                                    int nextMonster = scan.nextInt();
                                                    while (p2.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                                        System.out.printf("%s sudah mati\n", p2.getMonsters().get(nextMonster - 1).getName());
                                                        System.out.printf("Pilih monster: ");
                                                        nextMonster = scan.nextInt();
                                                    }
                                                    Monster newMonster = p2.getMonsters().get(nextMonster - 1);
                                                    p2.setCurrentMonster(newMonster);
                                                    System.out.printf("%s mengeluarkan %s!\n", p2.getName(), p2.getCurrentMonster().getName());
                                                }
                                            }
                                            else if (p1.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                                countMons1 -= 1;
                                                System.out.printf("%s mati\n", p1.getCurrentMonster().getName());
                                                if (countMons1==0) {
                                                    turn = false;
                                                }
                                                else {
                                                    System.out.printf("Pilih monster: ");
                                                    int nextMonster = scan.nextInt();
                                                    while (p1.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                                        System.out.printf("%s sudah mati\n", p1.getMonsters().get(nextMonster - 1).getName());
                                                        System.out.printf("Pilih monster: ");
                                                        nextMonster = scan.nextInt();
                                                    }
                                                    Monster newMonster = p1.getMonsters().get(nextMonster - 1);
                                                    p1.setCurrentMonster(newMonster);
                                                    System.out.printf("%s mengeluarkan %s!\n", p1.getName(),p1.getCurrentMonster().getName());
                                                }
                                            }
                                        }
                                    }
                                    else {
                                        // Player 2 Move
                                        System.out.printf("%s's %s Attack %s's %s\n", p2.getName(), p2.getCurrentMonster().getName(),
                                                p1.getName(), p1.getCurrentMonster().getName());
                                        p2.getCurrentMonster().getCurrentMove().applyMove(p2.getCurrentMonster(), p1.getCurrentMonster(), listOfEffectivity);
                                        if (p2.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                            countMons2 -= 1;
                                            System.out.printf("%s mati\n", p2.getCurrentMonster().getName());
                                            if (countMons2==0) {
                                                turn = false;
                                            }
                                            else {
                                                System.out.printf("\nPilih monster: ");
                                                int nextMonster = scan.nextInt();
                                                while (p2.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                                    System.out.printf("%s sudah mati\n", p2.getMonsters().get(nextMonster - 1).getName());
                                                    System.out.printf("Pilih monster: ");
                                                    nextMonster = scan.nextInt();
                                                }
                                                Monster newMonster = p2.getMonsters().get(nextMonster - 1);
                                                p2.setCurrentMonster(newMonster);
                                                System.out.printf("%s mengeluarkan %s!\n", p2.getName(), p2.getCurrentMonster().getName());
                                            }
                                        }
                                        else if (p1.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                            countMons1 -= 1;
                                            System.out.printf("%s mati\n", p1.getCurrentMonster().getName());
                                            if (countMons1==0) {
                                                turn = false;
                                            }
                                            else {
                                                System.out.printf("Pilih monster: ");
                                                int nextMonster = scan.nextInt();
                                                while (p1.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                                    System.out.printf("%s sudah mati\n", p1.getMonsters().get(nextMonster - 1).getName());
                                                    System.out.printf("Pilih monster: ");
                                                    nextMonster = scan.nextInt();
                                                }
                                                Monster newMonster = p1.getMonsters().get(nextMonster - 1);
                                                p1.setCurrentMonster(newMonster);
                                                System.out.printf("%s mengeluarkan %s!\n", p1.getName(),p1.getCurrentMonster().getName());
                                            }
                                        }
                                        else {
                                            // Player 1 Move First
                                            System.out.printf("%s's %s Attack %s's %s\n", p1.getName(), p1.getCurrentMonster().getName(),
                                                    p2.getName(), p2.getCurrentMonster().getName());
                                            p1.getCurrentMonster().getCurrentMove().applyMove(p1.getCurrentMonster(), p2.getCurrentMonster(), listOfEffectivity);
                                            if (p2.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                                countMons2 -= 1;
                                                System.out.printf("%s mati\n", p2.getCurrentMonster().getName());
                                                if (countMons2==0) {
                                                    turn = false;
                                                }
                                                else {
                                                    System.out.printf("\nPilih monster: ");
                                                    int nextMonster = scan.nextInt();
                                                    while (p2.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                                        System.out.printf("%s sudah mati\n", p2.getMonsters().get(nextMonster - 1).getName());
                                                        System.out.printf("Pilih monster: ");
                                                        nextMonster = scan.nextInt();
                                                    }
                                                    Monster newMonster = p2.getMonsters().get(nextMonster - 1);
                                                    p2.setCurrentMonster(newMonster);
                                                    System.out.printf("%s mengeluarkan %s!\n", p2.getName(), p2.getCurrentMonster().getName());
                                                }
                                            }
                                            else if (p1.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                                countMons1 -= 1;
                                                System.out.printf("%s mati\n", p1.getCurrentMonster().getName());
                                                if (countMons1==0) {
                                                    turn = false;
                                                }
                                                else {
                                                    System.out.printf("Pilih monster: ");
                                                    int nextMonster = scan.nextInt();
                                                    while (p1.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                                        System.out.printf("%s sudah mati\n", p1.getMonsters().get(nextMonster - 1).getName());
                                                        System.out.printf("Pilih monster: ");
                                                        nextMonster = scan.nextInt();
                                                    }
                                                    Monster newMonster = p1.getMonsters().get(nextMonster - 1);
                                                    p1.setCurrentMonster(newMonster);
                                                    System.out.printf("%s mengeluarkan %s!\n", p1.getName(),p1.getCurrentMonster().getName());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        else if ((inputP1 == 1) && (inputP2 == 2)) {
                            System.out.printf("\n%s mengeluarkan %s!\n", p2.getName(), p2.getCurrentMonster().getName());
                            if (p1.getCurrentMonster().getIsMoveable() == true) {
                                // Player 1 Move
                                System.out.printf("%s's %s Attack %s's %s\n", p1.getName(), p1.getCurrentMonster().getName(),
                                        p2.getName(), p2.getCurrentMonster().getName());
                                p1.getCurrentMonster().getCurrentMove().applyMove(p1.getCurrentMonster(), p2.getCurrentMonster(), listOfEffectivity);
                                if (p2.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                    countMons2 -= 1;
                                    System.out.printf("%s mati\n", p2.getCurrentMonster().getName());
                                    if (countMons2==0) {
                                        turn = false;
                                    }
                                    else {
                                        System.out.printf("\nPilih monster: ");
                                        int nextMonster = scan.nextInt();
                                        while (p2.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                            System.out.printf("%s sudah mati\n", p2.getMonsters().get(nextMonster - 1).getName());
                                            System.out.printf("Pilih monster: ");
                                            nextMonster = scan.nextInt();
                                        }
                                        Monster newMonster = p2.getMonsters().get(nextMonster - 1);
                                        p2.setCurrentMonster(newMonster);
                                        System.out.printf("%s mengeluarkan %s!\n", p2.getName(), p2.getCurrentMonster().getName());
                                    }
                                }
                                else if (p1.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                    countMons1 -= 1;
                                    System.out.printf("%s mati\n", p1.getCurrentMonster().getName());
                                    if (countMons1==0) {
                                        turn = false;
                                    }
                                    else {
                                        System.out.printf("Pilih monster: ");
                                        int nextMonster = scan.nextInt();
                                        while (p1.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                            System.out.printf("%s sudah mati\n", p1.getMonsters().get(nextMonster - 1).getName());
                                            System.out.printf("Pilih monster: ");
                                            nextMonster = scan.nextInt();
                                        }
                                        Monster newMonster = p1.getMonsters().get(nextMonster - 1);
                                        p1.setCurrentMonster(newMonster);
                                        System.out.printf("%s mengeluarkan %s!\n", p1.getName(),p1.getCurrentMonster().getName());
                                    }
                                }
                            }
                            else {
                                System.out.printf("%s tidak bisa bergerak\n", p1.getCurrentMonster().getName());
                            }
                        }

                        else if ((inputP1 == 2) && (inputP2 == 1)) {
                            System.out.printf("\n%s mengeluarkan %s!\n", p1.getName(), p1.getCurrentMonster().getName());
                            if (p2.getCurrentMonster().getIsMoveable() ==  true) {
                                // Player 2 Move
                                System.out.printf("%s's %s Attack %s's %s\n", p2.getName(), p2.getCurrentMonster().getName(),
                                        p1.getName(), p1.getCurrentMonster().getName());
                                p2.getCurrentMonster().getCurrentMove().applyMove(p2.getCurrentMonster(), p1.getCurrentMonster(), listOfEffectivity);
                                if (p2.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                    countMons2 -= 1;
                                    System.out.printf("%s mati\n", p2.getCurrentMonster().getName());
                                    if (countMons2==0) {
                                        turn = false;
                                    }
                                    else {
                                        System.out.printf("\nPilih monster: ");
                                        int nextMonster = scan.nextInt();
                                        while (p2.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                            System.out.printf("%s sudah mati\n", p2.getMonsters().get(nextMonster - 1).getName());
                                            System.out.printf("Pilih monster: ");
                                            nextMonster = scan.nextInt();
                                        }
                                        Monster newMonster = p2.getMonsters().get(nextMonster - 1);
                                        p2.setCurrentMonster(newMonster);
                                        System.out.printf("%s mengeluarkan %s!\n", p2.getName(), p2.getCurrentMonster().getName());
                                    }
                                }
                                else if (p1.getCurrentMonster().getStats().getHealthPoint() <= 0) {
                                    countMons1 -= 1;
                                    System.out.printf("%s mati\n", p1.getCurrentMonster().getName());
                                    if (countMons1==0) {
                                        turn = false;
                                    }
                                    else {
                                        System.out.printf("Pilih monster: ");
                                        int nextMonster = scan.nextInt();
                                        while (p1.getMonsters().get(nextMonster - 1).getStats().getHealthPoint() <= 0) {
                                            System.out.printf("%s sudah mati\n", p1.getMonsters().get(nextMonster - 1).getName());
                                            System.out.printf("Pilih monster: ");
                                            nextMonster = scan.nextInt();
                                        }
                                        Monster newMonster = p1.getMonsters().get(nextMonster - 1);
                                        p1.setCurrentMonster(newMonster);
                                        System.out.printf("%s mengeluarkan %s!\n", p1.getName(),p1.getCurrentMonster().getName());
                                    }
                                }
                            }
                            else {
                                System.out.printf("%s tidak bisa bergerak\n", p2.getCurrentMonster().getName());
                            }
                        }

                        else {
                            System.out.printf("\n%s mengeluarkan %s!\n", p1.getName(), p1.getCurrentMonster().getName());
                            System.out.printf("%s mengeluarkan %s!\n", p2.getName(), p2.getCurrentMonster().getName());
                        }
                    }
                    if (countMons1 == 0 && countMons2 == 0){
                        System.out.printf("Pertandingan berakhir dengan seri\n");
                        game = false;
                    }
                    else if (countMons1==0) {
                        System.out.printf("%s adalah juara pertandingan ini!\n", p2.getName());
                        game = false;
                    }
                    else if (countMons2==0) {
                        System.out.printf("%s adalah juara pertandingan ini!\n", p1.getName());
                        game = false;
                    }
                }
            }
        }
    }
}
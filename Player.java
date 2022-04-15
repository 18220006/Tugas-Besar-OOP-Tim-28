import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Monster> monsterList = new ArrayList<Monster>();
    private Monster currentMonster;


    public Player(String name, List<Monster> monsterList) {
        this.name = name;
        this.monsterList = monsterList;
    }

    public String getName() {
        return this.name;
    }

    public List<Monster> getMonsters() {
        return this.monsterList;
    }

    public void setCurrentMonster(Monster monster){
        this.currentMonster = monster;
    }

    public Monster getCurrentMonster() {
        return this.currentMonster;
    }

    public void printMonsters() {
        System.out.printf("%nMonster milik %s:%n", getName());
        int i = 0;
        for(Monster monster : monsterList) {
            i++;
            System.out.printf("%nID monster: %s%n", i);
            System.out.printf("Nama monster: %s%n", monster.getName());
            List<ElementType> elementTypes = monster.getElementType();
            for (ElementType elementType : elementTypes){
                if(elementType.equals(ElementType.NORMAL)){
                    System.out.printf("Element Type: %s%n", ElementType.NORMAL);
                }
                else if(elementType.equals(ElementType.FIRE)){
                    System.out.printf("Element Type: %s%n", ElementType.FIRE);
                }
                else if(elementType.equals(ElementType.WATER)){
                    System.out.printf("Element Type: %s%n", ElementType.WATER);
                }   
                else if(elementType.equals(ElementType.GRASS)){
                    System.out.printf("Element Type: %s%n", ElementType.GRASS);
                } 
            }
            monster.getStats().printStatus();
        }
    }

    public void printMonstersName() {
        int id = 0;
        System.out.printf("\nMonster milik %s:\n\n", getName());
        for (Monster monster : monsterList){
            System.out.println("[" + String.valueOf(id + 1) + "] " + monster.getName());
            id++;
        }
        System.out.println("\n");
    }

}

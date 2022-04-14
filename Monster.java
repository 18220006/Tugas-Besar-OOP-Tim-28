import java.util.List;
import java.util.ArrayList;

public class Monster {
    private int id;
    private String name;
    private List<ElementType> elementTypes = new ArrayList<ElementType>();
    private Stats baseStats;
    private List<Move> moves = new ArrayList<Move>();
    int selectedMove = -1;
    private StatusCondition statusCondition;
    private int sleepTime;
    private boolean isMoveable;
    private Move currentMove;

    public Monster(int id, String name, List<ElementType> elementTypes, Stats stats, List<Move> moves) {
        this.id = id;
        this.name = name;
        this.elementTypes = elementTypes;
        this.baseStats = stats;
        this.moves = moves;
        this.statusCondition = StatusCondition.NONE;
        this.sleepTime = 0;
        this.isMoveable = true;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ElementType> getElementType() {
        return elementTypes;
    }

    public void addElementType(ElementType el) {
        elementTypes.add(el);
    }

    public void setStats(Stats stats) {
        this.baseStats = stats;
    }

    public Stats getStats() {
        return baseStats;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void addMoves(Move move) {
        moves.add(move);
    }

    public void setMove(int selectedMove) {
        this.selectedMove = selectedMove;
    }

    public void setStatusCondition(StatusCondition statusCondition) {
        this.statusCondition = statusCondition;
    }

    public StatusCondition getStatusCondition() {
        return this.statusCondition;
    }

    public void setSleepTime(int sleepTime) {
        if(sleepTime < 0) {
            this.sleepTime = 0;
        }
        else {
            this.sleepTime = sleepTime;
        }
    }

    public int getSleepTime() {
        return this.sleepTime;
    }

    public void setIsMoveable(boolean isMoveable) {
        this.isMoveable = isMoveable;
    }

    public boolean getIsMoveable() {
        return this.isMoveable;
    }
    
    public int getMovesLenght(){
        return moves.size();
    }

    public void setCurrentMove(Move move){
        this.currentMove = move;
    }

    public Move getCurrentMove() {
        return this.currentMove;
    }
}

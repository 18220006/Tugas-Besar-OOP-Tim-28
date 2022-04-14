public abstract class Move {
    int id;
    String moveType;
    String name;
    ElementType type;
    int accuracy;
    int priority;
    int ammunition;
    String target;

    public Move(int id, String moveType, String name, ElementType type, int accuracy, int priority, int ammunition, String target) {
        this.id = id;
        this.moveType = moveType;
        this.name = name;
        this.type = type;
        this.accuracy = accuracy;
        this.priority = priority;
        this.ammunition = ammunition;
        this.target = target;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void getMoveType(String moveType) {
        this.moveType = moveType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(ElementType type) {
        this.type = type;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setAmmunition(int ammunition) {
        this.ammunition = ammunition;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getId(){
        return this.id;
    }
    
    public String getMoveType(){
        return this.moveType;
    }
    public String getName(){
        return this.name;
    }

    public ElementType getType(){
        return this.type;
    }

    public int getAccuracy(){
        return this.accuracy;
    }

    public int getPriority(){
        return this.priority;
    }

    public int getAmmunition(){
        return this.ammunition;
    }
    public String getTarget(){
        return this.target;
    }
    
    public abstract void applyMove(Monster ourMonster, Monster enemyMonster, Effectivity effectivity);

}

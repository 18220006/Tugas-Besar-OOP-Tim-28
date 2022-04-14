public class SpecialMoves extends Move{
    protected double BasePower;

    public SpecialMoves(int id, String moveType, String name, ElementType type, int accuracy, int priority, int ammunition, String target, double BasePower) {
        super(id, moveType, name, type, accuracy, priority, ammunition, target);
        this.BasePower = BasePower;
    }

    public void applyMove(Monster allyMonster, Monster enemyMonster, Effectivity effectivity){
        double randomAccuracy =  1 + (int)(Math.random() * ((100 - 1) + 1));
        if (randomAccuracy > super.getAccuracy()){
            System.out.printf("%s tidak kena!\n", super.getName());
        }
        else {
            double effective = 1.00;
            for (int i = 0; i < allyMonster.getElementType().size(); i++){
                ElementEffectivityKey effectivityTemp = new ElementEffectivityKey(super.getType(), enemyMonster.getElementType().get(i));
                effective = effective * Effectivity.getEffectivity(effectivityTemp);
            }

            double Burn;
            if (allyMonster.getStatusCondition() == StatusCondition.BURN){
                Burn = 0.5;
            } else{
                Burn = 1;
            }

            int min = 85;
            int max = 100;
            float randomDamage = ((int)Math.floor(Math.random()*(max-min + 1) + min));
            randomDamage = randomDamage/100;

            double damage;
            double sourceAttack = allyMonster.getStats().getSpecialAttack();
            double targetDefense = enemyMonster.getStats().getSpecialDefense();
            damage = Math.floor((BasePower * (sourceAttack / targetDefense) + 2) * randomDamage * effective * Burn);

            Stats enemyStats = enemyMonster.getStats();
            double newHealthPoint = enemyStats.getHealthPoint() - damage;
            enemyStats.setHealthPoint(newHealthPoint);
            enemyMonster.setStats(enemyStats);
            this.ammunition -= 1;
        }
    }
}

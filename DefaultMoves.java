public class DefaultMoves extends Move{
    protected double BasePower;

    public DefaultMoves() {
        super(1, "DEFAULT", "Default Move", ElementType.NORMAL, 100, 0, 9999, "ENEMY");
        this.BasePower = 50;
    }

    public void applyMove(Monster allyMonster, Monster enemyMonster, Effectivity effectivity) {
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
        double sourceAttack = allyMonster.getStats().getAttack();
        double targetDefense = enemyMonster.getStats().getDefense();
        damage = Math.floor((BasePower * (sourceAttack / targetDefense) + 2) * randomDamage * effective * Burn);

        Stats currentStats = enemyMonster.getStats();
        double newHealthPoint = currentStats.getHealthPoint() - damage;
        currentStats.setHealthPoint(newHealthPoint);
        enemyMonster.setStats(currentStats);

        Stats allyMonsterStats = allyMonster.getStats();
        double updateHP = Math.floor(allyMonsterStats.getMaxHealth() * 1 / 4);
        double currentHealth = allyMonsterStats.getHealthPoint() - updateHP;
        allyMonsterStats.setHealthPoint(currentHealth);
    }
}

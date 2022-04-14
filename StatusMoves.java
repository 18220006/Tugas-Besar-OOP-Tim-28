import java.util.*;

public class StatusMoves extends Move {
    protected String Effect;
    int[] EffectPoint;

    Random random = new Random();
    public StatusMoves(int id, String moveType, String name, ElementType type, int accuracy, int priority, int ammunition, String target, String effect, int[] effectPoint){
        super(id, moveType, name, type, accuracy, priority, ammunition, target);
        this.Effect = effect;
        this.EffectPoint = effectPoint;
    }

    public void applyMove(Monster allyMonster, Monster enemyMonster, Effectivity effectivity){
        double randomAccuracy =  1 + (int)(Math.random() * ((100 - 1) + 1));
        if (randomAccuracy > super.getAccuracy()){
            System.out.printf("%s tidak kena!", super.getName());
        }
        else {
            if (this.getTarget().equals("OWN")){
                double currentHP = allyMonster.getStats().getHealthPoint();
                currentHP += this.EffectPoint[0];
                allyMonster.getStats().setHealthPoint(currentHP);
            }
            else{
                if (Effect.equals("BURN")){
                    enemyMonster.setStatusCondition(StatusCondition.BURN);
                }else if (Effect.equals("POISON")){
                    enemyMonster.setStatusCondition(StatusCondition.POISON);
                }else if (Effect.equals("PARALYZE")){
                    enemyMonster.setStatusCondition(StatusCondition.PARALYZE);
                    double currentSpeed = enemyMonster.getStats().getSpeed();
                    enemyMonster.getStats().setSpeed(currentSpeed * 0.5);
                }else if (Effect.equals("SLEEP")){
                    int sleepTime = random.nextInt(7);
                    enemyMonster.setStatusCondition(StatusCondition.SLEEP);
                    enemyMonster.setSleepTime(sleepTime + 1);
                    enemyMonster.setIsMoveable(false);
                }
            }
        }
    }
}

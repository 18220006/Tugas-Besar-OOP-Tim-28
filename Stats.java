import java.lang.System;

public class Stats{
	private double healthPoint;
    private double maxHealthPoint;
    private double attack;
    private double defense;
    private double specialAttack;
    private double specialDefense;
    private double speed;

    public Stats(double healthPoint, double attack, double defense, double specialAttack, double specialDefense, double speed){
    	 this.healthPoint  = healthPoint;
         this.maxHealthPoint  = healthPoint;
		 this.attack = attack;
		 this.defense = defense;
		 this.specialAttack = specialAttack;
		 this.specialDefense = specialDefense;
		 this.speed = speed;
    }

    public double getHealthPoint(){
    	return healthPoint;
    }

    public double getMaxHealth(){
        return maxHealthPoint;
    }
    
    public void setHealthPoint(double healthPoint){
    	this.healthPoint  = healthPoint;
    }

    public double getAttack(){
    	return attack;
    }

    public void setAttack(double attack){
    	this.attack  = attack;
    }    

    public double getDefense(){
    	return defense;
    }

    public void setDefense(double defense){
    	this.defense  = defense;
    }

    public double getSpecialAttack(){
    	return specialAttack;
    }

    public void setSpecialAttack(double specialAttack){
    	this.specialAttack  = specialAttack;
    }

    public double getSpecialDefense(){
    	return specialDefense;
    }

    public void setSpecialDefense(double specialDefense){
    	this.specialDefense  = specialDefense;
    }

	public double getSpeed(){
    	return speed;
    }

    public void setSpeed(double speed){
    	this.speed  = speed;
    }

    public void printStatus(){
        System.out.println("Health Point    : " + getHealthPoint());
        System.out.println("Attack          : " + getAttack());
        System.out.println("Defense         : " + getDefense());
        System.out.println("Special Attack  : " + getSpecialAttack());
        System.out.println("Special Defense : " + getSpecialDefense());
        System.out.println("Speed           : " + getSpeed());   	
    }

}

import org.w3c.dom.ls.LSOutput;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.*;
public class Creature
{
    private static int FOOD2HEALTH = 6;
    private static int HEALTH2POWER = 4;
    private static int numStillAlive = 0;
    private String name;
    private int foodUnits;
    private int healthUnits;
    private int firePowerUnits;
    private Date dateCreated;
    private Date dateDied;

    public Creature(String name){
        this.name = name;
        numStillAlive++;
        Random rand = new Random();
        foodUnits = rand.nextInt((12 - 1) + 1) + 1; //random no. between 1 and 12 inclusive
        healthUnits = rand.nextInt((7 - 1) + 1) + 1; //random no. between 1 and 7 inclusive
        firePowerUnits = rand.nextInt((9 - 0) + 1) + 0; //random no. between 0-10 exclusive

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        dateCreated = new Date(); //getting the current date
        dateDied = null;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public void setHealthUnits(int n){
        this.healthUnits = n;
    }

    public void setFoodUnit(int n){
        this.foodUnits = n;
    }

    public void reduceFirePowerUnits(int n){
        this.firePowerUnits = n;
    }

    public String getName() {
        return this.name;
    }

    public int getFoodUnits(){
        return this.foodUnits;
    }

    public int getHealthUnits(){
        return this.healthUnits;
    }

    public int getFirePowerUnits(){
        return this.firePowerUnits;
    }

    public Date getDateCreated(){
        return this.dateCreated;
    }

    public Date getDateDied(){
        return this.dateDied;
    }

    public static int getNumStillAlive(){
        return numStillAlive;
    }

    public boolean isAlive(){
        if (this.dateDied == null){
            return true;
        }
        return false;
    }

    public int earnFood(){
        Random rand = new Random(); //might need fixing
        this.foodUnits += rand.nextInt((15 - 0) + 1) + 0;
        this.normalizeUnits();
        return this.earnFood();
    }

    public void attacking(Creature player){
        int temp = (int) Math.ceil(player.foodUnits/2);
        this.foodUnits += temp;
        player.foodUnits -= temp;

        int temp2 = (int) Math.ceil(player.healthUnits/2);
        this.healthUnits += temp2;
        player.healthUnits -= temp2;

        this.firePowerUnits -= 2;
        this.normalizeUnits();
        player.healthFoodUnitsZero();
    }

    public boolean healthFoodUnitsZero(){
        if (this.healthUnits == 0 && this.foodUnits == 0 && this.dateDied == null) {
            this.died();
            this.numStillAlive--;
            return true;
        }
        else if (this.healthUnits == 0 && this.foodUnits == 0){
            return true;
        }
        else
            return false;

    }

    private void died(){
        dateDied = new Date();
    }

    public String toString(){
        String attributes = (this.foodUnits + "-Food Units- " + this.healthUnits + ", -Health Units- " + this.firePowerUnits + ", -Fire power units- " );
        return attributes;
    }

    public String showStatus(){
        System.out.println("Food Units\t Health Units \t Firepower units \t Name");
        System.out.println("-----------\t -----------\t -----------\t -----------");
        System.out.println(this.getFoodUnits()+"\t"+this.getHealthUnits()
                +"\t"+this.getFirePowerUnits()+"\t"+this.getName());
        System.out.println(this.getDateCreated());
        System.out.println();
        if (this.getDateDied()==null){
            System.out.println("Date died: Is still alive");
        } else{
            System.out.println("Date died: " + this.getDateDied());
        }
        return this.showStatus();
    }

    public void normalizeUnits(){
        int temp;
        if (this.foodUnits >= 6){
            temp = this.foodUnits%6;
            this.foodUnits -= (temp*6);
            this.healthUnits += temp;
        }
        if (this.healthUnits > 4){
            temp = this.healthUnits % 4;
            this.healthUnits -= (temp*4);
            this.firePowerUnits += temp;
        }
    }

    public static void main(String[] args) {
        Scanner obj = new Scanner(System.in);
        System.out.println("Welcome to bootleg pokemon");
        System.out.println("How many Creatures would you like to have? (min 2, max 8): ");
        int creaturecount = obj.nextInt();
        while (creaturecount > 8||creaturecount < 2){
            System.out.println("Illegal number of creatures requested, try again");
            System.out.println("How many Creatures would you like to have? (min 2, max 8): ");
            creaturecount = obj.nextInt();
        }
        Creature[] crName = new Creature[(creaturecount)];
        System.out.println(crName.length);
        for (int i = 0;i<(crName.length-1);i++){
            System.out.print("What is the name of creature " + (i+1) + "?");
            crName[i] = new Creature(obj.nextLine());
            crName[i].showStatus();
        }

        Random rand = new Random();
        int turn = rand.nextInt((creaturecount - 1) + 1) + 1;
        int guy = turn-1;
        int n = guy;
        int option = 0;
        while (option != 5 && option != 6){
            System.out.println("Creature #"+turn+": "+crName[guy].getName()+", what do you want to do?");
            System.out.println("\t1. How many creatures are still alive?");
            System.out.println("\t2. See my status");
            System.out.println("\t3. See status of all players");
            System.out.println("\t4. Change my name");
            System.out.println("\t5. Work for some food");
            System.out.println("\t6. Attack another creature (Warning! may turn against you)");
            option = obj.nextInt(Integer.parseInt("Your choice please > "));
            System.out.println(" ");
            switch (option){
                case 1:
                    System.out.println("Number of creatures alive: "+ numStillAlive);
                    System.out.println(" ");
                    break;
                case 2:
                    System.out.println(crName[guy].showStatus());
                    System.out.println(" ");
                    break;
                case 3:
                    for (int m = 0; m < creaturecount-1;m++)
                        System.out.println(crName[n%creaturecount].showStatus());
                        n++;
                    System.out.println(" ");
                    break;
                case 4:
                    System.out.println("Your name is currently "+crName[guy].getName());
                    System.out.print("What is the new name: ");
                    crName[guy].setName(obj.next());
                    System.out.println(" ");
                    break;
                case 5:
                    System.out.println("You had "+crName[guy].getFoodUnits()+" food units, "
                    +crName[guy].getHealthUnits()+" health units and "+crName[guy].getFirePowerUnits()
                    +" firepower units before working.");
                    crName[guy].earnFood();
                    System.out.println("You now have "+crName[guy].getFoodUnits()+" food units, " +
                            crName[guy].getHealthUnits()+" health units and "+crName[guy].getFirePowerUnits() +
                            " firepower units after working.");
                    System.out.println(" ");
                    break;
                case 6:
                    System.out.print("Who would you like to attack (other than yourself/dead creatures)? :");
                    int target = obj.nextInt();
                    while (target > creaturecount && target < 1 && target != turn){
                        System.out.println("Creature does not exist/invalid target. Try again.");
                        System.out.println(" ");
                        System.out.print("Who would you like to attack (other than yourself/dead creatures)? :");
                    }
                    System.out.println("You are attacking "+ crName[target-1].getName()+"!");
                    int fight = rand.nextInt((2 - 0) + 1) + 0;
                    if (fight == 0 && crName[target-1].getFirePowerUnits() >= 2){
                        System.out.println("The tables turn! "+crName[guy].getName() +" is now under attack by "+
                                crName[target-1].getName() + "!");
                        crName[target-1].attacking(crName[guy]);
                        System.out.println(("You now have "+crName[guy].getFoodUnits()+" food units, " +
                                crName[guy].getHealthUnits()+" health units and "+crName[guy].getFirePowerUnits() + "firepower units"));
                    }
                    if (crName[guy].getFirePowerUnits() < 2){
                        System.out.println("You don't have enough firepower units! You are being attacked by "+
                                crName[target-1].getName()+"!");
                        crName[target-1].attacking(crName[guy]);
                        System.out.println(("You now have "+crName[guy].getFoodUnits()+" food units, " +
                                crName[guy].getHealthUnits()+" health units and "+crName[guy].getFirePowerUnits() + "firepower units"));
                    }
                    if (crName[guy].getFirePowerUnits() < 2 && crName[target-1].getFirePowerUnits() < 2){
                        System.out.println("You both have too little firepower to fight! Nothing happens.");
                        System.out.println(("You now have "+crName[guy].getFoodUnits()+" food units, " +
                                crName[guy].getHealthUnits()+" health units and "+crName[guy].getFirePowerUnits() + "firepower units"));

                    }
                    else{
                        System.out.println("You are attacking "+crName[target-1].getName()+"!");
                        crName[guy].attacking(crName[target-1]);
                        System.out.println(("You now have "+crName[guy].getFoodUnits()+" food units, " +
                                crName[guy].getHealthUnits()+" health units and "+crName[guy].getFirePowerUnits() + "firepower units"));
                    }


            }
        }

    }
}

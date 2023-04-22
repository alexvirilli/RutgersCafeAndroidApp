package softwaremethodology.rutgerscafe;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Donut.java - Donut class that handles all the logic for the Donut tab.
 *
 * @author Alex Virilli, Ryan Elizondo-Fallas
 */
public class Donut extends MenuItem {

    private String donutType; //must be yeast, cake, or donutHole
    private String flavor;
    private int quantity;

    public static final double yeastPrice = 1.59;
    public static final double cakePrice = 1.79;
    public static final double donutHolePrice = 0.39;

    public static final int defaultQuantity = 1;

    //getters
    public String getDonutType() {
        return donutType;
    }
    public String getFlavor(){
        return flavor;
    }
    public int getQuantity(){
        return quantity;
    }

    public Donut(String donutType){
        this.donutType = donutType;
        this.quantity = defaultQuantity;
        this.flavor = "no flavor selected";
    }
    public Donut(String donutType, int quantity){
        this.donutType = donutType;
        this.quantity = quantity;
        this.flavor = "no flavor selected";
    }
    //setter for flavor
    public void setFlavor(String flavor){
        this.flavor = flavor;
    }

    /**
     * itemPrice() - calculates the price of the donut based on the type and quantity
     * @return the price of the donut
     */
    public double itemPrice(){
        if(this.donutType.equalsIgnoreCase("yeast")){
            return yeastPrice * quantity;
        } else if(this.donutType.equalsIgnoreCase("cake")){
            return cakePrice * quantity;
        } else { //donutHole
            return donutHolePrice * quantity;
        }
    }

    /**
     * addQuantity() - adds the quantity to the donut
     * @param quantity
     */
    public void addQuantity(int quantity){
        this.quantity += quantity;
    }

    /**
     * removeQuantity() - removes the quantity from the donut
     * @param quantity
     */
    public void removeQuantity(int quantity){
        this.quantity -= quantity;
    }

    /**
     * toString() - returns a string representation of the donut
     * @return a string representation of the donut
     */
    @Override
    public String toString(){
        return "DONUT | TYPE: " + donutType + " FLAVOR: " + flavor + " QUANTITY: " + quantity + " PRICE: $" + String.format("%.2f",itemPrice());
    }

    /**
     * equals() - checks if two donuts are equal
     * @param obj
     * @return true if the donuts are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(!(obj instanceof Donut)) return false;
        Donut donut = (Donut) obj;
        return (this.donutType.equalsIgnoreCase(donut.getDonutType()) && this.flavor.equalsIgnoreCase(donut.getFlavor()));
    }

}


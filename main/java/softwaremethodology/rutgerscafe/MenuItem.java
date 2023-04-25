package softwaremethodology.rutgerscafe;

import java.io.Serializable;

/**
 * MenuItem.java - MenuItem class that handles adding and removing items from the order, and calculating the price.
 *
 * @author Alex Virilli, Ryan Elizondo-Fallas
 */
public abstract class MenuItem implements Serializable {

    private double price;

    public static final double defaultValue = 0;
    public MenuItem(){
        this.price = defaultValue;
    }

    public MenuItem(double price){
        this.price = price;
    }

    /**
     * itemPrice() - calculates the price of the item
     * @return
     */
    public abstract double itemPrice();

    /**
     * addQuantity() - adds the quantity to the existing quantity
     * @param quantity
     */
    public abstract void addQuantity(int quantity);

    /**
     * removeQuantity() - removes the quantity from the existing quantity
     * @param amount
     */
    public abstract void removeQuantity(int amount);

    public abstract String getItemName();

}

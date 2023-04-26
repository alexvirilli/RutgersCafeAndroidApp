package softwaremethodology.rutgerscafe;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Formatter;
import java.io.Serializable;

/**
 * Order.java - Order class that handles adding and removing items from the order, and getting the total price, subtotal, and tax.
 *
 * @author Alex Virilli, Ryan Elizondo-Fallas
 */
public class Order implements Serializable {

    private int orderNumber;
    private ArrayList<MenuItem> list;
    private boolean orderPlaced;

    public static final double njSalesTax = 0.06625;

    public Order(int orderNumber){
        this.orderNumber = orderNumber;
        this.list = new ArrayList<>();
        this.orderPlaced = false;
    }
    public Order(int orderNumber, ArrayList<MenuItem> list){
        this.orderNumber = orderNumber;
        this.list = list;
        this.orderPlaced = false;
    }

    //getters
    public boolean getOrderPlaced(){
        return this.orderPlaced;
    }
    public int getOrderNumber(){
        return this.orderNumber;
    }
    public ArrayList<MenuItem> getList(){
        return this.list;
    }

    /**
     * add() - adds a MenuItem to the order. If the item is already in the order, it will add the quantity to the existing item.
     * @param menuItem
     */
    public void add(MenuItem menuItem){
        if(list.contains(menuItem)){
            if(menuItem instanceof Donut){
                Donut donut = getDonut((Donut)menuItem);
                donut.addQuantity(((Donut) menuItem).getQuantity());
            } else {
                Coffee coffee = getCoffee((Coffee)menuItem);
                coffee.addQuantity(((Coffee) menuItem).getQuantity());
            }
        } else {
            list.add(menuItem);
        }
    }

    /**
     * remove() - removes a MenuItem from the order. If the item is already in the order, it will remove the quantity from the existing item.
     * @param menuItem
     */
    public void remove(MenuItem menuItem){
        if(menuItem instanceof Donut){
            Donut donutPtr = (Donut) menuItem;
            if(contains(donutPtr)){
                Donut listedDonut = getDonut(donutPtr);
                int quantityToRemove = ((Donut) menuItem).getQuantity();
                if(listedDonut.getQuantity() > quantityToRemove) listedDonut.removeQuantity(quantityToRemove);
                else if(listedDonut.getQuantity() == quantityToRemove) list.remove(menuItem);
            }
        }
        if(menuItem instanceof Coffee){
            Coffee coffeePtr = (Coffee) menuItem;
            if(contains(coffeePtr)){
                Coffee listedCoffee = getCoffee(coffeePtr);
                int quantityToRemove = ((Coffee) menuItem).getQuantity();
                if(listedCoffee.getQuantity() > quantityToRemove) listedCoffee.removeQuantity(quantityToRemove);
                else if(listedCoffee.getQuantity() == quantityToRemove) list.remove(menuItem);
            }
        }
    }

    public double getDonutCost(){
        double cost = 0;
        for (MenuItem menuItem : list) {
            if (menuItem instanceof Donut) {
                cost += menuItem.itemPrice();
            }
        }
        return cost;
    }

    public double getCoffeeCost(){
        double cost = 0;
        for(MenuItem menuItem : list){
            if(menuItem instanceof  Coffee){
                cost += menuItem.itemPrice();
            }
        }
        return cost;
    }

    public double getSubTotal(){
        double cost = 0;
        for(MenuItem menuItem : list){
            cost += menuItem.itemPrice();
        }
        return cost;
    }

    /**
     * contains() - checks if the order contains a specific donut
     * @param donut
     * @return true if the order contains the donut, false otherwise
     */
    public boolean contains(Donut donut){
        for(int i = 0; i < list.size(); i++){
            if(list.get(i) instanceof Donut){
                if(list.get(i).equals(donut)) return true;
            }
        }
        return false;
    }

    /**
     * contains() - checks if the order contains a specific coffee
     * @param coffee
     * @return true if the order contains the coffee, false otherwise
     */
    public boolean contains(Coffee coffee){
        for(int i = 0; i < list.size(); i++){
            if(list.get(i) instanceof Coffee){
                if(list.get(i).equals(coffee)) return true;
            }
        }
        return false;
    }

    public Donut getDonut(Donut donut){

        for(int i = 0; i < list.size(); i++){
            if(list.get(i) instanceof Donut){
                if(list.get(i).equals(donut)) return (Donut) list.get(i);
            }
        }
        return null;

    }

    public Coffee getCoffee(Coffee coffee){

        for(int i = 0; i < list.size(); i++){
            if(list.get(i) instanceof Coffee){
                if(list.get(i).equals(coffee)) return (Coffee) list.get(i);
            }
        }
        return null;

    }

    /**
     * getObservableList() - returns an observable list of the order
     * @return observableList
     */
    public ArrayList<String> getObservableList(){
        ArrayList<String> observableList = new ArrayList();
        for(int i = 0; i < list.size(); i++){
            observableList.add(list.get(i).toString());
        }
        return observableList;
    }

    public double getSalesTax(){
        return getSubTotal() * njSalesTax;
    }

    public double getTotal(){
        return getSubTotal() + getSalesTax();
    }


    public int countItemsInOrder(){
        int count = 0;
        for(MenuItem menuItem : list){
            count++;
        }
        return count;
    }

    //setter
    public void setOrderPlaced(boolean orderPlaced){
        this.orderPlaced = orderPlaced;
    }

}

package softwaremethodology.rutgerscafe;


import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * OrderArchive.java - OrderArchive class that handles the list of orders, and the current order.
 *
 * @author Alex Virilli, Ryan Elizondo-Fallas
 */
public class OrderArchive implements Serializable {

    private ArrayList<Order> orders;
    private int current;
    private int newOrder;

    public static final int defaultIndex = 0;

    //constructor
    public OrderArchive(){
        this.orders = new ArrayList();
        this.current = defaultIndex;
        this.newOrder = defaultIndex;
        Order order = new Order(current);
        orders.add(order);
    }

    //getters
    public int getCurrent(){
        return this.current;
    }
    public ArrayList<Order> getArchive(){
        return this.orders;
    }

    /**
     * decrementCurrent() - decrements the current order number
     */
    public void decrementCurrent(){
        if(current > 0 ) this.current--;
    }

    /**
     * placeOrder() - places the order, keeps reference of the order, and starts a new order
     */
    public void placeOrder(){
        orders.get(current).setOrderPlaced(true);
        this.current++;
        this.newOrder++;
        Order order = new Order(newOrder);
        orders.add(order);
    }

    public Order getOrder(int orderNumber){
        for(Order order : orders){
            if(order.getOrderNumber() == orderNumber) return order;
        }
        return null;
    }

    /**
     * displayOrders() - displays the orders in the ListView
     * @param listView
     */
    public void displayOrders(ListView listView){
        ArrayList<String> arrayList = new ArrayList<String>();
        for(Order order : orders) {
            if(order.getList().size() > 0 && order.getOrderPlaced()) {
                arrayList.add("order no. " + order.getOrderNumber());
                arrayList.addAll(order.getObservableList());
                arrayList.add("subtotal: $" + String.format("%.2f", order.getSubTotal()) + " sales tax: $" + String.format("%.2f", order.getSalesTax()) + " total: $" + String.format("%.2f", order.getTotal()));
            }
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(listView.getContext(), android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
    }
    /**
     * printwriteOrders() - prints the orders to the file
     * @param output
     */
    public void printwriteOrders(PrintWriter output){
        for(Order order : orders) {
            if(order.getList().size() > 0 && order.getOrderPlaced()) {
                output.println("order no. " + order.getOrderNumber());
                order.printwriteOrder(output);
                output.println("subtotal: $" + String.format("%.2f", order.getSubTotal()) + " sales tax: $" + String.format("%.2f", order.getSalesTax()) + " total: $" + String.format("%.2f", order.getTotal()));
            }
        }

    }
    public static final int DEFAULT_STORE_ORDER_INDEX = -1;
    public int countOrders(){
        int count = DEFAULT_STORE_ORDER_INDEX;
        for(Order order : orders){
            count++;
        }
        return count;
    }

}


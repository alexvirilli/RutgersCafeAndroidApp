package softwaremethodology.rutgerscafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BasketActivity extends AppCompatActivity {

    private OrderArchive orderArchive;
    private GlobalInformation info;
    private TextView subtotal, salesTax, total;
    private ListView orderList;
    private ArrayAdapter<String> arrayAdapter;
    public static final double njSalesTax = 0.06625;

    private void updateCostFields(){
        int archiveIndex = orderArchive.getCurrent();
        subtotal.setText("Subtotal: $"+String.format("%.2f",orderArchive.getArchive().get(archiveIndex).getSubTotal()));
        double calculatedTax = orderArchive.getArchive().get(archiveIndex).getSubTotal() * njSalesTax;
        salesTax.setText("Sales Tax: $"+String.format("%.2f",calculatedTax));
        double calculatedTotal = orderArchive.getArchive().get(archiveIndex).getSubTotal() +  calculatedTax;
        total.setText("Total: $"+String.format("%.2f",calculatedTotal));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        subtotal = findViewById(R.id.subtotal);
        salesTax = findViewById(R.id.salesTax);
        total = findViewById(R.id.total);
        orderList = findViewById(R.id.currentOrder);
        info = (GlobalInformation) getApplicationContext();
        orderArchive = info.getOrderArchive();

        int index = orderArchive.getCurrent();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,orderArchive.getArchive().get(index).getObservableList());
        orderList.setAdapter(arrayAdapter);
        //orderList.(orderArchive.getArchive().get(index).getObservableList());
        updateCostFields();
        orderList.setSelection(0);
        //orderList.getSelectionModel().select(0);
    }

    public void basketPrevious(View view) {
        Intent intent = new Intent(this, ActivityChooser.class);
        startActivity(intent);
    }

    public void removeItem(View view) {
        if(!(orderList.getItemAtPosition(0)==null)) {
            int index = orderList.getSelectedItemPosition();
            if(index != -1) {
                //System.out.println(index);
                //int index = orderList.getSelectionModel().getSelectedIndex();
                int archiveIndex = orderArchive.getCurrent();
                orderArchive.getArchive().get(archiveIndex).remove(orderArchive.getArchive().get(archiveIndex).getList().get(index));
                arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, orderArchive.getArchive().get(archiveIndex).getObservableList());
                orderList.setAdapter(arrayAdapter);
                updateCostFields();
            } else {
                String message = "Please select an item";
                Toast toast = Toast.makeText(this,message,Toast.LENGTH_SHORT);
                toast.show();
            }
        } else {
            String message = "Basket empty!";
            Toast toast = Toast.makeText(this,message,Toast.LENGTH_SHORT);
            toast.show();
        }
        info.setOrderArchive(orderArchive);
    }

    public void placeOrder(View view) {
        if(!(orderList.getItemAtPosition(0)==null)) {
            orderArchive.placeOrder();
            int archiveIndex = orderArchive.getCurrent();
            arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,orderArchive.getArchive().get(archiveIndex).getObservableList());
            orderList.setAdapter(arrayAdapter);
            //orderList.getItems().clear();
            subtotal.setText("Subtotal: $");
            salesTax.setText("Sales Tax: $");
            total.setText("Total: $");
            Toast toast = Toast.makeText(this,"Order placed!",Toast.LENGTH_SHORT); toast.show();
        } else {
            Toast toast = Toast.makeText(this,"Basket empty!",Toast.LENGTH_SHORT); toast.show();
        }
        info.setOrderArchive(orderArchive);
    }
}
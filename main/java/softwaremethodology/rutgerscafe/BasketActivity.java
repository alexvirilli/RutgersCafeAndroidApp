package softwaremethodology.rutgerscafe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
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
    public static final int NOT_FOUND = -1;
    private int itemSelectedPosition = -1;

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
        updateCostFields();
        orderList.setSelection(0);
        orderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Object item = adapterView.getItemAtPosition(position);
                itemSelectedPosition = position;
            }
        });
    }

    public void basketPrevious(View view) {
        Intent intent = new Intent(this, ActivityChooser.class);
        startActivity(intent);
    }



    public void removeItem(View view) {
        int archiveIndex = orderArchive.getCurrent();
        if(orderArchive.getArchive().get(archiveIndex).countItemsInOrder() != 0) {
            int index = itemSelectedPosition;
            if(index != NOT_FOUND) {
                AlertDialog.Builder alert = new AlertDialog.Builder(this); alert.setTitle("Remove Item");
                alert.setMessage("Are you sure you want to delete " + orderArchive.getArchive().get(archiveIndex).getList().get(index) + "?");
                alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        orderArchive.getArchive().get(archiveIndex).remove(orderArchive.getArchive().get(archiveIndex).getList().get(index));
                        arrayAdapter = new ArrayAdapter<>(alert.getContext(), android.R.layout.simple_list_item_1, orderArchive.getArchive().get(archiveIndex).getObservableList());
                        orderList.setAdapter(arrayAdapter);
                        updateCostFields();
                    }
                }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which){
                        Toast toast = Toast.makeText(alert.getContext(), "Canceled",Toast.LENGTH_SHORT); toast.show();
                    }
                });
                AlertDialog dialog = alert.create(); dialog.show();
            } else {
                Toast toast = Toast.makeText(this,R.string.unselected_item_error_message,Toast.LENGTH_SHORT); toast.show(); }
        } else {
            Toast toast = Toast.makeText(this,R.string.basket_empty_message,Toast.LENGTH_SHORT); toast.show(); }
        info.setOrderArchive(orderArchive); itemSelectedPosition = NOT_FOUND;
    }

    public void placeOrder(View view) {
        int archiveIndex = orderArchive.getCurrent();
        if(orderArchive.getArchive().get(archiveIndex).countItemsInOrder() != 0) {
            orderArchive.placeOrder();
            archiveIndex = orderArchive.getCurrent();
            arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,orderArchive.getArchive().get(archiveIndex).getObservableList());
            orderList.setAdapter(arrayAdapter);
            subtotal.setText("Subtotal: $");
            salesTax.setText("Sales Tax: $");
            total.setText("Total: $");
            Toast toast = Toast.makeText(this,R.string.order_placed_message,Toast.LENGTH_SHORT); toast.show();
        } else {
            Toast toast = Toast.makeText(this,R.string.basket_empty_message,Toast.LENGTH_SHORT); toast.show();
        }
        info.setOrderArchive(orderArchive);
    }
}
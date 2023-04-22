package softwaremethodology.rutgerscafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class StoreOrdersActivity extends AppCompatActivity {

    private GlobalInformation info;
    private OrderArchive orderArchive;
    private ListView orders;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_orders);
        info = (GlobalInformation) getApplicationContext();
        orderArchive = info.getOrderArchive();
        orders = findViewById(R.id.storeOrders);
        orderArchive.displayOrders(orders);
    }

    public void ordersPrevious(View view) {
        Intent intent = new Intent(this, ActivityChooser.class);
        startActivity(intent);
    }


    public void deleteOrder(View view) {
        if (orders.getSelectedItemPosition() != -1) {
            if (!(orders.getItemAtPosition(0) == null)) {
                String orderToCancel = orders.getSelectedItem().toString();
                if (orderToCancel.contains("no.")) {
                    int indexOffset = 2;
                    int orderNumber = Integer.parseInt(orderToCancel.substring(orderToCancel.indexOf(". ") + indexOffset));
                    Order order = orderArchive.getOrder(orderNumber);
                    orderArchive.getArchive().remove(order);


                    //orders.getItems().clear();

                    orderArchive.displayOrders(orders);
                    orderArchive.decrementCurrent();
                    Toast toast = Toast.makeText(this, orderToCancel + " cancelled", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(this, "Select an item with \"order no. x\"", Toast.LENGTH_SHORT);
                    toast.show();
                }
            } else {
                Toast toast = Toast.makeText(this, "No orders!", Toast.LENGTH_SHORT);
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(this,"Please select an item",Toast.LENGTH_SHORT); toast.show();
        }
    }
}
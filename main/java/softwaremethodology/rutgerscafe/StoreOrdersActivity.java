package softwaremethodology.rutgerscafe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class StoreOrdersActivity extends AppCompatActivity {

    private GlobalInformation info;
    private OrderArchive orderArchive;
    private ListView orders;
    private ArrayAdapter<String> arrayAdapter;
    private int itemSelectedPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_orders);
        info = (GlobalInformation) getApplicationContext();
        orderArchive = info.getOrderArchive();
        orders = findViewById(R.id.storeOrders);
        orderArchive.displayOrders(orders);

        orders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                itemSelectedPosition = position;
            }
        });
    }

    public void ordersPrevious(View view) {
        Intent intent = new Intent(this, ActivityChooser.class);
        startActivity(intent);
    }

    private void removeOrder(){
        String orderToCancel = orders.getItemAtPosition(itemSelectedPosition).toString();
        int indexOffset = 2;
        int orderNumber = Integer.parseInt(orderToCancel.substring(orderToCancel.indexOf(". ") + indexOffset));
        Order order = orderArchive.getOrder(orderNumber);
        orderArchive.getArchive().remove(order);
        orderArchive.displayOrders(orders);
        orderArchive.decrementCurrent();
        Toast toast = Toast.makeText(this, orderToCancel + " cancelled", Toast.LENGTH_SHORT);
        toast.show();
    }


    public void deleteOrder(View view) {
        if (itemSelectedPosition != -1) {
            if (orderArchive.countOrders() != 0) {
                String orderToCancel = orders.getItemAtPosition(itemSelectedPosition).toString();
                if (orderToCancel.contains("no.")) {
                new AlertDialog.Builder(this)
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete " + orderToCancel + "?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                removeOrder();
                                itemSelectedPosition = 0;
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                } else {
                    Toast toast = Toast.makeText(this, R.string.order_no_error_message, Toast.LENGTH_SHORT); toast.show();
                }
            } else {
                Toast toast = Toast.makeText(this, R.string.no_orders_error_message, Toast.LENGTH_SHORT); toast.show();
            }
        } else {
            Toast toast = Toast.makeText(this,R.string.unselected_item_error_message,Toast.LENGTH_SHORT); toast.show();
        }
    }
}
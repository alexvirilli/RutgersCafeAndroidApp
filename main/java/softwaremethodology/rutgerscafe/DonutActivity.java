package softwaremethodology.rutgerscafe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class DonutActivity extends AppCompatActivity {

    private GlobalInformation info;
    private OrderArchive orderArchive;
    private RecyclerView donutView;


    private ArrayAdapter<String> adapter;
    private ArrayList<Donut> itemList = new ArrayList<>();
    private EditText quantityDonutsField;
    private TextView runningTotalDonuts;


    private String donutTypes[] = {"Yeast","Cake","Donut Holes"};
    private String yeastFlavors[] = {"Plain","Chocolate","Vanilla","Powder","Strawberry","Jelly"};
    private String cakeFlavors[] = {"Vanilla","Chocolate","Red Velvet"};
    private String donutHoleFlavors[] = {"Glazed","Chocolate","Powdered"};

    private String donuts[] = {"Yeast-Plain", "Yeast-Chocolate", "Yeast-Vanilla", "Yeast-Powder",
        "Yeast-Strawberry", "Yeast-Jelly", "Cake-Vanilla", "Cake-Chocolate", "Cake-Red Velvet",
        "Donut Holes-Glazed","Donut Holes-Chocolate", "Donut Holes-Powdered"};

    private int donutImages[] = {R.drawable.yeastdonut, R.drawable.chocolateyeast,R.drawable.vanillayeast,R.drawable.powderyeast,
        R.drawable.strawberryyeast,R.drawable.yeastjelly,R.drawable.vanillacake,R.drawable.chocolatecake,R.drawable.redvelvet,
        R.drawable.glazedholes,R.drawable.chocolateholes,R.drawable.powderedholes};

    private void setUpMenuItems(){
        for(int i = 0; i < donuts.length; i++){
            String splitArr[] = donuts[i].split("-");
            Donut donut = new Donut(splitArr[0],splitArr[1],donutImages[i]);
            itemList.add(donut);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donut);
        //quantityDonutsField = findViewById(R.id.quantityField);
        runningTotalDonuts = (TextView) findViewById(R.id.donutRunningTotal);
        info = (GlobalInformation) getApplicationContext();
        orderArchive = info.getOrderArchive();
        donutView = (RecyclerView) findViewById(R.id.donutView);
        donutView = findViewById(R.id.donutView);
        setUpMenuItems();
        ItemsAdapter adapter = new ItemsAdapter(this,itemList,null);
        donutView.setAdapter(adapter);
        donutView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setUpdateRunningTotal(new ItemsAdapter.UpdateRunningTotal() {
            @Override
            public void onUpdate() {
                int archiveIndex = orderArchive.getCurrent();
                String updatedTotal = "Running Total: $" + String.format("%.2f", orderArchive.getArchive().get(archiveIndex).getDonutCost());
                runningTotalDonuts.setText(updatedTotal);
            }
        });

    }



    public void previous(View view) {
        Intent intent = new Intent(this, ActivityChooser.class);
        //intent.putExtra("donutToChooser",orderArchive);
        startActivity(intent);
    }

    private boolean isInt(String str){
        try{
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException ex){
            return false;
        }
    }


    private void addToOrder(Donut donutPtr, Order order){
        if(order.getList().contains(donutPtr)){
            Donut donut = order.getDonut(donutPtr);
            donut.addQuantity(donutPtr.getQuantity());
        } else {
            order.getList().add(donutPtr);
        }
    }

    /*
    private void add(){
        int archiveIndex = orderArchive.getCurrent();
        //String donutType = getSelectedDonutType();
        //String flavor = getSelectedFlavor();
        if(flavor != null) {
            if (isInt(quantityDonutsField.getText().toString()) && Integer.parseInt(quantityDonutsField.getText().toString()) > 0) {
                int quantityDonuts = Integer.parseInt(quantityDonutsField.getText().toString());
                Donut donut = new Donut(donutType, quantityDonuts);
                donut.setFlavor(flavor);
                addToOrder(donut, orderArchive.getArchive().get(archiveIndex));
                String updatedTotal = "Running Total: $" + String.format("%.2f", orderArchive.getArchive().get(archiveIndex).getDonutCost());
                runningTotalDonuts.setText(updatedTotal);
                quantityDonutsField.setText("0");
                Toast toast = Toast.makeText(this, "Added to order",Toast.LENGTH_SHORT); toast.show();
            } else {
                Toast toast = Toast.makeText(this,"Please enter an integer quantity",Toast.LENGTH_SHORT); toast.show();
            }
        }
    }

     */

    public void donutAdd(View view) {
        //add();
        info.setOrderArchive(orderArchive);
    }

    private void removeFromOrder(Donut donutPtr, Order order){
        if(order.contains(donutPtr)){
            Donut listedDonut = order.getDonut(donutPtr);
            int quantityToRemove = donutPtr.getQuantity();
            if(listedDonut.getQuantity() > quantityToRemove) listedDonut.removeQuantity(quantityToRemove);
            else if(listedDonut.getQuantity() == quantityToRemove) order.getList().remove(donutPtr);
            else { Toast toast = Toast.makeText(this,"Quantity to remove exceeds quantity in order",Toast.LENGTH_SHORT); toast.show(); }
        } else {
            Toast toast = Toast.makeText(this, "Donut is not in order",Toast.LENGTH_SHORT); toast.show();
        }
    }

    /*
    public void removeDonut(View view) {
        int archiveIndex = orderArchive.getCurrent();
        String donutType = getSelectedDonutType();
        String flavor = getSelectedFlavor();
        if(isInt(quantityDonutsField.getText().toString())){
            int quantityDonuts = Integer.parseInt(quantityDonutsField.getText().toString());
            Donut donut = new Donut(donutType,quantityDonuts);
            donut.setFlavor(flavor);
            removeFromOrder(donut, orderArchive.getArchive().get(archiveIndex));
            runningTotalDonuts.setText("Running Total: $" + String.format("%.2f", orderArchive.getArchive().get(archiveIndex).getDonutCost()));
            quantityDonutsField.setText("0");
        }
        info.setOrderArchive(orderArchive);
    }

     */



}
package softwaremethodology.rutgerscafe;

import androidx.appcompat.app.AppCompatActivity;

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

public class DonutActivity extends AppCompatActivity {

    private GlobalInformation info;
    private OrderArchive orderArchive;

    private Spinner donutFlavor;
    private Spinner donutType;
    private ArrayAdapter<String> adapter;
    private EditText quantityDonutsField;
    private TextView runningTotalDonuts;
    private ImageView donutImage;

    private String donutTypes[] = {"Yeast","Cake","Donut Holes"};
    private String yeastFlavors[] = {"Plain","Chocolate","Vanilla","Powder","Strawberry","Jelly"};
    private String cakeFlavors[] = {"Vanilla","Chocolate","Red Velvet"};
    private String donutHoleFlavors[] = {"Glazed","Chocolate","Powdered"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donut);

        quantityDonutsField = findViewById(R.id.quantityField);
        runningTotalDonuts = (TextView) findViewById(R.id.donutRunningTotal);
        donutType = findViewById(R.id.donutType);
        donutFlavor = findViewById(R.id.donutFlavor);
        donutImage = findViewById(R.id.donutImageView);
        adapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, donutTypes);
        donutType.setAdapter(adapter);
        adapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,yeastFlavors);
        donutFlavor.setAdapter(adapter);

        info = (GlobalInformation) getApplicationContext();
        orderArchive = info.getOrderArchive();

        donutType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getSelectedItem().equals("Yeast")){
                    adapter = new ArrayAdapter<String>(adapter.getContext(), androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, yeastFlavors);
                    donutFlavor.setAdapter(adapter);
                    donutImage.setImageResource(R.drawable.yeastdonut);
                } else if (adapterView.getSelectedItem().equals("Cake")){
                    adapter = new ArrayAdapter<String>(adapter.getContext(), androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, cakeFlavors);
                    donutFlavor.setAdapter(adapter);
                    donutImage.setImageResource(R.drawable.cakedonut);
                } else if (adapterView.getSelectedItem().equals("Donut Holes")){
                    adapter = new ArrayAdapter<String>(adapter.getContext(), androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, donutHoleFlavors);
                    donutFlavor.setAdapter(adapter);
                    donutImage.setImageResource(R.drawable.donutholes);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //nothing
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

    private String getSelectedDonutType(){
        return donutType.getSelectedItem().toString();
    }

    private String getSelectedFlavor(){
        return donutFlavor.getSelectedItem().toString();
    }

    private void addToOrder(Donut donutPtr, Order order){
        if(order.getList().contains(donutPtr)){
            Donut donut = order.getDonut(donutPtr);
            donut.addQuantity(donutPtr.getQuantity());
        } else {
            order.getList().add(donutPtr);
        }
    }

    private void add(){
        int archiveIndex = orderArchive.getCurrent();
        String donutType = getSelectedDonutType();
        String flavor = getSelectedFlavor();
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

    public void donutAdd(View view) {
        add();
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
}
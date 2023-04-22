package softwaremethodology.rutgerscafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.CheckBox;
import android.widget.Toast;


public class CoffeeActivity extends AppCompatActivity {

    private GlobalInformation info;
    private OrderArchive orderArchive;
    private CheckBox irishCream, frenchVanilla, mocha, caramel, sweetCream;
    private RadioButton shortSize, tallSize, grandeSize, ventiSize;
    private TextView runningTotal;
    private Spinner quantitySpinner;
    private String[] coffeeQuantities = {"1","2","3","4","5"};

    public static final double shortSizePrice = 1.89;
    public static final double tallSizePrice = 2.29;
    public static final double grandeSizePrice = 2.69;
    public static final double ventiSizePrice = 3.09;
    public static final double addOnPrice = 0.30;

    private double subTotal = 0;
    private double basePrice = 1.89;
    public static final int SMALL = 0;
    public static final int TALL = 1;
    public static final int GRANDE = 2;
    public static final int VENTI = 3;
    public static final int NOT_FOUND = -1;

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee);
        quantitySpinner = findViewById(R.id.coffeeQuantitySpinner);
        adapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, coffeeQuantities);
        quantitySpinner.setAdapter(adapter);
        orderArchive = (OrderArchive) getIntent().getSerializableExtra("chooserToCoffee");
        sweetCream = findViewById(R.id.sweetCream);
        irishCream = findViewById(R.id.irishCream);
        frenchVanilla = findViewById(R.id.frenchVanilla);
        mocha = findViewById(R.id.mocha);
        caramel = findViewById(R.id.caramel);
        shortSize = findViewById(R.id.shortSize);
        tallSize = findViewById(R.id.tallSize);
        grandeSize = findViewById(R.id.tallSize);
        ventiSize = findViewById(R.id.ventiSize);
        runningTotal = findViewById(R.id.coffeeRunningTotal);
        shortSize.setChecked(true);

        info = (GlobalInformation) getApplicationContext();
        orderArchive = info.getOrderArchive();

        runningTotal.setText("Running Total: $" + String.format("%.2f",subTotal + basePrice));
    }

    public void previousCoffee(View view) {
        Intent intent = new Intent(this, ActivityChooser.class);
        startActivity(intent);
    }

    public void calculateTotal(){

    }

    private int getSelectedSize(){
        if(shortSize.isChecked()){
            return SMALL;
        }
        else if(tallSize.isChecked()){
            return TALL;
        }
        else if(grandeSize.isChecked()){
            return GRANDE;
        }
        else if(ventiSize.isChecked()){
            return VENTI;
        }
        else{
            return NOT_FOUND;
        }
    }

    private void reset(){
        shortSize.setChecked(true);
        tallSize.setChecked(false);
        grandeSize.setChecked(false);
        ventiSize.setChecked(false);
        sweetCream.setChecked(false);
        mocha.setChecked(false);
        caramel.setChecked(false);
        irishCream.setChecked(false);
        frenchVanilla.setChecked(false);
        quantitySpinner.setSelection(0);
        basePrice = 1.89;
        runningTotal.setText("Running Total: $" + String.format("%.2f",subTotal + basePrice));
    }



    public void checkButton(View view) {
        boolean checked = ((RadioButton) view).isChecked();
}

    private void calculateBasePrice(){
        int size = getSelectedSize();
        switch(size){
            case SMALL:
                basePrice = shortSizePrice;
                break;
            case TALL:
                basePrice = tallSizePrice;
                break;
            case GRANDE:
                basePrice = grandeSizePrice;
                break;
            case VENTI:
                basePrice = ventiSizePrice;
                break;
            default:
                basePrice = 0;
                break;
        }

    }

    public void checkCheckBox(View view) {
        boolean checked = ((CheckBox) view).isChecked();
    }

    public void sweetCreamSelect(View view) {
        if(!sweetCream.isChecked()){
            basePrice -= addOnPrice;
        }
        else{
            basePrice += addOnPrice;
        }
        runningTotal.setText("Running Total: $" + String.format("%.2f",subTotal + basePrice));
    }

    public void frenchVanillaSelect(View view) {
        if(!frenchVanilla.isChecked()){
            basePrice -= addOnPrice;
        }
        else{
            basePrice += addOnPrice;
        }
        runningTotal.setText("Running Total: $" + String.format("%.2f",subTotal + basePrice));
    }

    public void irishCreamSelect(View view) {
        if(!irishCream.isChecked()){
            basePrice -= addOnPrice;
        }
        else{
            basePrice += addOnPrice;
        }
        runningTotal.setText("Running Total: $" + String.format("%.2f",subTotal + basePrice));
    }

    public void caramelSelect(View view) {
        if(!caramel.isChecked()){
            basePrice -= addOnPrice;
        }
        else{
            basePrice += addOnPrice;
        }
        runningTotal.setText("Running Total: $" + String.format("%.2f",subTotal + basePrice));
    }


    public void mochaSelect(View view) {
        if(!mocha.isChecked()){
            basePrice -= addOnPrice;
        }
        else{
            basePrice += addOnPrice;
        }
        runningTotal.setText("Running Total: $ " + String.format("%.2f",subTotal + basePrice));
    }

    public void shortSelect(View view) {
        basePrice = 1.89;
        runningTotal.setText("Running Total: $" + String.format("%.2f",subTotal + basePrice));
    }

    public void tallSelect(View view) {
        basePrice = 2.29;
        runningTotal.setText("Running Total: $" + String.format("%.2f",subTotal + basePrice));
    }

    public void grandeSelect(View view) {
        basePrice = 2.69;
        runningTotal.setText("Running Total: $" + String.format("%.2f",subTotal + basePrice));
    }

    public void ventiSelect(View view) {
        basePrice = 3.09;
        runningTotal.setText("Running Total: $" + String.format("%.2f",subTotal + basePrice));
    }

    private boolean isInt(String str){
        try{
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException ex){
            return false;
        }
    }

    private void addToOrder(Coffee coffeePtr, Order order){
        if(order.getList().contains(coffeePtr)){
            Coffee coffee = order.getCoffee(coffeePtr);
            coffee.addQuantity(coffeePtr.getQuantity());
        } else {
            order.getList().add(coffeePtr);
        }
    }

    private void add(){
        int archiveIndex = orderArchive.getCurrent();
        if(getSelectedSize() != NOT_FOUND){
            int size = getSelectedSize();
            if(isInt(quantitySpinner.getSelectedItem().toString())) {
                int coffeeQuantity = Integer.parseInt(quantitySpinner.getSelectedItem().toString());
                if(coffeeQuantity > 0 && coffeeQuantity < 6) {
                    Coffee coffee = new Coffee(size);
                    coffee.setQuantity(coffeeQuantity);
                    if (sweetCream.isSelected()) coffee.addIn("sweet cream");
                    if (frenchVanilla.isSelected()) coffee.addIn("french vanilla");
                    if (irishCream.isSelected()) coffee.addIn("irish cream");
                    if (caramel.isSelected()) coffee.addIn("caramel");
                    if (mocha.isSelected()) coffee.addIn("mocha");
                    addToOrder(coffee, orderArchive.getArchive().get(archiveIndex));
                    reset();
                }
            }
        }
    }

    public void coffeeAdd(View view) {
        add();
        info.setOrderArchive(orderArchive);
        Toast toast = Toast.makeText(this,"Coffee added to order", Toast.LENGTH_SHORT); toast.show();
        reset();
    }




}
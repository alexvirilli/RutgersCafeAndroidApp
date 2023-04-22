package softwaremethodology.rutgerscafe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import softwaremethodology.rutgerscafe.R;

public class ActivityChooser extends AppCompatActivity {

    private Button donutButton, coffeeButton, basketButton, storeOrdersButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);
        donutButton = findViewById(R.id.basketButton);
        coffeeButton = findViewById(R.id.coffeeButton);
        basketButton = findViewById(R.id.basketButton);
        storeOrdersButton = findViewById(R.id.storeOrdersButton);
    }

    public void donutClick(View view) {
        Intent intent = new Intent(this, DonutActivity.class);
        //intent.putExtra("chooserToDonut",orderArchive);
        startActivity(intent);
    }

    public void coffeeButton(View view) {
        Intent intent = new Intent(this, CoffeeActivity.class);
        //intent.putExtra("chooserToCoffee",orderArchive);
        startActivity(intent);
    }

    public void basketOpen(View view) {
        Intent intent = new Intent(this, BasketActivity.class);
        //intent.putExtra("chooserToBasket",orderArchive);
        startActivity(intent);
    }

    public void openStoreOrders(View view) {
        Intent intent = new Intent(this, StoreOrdersActivity.class);
        //intent.putExtra("chooserToStoreOrders",orderArchive);
        startActivity(intent);
    }
}
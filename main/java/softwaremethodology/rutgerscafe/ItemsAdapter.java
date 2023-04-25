package softwaremethodology.rutgerscafe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * This is an Adapter class to be used to instantiate an adapter for the RecyclerView.
 * Must extend RecyclerView.Adapter, which will enforce you to implement 3 methods:
 *      1. onCreateViewHolder, 2. onBindViewHolder, and 3. getItemCount
 *
 * You must use the data type <thisClassName.yourHolderName>, in this example
 * <ItemAdapter.ItemHolder>. This will enforce you to define a constructor for the
 * ItemAdapter and an inner class ItemsHolder (a static class)
 * The ItemsHolder class must extend RecyclerView.ViewHolder. In the constructor of this class,
 * you do something similar to the onCreate() method in an Activity.
 */
class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsHolder>{
    private Context context; //need the context to inflate the layout
    private ArrayList<Donut> items; //need the data binding to each row of RecyclerView
    private GlobalInformation info;
    private static UpdateRunningTotal updateRunningTotal;

    public interface UpdateRunningTotal{
        void onUpdate();
    }

    public void setUpdateRunningTotal(UpdateRunningTotal updateRunningTotal){
        this.updateRunningTotal = updateRunningTotal;
    }


    public ItemsAdapter(Context context, ArrayList<Donut> items, UpdateRunningTotal updateRunningTotal) {
        this.context = context;
        this.items = items;
        this.updateRunningTotal = updateRunningTotal;
    }


    /**
     * This method will inflate the row layout for the items in the RecyclerView
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate the row layout for the items
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_view, parent, false);
        return new ItemsHolder(view);
    }

    /**
     * Assign data values for each row according to their "position" (index) when the item becomes
     * visible on the screen.
     * @param holder the instance of ItemsHolder
     * @param position the index of the item in the list of items
     */
    @Override
    public void onBindViewHolder(@NonNull ItemsHolder holder, int position) {
        //assign values for each row
        holder.tv_name.setText(items.get(position).getItemName());
        double price = items.get(position).itemPrice();
        holder.tv_price.setText(""+price);
        holder.im_item.setImageResource(items.get(position).getImage());
    }

    /**
     * Get the number of items in the ArrayList.
     * @return the number of items in the list.
     */
    @Override
    public int getItemCount() {
        return items.size(); //number of MenuItem in the array list.
    }

    /**
     * Get the views from the row layout file, similar to the onCreate() method.
     */
    public static class ItemsHolder extends RecyclerView.ViewHolder {
        private TextView tv_name, tv_price, runningTotal;
        private ImageView im_item;
        private Button btn_add, btn_remove;
        private ConstraintLayout parentLayout; //this is the row layout
        private OrderArchive orderArchive;
        private GlobalInformation info;
        private EditText quantityDonutsField;
        //protected UpdateRunningTotal updateRunningTotal;


        private boolean isInt(String str){
            try{
                Integer.parseInt(str);
                return true;
            } catch (NumberFormatException ex){
                return false;
            }
        }

        private String getSelectedDonutType(){
            String strArr[] = tv_name.getText().toString().split(" ");
            return strArr[0];
        }

        private String getSelectedDonutFlavor(){
            String strArr[] = tv_name.getText().toString().split(" ");
            return strArr[1];
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
            String flavor = getSelectedDonutFlavor();
            if(flavor != null) {
                if (isInt(quantityDonutsField.getText().toString()) && Integer.parseInt(quantityDonutsField.getText().toString()) > 0) {
                    int quantityDonuts = Integer.parseInt(quantityDonutsField.getText().toString());
                    Donut donut = new Donut(donutType, quantityDonuts);
                    donut.setFlavor(flavor);
                    addToOrder(donut, orderArchive.getArchive().get(archiveIndex));
//                    String updatedTotal = "Running Total: $" + String.format("%.2f", orderArchive.getArchive().get(archiveIndex).getDonutCost());
//                    runningTotal.setText(updatedTotal);
                    quantityDonutsField.setText("");
                    Toast toast = Toast.makeText(itemView.getContext(), "Added to order",Toast.LENGTH_SHORT); toast.show();
                } else {
                    Toast toast = Toast.makeText(itemView.getContext(),"Please enter an integer quantity",Toast.LENGTH_SHORT); toast.show();
                }
            }
        }
        private void removeFromOrder(Donut donutPtr, Order order){
            if(order.contains(donutPtr)){
                Donut listedDonut = order.getDonut(donutPtr);
                int quantityToRemove = donutPtr.getQuantity();
                if(listedDonut.getQuantity() > quantityToRemove) listedDonut.removeQuantity(quantityToRemove);
                else if(listedDonut.getQuantity() == quantityToRemove) order.getList().remove(donutPtr);
                else { Toast toast = Toast.makeText(itemView.getContext(), "Quantity to remove exceeds quantity in order",Toast.LENGTH_SHORT); toast.show(); }
            } else {
                Toast toast = Toast.makeText(itemView.getContext(), "Donut is not in order",Toast.LENGTH_SHORT); toast.show();
            }
        }

        public void removeDonut() {
            int archiveIndex = orderArchive.getCurrent();
            String donutType = getSelectedDonutType();
            String flavor = getSelectedDonutFlavor();
            if(isInt(quantityDonutsField.getText().toString())){
                int quantityDonuts = Integer.parseInt(quantityDonutsField.getText().toString());
                Donut donut = new Donut(donutType,quantityDonuts);
                donut.setFlavor(flavor);
                removeFromOrder(donut, orderArchive.getArchive().get(archiveIndex));
                //runningTotal.setText("Running Total: $" + String.format("%.2f", orderArchive.getArchive().get(archiveIndex).getDonutCost()));
                quantityDonutsField.setText("");
            }
            info.setOrderArchive(orderArchive);

        }

        public ItemsHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_flavor);
            tv_price = itemView.findViewById(R.id.tv_price);
            im_item = itemView.findViewById(R.id.im_item);
            btn_add = itemView.findViewById(R.id.btn_add);
            btn_remove = itemView.findViewById(R.id.btn_remove);
            parentLayout = itemView.findViewById(R.id.rowLayout);
            //runningTotal = itemView.findViewById(R.id.donutRunningTotal);
            quantityDonutsField = itemView.findViewById(R.id.recyclerQuantity);
            info = (GlobalInformation) itemView.getContext().getApplicationContext();
            orderArchive = info.getOrderArchive();

            setAddButtonOnClick(itemView); //register the onClicklistener for the button on each row.
            setRemoveButtonOnClick(itemView);

            /* set onClickListener for the row layout,
             * clicking on a row will navigate to another Activity
             */
            parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent intent = new Intent(itemView.getContext(), ItemSelectedActivity.class);
//                    intent.putExtra("ITEM", tv_name.getText());
//                    itemView.getContext().startActivity(intent);
                }
            });
        }

        /**
         * Set the onClickListener for the button on each row.
         * Clicking on the button will create an AlertDialog with the options of YES/NO.
         * @param itemView
         */
        private void setAddButtonOnClick(@NonNull View itemView) {
            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(itemView.getContext());
                    alert.setTitle("Add to order");
                    alert.setMessage(tv_name.getText().toString());
                    //handle the "YES" click
                    alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            add();
                            info.setOrderArchive(orderArchive);
                            updateRunningTotal.onUpdate();
                        }
                        //handle the "NO" click
                    }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(itemView.getContext(), "Canceled", Toast.LENGTH_SHORT).show();
                        }
                    });
                    AlertDialog dialog = alert.create();
                    dialog.show();
                }
            });
        }

        private void setRemoveButtonOnClick(@NonNull View itemView){
            btn_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(itemView.getContext());
                    alert.setTitle("Remove from order");
                    alert.setMessage(tv_name.getText().toString());
                    //handle the "YES" click
                    alert.setPositiveButton("yes",new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which){
                            removeDonut();
                            info.setOrderArchive(orderArchive);
                            updateRunningTotal.onUpdate();
                        }
                    }).setNegativeButton("no",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog dialog = alert.create();
                    dialog.show();
                }
            });
        }

    }
}

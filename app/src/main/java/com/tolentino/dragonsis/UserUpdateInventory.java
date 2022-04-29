package com.tolentino.dragonsis;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.content.DialogInterface;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class UserUpdateInventory extends AppCompatActivity {

    ImageView img_man_upd_inv_back;
    TextView txt_man_upd_inv_id;
    TextView txt_man_upd_inv_prod_name;
    TextView txt_man_upd_inv_quantity;
    TextView txt_man_upd_inv_date;
    EditText edit_man_upd_inv_quantity_change;
    RadioButton radio_man_upd_inv_add;
    RadioButton radio_man_upd_inv_sold;
    RadioButton radio_man_upd_inv_remove;
    RadioGroup radiogr_man_upd_inv_action;
    EditText edit_man_upd_inv_remarks;
    Button btn_man_upd_inv_update;
    Button btn_man_upd_inv_delete;
    SharedPreferences pref;
    SharedPreferences user_pref;
    DbManager db;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update_inventory);

        img_man_upd_inv_back = findViewById(R.id.img_man_upd_inv_back);
        txt_man_upd_inv_id = findViewById(R.id.txt_man_upd_inv_id);
        txt_man_upd_inv_prod_name = findViewById(R.id.txt_man_upd_inv_prod_name);
        txt_man_upd_inv_quantity = findViewById(R.id.txt_man_upd_inv_quantity);
        txt_man_upd_inv_date = findViewById(R.id.txt_man_upd_inv_date);
        edit_man_upd_inv_quantity_change = findViewById(R.id.edit_man_upd_inv_quantity_change);
        radiogr_man_upd_inv_action = findViewById(R.id.radiogr_man_upd_inv_action);
        radio_man_upd_inv_add = findViewById(R.id.radiobtn_man_upd_inv_add);
        radio_man_upd_inv_sold = findViewById(R.id.radiobtn_man_upd_inv_sold);
        radio_man_upd_inv_remove = findViewById(R.id.radiobtn_man_upd_inv_remove);
        edit_man_upd_inv_remarks = findViewById(R.id.edit_man_upd_inv_remarks);
        btn_man_upd_inv_update = findViewById(R.id.btn_man_upd_inv_update);
        btn_man_upd_inv_delete = findViewById(R.id.btn_man_upd_inv_delete);

        radio_man_upd_inv_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radio_man_upd_inv_remove.setError(null);
            }
        });

        radio_man_upd_inv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radio_man_upd_inv_remove.setError(null);
            }
        });

        radio_man_upd_inv_sold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radio_man_upd_inv_remove.setError(null);
            }
        });

        db = new DbManager(this);
        pref = getSharedPreferences("inventory_list", MODE_PRIVATE);
        user_pref = getSharedPreferences("acc_details", MODE_PRIVATE);

        //Back to View Inventory Page
        img_man_upd_inv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Set Values of TextViews
        txt_man_upd_inv_id.setText(pref.getString("inventory_ID", null));
        txt_man_upd_inv_prod_name.setText(pref.getString("prod_name", null));
        txt_man_upd_inv_quantity.setText(pref.getString("inventory_quantity", null));
        edit_man_upd_inv_remarks.setText(pref.getString("inventory_remark", null));
        txt_man_upd_inv_date.setText(pref.getString("inventory_date", null));
        Log.i("Inv Quan Change Tag", pref.getString("inventory_quantity",null));

        //Update Inventory when pressed
        btn_man_upd_inv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inv_id = pref.getString("inventory_ID", null);
                String inv_prod_name = pref.getString("prod_name", null);
                String inv_remark = edit_man_upd_inv_remarks.getText().toString();

                int inv_quantity_change = 0;
                int inv_curr_quantity = 0;

                //if merong laman yung quantity change, update quantity, kung wala edi skip
                if(!edit_man_upd_inv_quantity_change.getText().toString().equals("")){
                    //initialize values
                    inv_quantity_change = Integer.parseInt(edit_man_upd_inv_quantity_change.getText().toString());
                    inv_curr_quantity = Integer.parseInt(pref.getString("inventory_quantity", null));

                    String new_inv_quan = changeQuantity(view,inv_curr_quantity,inv_quantity_change);
                    db.updateInventory(inv_id,new_inv_quan,inv_remark,inv_prod_name);

                }else{
                    edit_man_upd_inv_quantity_change.setError("Input is required");
                    int radioID = radiogr_man_upd_inv_action.getCheckedRadioButtonId();
                    if(radioID <= 0){
                        radio_man_upd_inv_remove.setError("Select Item");
                    }
                    String new_inv_quan = pref.getString("inventory_quantity", null);
                    db.updateInventory(inv_id,new_inv_quan,inv_remark,inv_prod_name);
                    db.close();
                }
            }
        });

        //Delete Inventory Record
        btn_man_upd_inv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(UserUpdateInventory.this)
                        .setTitle("Remove Inventory")
                        .setMessage("Are you sure you want to remove this item inventory?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int arg1) {
                                        db.deleteInventory(pref.getString("inventory_ID", null));

                                        //Back to View Inventory
                                        if(user_pref.getString("user_type", null).equals("Manager")){
                                            Intent i = new Intent(UserUpdateInventory.this, ManagerViewInventory.class);
                                            Intent endActivity = new Intent("finish_activity_man_view_inventory");
                                            sendBroadcast(endActivity);
                                            startActivity(i);
                                            finish();
                                        }else{
                                            Intent i = new Intent(UserUpdateInventory.this, EmployeeViewInventory.class);
                                            Intent endActivity = new Intent("finish_activity_emp_view_inventory");
                                            sendBroadcast(endActivity);
                                            startActivity(i);
                                            finish();
                                        }
                                    }
                        }).create().show();
            }
        });
    }

    public String changeQuantity(View v, int inv_curr_quan, int inv_quantity_change){
        int fin_quantity = 0;

        try {
            int radioID = radiogr_man_upd_inv_action.getCheckedRadioButtonId();
            radioButton = findViewById(radioID);
            String radioText = radioButton.getText().toString();
            //Toast.makeText(this,radioText,Toast.LENGTH_SHORT).show();
            if(radioText.equals("Add")){
                fin_quantity = inv_curr_quan + inv_quantity_change;

                Date d = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
                SimpleDateFormat tf = new SimpleDateFormat("KK:mm:ss a", Locale.getDefault());
                String curr_date = df.format(d);
                String curr_time = tf.format(d);
                db.insertInvHis(curr_date,"Added ", inv_quantity_change, pref.getString("prod_name", null), curr_time);
                db.addProductTotalQuant(pref.getString("prod_name", null),inv_quantity_change);

                //check if critical for notif
                boolean isProdCrit = db.checkProductCritical(pref.getString("prod_name", null));
                if(isProdCrit){
                    notifyCritical(pref.getString("prod_name",null));
                }

                //Back to Inventory
                if(user_pref.getString("user_type", null).equals("Manager")){
                    Intent i = new Intent(UserUpdateInventory.this, ManagerViewInventory.class);
                    Intent endActivity = new Intent("finish_activity_man_view_inventory");
                    sendBroadcast(endActivity);
                    startActivity(i);
                    finish();
                }else{
                    Intent i = new Intent(UserUpdateInventory.this, EmployeeViewInventory.class);
                    Intent endActivity = new Intent("finish_activity_emp_view_inventory");
                    sendBroadcast(endActivity);
                    startActivity(i);
                    finish();
                }
            }else if(radioText.equals("Sold")){
                if(checkQuantity(inv_quantity_change,inv_curr_quan) == true){
                    fin_quantity = inv_curr_quan - inv_quantity_change;

                Date d = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
                SimpleDateFormat tf = new SimpleDateFormat("KK:mm:ss a", Locale.getDefault());
                String curr_date = df.format(d);
                String curr_time = tf.format(d);
                db.insertInvHis(curr_date,"Sold ", inv_quantity_change, pref.getString("prod_name", null),curr_time);
                float prod_sales_amount = Float.parseFloat(db.getProductByProductName(pref.getString("prod_name",null)).get(0).get("prod_price"));
                float sales_amount = inv_quantity_change * prod_sales_amount;
                db.subtractProductTotalQuant(pref.getString("prod_name", null),inv_quantity_change);
                db.insertSales(sales_amount,inv_quantity_change,curr_date,curr_time, pref.getString("prod_name",null));

                    //check if critical for notif
                    boolean isProdCrit = db.checkProductCritical(pref.getString("prod_name", null));
                    if(isProdCrit){
                        notifyCritical(pref.getString("prod_name",null));
                    }
                }

                //Back to Inventory
                if(user_pref.getString("user_type", null).equals("Manager")){
                    Intent i = new Intent(UserUpdateInventory.this, ManagerViewInventory.class);
                    Intent endActivity = new Intent("finish_activity_man_view_inventory");
                    sendBroadcast(endActivity);
                    startActivity(i);
                    finish();
                }else{
                    Intent i = new Intent(UserUpdateInventory.this, EmployeeViewInventory.class);
                    Intent endActivity = new Intent("finish_activity_emp_view_inventory");
                    sendBroadcast(endActivity);
                    startActivity(i);
                    finish();
                }
            }else if(radioText.equals("Remove")){

                if(checkQuantity(inv_quantity_change,inv_curr_quan) == true){
                    fin_quantity = inv_curr_quan - inv_quantity_change;

                    Date d = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
                    SimpleDateFormat tf = new SimpleDateFormat("KK:mm:ss a", Locale.getDefault());
                    String curr_date = df.format(d);
                    String curr_time = tf.format(d);
                    db.insertInvHis(curr_date,"Removed ", inv_quantity_change, pref.getString("prod_name", null),curr_time);
                    db.subtractProductTotalQuant(pref.getString("prod_name", null),inv_quantity_change);

                    //check if critical for notif
                    boolean isProdCrit = db.checkProductCritical(pref.getString("prod_name", null));
                    if(isProdCrit){
                        notifyCritical(pref.getString("prod_name",null));
                    }

                }else{
                    fin_quantity = inv_curr_quan;
                }

                //Back to Inventory
                if(user_pref.getString("user_type", null).equals("Manager")){
                    Intent i = new Intent(UserUpdateInventory.this, ManagerViewInventory.class);
                    Intent endActivity = new Intent("finish_activity_man_view_inventory");
                    sendBroadcast(endActivity);
                    startActivity(i);
                    finish();
                }else{
                    Intent i = new Intent(UserUpdateInventory.this, EmployeeViewInventory.class);
                    Intent endActivity = new Intent("finish_activity_emp_view_inventory");
                    sendBroadcast(endActivity);
                    startActivity(i);
                    finish();
                }
            }
        } catch (NullPointerException e) {
            System.err.println("Null Pointer Exception");
            int radioID = radiogr_man_upd_inv_action.getCheckedRadioButtonId();
            if(radioID <= 0){
                radio_man_upd_inv_remove.setError("Select Item");
            }
            Toast.makeText(this, "Missing required input.", Toast.LENGTH_SHORT).show();
        }
        return String.valueOf(fin_quantity);
    }

    boolean checkQuantity(int change, int quantity){
        //return true if quantity is more than change
        if(change > quantity){
            Toast.makeText(this, "Not enough quantity of item", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

    void notifyCritical(String prod){
        String chan_ID = "chan_id";
        String chan_name = "Inventory Notification";
        String chan_desc = "chan_desc";
        int chan_importance = NotificationManager.IMPORTANCE_HIGH;
        String prod_name = prod;
        String notifGroup = "Inventory Notifications";

        Notification not_Builder = new NotificationCompat.Builder(this, chan_ID)
                .setSmallIcon(R.drawable.ic_warning)
                .setContentTitle("An item is now on critical quantity")
                .setContentText(prod_name + " needs to be restocked")
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setGroup(notifGroup)
                .build();

        int uniqueNotifID = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        NotificationManagerCompat notif_man = NotificationManagerCompat.from(this);
        notif_man.notify(uniqueNotifID,not_Builder);

        //Run notification
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(chan_ID,chan_name, chan_importance);
            channel.setDescription(chan_desc);
            channel.setShowBadge(true);
            NotificationManager man = getSystemService(NotificationManager.class);
            man.createNotificationChannel(channel);
        }
    }
}
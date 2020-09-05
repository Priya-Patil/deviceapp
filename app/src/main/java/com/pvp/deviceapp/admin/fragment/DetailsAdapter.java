package com.pvp.deviceapp.admin.fragment;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pvp.database.DatabaseHelper;

import com.pvp.deviceapp.R;
import com.pvp.deviceapp.admin.activities.AdminHomeActivity;
import com.pvp.deviceapp.admin.activities.NewConstants;
import com.pvp.deviceapp.admin.activities.SendDataActivity;
import com.pvp.deviceapp.utils.PrefManager;
import com.pvp.deviceapp.utils.Utility;


import java.util.ArrayList;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.MyViewHolder> {

    private Activity mContext;

    ArrayList<DetailsModel> list;

    DetailsModel model;
    DatabaseHelper databaseHelper;

   //public static String DB_FILEPATH = "/data/data/com.pvp.dairyapplication/databases/DeliveryBoy.db";
    public String DB_FILEPATH = "/data/data/com.pvp.dairyapplication/databases/DeliveryBoy.db";

    int customerId;

    int quantity=0;
    double amount=0.0;
    DatabaseHelper dbHelper;
    PrefManager prefManager;
    private  DetailsAdapter.ItemClickListener itemClickListener;

    BluetoothDevice bluetoothDevice1;

    public DetailsAdapter(Activity context, ArrayList<DetailsModel> list, BluetoothDevice bluetoothDevice) {
        this.list = list;
        mContext = context;
        bluetoothDevice1 = bluetoothDevice;
    }

    public DetailsAdapter(Activity context, ArrayList<DetailsModel> list, DetailsAdapter.ItemClickListener itemClickListener){
        this.list = list;
        mContext = context;
        this.itemClickListener=itemClickListener;
    }


    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    @NonNull
    @Override
    public DetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_for_home, null);
        dbHelper=new DatabaseHelper(mContext);
        prefManager=new PrefManager(mContext);

        //  prefManager=new PrefManager(mContext);
        return new  DetailsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsAdapter.MyViewHolder holder, int position) {

        final DetailsModel item = list.get(position);

        customerId=item.getId();
        holder.et_Title.setText("Title: "+item.getTitle());
        holder.et_Internal_diameter.setText("Internal diameter: "+item.getInternal_diameter());
        holder.et_Number_of_terms.setText("Number of turns: "+item.getNumber_of_terms());
        holder.et_Wire_diameter.setText("Wire diameter: "+ String.valueOf(item.getWire_diameter()));
        holder.et_Start_wait_time.setText("Start time: "+ String.valueOf(item.getStart_wait_time()));
        holder.et_Stop_wait_time.setText("Cutting time: "+item.getStop_wait_time());
        holder.et_Feed_speed.setText("Machine speed: "+item.getMachine_Speed());
        holder.et_Die_Diameter.setText("Die Diameter: "+item.getDie_Diameter());

       Log.e( "adapter: ", bluetoothDevice1.toString() );
        Bundle bundle=new Bundle();
        bundle.putParcelable("Custmodel",item);
        // NewRequestDetailsModel newRequestDetailsModel=item.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("model", String.valueOf(item));

               // Utility.launchActivity(mContext, MainActivity.class, false,bundle);
            }
        });

        holder.iv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(mContext,view);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.home_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem menu) {
                        /*Toast.makeText(HomeActivity.this,"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;*/
                        int id = menu.getItemId();
                        switch (id){
                            case R.id.item1:

                                Log.e( "onMenuItemClick: ", item.toString() );
                                Utility.launchActivity(mContext, EditDetailsActivity.class,
                                        false,bundle);
                                break;

                            case R.id.item2:

                                Log.e( "onMenuItemClick: ", item.toString() );
                                Delete(String.valueOf(item.getId()));

                                break;

                            case R.id.item3:

                               String fullstring= "c`IN01 "+item.getInternal_diameter()+
                                        "`IN02 "+item.getNumber_of_terms()+
                                        "`IN03 "+item.getWire_diameter()+
                                        "`IN04 "+item.getStart_wait_time()+
                                        "`IN05 "+item.getStop_wait_time()+
                                        "`IN06 "+item.getMachine_Speed()+
                                        "`IN10 "+item.getDie_Diameter()+"`" ;

                                Log.e( "onMenuItemClick: ", item.toString() );
                                prefManager.setAction("senddata");
                                prefManager.setTIME(fullstring+System.getProperty("line.separator"));

                                Intent intent = new Intent(mContext, SendDataActivity.class);
                                intent.putExtra(NewConstants.EXTRA_DEVICE, bluetoothDevice1);
                                mContext.startActivity(intent);
                               // mContext.finish();
                                //  Utility.launchActivity(mContext, MainActivity.class, false);
                               break;


                        }
                        return true;
                    }
                });
                popup.show();
            }
        });

    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView iv_more;
        TextView et_Internal_diameter,et_Number_of_terms, et_Wire_diameter, et_Start_wait_time,
                et_Stop_wait_time, et_Feed_speed,et_Die_Diameter, et_Title;



        public MyViewHolder(View itemView) {
            super(itemView);
            et_Title = itemView.findViewById(R.id.et_Title);
            et_Internal_diameter = itemView.findViewById(R.id.et_Internal_diameter);
            et_Internal_diameter = itemView.findViewById(R.id.et_Internal_diameter);
            et_Number_of_terms = itemView.findViewById(R.id.et_Number_of_terms);
            et_Wire_diameter = itemView.findViewById(R.id.et_Wire_diameter);
            et_Start_wait_time = itemView.findViewById(R.id.et_Start_wait_time);
            et_Stop_wait_time = itemView.findViewById(R.id.et_Stop_wait_time);
            et_Feed_speed = itemView.findViewById(R.id.et_Feed_speed);
            et_Die_Diameter = itemView.findViewById(R.id.et_Die_Diameter);
            iv_more = itemView.findViewById(R.id.iv_more);

            itemView.setOnClickListener(this); // bind the listener
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) itemClickListener.onClick(v, getAdapterPosition());
        }
    }

    //region Search Filter (setFilter Code)
    public  void setFilter(ArrayList<DetailsModel> newList) {
        list = new ArrayList<>();
        list.addAll(newList);
        notifyDataSetChanged();
    }



    private void Delete(String sid) {

        boolean isUpdate = dbHelper.Delete(sid);
        if (isUpdate == true) {

            int id = 0;

            SQLiteDatabase db = dbHelper.getWritableDatabase();

            Cursor cursor = db.rawQuery("SELECT  * FROM " +
                    dbHelper.TABLE_NEW_CUSTOMER, null);

            if (cursor.moveToLast()) {

                id = cursor.getInt(0);//to get id, 0 is the column index

//                    amtFor1Quantity = cursor.getDouble(10);
            }
           /* Toast.makeText(this, "Customer Updated Successfully.. "+id+" "+name+" "+ mobile +
                    " "+ address+" "+quantity+" "+amount+" "+amtFor1Quantity, Toast.LENGTH_LONG).show();
          */
            Toast.makeText(mContext, " Deleted Successfully.. ", Toast.LENGTH_SHORT).show();
            //Utility.launchActivity(EditDetailsActivity.this, HomepageActivity.class, false);
            // onBackPressed();

            Utility.launchActivity(mContext, AdminHomeActivity.class,
                    true);

        } else {
            Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
        // startActivity(new Intent(this, HomepageActivity.class));
        //  onBackPressed();

    }


}

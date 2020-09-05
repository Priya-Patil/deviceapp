package com.pvp.deviceapp.admin.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.pvp.database.DatabaseHelper;

import com.pvp.deviceapp.R;
import com.pvp.deviceapp.admin.activities.AdminHomeActivity;
import com.pvp.deviceapp.utils.PrefManager;
import com.pvp.deviceapp.utils.Utility;


public class EditDetailsActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btn_add_cust,btn_edit_cust;
    ImageView img_back;
    DatabaseHelper dbHelper;
    String customerName,mobile,address,productName,created;
    int quantity;
    String Customerid;
    int userId;
    double amtFor1Quantity=0.0;
    double amount,amount2;
    double result=0.0;
    double result2=0.0;

    TextView tv_amount;
    PrefManager prefManager;
    DetailsModel model;
    EditText et_Title, et_Internal_diameter,et_Number_of_terms, et_Wire_diameter, et_Start_wait_time,
            et_Stop_wait_time, et_Feed_speed,et_Die_Diameter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);
        bindView();
        listener();

        btn_add_cust.setVisibility(View.GONE);
        btn_edit_cust.setVisibility(View.VISIBLE);

        dbHelper=new DatabaseHelper(this);


        model=getIntent().getParcelableExtra("Custmodel");
        Customerid= String.valueOf(model.getId());
        Log.e( "onCreate: ", model.toString() );

        /*Cursor cursor = dbHelper.getData(Integer.parseInt(Customerid));

        if (cursor.moveToNext()) {
            amtFor1Quantity=cursor.getDouble(10);
        }

        Log.e("amtFor1QuantityEDIT",String.valueOf(amtFor1Quantity));
*/

        et_Title.setText(model.getTitle());
        et_Internal_diameter.setText(model.getInternal_diameter());
        et_Die_Diameter.setText(model.getDie_Diameter());
        et_Number_of_terms.setText(model.getNumber_of_terms());
        et_Wire_diameter.setText(model.getWire_diameter());
        et_Start_wait_time.setText(model.getStart_wait_time());
        et_Stop_wait_time.setText(model.getStop_wait_time());
        et_Feed_speed.setText(model.getMachine_Speed());
        et_Die_Diameter.setText(model.getDie_Diameter());

        Log.e("CustidEdit",String.valueOf(Customerid));
        Log.e("CustidEdit", String.valueOf(model));


    }

    private void listener() {
        btn_add_cust.setOnClickListener(this);
        btn_edit_cust.setOnClickListener(this);
        img_back.setOnClickListener(this);
    }

    private void bindView() {
        et_Title = findViewById(R.id.et_Title);
        et_Internal_diameter = findViewById(R.id.et_Internal_diameter);
        et_Number_of_terms = findViewById(R.id.et_Number_of_terms);
        et_Wire_diameter = findViewById(R.id.et_Wire_diameter);
        et_Start_wait_time = findViewById(R.id.et_Start_wait_time);
        et_Stop_wait_time = findViewById(R.id.et_Stop_wait_time);
        et_Feed_speed = findViewById(R.id.et_Feed_speed);
        et_Die_Diameter = findViewById(R.id.et_Die_Diameter);
        btn_add_cust = (Button) findViewById(R.id.btn_add_cust);
        btn_edit_cust = (Button) findViewById(R.id.btn_edit_cust);
        tv_amount =  findViewById(R.id.tv_amount);
        img_back = findViewById(R.id.img_back);
        prefManager=new PrefManager(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_edit_cust:

               /* if((et_name.getText().toString().length()==0) || (et_mobileno.getText().toString().length()<=9)
                        || (et_address.getText().toString().length()==0)
                        && (et_amtFor1Quantity.getText().toString().length()==0)
                        || (et_quantity.getText().toString().length()==0) || tv_amount.getText().toString().length()==0) {
                    Toast.makeText(this, "Please Enter all the Details", Toast.LENGTH_SHORT).show();
                }
                else{
                    customerName=et_name.getText().toString();
                    mobile=et_mobileno.getText().toString();
                    address=et_address.getText().toString();
                    productName=et_productname.getText().toString();
                    //int quantity=Double.parseDouble(et_quantity.getText().toString());
                    quantity=Integer.parseInt(et_quantity.getText().toString().trim());
                    amount=Double.parseDouble(tv_amount.getText().toString().trim());
                    amtFor1Quantity=Double.parseDouble(et_amtFor1Quantity.getText().toString().trim());
                    created= Utility.getCurrentDate();
                //    Log.e("data",Customerid+customerName+mobile+address+productName+quantity+amount+""+amtFor1Quantity);
*/
                    UpdateData(Customerid,et_Internal_diameter.getText().toString(),et_Number_of_terms.getText().toString(),
                            et_Wire_diameter.getText().toString(),
                            et_Start_wait_time.getText().toString(),et_Stop_wait_time.getText().toString(),
                            et_Feed_speed.getText().toString(),et_Die_Diameter.getText().toString(), et_Title.getText().toString());
                //}
                break;

            case R.id.img_back:
                onBackPressed();
                break;
        }
    }


    private void UpdateData(String sid,String Internal_diameter,String Number_of_terms,String Wire_diameter,
                            String Start_wait_time,String Stop_wait_time,String Machine_Speed,
                            String Die_Diameter,String title) {

          boolean isUpdate = dbHelper.UpdateData(sid,
                    Internal_diameter,
                    Number_of_terms,Wire_diameter,Start_wait_time,Stop_wait_time,Machine_Speed,Die_Diameter, title);


            if (isUpdate == true) {

                int id = 0;

                SQLiteDatabase db = dbHelper.getWritableDatabase();

                Cursor cursor = db.rawQuery("SELECT  * FROM " +
                        dbHelper.TABLE_NEW_CUSTOMER, null);

                if (cursor.moveToLast()) {

                    id = cursor.getInt(0);//to get id, 0 is the column index
                    mobile = cursor.getString(2);
                    address = cursor.getString(3);

//                    amtFor1Quantity = cursor.getDouble(10);
                }
           /* Toast.makeText(this, "Customer Updated Successfully.. "+id+" "+name+" "+ mobile +
                    " "+ address+" "+quantity+" "+amount+" "+amtFor1Quantity, Toast.LENGTH_LONG).show();
          */
                Toast.makeText(this, " Updated Successfully.. ", Toast.LENGTH_SHORT).show();
                //Utility.launchActivity(EditDetailsActivity.this, HomepageActivity.class, false);
               // onBackPressed();

                Utility.launchActivity(EditDetailsActivity.this, AdminHomeActivity.class,
                        true);

            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
           // startActivity(new Intent(this, HomepageActivity.class));
          //  onBackPressed();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

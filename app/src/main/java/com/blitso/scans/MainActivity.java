package com.blitso.scans;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;
import android.content.Intent;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.blitso.scans.R;
import com.blitso.scans.myDbAdapter;
import com.chrisdisdero.crdcrypt.CRDCrypt;
import com.chrisdisdero.crdcrypt.CRDCryptException;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.scottyab.aescrypt.AESCrypt;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import com.shreyaspatil.MaterialDialog.MaterialDialog;


import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import com.chrisdisdero.crdcrypt.CRDCrypt;
import com.chrisdisdero.crdcrypt.CRDCryptException;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MEDIA";
    private static final int PERMISSION_REQUEST_CODE = 100;
    Button buttonScan, btn1, btn2, btn3, btn4, btn6;
    EditText Pass , updateold, updatenew, delete, fname, age, sex, address, phone, email, temperature, q1, q2, q3, q4, currDate, currTime, deleteedit,q1yes, q3yes, q4yes;
    myDbAdapter helper;
    TextView scanOutput, textviewview, viewname, viewphone, viewtemp, viewdate,detailed_data,tdata ,clickddata, output;
    String unsplice, undata, cundata;
    private RadioGroup question1, question2, question3, question4;
    private RadioButton answer1, answer2, answer3, answer4;
    private static final String FILE_NAME = "participants.txt";
    IntentIntegrator qrScan;
    ScrollView scroll_toggle;
    ListView detailed_data1;
    LinearLayout deletelayout, viewpage, qrtoadd, btnrow2, afterscanproof, waiver1, waiver2, waiver3,detailed_layout;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        scroll_toggle=findViewById(R.id.scroll_toggle);
        deletelayout=findViewById(R.id.deletelayout);
        deletelayout.setVisibility(View.GONE);
        viewpage=findViewById(R.id.viewpage);
        viewpage.setVisibility(View.GONE);
        qrtoadd=findViewById(R.id.qrtoadd);
        qrtoadd.setVisibility(View.GONE);
        btnrow2=findViewById(R.id.btnrow2);
        afterscanproof=findViewById(R.id.afterscanproof);
        afterscanproof.setVisibility(View.GONE);
        waiver1=findViewById(R.id.waiver1);
        waiver2=findViewById(R.id.waiver2);
        waiver3=findViewById(R.id.waiver3);
        waiver1.setVisibility(View.GONE);
        waiver2.setVisibility(View.GONE);
        waiver3.setVisibility(View.GONE);


        fname = findViewById(R.id.fname);
        age=findViewById(R.id.age);
        sex=findViewById(R.id.sex);
        address=findViewById(R.id.address);
        phone=findViewById(R.id.phone);
        email=findViewById(R.id.email);
        temperature=findViewById(R.id.temperature);
        q1=findViewById(R.id.q1);
        q2=findViewById(R.id.q2);
        q3=findViewById(R.id.q3);
        q4=findViewById(R.id.q4);
        currDate=findViewById(R.id.currDate);
        currTime=findViewById(R.id.currTime);
        deleteedit=findViewById(R.id.editText6);
        detailed_data1= findViewById(R.id.ddata);
        tdata=findViewById(R.id.textView3);
        tdata.setVisibility(View.GONE);
        detailed_layout=findViewById(R.id.detailed_layout);
        clickddata=findViewById(R.id.clickddata);
        clickddata.setVisibility(View.GONE);


        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);


        textviewview=findViewById(R.id.textviewview);
        textviewview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAllView();
                detailed_data1.setVisibility(View.VISIBLE);
                btnrow2.setVisibility(View.GONE);
                buttonScan.setVisibility(View.GONE);
                actionBar.setHomeButtonEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
                String data = helper.detailedData();
                tdata.setText(data);
                undata = tdata.getText().toString();
                final String[] split = undata.split(";");
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.listview, R.id.textView, split);
                detailed_data1.setAdapter(arrayAdapter);

                String alldata = helper.clickedddata();
                clickddata.setText(alldata);
                cundata = clickddata.getText().toString();
                final String[] split1 = cundata.split(";");


                detailed_data1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selectedItem = (String) parent.getItemAtPosition(position);

                        //Toast.makeText(MainActivity.this,split1[position],Toast.LENGTH_SHORT).show();

                        MaterialDialog mBottomSheetDialog = new MaterialDialog.Builder(MainActivity.this)
                                .setAnimation(R.raw.user_details_open)
                                .setTitle("Full Details")
                                .setMessage(split1[position])
                                .setCancelable(true)
                                .build();

                        // Show Dialog
                        mBottomSheetDialog.show();
                    }
                });
            }
        });


        btn1=findViewById(R.id.button);
        btn2=findViewById(R.id.button2);
        btn3=findViewById(R.id.button3);
        btn4=findViewById(R.id.button4);
        btn6=findViewById(R.id.button6);
        buttonScan = findViewById(R.id.buttonScan);


        viewname=findViewById(R.id.viewname);
        viewphone=findViewById(R.id.viewphone);
        viewtemp=findViewById(R.id.viewtemp);
        viewdate=findViewById(R.id.viewdate);

        helper = new myDbAdapter(this);

        scanOutput = findViewById(R.id.textView);
        //intializing scan object
        question1 = findViewById(R.id.group1);
        q1yes = findViewById(R.id.q1yes);
        question2 = findViewById(R.id.group2);
        question3 = findViewById(R.id.group3);
        q3yes = findViewById(R.id.q3yes);
        question4 = findViewById(R.id.group4);
        q4yes = findViewById(R.id.q4yes);



        final IntentIntegrator qrScan;
        qrScan = new IntentIntegrator(this);
        qrScan.setPrompt("Scan QR Code");
        qrScan.setBeepEnabled(true);
        qrScan.setOrientationLocked(true);
        //attaching onclick listener
        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrScan.initiateScan();
            }
        });
        debugToggle();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                btnrow2.setVisibility(View.VISIBLE);
                buttonScan.setVisibility(View.VISIBLE);
                detailed_data1.setVisibility(View.GONE);
                ActionBar actionBar = getSupportActionBar();
                actionBar.setHomeButtonEnabled(false);
                actionBar.setDisplayHomeAsUpEnabled(false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void hideAllView(){
        deletelayout.setVisibility(View.GONE);
        viewpage.setVisibility(View.GONE);
        detailed_data1.setVisibility(View.GONE);
    }

    public void addUser(View view)
    {
//        if(viewname.getText() == "Gabriel Asuncion (Admin)" || viewtemp.getText() == "db.deleteAll()"){
//
//        }
        btn1.setBackgroundResource(R.drawable.duo_shape_rectangled_rounded_color_accent);
        String t1 = fname.getText().toString();
        String t2 = age.getText().toString();
        String t3 = sex.getText().toString();
        String t4 = address.getText().toString();
        String t5 = phone.getText().toString();
        String t6 = email.getText().toString();
        String t7 = temperature.getText().toString();
        String t8 = q1.getText().toString();
        String t9 = q2.getText().toString();
        String t10 = q3.getText().toString();
        String t11 = q4.getText().toString();
        String t12 = currDate.getText().toString();
        String t13 = currTime.getText().toString();
        if(t1.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"The QR Code doesn't have a Name", Toast.LENGTH_LONG).show();
        } else if(t2.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"The QR Code doesn't set their Birthdate", Toast.LENGTH_LONG).show();
        } else if(t4.length()<5)
        {
            Toast.makeText(getApplicationContext(),"The QR Code doesn't have a Proper Address", Toast.LENGTH_LONG).show();
        }
        else
        {
            long id = helper.insertData(t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13);
            if(id<=0)
            {
                Toast.makeText(getApplicationContext(),"Insertion Unsuccessful", Toast.LENGTH_LONG).show();
            } else
            {
                Toast.makeText(getApplicationContext(),"Insertion Successful", Toast.LENGTH_LONG).show();
            }
            resetQrFields();
            afterscanproof.setVisibility(View.GONE);
            qrtoadd.setVisibility(View.GONE);
            btnrow2.setVisibility(View.VISIBLE);
        }
    }
    public  void resetQrFields(){
        fname.setText("");
        age.setText("");
        sex.setText("");
        address.setText("");
        phone.setText("");
        email.setText("");
        temperature.setText("");
        q1.setText("");
        q2.setText("");
        q3.setText("");
        q4.setText("");
        currDate.setText("");
        currTime.setText("");
    }
    public void debugToggle(){
        scroll_toggle.setVisibility(View.GONE);
    }

    public void viewdata(View view)
    {
        verifyStoragePermissions(MainActivity.this);
        hideAllView();
        viewpage.setVisibility(View.VISIBLE);
        String data = helper.getData();
        textviewview.setText(data);
        if (data.equals("Table is empty")){
            btn6.setVisibility(View.GONE);
        }else {
            btn6.setVisibility(View.VISIBLE);
        }

    }
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
    private void checkExternalMedia(){
        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // Can read and write the media
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // Can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            // Can't read or write
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }
        textviewview.append("\n\nExternal Media: readable="
                +mExternalStorageAvailable+" writable="+mExternalStorageWriteable);
    }
    private void writeToSDFile(){
        File root = android.os.Environment.getExternalStorageDirectory();
        textviewview.append("\nExternal file system root: "+root);

        File dir = new File (root.getAbsolutePath() + "/Download");
        dir.mkdirs();
        File file = new File(dir, "participants.csv");

        try {
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(f);
            pw.println(helper.getwholedata());
            csvanim();
            pw.flush();
            pw.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i(TAG, "******* File not found. Did you add a WRITE_EXTERNAL_STORAGE permission to the   manifest?");
            Toast.makeText(MainActivity.this, "Not Success", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        textviewview.append("\n\nFile written to "+file);
    }
    public void savetotext(View v) {

        String text = helper.getwholedata();
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text.getBytes());
            checkExternalMedia();
            writeToSDFile();
            hideAllView();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void update(View view)
    {
        String u1 = updateold.getText().toString();
        String u2 = updatenew.getText().toString();
        btn3.setBackgroundResource(R.drawable.duo_shape_rectangled_rounded_color_accent);
        if(u1.isEmpty() || u2.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Enter Data", Toast.LENGTH_LONG).show();
        }
        else
        {
            int a= helper.updateName( u1, u2);
            if(a<=0)
            {
                Toast.makeText(getApplicationContext(),"Unsuccessful", Toast.LENGTH_LONG).show();
                updateold.setText("");
                updatenew.setText("");
            } else {
                Toast.makeText(getApplicationContext(),"Updated", Toast.LENGTH_LONG).show();
                updateold.setText("");
                updatenew.setText("");
            }
        }

    }
    public void deleteClicked(View view){
        hideAllView();
        deletelayout.setVisibility(View.VISIBLE);
    }
    public void delete(View view)
    {
        String uname = deleteedit.getText().toString();

        if(uname.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Enter Data", Toast.LENGTH_LONG).show();
            deletelayout.setVisibility(View.GONE);
        }
        else{
            int a= helper.delete(uname);
            if(a<=0)
            {
                Toast.makeText(getApplicationContext(),"Unsuccessful", Toast.LENGTH_LONG).show();
                deleteedit.setText("");
                deletelayout.setVisibility(View.GONE);
            }
            else
            {
                Toast.makeText(this, "DELETED", Toast.LENGTH_LONG).show();
                deleteedit.setText("");
                deletelayout.setVisibility(View.GONE);
            }
        }
    }

        @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        hideAllView();
        final String checkdate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
        final String getdate = new SimpleDateFormat("MMddyyyy", Locale.getDefault()).format(new Date());
        final String gettime = new SimpleDateFormat("HHmmss", Locale.getDefault()).format(new Date());
        String adminaccess = "admin_gabriel_delete_table";
        String printform = "admin_gabriel_print_form";
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data

                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    scanOutput.setText(result.getContents());
                    String encin = result.getContents();
                    String password = "password";

//                    try {
//
//                      String messageAfterDecrypt = AESCrypt.decrypt(password, encin);
//                      scanOutput.setText(messageAfterDecrypt);
//                    }catch (GeneralSecurityException e){
//                        //handle error - could be due to incorrect password or tampered encryptedMsg
//                    }
                    unsplice = scanOutput.getText().toString();
                    StringBuilder input1 = new StringBuilder();
                    input1.append(unsplice);
                    scanOutput.setText(input1.reverse());
                    unsplice = scanOutput.getText().toString();
                    scanOutput.setVisibility(View.GONE);
                    String[] splitting = unsplice.split(";");
                    Log.i("QRmsg",unsplice);
                    for (int i=0; i < splitting.length; i++)
                    {
                        System.out.println(splitting[i]);
                    }
                    fname.setText(splitting[0]);
                    viewname.setText("Full Name: "+splitting[0].toUpperCase());
                    age.setText(splitting[1]);
                    sex.setText(splitting[2]);
                    address.setText(splitting[3]);
                    phone.setText(splitting[4]);
                    viewphone.setText("Phone Number: "+splitting[4].toUpperCase());
                    email.setText(splitting[5]);
                    temperature.setText(splitting[6]);
                    viewtemp.setText("Temperature: "+splitting[6].toUpperCase());
                    q1.setText(splitting[7]);
                    q2.setText(splitting[8]);
                    q3.setText(splitting[9]);
                    q4.setText(splitting[10]);
                    currDate.setText(splitting[11]);
                    currTime.setText(splitting[12]);
                    if(fname.getText().toString().equals(adminaccess)){
                        adminpowers();
                    }else if(fname.getText().toString().equals(printform)){

                    }else if(fname.getText().toString().equals(""))
                    {
                        Toast.makeText(getApplicationContext(),"The QR Code doesn't have a Name", Toast.LENGTH_LONG).show();
                    } else if(age.getText().toString().equals(""))
                    {
                        Toast.makeText(getApplicationContext(),"The QR Code doesn't set their Birthdate", Toast.LENGTH_LONG).show();
                    } else if(address.getText().toString().length()<5)
                    {
                        Toast.makeText(getApplicationContext(),"The QR Code doesn't have a Proper Address", Toast.LENGTH_LONG).show();
                    } else if(phone.getText().toString().equals(""))
                    {
                        Toast.makeText(getApplicationContext(),"The QR Code doesn't have a Phone Number", Toast.LENGTH_LONG).show();
                    }
                    else if(sex.getText().toString().equals("Gender"))
                    {
                        Toast.makeText(getApplicationContext(),"The QR Code doesn't set their gender", Toast.LENGTH_LONG).show();
                    }
                    else if(fname.getText().toString().equals("1"))
                    {
                        Toast.makeText(getApplicationContext(),"The QR Code is outdated", Toast.LENGTH_LONG).show();
                    }
                    else{
                        currDate.setText(getdate);
                        currTime.setText(gettime);
                        withEditText();
                    }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void withEditText() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Welcome, "+fname.getText().toString());
        builder.setMessage("Enter his/her temperature");

        final EditText input = new EditText(MainActivity.this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                temperature.setText(input.getText().toString());
                viewtemp.setText("Temperature: "+input.getText().toString()+"°C");
                viewdate.setText("Time in: "+currTime.getText().toString()+" || "+currDate.getText().toString());
//                waiver1();
            }
        });
        builder.show();
        btnrow2.setVisibility(View.GONE);
        afterscanproof.setVisibility(View.VISIBLE);
        qrtoadd.setVisibility(View.VISIBLE);

    }
    public void qroutdate(){
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(this)
                .setTitle("QR Code Outdated")
                .setMessage("QR Code needs to be generated within entering the facility /n Advice to create a new one")
                .setCancelable(false)
                .setPositiveButton("Delete", R.drawable.ic_check, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();

        // Show Dialog
        mBottomSheetDialog.show();
    }
    public void adminpowers(){
        final myDbAdapter.myDbHelper db = new myDbAdapter.myDbHelper(getBaseContext());
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(this)
                .setAnimation(R.raw.delete_anim)
                .setTitle("Delete Table?")
                .setMessage("This will wipe all of the data existing")
                .setCancelable(false)
                .setPositiveButton("Yes", R.drawable.ic_check, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        db.deleteAll();
                        successpop();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("No", R.drawable.ic_close, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();
        mBottomSheetDialog.show();
    }


    public void csvanim(){
        File root = android.os.Environment.getExternalStorageDirectory();
        File dir = new File (root.getAbsolutePath() + "/download");
        MaterialDialog mBottomSheetDialog = new MaterialDialog.Builder(this)
                .setAnimation(R.raw.save_anim)
                .setTitle("CSV Saved")
                .setMessage("Saved to "+ dir+" participant.csv")
                .setCancelable(true)
                .build();

        // Show Dialog
        mBottomSheetDialog.show();


    }
    public void successpop(){
        File root = android.os.Environment.getExternalStorageDirectory();
        File dir = new File (root.getAbsolutePath() + "/download");
        final MaterialDialog mBottomSheetDialog = new MaterialDialog.Builder(this)
                .setAnimation(R.raw.success_anim)
                .setTitle("Delete Success")
                .setCancelable(true)
                .build();

        // Show Dialog
        mBottomSheetDialog.show();
        Thread background = new Thread() {
            public void run() {
                try {
                    sleep(1*2000);
                    mBottomSheetDialog.dismiss();
                } catch (Exception e) {
                }
            }
        };
        background.start();
    }
    public void waiver1(){
        waiver1.setVisibility(View.VISIBLE);

    }
    public void waiver2(View view){
        int selectedId1 = question1.getCheckedRadioButtonId();
        answer1 = MainActivity.this.findViewById(selectedId1);
        int selectedId2 = question1.getCheckedRadioButtonId();
        answer2 = MainActivity.this.findViewById(selectedId2);
        q1.setText(answer1.toString()+" "+q1yes.getText().toString());
        q2.setText(answer2.toString());

//        waiver1.setVisibility(View.GONE);
//        waiver2.setVisibility(View.VISIBLE);
    }
    public void waiver3(View view){
        int selectedId3 = question1.getCheckedRadioButtonId();
        answer3 = MainActivity.this.findViewById(selectedId3);
        int selectedId4 = question1.getCheckedRadioButtonId();
        answer4 = MainActivity.this.findViewById(selectedId4);
        q3.setText(answer3.toString()+" "+q3yes.getText().toString());
        q4.setText(answer4.toString()+" "+q4yes.getText().toString());
//        waiver2.setVisibility(View.GONE);
//        waiver3.setVisibility(View.VISIBLE);
    }
    public void waiverfinish(View view){
        waiver3.setVisibility(View.GONE);
    }

    protected OnBackPressedListener onBackPressedListener;

    public interface OnBackPressedListener {
        void doBack();

    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to close this activity?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

}

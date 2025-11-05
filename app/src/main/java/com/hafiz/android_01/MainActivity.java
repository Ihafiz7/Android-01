package com.hafiz.android_01;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.hafiz.android_01.ui.BatteryReceiver;
import com.hafiz.android_01.ui.NetworkChangeReceiver;
import com.hafiz.android_01.ui.ProductAddActivity;
import com.hafiz.android_01.ui.ProductListActivity;

public class MainActivity extends AppCompatActivity {

    EditText etUserName, etPassword;
    Button btnLogin, btnSignup;
    FloatingActionButton fab;

    NetworkChangeReceiver receiver = new NetworkChangeReceiver();
    BatteryReceiver batteryReceiver = new BatteryReceiver();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        unregisterReceiver(batteryReceiver);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        etUserName =findViewById(R.id.phEmail);
        etPassword = findViewById(R.id.phPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignUp);
        fab = findViewById(R.id.btnTree);

        //for receiver
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver,filter);

        //battery
        IntentFilter btrFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver,btrFilter);

        Intent intent = new Intent(this, MyService.class);
        startService(intent);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                String email = etUserName.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                System.out.println(email +  password);
//                Toast.makeText(MainActivity.this, "logging in ...", Toast.LENGTH_SHORT).show();
                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }else if(email.equals("admin") && password.equals("1234")){

                    SharedPreferences sharedPreferences = getSharedPreferences("LoginReff", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("username", "Hafiz");
                    editor.putInt("password", 1234);
                    editor.putBoolean("isLoggedIn", true);

                    editor.apply();

                    btnLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent( MainActivity.this, ProductAddActivity.class);
                            startActivity(intent);

                        }
                    });
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                }else {
//                    Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
//                    Snackbar.make(findViewById(android.R.id.content), "Message Deleted", Snackbar.LENGTH_LONG)
//                            .setAction("Undo", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    Toast.makeText(MainActivity.this, "Action Undone", Toast.LENGTH_SHORT).show();
//                                }
//                            }).show();

//                    new AlertDialog.Builder(MainActivity.this).setTitle("Delete Item")
//                            .setMessage("Are you sure?").setIcon(android.R.drawable.ic_dialog_alert)
//                            .setPositiveButton("Yes", (dialog, which) -> {
//                                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
//                            }).setNegativeButton("No", (dialog, which) -> {
//                                Toast.makeText(MainActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
//                            }).show();

                    LayoutInflater inflater = getLayoutInflater();
                    View layout  = inflater.inflate(R.layout.custom_toast, null);

                    TextView text = layout.findViewById(R.id.toast_text);
                    text.setText("Custom toast works!");

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Floating button clicked", Toast.LENGTH_LONG).show();

            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                Toast.makeText(MainActivity.this, "SignUp Clicked", Toast.LENGTH_LONG).show();
*/
                Intent intent = new Intent( MainActivity.this, ProductListActivity.class);
                startActivity(intent);
            }
        });

    }
}
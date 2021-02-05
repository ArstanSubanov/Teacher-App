package com.example.teacherapp3;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private static String ip = "192.168.1.5";
    private static String port = "1433";
    private static String classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String dataBase = "jamk1711";
    private static String username = "inai";
    private static String password = "inai";
    private static String url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+dataBase;

    private Connection connection = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);

        textView = findViewById(R.id.textView);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            Class.forName(classes);
            connection = DriverManager.getConnection(url, username, password);

            textView.setText("SUCCESS");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
            textView.setText("ERROR");
        }catch (SQLException e){
            e.printStackTrace();
            textView.setText("FAILURE");
        }


        if (connection!=null){

            Statement statement = null;
            try{
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT DisciplineName FROM Disciplines");
                while(resultSet.next()){
                    textView.setText(resultSet.getString(1));
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }else{
            textView.setText("Connection is null");
        }


    }

}

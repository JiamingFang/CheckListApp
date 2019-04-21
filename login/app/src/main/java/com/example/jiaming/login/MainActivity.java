package com.example.jiaming.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
  //Created by Jimmy Fang, 2019 Jan
  static List<String> list = new ArrayList<>();
  static List<String> list2 = new ArrayList<>();
  static List<Integer> list3 = new ArrayList<>();
  static List<Boolean> list4 = new ArrayList<>();
  static int count = 1;
  static int accountID  = 0;
  static int curID = -1;


  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
    Boolean check = sharedPref.getBoolean("login_check",false);
    accountID = sharedPref.getInt("id",0);
    curID = sharedPref.getInt("curID", -1);
      if (check == false) {
        Fragment fragment = new login();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, fragment);
        transaction.commit();
      } else if(check){
        Fragment fragment = new Data();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, fragment);
        transaction.commit();
      }
  }

}

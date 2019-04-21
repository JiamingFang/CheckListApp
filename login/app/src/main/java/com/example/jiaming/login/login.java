package com.example.jiaming.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends Fragment {
  myDbAdapter helper;
  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.login, container, false);
  }

  @Override public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    helper = new myDbAdapter(getActivity());
    Button button = (Button) view.findViewById(R.id.button);
    final EditText et = (EditText) view.findViewById((R.id.eidtView));
    et.requestFocus();
    final EditText et2 = (EditText) view.findViewById(R.id.editView2);
    final myDbAdapter helper = new myDbAdapter(getActivity());
    button.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Fragment fragment = new Data();
        MainActivity.curID = helper.getAccount(et.getText().toString(),et2.getText().toString());
        Log.d("id: ",MainActivity.curID+"");
        if(MainActivity.curID != -1){
          FragmentManager fm = getActivity().getSupportFragmentManager();
          FragmentTransaction transaction = fm.beginTransaction();
          transaction.replace(R.id.contentFragment, fragment);
          transaction.commit();
          SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
          SharedPreferences.Editor editor = sharedPref.edit();
          editor.putBoolean("login_check",true);
          editor.putInt("curID",MainActivity.curID);
          editor.commit();
        }
        else{
          Toast.makeText(getContext(), "Account incorrect or non-existing", Toast.LENGTH_SHORT).show();
        }
      }
    });
    Button button2 = (Button) view.findViewById((R.id.button2));
    button2.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        int check = helper.checkExist(et.getText().toString());
        if(check == 1){
          Toast.makeText(getContext(), "This account already exists", Toast.LENGTH_SHORT).show();
        }
        else {
          if(et.getText().toString().equals("")){
            Toast.makeText(getContext(), "Username cannot be blank", Toast.LENGTH_SHORT).show();
          }
          else if(et2.getText().toString().equals("")){
            Toast.makeText(getContext(), "Password cannot be blank", Toast.LENGTH_SHORT).show();
          }
          else {
            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.commit();
            helper.insertAccount(et.getText().toString(), et2.getText().toString());
            MainActivity.accountID++;
            editor.putInt("id", MainActivity.accountID);
            Toast.makeText(getContext(), "Account created", Toast.LENGTH_SHORT).show();
          }
        }
      }
    });
  }
}

package com.example.jiaming.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.List;

public class Data extends Fragment {
  private RecyclerView recyclerView;
  static CustomAdapter mAdapter;
  List<String> list = MainActivity.list;
  List<String> list2 = MainActivity.list2;
  List<Integer> list3 = MainActivity.list3;
  List<Boolean> list4 = MainActivity.list4;
  myDbAdapter helper;

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    helper = new myDbAdapter(getActivity());
    return inflater.inflate(R.layout.data, container, false);
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);


    mAdapter = new CustomAdapter(list,list2,list3,list4);
    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
    recyclerView.setLayoutManager(mLayoutManager);
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.setAdapter(mAdapter);

    MainActivity.list.clear();
    MainActivity.list2.clear();
    MainActivity.list4.clear();

    helper.getData(MainActivity.curID);
    Log.d("curID", "curID: "+MainActivity.curID);
    MainActivity.count = list.size()+1;

    Button fab = (Button) getActivity().findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        addItem(getActivity());
      }
    });

    Button fab2 = (Button) getActivity().findViewById(R.id.fab2);
    fab2.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Fragment fragment = new Logout();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, fragment);
        transaction.commit();
      }
    });

    Button fab3 = (Button) getActivity().findViewById(R.id.fab3);
    fab3.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        int num = 0;
        Log.d("list4.size(): ", list4.size()+"");
        Log.d("count: ", MainActivity.count+"");
        Log.d("list4.get(): ", CustomAdapter.list4.get(0)+"");
        for(int i = 0; i < MainActivity.count-1; i++){
          if(CustomAdapter.list4.size() != 0 && CustomAdapter.list4.get(i-num)){
            Log.d("Title",list.get(i-num)+"");
            helper.deleteContact(list.get(i-num));
            list.remove(i-num);
            list2.remove(i-num);
            CustomAdapter.list4.remove(i-num);
            num++;
            mAdapter.notifyDataSetChanged();
          }
        }
        MainActivity.count-=num;
      }
    });

  }

  private void addItem(Context c){
    LinearLayout layout = new LinearLayout(c);
    layout.setOrientation(LinearLayout.VERTICAL);

    // Add a TextView here for the "Title" label, as noted in the comments
    final EditText titleBox = new EditText(c);
    titleBox.setHint("Title");
    layout.addView(titleBox); // Notice this is an add method

    // Add another TextView here for the "Description" label
    final EditText descriptionBox = new EditText(c);
    descriptionBox.setHint("Description");
    layout.addView(descriptionBox); // Another add method
    AlertDialog dialog = new AlertDialog.Builder(c)
        .setTitle("Add new item")
        .setView(layout)
        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialog, int which) {
            list.add(titleBox.getText().toString());
            list2.add(descriptionBox.getText().toString());
            list3.add(MainActivity.count);
            list4.add(false);
            MainActivity.count++;
            helper.insertData(titleBox.getText().toString(),descriptionBox.getText().toString(),0,MainActivity.curID);
          }
        })
        .setNegativeButton("Cancel", null)
        .create();
    dialog.show();
  }
}
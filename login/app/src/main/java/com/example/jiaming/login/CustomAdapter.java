package com.example.jiaming.login;


import android.graphics.Color;
import android.graphics.ColorSpace;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{
  ImageView imageView;
  List<String> list;
  List<String> list2;
  List<Integer> list3;
  static List<Boolean> list4;

  public CustomAdapter(List<String> list, List<String> list2, List<Integer> list3, List<Boolean> list4){
    this.list = list;
    this.list2 = list2;
    this.list3 = list3;
    this.list4 = list4;
  }

  public class MyViewHolder extends RecyclerView.ViewHolder{
    TextView textView,textView2,textView3;// init the item view's
    AdapterView.OnItemSelectedListener itemSelectedListener;
    LinearLayout layout;

    public MyViewHolder(View itemView) {
      super(itemView);
      layout = (LinearLayout) itemView.findViewById(R.id.layout);
      textView = (TextView) itemView.findViewById(R.id.textView);
      textView2 = (TextView) itemView.findViewById(R.id.textView2);
      textView3 = (TextView) itemView.findViewById(R.id.textView3);


    }
  }

  @NonNull
  @Override
  public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.each_row, viewGroup, false);
    return new MyViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull final MyViewHolder viewHolder, final int i) {
    viewHolder.textView.setText(list2.get(i));
    viewHolder.textView2.setText(list.get(i));
    viewHolder.textView3.setText(i+1+"");
    if(list4.get(i)){
      viewHolder.layout.setBackgroundColor(Color.parseColor("#58857e"));
    }
    else{
      viewHolder.layout.setBackgroundColor(Color.TRANSPARENT);
    }
    viewHolder.layout.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if(list4.get(i)){
          list4.set(i, false);
          viewHolder.layout.setBackgroundColor(Color.TRANSPARENT);
        }
        else{
          list4.set(i, true);
          Log.d("click","click");
          viewHolder.layout.setBackgroundColor(Color.parseColor("#58857e"));
        }
      }
    });
  }

  @Override
  public int getItemCount() {
    return list.size();
  }


}

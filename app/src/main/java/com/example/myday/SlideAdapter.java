package com.example.myday;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SlideAdapter extends PagerAdapter {

    Context context;
    LayoutInflater linf;
    String first = "In our daily busy life we often forget special days or events by mistake. But now save the date and voilà this app will give you an alert !!";
    String second = "Exams, quiz, assignments are parts of student life. Middle of many works we might mix up the schedules. But not anymore when this app is here !";
    String third = "What can be more important than your health. So forgetting to take medicines or other health tasks can become risky.";
    public SlideAdapter(Context context){
        this.context=context;
    }
    public int[] slideimage = {
        R.drawable.gift,
        R.drawable.exam,
        R.drawable.health
    };
    public String[] heading = {
            first,second,third
    };
    @Override
    public int getCount() {
        return heading.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        linf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = linf.inflate(R.layout.slidelayout,container,false);
        ImageView imageView = view.findViewById(R.id.slideimage);
        TextView tv = view.findViewById(R.id.slideheading);
        imageView.setImageResource(slideimage[position]);
        tv.setText(heading[position]);

        Button gobut = view.findViewById(R.id.gobut);
        if(position!=getCount()-1)
            gobut.setVisibility(Button.INVISIBLE);
        gobut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,LoginActivity.class);
                context.startActivity(intent);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}

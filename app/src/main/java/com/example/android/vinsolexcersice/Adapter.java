package com.example.android.vinsolexcersice;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    public interface Callback {
        void callback(int pos, String string);
    }

    private Context context;
    private ArrayList<String> list;
    private Callback callback;
    private int height,width;

    public Adapter(Context context, ArrayList<String> list, Callback callback, int height, int width) {
        this.context = context;
        this.list = list;
        this.callback = callback;
        this.height = height;
        this.width = width;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = ((LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.item_view, viewGroup, false);
        view.getLayoutParams().width=width;
        view.getLayoutParams().height=height;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final String string = list.get(i);
        viewHolder.textView.setText(string);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimatorSet shrinkAnimation = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.shrink_to_middle);
                shrinkAnimation.setTarget(viewHolder.itemView);
                shrinkAnimation.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        AnimatorSet growAnimation = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.grow_from_middle);
                        growAnimation.setTarget(viewHolder.itemView);
                        growAnimation.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                callback.callback(viewHolder.getAdapterPosition(), string);
                            }
                        });
                        growAnimation.start();
                    }
                });
                shrinkAnimation.start();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);
        }
    }
}

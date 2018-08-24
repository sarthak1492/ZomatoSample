package com.sarthaksharma.zomato.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Movie;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sarthaksharma.zomato.CollectionsDetailsActivity;
import com.sarthaksharma.zomato.POJO.CollectionListItems;
import com.sarthaksharma.zomato.R;

import java.util.List;

/**
 * Created by sarthaksharma on 28/01/18.
 */

public class CollectionsAdapter extends RecyclerView.Adapter<CollectionsAdapter.MyViewHolder> {

    private List<CollectionListItems> moviesList;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    View itemView;
    Context context;
    private  Integer[] imageIDs = {R.drawable.images, R.drawable.food3, R.drawable.food10, R.drawable.maxresdefault, R.drawable.bcg,
            R.drawable.burger,
            R.drawable.chiken,
            R.drawable.images,
            R.drawable.images,
            R.drawable.food3,
            R.drawable.food10, R.drawable.maxresdefault, R.drawable.bcg, R.drawable.burger, R.drawable.chiken, R.drawable.images,
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;
        public ImageView imgBackground;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            //   year = (TextView) view.findViewById(R.id.year);
        }
    }

    public CollectionsAdapter(Context context,List<CollectionListItems> moviesList) {
        this.context=context;
        this.moviesList = moviesList;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.collections_items, parent, false);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CollectionsAdapter.MyViewHolder holder, final int position) {

        final CollectionListItems movie = moviesList.get(position);
        holder.title.setText(movie.getTitle());
        holder.genre.setText(movie.getGenre());
        //   holder.year.setText(movie.getYear());

        for(int i=0; i<imageIDs.length; i++){
            itemView.setBackgroundResource(imageIDs[position]);
        }
        holder.setIsRecyclable(false);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CollectionsDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //  intent.putExtra("ItemPath", images.get(position).getImage());
                editor.putString("Heading", moviesList.get(position).getTitle());
                editor.putInt("Images", imageIDs[position]);
                editor.commit();
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
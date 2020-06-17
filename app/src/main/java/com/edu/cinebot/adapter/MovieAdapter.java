package com.edu.cinebot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.cinebot.R;
import com.edu.cinebot.entity.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends BaseAdapter implements Filterable {

    private final LayoutInflater inflater;
    private List<Movie> listMoviesOut;
    private  List<Movie> listMoviesIn;

    public MovieAdapter(Context context, List<Movie> listMovies) {
        listMoviesOut = listMovies;
        listMoviesIn = listMovies;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() { return listMoviesOut.size();}

    @Override
    public Movie getItem(int position) {return listMoviesOut.get(position);
    }

    @Override
    public long getItemId(int i) {return 0;}

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listMoviesOut = (List<Movie>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<Movie> FilteredArrList = new ArrayList<>();
                if (listMoviesIn == null) {
                    listMoviesIn = new ArrayList<>(listMoviesOut);
                }
                if (constraint == null || constraint.length() == 0) {
                    results.count = listMoviesIn.size();
                    results.values = listMoviesIn;
                } else {

                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < listMoviesIn.size(); i++) {
                        String data = listMoviesIn.get(i).getName();
                        if (data.toLowerCase().contains(constraint.toString())) {
                            FilteredArrList.add(listMoviesIn.get(i));
                        }
                    }
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.movie_item_layout, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        Picasso.get().load(listMoviesOut.get(position).getImage()).into(holder.image);
        holder.txtName.setText("Titulo: "+listMoviesOut.get(position).getName());
        holder.txtDescription.setText(listMoviesOut.get(position).getDescription());
        return convertView;
    }

    class ViewHolder{
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtDescription)
        TextView txtDescription;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

package com.example.starsgallery.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.starsgallery.R;
import com.example.starsgallery.beans.Star;
import com.example.starsgallery.service.StarService;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StarAdapter extends RecyclerView.Adapter<StarAdapter.StarViewHolder> implements Filterable {

    private final Context context;
    private final List<Star> stars;
    private List<Star> starsFilter;

    public StarAdapter(Context context, List<Star> stars) {
        this.context = context;
        this.stars = new ArrayList<>(stars);
        this.starsFilter = new ArrayList<>(stars);
    }

    @NonNull
    @Override
    public StarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.star_item, parent, false);
        final StarViewHolder holder = new StarViewHolder(v);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View popup = LayoutInflater.from(context).inflate(R.layout.star_edit_item, null, false);

                final ImageView img = popup.findViewById(R.id.img);
                final RatingBar bar = popup.findViewById(R.id.ratingBar);
                final TextView idss = popup.findViewById(R.id.idss);

                if (holder.imgStar.getDrawable() != null && holder.imgStar.getDrawable() instanceof BitmapDrawable) {
                    Bitmap bitmap = ((BitmapDrawable) holder.imgStar.getDrawable()).getBitmap();
                    img.setImageBitmap(bitmap);
                } else {
                    img.setImageDrawable(holder.imgStar.getDrawable());
                }

                bar.setRating(holder.rating.getRating());

                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Star selectedStar = starsFilter.get(position);
                    idss.setText(String.valueOf(selectedStar.getId()));
                }

                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle("Notez :")
                        .setMessage("Donner une note entre 1 et 5 :")
                        .setView(popup)
                        .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                float s = bar.getRating();
                                int ids = Integer.parseInt(idss.getText().toString());

                                Star star = StarService.getInstance().findById(ids);
                                if (star != null) {
                                    star.setRating(s);
                                    StarService.getInstance().update(star);

                                    int pos = holder.getAdapterPosition();
                                    if (pos != RecyclerView.NO_POSITION) {
                                        notifyItemChanged(pos);
                                    } else {
                                        notifyDataSetChanged();
                                    }
                                }
                            }
                        })
                        .setNegativeButton("Annuler", null)
                        .create();

                dialog.show();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull StarViewHolder holder, int position) {
        Star star = starsFilter.get(position);

        int resId = context.getResources().getIdentifier(
                star.getImg(),
                "drawable",
                context.getPackageName()
        );

        if (resId != 0) {
            holder.imgStar.setImageResource(resId);
        } else {
            holder.imgStar.setImageResource(R.drawable.star);
        }

        holder.tvName.setText(star.getName());
        holder.rating.setRating(star.getRating());
    }

    @Override
    public int getItemCount() {
        return starsFilter.size();
    }

    public static class StarViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgStar;
        TextView tvName;
        RatingBar rating;

        public StarViewHolder(@NonNull View itemView) {
            super(itemView);
            imgStar = itemView.findViewById(R.id.imgStar);
            tvName = itemView.findViewById(R.id.tvName);
            rating = itemView.findViewById(R.id.rating);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Star> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(stars);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Star star : stars) {
                        if (star.getName().toLowerCase().contains(filterPattern)) {
                            filteredList.add(star);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                starsFilter.clear();
                starsFilter.addAll((List<Star>) results.values);
                notifyDataSetChanged();
            }
        };
    }
}
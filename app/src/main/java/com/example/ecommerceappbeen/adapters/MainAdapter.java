package com.example.ecommerceappbeen.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ecommerceappbeen.R;
import com.example.ecommerceappbeen.imageprocessing.SliderAdapterExample;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import xyz.hanks.library.bang.SmallBangView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private ArrayList<String> nameArrayList, desArrayList, priceArrayList, urlArrayList;
    private Context context;
    private String cancelLike;
    private SliderAdapterExample.OnItemClicked onClick;

    public interface OnItemClicked {
        void onItemClick(int position);
    }

    public MainAdapter(Context context, ArrayList<String> nameArrayList, ArrayList<String> desArrayList,
                       ArrayList<String> priceArrayList, ArrayList<String> urlArrayList) {
        this.nameArrayList = nameArrayList;
        this.desArrayList = desArrayList;
        this.priceArrayList = priceArrayList;
        this.urlArrayList = urlArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.rv_mainrow, parent, false);

        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {

        holder.getTxtDes().setText(desArrayList.get(position));
        holder.getTxtName().setText(nameArrayList.get(position));
        holder.getTxtPrice().setText(priceArrayList.get(position));
        Glide.with(context).asBitmap().placeholder(R.drawable.progress_animation)
                .load(urlArrayList.get(position)).diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.getImgProducts());

        getLike(holder, position);

        holder.getImgLike().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeOrRemoveLike(holder, position);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return nameArrayList.size();
    }


    public class MainViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName, txtDes, txtPrice;
        private ImageView imgProducts;
        private SmallBangView imgLike;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtDes = itemView.findViewById(R.id.txtDes);
            imgProducts = itemView.findViewById(R.id.imgProducts);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            imgLike = itemView.findViewById(R.id.rv_imageViewAnimation);

        }

        public TextView getTxtName() {
            return txtName;
        }

        public TextView getTxtDes() {
            return txtDes;
        }

        public SmallBangView getImgLike() {
            return imgLike;
        }

        public ImageView getImgProducts() {
            return imgProducts;
        }

        public TextView getTxtPrice() {
            return txtPrice;
        }
    }

    public void setOnClick(SliderAdapterExample.OnItemClicked onClick)
    {
        this.onClick=onClick;
    }



    public void cancelLike(int position) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("LikedBy");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {

                    for (ParseObject allQuotesObject : objects) {
                        if (Objects.equals(allQuotesObject.get("likedBy"), ParseUser.getCurrentUser().getEmail())) {

                            if (Objects.equals(allQuotesObject.get("desc"), cancelLike))

                                allQuotesObject.deleteInBackground();

                            Toast.makeText(context.getApplicationContext(), "Like Removed", Toast.LENGTH_SHORT).show();

                            allQuotesObject.saveInBackground();
                        }
                    }
                }
            }
        });

    }

    private void getLike(MainViewHolder holder, int position) {
        ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("LikedBy");

        queryAll.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0) {
                        for (ParseObject allQuotesObject : objects) {

                            if (Objects.equals(allQuotesObject.get("desc"), desArrayList.get(position)) &&
                                    Objects.equals(allQuotesObject.get("username"), ParseUser.getCurrentUser().getUsername())) {

                                holder.getImgLike().setSelected(true);

                            }

                        }
                    }
                }
            }
        });
    }

        private void likeOrRemoveLike (MainViewHolder holder,int position){

            if (!holder.getImgLike().isSelected()) {

                ParseObject myNewObject = new ParseObject("LikedBy");
                myNewObject.put("desc", desArrayList.get(position));
                myNewObject.put("name", nameArrayList.get(position));
                myNewObject.put("username", ParseUser.getCurrentUser().getUsername());
                myNewObject.put("likedBy", ParseUser.getCurrentUser().getEmail());
                myNewObject.put("photoURL", urlArrayList.get(position));
                myNewObject.put("price", priceArrayList.get(position));

                myNewObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(context.getApplicationContext(), "Liked", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                holder.getImgLike().setSelected(true);
                holder.getImgLike().likeAnimation();
            } else {
                cancelLike = desArrayList.get(position);
                cancelLike(position);
                holder.getImgLike().setSelected(false);
                holder.getImgLike().likeAnimation();
            }
        }

    }

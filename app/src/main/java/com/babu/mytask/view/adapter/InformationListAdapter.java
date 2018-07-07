package com.babu.mytask.view.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.babu.mytask.R;
import com.babu.mytask.data.model.InformationData;
import com.babu.mytask.view.activity.MainActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Babu on 7/4/2018.
 */
public class InformationListAdapter extends RecyclerView.Adapter<InformationListAdapter.InformationListViewHolder> {
    private Context context;
    private ArrayList<InformationData> informationDataArrayList;

    public InformationListAdapter(MainActivity context, ArrayList<InformationData> informationDataArrayList) {
        this.context = context;
        this.informationDataArrayList = informationDataArrayList;
    }

    class InformationListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView title;
        @BindView(R.id.tv_description)
        TextView description;
        @BindView(R.id.iv_imageView)
        ImageView image;

        InformationListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public InformationListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.informartion_item_list, parent, false);
        return new InformationListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(InformationListViewHolder holder, int position) {
        InformationData informationData = informationDataArrayList.get(position);
        if (!TextUtils.isEmpty(informationData.getTitle())) {
            holder.title.setText(informationData.getTitle());
        } else {
            holder.title.setText("");
        }
        if (!TextUtils.isEmpty(informationData.getDescription())) {
            holder.description.setText(informationData.getDescription());
        } else {
            holder.description.setText("");
        }
        String url = informationData.getImageUrl();
        if (!TextUtils.isEmpty(url)) {
            Glide.with(context).load(url).apply(new RequestOptions()
                    .placeholder(R.drawable.img_not_available)
                    .error(R.drawable.img_not_available)).into(holder.image);
        } else {
            holder.image.setImageResource(R.drawable.img_not_available);
        }
    }

    @Override
    public int getItemCount() {
        return (informationDataArrayList != null) ? informationDataArrayList.size() : 0;
    }
}

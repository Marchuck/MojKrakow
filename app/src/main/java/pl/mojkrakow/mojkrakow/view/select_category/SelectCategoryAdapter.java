package pl.mojkrakow.mojkrakow.view.select_category;

/**
 * Project "MojKrakow"
 * <p>
 * Created by Lukasz Marczak
 * on 22.03.2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.mojkrakow.mojkrakow.CategoryItemImageView;
import pl.mojkrakow.mojkrakow.R;
import pl.mojkrakow.mojkrakow.view.IssueCategory;


/**
 * @author Lukasz Marczak
 * @since 08.05.16.
 */
public class SelectCategoryAdapter extends RecyclerView.Adapter<SelectCategoryAdapter.SelectCategoryAdapterViewHolder> {

    List<IssueCategory> dataSet = new ArrayList<>();

    public SelectCategoryAdapter(List<IssueCategory> dataSet) {
        this.dataSet = dataSet;
    }

    public OnIssueChosenListener listener;

    public interface OnIssueChosenListener {
        void onChosen(IssueCategory category);
    }

    public SelectCategoryAdapter() {
        this(new ArrayList<IssueCategory>());
    }

    public void refreshDataset(List<IssueCategory> dataSet) {
        this.dataSet = dataSet;
        notifyItemRangeChanged(0, getItemCount());
        notifyDataSetChanged();
    }

    @Override
    public SelectCategoryAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, null, false);
        return new SelectCategoryAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SelectCategoryAdapterViewHolder holder, int position) {
        final IssueCategory item = dataSet.get(position);
        holder.textView.setText(String.valueOf(item));
        if (item.selected) {
            holder.imageView.scaleBoth(CategoryItemImageView.BIG);
        } else {
            holder.imageView.scaleBoth(CategoryItemImageView.SMALL);
        }
        holder.imageView.setBackgroundResource(item.res);
        holder.imageView.addOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    item.selected = !item.selected;
                    listener.onChosen(item);
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet == null ? 0 : dataSet.size();
    }

    public static class SelectCategoryAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        CategoryItemImageView imageView;

        public SelectCategoryAdapterViewHolder(View v) {
            super(v);
            imageView = (CategoryItemImageView) v.findViewById(R.id.image);
            textView = (TextView) v.findViewById(R.id.text);
        }
    }
}


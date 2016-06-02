package me.sivze.githubmoststarred.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.sivze.githubmoststarred.R;
import me.sivze.githubmoststarred.fragment.ReposFragment;
import me.sivze.githubmoststarred.model.ReposModel;
import me.sivze.githubmoststarred.util.Constants;
import me.sivze.githubmoststarred.util.Settings;

/**
 * Created by Siva on 6/1/2016.
 */
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemHolder> {

    private List<ReposModel> mItemsList;
    private int mDefaultColor;
    private ReposFragment.Callback mCallback;
    private Context mContext;

    public ItemsAdapter(
            ArrayList<ReposModel> reposList,
            int defaultColor,
            Context context,
            ReposFragment.Callback callback) {

        mItemsList = reposList;
        mDefaultColor = defaultColor;
        mContext = context;
        mCallback = callback;

    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_template_repos_grid, parent, false);
        return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(final ItemHolder holder, final int position) {
        final ReposModel itemData = mItemsList.get(position);

        String sortType = Settings.getPrefs(
                holder.mSortTypeValueTextView.getContext(),
                Constants.SORT_MODE,
                Constants.SORT_BY_STARS);

        holder.mInfoContainer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mCallback.onItemSelected(itemData, position);
            }
        });

        holder.mNameTextView.setText(itemData.name);
        holder.mUserTextView.setText(mContext.getString(R.string.owner,itemData.owner.login));
        holder.mCreatedDateTextView.setText(mContext.getString(R.string.created_at, itemData.getFormattedDate()));

        if (Constants.SORT_BY_STARS.equals(sortType)) {
            setIconForType(holder);
            holder.mSortTypeValueTextView.setText(String.valueOf(itemData.stars));
        } else {
            setIconForType(holder);
            holder.mSortTypeValueTextView.setText(String.valueOf(itemData.forks));
        }

        String imageUrl = itemData.owner.avatar_url;
        final FrameLayout container = holder.mInfoContainer;

        Picasso.with(holder.mAvatarImageView.getContext()).load(imageUrl).
                into(holder.mAvatarImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap posterBitmap = ((BitmapDrawable) holder.mAvatarImageView.getDrawable()).getBitmap();
                        Palette.from(posterBitmap).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                container.setBackgroundColor(ColorUtils.setAlphaComponent(palette.getMutedColor(mDefaultColor), 190)); //Opacity
                            }
                        });
                    }

                    @Override
                    public void onError() {
                    }
                });
    }

    private void setIconForType(ItemHolder holder) {
        if (Settings.getPrefs(holder.mSortTypeIconImageView.getContext(),
                Constants.SORT_MODE,
                Constants.SORT_BY_STARS).equals(Constants.SORT_BY_STARS)) {
            holder.mSortTypeIconImageView.setImageResource(R.drawable.ic_star);
        } else {
            holder.mSortTypeIconImageView.setImageResource(R.drawable.ic_fork);
        }
    }

    @Override
    public int getItemCount() {
        return mItemsList == null ? 0 : mItemsList.size();
    }

    public void addItems(List<ReposModel> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        mItemsList = data;
        notifyDataSetChanged();
    }

    public static final class ItemHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.grid_item_avatar_image_view)
        ImageView mAvatarImageView;

        @Bind(R.id.grid_item_name_text_view)
        TextView mNameTextView;

        @Bind(R.id.grid_item_sort_type_image_view)
        ImageView mSortTypeIconImageView;

        @Bind(R.id.grid_item_sort_type_text_view)
        TextView mSortTypeValueTextView;

        @Bind(R.id.grid_item_user_text_view)
        TextView mUserTextView;

        @Bind(R.id.grid_item_created_date_text_view)
        TextView mCreatedDateTextView;

        @Bind(R.id.grid_item_info_container)
        FrameLayout mInfoContainer;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}


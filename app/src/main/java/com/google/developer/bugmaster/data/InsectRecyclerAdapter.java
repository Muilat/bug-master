package com.google.developer.bugmaster.data;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.developer.bugmaster.InsectDetailsActivity;
import com.google.developer.bugmaster.R;
import com.google.developer.bugmaster.views.DangerLevelView;

/**
 * RecyclerView adapter extended with project-specific required methods.
 */

public class InsectRecyclerAdapter extends
        RecyclerView.Adapter<InsectRecyclerAdapter.InsectHolder> {


    /* ViewHolder for each insect item */
    public class InsectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //class variables
        TextView tv_name, tv_scientific_name;

        DangerLevelView tv_danger_level;

        public InsectHolder(View itemView) {
            super(itemView);

			tv_danger_level =  (DangerLevelView) itemView.findViewById(R.id.tv_danger_level);
			tv_name =  (TextView)itemView.findViewById(R.id.tv_friendly_name);
			tv_scientific_name =  (TextView)itemView.findViewById(R.id.tv_scientific_name);


        }

        @Override
        public void onClick(View v) {
//			int position = viewHolder.getAdapterPosition();
//			Insect insect = getItem(position);
//			int id = (int)viewHolder.itemView.getTag();
//			//TODO: turn insect to parcel
//			Intent InsectDetailsActivity = new Intent(mContext, InsectDetailsActivity.class);
//                insectDetailsIntent.putExtra(InsectDetailsActivity.EXTRA_INSECT_ID, id);
//                mContext.startActivity(insectDetailsIntent);
        }
    }

    private Cursor mCursor;
	private Context mContext;

	/**
     * Constructor using the context and the db cursor
     *
     * @param context the calling context/activity
     */
    public InsectRecyclerAdapter(Context context/*, Cursor cursor*/) {
        this.mContext = context;
//        this.mCursor = cursor;
    }

    @Override
    public InsectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the insect_list_item_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.insect_list_item, parent, false);
        final InsectHolder viewHolder = new InsectHolder(view);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                Insect insect = getItem(position);
//                int id = (int)viewHolder.itemView.getTag();
                //TODO: turn insect to parcel
                Intent insectDetailsIntent = new Intent(mContext, InsectDetailsActivity.class);
                insectDetailsIntent.putExtra(InsectDetailsActivity.EXTRA_INSECT, insect);
                mContext.startActivity(insectDetailsIntent);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(InsectHolder holder, int position) {

        mCursor.moveToPosition(position); // get to the right location in the cursor

        //Insect insect =getItem(position);
		Insect insect = new Insect(mCursor);

        //Set values
//        holder.itemView.setTag(id);

        holder.tv_name.setText(insect.getName()+"");
        holder.tv_scientific_name.setText(insect.getScientificName()+"");

//        DangerLevelView dangerLevelView = new DangerLevelView()
        DangerLevelView dangerLevelView = new DangerLevelView(mContext);
        holder.tv_danger_level.setDangerLevel( insect.getDangerLevel());

    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }

    /**
     * Return the {@link Insect} represented by this item in the adapter.
     *
     * @param position Adapter item position.
     *
     * @return A new {@link Insect} filled with this position's attributes
     *
     * @throws IllegalArgumentException if position is out of the adapter's bounds.
     */
    public Insect getItem(int position) {
        if (position < 0 || position >= getItemCount()) {
            throw new IllegalArgumentException("Item position is out of adapter's range");
        } else if (mCursor.moveToPosition(position)) {
            return new Insect(mCursor);
        }
        return null;
    }
	
	//TODO: find something relating for swapCursor;
}

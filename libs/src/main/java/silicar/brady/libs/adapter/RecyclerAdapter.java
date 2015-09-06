package silicar.brady.libs.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import silicar.brady.libs.view.util.RecyclerViewHolder;

/**
 * RecyclerView通用Adapter
 * Created by Work on 2015/9/6.
 * @version 1.0
 * @since 2015/9/6
 * @author 图图
 */
public abstract class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder recyclerViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

package android.room.play.com.nasadaily.adapters;

import android.content.Context;
import android.room.play.com.nasadaily.Listener.RecyclerViewClickListener;
import android.room.play.com.nasadaily.R;
import android.room.play.com.nasadaily.vo.ImageViewHolder;
import android.room.play.com.nasadaily.vo.NasaDailyVO;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Chirag Pc on 9/23/2015.
 */
public class ImageListAdapter extends RecyclerView.Adapter<ImageViewHolder> {

    private List<NasaDailyVO> nasaDailyVOList;
    private Context context;
    private RecyclerViewClickListener recyclerViewClickListener = new RecyclerViewClickListener();
    private View cardView = null;

    public ImageListAdapter(List<NasaDailyVO> nasaDailyVOList, Context context) {
        this.nasaDailyVOList = nasaDailyVOList;
        this.context = context;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        cardView = layoutInflater.inflate(R.layout.card_layout, viewGroup, false);
        cardView.setOnClickListener(recyclerViewClickListener);
        return new ImageViewHolder(cardView, context);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder imageViewHolder, int index) {
        imageViewHolder.setImage(nasaDailyVOList.get(index).getUri());
        imageViewHolder.getTextViewTitle().setText(nasaDailyVOList.get(index).getTitle());
        imageViewHolder.getTextViewDesc().setText(nasaDailyVOList.get(index).getExplanation());

        //Set Transition Name dynamically
        cardView.setTransitionName(context.getResources().getString(R.string.transition_cover_card) + index);
        imageViewHolder.getImageView().setTransitionName(context.getResources().getString(R.string.transition_cover_image) + index);
        imageViewHolder.getTextViewTitle().setTransitionName(context.getResources().getString(R.string.transition_cover_title) + index);
        imageViewHolder.getTextViewDesc().setTransitionName(context.getResources().getString(R.string.transition_cover_desc) + index);
    }

    @Override
    public int getItemCount() {
        return nasaDailyVOList.size();
    }
}

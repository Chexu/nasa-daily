package android.room.play.com.nasadaily.vo;

import android.content.Context;
import android.room.play.com.nasadaily.R;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by Chirag Pc on 9/23/2015.
 */
public class ImageViewHolder extends RecyclerView.ViewHolder {
    private ImageView imageView;
    private TextView textViewTitle;
    private TextView textViewDesc;
    private Context context;


    public ImageViewHolder(View itemView, Context context) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.thumbnail);
        textViewTitle = (TextView) itemView.findViewById(R.id.title);
        textViewDesc = (TextView) itemView.findViewById(R.id.description);
        this.context = context;
    }

    public TextView getTextViewDesc() {
        return textViewDesc;
    }

    public void setTextViewDesc(TextView textViewDesc) {
        this.textViewDesc = textViewDesc;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImage(String imageUrl) {
        if (imageView != null) {
            Picasso.with(context)
                    .load(imageUrl)
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                           /* Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                            Palette.generateAsync(bitmap,
                                    new Palette.PaletteAsyncListener() {
                                        @Override
                                        public void onGenerated(Palette palette) {
                                            Palette.Swatch vibrant = palette.getVibrantSwatch();
                                            if (vibrant != null) {
                                                itemView.setBackgroundColor(vibrant.getRgb());
                                                textViewTitle.setTextColor(vibrant.getTitleTextColor());
                                                textViewDesc.setTextColor(vibrant.getBodyTextColor());
                                            }
                                        }
                                    });*/
                        }

                        @Override
                        public void onError() {
                            imageView.setImageResource(R.drawable.ic_server_error);
                        }
                    });
            //RenderBitmapTask renderBitmapTask = new RenderBitmapTask(imageView);
            //renderBitmapTask.execute(imageUrl);
        }
    }

    public TextView getTextViewTitle() {
        return textViewTitle;
    }

    public void setTextViewTitle(TextView textViewTitle) {
        this.textViewTitle = textViewTitle;
    }

}

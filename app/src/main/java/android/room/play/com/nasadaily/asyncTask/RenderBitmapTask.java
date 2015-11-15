package android.room.play.com.nasadaily.asyncTask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by Chirag Pc on 8/22/2015.
 */
public class RenderBitmapTask extends AsyncTask<String, String, Bitmap> {
    private static final String TAG = RenderBitmapTask.class.getName();

    private ImageView imageView;

    public RenderBitmapTask(ImageView imageView) {
        this.imageView = imageView;
    }

    protected Bitmap doInBackground(String... urls) {
        Log.d(TAG, "Entry in doInBackground ");
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e(TAG, "Exception in doInBackground" + e.getMessage());
            e.printStackTrace();
        }
        Log.d(TAG, "Exit from doInBackground ");
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
    }
}

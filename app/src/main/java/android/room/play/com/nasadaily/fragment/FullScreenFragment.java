package android.room.play.com.nasadaily.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.room.play.com.nasadaily.R;
import android.room.play.com.nasadaily.util.Constants;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Chirag Pc on 10/1/2015.
 */
public class FullScreenFragment extends Fragment {
    private static final String TAG = FullScreenFragment.class.getName();
    private View fullScreenFragmentView;
    private ImageView imageView = null;
    private Bitmap bitmap = null;

    public static FullScreenFragment getInstance(Bitmap bitmap) {
        FullScreenFragment imageFragment = new FullScreenFragment();
        imageFragment.setBitmap(bitmap);
        return imageFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);
        if (savedState == null) {
            savedState = this.getArguments();
            setImage(bitmap);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (savedInstanceState == null) {
            savedInstanceState = this.getArguments();
        }
        fullScreenFragmentView = inflater.inflate(R.layout.fragment_full_screen, container, false);
        imageView = (ImageView) fullScreenFragmentView.findViewById(R.id.image_full);

        //fullScreenFragmentView.findViewById(R.id.image_full_screen_container).setTransitionName(savedInstanceState.getString(Constants.ARG_TRANSITION_NAME));
        imageView.setTransitionName(savedInstanceState.getString(Constants.ARG_TRANSITION_NAME));

        //((ActionBarActivity)getActivity()).getSupportActionBar().hide();
        // Hide the status bar.
        //View decorView = getActivity().getWindow().getDecorView();
        //int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_FULLSCREEN;
        //decorView.setSystemUiVisibility(uiOptions);

        return fullScreenFragmentView;
    }

    public void setImage(Bitmap imageBitmap) {
        if (imageView != null) {
            imageView.setImageBitmap(imageBitmap);
        }
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

}


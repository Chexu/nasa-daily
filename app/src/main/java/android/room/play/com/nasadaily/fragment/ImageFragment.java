package android.room.play.com.nasadaily.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.room.play.com.nasadaily.R;
import android.room.play.com.nasadaily.activity.AstronomyMainActivity;
import android.room.play.com.nasadaily.util.Constants;
import android.support.v7.app.ActionBarActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Chirag Pc on 8/22/2015.
 */
public class ImageFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = ImageFragment.class.getName();
    private String title = null;
    private View imageFragmentView;
    private ImageView imageView = null;
    private TextView textView = null;
    private ProgressBar progressBar;
    private String description = null;
    private TextView textViewDesc = null;
    private Bitmap bitmap = null;
    private String transitionName = "";

    public static ImageFragment getInstance(Bitmap bitmap) {
        ImageFragment imageFragment = new ImageFragment();
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
            title = savedState.getString(Constants.ARG_TITLE);
            description = savedState.getString(Constants.ARG_DESCRIPTION);
            setTitle(title);
            setDescription(description);
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
        transitionName = savedInstanceState.getString(Constants.ARG_TRANSITION_NAME);
        progressBar = (ProgressBar) getActivity().findViewById(R.id.progress_bar);
        imageFragmentView = inflater.inflate(R.layout.fragment_image_details, container, false);
        imageView = (ImageView) imageFragmentView.findViewById(R.id.image);
        textView = (TextView) imageFragmentView.findViewById(R.id.title);
        textViewDesc = (TextView) imageFragmentView.findViewById(R.id.description);

        imageFragmentView.findViewById(R.id.image_details_scroll_container).setTransitionName(savedInstanceState.getString(Constants.ARG_TRANSITION_CARD));
        imageView.setTransitionName(transitionName);
        textView.setTransitionName(savedInstanceState.getString(Constants.ARG_TRANSITION_TITLE));
        textViewDesc.setTransitionName(savedInstanceState.getString(Constants.ARG_TRANSITION_DESC));

        ((ActionBarActivity) getActivity()).getSupportActionBar().show();
        View decorView = getActivity().getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
        decorView.setSystemUiVisibility(uiOptions);
        imageView.setOnClickListener(this);
        return imageFragmentView;
    }

    public void onClick(View view) {
        Bundle fullScreenFragmentBundle = new Bundle();
        fullScreenFragmentBundle.putString(Constants.ARG_TRANSITION_NAME, imageView.getTransitionName());
        if (imageView.getDrawable() != null) {
            FullScreenFragment fullScreenFragment = FullScreenFragment.getInstance(((BitmapDrawable) imageView.getDrawable()).getBitmap());
            fullScreenFragment.setArguments(fullScreenFragmentBundle);

            // Inflate transitions to apply
            Transition changeTransform = TransitionInflater.from(view.getContext()).inflateTransition(R.transition.change_image_transform);
            //changeTransform.setStartDelay(300);
            Transition explodeTransform = TransitionInflater.from(view.getContext()).inflateTransition(android.R.transition.explode);
            //explodeTransform.setStartDelay(300);

            this.setSharedElementReturnTransition(changeTransform);
            this.setExitTransition(explodeTransform);

            fullScreenFragment.setSharedElementEnterTransition(changeTransform);
            fullScreenFragment.setEnterTransition(explodeTransform);

            View container = imageFragmentView.findViewById(R.id.image_details_fragment_container);

            FragmentTransaction fragmentTransaction = AstronomyMainActivity.fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.activity_fragment_first, fullScreenFragment)
                    .addSharedElement(imageView, imageView.getTransitionName())
                    .addSharedElement(container, container.getTransitionName())
                    .addToBackStack(null)
                    .commit();
        }
    }

    public void setImage(Bitmap imageBitmap) {
        if (imageView != null) {
            imageView.setImageBitmap(imageBitmap);
        }
    }

    public void setTitle(String title) {
        this.title = title;
        if (textView != null) {
            textView.setText(title.toCharArray(), 0, title.length());
        }
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        if (textViewDesc != null) {
            textViewDesc.setText(description.toCharArray(), 0, description.length());
        }
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getTransitionName() {
        return transitionName;
    }

    public void setTransitionName(String transitionName) {
        this.transitionName = transitionName;
    }
}

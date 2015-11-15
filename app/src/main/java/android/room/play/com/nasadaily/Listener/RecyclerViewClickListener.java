package android.room.play.com.nasadaily.Listener;

import android.app.FragmentTransaction;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.room.play.com.nasadaily.R;
import android.room.play.com.nasadaily.activity.AstronomyMainActivity;
import android.room.play.com.nasadaily.fragment.ImageFragment;
import android.room.play.com.nasadaily.fragment.RecyclerViewFragment;
import android.room.play.com.nasadaily.util.Constants;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Chirag Pc on 9/27/2015.
 */
public class RecyclerViewClickListener implements RecyclerView.OnClickListener {

    @Override
    public void onClick(View view) {
        RecyclerViewFragment recyclerViewFragment = (RecyclerViewFragment) AstronomyMainActivity.fragmentManager.findFragmentById(R.id.activity_fragment_first);
        Bundle imageFragmentBundle = new Bundle();
        ImageView imageView = (ImageView) view.findViewById(R.id.thumbnail);
        TextView textViewTitle = (TextView) view.findViewById(R.id.title);
        TextView textViewDesc = (TextView) view.findViewById(R.id.description);
        View cardView = recyclerViewFragment.getView().findViewById(R.id.card_view);
        View recyclerView = recyclerViewFragment.getView().findViewById(R.id.image_recycler_view);
        imageFragmentBundle.putString(Constants.ARG_TITLE, textViewTitle.getText().toString());
        imageFragmentBundle.putString(Constants.ARG_DESCRIPTION, textViewDesc.getText().toString());
        imageFragmentBundle.putString(Constants.ARG_TRANSITION_CARD, cardView.getTransitionName());
        imageFragmentBundle.putString(Constants.ARG_TRANSITION_NAME, imageView.getTransitionName());
        imageFragmentBundle.putString(Constants.ARG_TRANSITION_TITLE, textViewTitle.getTransitionName());
        imageFragmentBundle.putString(Constants.ARG_TRANSITION_DESC, textViewDesc.getTransitionName());

        if (imageView.getDrawable() != null) {
            ImageFragment imageFragment = ImageFragment.getInstance(((BitmapDrawable) imageView.getDrawable()).getBitmap());
            imageFragment.setArguments(imageFragmentBundle);

            // Inflate transitions to apply
            Transition changeTransform = TransitionInflater.from(view.getContext()).inflateTransition(R.transition.change_image_transform);
            //changeTransform.setStartDelay(300);
            Transition explodeTransform = TransitionInflater.from(view.getContext()).inflateTransition(android.R.transition.explode);
            //explodeTransform.setStartDelay(300);

            recyclerViewFragment.setSharedElementReturnTransition(changeTransform);
            recyclerViewFragment.setExitTransition(explodeTransform);

            imageFragment.setSharedElementEnterTransition(changeTransform);
            imageFragment.setEnterTransition(explodeTransform);

            FragmentTransaction fragmentTransaction = AstronomyMainActivity.fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.activity_fragment_first, imageFragment)
                    .addSharedElement(recyclerView, recyclerView.getTransitionName())
                    .addSharedElement(cardView, cardView.getTransitionName())
                    .addSharedElement(imageView, imageView.getTransitionName())
                    .addSharedElement(textViewTitle, textViewTitle.getTransitionName())
                    .addSharedElement(textViewDesc, textViewDesc.getTransitionName())
                    .addToBackStack(null)
                    .commit();
        }
    }
}

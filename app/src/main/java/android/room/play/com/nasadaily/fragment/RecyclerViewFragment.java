package android.room.play.com.nasadaily.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.room.play.com.nasadaily.R;
import android.room.play.com.nasadaily.adapters.ImageListAdapter;
import android.room.play.com.nasadaily.animators.FadeInAnimator;
import android.room.play.com.nasadaily.asyncTask.NasaImageLoadTask;
import android.room.play.com.nasadaily.util.Constants;
import android.room.play.com.nasadaily.vo.NasaDailyVO;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Chirag Pc on 9/27/2015.
 */
public class RecyclerViewFragment extends Fragment {
    private static final String TAG = RecyclerViewFragment.class.getName();
    private ImageListAdapter imageListAdapter = null;
    private List<NasaDailyVO> nasaDailyVOs = new ArrayList<NasaDailyVO>();
    private ProgressBar progressBar;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        imageListAdapter = new ImageListAdapter(nasaDailyVOs, getActivity());
    }

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        progressBar = (ProgressBar) getActivity().findViewById(R.id.progress_bar);

        View recyclerFragmentView = inflater.inflate(R.layout.fragment_recycler_layout, container, false);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView recyclerView = (RecyclerView) recyclerFragmentView.findViewById(R.id.image_recycler_view);
        recyclerView.setAdapter(imageListAdapter);
        recyclerView.setItemAnimator(new FadeInAnimator());
        recyclerView.setLayoutManager(layoutManager);
        if (savedInstanceState == null) {
            getDailyImage();
        }

        return recyclerFragmentView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_refresh:
                nasaDailyVOs.clear();
                getDailyImage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getDailyImage() {

        if (nasaDailyVOs.size() > 0) {

        } else {
            progressBar.setVisibility(View.VISIBLE);
            Calendar calendar = Calendar.getInstance();
            //int startDay = calendar.get(Calendar.DAY_OF_MONTH);
            List<String> dateList = new ArrayList<String>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //int endDay = Math.abs(startDay - Constants.DATE_DIFFERENCE);

            for (int dayVar = 0; dayVar <= Constants.DATE_DIFFERENCE; dayVar++) {
                dateList.add(sdf.format(calendar.getTimeInMillis()));
                calendar.add(Calendar.DATE, -1);
            }
            NasaImageLoadTask nasaImageLoadTask = new NasaImageLoadTask(nasaDailyVOs, imageListAdapter, progressBar);
            nasaImageLoadTask.execute(dateList);
            nasaImageLoadTask = null;
        }
    }
}

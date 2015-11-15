package android.room.play.com.nasadaily.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.room.play.com.nasadaily.R;
import android.room.play.com.nasadaily.fragment.RecyclerViewFragment;
import android.room.play.com.nasadaily.util.AppUtils;
import android.room.play.com.nasadaily.util.Constants;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class AstronomyMainActivity extends ActionBarActivity {

    public static FragmentManager fragmentManager;
    private ProgressBar progressBar;
    private ImageView errorImageView;
    private TextView errorTextView;
    private TextView errorTextViewDesc;
    private View errorContainer;
    private Toolbar toolbar;
    private static final String TAG = AstronomyMainActivity.class.getName();
    private RecyclerViewFragment recyclerViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_of_the_day);
        initializeComponents();
        fragmentManager = getFragmentManager();
        checkInternetConnectivity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_image_of_the_day, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    private void checkInternetConnectivity() {
        progressBar.setVisibility(View.VISIBLE);
        Toast messageToast = Toast.makeText(getApplicationContext(), R.string.internet_check, Toast.LENGTH_SHORT);
        messageToast.show();
        errorContainer.setVisibility(View.GONE);
        if (AppUtils.isNetworkAvailable(getApplicationContext())) {
            recyclerViewFragment = new RecyclerViewFragment();
            FragmentTransaction fragmentTransaction = AstronomyMainActivity.fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.activity_fragment_first, recyclerViewFragment).commit();
        } else {
            showErrorOnScreen(Constants.MSG_NETWORK_ERROR_CODE);
        }
    }

    public boolean showErrorOnScreen(int errorCode) {
        errorContainer.setVisibility(View.VISIBLE);
        switch (errorCode) {
            case Constants.MSG_NETWORK_ERROR_CODE:
                errorImageView.setImageResource(R.drawable.ic_network_notification);
                errorTextView.setText(R.string.network_error);
                errorTextViewDesc.setText(R.string.network_error_desc);
                break;
            case Constants.MSG_SERVER_ERROR_CODE:
                errorImageView.setImageResource(R.drawable.ic_server_error);
                errorTextView.setText(R.string.server_error);
                errorTextViewDesc.setText(R.string.server_error_desc);
                break;
            default:
                errorImageView.setImageResource(R.drawable.ic_server_error);
                errorTextView.setText(R.string.server_error);
                errorTextViewDesc.setText(R.string.server_error_desc);
                break;
        }
        errorContainer.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        return true;
    }

    private void initializeComponents() {
        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        errorImageView = (ImageView) findViewById(R.id.image_error_msg);
        errorTextView = (TextView) findViewById(R.id.error_msg);
        errorTextViewDesc = (TextView) findViewById(R.id.error_msg_desc);
        errorContainer = findViewById(R.id.error_container);
        setSupportActionBar(toolbar);
    }
}

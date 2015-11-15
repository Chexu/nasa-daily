package android.room.play.com.nasadaily.asyncTask;

import android.os.AsyncTask;
import android.room.play.com.nasadaily.adapters.ImageListAdapter;
import android.room.play.com.nasadaily.util.Constants;
import android.room.play.com.nasadaily.vo.NasaDailyVO;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Chirag Pc on 8/22/2015.
 */
public class NasaImageLoadTask extends AsyncTask<List<String>, Void, List<NasaDailyVO>> {

    private static final String TAG = NasaImageLoadTask.class.getName();

    private NasaDailyVO nasaDailyVO = null;
    private HashMap<String, String> postDataParams = new HashMap<>();
    private List<NasaDailyVO> nasaDailyVOs = null;
    private ImageListAdapter imageListAdapter = null;
    private ProgressBar progressBar;

    public NasaImageLoadTask(List<NasaDailyVO> nasaDailyVOs, ImageListAdapter imageListAdapter, ProgressBar progressBar) {
        this.imageListAdapter = imageListAdapter;
        this.nasaDailyVOs = nasaDailyVOs;
        this.progressBar = progressBar;
    }

    @Override
    protected List<NasaDailyVO> doInBackground(List<String>... params) {
        Log.d(TAG, " Entry in doInBackground ");
        List<NasaDailyVO> nasaDailyVOList = new ArrayList<NasaDailyVO>();
        try {
            List<String> passedParams = params[0];
           /* if(passedParams.get(Constants.PARAM_DATE) != null){
                postDataParams.putAll(passedParams);
            }*/
            for (String date : passedParams) {
                postDataParams.put(Constants.PARAM_DATE, date);
                postDataParams.put(Constants.PARAM_API_KEY, Constants.PARAM_API_VALUE);
                postDataParams.put(Constants.PARAM_FORMAT, Constants.PARAM_JSON);
                postDataParams.put(Constants.TAG_CONCEPTS, Constants.TRUE);
                nasaDailyVO = getNasaHttpResponse(Constants.NASA_APOD_URL, postDataParams);
                if (nasaDailyVO != null) {
                    nasaDailyVOList.add(nasaDailyVO);
                }
            }

        } catch (Exception e) {
            Log.e(TAG, "Exception in doInBackground ", e);
        }
        return nasaDailyVOList;
    }

    @Override
    protected void onPostExecute(List<NasaDailyVO> nasaDailyVOList) {
        Log.d(TAG, "Entry in NasaImageLoadTask.onPostExecute ");
        super.onPostExecute(nasaDailyVOList);
        if (nasaDailyVOList.size() == 0) {
            /*Toast errorToast = Toast.makeText(imageFragment.getActivity().getApplicationContext(), R.string.server_error_desc, Toast.LENGTH_SHORT);
            errorToast.show();
            ImageView imageView = imageFragment.getImageView();
            imageView.setImageResource(R.drawable.ic_server_error);
            imageView.setMinimumHeight(20);*/
        } else {
            nasaDailyVOs.addAll(nasaDailyVOList);
            imageListAdapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
        }
        Log.d(TAG, "Exit from onPostExecute ");
    }

    public NasaDailyVO getNasaHttpResponse(String nasaApodUrl, HashMap<String, String> postDataParams) {
        Log.d(TAG, "Entry getNasaHttpResponse ");
        InputStream inputStream = null;
        String response = "";
        NasaDailyVO nasaDailyVO = null;
        URL url = null;
        OutputStream outputStream = null;
        BufferedWriter bufferedWriter = null;
        int responseCode = -1;
        HttpsURLConnection httpsURLConnection = null;
        try {
            url = new URL(nasaApodUrl + "?" + getPostDataString(postDataParams));
            httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.connect();

            responseCode = httpsURLConnection.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                response = convertInputStreamToString(httpsURLConnection.getInputStream());
                nasaDailyVO = convertStringToNasaDailyVO(response);
            } else {
                String error = convertInputStreamToString(httpsURLConnection.getErrorStream());
            }

        } catch (Exception e) {
            httpsURLConnection.disconnect();
            Log.e(TAG, "Exception in getNasaDailyVO", e);
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (httpsURLConnection != null) {
                    httpsURLConnection.disconnect();
                }
            } catch (Exception ex) {
                Log.e(TAG, "Exception while closing streams in getNasaDailyVO", ex);
            }
        }
        Log.d(TAG, "Exit from getNasaDailyVO ");
        return nasaDailyVO;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        Log.d(TAG, "Entry in getPostDataString ");
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        Log.d(TAG, "Exit getPostDataString ");
        return result.toString();
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        Log.d(TAG, "Entry convertInputStreamToString ");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }
        inputStream.close();
        Log.d(TAG, "Exit convertInputStreamToString ");
        return result;
    }

    private NasaDailyVO convertStringToNasaDailyVO(String response) throws Exception {
        Log.d(TAG, "Entry in convertStringToNasaDailyVO ");
        NasaDailyVO nasaDailyVO = null;
        JSONObject jsonObject = null;
        String[] concepts;
        try {
            nasaDailyVO = new NasaDailyVO();
            jsonObject = new JSONObject(response);
            nasaDailyVO.setTitle(jsonObject.getString(Constants.TAG_TITLE));
            nasaDailyVO.setUri(jsonObject.getString(Constants.TAG_URL));
            nasaDailyVO.setExplanation(jsonObject.getString(Constants.TAG_EXPLANATION));
            /*JSONArray jsonArray = jsonObject.getJSONArray(Constants.TAG_CONCEPTS);
            concepts = new String[jsonArray.length()];
            for(int count = 0; count<jsonArray.length(); count++){
                concepts[count] = jsonArray.getString(count);
            }
            nasaDailyVO.setConcepts(concepts);*/
            nasaDailyVO.setMediaType(jsonObject.getString(Constants.TAG_MEDIA_TYPE));
        } catch (Exception e) {
            Log.e(TAG, "Exception in convertStringToNasaDailyVO", e);
            throw new Exception("Exception in NasaImageLoadTask.convertStringToNasaDailyVO", e);
        }
        Log.d(TAG, "Exit from convertStringToNasaDailyVO ");
        return nasaDailyVO;
    }

}

package me.sivze.githubmoststarred.loader;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import me.sivze.githubmoststarred.model.ReposModel;
import me.sivze.githubmoststarred.model.ReposResponseModel;
import me.sivze.githubmoststarred.service.Services;
import me.sivze.githubmoststarred.util.Constants;
import me.sivze.githubmoststarred.util.Settings;
import retrofit.Call;
import retrofit.Response;

/**
 * Created by Siva on 6/1/2016.
 * Retrieves repositories from the GitHub web server
 */
public class ReposLoader extends BaseTaskLoader{

    private static final String TAG = "ReposLoader";

    public ReposLoader(Context context) {
        super(context);
    }

    @Override
    public List<ReposModel> loadInBackground() {

        //get date before seven days
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        String query = Constants.CREATED_SYNTAX+new SimpleDateFormat(Constants.DATE_FORMAT).format(cal.getTime());

        Call<ReposResponseModel> createdCall = Services.getGitHubService().getRepos(
                query,
                Settings.getPrefs(getContext(), Constants.SORT_MODE, Constants.SORT_BY_STARS),
                Constants.ORDER_BY);
        try {
            Response<ReposResponseModel> result = createdCall.execute();
            return result.body().getRepos();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "IOException occurred in loadInBackground()");
        }
        return null;
    }
}

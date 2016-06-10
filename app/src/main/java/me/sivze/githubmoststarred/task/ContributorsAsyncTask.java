package me.sivze.githubmoststarred.task;

import android.util.Log;
import android.widget.ProgressBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import me.sivze.githubmoststarred.model.ContributorsModel;
import me.sivze.githubmoststarred.service.Services;
import retrofit.Call;
import retrofit.Response;

/**
 * Created by Siva on 6/2/2016.
 */
public class ContributorsAsyncTask extends BaseAsyncTask<ContributorsModel> {

    private static final String TAG = ContributorsAsyncTask.class.getSimpleName();

    private String mContributorName;
    private String mRepoName;

    public ContributorsAsyncTask(
            String contributorName,
            String repoName,
            ProgressBar mProgressBar,
            FetchDataListener mListener) {

        super(mProgressBar, mListener);
        this.mContributorName = contributorName;
        this.mRepoName = repoName;
    }

    @Override
    protected ArrayList<ContributorsModel> doInBackground(Void... params) {

        Call<ContributorsModel[]> createdCall = Services.getGitHubService().getContributors(
                mContributorName,
                mRepoName);
        try {
            Response<ContributorsModel[]> results = createdCall.execute();
            return new ArrayList<ContributorsModel>(Arrays.asList(results.body()));
        } catch (IOException e) {
            Log.e(TAG, "IOException occurred in doInBackground()");
        }
        return null;
    }
}

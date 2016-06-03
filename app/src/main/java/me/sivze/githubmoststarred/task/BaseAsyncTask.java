package me.sivze.githubmoststarred.task;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

/**
 * Created by Siva on 6/2/2016.
 */
public abstract class BaseAsyncTask<T>  extends AsyncTask<Void, Void, ArrayList<T>> {

    private ProgressBar mProgressBar;
    private FetchDataListener mListener;

    public interface FetchDataListener<T> {
        public void onFetchData(ArrayList<T> resultList);
    }

    public BaseAsyncTask(ProgressBar mProgressBar, FetchDataListener mListener) {
        this.mProgressBar = mProgressBar;
        this.mListener = mListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(ArrayList<T> result) {
        super.onPostExecute(result);
        mProgressBar.setVisibility(View.GONE);
        if (mListener != null) {
            mListener.onFetchData(result);
        }
    }
}

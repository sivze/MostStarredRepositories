package me.sivze.githubmoststarred.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Siva on 6/1/2016.
 */
public abstract class BaseTaskLoader<T> extends AsyncTaskLoader<List<T>> {

    private List<T> mResults;
    
    public BaseTaskLoader(Context context){
        super(context);
    }

    @Override
    public void deliverResult(List<T> items) {
        mResults = items;
        if (isStarted()) {
            super.deliverResult(items);
        }
    }

    @Override
    protected void onStartLoading() {
        if (mResults != null) {
            deliverResult(mResults);
        }

        if (takeContentChanged() || mResults == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
    }
}

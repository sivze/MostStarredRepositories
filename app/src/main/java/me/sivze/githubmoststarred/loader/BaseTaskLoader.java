package me.sivze.githubmoststarred.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Siva on 6/1/2016.
 */
public abstract class BaseTaskLoader<T> extends AsyncTaskLoader<List<T>> {

    private List<T> mResults;

    public BaseTaskLoader(Context context) {
        super(context);
    }

    /**
     * Called when there is new data to deliver to the client.  The
     * super class will take care of delivering it; the implementation
     * here just adds a little more logic.
     */
    @Override
    public void deliverResult(List<T> items) {
        mResults = items;
        if (isStarted()) {
            super.deliverResult(items);
        }
    }

    /**
     * Handles the request to start the loader
     */
    @Override
    protected void onStartLoading() {
        //if we currently have results available, deliver it immediately
        if (mResults != null) {
            deliverResult(mResults);
        }
        //If content changed since last time it was loaded or if it is not currently available
        if (takeContentChanged() || mResults == null) {
            forceLoad(); //force an asynchronous load ignoring the previous loaded data set and loads new data set
        }
    }

    @Override
    protected void onStopLoading() {
        //attempt to cancel the load task if possible
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        //at this point we can release the resources if needed
        if(mResults!=null){
            mResults = null;
        }
    }
}

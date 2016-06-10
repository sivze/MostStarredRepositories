package me.sivze.githubmoststarred.fragment;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.sivze.githubmoststarred.R;
import me.sivze.githubmoststarred.adapter.ItemsAdapter;
import me.sivze.githubmoststarred.loader.ReposLoader;
import me.sivze.githubmoststarred.model.ReposModel;
import me.sivze.githubmoststarred.ui.SpacesItemDecoration;
import me.sivze.githubmoststarred.util.Constants;
import me.sivze.githubmoststarred.util.Settings;

/**
 * Created by Siva on 6/1/2016.
 */
public class ReposFragment
        extends Fragment
        implements LoaderManager.LoaderCallbacks<List<ReposModel>>,
        SwipeRefreshLayout.OnRefreshListener{

    public static final String LOG_TAG = ReposFragment.class.getSimpleName();

    @Bind(R.id.fragement_repos_swipe_refresh_layout)
    SwipeRefreshLayout mReposSwipeRefreshLayout;

    @Bind(R.id.fragment_repos_recycler_view)
    RecyclerView mReposRecyclerView;

    private ArrayList<ReposModel> mReposList;
    private ItemsAdapter mReposAdapter;

    public interface Callback {
        void onItemSelected(ReposModel reposItem, int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_repos, container, false);
        ButterKnife.bind(this, view);

        int gridColumns = getResources().getInteger(R.integer.grid_columns);
        int progressViewOffsetStart = getResources().getInteger(R.integer.progress_view_offset_start);
        int progressViewOffsetEnd = getResources().getInteger(R.integer.progress_view_offset_end);

        //set progress bar color
        mReposSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark);

        //set progress bar draggable length
        mReposSwipeRefreshLayout.setProgressViewOffset(true, progressViewOffsetStart, progressViewOffsetEnd);

        final GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), gridColumns);

        mReposRecyclerView.setLayoutManager(mLayoutManager);

        //for spacing between items "1dp"
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.grid_item_spacing);
        mReposRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        int colorPrimaryLight = ContextCompat.getColor(getActivity(), (R.color.colorPrimaryTransparent));

        mReposAdapter = new ItemsAdapter(
                mReposList,
                colorPrimaryLight,
                getActivity(),
                (Callback) getActivity());

        mReposRecyclerView.setAdapter(mReposAdapter);

        //set listener for onRefreshListener that register the swipe refresh event to be triggered,
        // otherwise it will be loading indeterminately
        mReposSwipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mReposList = new ArrayList<>();
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String sortType;
        boolean result;

        switch (item.getItemId()) {
            case R.id.menu_main_starred:
                sortType = Constants.SORT_BY_STARS;
                result = true;
                break;
            case R.id.menu_main_forked:
                sortType = Constants.SORT_BY_FORKS;
                result = true;
                break;
            default:
                sortType = Constants.SORT_BY_STARS;
                result = super.onOptionsItemSelected(item);
                break;
        }

        item.setChecked(true);
        Settings.savePrefs(getActivity(), Constants.SORT_MODE, sortType);
        restartLoader();
        mReposSwipeRefreshLayout.setRefreshing(true);
        return result;
    }

    private void restartLoader() {
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public Loader<List<ReposModel>> onCreateLoader(int id, Bundle args) {
        return new ReposLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<ReposModel>> loader, List<ReposModel> data) {
        mReposSwipeRefreshLayout.setRefreshing(false);
        mReposAdapter.addItems(data);

        Snackbar.make(
                getView(),
                data == null || data.isEmpty() ? R.string.load_failed : R.string.load_success,
                Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public void onLoaderReset(Loader<List<ReposModel>> loader) {
        //clear the data in the adapter
        mReposSwipeRefreshLayout.setRefreshing(false);
        mReposAdapter.addItems(null);
    }

    @Override
    public void onRefresh() {
        restartLoader();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}

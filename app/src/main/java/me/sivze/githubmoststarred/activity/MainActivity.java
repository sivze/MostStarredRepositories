package me.sivze.githubmoststarred.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import me.sivze.githubmoststarred.R;
import me.sivze.githubmoststarred.fragment.ContributorsFragment;
import me.sivze.githubmoststarred.fragment.ReposFragment;
import me.sivze.githubmoststarred.model.ReposModel;
import me.sivze.githubmoststarred.util.Constants;

public class MainActivity
        extends AppCompatActivity
        implements ReposFragment.Callback {

    private static final String TAG = MainActivity.class.getSimpleName();
    private boolean mTwoPane = true;
    private int selectedPosition = RecyclerView.NO_POSITION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.contributors_container) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contributors_container,
                        ContributorsFragment.newInstance(new Bundle()),
                        ContributorsFragment.TAG).commit();
            } else {
                selectedPosition = savedInstanceState.getInt(Constants.POSITION_KEY);
            }
        } else {
            mTwoPane = false;
            getSupportActionBar().setElevation(0f);
        }
    }

    /**
     * This overriden method used as listener when user select a item in the grid.
     *
     * @param reposData selected repo
     * @param position  selected position in the grid
     */
    @Override
    public void onItemSelected(ReposModel reposData, int position) {
        Log.d(TAG, "onItemSelected() called");
        selectedPosition = position;
        if (mTwoPane) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Bundle args = new Bundle();
            args.putParcelable(Constants.REPO_KEY, reposData);
            ContributorsFragment fragment = ContributorsFragment.newInstance(args);
            fragmentTransaction.replace(R.id.contributors_container, fragment, ContributorsFragment.TAG).commit();
        }
        else{
            Intent openDetailIntent = new Intent(this, ContributorsActivity.class);
            openDetailIntent.putExtra(Constants.REPO_KEY, reposData);
            startActivity(openDetailIntent);
        }
    }
    public int getSelectedPosition() {
        return selectedPosition;
    }
}

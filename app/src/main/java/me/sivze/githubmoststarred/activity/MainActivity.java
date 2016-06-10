package me.sivze.githubmoststarred.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import me.sivze.githubmoststarred.R;
import me.sivze.githubmoststarred.fragment.ReposFragment;
import me.sivze.githubmoststarred.model.ReposModel;
import me.sivze.githubmoststarred.util.Constants;

public class MainActivity
        extends AppCompatActivity
        implements ReposFragment.Callback {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        Intent openDetailIntent = new Intent(this, ContributorsActivity.class);
        openDetailIntent.putExtra(Constants.REPO_KEY, reposData);
        startActivity(openDetailIntent);
    }
}

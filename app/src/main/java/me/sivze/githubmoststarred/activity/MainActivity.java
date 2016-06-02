package me.sivze.githubmoststarred.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import me.sivze.githubmoststarred.R;
import me.sivze.githubmoststarred.fragment.ReposFragment;
import me.sivze.githubmoststarred.model.ReposModel;
import me.sivze.githubmoststarred.util.Constants;

public class MainActivity
        extends AppCompatActivity
        implements ReposFragment.Callback{

    private static final String TAG = "MainActivity";
    private int selectedPosition = RecyclerView.NO_POSITION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.SELECTED_ITEM_POSITION_KEY, selectedPosition);
    }

    /**
     * This overriden method used as listener when user select a item in the grid.
     * @param reposData selected repo
     * @param position selected position in the grid
     */
    @Override
    public void onItemSelected(ReposModel reposData, int position) {
        Log.d(TAG, "onItemSelected() called");
        selectedPosition = position;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }
}

package me.sivze.githubmoststarred.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.sivze.githubmoststarred.R;
import me.sivze.githubmoststarred.fragment.ContributorsFragment;

/**
 * Created by Siva on 6/2/2016.
 */
public class ContributorsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributors);

        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Bundle bundle = new Bundle(getIntent().getExtras());
            fragmentTransaction.add(R.id.contributors_container, ContributorsFragment.newInstance(bundle)).commit();
        }
    }
}

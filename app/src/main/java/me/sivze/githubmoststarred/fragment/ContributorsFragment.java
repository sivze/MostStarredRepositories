package me.sivze.githubmoststarred.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.sivze.githubmoststarred.R;
import me.sivze.githubmoststarred.model.ContributorsModel;
import me.sivze.githubmoststarred.model.ReposModel;
import me.sivze.githubmoststarred.task.BaseAsyncTask;
import me.sivze.githubmoststarred.task.ContributorsAsyncTask;
import me.sivze.githubmoststarred.util.Constants;

/**
 * Created by Siva on 6/2/2016.
 */
public class ContributorsFragment extends Fragment{

    @Bind(R.id.contributors_linear_layout)
    LinearLayout mContributorsLinearLayout;

    @Bind(R.id.contributor_progress_bar)
    ProgressBar mContributorProgressBar;

    private ReposModel mReposData;
    private ArrayList<ContributorsModel> mContributors;
    private ContributorsAsyncTask contributorsAsyncTask;

    public static ContributorsFragment newInstance(Bundle bundle) {
        ContributorsFragment fragment = new ContributorsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(Constants.CONTRIBUTORS_LIST_KEY, mContributors);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mReposData = getArguments().getParcelable(Constants.REPO_KEY);
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contributors, container, false);

        ButterKnife.bind(this, view);

        if (savedInstanceState != null) {
            mContributors = savedInstanceState.getParcelableArrayList(Constants.CONTRIBUTORS_LIST_KEY);
            addContributorViews(mContributors);
        } else {
            executeTasks(mReposData);
        }
        return view;
    }

    /**
     * Runs tasks in background and retrives contributors
     * @param reposData current repoData
     */
    private void executeTasks(ReposModel reposData) {

        if (reposData == null) {
            return;
        }

        contributorsAsyncTask = new ContributorsAsyncTask(
                reposData.owner.login,
                reposData.name,
                mContributorProgressBar,
                new BaseAsyncTask.FetchDataListener<ContributorsModel>() {
                    @Override
                    public void onFetchData(ArrayList<ContributorsModel> resultList) {
                        mContributors = resultList;
                        addContributorViews(mContributors);
                    }
                });

        contributorsAsyncTask.execute();
    }

    private void addContributorViews(List<ContributorsModel> resultList) {

        final LayoutInflater inflater = LayoutInflater.from(getActivity());

        if (resultList != null && !resultList.isEmpty()) {

            for (final ContributorsModel contributor : resultList) {

                final View contributorView = inflater.inflate(
                        R.layout.item_template_contributors_list,
                        mContributorsLinearLayout,
                        false);

                contributorView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openUrlIntent(contributor.profileUrl);
                    }
                });

                final ImageView contributorAvatar = ButterKnife.findById(contributorView, R.id.contributor_avatar_image_view);
                final TextView contributorName = ButterKnife.findById(contributorView, R.id.contributor_name_text_view);

                contributorName.setText(contributor.name);

                Picasso.with(getActivity())
                        .load(contributor.avatarUrl)
                        .into(contributorAvatar);
                mContributorsLinearLayout.addView(contributorView);
            }
        }
    }

    private void openUrlIntent(String url) {
        Intent urlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(urlIntent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

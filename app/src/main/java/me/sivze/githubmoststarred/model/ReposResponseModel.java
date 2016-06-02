package me.sivze.githubmoststarred.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Siva on 6/1/2016.
 */


public class ReposResponseModel
{
    @SerializedName("items")
    private List<ReposModel> repos;

    public int total_count;
    public boolean incomplete_results;

    public List<ReposModel> getRepos() {
        return repos;
    }

}

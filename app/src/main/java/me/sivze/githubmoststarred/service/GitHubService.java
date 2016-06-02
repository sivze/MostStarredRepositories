package me.sivze.githubmoststarred.service;

import me.sivze.githubmoststarred.model.ReposResponseModel;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Siva on 6/1/2016.
 */
public interface GitHubService {
    @GET("/search/repositories")
    Call<ReposResponseModel> getRepos(
            @Query("q") String query,
            @Query("sort") String sortBy,
            @Query("order") String orderBy);

//    @GET("/repos")
//    Call<ContributorsModel> getContributors(@Path("id") long , @Query("") String Key);

}

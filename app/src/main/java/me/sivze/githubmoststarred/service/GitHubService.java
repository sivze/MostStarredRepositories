package me.sivze.githubmoststarred.service;

import me.sivze.githubmoststarred.model.ContributorsModel;
import me.sivze.githubmoststarred.model.ReposResponseModel;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Siva on 6/1/2016.
 * Retrofit turns your HTTP API into a java interface
 */
public interface GitHubService {
    @GET("/search/repositories")
    Call<ReposResponseModel> getRepos(
            @Query("q") String query,
            @Query("sort") String sortBy,
            @Query("order") String orderBy);

    @GET("/repos/{login}/{name}/contributors")
    Call<ContributorsModel[]> getContributors(
            @Path("login") String contributorLogin,
            @Path("name") String repoName);

}

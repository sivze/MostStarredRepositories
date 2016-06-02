package me.sivze.githubmoststarred.service;

import me.sivze.githubmoststarred.util.Constants;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Siva on 6/1/2016.
 */
public class Services {
    private static GitHubService gitHubService;

    static {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.GITHUB_API_BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build();
        gitHubService = retrofit.create(GitHubService.class);
    }

    public static GitHubService getGitHubService() {
        return gitHubService;
    }
}

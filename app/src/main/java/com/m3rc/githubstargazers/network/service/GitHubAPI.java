package com.m3rc.githubstargazers.network.service;

import com.m3rc.githubstargazers.network.output.GitHubUser;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitHubAPI {

    @GET("/repos/{owner}/{repo}/stargazers")
    Observable<Response<List<GitHubUser>>> getStargazers(
            @Path("owner") String owner,
            @Path("repo") String repo,
            @Query("page") int page);
}

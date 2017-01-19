package com.m3rc.githubstargazers.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.m3rc.githubstargazers.R;
import com.m3rc.githubstargazers.network.output.GitHubUser;
import com.m3rc.githubstargazers.network.service.GitHubAPI;
import com.m3rc.githubstargazers.network.service.GitHubAPIService;
import com.m3rc.githubstargazers.view.adapter.GitHubUserRecyclerViewAdapter;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Headers;
import retrofit2.Response;

public class StargazersActivity extends AppCompatActivity {

    @BindView(R.id.stargazers_recycler_view)
    RecyclerView stargazersRecyclerView;
    private GitHubUserRecyclerViewAdapter gitHubUserRecyclerViewAdapter;
    private ProgressDialog progressDialog;

    private GitHubAPI gitHubAPI;

    private String owner, repo;

    private CompositeDisposable compositeDisposable;

    private int currentPage = 1, lastPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stargazers);

        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            owner = extra.getString(HomeActivity.OWNER_EXTRA);
            repo = extra.getString(HomeActivity.REPO_EXTRA);
        } else if (savedInstanceState != null) {
            owner = savedInstanceState.getString(HomeActivity.OWNER_EXTRA);
            repo = savedInstanceState.getString(HomeActivity.REPO_EXTRA);
        }

        compositeDisposable = new CompositeDisposable();

        gitHubAPI = GitHubAPIService.getService();

        compositeDisposable.add(
                gitHubAPI.getStargazers(owner, repo, currentPage)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::onResponse, this::onError));

    }

    private void initList(List<GitHubUser> userList) {
        gitHubUserRecyclerViewAdapter = new GitHubUserRecyclerViewAdapter(userList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        stargazersRecyclerView.setLayoutManager(linearLayoutManager);
        stargazersRecyclerView.setAdapter(gitHubUserRecyclerViewAdapter);
    }

    private int extractLastPageFromHeader(Headers headers) {
        String link = headers.get("Link");
        if (link != null) {
            Pattern lastPagePattern = Pattern.compile(".*page=([0-9]+)>; rel=\"last\".*");
            Matcher lastPageMatcher = lastPagePattern.matcher(link);
            if (lastPageMatcher.matches())
                return Integer.parseInt(lastPageMatcher.group(1));
            else
                return currentPage;
        } else
            return currentPage;
    }

    private void onResponse(Response<List<GitHubUser>> response) {
        List<GitHubUser> list = response.body();
        if (lastPage == 0) {
            lastPage = extractLastPageFromHeader(response.headers());
        }
        if (gitHubUserRecyclerViewAdapter == null)
            initList(list);
        else
            gitHubUserRecyclerViewAdapter.addUsers(list);
        if (currentPage == lastPage)
            gitHubUserRecyclerViewAdapter.setComplete(true);
        else
            currentPage++;

        progressDialog.hide();
    }

    private void onError(Throwable throwable) {
        progressDialog.hide();
        Toast.makeText(this, "An error has occurred", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(HomeActivity.OWNER_EXTRA, owner);
        outState.putString(HomeActivity.REPO_EXTRA, repo);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    public void loadNextPage(View v) {
        progressDialog.show();
        compositeDisposable.add(
                gitHubAPI.getStargazers(owner, repo, currentPage)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::onResponse, this::onError));
    }
}

package com.m3rc.githubstargazers.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.m3rc.githubstargazers.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    public static final String OWNER_EXTRA = "OWNER_EXTRA";
    public static final String REPO_EXTRA = "REPO_EXTRA";

    @BindView(R.id.home_input_owner)
    EditText ownerInput;
    @BindView(R.id.home_input_repo)
    EditText repoInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);
    }

    public void onSubmit(View v) {
        Intent intent = new Intent(this, StargazersActivity.class);
        intent.putExtra(OWNER_EXTRA, ownerInput.getText().toString());
        intent.putExtra(REPO_EXTRA, repoInput.getText().toString());
        startActivity(intent);
    }

}

package com.m3rc.githubstargazers.network.output;

public class GitHubUser {

    private Long id;
    private String login;
    private String avatar_url;
    private String url;
    private String html_url;

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public String getUrl() {
        return url;
    }

    public String getHtml_url() {
        return html_url;
    }
}

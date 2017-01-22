# GitHubStargazers

A simple application that interacts with [Github API](https://developer.github.com/v3/),
to retrieve all [Stargazers](https://developer.github.com/v3/activity/starring/#list-stargazers)
for a specific Repo and display them on a list.

The project is setup using:

- Functional tests with [Espresso](http://google.github.io/android-testing-support-library/docs/espresso)
- [RxJava 2](https://github.com/ReactiveX/RxJava) and [RxAndroid](https://github.com/ReactiveX/RxAndroid)
- [Retrofit](http://square.github.io/retrofit/) and [OkHttp](https://github.com/square/okhttp)
- [Butterknife](https://github.com/JakeWharton/butterknife)
- [Glide](https://github.com/bumptech/glide)


Requirements
------------

- Java JDK 8 (the project uses [Jack compiler](https://source.android.com/source/jack.html))
- [Android SDK](http://developer.android.com/sdk/index.html)
- Android [7.1 API 25](http://developer.android.com/tools/revisions/platforms.html#7.1)
- Android SDK Tools
- Android SDK Build tools 25.0.2
- Android Support Repository
- Android Support Library


Testing
--------

Create a test configuration in Android Studio:

- Open Run menu -> Edit Configurations
- Add a new Android Tests configuration
- Choose the module


Thanks
--------
# iTunesMovieExplorer

This is tutorial project to reminds me of everything I learn as Android Developer.
The API is
from [iTunes Search](https://developer.apple.com/library/archive/documentation/AudioVideo/Conceptual/iTuneSearchAPI/Searching.html#//apple_ref/doc/uid/TP40017632-CH5-SW1)
I develop this app from scratch in 2 1/2 days.

## App Description

This project are using some jetpack library and another library, eg.

1. Retrofit
2. Room (Database) - to store favorite movies
3. Hilt Dagger (Injection)
4. Flow Coroutines

This project is written in MVVM architecture. Why I use MVVM, because it's recommended by the
Android Developers. ViewModel can also maintain lifecycle-aware for Android, so it can be helpful
that we not too add more code for lifecycle-aware.

In this project only need 1 repository and 1 ViewModel, because I only use 1 activity.

API configuration, Room Database configuration, and Hilt Dagger provide for injection are placed
on [core library](core/src/main/java/com/rudyrachman16/itunesmovieexplorer/core), while for UI and
ViewModel are placed on [app library](app/src/main/java/com/rudyrachman16/itunesmovieexplorer).

For UI, it using 1 activity while in 1 activity have 3 fragments eg., Search, Favorite, and Detail,
you can visualize it in [main_navigation](app/src/main/res/navigation/main_navigation.xml).

This project also using CircleCI for Continuous Integration.

### Disadvantage

The disadvantage of this project is

1. Lack of Unit Test. I still not too adapt to well to write Unit Test for Flow.
2. Not obfuscated so the size of APK somehow bigger.

## Replicate this project by

Update your local.properties from [local.properties.example](local.properties.example) file, copy
the value of BASE_URL and FLAG_URL into your local.properties. 
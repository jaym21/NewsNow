<br/>
  <p align="center"><img src="screenshots/launcher_icon.jpg" height="300" /></p>
<br/>

## Features
- News Now is an Android app to read news.
- News are available in 7 categories which are General, Business, Entertainment, Sports, Health, Science and Technology.
- App theme can be changed between light and dark mode using a simple switch.
- Full news article can be read inside the app by just clicking on the article.
- News article link can be shared to other apps. 
- News are cached using room database, so when app is launched with no internet cached news is displayed.

## Screenshots
</br>

|   Light Mode 🌞  |   Dark Mode 🌑   |   News Article Open In App 
|---	|---	|---
|  ![](https://github.com/jaym21/NewsNow/blob/main/screenshots/screenshot1.jpg)    |  ![](https://github.com/jaym21/NewsNow/blob/main/screenshots/screenshot2.jpg)    |   ![](https://github.com/jaym21/NewsNow/blob/main/screenshots/screenshot3.jpg)    

</br>

## Built With 🛠
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more.
- [Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html) - A flow is an asynchronous version of a Sequence, a type of collection whose values are lazily produced.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
  - [Navigation](https://developer.android.com/guide/navigation/navigation-getting-started) - Navigation is a framework for navigating between ‘destinations’ within an Android application
  - [Room](https://developer.android.com/jetpack/androidx/releases/room) - The Room persistence library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite. 
  - [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) - Jetpack DataStore is a data storage solution that allows you to store key-value pairs or typed objects with protocol buffers. DataStore uses Kotlin coroutines and Flow to store data asynchronously, consistently, and transactionally.
- [Dependency Injection](https://developer.android.com/training/dependency-injection) - 
  - [Hilt-Dagger](https://dagger.dev/hilt/) - Standard library to incorporate Dagger dependency injection into an Android application.
  - [Hilt-ViewModel](https://developer.android.com/training/dependency-injection/hilt-jetpack) - DI for injecting `ViewModel`.
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [Moshi](https://github.com/square/moshi) - A modern JSON library for Kotlin and Java.
- [Moshi Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/moshi) - A Converter which uses Moshi for serialization to and from JSON.
- [Glide](https://github.com/bumptech/glide) - Glide is a fast and efficient open source media management and image loading framework for Android
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android

## Credits
- [News API](https://newsapi.org/) is used to get all the news.

# State & Auth App

Android app built with Kotlin and Jetpack Compose for the Mobile Apps II assignment. It covers state management, Firebase Authentication (BaaS) and exception handling.

## Features

- State: one button increments two counters. One uses `remember` (lost on rotation) and the other uses `rememberSaveable` (kept on rotation). State hoisting is used.
- BaaS: sign up, log in and log out with Firebase Authentication (Email/Password).
- Exceptions: precondition functions (`check()`, `require()`) and a custom exception, printed in Logcat.

## Project structure

Each screen has its own folder with the screen and its ViewModel.

```
app/src/main/java/com/example/authexample/
├── MainActivity.kt              # Entry point, runs the exception demos
├── AppNavigation.kt             # NavHost with the login, signup and home routes
├── AuthState.kt                 # Authenticated / Unauthenticated / Loading / Error
├── screens/
│   ├── loginscreen/
│   │   ├── LoginScreen.kt
│   │   └── LoginScreenViewModel.kt    # Firebase login
│   ├── signupscreen/
│   │   ├── SignupScreen.kt
│   │   └── SignupScreenViewModel.kt   # Firebase signup
│   └── homescreen/
│       ├── HomeScreen.kt              # Counters (HomeScreen = state owner, CounterContent = stateless)
│       └── HomeScreenViewModel.kt     # Firebase logout
└── exceptions/
    └── ExceptionsDemo.kt        # Exceptions 1 and 2
```

The two counters stay in `HomeScreen` (not in the ViewModel) on purpose: a ViewModel survives rotation, so both counters would survive and the difference between `remember` and `rememberSaveable` could not be shown.

## Requirements

- Android Studio with an emulator or a physical device
- A Firebase account
- minSdk 24, compileSdk 36

## Setup

1. Go to the [Firebase console](https://console.firebase.google.com) and create a project.
2. Add an Android app with the package name `com.example.authexample`.
3. Download `google-services.json` and place it in the `app/` folder.
4. In **Authentication > Sign-in method**, enable **Email/Password**.
5. Open the project in Android Studio, click **Sync Now** and run the app.

The app does not build without `google-services.json`.

## How to test

State
1. Log in and press "Increment both" several times.
2. Rotate the emulator.
3. The `remember` counter goes back to 0. The `rememberSaveable` counter keeps its value.

Authentication
1. Sign up with an email and a password of at least 6 characters.
2. Log out and log in again.
3. Close and reopen the app. The user stays logged in.
4. The user appears in the Firebase console under Authentication > Users.

Exceptions
1. Run the app and open Logcat.
2. Filter by the tag `EXC`.
3. Change the values in `ExceptionsDemo.kt` (light on/off, positive/negative number, short/long word) to see the different paths.
4. Comment out `runExceptionDemos()` in `MainActivity.kt` to turn the demos off.

## Exception details

| Case | Function | Exception |
|------|----------|-----------|
| Light is not turned on | `check()` | `IllegalStateException` |
| Number is negative | `require()` | `IllegalArgumentException` |
| Word longer than 10 characters | `throw` | `WordTooLongException` (custom, `open` class) |

## References

- https://developer.android.com/develop/ui/compose/state
- https://firebase.google.com/docs/auth/android/start
- https://kotlinlang.org/docs/exceptions.html
- https://github.com/isuru10/mad2-baas-authentication (base project from the course)
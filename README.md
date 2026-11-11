A single-screen app that demonstrates the three kinds of Android menus plus a background thread that updates the UI.

What the app does
1. The screen shows two clickable texts ("Hello World!" and "Sveikas pasauli!") and an output line that starts as X.
2. Action bar menu:
NUSTATYTI SKIRTUMĄ (Determine the difference) is always visible and has a sub-menu item Valandų skirtumas (Hour difference). It opens a TimePickerDialog, and after you pick a time shows the number of minutes between now and that time in an AlertDialog and in the first text.
Išeiti (Exit), in the overflow (three dots) menu, closes the activity.
3. Context menu on either text. It opens on a normal tap as well as a long press:
Simbolių skaičius šiame tekste (Number of characters in this text) counts the characters and shows the result in an AlertDialog and in the second text.
Simbolių vardijimas po vieną (Name the characters one by one) starts a background thread that shows the text one character at a time in the output line, one per second.

Tech stack
- Java 11, Android SDK (min 33, target and compile 36)
- Options menu and context menu, TimePickerDialog, AlertDialog, plain Thread
- AndroidX AppCompat 1.8.0, Activity 1.13.0, Material 1.14.0, ConstraintLayout 2.2.2 (theme: Theme.AppCompat.Light.DarkActionBar)
- Gradle 8.13 and Android Gradle Plugin 8.13.2
- Android Studio

How to build and run
Requirements
- Android Studio (a recent release that supports Android Gradle Plugin 8.13) with Android SDK Platform 36 installed
- JDK 17 or newer (the JDK bundled with Android Studio is fine)
- An emulator or phone running Android 13 (API 33) or newer, because minSdk is 33
Steps
- git clone https://github.com/Aumila123/android-menus-and-threads.git
- Open the folder in Android Studio and wait for the Gradle sync to finish.
- Pick a device in the device selector and press Run.

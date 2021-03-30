# AndroidX Essentials
```
buildscript {
  dependencies {
    // Required for Firebase Library
    classpath 'com.google.gms:google-services:latest_version'
    classpath 'com.google.firebase:firebase-crashlytics-gradle:latest_version'
  }
}
```
```
allprojects {
  repositories {
    maven {
      url "https://dl.bintray.com/kunal26das/androidx.essentials/"
    }
  }
}
```

# Installation

 Installation is done using [Jitpack](https://jitpack.io/). Add to the root application `build.gradle`

```java
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

In the single module `build.gradle` add

```text
dependencies {
    ...
    implementation 'it.lucacrema:android-preferences:3.0'
}
```


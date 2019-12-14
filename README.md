# Android preferences
[![](https://jitpack.io/v/CremaLuca/android-preferences.svg)](https://jitpack.io/#CremaLuca/android-preferences)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/5ffe7ceded4c440baf76cd9cfb3199df)](https://www.codacy.com/manual/CremaLuca/android-preferences?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=CremaLuca/android-preferences&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/CremaLuca/android-preferences.svg?branch=master)](https://travis-ci.org/CremaLuca/android-preferences)

Library that uses android preferences quickly to write and read `int`, `float`, `long`, `String`, `boolean`, `Object` from memory.

You can use this library anywhere on your app, it just needs a context.
```Java
public class PrefsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        
        PreferencesManager.setInt(this, "intKey", 5);
        PreferencesManager.setFloat(this, "floatKey", 2.392658f);
        PreferencesManager.setLong(this, "longKey", 12000000000L);
        PreferencesManager.setString(this, "stringKey", "Test string");
        PreferencesManager.setBoolean(this, "boolKey", false);
        PreferencesManager.setObject(this, "objectKey", new String("Test object"));
        
        int preferencesInt = PreferencesManager.getInt(this, "intKey");
        float preferencesFloat = PreferencesManager.getFloat(this, "floatKey");
        long preferencesLong = PreferencesManager.getLong(this, "longKey");
        String preferencesString = PreferencesManager.getString(this, "stringKey");
        boolean preferencesBoolean = PreferencesManager.getBoolean(this, "boolKey");
        String preferencesStringObject = (String)PreferencesManager.getObject(this, "objectKey");
    }
}
```

# Installation
Installation is done using [Jitpack](https://jitpack.io). Add to the root application `build.gradle`
```Java
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
In the single module `build.gradle` add
```Java
dependencies {
    ...
    implementation 'it.lucacrema:android-preferences:3.0'
}
```

# Usage
With Android preferences you can read and write `int`, `float`, `long`, `String`, `boolean` and `Object`, the way you do it is:
```Java
PreferencesManager.set{type of data}(context, key, value);
PreferencesManager.get{type of data}(context, key);
```
Where {type of data} can be `Int`, `Float`, `Long`, `String`, `Boolean`, `Object`

There is a method to get a `Map<String, ?>` of all saved values:
```Java
PreferencesManager.getAllValues(context);
```

You can also remove saved values of any type
```Java
PreferencesManager.removeValue(context, key);
```
And remove all the saved values
```Java
PreferencesManager.removeAllValues(context);
```

### Bonus methods
If you want to save a counter in preferences and update it you can use
```Java
PreferencesManager.updateInt(context, key); //Adds 1 to the currently saved value
PreferencesManager.updateInt(context, key, 5); //Adds 5 to the currently saved value
```
If you need to save a counters that resets to 1 once it gets to a defined value you can use:
```Java
PreferencesManager.shiftInt(context, key, 3); //returns 1 (if the value was empty)
PreferencesManager.shiftInt(context, key, 3); //returns 2
PreferencesManager.shiftInt(context, key, 3); //returns 3
PreferencesManager.shiftInt(context, key, 3); //returns 1
```

## Warning
If you were to use the same string key for two values of different type, the old value would be overwritten
```Java
PreferencesManager.setInt(context, "key", 5);
PreferencesManager.setString(context, "key", "string");

PreferencesManager.getInt(context, "key"); //Throws ClassCastException
PreferencesManager.getString(context, "key"); //returns "string"
```

# License

```
Copyright 2019 Luca Crema

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

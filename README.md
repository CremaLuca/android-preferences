# Android preferences
[![](https://jitpack.io/v/CremaLuca/android-preferences.svg)](https://jitpack.io/#CremaLuca/android-preferences)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/5ffe7ceded4c440baf76cd9cfb3199df)](https://www.codacy.com/manual/CremaLuca/android-preferences?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=CremaLuca/android-preferences&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/CremaLuca/android-preferences.svg?branch=master)](https://travis-ci.org/CremaLuca/android-preferences)
[![Maintainability](https://api.codeclimate.com/v1/badges/7573e56874c27755d8c5/maintainability)](https://codeclimate.com/github/CremaLuca/android-preferences/maintainability)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
![GitHub repo size](https://img.shields.io/github/repo-size/CremaLuca/android-preferences)
![GitHub Release Date](https://img.shields.io/github/release-date/CremaLuca/android-preferences)

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
MIT License

Copyright (c) 2019 Luca Crema

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

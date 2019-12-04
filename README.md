# Android preferences
Library that uses android preferences quickly to write and read `int`, `String`, `boolean`, `Object` from memory.

You can use this library anywhere on your app, it just needs a context.
```Java
public class PrefsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        
        PreferencesManager.setInt(this, "intKey", 5);
        PreferencesManager.setString(this, "stringKey", "Test string");
        PreferencesManager.setBoolean(this, "boolKey", false);
        PreferencesManager.setObject(this, "objectKey", new String("Test object"));
        
        int preferencesInt = PreferencesManager.getInt(this, "intKey");
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
    implementation 'it.lucacrema:android-preferences:2.5'
}
```

# Usage
With Android preferences you can read and write `int`, `String`, `boolean` and `Object`, the way you do it is:
```Java
PreferencesManager.set{type of data}(context, key, value);
```
Where {type of data} can be `Int`, `String`, `Boolean`, `Object`

You can also remove saved values of any type
```Java
PreferencesManager.removeValue(context, key);
```
And remove all the saved values
```Java
PreferencesManager.removeAllValues(context);
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

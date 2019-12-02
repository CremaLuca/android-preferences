# android-preferences
Library that uses android preferences quickly, without any need of Builders.

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
        String preferencesStringObject = PreferencesManager.getObject(this, "objectKey");
    }
}
```

# License

```
Copyright 2014 Pixplicity (http://pixplicity.com)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

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

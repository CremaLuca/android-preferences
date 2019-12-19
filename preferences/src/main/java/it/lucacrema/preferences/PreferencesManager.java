package it.lucacrema.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

/**
 * Service that writes and reads small configurations/preferences in Android memory.
 * Wrapper for {@link androidx.preference.PreferenceManager} class
 *
 * @author Luca Crema
 * @version 3.1
 * @since 19/12/2019
 */
@SuppressWarnings({"unused", "WeakerAccess", "UnusedReturnValue"})
final public class PreferencesManager {

    public static final int DEFAULT_INTEGER_RETURN = -1;
    public static final float DEFAULT_FLOAT_RETURN = 0f;
    public static final long DEFAULT_LONG_RETURN = -1L;
    public static final String DEFAULT_STRING_RETURN = "";
    public static final boolean DEFAULT_BOOLEAN_RETURN = false;

    protected static final int DEFAULT_UPDATE_INT_ADD = 1;
    protected ObjectSerializerUtility objectSerializerUtility;
    private SharedPreferences sharedPreferences;

    /**
     * @param ctx Current application context, used to set default shared preferences
     */
    public PreferencesManager(Context ctx) {
        this(PreferenceManager.getDefaultSharedPreferences(ctx));
    }

    /**
     * @param sharedPrefs usually it's {@code PreferencesManager.getDefaultSharedPreferences(context)}
     */
    public PreferencesManager(SharedPreferences sharedPrefs) {
        this(sharedPrefs, new ObjectSerializerUtility());
    }

    /**
     * @param sharedPrefs             usually it's {@code PreferencesManager.getDefaultSharedPreferences(context)}.
     * @param objectSerializerUtility class used to serialize and de-serialize objects.
     */
    public PreferencesManager(SharedPreferences sharedPrefs, ObjectSerializerUtility objectSerializerUtility) {
        this.sharedPreferences = sharedPrefs;
        this.objectSerializerUtility = objectSerializerUtility;
    }

    /**
     * Sums the current value of the preference to {@value DEFAULT_UPDATE_INT_ADD}
     *
     * @param key key for the resource
     * @return if the value has been updated correctly
     */
    public int updateInt(@NonNull String key) {
        return updateInt(key, DEFAULT_UPDATE_INT_ADD);
    }

    /**
     * @return Shared preferences, either the default one or the custom one, depends on which constructor has been used
     */
    private SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    /**
     * @return the editor for the preferences
     */
    private SharedPreferences.Editor getEditor() {
        return sharedPreferences.edit();
    }

    /**
     * @param key key for the resource
     * @return the value of the resource if present, {@link #DEFAULT_INTEGER_RETURN} ({@value #DEFAULT_INTEGER_RETURN}) otherwise
     * @throws ClassCastException if the stored value for the required key is not int
     */
    public int getInt(@NonNull String key) throws ClassCastException {
        return getSharedPreferences().getInt(key, DEFAULT_INTEGER_RETURN);
    }

    /**
     * @param key key for the resource
     * @return the value of the resource if present, {@link #DEFAULT_FLOAT_RETURN} ({@value #DEFAULT_FLOAT_RETURN}) otherwise
     * @throws ClassCastException if the stored value for the required key is not float
     */
    public float getFloat(@NonNull String key) throws ClassCastException {
        return getSharedPreferences().getFloat(key, DEFAULT_FLOAT_RETURN);
    }

    /**
     * @param key key for the resource
     * @return the value of the resource if present, {@link #DEFAULT_LONG_RETURN} ({@value #DEFAULT_LONG_RETURN}) otherwise
     * @throws ClassCastException if the stored value for the required key is not long
     */
    public long getLong(@NonNull String key) throws ClassCastException {
        return getSharedPreferences().getLong(key, DEFAULT_LONG_RETURN);
    }

    /**
     * @param key key for the resource
     * @return the value of the resource if present, {@link #DEFAULT_STRING_RETURN} ({@value #DEFAULT_STRING_RETURN}) otherwise
     * @throws ClassCastException if the stored value for the required key is not String
     */
    public String getString(@NonNull String key) throws ClassCastException {
        return getSharedPreferences().getString(key, DEFAULT_STRING_RETURN);
    }

    /**
     * @param key key for the resource
     * @return the value of the resource if present, {@link #DEFAULT_BOOLEAN_RETURN} ({@value #DEFAULT_BOOLEAN_RETURN}) otherwise
     * @throws ClassCastException if the stored value for the required key is not boolean
     */
    public boolean getBoolean(@NonNull String key) throws ClassCastException {
        return sharedPreferences.getBoolean(key, DEFAULT_BOOLEAN_RETURN);
    }

    /**
     * @param key key for the resource
     * @return the object de-serialized if present, null otherwise
     * @throws ClassCastException if the stored value for the required key is not an Object
     */
    public Object getObject(@NonNull String key) throws ClassCastException {
        String memoryObjectString = sharedPreferences.getString(key, null);
        if (memoryObjectString == null)
            return null;

        return objectSerializerUtility.deserializeObject(memoryObjectString);
    }

    /**
     * @return all the values saved in preferences
     */
    public Map<String, ?> getAllValues() {
        return getSharedPreferences().getAll();
    }

    /**
     * @param key   key for the resource
     * @param value value to be put or override
     * @return if the value has been set correctly
     */
    public boolean setInt(@NonNull String key, int value) {
        return getEditor().putInt(key, value).commit();
    }

    /**
     * @param key   key for the resource
     * @param value value to be put or override
     * @return if the value has been set correctly
     */
    public boolean setFloat(@NonNull String key, float value) {
        return getEditor().putFloat(key, value).commit();
    }

    /**
     * @param key   key for the resource
     * @param value value to be put or override
     * @return if the value has been set correctly
     */
    public boolean setLong(@NonNull String key, long value) {
        return getEditor().putLong(key, value).commit();
    }

    /**
     * @param key   key for the resource
     * @param value value to be put or override
     * @return if the value has been set correctly
     */
    public boolean setString(@NonNull String key, String value) {
        if (value == null)
            return removeValue(key);
        return getEditor().putString(key, value).commit();
    }

    /**
     * @param key   key for the resource
     * @param value value to be put or override
     * @return if the value has been set correctly
     */
    public boolean setBoolean(@NonNull String key, boolean value) {
        return getEditor().putBoolean(key, value).commit();
    }

    /**
     * Saves a whole object state
     *
     * @param key    key for the resource
     * @param object value to be put or override
     * @return if the value has been set correctly
     * @throws IOException Any exception thrown by the underlying OutputStream.
     */
    public boolean setObject(@NonNull String key, @NonNull Serializable object) throws IOException {
        return setString(key, objectSerializerUtility.serializeObject(object));
    }

    /**
     * Sums the value to an integer
     *
     * @param key   key for the resource
     * @param value value to be summed to the current value
     * @return if the value has been updated correctly
     */
    public int updateInt(@NonNull String key, int value) {
        int currentValue = getInt(key);
        if (currentValue == DEFAULT_INTEGER_RETURN)
            currentValue = 0;
        getEditor().putInt(key, currentValue + value).commit();
        return currentValue + value;
    }

    /**
     * Sums 1 to the integer saved in memory and goes back to 1 if it exceeds the maxValue
     *
     * @param key key for the resource
     * @return result of the shift, between 1 and maxValue included
     */
    public int shiftInt(@NonNull String key, int maxValue) {
        int currentValue = getInt(key);
        if (currentValue == DEFAULT_INTEGER_RETURN)
            currentValue = 0;
        int nextValue = (currentValue % maxValue) + 1;
        setInt(key, nextValue);
        return nextValue;
    }

    /**
     * Removes a value of any type from the phone preferences
     *
     * @param key key for the resource
     * @return if the value has been removed correctly
     */
    public boolean removeValue(@NonNull String key) {
        return getEditor().remove(key).commit();
    }

    /**
     * Remove all saved values from the device's preferences
     * Use with caution
     *
     * @return if all the values have been removed correctly
     */
    public boolean removeAllValues() {
        return getEditor().clear().commit();
    }

}

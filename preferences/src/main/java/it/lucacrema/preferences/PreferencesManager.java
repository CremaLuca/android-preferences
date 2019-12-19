package it.lucacrema.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    private Context context;
    private SharedPreferences sharedPreferences;

    /**
     * @param ctx Current application context
     */
    public PreferencesManager(Context ctx) {
        this(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
    }

    /**
     * @param ctx         Current application context
     * @param sharedPrefs usually it's {@code PreferencesManager.getDefaultSharedPreferences(context)}
     */
    public PreferencesManager(Context ctx, SharedPreferences sharedPrefs) {
        this.context = ctx;
        this.sharedPreferences = sharedPrefs;
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
     * @param sharedPreferences can be the default or a custom shared preferences class
     * @return the editor for the preferences
     */
    private SharedPreferences.Editor getEditor(@NonNull SharedPreferences sharedPreferences) {
        return sharedPreferences.edit();
    }

    /**
     * @param context context of an Activity or Service
     * @return default shared preferences editor
     */
    private SharedPreferences.Editor getEditor(@NonNull Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).edit();
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

        try {
            byte[] b = Base64.decode(memoryObjectString, Base64.DEFAULT);
            ByteArrayInputStream bi = new ByteArrayInputStream(b);
            ObjectInputStream si = new ObjectInputStream(bi);
            return si.readObject();
        } catch (IOException e) {
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        }
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
        SharedPreferences.Editor editor = getEditor(context);
        editor.putInt(key, value);
        return editor.commit();
    }

    /**
     * @param key   key for the resource
     * @param value value to be put or override
     * @return if the value has been set correctly
     */
    public boolean setFloat(@NonNull String key, float value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putFloat(key, value);
        return editor.commit();
    }

    /**
     * @param key   key for the resource
     * @param value value to be put or override
     * @return if the value has been set correctly
     */
    public boolean setLong(@NonNull String key, long value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putLong(key, value);
        return editor.commit();
    }

    /**
     * @param key   key for the resource
     * @param value value to be put or override
     * @return if the value has been set correctly
     */
    public boolean setString(@NonNull String key, String value) {
        if (value == null)
            return removeValue(key);
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(key, value);
        return editor.commit();
    }

    /**
     * @param key   key for the resource
     * @param value value to be put or override
     * @return if the value has been set correctly
     */
    public boolean setBoolean(@NonNull String key, boolean value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putBoolean(key, value);
        return editor.commit();
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
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream so = new ObjectOutputStream(bo);
        so.writeObject(object);
        so.flush();
        return setString(key, new String(Base64.encode(bo.toByteArray(), Base64.DEFAULT)));
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
        SharedPreferences.Editor editor = getEditor(context);
        editor.putInt(key, currentValue + value);
        editor.commit();
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
        SharedPreferences.Editor editor = getEditor(context);
        editor.remove(key);
        return editor.commit();
    }

    /**
     * Remove all saved values from the device's preferences
     * Use with caution
     *
     * @return if all the values have been removed correctly
     */
    public boolean removeAllValues() {
        SharedPreferences.Editor editor = getEditor(context);
        return editor.clear().commit();
    }

}

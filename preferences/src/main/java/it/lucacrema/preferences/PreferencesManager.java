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
 * @version 3.0
 * @since 02/12/2019
 */
@SuppressWarnings({"unused", "WeakerAccess", "UnusedReturnValue"})
final public class PreferencesManager {

    public static final int DEFAULT_INTEGER_RETURN = -1;
    public static final float DEFAULT_FLOAT_RETURN = 0f;
    public static final long DEFAULT_LONG_RETURN = -1L;
    public static final String DEFAULT_STRING_RETURN = "";
    public static final boolean DEFAULT_BOOLEAN_RETURN = false;

    protected static final int DEFAULT_UPDATE_INT_ADD = 1;

    /**
     * @param context context of an Activity or Service
     * @return default shared preferences class
     */
    private static SharedPreferences getSharedPreferences(@NonNull Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * @param sharedPreferences can be the default or a custom shared preferences class
     * @return the editor for the preferences
     */
    private static SharedPreferences.Editor getEditor(@NonNull SharedPreferences sharedPreferences) {
        return sharedPreferences.edit();
    }

    /**
     * @param context context of an Activity or Service
     * @return default shared preferences editor
     */
    private static SharedPreferences.Editor getEditor(@NonNull Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).edit();
    }

    /**
     * @param ctx context of an Activity or Service
     * @param key key for the resource
     * @return the value of the resource if present, {@link #DEFAULT_INTEGER_RETURN} ({@value #DEFAULT_INTEGER_RETURN}) otherwise
     * @throws ClassCastException if the stored value for the required key is not int
     */
    public static int getInt(@NonNull Context ctx, @NonNull String key) throws ClassCastException {
        return getSharedPreferences(ctx).getInt(key, DEFAULT_INTEGER_RETURN);
    }

    /**
     * @param ctx context of an Activity or Service
     * @param key key for the resource
     * @return the value of the resource if present, {@link #DEFAULT_FLOAT_RETURN} ({@value #DEFAULT_FLOAT_RETURN}) otherwise
     * @throws ClassCastException if the stored value for the required key is not float
     */
    public static float getFloat(@NonNull Context ctx, @NonNull String key) throws ClassCastException {
        return getSharedPreferences(ctx).getFloat(key, DEFAULT_FLOAT_RETURN);
    }

    /**
     * @param ctx context of an Activity or Service
     * @param key key for the resource
     * @return the value of the resource if present, {@link #DEFAULT_LONG_RETURN} ({@value #DEFAULT_LONG_RETURN}) otherwise
     * @throws ClassCastException if the stored value for the required key is not long
     */
    public static long getLong(@NonNull Context ctx, @NonNull String key) throws ClassCastException {
        return getSharedPreferences(ctx).getLong(key, DEFAULT_LONG_RETURN);
    }

    /**
     * @param ctx context of an Activity or Service
     * @param key key for the resource
     * @return the value of the resource if present, {@link #DEFAULT_STRING_RETURN} ({@value #DEFAULT_STRING_RETURN}) otherwise
     * @throws ClassCastException if the stored value for the required key is not String
     */
    public static String getString(@NonNull Context ctx, @NonNull String key) throws ClassCastException {
        return getSharedPreferences(ctx).getString(key, DEFAULT_STRING_RETURN);
    }

    /**
     * @param ctx context of an Activity or Service
     * @param key key for the resource
     * @return the value of the resource if present, {@link #DEFAULT_BOOLEAN_RETURN} ({@value #DEFAULT_BOOLEAN_RETURN}) otherwise
     * @throws ClassCastException if the stored value for the required key is not boolean
     */
    public static boolean getBoolean(@NonNull Context ctx, @NonNull String key) throws ClassCastException {
        return getSharedPreferences(ctx).getBoolean(key, DEFAULT_BOOLEAN_RETURN);
    }

    /**
     * @param ctx context of an Activity or Service
     * @param key key for the resource
     * @return the object de-serialized if present, null otherwise
     * @throws ClassCastException if the stored value for the required key is not an Object
     */
    public static Object getObject(@NonNull Context ctx, @NonNull String key) throws ClassCastException {
        String memoryObjectString = getSharedPreferences(ctx).getString(key, null);
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
     * @param ctx context of an Activity or Service
     * @return all the values saved in preferences
     */
    public static Map<String, ?> getAllValues(@NonNull Context ctx) {
        return getSharedPreferences(ctx).getAll();
    }

    /**
     * @param ctx   context of an Activity or Service
     * @param key   key for the resource
     * @param value value to be put or override
     * @return if the value has been set correctly
     */
    public static boolean setInt(@NonNull Context ctx, @NonNull String key, int value) {
        SharedPreferences.Editor editor = getEditor(ctx);
        editor.putInt(key, value);
        return editor.commit();
    }

    /**
     * @param ctx   context of an Activity or Service
     * @param key   key for the resource
     * @param value value to be put or override
     * @return if the value has been set correctly
     */
    public static boolean setFloat(@NonNull Context ctx, @NonNull String key, float value) {
        SharedPreferences.Editor editor = getEditor(ctx);
        editor.putFloat(key, value);
        return editor.commit();
    }

    /**
     * @param ctx   context of an Activity or Service
     * @param key   key for the resource
     * @param value value to be put or override
     * @return if the value has been set correctly
     */
    public static boolean setLong(@NonNull Context ctx, @NonNull String key, long value) {
        SharedPreferences.Editor editor = getEditor(ctx);
        editor.putLong(key, value);
        return editor.commit();
    }

    /**
     * @param ctx   context of an Activity or Service
     * @param key   key for the resource
     * @param value value to be put or override
     * @return if the value has been set correctly
     */
    public static boolean setString(@NonNull Context ctx, @NonNull String key, String value) {
        if (value == null)
            return removeValue(ctx, key);
        SharedPreferences.Editor editor = getEditor(ctx);
        editor.putString(key, value);
        return editor.commit();
    }

    /**
     * @param ctx   context of an Activity or Service
     * @param key   key for the resource
     * @param value value to be put or override
     * @return if the value has been set correctly
     */
    public static boolean setBoolean(@NonNull Context ctx, @NonNull String key, boolean value) {
        SharedPreferences.Editor editor = getEditor(ctx);
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /**
     * Saves a whole object state
     *
     * @param ctx    context of an Activity or Service
     * @param key    key for the resource
     * @param object value to be put or override
     * @return if the value has been set correctly
     * @throws IOException Any exception thrown by the underlying OutputStream.
     */
    public static boolean setObject(@NonNull Context ctx, @NonNull String key, @NonNull Serializable object) throws IOException {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream so = new ObjectOutputStream(bo);
        so.writeObject(object);
        so.flush();
        return setString(ctx, key, new String(Base64.encode(bo.toByteArray(), Base64.DEFAULT)));
    }

    /**
     * Sums the value to an integer
     *
     * @param ctx   context of an Activity or Service
     * @param key   key for the resource
     * @param value value to be summed to the current value
     * @return if the value has been updated correctly
     */
    public static int updateInt(@NonNull Context ctx, @NonNull String key, int value) {
        int currentValue = getInt(ctx, key);
        if (currentValue == DEFAULT_INTEGER_RETURN)
            currentValue = 0;
        SharedPreferences.Editor editor = getEditor(ctx);
        editor.putInt(key, currentValue + value);
        editor.commit();
        return currentValue + value;
    }

    /**
     * Sums the current value of the preference to {@value DEFAULT_UPDATE_INT_ADD}
     *
     * @param ctx context of an Activity or Service
     * @param key key for the resource
     * @return if the value has been updated correctly
     */
    public static int updateInt(@NonNull Context ctx, @NonNull String key) {
        return updateInt(ctx, key, DEFAULT_UPDATE_INT_ADD);
    }

    /**
     * Sums 1 to the integer saved in memory and goes back to 1 if it exceeds the maxValue
     *
     * @param ctx context of an Activity or Service
     * @param key key for the resource
     * @return result of the shift, between 1 and maxValue included
     */
    public static int shiftInt(@NonNull Context ctx, @NonNull String key, int maxValue) {
        int currentValue = getInt(ctx, key);
        if (currentValue == DEFAULT_INTEGER_RETURN)
            currentValue = 0;
        int nextValue = (currentValue % maxValue) + 1;
        setInt(ctx, key, nextValue);
        return nextValue;
    }

    /**
     * Removes a value of any type from the phone preferences
     *
     * @param ctx context of an Activity or Service
     * @param key key for the resource
     * @return if the value has been removed correctly
     */
    public static boolean removeValue(@NonNull Context ctx, @NonNull String key) {
        SharedPreferences.Editor editor = getEditor(ctx);
        editor.remove(key);
        return editor.commit();
    }

    /**
     * Remove all saved values from the device's preferences
     * Use with caution
     *
     * @param ctx context of an Activity or Service
     * @return if all the values have been removed correctly
     */
    public static boolean removeAllValues(@NonNull Context ctx) {
        SharedPreferences.Editor editor = getEditor(ctx);
        return editor.clear().commit();
    }

}

package it.lucacrema.preferences;

import android.content.SharedPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.io.Serializable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PreferencesManagerTest {

    private static final String DEFAULT_INT_KEY = "defaultIntKey";
    private static final String DEFAULT_STRING_KEY = "defaultStringKey";
    private static final String DEFAULT_BOOL_KEY = "defaultBooleanKey";
    private static final String DEFAULT_FLOAT_KEY = "defaultFloatKey";
    private static final String DEFAULT_LONG_KEY = "defaultLongKey";
    private static final String DEFAULT_OBJECT_KEY = "defaultObjectKey";
    private static final String DEFAULT_OBJECT_KEY_2 = "defaultObjectKey2";

    private static final int DEFAULT_INT_VALUE = 100;
    private static final String DEFAULT_STRING_VALUE = "Roberto";
    private static final boolean DEFAULT_BOOL_VALUE = true;
    private static final float DEFAULT_FLOAT_VALUE = 12.3232132313f;
    private static final long DEFAULT_LONG_VALUE = 33123232123123132L;
    private static final String DEFAULT_OBJECT_VALUE = "Test String";
    private static final Integer DEFAULT_OBJECT_VALUE_2 = 6;
    private static final int MAX_SHIFT_VALUE = 3;

    private static final String PRESENT_INT_KEY = "presentIntKey";
    private static final String PRESENT_STRING_KEY = "presentStringKey";
    private static final String PRESENT_BOOL_KEY = "presentBooleanKey";
    private static final String PRESENT_OBJECT_KEY = "presentObjectKey";

    private static final int PRESENT_INT_VALUE = 50;
    private static final String PRESENT_STRING_VALUE = "Ignazio";
    private static final boolean PRESENT_BOOL_VALUE = false;
    private static final String PRESENT_OBJECT_VALUE = "Test Object String";

    private static final double DELTA = 0.0001d;

    @Mock
    private SharedPreferences mockSharedPreferences;
    @Mock
    private SharedPreferences.Editor mockSharedPreferencesEditor;
    @Mock
    private ObjectSerializerUtility mockObjectSerializer;

    private PreferencesManager preferencesManager;

    @Before
    public void setUp() {
        createSharedPrefsMock();
        preferencesManager = new PreferencesManager(mockSharedPreferences, mockObjectSerializer);
        preferencesManager.removeAllValues();
        preferencesManager.setInt(PRESENT_INT_KEY, PRESENT_INT_VALUE);
        preferencesManager.setString(PRESENT_STRING_KEY, PRESENT_STRING_VALUE);
        preferencesManager.setBoolean(PRESENT_BOOL_KEY, PRESENT_BOOL_VALUE);
        try {
            preferencesManager.setObject(PRESENT_OBJECT_KEY, PRESENT_OBJECT_VALUE);
        } catch (IOException e) {
            //Error
        }
    }

    private void createSharedPrefsMock() {
        when(mockSharedPreferences.edit()).thenReturn(mockSharedPreferencesEditor);
        when(mockSharedPreferencesEditor.clear()).thenReturn(mockSharedPreferencesEditor);
        when(mockSharedPreferencesEditor.putInt(any(String.class), any(Integer.class))).thenReturn(mockSharedPreferencesEditor);
        when(mockSharedPreferencesEditor.putFloat(any(String.class), any(Float.class))).thenReturn(mockSharedPreferencesEditor);
        when(mockSharedPreferencesEditor.putLong(any(String.class), any(Long.class))).thenReturn(mockSharedPreferencesEditor);
        when(mockSharedPreferencesEditor.putBoolean(any(String.class), any(Boolean.class))).thenReturn(mockSharedPreferencesEditor);
        when(mockSharedPreferencesEditor.putString(any(String.class), any(String.class))).thenReturn(mockSharedPreferencesEditor);
        when(mockSharedPreferencesEditor.commit()).thenReturn(true);
        try {
            when(mockObjectSerializer.serializeObject(any(Serializable.class))).thenReturn("Nothing");
        } catch (IOException e) {
            //Do nothing, fail
        }
        when(mockSharedPreferencesEditor.remove(any(String.class))).thenReturn(mockSharedPreferencesEditor);
    }

    //Tests for getValue with undefined value

    @Test
    public void getInt_defaultInteger_isEquals() {
        when(mockSharedPreferences.getInt(DEFAULT_INT_KEY, PreferencesManager.DEFAULT_INTEGER_RETURN))
                .thenReturn(PreferencesManager.DEFAULT_INTEGER_RETURN);

        Assert.assertEquals(PreferencesManager.DEFAULT_INTEGER_RETURN, preferencesManager.getInt(DEFAULT_INT_KEY));
    }

    @Test
    public void getString_defaultString_isEquals() {
        when(mockSharedPreferences.getString(DEFAULT_STRING_KEY, PreferencesManager.DEFAULT_STRING_RETURN))
                .thenReturn(PreferencesManager.DEFAULT_STRING_RETURN);
        Assert.assertEquals(PreferencesManager.DEFAULT_STRING_RETURN, preferencesManager.getString(DEFAULT_STRING_KEY));
    }

    @Test
    public void getFloat_defaultFloat_isEquals() {
        when(mockSharedPreferences.getFloat(DEFAULT_FLOAT_KEY, PreferencesManager.DEFAULT_FLOAT_RETURN))
                .thenReturn(PreferencesManager.DEFAULT_FLOAT_RETURN);

        Assert.assertEquals(PreferencesManager.DEFAULT_FLOAT_RETURN, preferencesManager.getFloat(DEFAULT_FLOAT_KEY), DELTA);
    }

    @Test
    public void getLong_defaultLong_isEquals() {
        when(mockSharedPreferences.getLong(DEFAULT_LONG_KEY, PreferencesManager.DEFAULT_LONG_RETURN))
                .thenReturn(PreferencesManager.DEFAULT_LONG_RETURN);

        Assert.assertEquals(PreferencesManager.DEFAULT_LONG_RETURN, preferencesManager.getLong(DEFAULT_LONG_KEY));
    }

    @Test
    public void getBoolean_defaultBoolean_isEquals() {
        when(mockSharedPreferences.getBoolean(DEFAULT_BOOL_KEY, PreferencesManager.DEFAULT_BOOLEAN_RETURN))
                .thenReturn(PreferencesManager.DEFAULT_BOOLEAN_RETURN);

        Assert.assertEquals(PreferencesManager.DEFAULT_BOOLEAN_RETURN, preferencesManager.getBoolean(DEFAULT_BOOL_KEY));
    }

    @Test
    public void getObject_isNull() {
        when(mockSharedPreferences.getString(DEFAULT_OBJECT_KEY, null))
                .thenReturn(null);

        Assert.assertNull(preferencesManager.getObject(DEFAULT_OBJECT_KEY));
    }

    //test if the default value is returned for an over
    @Test(expected = ClassCastException.class)
    public void setString_getInt_throwsError() throws ClassCastException {
        when(mockSharedPreferences.getInt(eq(PRESENT_STRING_KEY), any(Integer.class))).thenThrow(ClassCastException.class);

        Assert.assertEquals(PreferencesManager.DEFAULT_INTEGER_RETURN, preferencesManager.getInt(PRESENT_STRING_KEY));
    }

    @Test
    public void overrideValueOtherType_isCorrect() {
        when(mockSharedPreferences.getInt(eq(PRESENT_STRING_KEY), any(Integer.class))).thenReturn(DEFAULT_INT_VALUE);

        preferencesManager.setInt(PRESENT_STRING_KEY, DEFAULT_INT_VALUE);
        Assert.assertEquals(DEFAULT_INT_VALUE, preferencesManager.getInt(PRESENT_STRING_KEY));
    }

    //tests for setValue

    @Test
    public void setInt_getInt_isEquals() {
        when(mockSharedPreferences.getInt(eq(DEFAULT_INT_KEY), any(Integer.class))).thenReturn(DEFAULT_INT_VALUE);

        preferencesManager.setInt(DEFAULT_INT_KEY, DEFAULT_INT_VALUE);
        Assert.assertEquals(DEFAULT_INT_VALUE, preferencesManager.getInt(DEFAULT_INT_KEY));
    }

    @Test
    public void setString_getString_isEquals() {
        when(mockSharedPreferences.getString(eq(DEFAULT_STRING_KEY), any(String.class))).thenReturn(DEFAULT_STRING_VALUE);

        preferencesManager.setString(DEFAULT_STRING_KEY, DEFAULT_STRING_VALUE);
        Assert.assertEquals(DEFAULT_STRING_VALUE, preferencesManager.getString(DEFAULT_STRING_KEY));
    }

    @Test
    public void setFloat_getFloat_isEquals() {
        when(mockSharedPreferences.getFloat(eq(DEFAULT_FLOAT_KEY), any(Float.class))).thenReturn(DEFAULT_FLOAT_VALUE);

        preferencesManager.setFloat(DEFAULT_FLOAT_KEY, DEFAULT_FLOAT_VALUE);
        Assert.assertEquals(DEFAULT_FLOAT_VALUE, preferencesManager.getFloat(DEFAULT_FLOAT_KEY), DELTA);
    }

    @Test
    public void setLong_getLong_isEquals() {
        when(mockSharedPreferences.getLong(eq(DEFAULT_LONG_KEY), any(Long.class))).thenReturn(DEFAULT_LONG_VALUE);

        preferencesManager.setLong(DEFAULT_LONG_KEY, DEFAULT_LONG_VALUE);
        Assert.assertEquals(DEFAULT_LONG_VALUE, preferencesManager.getLong(DEFAULT_LONG_KEY));
    }

    @Test
    public void setBoolean_getBoolean_isEquals() {
        when(mockSharedPreferences.getBoolean(eq(DEFAULT_BOOL_KEY), any(Boolean.class))).thenReturn(DEFAULT_BOOL_VALUE);

        preferencesManager.setBoolean(DEFAULT_BOOL_KEY, DEFAULT_BOOL_VALUE);
        Assert.assertEquals(DEFAULT_BOOL_VALUE, preferencesManager.getBoolean(DEFAULT_BOOL_KEY));
    }

    @Test
    public void setObject_getObject_String_isEquals() {
        when(mockSharedPreferences.getString(eq(DEFAULT_OBJECT_KEY), any())).thenReturn(DEFAULT_STRING_VALUE);
        when(mockObjectSerializer.deserializeObject(any(String.class))).thenReturn(DEFAULT_OBJECT_VALUE);

        try {
            preferencesManager.setObject(DEFAULT_OBJECT_KEY, DEFAULT_OBJECT_VALUE);
        } catch (IOException e) {
            Assert.fail("Should not have thrown an IOException");
        }
        Assert.assertEquals(DEFAULT_OBJECT_VALUE, preferencesManager.getObject(DEFAULT_OBJECT_KEY));
    }

    @Test
    public void setObject_getObject_Integer_isEquals() {
        when(mockSharedPreferences.getString(eq(DEFAULT_OBJECT_KEY_2), any())).thenReturn(DEFAULT_STRING_VALUE);
        when(mockObjectSerializer.deserializeObject(any(String.class))).thenReturn(DEFAULT_OBJECT_VALUE_2);

        try {
            preferencesManager.setObject(DEFAULT_OBJECT_KEY_2, DEFAULT_OBJECT_VALUE_2);
        } catch (IOException e) {
            Assert.fail("Should not have thrown an IOException");
        }
        Assert.assertEquals(DEFAULT_OBJECT_VALUE_2, preferencesManager.getObject(DEFAULT_OBJECT_KEY_2));
    }

    @Test
    public void updateInt_getInt_isEquals() {
        when(mockSharedPreferences.getInt(eq(DEFAULT_INT_KEY), any(Integer.class))).thenReturn(PreferencesManager.DEFAULT_UPDATE_INT_ADD);

        preferencesManager.updateInt(DEFAULT_INT_KEY);
        Assert.assertEquals(PreferencesManager.DEFAULT_UPDATE_INT_ADD, preferencesManager.getInt(DEFAULT_INT_KEY));
    }

    @Test
    public void updateInt_twice_getInt_isEquals() {
        when(mockSharedPreferences.getInt(eq(DEFAULT_INT_KEY), any(Integer.class))).thenReturn(2 * DEFAULT_INT_VALUE);

        preferencesManager.updateInt(DEFAULT_INT_KEY, DEFAULT_INT_VALUE);
        preferencesManager.updateInt(DEFAULT_INT_KEY, DEFAULT_INT_VALUE);
        Assert.assertEquals(2 * DEFAULT_INT_VALUE, preferencesManager.getInt(DEFAULT_INT_KEY));
    }

    @Test
    public void shiftInt_oneStep_getInt_isEquals() {
        when(mockSharedPreferences.getInt(eq(DEFAULT_INT_KEY), any(Integer.class))).thenReturn(1);

        preferencesManager.shiftInt(DEFAULT_INT_KEY, MAX_SHIFT_VALUE);
        Assert.assertEquals(1, preferencesManager.getInt(DEFAULT_INT_KEY));
    }

    /**
     * We shift the integer so much it goes back to 1
     */
    @Test
    public void shiftInt_maxSteps_getInt_isRestarted() {
        when(mockSharedPreferences.getInt(eq(DEFAULT_INT_KEY), any(Integer.class))).thenReturn(1);

        for (int i = 0; i < MAX_SHIFT_VALUE + 1; i++) {
            preferencesManager.shiftInt(DEFAULT_INT_KEY, MAX_SHIFT_VALUE);
        }
        Assert.assertEquals(1, preferencesManager.getInt(DEFAULT_INT_KEY));
    }

    @Test
    public void shiftInt_moreSteps_getInt_isFull() {
        when(mockSharedPreferences.getInt(eq(DEFAULT_INT_KEY), any(Integer.class))).thenReturn(MAX_SHIFT_VALUE);

        for (int i = 0; i < MAX_SHIFT_VALUE; i++) {
            preferencesManager.shiftInt(DEFAULT_INT_KEY, MAX_SHIFT_VALUE);
        }
        Assert.assertEquals(MAX_SHIFT_VALUE, preferencesManager.getInt(DEFAULT_INT_KEY));
    }

    @Test
    public void removeValue_getInt_isEmpty() {
        when(mockSharedPreferences.getInt(eq(DEFAULT_INT_KEY), any(Integer.class))).thenReturn(PreferencesManager.DEFAULT_INTEGER_RETURN);

        preferencesManager.setInt(DEFAULT_INT_KEY, DEFAULT_INT_VALUE);
        preferencesManager.removeValue(DEFAULT_INT_KEY);
        Assert.assertEquals(PreferencesManager.DEFAULT_INTEGER_RETURN, preferencesManager.getInt(DEFAULT_INT_KEY));
    }

    @Test
    public void removeValue_getString_isEmpty() {
        when(mockSharedPreferences.getString(eq(DEFAULT_STRING_KEY), any())).thenReturn(PreferencesManager.DEFAULT_STRING_RETURN);

        preferencesManager.setString(DEFAULT_STRING_KEY, DEFAULT_STRING_VALUE);
        preferencesManager.removeValue(DEFAULT_STRING_KEY);
        Assert.assertEquals(PreferencesManager.DEFAULT_STRING_RETURN, preferencesManager.getString(DEFAULT_STRING_KEY));
    }

    @Test
    public void removeValue_getBoolean_isEmpty() {
        when(mockSharedPreferences.getBoolean(eq(DEFAULT_BOOL_KEY), anyBoolean())).thenReturn(PreferencesManager.DEFAULT_BOOLEAN_RETURN);

        preferencesManager.setBoolean(DEFAULT_BOOL_KEY, DEFAULT_BOOL_VALUE);
        preferencesManager.removeValue(DEFAULT_BOOL_KEY);
        Assert.assertEquals(PreferencesManager.DEFAULT_BOOLEAN_RETURN, preferencesManager.getBoolean(DEFAULT_BOOL_KEY));
    }

    @Test
    public void removeAllValues_getInt_isEmpty() {
        when(mockSharedPreferences.getInt(eq(DEFAULT_INT_KEY), any(Integer.class))).thenReturn(PreferencesManager.DEFAULT_INTEGER_RETURN);

        preferencesManager.setInt(DEFAULT_INT_KEY, DEFAULT_INT_VALUE);
        preferencesManager.removeAllValues();
        Assert.assertEquals(PreferencesManager.DEFAULT_INTEGER_RETURN, preferencesManager.getInt(DEFAULT_INT_KEY));
    }

    @Test
    public void removeAllValues_getString_isEmpty() {
        when(mockSharedPreferences.getString(eq(DEFAULT_STRING_KEY), any())).thenReturn(PreferencesManager.DEFAULT_STRING_RETURN);

        preferencesManager.setString(DEFAULT_STRING_KEY, DEFAULT_STRING_VALUE);
        preferencesManager.removeAllValues();
        Assert.assertEquals(PreferencesManager.DEFAULT_STRING_RETURN, preferencesManager.getString(DEFAULT_STRING_KEY));
    }

    @Test
    public void removeAllValues_getIBoolean_isEmpty() {
        preferencesManager.setBoolean(DEFAULT_BOOL_KEY, DEFAULT_BOOL_VALUE);
        preferencesManager.removeAllValues();
        Assert.assertEquals(PreferencesManager.DEFAULT_BOOLEAN_RETURN, preferencesManager.getBoolean(DEFAULT_BOOL_KEY));
    }
}
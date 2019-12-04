package it.lucacrema.preferences;

import android.content.Context;
import android.preference.Preference;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

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
    private Context ctx;

    @Before
    public void setUp() {
        ctx = InstrumentationRegistry.getInstrumentation().getTargetContext();
        PreferencesManager.removeAllValues(ctx);
        PreferencesManager.setInt(ctx, PRESENT_INT_KEY, PRESENT_INT_VALUE);
        PreferencesManager.setString(ctx, PRESENT_STRING_KEY, PRESENT_STRING_VALUE);
        PreferencesManager.setBoolean(ctx, PRESENT_BOOL_KEY, PRESENT_BOOL_VALUE);
        try{
            PreferencesManager.setObject(ctx, PRESENT_OBJECT_KEY, PRESENT_OBJECT_VALUE);
        }catch(IOException e){
            //Error
        }
    }

    //Tests for getValue with undefined value

    @Test
    public void getInt_defaultInteger_isEquals() {
        Assert.assertEquals(PreferencesManager.DEFAULT_INTEGER_RETURN, PreferencesManager.getInt(ctx, DEFAULT_INT_KEY));
    }

    @Test
    public void getString_defaultString_isEquals() {
        Assert.assertEquals(PreferencesManager.DEFAULT_STRING_RETURN, PreferencesManager.getString(ctx, DEFAULT_STRING_KEY));
    }

    @Test
    public void getFloat_defaultFloat_isEquals() {
        Assert.assertEquals(PreferencesManager.DEFAULT_FLOAT_RETURN, PreferencesManager.getFloat(ctx, DEFAULT_FLOAT_KEY), DELTA);
    }

    @Test
    public void getLong_defaultLong_isEquals() {
        Assert.assertEquals(PreferencesManager.DEFAULT_LONG_RETURN, PreferencesManager.getLong(ctx, DEFAULT_LONG_KEY));
    }

    @Test
    public void getBoolean_defaultBoolean_isEquals() {
        Assert.assertEquals(PreferencesManager.DEFAULT_BOOLEAN_RETURN, PreferencesManager.getBoolean(ctx, DEFAULT_BOOL_KEY));
    }

    @Test
    public void getObject_isNull() {
        Assert.assertNull(PreferencesManager.getObject(ctx, DEFAULT_OBJECT_KEY));
    }

    //test if the default value is returned for an over
    @Test(expected = ClassCastException.class)
    public void setString_getInt_throwsError(){
        Assert.assertEquals(PreferencesManager.DEFAULT_INTEGER_RETURN, PreferencesManager.getInt(ctx, PRESENT_STRING_KEY));
    }

    @Test
    public void overrideValueOtherType_isCorrect(){
        PreferencesManager.setInt(ctx, PRESENT_STRING_KEY, DEFAULT_INT_VALUE);
        Assert.assertEquals(DEFAULT_INT_VALUE, PreferencesManager.getInt(ctx, PRESENT_STRING_KEY));
    }

    //tests for setValue

    @Test
    public void setInt_getInt_isEquals() {
        PreferencesManager.setInt(ctx, DEFAULT_INT_KEY, DEFAULT_INT_VALUE);
        Assert.assertEquals(DEFAULT_INT_VALUE, PreferencesManager.getInt(ctx, DEFAULT_INT_KEY));
    }

    @Test
    public void setString_getString_isEquals() {
        PreferencesManager.setString(ctx, DEFAULT_STRING_KEY, DEFAULT_STRING_VALUE);
        Assert.assertEquals(DEFAULT_STRING_VALUE, PreferencesManager.getString(ctx, DEFAULT_STRING_KEY));
    }

    @Test
    public void setFloat_getFloat_isEquals() {
        PreferencesManager.setFloat(ctx, DEFAULT_FLOAT_KEY, DEFAULT_FLOAT_VALUE);
        Assert.assertEquals(DEFAULT_FLOAT_VALUE, PreferencesManager.getFloat(ctx, DEFAULT_FLOAT_KEY), DELTA);
    }

    @Test
    public void setLong_getLong_isEquals() {
        PreferencesManager.setLong(ctx, DEFAULT_LONG_KEY, DEFAULT_LONG_VALUE);
        Assert.assertEquals(DEFAULT_LONG_VALUE, PreferencesManager.getLong(ctx, DEFAULT_LONG_KEY));
    }

    @Test
    public void setBoolean_getBoolean_isEquals() {
        PreferencesManager.setBoolean(ctx, DEFAULT_BOOL_KEY, DEFAULT_BOOL_VALUE);
        Assert.assertEquals(DEFAULT_BOOL_VALUE, PreferencesManager.getBoolean(ctx, DEFAULT_BOOL_KEY));
    }

    @Test
    public void setObject_getObject_String_isEquals() {
        try {
            PreferencesManager.setObject(ctx, DEFAULT_OBJECT_KEY, DEFAULT_OBJECT_VALUE);
        } catch (IOException e) {
            Assert.fail("Should not have thrown an IOException");
        }
        Assert.assertEquals(DEFAULT_OBJECT_VALUE, PreferencesManager.getObject(ctx, DEFAULT_OBJECT_KEY));
    }

    @Test
    public void setObject_getObject_Integer_isEquals() {
        try {
            PreferencesManager.setObject(ctx, DEFAULT_OBJECT_KEY_2, DEFAULT_OBJECT_VALUE_2);
        } catch (IOException e) {
            Assert.fail("Should not have thrown an IOException");
        }
        Assert.assertEquals(DEFAULT_OBJECT_VALUE_2, PreferencesManager.getObject(ctx, DEFAULT_OBJECT_KEY_2));
    }

    @Test
    public void updateInt_getInt_isEquals() {
        PreferencesManager.updateInt(ctx, DEFAULT_INT_KEY);
        Assert.assertEquals(PreferencesManager.DEFAULT_UPDATE_INT_ADD, PreferencesManager.getInt(ctx, DEFAULT_INT_KEY));
    }

    @Test
    public void updateInt_twice_getInt_isEquals() {
        PreferencesManager.updateInt(ctx, DEFAULT_INT_KEY, DEFAULT_INT_VALUE);
        PreferencesManager.updateInt(ctx, DEFAULT_INT_KEY, DEFAULT_INT_VALUE);
        Assert.assertEquals(2 * DEFAULT_INT_VALUE, PreferencesManager.getInt(ctx, DEFAULT_INT_KEY));
    }

    @Test
    public void shiftInt_oneStep_getInt_isEquals() {
        PreferencesManager.shiftInt(ctx, DEFAULT_INT_KEY, MAX_SHIFT_VALUE);
        Assert.assertEquals(1, PreferencesManager.getInt(ctx, DEFAULT_INT_KEY));
    }

    /**
     * We shift the integer so much it goes back to 1
     */
    @Test
    public void shiftInt_maxSteps_getInt_isRestarted() {
        for (int i = 0; i < MAX_SHIFT_VALUE + 1; i++) {
            PreferencesManager.shiftInt(ctx, DEFAULT_INT_KEY, MAX_SHIFT_VALUE);
        }
        Assert.assertEquals(1, PreferencesManager.getInt(ctx, DEFAULT_INT_KEY));
    }

    @Test
    public void shiftInt_moreSteps_getInt_isFull() {
        for (int i = 0; i < MAX_SHIFT_VALUE; i++) {
            PreferencesManager.shiftInt(ctx, DEFAULT_INT_KEY, MAX_SHIFT_VALUE);
        }
        Assert.assertEquals(MAX_SHIFT_VALUE, PreferencesManager.getInt(ctx, DEFAULT_INT_KEY));
    }

    @Test
    public void removeValue_getInt_isEmpty() {
        PreferencesManager.setInt(ctx, DEFAULT_INT_KEY, DEFAULT_INT_VALUE);
        PreferencesManager.removeValue(ctx, DEFAULT_INT_KEY);
        Assert.assertEquals(PreferencesManager.DEFAULT_INTEGER_RETURN, PreferencesManager.getInt(ctx, DEFAULT_INT_KEY));
    }

    @Test
    public void removeValue_getString_isEmpty() {
        PreferencesManager.setString(ctx, DEFAULT_STRING_KEY, DEFAULT_STRING_VALUE);
        PreferencesManager.removeValue(ctx, DEFAULT_STRING_KEY);
        Assert.assertEquals(PreferencesManager.DEFAULT_STRING_RETURN, PreferencesManager.getString(ctx, DEFAULT_STRING_KEY));
    }

    @Test
    public void removeValue_getBoolean_isEmpty() {
        PreferencesManager.setBoolean(ctx, DEFAULT_BOOL_KEY, DEFAULT_BOOL_VALUE);
        PreferencesManager.removeValue(ctx, DEFAULT_BOOL_KEY);
        Assert.assertEquals(PreferencesManager.DEFAULT_BOOLEAN_RETURN, PreferencesManager.getBoolean(ctx, DEFAULT_BOOL_KEY));
    }

    @Test
    public void removeAllValues_getInt_isEmpty() {
        PreferencesManager.setInt(ctx, DEFAULT_INT_KEY, DEFAULT_INT_VALUE);
        PreferencesManager.removeAllValues(ctx);
        Assert.assertEquals(PreferencesManager.DEFAULT_INTEGER_RETURN, PreferencesManager.getInt(ctx, DEFAULT_INT_KEY));
    }

    @Test
    public void removeAllValues_getString_isEmpty() {
        PreferencesManager.setString(ctx, DEFAULT_STRING_KEY, DEFAULT_STRING_VALUE);
        PreferencesManager.removeAllValues(ctx);
        Assert.assertEquals(PreferencesManager.DEFAULT_STRING_RETURN, PreferencesManager.getString(ctx, DEFAULT_STRING_KEY));
    }

    @Test
    public void removeAllValues_getIBoolean_isEmpty() {
        PreferencesManager.setBoolean(ctx, DEFAULT_BOOL_KEY, DEFAULT_BOOL_VALUE);
        PreferencesManager.removeAllValues(ctx);
        Assert.assertEquals(PreferencesManager.DEFAULT_BOOLEAN_RETURN, PreferencesManager.getBoolean(ctx, DEFAULT_BOOL_KEY));
    }
}
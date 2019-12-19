package it.lucacrema.preferences;

import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Utility class to serialize objects into strings and de-serialize them.
 *
 * @author Luca Crema
 * @since 19/12/2019
 */
final class ObjectSerializerUtility {

    /**
     * Serializes an object into a String.
     *
     * @param o object to serialize.
     * @return the object serialized into a String.
     * @throws IOException if ObjectOutputStream fails to write in memory.
     */
    public static String serializeObject(Serializable o) throws IOException {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream so = new ObjectOutputStream(bo);
        so.writeObject(o);
        so.flush();
        return new String(Base64.encode(bo.toByteArray(), Base64.DEFAULT));
    }

    /**
     * De-serializes an object from a string. The string should be an object serialized.
     *
     * @param s An object serialized.
     * @return The de-serialized object if it was correctly parsed, null otherwise.
     * @throws ClassCastException if the String does not contain a class.
     */
    public static Object deserializeObject(String s) throws ClassCastException {
        try {
            byte[] b = Base64.decode(s, Base64.DEFAULT);
            ByteArrayInputStream bi = new ByteArrayInputStream(b);
            ObjectInputStream si = new ObjectInputStream(bi);
            return si.readObject();
        } catch (IOException ioe) {
            return null;
        } catch (ClassNotFoundException cnfe) {
            return null;
        }
    }
}

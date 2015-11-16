package fr.enlight.hpdata.utils;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

/**
 * This class provides helping methods to manipulate file reading and writing.
 */
public class FileHelper {

    /**
     * Save a list of Serializable object into the given file.
     *
     * @param filepath the file where the object must be saved
     * @param objectToSave the object list to save
     */
    public static void saveFileObjects(File filepath, List<? extends Serializable> objectToSave){
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(filepath);
            oos = new ObjectOutputStream(fos);

            oos.writeObject(objectToSave);

        } catch (IOException e) {
            Log.e(FileHelper.class.getSimpleName(), "Cannot save object to file : " + filepath, e);

        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if(fos != null){
                    fos.close();
                }

            } catch (IOException e) {
                Log.e(FileHelper.class.getSimpleName(), "Cannot close stream", e);
            }
        }
    }

    /**
     * Load a list of Serializable object from the given file. This file must be a file created using the method saveFileObjects.
     *
     * @param filepath the file where the object is stored
     * @param objectType the typed class of the object
     * @param <D> the type of the object
     * @return the typed objects
     */
    public static <D extends Serializable> List<D> loadFileObjects(File filepath, Class<D> objectType){
        List<D> result = null;

        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(filepath);
            ois = new ObjectInputStream(fis);

            List<?> resultList = (List<?>) ois.readObject();
            if(resultList != null && !resultList.isEmpty() && objectType.isAssignableFrom(resultList.get(0).getClass())){
                //noinspection unchecked
                result = (List<D>) resultList;
            }


        } catch (IOException e) {
            Log.e(FileHelper.class.getSimpleName(), "Cannot save object to file : " + filepath, e);

        } catch (ClassNotFoundException e) {
            Log.e(FileHelper.class.getSimpleName(), "Class not found for object in file : " + filepath, e);

        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if(fis != null){
                    fis.close();
                }

            } catch (IOException e) {
                Log.e(FileHelper.class.getSimpleName(), "Cannot close stream", e);
            }
        }

        return result;
    }
}

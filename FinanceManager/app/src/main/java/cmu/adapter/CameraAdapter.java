package cmu.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Pedro on 02/12/2015.
 * CameraAdapter class. Serves as a connector between the UI and the camera Service.
 * Warnings seen are because Android dev studio thinks we cannot catch the exception, which it is
 * caught... so not to worry.
 */
public class CameraAdapter {

    public CameraAdapter(){

    }

    public void savePhoto(Context context, Intent data){
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        String folderName = "/FinanceManager";
        String nameOfFile = "FM";

        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + folderName;
        String currentDate = getCurrentDateAndTime();
        File dir = new File(filePath);

        if(!dir.exists()){
            dir.mkdirs();
        }

        File file = new File(dir, nameOfFile + currentDate+ ".jpg");

        try{
            FileOutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, stream);
            stream.flush();
            stream.close();
            // Make photo available
            MediaScannerConnection.scanFile(context, new String[]{file.toString()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                    Log.e("ExternalStorage", "Scanned " + path + ":");
                    Log.e("ExternalStorage", "-> uri=" + uri);
                }
            });
            Toast.makeText(context, "Your moment was saved!", Toast.LENGTH_SHORT).show();
        }catch (Exception n){
            Toast.makeText(context, "Something went wrong saving the moment.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private String getCurrentDateAndTime(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return format.format(calendar.getTime());
    }
}

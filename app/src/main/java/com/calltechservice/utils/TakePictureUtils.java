package com.calltechservice.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/** this class is used for image operation */

public  class TakePictureUtils {

  public static final int TAKE_PICTURE = 1;
  public static final int PICK_GALLERY = 2;
  public static final int CROP_FROM_CAMERA = 3;
  public static final int TAKE_FILE = 4;
  private static final String TEMP_PHOTO_FILE_NAME = "temp";


   /** this method is used for take picture from camera */
  public static void takePicture(Activity context, String fileName) {

      Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

      try {

          Uri mImageCaptureUri = null;
          Log.e(context + "", "file name " + fileName);

       //    mImageCaptureUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", new File(context.getExternalFilesDir("temp"), fileName + ".png"));


          mImageCaptureUri = Uri.fromFile(new File(context.getExternalFilesDir("temp"), fileName + ".png"));
          intent.putExtra(MediaStore.EXTRA_OUTPUT,mImageCaptureUri);
          intent.putExtra("return-data", true);
          context.startActivityForResult(intent, TAKE_PICTURE);

      } catch (ActivityNotFoundException e) {
          Log.e(context + "", "cannot take picture " + e);

      } catch (Exception ex) {

          Log.e(context + "", "cannot take picture " + ex);
      }
  }


    public static void captureImage(Activity context) {
        Uri fileUri=null;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
//            fileTemp = ImageUtils.getOutputMediaFile();
            ContentValues values = new ContentValues(1);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
            fileUri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//            if (fileTemp != null) {
//            fileUri = Uri.fromFile(fileTemp);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            context.startActivityForResult(intent, TAKE_PICTURE);
//            } else {
//                Toast.makeText(this, getString(R.string.error_create_image_file), Toast.LENGTH_LONG).show();
//            }
        } else {
            Toast.makeText(context,"cannot take picture " , Toast.LENGTH_LONG).show();
        }
    }



   /** this method is used for copy stream */

   public static void copyStream(InputStream input, OutputStream output)
              throws IOException {

          byte[] buffer = new byte[1024];
          int bytesRead;
          while ((bytesRead = input.read(buffer)) != -1) {
              output.write(buffer, 0, bytesRead);
          }
      }



      private File getFileTemp(Context context, String fileName) {
          File mFileTemp = null;


          if(mFileTemp != null && fileExists(mFileTemp.getPath())){
              return mFileTemp;
          }
          else{
              String state = Environment.getExternalStorageState();
              if (Environment.MEDIA_MOUNTED.equals(state)) {
                  System.out.println("Media Mounted ");
                  String RootDir = Environment.getExternalStorageDirectory()+ File.separator + "Strings/";
                  File RootFile = new File(RootDir);
                  if(RootFile.isDirectory()){

                  }else{
                      RootFile.mkdir();
                  }
                  mFileTemp = new File(RootFile,  fileName);
                  System.out.println("if mFileTemp : " + mFileTemp);
              }
              else {
                  mFileTemp = new File(context.getFilesDir(), fileName);
                  System.out.println("media not mounted");
                  System.out.println("else mFileTemp : " + mFileTemp);
              }
              return mFileTemp;
          }
      }
      private boolean fileExists(String filePath){
          File file = new File(filePath);
          if(file.exists())
              return true;
          else
              return false;
      }

}

/**
 *
 * Stencyl Extension, Create by Robin Schaafsma
 * https://byrobingames.github.io
 *
 **/

package com.byrobin.simpleshare;

import org.haxe.lime.*;
import android.content.Intent;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.Context;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Environment;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ByteArrayInputStream;
import android.net.Uri;
import android.graphics.Bitmap;
import android.util.Base64;
import android.provider.MediaStore;
import android.util.Log;

import org.haxe.extension.Extension;

import androidx.core.content.FileProvider;
import java.util.Objects;
import java.text.SimpleDateFormat;
import java.util.Date;

///permisions
import android.content.pm.PackageManager;
import android.Manifest;
import androidx.core.app.ActivityCompat;

public class Share extends Extension
{
    //////////////////////////////////////////////////////////////////////////////////////////////////
    private static final int REQUEST_ALL = 0;
    private static String[] PERMISSIONS_ALL = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
    //////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    public static String path;
    public static Uri uri;
    
    private static boolean shareSucceed = false;
    private static boolean shareFailed = false;

    public static void shareContent(final String msg, final String url, final boolean withImage)
    {
        
        mainActivity.runOnUiThread(new Runnable()
        {
            public void run()
            {
                
                if(!withImage)
                {

                    Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, msg + "\n\n" + url);
                    Extension.mainContext.startActivity(intent);

                    shareSucceed = true;
                    shareFailed = false;
                    
                }
                    
            }
        });
    }
    
    public static void saveImageAndShare (final String msg, final String url, final String base64Img)
    {
        
        if(!hasPermissions(PERMISSIONS_ALL)){
            ActivityCompat.requestPermissions(mainActivity, PERMISSIONS_ALL, REQUEST_ALL);
            return;
        }
        mainActivity.runOnUiThread(new Runnable()
                                   {
            public void run()
            {
                Bitmap image = convertToImage(base64Img);
                File filePath = null;
                
                // Create unique filenames for each share (Good when sharing to a directory. -> If the file already exists -> and you don't have the option to rename the file)
                SimpleDateFormat dateFormat = new SimpleDateFormat("_yyyy-MM-dd_HH.mm.ss");
                String currentDateTime = dateFormat.format(new Date());

                try {
                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(builder.build());

                    filePath = new File(Extension.mainContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Screen" +currentDateTime +".png"); // Use .png or .jpg
                    FileOutputStream fos = new FileOutputStream(filePath);

                    image.compress(Bitmap.CompressFormat.PNG, 100, fos); // Use PNG or JPEG
                    fos.close();
                    
                } catch (Exception e) {
                    Log.e("saveToInternalStorage()", e.getMessage());
                }
                        
                Log.v("Simpleshare file path: ", "filePath is: "+filePath.toString());
                
                // Fixing the : FileUriExposedViolation error:                                                                  
                // Using a FileProvider (Other apps can then read the image-file temporarly when we share it)           
                Uri fileUri = FileProvider.getUriForFile( Objects.requireNonNull( Extension.mainContext.getApplicationContext() ), "com.game.name" +".provider", filePath);  // "com.game.name.provider" <- Justin! This ID, MUST be worldwide UNIQUE, and should ideally be generated automatically at build time, it should then be set to the BundleID of the curent Stencyl Game +".provider" -> The same ID should also dynamically be created in the Game Android Manifest at build time (I used the template in workspace)
                
                        // To get FileProvider to work together with Stencyl:
                        // I have modified the template manifest in [Stecyl] \plaf\lime-templates\android\template\app\src\main 
                        // , to be able to set up the <provider>-tags (Sice they hade to be placed between the <application>-tags).
                        //
                        // Ideally -> this should be automated so that you replace this static string: "com.game.name.provider" with the
                        // games BundleID +".provider", both in the Android Manifest for the game as well as in -> this Share.java code.

                            /*
                                <application> 
                                .....

                                    <!-- This is added to the template manifest: -->>

                                    <provider
                                        android:name="androidx.core.content.FileProvider" 
                                        android:authorities="com.game.name.provider"   
                                        android:exported="false"
                                        android:grantUriPermissions="true">
                                        <meta-data
                                            android:name="android.support.FILE_PROVIDER_PATHS"
                                            android:resource="@xml/file_paths">
                                        </meta-data>
                                    </provider>         



                                .....                       
                                </application>

                            */ 
                    
                        //  AND -> I have ALSO in [Stecyl] \plaf\lime-templates\android\template\app\src\main -> under [res] -> created a new folder: [xml] AND added the file: file_paths.xml  
                        
                            /*
                                    The file: file_paths.xml contains: 
                                
                                    <?xml version="1.0" encoding="utf-8"?> 
                                    <paths xmlns:android="http://schemas.android.com/apk/res/android"> 
                                                <external-path name="external_files" path="." /> 
                                    </paths>
                            */
                    
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_TEXT, msg + "\n\n" + url);
                intent.putExtra(Intent.EXTRA_STREAM, fileUri);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // If write permission needed use: | Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                Extension.mainContext.startActivity(intent);     
                
                shareSucceed = true;
                shareFailed = false;
            }
        });
    }
    
    public static Bitmap convertToImage(String image)
    {
        try
        {
            InputStream stream = new ByteArrayInputStream(Base64.decode(image.getBytes(), Base64.DEFAULT));
            Bitmap bitmap = android.graphics.BitmapFactory.decodeStream(stream);
            return bitmap;
        }
        catch (Exception e)
        {
            return null;
        }
    }
    
    
    static public boolean shareResultSucceed()
    {
        if(shareSucceed)
        {
            shareSucceed = false;
            return true;
        }
        return false;
    }
    
    static public boolean shareResultFailed()
    {
        return shareFailed;
    }
    
    public static boolean hasPermissions(String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= 23 && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(mainActivity, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

}

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

///permisions
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.Manifest;

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
                    Extension.mainContext.startActivity(Intent.createChooser(intent, "Share via.."));
                    
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
                
                try {
                    StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(builder.build());

                    filePath = new File(Extension.mainContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "screen.png");
                    FileOutputStream fos = new FileOutputStream(filePath);

                    image.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.close();
                    
                } catch (Exception e) {
                    Log.e("saveToInternalStorage()", e.getMessage());
                }
                
                //File filePath = Extension.mainContext.getFileStreamPath("screen.png");
                
                Log.e("file path ", filePath.toString());
                
                Uri fileUri = Uri.fromFile(filePath);
                
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_TEXT, msg + "\n\n" + url);
                intent.putExtra(Intent.EXTRA_STREAM, fileUri);
                Extension.mainContext.startActivity(Intent.createChooser(intent, "Share via.."));
                
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

package;

#if cpp
import cpp.Lib;
#elseif neko
import neko.Lib;
#else
import openfl.Lib;
#end

import com.stencyl.behavior.Script;
import com.stencyl.behavior.Script.*;
import openfl.display.BitmapData;
import openfl.utils.ByteArray;
import openfl.geom.Rectangle;
import openfl.display.PNGEncoderOptions;

import haxe.io.Bytes;

#if android
#if openfl_legacy
import openfl.utils.JNI;
#else
import lime.system.JNI;
#end
#end

#if ios
@:buildXml('<include name="${haxelib:simpleshare}/project/Build.xml"/>')
#end
class Share
{	
	private static var msg:String;
	private static var url:String;
	private static var withImage:Bool = false;
	

	public static function shareContent(msg:String, url:String):Void
	{
	
		Share.msg = msg;
		Share.url = url;
		withImage = false;
				
		#if(cpp && mobile && !android)
		iosShare(msg, url, withImage);
		#end
				
		#if android
		if(androidShare == null)
		{
			androidShare = JNI.createStaticMethod("com.byrobin.simpleshare.Share", "shareContent", "(Ljava/lang/String;Ljava/lang/String;Z)V");
		}
		androidShare(msg, url, withImage);
		#end
	}
	
	
	public static function shareContentWithImage(msg:String, url:String, img:BitmapData):Void
	{
	
		Share.msg = msg;
		Share.url = url;
		
		saveImage(img);
	}
	
	public static function saveImage(bmd:BitmapData):Void 
	{
	#if ios
		#if openfl_legacy
		var ba:ByteArray = bmd.getPixels(new Rectangle(0, 0, bmd.width, bmd.height));
		sharescreenshot(msg, url, ba.getData(), Std.int(bmd.width), Std.int(bmd.height));
		#else
		var ba:ByteArrayData = bmd.getPixels(new Rectangle(0, 0, bmd.width, bmd.height));
		sharescreenshot(msg, url, ba.getData(), Std.int(bmd.width), Std.int(bmd.height));
		#end
	#elseif android
		if (jni_call_save_image == null) 
		{
			jni_call_save_image = JNI.createStaticMethod ("com.byrobin.simpleshare.Share", "saveImageAndShare", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");
		}
		jni_call_save_image(msg,url, getB64PngData(bmd));
	#end
	}

	#if android
		
	private static inline var BASE_64_ENCODINGS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
	private static inline var BASE_64_PADDING = "=";
		
	static inline function getB64PngData(bmd:BitmapData):String
	{
		var b:ByteArray = bmd.encode(bmd.rect, new PNGEncoderOptions()); //bmd.encode("png");
		var base64:String = new haxe.crypto.BaseCode(Bytes.ofString(BASE_64_ENCODINGS)).encodeBytes(b).toString();
		var remainder = base64.length % 4;
				
		if (remainder > 1) 
		{
			base64 += BASE_64_PADDING;
		}
		if (remainder == 2) 
		{
			base64 += BASE_64_PADDING;
		}
			return base64;
	}
		
	#end

 public static function getShareResult(info:Int):Bool
    {
	 	if (info == 0)
        {
            #if ios
			return sharesucceed();
            #end

            #if android
            if(sharesucceed == null)
            {
                sharesucceed = JNI.createStaticMethod("com.byrobin.simpleshare.Share", "shareResultSucceed", "()Z", true);
            }
            var args = new Array<Dynamic>();
            return sharesucceed(args);
            #end
		}
        else
        {
		    #if ios
            return sharefailed();
            #end
            
            #if android
            if(sharefailed == null)
            {
                sharefailed = JNI.createStaticMethod("com.byrobin.simpleshare.Share", "shareResultFailed", "()Z", true);
            }
            var args = new Array<Dynamic>();
            return sharefailed(args);
            #end
        }
        
		return false;
    }



	#if android
	private static var androidShare :String -> String -> Bool -> Void = null;
	private static var jni_call_save_image:Dynamic;
	private static var sharesucceed:Dynamic;
    private static var sharefailed:Dynamic;
	#end

	#if(cpp && mobile && !android)
	private static var iosShare = cpp.Lib.load("share", "ios_share", 3);
	private static var sharescreenshot = cpp.Lib.load ("share", "share_screenshot", 5);
	private static var sharesucceed = cpp.Lib.load("share","get_share_succeed",0);
	private static var sharefailed = cpp.Lib.load("share","get_share_failed",0);
	#end
	
}
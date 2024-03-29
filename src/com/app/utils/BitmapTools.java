package com.app.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

public class BitmapTools {

	public BitmapTools() {
		// TODO Auto-generated constructor stub
	}

	@SuppressLint("NewApi")
	public static String compressImgeToDisk(Bitmap bitmap) {
		OutputStream outputStream = null;
		String realPath = "";
		// 位图的引用不为空，数据不为空
		if (bitmap != null && bitmap.getByteCount() > 0) {
			try {
				String rootPath = SDCardTools.getSDcardRootPath();// 下载的缓存目录
				if (rootPath != null) {
					// currentTimeMillis 返回当前系统时间的毫秒自1970年1月1日0时UTC
					realPath = rootPath + File.separator
							+ System.currentTimeMillis() + ".png";
					outputStream = new FileOutputStream(realPath);
					bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				if(outputStream != null){
					try {
						outputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}
		return realPath;

	}

	public static Bitmap getSmallBitmap(String filePath) {  
        
        final BitmapFactory.Options options = new BitmapFactory.Options();  
        options.inJustDecodeBounds = true;  
        BitmapFactory.decodeFile(filePath, options);  
  
        // Calculate inSampleSize  
        options.inSampleSize = calculateInSampleSize(options, 480, 800);  
  
        // Decode bitmap with inSampleSize set  
        options.inJustDecodeBounds = false;  
          
        Bitmap bm = BitmapFactory.decodeFile(filePath, options);  
        if(bm == null){  
            return  null;  
        }  
        int degree = readPictureDegree(filePath);  
        bm = rotateBitmap(bm,degree) ;  
        ByteArrayOutputStream baos = null ;  
        try{  
            baos = new ByteArrayOutputStream();  
            bm.compress(Bitmap.CompressFormat.JPEG, 30, baos);  
              
        }finally{  
            try {  
                if(baos != null)  
                    baos.close() ;  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return bm ;  
  
    }  

	
	
public static Bitmap getSmallBitmap(Bitmap bm) {  
        
        /*final BitmapFactory.Options options = new BitmapFactory.Options();  
        options.inJustDecodeBounds = true;  
        BitmapFactory.decodeFile(filePath, options);  
  
        // Calculate inSampleSize  
        options.inSampleSize = calculateInSampleSize(options, 480, 800);  
  
        // Decode bitmap with inSampleSize set  
        options.inJustDecodeBounds = false;  
          
        Bitmap bm = BitmapFactory.decodeFile(filePath, options);  */
        if(bm == null){  
            return  null;  
        }  
       /* int degree = readPictureDegree(filePath);  
        bm = rotateBitmap(bm,degree) ;  */
        ByteArrayOutputStream baos = null ;  
        try{  
            baos = new ByteArrayOutputStream();  
            bm.compress(Bitmap.CompressFormat.JPEG, 30, baos);  
              
        }finally{  
            try {  
                if(baos != null)  
                    baos.close() ;  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return bm ;  
  
    }  
	
	
	private static int readPictureDegree(String path) {    
        int degree  = 0;    
        try {    
                ExifInterface exifInterface = new ExifInterface(path);    
                int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);    
                switch (orientation) {    
                case ExifInterface.ORIENTATION_ROTATE_90:    
                        degree = 90;    
                        break;    
                case ExifInterface.ORIENTATION_ROTATE_180:    
                        degree = 180;    
                        break;    
                case ExifInterface.ORIENTATION_ROTATE_270:    
                        degree = 270;    
                        break;    
                }    
        } catch (IOException e) {    
                e.printStackTrace();    
        }    
        return degree;    
    }   
	
	
	private static Bitmap rotateBitmap(Bitmap bitmap, int rotate){  
        if(bitmap == null)  
            return null ;  
          
        int w = bitmap.getWidth();  
        int h = bitmap.getHeight();  
  
        // Setting post rotate to 90  
        Matrix mtx = new Matrix();  
        mtx.postRotate(rotate);  
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);  
    }  
	
	private static int calculateInSampleSize(BitmapFactory.Options options,  
            int reqWidth, int reqHeight) {  
        // Raw height and width of image  
        final int height = options.outHeight;  
        final int width = options.outWidth;  
        int inSampleSize = 1;  
  
        if (height > reqHeight || width > reqWidth) {  
  
            // Calculate ratios of height and width to requested height and  
            // width  
            final int heightRatio = Math.round((float) height  
                    / (float) reqHeight);  
            final int widthRatio = Math.round((float) width / (float) reqWidth);  
  
            // Choose the smallest ratio as inSampleSize value, this will  
            // guarantee  
            // a final image with both dimensions larger than or equal to the  
            // requested height and width.  
            inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;  
        }  
  
        return inSampleSize;  
    }  
	
}

package com.zczczy.leo.fuwuwangapp.tools;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageUtil {

	public static final int CAMERA_ACTIVITY = 1;

	public static final int SELECT_PHOTO_REQUEST_CODE = 2;

	public static final int IMAGE_CROP = 3;

	public static void cameraIntent(Activity activity, String name) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File f1 = new File(name);
		Uri u1 = Uri.fromFile(f1);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, u1);
		activity.startActivityForResult(intent, CAMERA_ACTIVITY);
	}

	public static void photoIntent(Activity activity) {
		Intent innerIntent = new Intent(Intent.ACTION_GET_CONTENT); // "android.intent.action.GET_CONTENT"
		innerIntent.setType("image/*");
		Intent wrapperIntent = Intent.createChooser(innerIntent, null);
		activity.startActivityForResult(wrapperIntent, SELECT_PHOTO_REQUEST_CODE);

	}

	public static void corpIntent(Activity activity, Uri dataUri, Uri outputUri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(dataUri, "image/*");
		intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
		intent.putExtra("crop", "true");
		// intent.putExtra("aspectX", 1);
		// intent.putExtra("aspectY", 1);
		// intent.putExtra("outputX", 400);
		// intent.putExtra("outputY", 400);
		intent.putExtra("noFaceDetection", true);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", false);
		// intent.putExtra("return-data", true);
		activity.startActivityForResult(intent, IMAGE_CROP);
	}

	public static void resetPhotp(String pathName) {

		BufferedOutputStream bos = null;
		Bitmap icon = null;
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(pathName, options); // 此时返回bm为空
			float percent = options.outHeight > options.outWidth ? options.outHeight / 960f : options.outWidth / 960f;

			if (percent < 1) {
				percent = 1;
			}
			int width = (int) (options.outWidth / percent);
			int height = (int) (options.outHeight / percent);
			icon = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

			// 初始化画布 绘制的图像到icon上
			Canvas canvas = new Canvas(icon);
			// 建立画笔
			Paint photoPaint = new Paint();
			// 获取跟清晰的图像采样
			photoPaint.setDither(true);
			// 过滤一些
			// photoPaint.setFilterBitmap(true);
			options.inJustDecodeBounds = false;

			Bitmap prePhoto = BitmapFactory.decodeFile(pathName);
			if (percent > 1) {
				prePhoto = Bitmap.createScaledBitmap(prePhoto, width, height, true);
			}

			canvas.drawBitmap(prePhoto, 0, 0, photoPaint);

			if (prePhoto != null && !prePhoto.isRecycled()) {
				prePhoto.recycle();
				prePhoto = null;
				System.gc();
			}

			// 设置画笔
			Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);
			// 字体大小
			textPaint.setTextSize(50.0f);
			// 采用默认的宽度
			textPaint.setTypeface(Typeface.DEFAULT);
			// 采用的颜色
			textPaint.setColor(Color.YELLOW);
			// 阴影设置
			// textPaint.setShadowLayer(3f, 1, 1, Color.DKGRAY);

//			// 时间水印
//			String mark = getCurrTime("yyyy-MM-dd HH:mm:ss");
//			float textWidth = textPaint.measureText(mark);
//			canvas.drawText(mark, width - textWidth - 10, height - 26, textPaint);

			bos = new BufferedOutputStream(new FileOutputStream(pathName));

			int quaility = (int) (100 / percent > 80 ? 80 : 100 / percent);
			icon.compress(CompressFormat.JPEG, quaility, bos);
			bos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (icon != null && !icon.isRecycled()) {
				icon.recycle();
				icon = null;
				System.gc();
			}
		}
	}

	private static String getCurrTime(String pattern) {
		if (pattern == null) {
			pattern = "yyyyMMddHHmmss";
		}
		return (new SimpleDateFormat(pattern)).format(new Date());
	}

	public String getData(String pattern) {
		if (pattern == null) {
			pattern = "yyyyMMddHHmmss";
		}
		return (new SimpleDateFormat(pattern)).format(new Date());
	}
}

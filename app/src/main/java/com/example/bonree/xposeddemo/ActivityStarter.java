//package com.example.bonree.xposeddemo;
//
//import java.net.URI;
//
//import android.app.ActivityManagerNative;
//import android.app.IActivityManager;
//import android.app.IApplicationThread;
//import android.app.IActivityManager.WaitResult;
//import android.content.Intent;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.IBinder;
//import android.os.ParcelFileDescriptor;
//import android.os.SystemClock;
//import android.util.Log;
//
//public class ActivityStarter {
//	private static final String TAG = "ActivityStarter";
//
//	public static void main(String[] args){
//		 long startTimeMs = 0;
//		if(args != null && args.length>0){
//			IActivityManager activityManager = ActivityManagerNative.getDefault();
//
//	        Intent intent = ServiceStarter.makeIntent(args);
//	        if(intent != null){
//		        intent.setAction(Intent.ACTION_MAIN);
//				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				intent.addCategory(Intent.CATEGORY_LAUNCHER);
//
//		        startTimeMs = SystemClock.uptimeMillis();
//		        log("AppStarter startTimeMs:"+startTimeMs);
//		        WaitResult result = null;
//				switch(Build.VERSION.SDK_INT){
//				/**
//				 * 不支持
//				case 1-7
//				case 8:
//				case 9://2.3.6
//				 * public int  [More ...] startActivity(IApplicationThread caller,
//		             Intent intent, String resolvedType, Uri[] grantedUriPermissions,
//		             int grantedMode, IBinder resultTo, String resultWho, int requestCode,
//		             boolean onlyIfNeeded, boolean debug) throws RemoteException;
//				 */
//				case 14://4.0
//				case 15://4.0
//	//				public int startActivity(IApplicationThread caller,
//	//				 			Intent intent, String resolvedType, Uri[] grantedUriPermissions,
//	//				 			int grantedMode, IBinder resultTo, String resultWho, int requestCode,
//	//				 			boolean onlyIfNeeded, boolean debug, String profileFile,
//	//				 			ParcelFileDescriptor profileFd, boolean autoStopProfiler) throws RemoteException;
//	//				public WaitResult startActivityAndWait(IApplicationThread caller,
//	//				 			Intent intent, String resolvedType, Uri[] grantedUriPermissions,
//	//				 			int grantedMode, IBinder resultTo, String resultWho, int requestCode,
//	//				 			boolean onlyIfNeeded, boolean debug, String profileFile,
//	//				 			ParcelFileDescriptor profileFd, boolean autoStopProfiler) throws RemoteException;
//
//	//				new Reflect(activityManager)
//	//				.method("startActivity", IApplicationThread.class,
//	//						Intent.class,String.class,URI[].class,
//	//						int.class,IBinder.class,String.class,int.class,
//	//						boolean.class,boolean.class,String.class,
//	//						ParcelFileDescriptor.class,boolean.class)
//	//				.type("android.app.IActivityManager")
//	//				.invoke(int.class,
//	//						null,
//	//						intent,null,null,
//	//						0,null,null,0,
//	//						false,false,null,
//	//						null,false);
//
//					result = new Reflect(activityManager)
//							.method("startActivityAndWait", IApplicationThread.class,
//									Intent.class, String.class, URI[].class,
//									int.class, IBinder.class, String.class, int.class,
//									boolean.class, boolean.class, String.class,
//									ParcelFileDescriptor.class, boolean.class)
//							.type("android.app.IActivityManager")
//							.invoke(WaitResult.class,
//									null,
//									intent, null, null,
//									0, null, null, 0,
//									false, false, null,
//									null, false);
//					break;
//				case 16://4.1.1  4.1.2
//	//				 public int  [More ...] startActivity(IApplicationThread caller,
//	//						              Intent intent, String resolvedType, IBinder resultTo, String resultWho,
//	//						              int requestCode, int flags, String profileFile,
//	//						              ParcelFileDescriptor profileFd, Bundle options) throws RemoteException;
//	//				 public WaitResult  [More ...] startActivityAndWait(IApplicationThread caller,
//	//						              Intent intent, String resolvedType, IBinder resultTo, String resultWho,
//	//						              int requestCode, int flags, String profileFile,
//	//						              ParcelFileDescriptor profileFd, Bundle options) throws RemoteException;
//	//				new Reflect(activityManager)
//	//				.method("startActivity", IApplicationThread.class,
//	//						Intent.class,String.class,IBinder.class,String.class,
//	//						int.class,int.class,String.class,
//	//						ParcelFileDescriptor.class,Bundle.class)
//	//				.type("android.app.IActivityManager")
//	//				.invoke(int.class,
//	//						null,
//	//						intent, null, null, null,
//	//						0, 0, null,
//	//						null,null);
//
//					result = new Reflect(activityManager)
//					.method("startActivityAndWait", IApplicationThread.class,
//							Intent.class, String.class, IBinder.class, String.class,
//							int.class, int.class, String.class,
//							ParcelFileDescriptor.class, Bundle.class)
//					.type("android.app.IActivityManager")
//					.invoke(WaitResult.class,
//							null,
//							intent, null, null,null,
//							0, 0, null,
//							null, null);
//					break;
//				case 17://4.2 4.2.2
//	//				public int  [More ...] startActivity(IApplicationThread caller,
//	//						             Intent intent, String resolvedType, IBinder resultTo, String resultWho,
//	//						             int requestCode, int flags, String profileFile,
//	//						             ParcelFileDescriptor profileFd, Bundle options) throws RemoteException;
//	//				public WaitResult  [More ...] startActivityAndWait(IApplicationThread caller,
//	//						             Intent intent, String resolvedType, IBinder resultTo, String resultWho,
//	//						             int requestCode, int flags, String profileFile,
//	//						             ParcelFileDescriptor profileFd, Bundle options, int userId) throws RemoteException;
//	//				new Reflect(activityManager)
//	//				.method("startActivity", IApplicationThread.class,
//	//						Intent.class,String.class,IBinder.class,String.class,
//	//						int.class,int.class,String.class,
//	//						ParcelFileDescriptor.class,Bundle.class)
//	//				.type("android.app.IActivityManager")
//	//				.invoke(int.class,
//	//						null,
//	//						intent, null, null, null,
//	//						0, 0, null,
//	//						null,null);
//
//					result = new Reflect(activityManager)
//					.method("startActivityAndWait", IApplicationThread.class,
//							Intent.class, String.class, IBinder.class, String.class,
//							int.class, int.class, String.class,
//							ParcelFileDescriptor.class, Bundle.class,int.class)
//					.type("android.app.IActivityManager")
//					.invoke(WaitResult.class,
//							null,
//							intent, null, null,null,
//							0, 0, null,
//							null, null,0);
//					break;
//				case 18://4.3 4.3.1
//				case 19://4.4 - 4.4.2 - 4.4.4
//	//				public int  [More ...] startActivity(IApplicationThread caller, String callingPackage,
//	//						             Intent intent, String resolvedType, IBinder resultTo, String resultWho,
//	//						             int requestCode, int flags, String profileFile,
//	//						             ParcelFileDescriptor profileFd, Bundle options) throws RemoteException;
//
//	//				public WaitResult  [More ...] startActivityAndWait(IApplicationThread caller, String callingPackage,
//	//						             Intent intent, String resolvedType, IBinder resultTo, String resultWho,
//	//						             int requestCode, int flags, String profileFile,
//	//						             ParcelFileDescriptor profileFd, Bundle options, int userId) throws RemoteException;
//
//	//				new Reflect(activityManager)
//	//				.method("startActivity", IApplicationThread.class,String.class,
//	//						Intent.class,String.class,IBinder.class,String.class,
//	//						int.class,int.class,String.class,
//	//						ParcelFileDescriptor.class,Bundle.class)
//	//				.type("android.app.IActivityManager")
//	//				.invoke(int.class,
//	//						null, null,
//	//						intent, null, null, null,
//	//						0, 0, null,
//	//						null,null);
//					result = new Reflect(activityManager)
//							.method("startActivityAndWait", IApplicationThread.class,String.class,
//									Intent.class, String.class, IBinder.class, String.class,
//									int.class, int.class, String.class,
//									ParcelFileDescriptor.class, Bundle.class, int.class)
//							.type("android.app.IActivityManager")
//							.invoke(WaitResult.class,
//									null, null,
//									intent, null, null, null,
//									0, 0, null,
//									null, null, 0);
//					break;
//				case 21://5.0
//				case 22://5.1
//				case 23://6.0
//				case 24://7.0
//				default:
//					Class<?> profilerInfo = getProfilerInfoClass();
//					if(profilerInfo!=null){
//						/**
//						 * public WaitResult startActivityAndWait(IApplicationThread caller, String callingPackage,
//						Intent intent, String resolvedType, IBinder resultTo, String resultWho,
//						int requestCode, int flags, ProfilerInfo profilerInfo, Bundle options,
//						int userId) throws RemoteException;
//						userId UserHandle.USER_CURRENT = -2  //A user id to indicate the currently active user
//						 */
//						result = new Reflect(activityManager)
//								.method("startActivityAndWait", IApplicationThread.class,
//										String.class, Intent.class, String.class,
//										IBinder.class, String.class, int.class, int.class,
//										profilerInfo, Bundle.class, int.class)
//								.type("android.app.IActivityManager")
//								.invoke(WaitResult.class,
//										null,
//										null, intent, null,
//										null, null, 0, 0,
//										null, null,0);
//					}else{
//						log("ProfilerInfo.class not found!!");
//					}
//					break;
//				}
//				if(result!=null){
//					log("result:"+result.result);
//					log("thisTime:"+result.thisTime);
//					log("totalTime:"+result.totalTime);
//					log("timeout:"+result.timeout);
//				}
//	        }else{
//	        	for(String arg : args){
//	        		System.out.println("ActivityStarter Fail! make intent fail!! "+arg);
//	        	}
//	        }
//		}else{
//			startTimeMs = SystemClock.uptimeMillis();
//			log("AppStarter startTimeMs:"+startTimeMs);
//			log("AppStarter Fail! args is null, can not find service!");
//		}
//
//		log("AppStarter TotalTimeMs:"+(SystemClock.uptimeMillis()-startTimeMs));
//	}
//
//	private static Class<?> getProfilerInfoClass() {
//		try {
//			return Class.forName("android.app.ProfilerInfo");
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//		try{
//			return Class.forName("android.app.ProfilerInfo", false, ParcelFileDescriptor.class.getClassLoader());
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	public static void log(String info) {
//		System.out.println(info);
//		Log.e(TAG, "------"+info);
//	}
//
//}

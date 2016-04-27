package kim.uno.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

public class ProgressUtil {
	
	// progress instance 
	static ProgressDialog mProgressDialog = null;
	static String mPreMessage = null;
	
	public static boolean isShowing() {
		return mProgressDialog != null && mProgressDialog.isShowing();
	}
	
	public static void show(Context context) {
		show(context, "Loading...");
	}
	
	public static void show(Context context, final int message) {
		show(context, context.getString(message));
	}

    public static void show(Context context, String message) {
        show(context, message, false);
    }
	
	public static void show(Context context, final String message, boolean isCancelable) {
		
		// 초기 호출 
		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(context);
		
		// 진행중
		} else if (isShowing()) {
			
			// 진행중이지만 메시지가 변경됨 
			if (!mPreMessage.equals(message)) {
				mPreMessage = message;
				
				// 재사용 가능 
				if (context instanceof Activity) {
					((Activity) context).runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							mProgressDialog.setMessage(message);
						}
					});
					
				// 재사용이 불가능 
				} else {
					mProgressDialog.dismiss();
					show(context, message);
				}
				
			// 메시지가 동일한 경우  
			} else { }
			return;
		}
		
		mPreMessage = message;
		
		mProgressDialog = mProgressDialog == null ? new ProgressDialog(context) : mProgressDialog;
		mProgressDialog.setMessage(message);
		mProgressDialog.setCancelable(isCancelable);

        if (context instanceof Activity) {
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mProgressDialog.show();
                }
            });
        } else {
            mProgressDialog.show();
        }
	}
    
    public static void dismiss() {
        if(mProgressDialog != null){
        	mProgressDialog.dismiss();
        	mProgressDialog = null;
        	mPreMessage = null;
        }
    }
    
}

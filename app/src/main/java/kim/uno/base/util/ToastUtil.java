package kim.uno.base.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

    // field
    private static Toast mToast;

    public static void show(Context context, int message) {
        show(context, context.getString(message));
    }

    public static void show(Context context, String message) {
        show(context, message, Toast.LENGTH_SHORT);
    }

    public static void show(final Context context, final int message, final int duration) {
        show(context, context.getString(message), duration);
    }

    public static void show(final Context context, final String message, final int duration) {

        try {

            // from activity
            if (context instanceof Activity) {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        makeText(context, message, duration);
                    }
                });
            }

            // other context
            else {
                makeText(context, message, duration);
            }
        }

        catch (Exception e) { LogUtil.e(e); }
    }

    private static void makeText(Context context, String message, int duration) {
        dismiss();

//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View innerView = inflater.inflate(R.layout.toast, null);
//        innerView.setPadding(0, 0, 0, (int) (PrefUtil.getInt(context, Consts.SP_DISPLAY_HEIGHT) * 0.1f));
//        ((TextView) innerView.findViewById(R.id.tv_toast)).setText(message);

        mToast = Toast.makeText(context, message, duration);
//        mToast.setView(innerView);
//        mToast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 0);
        mToast.setDuration(duration);
        mToast.show();
    }

    public static void dismiss() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}

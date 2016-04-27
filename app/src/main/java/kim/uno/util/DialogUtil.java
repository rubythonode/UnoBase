package kim.uno.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import static android.content.DialogInterface.OnClickListener;
import static android.content.DialogInterface.OnDismissListener;

public class DialogUtil {

    static final String POSITIVE = "확인";
    static final String NEGATIVE = "취소";

    // instance
    static DialogUtil mInstance;

    // field
    private Context mContext;
    private AlertDialog.Builder mAlertBuilder;
    private ArrayList<AlertDialog> mDialogStack;

    private boolean isModifyPositive;
    private boolean isModifyNegative;
    private boolean isModifyDismiss;

    public static DialogUtil getInstance(Context context) {
        if (mInstance == null) mInstance = new DialogUtil();
        mInstance.init(context);
        return mInstance;
    }

    private DialogUtil() { }

    public void init(Context context) {
        mContext = context;
        mAlertBuilder = new AlertDialog.Builder(context);
        isModifyPositive = false;
        isModifyNegative = false;
        isModifyDismiss = false;
    }


    /*
     * -------------------------------------------------------------------
     * Interface
     * -------------------------------------------------------------------
     */

    public interface OnItemClickListener<T> {
        void onItemClick(T item, int position);
    }


    /*
     * -------------------------------------------------------------------
     * Listener
     * -------------------------------------------------------------------
     */

    public DialogUtil positive() {
        return positive(null, null);
    }

    public DialogUtil positive(int button) {
        return positive(mContext.getString(button));
    }

    public DialogUtil positive(String button) {
        return positive(button, null);
    }

    public DialogUtil positive(OnClickListener listener) {
        return positive(null, listener);
    }

    public DialogUtil positive(String button, OnClickListener listener) {
        if (TextUtils.isEmpty(button)) button = POSITIVE;
        mAlertBuilder.setPositiveButton(button, listener);
        isModifyPositive = true;
        return this;
    }

    public DialogUtil negative() {
        return negative(null, null);
    }

    public DialogUtil negative(int button) {
        return negative(mContext.getString(button));
    }

    public DialogUtil negative(String button) {
        return negative(button, null);
    }

    public DialogUtil negative(OnClickListener listener) {
        return negative(null, listener);
    }

    public DialogUtil negative(String button, OnClickListener listener) {
        if (TextUtils.isEmpty(button)) button = NEGATIVE;
        mAlertBuilder.setPositiveButton(button, listener);
        isModifyNegative = true;
        return this;
    }

    /**
     * 해당 메소드 처리가 부족합니다
     * dimiss되는 경우 dismissAll() 을 반드시 호출하여 정리해주세요
     * @param listener
     * @return
     */
    @Deprecated
    public DialogUtil dismiss(OnDismissListener listener) {
        mAlertBuilder.setOnDismissListener(listener);
        isModifyDismiss = true;
        return this;
    }

    public DialogUtil title(int title) {
        return title(mContext.getString(title));
    }

    public DialogUtil title(String title) {
        mAlertBuilder.setTitle(title);
        return this;
    }

    public DialogUtil message(int message) {
        return message(mContext.getString(message));
    }

    public DialogUtil message(String message) {
        mAlertBuilder.setMessage(message);
        return this;
    }

    public DialogUtil items(CharSequence[] labels, OnItemClickListener listener) {
        return items(labels, null, listener);
    }

    public DialogUtil items(CharSequence[] labels, final List items, final OnItemClickListener listener) {
        mAlertBuilder.setItems(labels, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {

                // 리스너를 등록한 경우
                if (listener != null) {

                    // 반환할 오브젝트가 없는경우
                    if (items == null || items.size() <= position) {
                        listener.onItemClick(null, position);
                    }

                    // 선택된 오브젝트를 반환
                    else {
                        listener.onItemClick(items.get(position), position);
                    }
                }
            }
        });
        return this;
    }


    /*
     * -------------------------------------------------------------------
     * Show
     * -------------------------------------------------------------------
     */

    public void showAlert() {
        showAlert(true);
    }

    public void showAlert(boolean isCanceledOnTouchOutside) {
        if (!isModifyPositive) positive();
        show(isCanceledOnTouchOutside);
    }

    public void showConfirm() {
        showConfirm(true);
    }

    public void showConfirm(boolean isCanceledOnTouchOutside) {
        if (!isModifyPositive) positive();
        if (!isModifyNegative) negative();
        show(isCanceledOnTouchOutside);
    }

    public void show(final boolean isCanceledOnTouchOutside) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                final AlertDialog alertDialog = mAlertBuilder.create();
                alertDialog.setCanceledOnTouchOutside(isCanceledOnTouchOutside);

                if (!isModifyDismiss) {
                    alertDialog.setOnDismissListener(new OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            if (mDialogStack.contains(alertDialog)) {
                                mDialogStack.remove(alertDialog);
                            }
                        }
                    });
                }

                alertDialog.show();

                if (mDialogStack == null) mDialogStack = new ArrayList<AlertDialog>();
                mDialogStack.add(0, alertDialog);
            }
        });
    }

    public static boolean isShowing() {
        return mInstance != null
                && mInstance.mDialogStack != null && mInstance.mDialogStack.size() > 0
                && mInstance.mDialogStack.get(0).isShowing();
    }

    public static void dismissAll() {
        if (isShowing()) {
            for (int i = 0; i < mInstance.mDialogStack.size(); i++) {
                Dialog dialog = mInstance.mDialogStack.get(0);
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }

                if (mInstance.mDialogStack.contains(dialog)) {
                    mInstance.mDialogStack.remove(dialog);
                }
            }
        }
    }
    
}

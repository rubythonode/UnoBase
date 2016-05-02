package kim.uno.base.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import kim.uno.base.consts.Consts;

import static android.provider.Settings.Secure;

public class IoUtil {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-z0-9._%+-]*@[a-z0-9.-]*\\.[a-z]{2,6}");
    private static final char HANGUL_BEGIN_UNICODE = 44032; // 가
    private static final char HANGUL_END_UNICODE = 55203; // 힣
    private static final char HANGUL_BASE_CHOSUNG_UNIT = 588;
    private static final char HANGUL_BASE_JONGSUNG_UNIT = 28;
    private static final char[] CHOSUNG_LIST = {
            'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ',
            'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ' };

    // 종성 리스트. 00 ~ 27 + 1(1개 없음)
    private static final char[] JONGSUNG_LIST = {
            ' ', 'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ',
            'ㄹ', 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ',
            'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅊ',
            'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    };
	
	public static boolean isNull(String string) {
		return string == null || string.trim().length() == 0 || string.equals("null");
	}

    public static boolean isNotNull(String string) {
        return string != null && string.trim().length() > 0 && !string.equals("null");
    }

    public static boolean isEmpty(List list) {
        return list == null || list.size() == 0;
    }

    public static boolean isNotEmpty(List list) {
        return list != null && list.size() > 0;
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.size() == 0;
    }

    public static boolean isNotEmpty(Map map) {
        return map != null && map.size() > 0;
    }

    public static boolean isContainsNull(String... strings) {
        for (String string : strings) {
            if (isNull(string)) return true;
        }
        return false;
    }

    public static boolean isContainsNotNull(String... strings) {
        for (String string : strings) {
            if (isNotNull(string)) return true;
        }
        return true;
    }

    public static void deleteFile(String path) {
        try {
            File file = new File(path);
            if (file.exists()) deleteRecursive(file);
        } catch (Exception e) { LogUtil.e(e); }
    }

    public static void deleteRecursive(File fileOrDirectory) throws IOException {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteRecursive(child);

        String path = Consts.DIR_ROOT;

        // root
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // temp
        path += Consts.DIR_SEPARATOR + Consts.DIR_TRASH;
        dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // nomedia
        dir = new File(path + Consts.DIR_SEPARATOR + ".nomedia");
        if (!dir.exists()) {
            dir.createNewFile();
        }

        File trash  = new File(path + Consts.DIR_SEPARATOR + System.currentTimeMillis());
        fileOrDirectory.renameTo(trash);
        trash.delete();
    }

    public static Uri saveBitmap(Bitmap bitmap) {
        return saveBitmap(bitmap, Consts.DIR_MEDIA);
    }

    public static Uri saveBitmap(Bitmap bitmap, String directory) {
        String path = Consts.DIR_ROOT;

        // root
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // media
        path += Consts.DIR_SEPARATOR + directory;
        dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // file
        path += Consts.DIR_SEPARATOR + System.currentTimeMillis() + ".png";
        File file = new File(path);

        try {
            file.createNewFile();
            FileOutputStream filestream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, filestream);
            filestream.close();
        }

        catch (Exception e) {
            LogUtil.e(e);
            return saveBigBitmap(bitmap, directory);
        }
        return Uri.parse(path);
    }

    public static Uri saveBigBitmap(Bitmap bitmap, String directory) {
        String path = Consts.DIR_ROOT;

        // root
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // media
        path += Consts.DIR_SEPARATOR + directory;
        dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // file
        path += Consts.DIR_SEPARATOR + System.currentTimeMillis() + ".png";
        File file = new File(path);

        try {
            file.createNewFile();
            FileOutputStream filestream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, filestream);
            filestream.close();
        }

        catch (Exception e) {
            LogUtil.e(e);
            return null;
        }
        return Uri.parse(path);
    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager mgr = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static boolean verifyEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public static String getAndroidId(Context context) {
        String androidId = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        androidId = getMD5Hash(androidId);
        return androidId;
    }

    public static String getMD5Hash(String s) {
        MessageDigest m = null;
        String hash = null;

        try {
            m = MessageDigest.getInstance("MD5");
            m.update(s.getBytes(), 0, s.length());
            hash = new BigInteger(1, m.digest()).toString(16);
        } catch (Exception e) { LogUtil.e(e); }
        return hash;
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) return capitalize(model);
        else return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String s) {
        if (s == null || s.length() == 0) return "";
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) return s;
        else return Character.toUpperCase(first) + s.substring(1);
    }

    public static String getDeviceVersion() {
        return Build.VERSION.RELEASE + "";
    }

    public static String getAppVersion(Context context) {
        PackageInfo pInfo = null;
        try { pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) { LogUtil.e(e); }
        return pInfo.versionName;
    }

    public static String makeChosung(String name) {
        String chosung = "";
        char cho;
        char afterCho;
        for(int i=0; i < name.length(); i++){
            cho = name.charAt(i);
            if(cho < HANGUL_BEGIN_UNICODE){
                afterCho = cho;
            } else {
                try{
                    afterCho = CHOSUNG_LIST[(cho-HANGUL_BEGIN_UNICODE)/ HANGUL_BASE_CHOSUNG_UNIT];
                } catch (Exception e) {
                    afterCho = cho;
                }
            }
            chosung += afterCho;
        }
        return chosung;
    }

    public static String makeChosungFirst(String name) {
        String chosung = "";
        char cho;
        char afterCho;
        if(name.length() > 0){
            cho = name.charAt(0);
            if(cho < HANGUL_BEGIN_UNICODE){
                afterCho = cho;
            } else {
                try{
                    afterCho = CHOSUNG_LIST[(cho-HANGUL_BEGIN_UNICODE)/ HANGUL_BASE_CHOSUNG_UNIT];
                } catch (Exception e) {
                    afterCho = cho;
                }
            }
            chosung += afterCho;
        }
        return chosung;
    }

    public static String makeJosa(String word) {
        return makeJosa(word, "를", "을");
    }

    public static String makeJosa(String word, String none, String josa) {
        String result = none;
        try {
            int cBase = word.charAt(word.length() - 1) - HANGUL_BEGIN_UNICODE;
            int c1 = cBase / HANGUL_BASE_CHOSUNG_UNIT;                                              // 초성
            int c2 = (cBase - (HANGUL_BASE_CHOSUNG_UNIT * c1)) / HANGUL_BASE_JONGSUNG_UNIT;         // 중성
            int c3 = (cBase - (HANGUL_BASE_CHOSUNG_UNIT * c1) - (HANGUL_BASE_JONGSUNG_UNIT * c2));  // 종성
            result =  JONGSUNG_LIST[c3] == ' ' ? none : josa;
        } catch (Exception e) { LogUtil.e(e); }
        return result;
    }

}

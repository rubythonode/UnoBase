package kim.uno.base.util;

import android.content.Context;
import android.graphics.Typeface;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class FontUtil {
     
    static Map<String, Typeface> mTypeface;

    public static Typeface getFont(Context context, String font) {
        if (mTypeface == null) mTypeface = new HashMap<>();
        if (mTypeface.containsKey(font) == false || mTypeface.get(font) == null)
            mTypeface.put(font, Typeface.createFromAsset(context.getAssets(), font));
        return mTypeface.get(font);
    }
 
    public static Typeface getFontRegular(Context context){
        return getFont(context, "BMDOHYEON_otf.otf");
    }

    public static void initDefaultFont(Context context) {
        setDefaultFont(getFontRegular(context));
    }

    public static void setDefaultFont(Typeface font) {
        replaceFont("DEFAULT", font);
        replaceFont("SANS_SERIF", font);
        replaceFont("SERIF", font);
    }

    protected static void replaceFont(String staticTypefaceFieldName, final Typeface newTypeface) {
        try {
            Field StaticField = Typeface.class.getDeclaredField(staticTypefaceFieldName);
            StaticField.setAccessible(true);
            StaticField.set(null, newTypeface);
        } catch (Exception e) { LogUtil.e(e); }
    }
 
}
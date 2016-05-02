package kim.uno.base.consts;

import android.os.Environment;

public class Consts {


    /*
     * -------------------------------------------------------------------
     * Release info
     * -------------------------------------------------------------------
     */

    public static final String OS = "android";


    /*
     * -------------------------------------------------------------------
     * Directory
     * -------------------------------------------------------------------
     */

    public static final String DIR_SEPARATOR = System.getProperty("file.separator");                                        // file separator
    public static final String DIR_ROOT = Environment.getExternalStorageDirectory() + DIR_SEPARATOR + "BoardTown";          // app root
    public static final String DIR_MEDIA = "media";                                                                         // media folder
    public static final String DIR_TRASH = "trash";                                                                         // trash folder


    /*
     * -------------------------------------------------------------------
     * Google cloud messaging
     * -------------------------------------------------------------------
     */

    public static final String GCM_SENDER_ID = "120139937087";                                      // unoo project number
    public static final String GCM_API_KEY = "AIzaSyDrSO-jw272w4yBchSJIoJHaMukvaAYebM";             // boardtown package api key


    /*
     * -------------------------------------------------------------------
     * SharedPreferences
     * -------------------------------------------------------------------
     */

    public static final String SP_DISPLAY_WIDTH                     = "display_width";
    public static final String SP_DISPLAY_HEIGHT                    = "display_height";


    /*
     * -------------------------------------------------------------------
     * Activity request code
     * -------------------------------------------------------------------
     */

    public static final String REQUEST_CODE 				    = "requestCode";
    public static final int REQUEST_CODE_SPLASH 				= 1000;
    public static final int REQUEST_CODE_MAIN                   = 1010;
    public static final int REQUEST_CODE_GROUP                  = 1020;
    public static final int REQUEST_CODE_CLIP                   = 1021;
    public static final int REQUEST_CODE_LECTURE                = 1022;
    public static final int REQUEST_CODE_MAGAZINE               = 1023;
    public static final int REQUEST_CODE_SHOP                   = 1024;
    public static final int REQUEST_CODE_WEBVIEW                = 1025;
    public static final int REQUEST_CODE_YOUTUBE                = 1026;
    public static final int REQUEST_CODE_GROUP_DETAIL           = 1030;
    public static final int REQUEST_CODE_CLIP_DETAIL            = 1031;
    public static final int REQUEST_CODE_SHOP_DETAIL            = 1032;
    public static final int REQUEST_CODE_MAGAZINE_DETAIL        = 1033;

}

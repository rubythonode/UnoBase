package kim.uno.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kim.uno.BuildConfig;

import static com.bumptech.glide.request.animation.ViewPropertyAnimation.Animator;

public class ImageLoader {

    /** STATIC */
    // instance
    static ImageLoader instance;

    // default image
    public static final int NONE_WAITING = 0;
    public static final int NONE_ERROR = 0;

    // transformation
    private static  CenterCrop transCenerCrop;
    private static  RoundedCornerTransformation transRoundedCorner;
    private static  CircleTransformation transCicle;

    // animation
    private Animator defaultAnimator = new Animator() {
        @Override
        public void animate(View view) {
            view.setAlpha(0f);
            view.animate()
                    .alpha(1f)
                    .setDuration(300);
        }
    };

    /** FIELD */
    private Context mContext;
    private ModelCache<String, GlideUrl> mUrlCache;
    private LoaderVariable mLoaderVariable;

    // image
    private int mWatingImageResId = NONE_WAITING;
    private int mErrorImageResId = NONE_ERROR;

    // transformation
    private enum Transform { CENTER_CROP, ROUNDED_CORNER, CIRCLE }
    private Transform mTransform;
    private int mWidth, mHeight;

    // animation
    private Animator mAnimator = defaultAnimator;

    public static ImageLoader getInstance(Context context) {
        if (instance == null) {
            instance = new ImageLoader(context);
        }
        instance.init(context);
        return instance;
    }

    private void init(Context context) {
        mContext = context;
        mTransform = null;
        mAnimator = defaultAnimator;
    }

    private ImageLoader(Context context) {
        mUrlCache = new ModelCache<String, GlideUrl>();
        mLoaderVariable = new LoaderVariable(context, mUrlCache);

        transCenerCrop = new CenterCrop(context);
        transRoundedCorner = new RoundedCornerTransformation(context);
        transCicle = new CircleTransformation(context);
    }

    private Transformation getTransformation() {
        if (mTransform == null) return null;

        switch (mTransform) {
            case CENTER_CROP: return transCenerCrop;
            case ROUNDED_CORNER: return transRoundedCorner;
            case CIRCLE: return transCicle;
        }

        return null;
    }

    public Animator getAnimator() {
        return mAnimator;
    }

    public ImageLoader setAnimator(Animator animator) {
        this.mAnimator = animator;
        return this;
    }

    public int getErrorImageResId() {
        return mErrorImageResId;
    }

    public int getWatingImageResId() {
        return mWatingImageResId;
    }

    public ImageLoader centerCrop() {
        mTransform = Transform.CENTER_CROP;
        return this;
    }

    public ImageLoader roundedCorner() {
        mTransform = Transform.ROUNDED_CORNER;
        return this;
    }

    public ImageLoader circle() {
        circle(0, 0);
        return this;
    }

    public ImageLoader circle(int width, int height) {
        mTransform = Transform.CIRCLE;
        mWidth = width;
        mHeight = height;
        return this;
    }

    public ImageLoader waitingImage(int waitingImageResId) {
        mWatingImageResId = waitingImageResId;
        return this;
    }

    public ImageLoader errorImage(int errorImageResId) {
        mErrorImageResId = errorImageResId;
        return this;
    }

    public BitmapTypeRequest<String> load(String url) {
//        return Glide.with(mContext).load(url).asBitmap();
        return Glide.with(mContext).using(mLoaderVariable).load(url).asBitmap();
    }

    public BitmapTypeRequest<Uri> load(int drawable) {
        return load(Uri.parse("android.resource://" + BuildConfig.APPLICATION_ID + "/" + drawable));
    }

    public BitmapTypeRequest<Uri> load(Uri uri) {
        return Glide.with(mContext).load(uri).asBitmap();
    }

    public void blur(ImageView iv, String url) {
        blur(iv, url, 5);
    }

    public void blur(ImageView iv, Uri uri) {
        blur(iv, uri, 5);
    }

    public void blur(ImageView iv, int drawable) {
        blur(iv, drawable, 5);
    }

    public void blur(ImageView iv, String url, int radius) {
        blur(iv, load(url), radius);
    }

    public void blur(ImageView iv, Uri uri, int radius) {
        blur(iv, load(uri), radius);
    }

    public void blur(ImageView iv, int drawable, int radius) {
        blur(iv, load(drawable), radius);
    }

    private void blur(final ImageView iv, BitmapTypeRequest request, final int radius) {
        request.into(new SimpleTarget<Bitmap>(100, 100) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                iv.setImageBitmap(blur(resource, radius));
            }
        });
    }

    public void load(ImageView imageView, String url) {
        load(imageView, load(url));
    }

    public void load(ImageView imageView, int drawable) {
        load(imageView, load(Uri.parse("android.resource://" + BuildConfig.APPLICATION_ID + "/" + drawable)));
    }

    public void load(ImageView imageView, Uri uri) {
        load(imageView, load(uri));
    }

    public void load(final ImageView imageView, final BitmapTypeRequest request) {

        // transformation
        if (getTransformation() != null) {
            request.transform(getTransformation());
        } else {
            request.dontTransform();
        }

        // animate
        if (getAnimator() != null) {
            request.animate(getAnimator());
        } else {
            request.dontAnimate();
        }

        // waiting
        if (getWatingImageResId() != 0) {
            request.placeholder(getWatingImageResId());
        }

        // error
        if (getErrorImageResId() != 0) {
            request.error(getErrorImageResId());
        }

        if (mContext instanceof Activity) {
            ((Activity) mContext).runOnUiThread(new Runnable() {
                public void run() { request.into(imageView); }
            });
        } else {
            request.into(imageView);
        }
    }

    public void loadGif(final ImageView imageView, String url) {

        if (isGif(url) == false) {
            load(imageView, url);
            return;
        }

        final DrawableTypeRequest<String> request = Glide.with(mContext).load(url);

        // transformation
        if (getTransformation() != null) {
            request.transform(getTransformation());
        } else {
            request.dontTransform();
        }

        // animate
        if (getAnimator() != null) {
            request.animate(getAnimator());
        } else {
            request.dontAnimate();
        }

        // waiting
        if (getWatingImageResId() != 0) {
            request.placeholder(getWatingImageResId());
        }

        // error
        if (getErrorImageResId() != 0) {
            request.error(getErrorImageResId());
        }

        if (mContext instanceof Activity) {
            ((Activity) mContext).runOnUiThread(new Runnable() {
                public void run() { request.into(imageView); }
            });
        } else {
            request.into(imageView);
        }
    }

    public boolean isGif(String url) {
        if (url == null || url.trim().length() == 0) {
            return false;
        }

        String[] split = url.split("\\.");
        int max = split.length;
        if (max <= 0) {
            return false;
        }
        String format = split[max - 1];
        return format.toLowerCase().contains("gif");
    }

    public class RoundedCornerTransformation extends BitmapTransformation {

        private Context mContext;
        public RoundedCornerTransformation(Context context) {
            super(context);
            mContext = context;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            int width = toTransform.getWidth();
            int height = toTransform.getHeight();
            Bitmap result = pool.get(width, height, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            }
            int radius = (int) (5 * mContext.getResources().getDisplayMetrics().density);
            Canvas canvas = new Canvas(result);

            RectF rectF = new RectF(0, 0, width, height);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setDither(true);
            BitmapShader shader = new BitmapShader(
                    toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            paint.setShader(shader);
            canvas.drawRoundRect(rectF, radius, radius, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }
    }

    public class CircleTransformation extends BitmapTransformation {

        public CircleTransformation(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            int width = toTransform.getWidth();
            int height = toTransform.getHeight();
            Bitmap result = pool.get(width, height, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            RectF rectF = new RectF(0, 0, width, height);
            if (mWidth != 0 && mHeight != 0) {
                rectF.set(0, 0, mWidth, mHeight);
            }
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setDither(true);
            BitmapShader shader = new BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            paint.setShader(shader);

            canvas.drawOval(rectF, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }
    }

    public static Bitmap blur(Bitmap bitmap, int radius) {
        Bitmap result;
        if(bitmap.getConfig() == null){
//			bitmap = sentBitmap;
            return bitmap;
        }else{
            result = bitmap.copy(bitmap.getConfig(), true);
        }

        if (radius < 1) {
            return (null);
        }

        int w = result.getWidth();
        int h = result.getHeight();

        int[] pix = new int[w * h];
//        //Log.e("pix", w + " " + h + " " + pix.length);
        result.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = ( 0xff000000 & pix[yi] ) | ( dv[rsum] << 16 ) | ( dv[gsum] << 8 ) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

//        //Log.e("pix", w + " " + h + " " + pix.length);
        result.setPixels(pix, 0, w, 0, 0, w, h);

        return (result);
    }

    private static class LoaderVariable extends BaseGlideUrlLoader<String> {
        private static final Pattern PATTERN = Pattern.compile("__w-((?:-?\\d+)+)__");

        public LoaderVariable(Context context, ModelCache<String, GlideUrl> urlCache) {
            super(context, urlCache);
        }

        /**
         * If the URL contains a special variable width indicator (eg "__w-200-400-800__")
         * we get the buckets from the URL (200, 400 and 800 in the example) and replace
         * the URL with the best bucket for the requested width (the bucket immediately
         * larger than the requested width).
         */
        @Override
        protected String getUrl(String model, int width, int height) {
            Matcher m = PATTERN.matcher(model);
            int bestBucket = 0;
            if (m.find()) {
                String[] found = m.group(1).split("-");
                for (String bucketStr : found) {
                    bestBucket = Integer.parseInt(bucketStr);
                    if (bestBucket >= width) {
                        // the best bucket is the first immediately bigger than the requested width
                        break;
                    }
                }
                if (bestBucket > 0) {
                    model = m.replaceFirst("w"+bestBucket);
                    LogUtil.d("width="+width+", URL successfully replaced by "+model);
                }
            }
            return model;
        }
    }

}

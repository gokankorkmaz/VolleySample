package kodluyoruz.com.volleysampleders.network;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;
//2.2 ( VolleyActivity Dön)

/**
 * Bilinmesi gereken bu class'ta bellekten ne kadar yer ayırması gerektigini soyluyoruz
 * Resimlerin tekrar kullanılmasını saglayan ne kadarlık bir alan ayıracagımızı bildiriyoruz
 */

class LruBitmapCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

    LruBitmapCache() {
        this(getDefaultLruCacheSize());
    }

    private LruBitmapCache(int sizeInKiloBytes) {
        super(sizeInKiloBytes);
    }

    //Örneğin 4 Gb'lık bir telefon oldugunu düşünelim.(4096MB)Bunu 8'e bölüp cihazda 512Mb'lık bir yer ayırdık
    private static int getDefaultLruCacheSize() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        return maxMemory / 8;
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight() / 1024;
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}
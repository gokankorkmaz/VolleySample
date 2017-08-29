package kodluyoruz.com.volleysampleders.network;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by GokhanKorkmaz on 26.08.2017.
 */

//2.1 (LruBitmapCache) gec

/**
 * application classından extend edilen classlar telefonu ilk actıgında calısan classlardır(TELEFONU İLK ACTIGINDA)
 * Uygulama ilk acıldıgında da aynı sekilde
 * Bu class'ı yazdıgımız icin manifest dosyasına Application tag altına   android:name=".network.AppController" ekle
 * <p>
 * <p>
 * EKLEME SİLME İMAGE LOADERI GERİ DÖNME VE KUYRUGU GERİ DONME METOTLARI VAR
 * ekleme silme ve image loaderi geri donme bunları dısarıdan yapıcaz
 * bu 2 class ı bir kez yazıcazzz
 * bu classlarla işimiz bitti geri DON
 */

public class AppController extends Application {

    private static final String TAG = AppController.class.getSimpleName();
    private static AppController mInstance; //static yaptık
    private RequestQueue mRequestQueue;

    /**
     * 2 farklı Request' cesidi var.
     * Asenkron Request:Gönderilen request'lerin birbirleriyle ilgileri yok.Birinden gelen cevap diğerini ilgilendirmiyor
     * Senkron Request:Birinden atılan request'in cevabı diğerinin parametresi olabilir.Birbiriyle bağlantılı çalışıyorlar.
     * Volley atılan requestleri bir kuyruga ekler.İstenildiği takdirde kuyruktan o istek iptal edilebilir.
     * Herhangi bir beklenme süresi tanımlanıp o süre geçildiğinde request iptal edilebilir.
     */


    private ImageLoader mImageLoader; //cashleme mekanizması

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    /**synchronized bu metoda aynı anda birden fazla işlemci giremeyecektir demek
     *vollate girmesine izin verir.
     */


    /**
     * Singleton yapısından daha önce bahsetmiştik.
     * Class'ın kendisinden instance yaratılır
     * Constructor 'ı private yapıyorduk
     * Instance 'ı dönen bir metot yazıyorduk
     * Böylece içindeki bütün public değişkenlere erişebiliyorduk
     * Aynı classtan nesne yaratmaya izin vermeyen bir yapı sağlıyordu
     * Böylece her kullanılan değişken için bellekte tek bir yer kaplayan obje üzerinden yapmıs oluyorduk.
     * A class'ında bir değişkenin değeri değiştiğinde B class'ından değerin değiştiğini görebiliyorduk
     */


    @Override
    public void onCreate() {

        super.onCreate();

        mInstance = this;
    }

    //volley'in kuyrugunu donuyor
    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    //image loader'i geri donduren metod
    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache());
        }
        return this.mImageLoader;
    }


    /**
     * Parametreleri atılan request,tag
     * TAG id'yi kullanmadıgın alanlarda tag tanımlayıp bu zamanlarda kullanabiliyorsun
     * Her request'e ayrı bir tag verilir bu sekilde.
     */

    public <T> void addToRequestQueue(Request<T> req, String tag) {

        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }


    //iptal etmek icin  kuyruktan silme .bu tage iptal requestler iptal ediliyor yada 1 tane
    public void cancelPendingRequests(Object tag) {

        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
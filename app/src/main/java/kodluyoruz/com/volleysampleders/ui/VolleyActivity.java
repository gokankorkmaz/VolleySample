package kodluyoruz.com.volleysampleders.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import kodluyoruz.com.volleysampleders.R;
import kodluyoruz.com.volleysampleders.network.AppController;

public class VolleyActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {

    //Manifest'ten internet izni ver.
    //1-Öncelikle apiURL linke ihtiyacımız var.
    private String apiURL = "https://api.myjson.com/bins/1guxr9";


    //back endci donecek tipi soylemesi gerek  donecek formatı backend cinin soylemesi lazım her 2 yapıyıda
    //kullamak gerekiyorsa string request yazman gerek

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);

        //2-İstek Yap jsonObjectRequest tanımla
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, apiURL, null, this, this);
        /**Atılan Method'un Tipi
         *Url adresi
         *null
         *Basarılı cevap onResponse( Alt +Enter) ile implement et
         *Basarısız cevap onErrorResponse (Alt + Enter ) ile implement et
         */


//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest();
//        StringRequest stringRequest = new StringRequest();
        //2.1-Gonderim İcin Kullanılacak Class Yazılır (AppController)


        //3-1 Request'i AppController class ile kuyruga ekliyoruz.
        //Parametreler atılan request ve bu request için belirlenen bir tag
        AppController.getInstance().addToRequestQueue(jsonObjectRequest, "Request 1"); //cevap donene kısıma git
    }

    //3-2 Basarılı Cevap (json donebiliyorsa o
    @Override
    public void onResponse(JSONObject response) {
        try {
            Toast.makeText(this, response.getJSONObject("glossary").getString("title"), Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //3.2 Basarısız Cevap error ekrana basılır
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "Büyük Başarısızlıklar Var" + error.getMessage(), Toast.LENGTH_SHORT).show();

    }


}

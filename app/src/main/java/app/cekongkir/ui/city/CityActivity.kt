package app.cekongkir.ui.city

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.cekongkir.R

class CityActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
package app.cekongkir.ui.tracking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.cekongkir.R

class TrackingActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracking)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
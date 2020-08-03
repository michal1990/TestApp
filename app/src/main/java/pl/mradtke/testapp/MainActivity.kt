package pl.mradtke.testapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * @author Michał Radtke
 * @version 28.07.2020
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
    }
}

package pl.mradtke.testapp.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import pl.mradtke.model.ui.UserItem
import pl.mradtke.testapp.R
import pl.mradtke.testapp.util.setImageUrl

/**
 * @author Michał Radtke
 * @version 29.07.2020
 */
class UserDetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER_ITEM = "extra_user_item"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)
        setSupportActionBar(findViewById(R.id.details_toolbar))
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        val userDetails = intent.getParcelableExtra<UserItem>(EXTRA_USER_ITEM)
        userDetails?.let { details ->
            findViewById<ImageView>(R.id.user_details_image).setImageUrl(details.avatarUrl)
            findViewById<TextView>(R.id.user_details_username).text = details.username
            findViewById<TextView>(R.id.user_details_url).apply {
                text = details.userUrl
                setOnClickListener { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(details.userUrl))) }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        finishAfterTransition()
    }
}

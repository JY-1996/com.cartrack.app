package com.cartrack.app.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.cartrack.app.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ItemDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        setSupportActionBar(findViewById(R.id.detail_toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            val intent = Intent(this@ItemDetailActivity, MapsActivity::class.java).apply {
                putExtra(ItemDetailFragment.ARG_LAT, intent.getStringExtra(ItemDetailFragment.ARG_LAT))
                putExtra(ItemDetailFragment.ARG_LNG, intent.getStringExtra(ItemDetailFragment.ARG_LNG))
                putExtra(ItemDetailFragment.ARG_CITY, intent.getStringExtra(ItemDetailFragment.ARG_CITY))
            }
            startActivity(intent)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            val fragment = ItemDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(
                        ItemDetailFragment.ARG_ITEM_ID,
                        intent.getIntExtra(ItemDetailFragment.ARG_ITEM_ID,0)
                    )
                }
            }

            supportFragmentManager.beginTransaction()
                .add(R.id.item_detail_container, fragment)
                .commit()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {

                navigateUpTo(Intent(this, ItemListActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}
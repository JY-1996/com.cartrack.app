package com.cartrack.app.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.cartrack.app.R
import com.cartrack.app.data.detail.DetailRepository
import com.cartrack.app.data.detail.UserContent.ITEM_MAP
import com.cartrack.app.data.detail.UserContent.User

class ItemListActivity : AppCompatActivity() {

    private var twoPane: Boolean = false
    private lateinit var myAdapter:SimpleItemRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = title
        val loading = findViewById<ProgressBar>(R.id.loading)

        if (findViewById<NestedScrollView>(R.id.item_detail_container) != null) {
            twoPane = true
        }
        myAdapter = SimpleItemRecyclerViewAdapter(this@ItemListActivity,twoPane)
        setupRecyclerView(findViewById(R.id.item_list))

        val repository = DetailRepository()
        val viewModelFactory = DetailViewModelFactory(repository)
        val viewModel = ViewModelProvider(this@ItemListActivity, viewModelFactory).get(DetailViewModel::class.java)
        viewModel.getDetail()
        loading.visibility = View.VISIBLE
        viewModel.detail.observe(this, Observer { response ->
            loading.visibility = View.INVISIBLE
            if(response.isSuccessful){
                response.body()?.let {
                    myAdapter.setData(it)
                    for(item in it){
                        ITEM_MAP[item.id] = item
                    }
                }
            }else {
                Toast.makeText(this, response.code(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = myAdapter
    }

    class SimpleItemRecyclerViewAdapter(
        private val parentActivity: ItemListActivity,
        private val twoPane: Boolean
    ) :
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val onClickListener: View.OnClickListener
        var values = emptyList<User>()
        init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag as User
                if (twoPane) {
                    val fragment = ItemDetailFragment()
                        .apply {
                        arguments = Bundle().apply {
                            putInt(ItemDetailFragment.ARG_ITEM_ID, item.id)
                        }
                    }
                    parentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit()
                } else {
                    val intent = Intent(v.context, ItemDetailActivity::class.java).apply {
                        putExtra(ItemDetailFragment.ARG_ITEM_ID, item.id)
                        putExtra(ItemDetailFragment.ARG_LAT, item.address.geo.lat)
                        putExtra(ItemDetailFragment.ARG_LNG, item.address.geo.lng)
                        putExtra(ItemDetailFragment.ARG_CITY, item.address.city)
                    }
                    v.context.startActivity(intent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
            holder.content.text = parentActivity.resources.getString(R.string.list, item.name, item.address.city)

            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
        }
        fun setData(user: List<User>){
            values = user
            notifyDataSetChanged()
        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val content: TextView = view.findViewById(R.id.content)
        }
    }
}
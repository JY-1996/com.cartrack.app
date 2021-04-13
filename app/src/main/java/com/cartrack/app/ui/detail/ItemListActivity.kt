package com.cartrack.app.ui.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class ItemListActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false
//    private lateinit var myAdapter:SimpleItemRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = title

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        if (findViewById<NestedScrollView>(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }
//        myAdapter = SimpleItemRecyclerViewAdapter(this@ItemListActivity,twoPane)
//        setupRecyclerView(findViewById(R.id.item_list))

        val repository = DetailRepository()
        val viewModelFactory = DetailViewModelFactory(repository)
        val viewModel = ViewModelProvider(this@ItemListActivity, viewModelFactory).get(DetailViewModel::class.java)
        viewModel.getDetail2()
        viewModel.detail2.observe(this, Observer { response ->
            if(response.isSuccessful){
                Log.e("test",response.body()!!.string())
                val list = response.body()!!
//                myAdapter.setData(list)
//                    for(item in list){
//                        ITEM_MAP[item.id] = item
//                    }
//                response.body()?.let {
//                    myAdapter.setData(it)
//                    for(item in it){
//                        ITEM_MAP[item.id] = item
//                    }
//                }
            }else {
                Toast.makeText(this, response.code(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
//        recyclerView.adapter = myAdapter
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
                            putString(ItemDetailFragment.ARG_ITEM_ID, item.id.toString())
                        }
                    }
                    parentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit()
                } else {
                    val intent = Intent(v.context, ItemDetailActivity::class.java).apply {
                        putExtra(ItemDetailFragment.ARG_ITEM_ID, item.id)
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
            holder.idView.text = item.id.toString()
            holder.contentView.text = item.email

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
            val idView: TextView = view.findViewById(R.id.id_text)
            val contentView: TextView = view.findViewById(R.id.content)
        }
    }
}
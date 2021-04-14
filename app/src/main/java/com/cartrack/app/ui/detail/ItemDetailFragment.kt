package com.cartrack.app.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.cartrack.app.R
import com.cartrack.app.data.detail.UserContent.ITEM_MAP
import com.cartrack.app.data.detail.UserContent.User
import com.google.android.material.appbar.CollapsingToolbarLayout

class ItemDetailFragment : Fragment() {

    private var item: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                item = ITEM_MAP[it.getInt(ARG_ITEM_ID)]
                activity?.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)?.title =
                    item?.name
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)

        item?.let {
            rootView.findViewById<TextView>(R.id.name).text = it.name
            rootView.findViewById<TextView>(R.id.username).text = it.username
            rootView.findViewById<TextView>(R.id.email).text = it.email
            rootView.findViewById<TextView>(R.id.street).text = it.address.street
            rootView.findViewById<TextView>(R.id.suite).text = it.address.suite
            rootView.findViewById<TextView>(R.id.city).text = it.address.city
            rootView.findViewById<TextView>(R.id.zipcode).text = it.address.zipcode
            rootView.findViewById<TextView>(R.id.geo).text = requireContext().resources.getString(R.string.list,it.address.geo.lat,it.address.geo.lng)
            rootView.findViewById<TextView>(R.id.phone).text = it.phone
            rootView.findViewById<TextView>(R.id.website).text = it.email
            rootView.findViewById<TextView>(R.id.companyname).text = it.name
            rootView.findViewById<TextView>(R.id.catchPhase).text = it.username
            rootView.findViewById<TextView>(R.id.bs).text = it.email
        }

        return rootView
    }

    companion object {

        const val ARG_ITEM_ID = "item_id"
        const val ARG_LAT = "lat"
        const val ARG_LNG = "lng"
        const val ARG_CITY = "city"

    }
}
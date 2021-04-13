package com.cartrack.app.dummy
//
//import android.util.Log
//import com.cartrack.app.retrofit.RetrofitClientInstance
//import okhttp3.ResponseBody
//import org.json.JSONObject
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import java.util.ArrayList
//import java.util.HashMap
//
///**
// * Helper class for providing sample content for user interfaces created by
// * Android template wizards.
// *
// * TODO: Replace all uses of this class before publishing your app.
// */
//object DummyContent {
//
//    /**
//     * An array of sample (dummy) items.
//     */
//    val ITEMS: MutableList<User> = ArrayList()
//
//    /**
//     * A map of sample (dummy) items, by ID.
//     */
//    val ITEM_MAP: MutableMap<Int, User> = HashMap()
//
//    private val COUNT = 25
//
//    init {
//        RetrofitClientInstance.route.getDetail().enqueue(object : Callback<List<User>> {
//            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
//                if (response.isSuccessful) {
//                    val list = response.body()
//                    for (item in list!!){
//                        Log.e("test",item.id.toString())
//                        ITEMS.add(item)
//                        ITEM_MAP[item.id] = item
//                    }
//
//                }
//            }
//            override fun onFailure(call: Call<List<User>>, t: Throwable) {
//                t.printStackTrace()
//            }
//        })
//        // Add some sample items.
////        for (i in 1..COUNT) {
////            addItem(createDummyItem(i))
////        }
//    }
//
////    private fun addItem(item: User) {
////        ITEMS.add(item)
////        ITEM_MAP.put(item.id, item)
////    }
//
////    private fun createDummyItem(position: Int): User {
////        return User(position, "Item " + position, makeDetails(position))
////    }
//
////    private fun makeDetails(position: Int): String {
////        val builder = StringBuilder()
////        builder.append("Details about Item: ").append(position)
////        for (i in 0..position - 1) {
////            builder.append("\nMore details information here.")
////        }
////        return builder.toString()
////    }
//
//    /**
//     * A dummy item representing a piece of content.
//     */
////    data class User(
////        val id: Int,
////        val name: String,
////        val username: String,
////        val phone: String,
////        val website: String,
////        val email: String,
////        val address: JSONObject,
////        val company: JSONObject
////    ) {
//////        override fun toString(): String = content
////    }
//}
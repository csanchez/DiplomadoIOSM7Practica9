package com.example.jsonxml

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class VolleyApi constructor(context: Context) {
    companion object{
        @Volatile
        private var INSTANCE:VolleyApi?=null

        fun getInstance(context: Context)= INSTANCE?: synchronized(this){
            INSTANCE?:VolleyApi(context).also { INSTANCE=it }

        }
    }

    val requestQueue: RequestQueue by lazy{
        Volley.newRequestQueue(context.applicationContext)
    }

    fun<T> add(req:Request<T>){
        requestQueue.add(req)
    }


}
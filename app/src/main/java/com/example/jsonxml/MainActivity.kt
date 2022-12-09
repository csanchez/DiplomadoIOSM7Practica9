package com.example.jsonxml

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.example.jsonxml.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONObject
import java.net.URLEncoder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var volleyApi: VolleyApi
    private lateinit var url:String
    private val ipPuerto="10.2.18.124:8080"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        volleyApi = VolleyApi(this)
        binding.search.setOnClickListener(){
            binding.outText.text=""
            url="https://www.google.es/search?q="+URLEncoder.encode(
                binding.searchText.text.toString(), "UTF-8"
            )
            buscar()
        }

        binding.restxml.setOnClickListener(){
            studentXML()
        }

        binding.restjson.setOnClickListener(){
            studentJSON()
        }

        binding.restjsonid.setOnClickListener(){
            sudentID()
        }
        binding.restjsonadd.setOnClickListener(){
            sudentADD()
        }
        binding.restjsondelete.setOnClickListener(){
            studentDelete()
        }
    }


    private fun buscar(){
        val stringRequest=object: StringRequest(
            Request.Method.GET,url,Response.Listener<String>{response->
                binding.outText.text=response
            },Response.ErrorListener { binding.outText.text="No se encuentra la informaci[on, revice si conexi[on" }){
            override fun getHeaders(): MutableMap<String, String> {
                val headers=HashMap<String,String>()
                headers["User-Agent"]="Mozilla/5.0 (Windows NT 6.1)"
                return headers
            }
        }
        volleyApi.add(stringRequest)
    }


    private fun studentXML(){
        val urlXML = "http://"+ipPuerto+"/estudiantesXML"
        val stringRequest=object: StringRequest(
            Request.Method.GET,urlXML,
            Response.Listener<String>{response->
                binding.outText.text=response
            },
            Response.ErrorListener { binding.outText.text="No se encuentra la información, revice si conexión" }){
            override fun getHeaders(): MutableMap<String, String> {
                val headers=HashMap<String,String>()
                headers["User-Agent"]="Mozilla/5.0 (Windows NT 6.1)"
                return headers
            }
        }
        volleyApi.add(stringRequest)
    }

    private fun studentJSON(){
        val urlJSON = "http://"+ipPuerto+"/estudiantesJSON"
        var cadena =""
        val jsonRequest=object: JsonArrayRequest(
            urlJSON,

            Response.Listener<JSONArray>{response ->
                (0 until response.length()).forEach{
                    val estudiante = response.getJSONObject(it)
                    val materias = estudiante.getJSONArray("materias")
                    cadena += estudiante.get("cuenta").toString()+"<"
                    (0 until materias.length()).forEach{
                        val datos = materias.getJSONObject(it)
                        cadena += datos.get("nombre").toString()+" ** "+datos.get("creditos".toString())+"---"
                    }
                    cadena+="> \n"
                }
                binding.outText.text = cadena
            },
            Response.ErrorListener {
                binding.outText.text="No se encuentra la información, revice si conexión"
            }){
            override fun getHeaders(): MutableMap<String, String> {
                val headers=HashMap<String,String>()
                headers["User-Agent"]="Mozilla/5.0 (Windows NT 6.1)"
                return headers
            }
        }
        volleyApi.add(jsonRequest)
    }

    private fun sudentID() {
        val urlJSON = "http://" + ipPuerto + "/id/" + binding.searchText.text.toString()
        var cadena = ""
        val jsonRequest = object : JsonObjectRequest(
            Method.GET,
            urlJSON,
            null,
            Response.Listener<JSONObject> { response ->
                binding.outText.text = response.get("cuenta")
                    .toString() + "----" + response.get("nombre").toString() + "\n"
            },
            Response.ErrorListener {
                binding.outText.text = "No se encuentra la información, revice la conexión"
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0 (Windows NT 6.1)"
                return headers
            }
        }
        volleyApi.add(jsonRequest)
    }

    private fun sudentADD(){
        val urlJSON = "http://"+ipPuerto+"/agregarestudiante"
        var cadena =""
        val jsonRequest=object: JsonArrayRequest(
            urlJSON,

            Response.Listener<JSONArray>{response ->
                (0 until response.length()).forEach{
                    val estudiante = response.getJSONObject(it)
                    val materias = estudiante.getJSONArray("materias")
                    cadena += estudiante.get("cuenta").toString()+"<"
                    (0 until materias.length()).forEach{
                        val datos = materias.getJSONObject(it)
                        cadena += datos.get("nombre").toString()+" ** "+datos.get("creditos".toString())+"---"
                    }
                    cadena+="> \n"
                }
                binding.outText.text = cadena
            },
            Response.ErrorListener {
                binding.outText.text="No se encuentra la información, revice si conexión"
            }){
            override fun getHeaders(): MutableMap<String, String> {
                val headers=HashMap<String,String>()
                headers["User-Agent"]="Mozilla/5.0 (Windows NT 6.1)"
                return headers
            }

            override fun getBody(): ByteArray {
                val estudiante = JSONObject()
                estudiante.put("cuenta","A000")
                estudiante.put("nombre","KATI")
                estudiante.put("edad","25")
                val materias = JSONArray()
                val item = JSONObject()
                item.put("id",1)
                item.put("nombre","Progra")
                item.put("creditos","50")
                materias.put(item)
                estudiante.put("materias",materias)
                return estudiante.toString().toByteArray(charset = Charsets.UTF_8)

            }

            override fun getMethod(): Int {
                return Method.POST
            }
        }
        volleyApi.add(jsonRequest)
    }

    private fun studentDelete(){
        val urlJSON = "http://"+ipPuerto+"/borrarestudiante/"+binding.searchText.text.toString()
        var cadena =""
        val jsonRequest=object: JsonArrayRequest(
            urlJSON,

            Response.Listener<JSONArray>{response ->
                (0 until response.length()).forEach{
                    val estudiante = response.getJSONObject(it)
                    val materias = estudiante.getJSONArray("materias")
                    cadena += estudiante.get("cuenta").toString()+"<"
                    (0 until materias.length()).forEach{
                        val datos = materias.getJSONObject(it)
                        cadena += datos.get("nombre").toString()+" ** "+datos.get("creditos".toString())+"---"
                    }
                    cadena+="> \n"
                }
                binding.outText.text = cadena
            },
            Response.ErrorListener {
                binding.outText.text="No se encuentra la información, revice si conexión"
            }){
            override fun getHeaders(): MutableMap<String, String> {
                val headers=HashMap<String,String>()
                headers["User-Agent"]="Mozilla/5.0 (Windows NT 6.1)"
                return headers
            }

            override fun getMethod(): Int {
                return Method.DELETE
            }
        }
        volleyApi.add(jsonRequest)
    }

}
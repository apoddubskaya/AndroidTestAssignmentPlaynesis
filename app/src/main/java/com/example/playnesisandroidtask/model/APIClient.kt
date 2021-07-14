package com.example.playnesisandroidtask.model

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET

@Xml(name = "ValCurs")
data class ValCurs(
        @Element val valutes: List<ValuteInfo>
)

@Xml(name = "Valute")
data class ValuteInfo(
    @PropertyElement(name = "CharCode") val charCode: String,
    @PropertyElement(name = "Nominal") val nominal: String,
    @PropertyElement(name = "Value") val value: String
        )

interface CbrService {
    @GET("scripts/XML_daily.asp")
    fun getData(): Call<ValCurs>
}

object APIClient {
    private var service: CbrService

    init {
        val tikxml = TikXml.Builder().exceptionOnUnreadXml(false).build()
        val retrofit = Retrofit.Builder()
                .baseUrl("https://www.cbr.ru/")
                .addConverterFactory(TikXmlConverterFactory.create(tikxml))
                .build()
        service = retrofit.create(CbrService::class.java)
    }

    fun addHandlerForGetData(handler: (data: List<ValuteInfo>?) -> Unit) {
        val call = service.getData()
        call.enqueue(object: Callback<ValCurs>
            {
                override fun onFailure(call: Call<ValCurs>, t: Throwable) {
                    handler(null)
                }

                override fun onResponse(call: Call<ValCurs>, response: Response<ValCurs>) {
                    handler(response.body()?.valutes)
                }
            }
        )
    }
}
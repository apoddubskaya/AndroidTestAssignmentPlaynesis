package com.example.playnesisandroidtask.model

import java.util.*

object Model {

    data class CostInfo(val nominal: Int, val value: Double)

    class CurrencyInfo(charCode: String) {
        internal val currency = Currency.getInstance(charCode)
        override fun toString(): String {
            return "${currency.getDisplayName(Locale("ru"))} - ${currency.currencyCode}"
        }
    }

    private val dataHashMap = hashMapOf(
            "RUB" to CostInfo(1, 1.0)
    )
    lateinit var currencyList: List<CurrencyInfo>
        private set

    fun setData(listData: List<ValuteInfo>) {
        for (el in listData) {
            dataHashMap[el.charCode] = CostInfo(
                nominal = el.nominal.toInt(),
                value = el.value.replace(',', '.').toDouble()
            )
        }
        currencyList = dataHashMap.keys.map{CurrencyInfo(it)}
    }
    
    fun convert(value: Double, from: String, to: String): Double? {
        return if (dataHashMap[from] == null || dataHashMap[to] == null)
            null
        else {
            val inRubles = (value / dataHashMap[from]!!.nominal) * dataHashMap[from]!!.value
            (inRubles / dataHashMap[to]!!.value) * dataHashMap[to]!!.nominal
        }
    }

    fun getIndexOf(charCode: String): Int{
        for (i in currencyList.indices)
            if (charCode == currencyList[i].currency.currencyCode)
                return i
        return -1
    }
    fun getCharCodeByIndex(index: Int): String = currencyList[index].currency.currencyCode
}
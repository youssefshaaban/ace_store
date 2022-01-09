package com.example.mvvm_template.data.remote_service.response.order

import com.example.mvvm_template.domain.entity.RateOrder

data class LastRatedResponse(val orderId:Int,val products:List<OrderDetail>)

fun LastRatedResponse.toRateOrder():RateOrder= RateOrder(this.orderId,this.products.map { t->t.toOrderDetailEntity() })
package kr.co.shared.clocktest.data

import java.io.Serializable

data class RoutineData (
    val current_time: String,
    val current_date: String,

    val work_time: Int,
    val work_time_type: Int,
    val rest_time: Int,
    val rest_time_type: Int,
    val set_count: Int,
) : Serializable
package com.example.android_lab2_ui_mvc.model

import androidx.annotation.StringRes

/**
 * 模型层返回给视图层的查询结果。
 *
 * 之所以不直接返回 String，是为了同时满足两点：
 * 1. 实验要求「禁止在代码中直接出现字符串」——文本一律放在字符串资源里，
 *    模型层只持有资源 id，因此不需要 Context 也能表达查询结果；
 * 2. 「未收录该语言：xxx」这类带参数的提示，把参数单独带出来，
 *    由视图层用 getString(textRes, formatArg) 完成格式化。
 *
 * @param textRes   建议文本对应的字符串资源 id
 * @param formatArg 格式化参数，为 null 表示该文案不需要占位符
 */
data class Advice(
    @get:StringRes val textRes: Int,
    val formatArg: String? = null
)

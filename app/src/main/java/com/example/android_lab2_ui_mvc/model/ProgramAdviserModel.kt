package com.example.android_lab2_ui_mvc.model

import com.example.android_lab2_ui_mvc.R

/**
 * MVC 模式中的 Model（模型层）
 * 负责数据与业务逻辑：保存语言信息，并根据查询返回结果。
 * 不持有任何 UI / Context 引用，与视图完全解耦。
 *
 * 实验要求禁止在代码中直接出现字符串，所以这里保存的是建议文案的
 * 字符串资源 id，而不是文案本身；真正的文本由视图层取出显示。
 */
class ProgramAdviserModel {

    // 语言 -> 建议信息的字符串资源 id
    private val adviceResMap: Map<String, Int> = mapOf(
        "kotlin" to R.string.advice_kotlin,
        "java" to R.string.advice_java,
        "python" to R.string.advice_python,
        "javascript" to R.string.advice_javascript,
        "swift" to R.string.advice_swift
    )

    /** 根据语言名称查询建议，模型层返回结果 */
    fun getAdvice(language: String): Advice {
        val adviceRes = adviceResMap[language.lowercase()]
        return if (adviceRes != null) {
            Advice(adviceRes)
        } else {
            // 未收录：文案在资源里，语言名作为格式化参数回传
            Advice(R.string.advice_not_found, language)
        }
    }
}

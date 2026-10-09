package com.example.android_lab2_ui_mvc.model

/**
 * MVC 模式中的 Model（模型层）
 * 负责数据与业务逻辑：保存语言信息，并根据查询返回结果。
 * 不持有任何 UI 引用，与视图完全解耦。
 */
class ProgramAdviserModel {

    // 语言 -> 建议信息
    private val adviceMap = mapOf(
        "kotlin" to "kotlin: Google 官方推荐，简洁安全，适合现代 Android 开发",
        "java" to "java: 生态成熟稳定，资料丰富，适合入门 Android",
        "python" to "python: 语法简洁，擅长数据分析与人工智能",
        "javascript" to "javascript: 浏览器端王者，也可做跨端开发",
        "swift" to "swift: 苹果官方语言，用于 iOS / macOS 开发"
    )

    /** 根据语言名称查询建议，模型层返回结果 */
    fun getAdvice(language: String): String {
        return adviceMap[language.lowercase()]
            ?: "未收录该语言：$language"
    }
}

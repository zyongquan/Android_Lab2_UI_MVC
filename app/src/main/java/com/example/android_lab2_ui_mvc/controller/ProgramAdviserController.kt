package com.example.android_lab2_ui_mvc.controller

import com.example.android_lab2_ui_mvc.model.Advice
import com.example.android_lab2_ui_mvc.model.ProgramAdviserModel

/**
 * MVC 模式中的 Controller（控制器层）
 * 接收 View 传来的用户操作，调用 Model 处理业务逻辑，
 * 再把结果交回 View 显示。自身不关心 UI 具体如何绘制。
 */
class ProgramAdviserController(private val model: ProgramAdviserModel = ProgramAdviserModel()) {

    /** 查询某门语言的建议，返回结果由视图层负责渲染 */
    fun onQueryLanguage(language: String): Advice {
        return model.getAdvice(language)
    }
}

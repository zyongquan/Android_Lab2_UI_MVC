package com.example.android_lab2_ui_mvc

import android.os.Bundle
import android.view.Gravity
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android_lab2_ui_mvc.controller.ProgramAdviserController

/**
 * MVC 模式中的 View（视图层）
 * 只负责界面展示与把用户操作转交给 Controller，业务逻辑全部在
 * Model / Controller 中完成。
 */
class MainActivity : AppCompatActivity() {

    private val controller = ProgramAdviserController()

    // 区1：动态添加 TextView 的计数
    private var textViewCount = 0

    // 区2：上一次查询过的语言，用于屏幕重建后恢复查询结果
    private var lastQueriedLanguage: String? = null

    private lateinit var container: LinearLayout
    private lateinit var spinnerLanguage: Spinner
    private lateinit var tvAdvice: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        container = findViewById(R.id.container_textviews)
        spinnerLanguage = findViewById(R.id.spinner_language)
        tvAdvice = findViewById(R.id.tv_advice)

        initSpinner()
        initButtons()

        // 屏幕旋转等重建场景：恢复区1已添加的 TextView 与区2上次的查询结果
        restoreState(savedInstanceState)
    }

    /** 初始化区2的下拉框（数据来自字符串资源） */
    private fun initSpinner() {
        val languages = resources.getStringArray(R.array.languages)
        spinnerLanguage.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            languages
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        spinnerLanguage.setSelection(0, false)
    }

    private fun initButtons() {
        // 区1：点击按钮，用代码动态生成 TextView，ScrollView 可滚动查看
        findViewById<Button>(R.id.btn_add_textview).setOnClickListener {
            addTextViewDynamically()
        }

        // 区2：按钮事件响应，查询模型层并返回结果（MVC）
        findViewById<Button>(R.id.btn_confirm).setOnClickListener {
            showAdvice()
        }
    }

    /** 用代码动态创建 TextView 并加入 ScrollView 内的 LinearLayout */
    private fun addTextViewDynamically() {
        textViewCount++
        container.addView(createNumberedTextView(textViewCount))
        Toast.makeText(
            this,
            getString(R.string.new_textview_format, textViewCount),
            Toast.LENGTH_SHORT
        ).show()
    }

    /**
     * 创建一个带序号的 TextView（纯代码生成，不依赖布局文件）。
     * 外观参数与之前保持一致，避免影响已有运行截图的效果。
     */
    private fun createNumberedTextView(index: Int): TextView = TextView(this).apply {
        text = getString(R.string.new_textview_format, index)
        textSize = 22f
        setTextColor(getColor(R.color.black))
        gravity = Gravity.CENTER_VERTICAL
        setPadding(16, 24, 16, 24)
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }

    /**
     * 重建后恢复界面状态：
     * 区1按保存的计数把动态生成的 TextView 逐个补回来；
     * 区2用上次查询的语言重新走一遍 MVC，把结果显示回去。
     */
    private fun restoreState(savedInstanceState: Bundle?) {
        textViewCount = savedInstanceState?.getInt(KEY_TEXT_VIEW_COUNT) ?: 0
        for (index in 1..textViewCount) {
            container.addView(createNumberedTextView(index))
        }

        val restoredLanguage = savedInstanceState?.getString(KEY_LAST_LANGUAGE)
        lastQueriedLanguage = restoredLanguage
        if (!restoredLanguage.isNullOrEmpty()) {
            selectLanguage(restoredLanguage)
            renderAdvice(restoredLanguage)
        } else {
            spinnerLanguage.setSelection(0, false)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_TEXT_VIEW_COUNT, textViewCount)
        val currentLanguage = spinnerLanguage.selectedItem as? String
        outState.putString(KEY_LAST_LANGUAGE, currentLanguage ?: lastQueriedLanguage)
    }

    /** 区2：把用户选择交给 Controller（Controller 再问 Model），拿到结果后显示 */
    private fun showAdvice() {
        val language = spinnerLanguage.selectedItem as? String ?: return
        lastQueriedLanguage = language
        renderAdvice(language)
    }

    private fun selectLanguage(language: String) {
        val adapter = spinnerLanguage.adapter as? ArrayAdapter<*> ?: return
        val index = (0 until adapter.count).indexOfFirst { position ->
            adapter.getItem(position)?.toString() == language
        }
        if (index >= 0) {
            spinnerLanguage.setSelection(index, false)
        }
    }

    /** 按 Controller 返回的资源 id（及可选格式化参数）渲染建议文本 */
    private fun renderAdvice(language: String) {
        val advice = controller.onQueryLanguage(language)
        tvAdvice.text = advice.formatArg
            ?.let { getString(advice.textRes, it) }
            ?: getString(advice.textRes)
    }

    private companion object {
        /** 保存「已动态添加多少个 TextView」的 Bundle key */
        const val KEY_TEXT_VIEW_COUNT = "key_text_view_count"

        /** 保存「上次查询的语言」的 Bundle key */
        const val KEY_LAST_LANGUAGE = "key_last_language"
    }
}

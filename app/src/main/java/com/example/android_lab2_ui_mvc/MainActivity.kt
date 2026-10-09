package com.example.android_lab2_ui_mvc

import android.os.Bundle
import android.view.Gravity
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.android_lab2_ui_mvc.controller.ProgramAdviserController

class MainActivity : ComponentActivity() {

    private val controller = ProgramAdviserController()

    // 区1：动态添加 TextView 的计数
    private var textViewCount = 0

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
    }

    private fun initButtons() {
        // 区1：点击按钮，用代码动态生成 TextView，ScrollView 可滚动查看
        findViewById<Button>(R.id.btn_add_textview).setOnClickListener {
            addTextViewDynamically()
        }

        // 区2：按钮事件响应，查询模型层并返回结果（MVC）
        findViewById<Button>(R.id.btn_confirm).setOnClickListener {
            val language = spinnerLanguage.selectedItem as String
            tvAdvice.text = controller.onQueryLanguage(language)
        }
    }

    /** 用代码动态创建 TextView 并加入 ScrollView 内的 LinearLayout */
    private fun addTextViewDynamically() {
        textViewCount++
        val tv = TextView(this).apply {
            text = getString(R.string.new_textview_format, textViewCount)
            textSize = 22f
            setTextColor(getColor(R.color.black))
            gravity = Gravity.CENTER_VERTICAL
            setPadding(16, 24, 16, 24)
        }
        container.addView(
            tv,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        )
        Toast.makeText(this, getString(R.string.new_textview_format, textViewCount), Toast.LENGTH_SHORT).show()
    }
}

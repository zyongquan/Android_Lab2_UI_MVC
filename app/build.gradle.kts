// 实验二「UI控件代码生成以及MVC」应用模块
// 纯 View（XML 布局 + 代码动态生成控件）实现的 MVC 工程，
// 只依赖 appcompat 与 core-ktx，不包含 Compose。
// Kotlin 编译由 AGP 9 的内置 Kotlin 支持完成，因此这里不需要单独声明 kotlin 插件。
plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.android_lab2_ui_mvc"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.android_lab2_ui_mvc"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

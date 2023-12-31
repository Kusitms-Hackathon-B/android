plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.jjikmuk"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.jjikmuk"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    // Jetpack Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.6.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.6.0")

    // Retrofit, OkHttp
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.12.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation ("com.squareup.okhttp3:okhttp:4.9.3")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.2")

    // MpChart
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    // RecyclerView
    implementation ("androidx.recyclerview:recyclerview:1.3.1")

    // BottomAppBar
    implementation("com.google.android.material:material:1.3.0-alpha03")

    //lottie
    implementation("com.airbnb.android:lottie:5.0.2")

    // CircleImageView
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // 주간 캘린더?
    implementation("com.github.kizitonwose:CalendarView:1.0.4")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.12.0")


    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.android.identity:identity-credential-android:20231002")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //view page
    implementation ("androidx.viewpager2:viewpager2:1.0.0")
    // Kakao-SDK
    implementation ("com.kakao.sdk:v2-user:2.13.0") // 카카오 로그인
    implementation ("me.relex:circleindicator:2.1.4")



}
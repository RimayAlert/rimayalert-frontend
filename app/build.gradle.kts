import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    id("kotlin-kapt")
}

/**
 * Helper para cargar archivos .properties
 */
fun loadProperties(fileName: String): Properties {
    val props = Properties()
    val file = rootProject.file(fileName)
    if (file.exists()) {
        file.inputStream().use { props.load(it) }
    }
    return props
}

//val keystoreProperties = loadProperties("key.properties")
val envProperties = loadProperties("env.properties")

android {
    namespace = "com.fazq.rimayalert"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.fazq.rimayalert"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas"
            }
        }
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

    flavorDimensions += "flavor-type"

    /**
     * Helper para configurar flavors
     */
    fun com.android.build.api.dsl.ProductFlavor.configureFlavor(
        env: Properties,
        baseKey: String,
        socketKey: String,
        authority: String,
        appName: String
    ) {
        val baseUrl = env.getProperty(baseKey)
            ?: throw GradleException("$baseKey not set in env.properties")

        val baseUrlSocket = env.getProperty(socketKey)
            ?: throw GradleException("$socketKey not set in env.properties")

        manifestPlaceholders.putAll(
            mapOf(
                "fileProviderAuthority" to authority,
                "baseUrl" to "\"$baseUrl\"",
                "baseUrlSocket" to "\"$baseUrlSocket\""
            )
        )
        buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
        buildConfigField("String", "BASE_URL_SOCKET", "\"$baseUrlSocket\"")
        resValue("string", "app_name", appName)
    }

    productFlavors {
        create("dev") {
            dimension = "flavor-type"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"

            configureFlavor(
                envProperties,
                "BASE_URL_DEV",
                "BASE_URL_SOCKET_DEV",
                "com.hey.inplanet.biolunch.dev.fileprovider",
                "Hey Biometric Dev"
            )
        }
        create("prod") {
            dimension = "flavor-type"

            configureFlavor(
                envProperties,
                "BASE_URL_PROD",
                "BASE_URL_SOCKET_PROD",
                "com.fazq.rimayalert.fileprovider",
                "Rimay Alert"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.appcompat)

//    Compose Icons
    implementation(libs.androidx.compose.material.icons.extended)

    // Room
    implementation(libs.room.ktx)
    implementation(libs.androidx.ui)
    kapt(libs.room.compiler)

    // Utils
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    // DI
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

//    splashscreen
    implementation(libs.androidx.core.splashscreen)

    // Networking
    implementation(libs.retrofit)
    implementation(libs.retrofit.scalars)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp.logging)

    // Utils
    implementation(libs.joda.time)

    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}

kapt {
    correctErrorTypes = true
}


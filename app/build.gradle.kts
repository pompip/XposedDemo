plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
}
//plugins{
//    id("java")
//}

android {
    compileSdkVersion (27)
    defaultConfig {
        applicationId =  "com.example.bonree.xposeddemo"
        minSdkVersion (21)
        targetSdkVersion (27)
        versionCode =  1
        versionName = "1.0"
        testInstrumentationRunner =  "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName( "release"){
            isMinifyEnabled =  false
            proguardFiles( getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    buildOutputs {  }

}

dependencies {

    implementation( "com.android.support:appcompat-v7:27.1.1")
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    testImplementation("junit:junit:4.12")
    testImplementation ("org.jetbrains.kotlin:kotlin-test-junit:1.3.21")
    androidTestImplementation("com.android.support.test:runner:1.0.2")
    androidTestImplementation("com.android.support.test.espresso:espresso-core:3.0.2")


    compileOnly("de.robv.android.xposed:api:82")
    compileOnly("de.robv.android.xposed:api:82:sources")
    implementation(kotlin("stdlib", "1.3.21"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.21")
//    implementation ("com.google.android.exoplayer:exoplayer:2.9.0")

}

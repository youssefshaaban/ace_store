
// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
  val kotlin_version by extra("1.4.32")
    //ext.kotlin_version = "1.4.32"
  repositories {
    google()
    jcenter()
  }

  dependencies {

//    classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"

    classpath(Dependencies.AndroidGradlePlugin)
    classpath(Dependencies.KotlinPlugin)
      classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
      // classpath(Dependencies.HiltClathPath)
      // NOTE: Do not place your application dependencies here; they belong
    // in the individual module build.gradle.kts.kts files
  }
}

allprojects {
  repositories {
    google()
    jcenter()
    maven { url = uri("https://jitpack.io") }
  }
}


//task clean(type: Delete) {
//  delete rootProject.buildDir
//}

tasks.register("clean",Delete::class){
  delete(rootProject.buildDir)
}
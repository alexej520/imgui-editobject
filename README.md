# Usage
1) Customize the style
```kotlin
val myStyle = PropertyStyle(
        nameColor = Vec4(0.949f, 0.555f, 0.492f, 1f),
        separatorColor = Vec4(0.730f, 0.730f, 0.730f, 1f),
        showType = true,
        typeColor = Vec4(0.156f, 0.480f, 0.867f, 1f),
        showInstanceId = true,
        instanceIdColor = Vec4(0.5f, 0.5f, 0.5f, 1f),
        showToString = true,
        toStringColor = Vec4(1f, 1f, 1f, 1f)
)
```
2) Draw all object properties with [ImGui](https://github.com/kotlin-graphics/imgui)
```kotlin
class MyClass {
    var prop1 = 0
    var prop2 = 0.3
}

val prop = listOf(
        1,
        1.0,
        2.3f,
        arrayOf(0, 4),
        "sample",
        MyClass()
)

fun draw() {
    with(ImGui) {
        allProperties(
                id = createId("objectName"),
                property = ::prop,
                style = myStyle
        )
    }
}
```
# Gradle
```groovy
repositories {
    mavenCentral()
    maven { url "https://oss.sonatype.org/content/repositories/snapshots/" }
    maven { url "https://oss.sonatype.org/content/repositories/releases/" }
    maven { url 'https://jitpack.io' }
}

dependencies {
    compile 'com.github.alexej520.imgui-editobject:kotlin-common:0.1'
}
```

package imgui.editobject.kotlin

import glm_.vec2.Vec2
import glm_.vec4.Vec4
import imgui.Col
import imgui.IO
import imgui.ImGui
import imgui.NUL
import imgui.editobject.kotlin.propertyOwner.charArray
import imgui.editobject.kotlin.propertyOwner.float
import imgui.editobject.kotlin.propertyOwner.int
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty0
import kotlin.reflect.KProperty1
import kotlin.reflect.jvm.isAccessible


fun ImGui.propertyJavaPrimitiveType(
        id: String,
        property: KProperty0<Any?>,
        obj: Any? = property.get(),
        name: String = property.name,
        type: String = property.returnType.simpleName,
        style: PropertyStyle
) {
    val mProperty = property as? KMutableProperty0<*>
    if (mProperty != null) {
        propertyLine(id, name = name, type = type, style = style)
        sameLine()
        when (obj) {
            is Float -> inputFloat(id, mProperty as KMutableProperty0<Float>)
            is Double -> {
                float = obj.toFloat()
                inputFloat(id, ::float)
                (mProperty as KMutableProperty0<Double>).set(float.toDouble())
            }
            is Int -> inputInt(id, mProperty as KMutableProperty0<Int>)
            is Long -> {
                int = obj.toInt()
                inputInt(id, ::int)
                (mProperty as KMutableProperty0<Long>).set(int.toLong())
            }
            is Short -> {
                int = obj.toInt()
                inputInt(id, ::int)
                (mProperty as KMutableProperty0<Short>).set(int.toShort())
            }
            is Byte -> {
                int = obj.toInt()
                inputInt(id, ::int)
                (mProperty as KMutableProperty0<Byte>).set(int.toByte())
            }
            is Char -> {
                charArray.string = obj.toString()
                inputText(id, charArray)
                (mProperty as KMutableProperty0<Char>).set(charArray[0])
            }
            is Boolean -> checkbox(id, mProperty as KMutableProperty0<Boolean>)
            else -> throw IllegalArgumentException("obj is not JavaPrimitive: ${if (obj == null) null else obj::class}")
        }
    } else {
        propertySimple(id, name, type, obj.toString(), style = style)
    }
}

fun ImGui.propertyString(id: String, property: KProperty0<Any?>, obj: String, name: String = property.name, type: String = property.returnType.simpleName, style: PropertyStyle) {
    val mutableProperty = property as? KMutableProperty0<*>
    if (mutableProperty != null) {
        propertyLine(id, name, type, style = style)
        charArray.string = obj
        sameLine()
        inputText(id, charArray)
        val newString = charArray.string
        if (newString != obj) {
            (mutableProperty as KMutableProperty0<String?>).set(newString)
        }
    } else {
        propertyLine(id = id, name = property.name, type = property.returnType.simpleName, string = obj, style = style)
    }
}

fun ImGui.propertySimple(
        id: String,
        name: String,
        type: String?,
        instance: String?,
        style: PropertyStyle
) {
    propertyLine(id, name, type, instance, style = style)
}

fun ImGui.propertyJavaArray(
        id: String,
        property: KProperty0<Any?>,
        obj: Any? = property.get(),
        name: String = property.name,
        type: String? = property.returnType.simpleName,
        style: PropertyStyle
) {
    val mProperty = property as? KMutableProperty0<Any?>
    if (obj == null) {
        propertyNull(name, type, style)
        return
    }
    if (propertyNode(id = id, name = name, type = type, instance = instanceId(obj), string = obj.toString(), style = style)) {
        when (obj) {
            is Array<*> -> {
                text("size: Int = ${obj.size}")
                val itemType = property.returnType.arguments.first().simpleName
                withProperty0<Any?> { prop ->
                    obj.forEachIndexed { index, o ->
                        prop.set(o)
                        allProperties(id.addPart(index), prop, o, "$index", itemType, style)
                        (obj as Array<Any?>)[index] = prop.get()
                    }
                }
            }
            is FloatArray -> {
                text("size: Int = ${obj.size}")
                val prop = propertyOwner::float
                obj.forEachIndexed { index, o ->
                    prop.set(o)
                    propertyJavaPrimitiveType(id.addPart(index), prop, o, "$index", "Float", style)
                    obj[index] = prop.get()
                }
            }
            is DoubleArray -> {
                text("size: Int = ${obj.size}")
                val prop = propertyOwner::double
                obj.forEachIndexed { index, o ->
                    prop.set(o)
                    propertyJavaPrimitiveType(id.addPart(index), prop, o, "$index", "Double", style)
                    obj[index] = prop.get()
                }
            }
            is IntArray -> {
                text("size: Int = ${obj.size}")
                val prop = propertyOwner::int
                obj.forEachIndexed { index, o ->
                    prop.set(o)
                    propertyJavaPrimitiveType(id.addPart(index), prop, o, "$index", "Int", style)
                    obj[index] = prop.get()
                }
            }
            is LongArray -> {
                text("size: Int = ${obj.size}")
                val prop = propertyOwner::long
                obj.forEachIndexed { index, o ->
                    prop.set(o)
                    propertyJavaPrimitiveType(id.addPart(index), prop, o, "$index", "Long", style)
                    obj[index] = prop.get()
                }
            }
            is ShortArray -> {
                text("size: Int = ${obj.size}")
                val prop = propertyOwner::short
                obj.forEachIndexed { index, o ->
                    prop.set(o)
                    propertyJavaPrimitiveType(id.addPart(index), prop, o, "$index", "Short", style)
                    obj[index] = prop.get()
                }
            }
            is ByteArray -> {
                text("size: Int = ${obj.size}")
                val prop = propertyOwner::byte
                obj.forEachIndexed { index, o ->
                    prop.set(o)
                    propertyJavaPrimitiveType(id.addPart(index), prop, o, "$index", "Byte", style)
                    obj[index] = prop.get()
                }
            }
            is CharArray -> {
                text("size: Int = ${obj.size}")
                val prop = propertyOwner::char
                obj.forEachIndexed { index, o ->
                    prop.set(o)
                    propertyJavaPrimitiveType(id.addPart(index), prop, o, "$index", "Char", style)
                    obj[index] = prop.get()
                }
            }
            is BooleanArray -> {
                text("size: Int = ${obj.size}")
                val prop = propertyOwner::boolean
                obj.forEachIndexed { index, o ->
                    prop.set(o)
                    propertyJavaPrimitiveType(id.addPart(index), prop, o, "$index", "Boolean", style)
                    obj[index] = prop.get()
                }
            }
            else -> throw IllegalArgumentException()
        }
        treePop()
    }
}

@Suppress("UNCHECKED_CAST")
inline fun <T> withProperty0(block: (KMutableProperty0<T>) -> Unit) {
    ++propertyOwner.anyIndex
    block(propertyOwner::any as KMutableProperty0<T>)
    propertyOwner.anies[propertyOwner.anyIndex--] = null
}

object propertyOwner {
    var anyIndex: Int = -1
    val anies = arrayOfNulls<Any?>(128)
    var any: Any?
        get() = anies[anyIndex]
        set(value) {
            anies[anyIndex] = value
        }

    val booleanArray = BooleanArray(256)
    val charArray = CharArray(256)
    var float: Float = 0f
    var double: Double = 0.0
    var int: Int = 0
    var long: Long = 0
    var short: Short = 0
    var byte: Byte = 0
    var char: Char = NUL
    var boolean: Boolean = false
}

inline val CharArray.strLen: Int
    get() {
        forEachIndexed { index, c ->
            if (c == NUL) {
                return index
            }
        }
        return size
    }

// jvm runtime error when inline
var CharArray.string: String
    set(value) {
        value.toCharArray(this)
        for (i in value.length..lastIndex) {
            set(i, NUL)
        }
    }
    get() = String(this, 0, strLen)

data class PropertyStyle(
        val nameColor: Vec4,
        val separatorColor: Vec4,
        val showType: Boolean,
        val typeColor: Vec4,
        val showInstanceId: Boolean,
        val instanceIdColor: Vec4,
        val showToString: Boolean,
        val toStringColor: Vec4
) {
    companion object {
        val DEFAULT = PropertyStyle(
                nameColor = Vec4(0.949f, 0.555f, 0.492f, 1f),
                separatorColor = Vec4(0.730f, 0.730f, 0.730f, 1f),
                showType = true,
                typeColor = Vec4(0.156f, 0.480f, 0.867f, 1f),
                showInstanceId = true,
                instanceIdColor = Vec4(0.5f, 0.5f, 0.5f, 1f),
                showToString = true,
                toStringColor = Vec4(1f, 1f, 1f, 1f)
        )
    }
}

fun ImGui.propertyNode(
        id: String, name: String, type: String? = null, instance: String? = null, string: String? = null, style: PropertyStyle
): Boolean = with(style) {
    pushStyleColor(Col.Text, nameColor)
    val node = treeNode(strId = id, fmt = name)
    popStyleColor()
    propertyInternal(id, name, type, instance, string, style)
    node
}

fun ImGui.propertyLine(
        id: String, name: String, type: String? = null, instance: String? = null, string: String? = null, style: PropertyStyle
): Unit = with(style) {
    textColored(nameColor, name)
    propertyInternal(id, name, type, instance, string, style)
}

private fun ImGui.propertyInternal(
        id: String, name: String, type: String? = null, instance: String? = null, string: String? = null, style: PropertyStyle
) = with(style) {
    if (showType && type != null) {
        sameLine(spacingW = 0f)
        textColored(separatorColor, ": ")
        sameLine(spacingW = 0f)
        textColored(typeColor, type)
    }
    if (showInstanceId && instance != null) {
        sameLine(spacingW = 0f)
        textColored(separatorColor, " = ")
        sameLine(spacingW = 0f)
        textColored(instanceIdColor, instance)
    }
    if (showToString && string != null) {
        sameLine()
        val winId = getId(id.addPart("window$$$"))
        val storage = currentWindow.stateStorage
        val buttonPressed = button("+" + id)
        if (buttonPressed) {
            storage[winId] = true
        }
        propertyOwner.booleanArray[0] = storage.bool(winId)
        if (propertyOwner.booleanArray[0]) {
            if (begin(instance ?: string, propertyOwner.booleanArray)) {
                if (buttonPressed) {
                    setWindowSize(Vec2(400f, 100f))
                    val windowPos = Vec2(IO.mousePos.x - 400f, IO.mousePos.y - 100f)
                    setWindowPos(windowPos)
                }
                pushStyleColor(Col.Text, toStringColor)
                textWrapped(string)
                popStyleColor()
                end()
            }
            storage[winId] = propertyOwner.booleanArray[0]
        }
        sameLine()
        textColored(toStringColor, string.replace('\n', ' '))
    }
}

@Suppress("NOTHING_TO_INLINE")
inline fun instanceId(obj: Any) = "{${obj::class.java.name}@${Integer.toHexString(System.identityHashCode(obj))}}"

@Suppress("NOTHING_TO_INLINE")
inline fun createId(str: String) = "##" + str

@Suppress("NOTHING_TO_INLINE")
inline fun String.addPart(idPart: String) = "$this.$idPart"

@Suppress("NOTHING_TO_INLINE")
inline fun String.addPart(idPart: Int) = "$this.$idPart"

fun ImGui.propertyNull(name: String, type: String? = null, style: PropertyStyle) {
    propertyLine(id = "", name = name, type = type, string = "null", style = style)
}

fun ImGui.allProperties(
        id: String,
        property: KProperty0<Any?>,
        obj: Any? = property.get(),
        name: String = property.name,
        type: String = property.returnType.simpleName,
        style: PropertyStyle
) {
    val javaClass = obj?.javaClass
    val kotlinClass = javaClass?.kotlin
    val javaPrimitiveType = kotlinClass?.javaPrimitiveType
    when {
        obj == null -> {
            propertyNull(name, type, style)
        }
        obj is String -> {
            propertyString(id, property, obj, name, type, style)
        }
        javaPrimitiveType != null -> {
            propertyJavaPrimitiveType(id, property, obj, name, type, style)
        }
        javaClass!!.isArray -> {
            propertyJavaArray(id, property, obj, name, type, style)
        }
        else -> {
            val allProperties = obj::class.allDeclaredProperties
            if (allProperties.isEmpty()) {
                propertySimple(id, name, type, instanceId(obj), style = style)
            } else {
                if (propertyNode(id = id, name = name, type = type, instance = instanceId(obj), string = obj.toString(), style = style)) {
                    allProperties.forEach { prop ->
                        prop.isAccessible = true
                        val propName = prop.name
                        @Suppress("UNCHECKED_CAST")
                        allProperties(id = id.addPart(propName), name = propName, property = (prop as KProperty1<Any, Any?>).getProperty0(obj), style = style)
                    }
                    treePop()
                }
            }
        }
    }
}

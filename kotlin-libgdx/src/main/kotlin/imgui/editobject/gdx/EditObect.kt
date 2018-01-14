package imgui.editobject.gdx

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import imgui.ImGui
import imgui.editobject.kotlin.*
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty0
import kotlin.reflect.KProperty1
import kotlin.reflect.jvm.isAccessible

fun ImGui.propertyGdxPrimitiveType(
        id: String,
        property: KProperty0<Any?>,
        obj: Any? = property.get(),
        name: String = property.name,
        type: String = property.returnType.simpleName,
        style: PropertyStyle
): Unit {
    val mProperty = property as? KMutableProperty0<Any?>
    propertyLine(id, name, type, style = style)
    sameLine()
    when (obj) {
        is Vector2 -> {
            pushMultiItemsWidths(2)
            inputFloat(id.addPart("x"), obj::x)
            popItemWidth()
            sameLine()
            inputFloat(id.addPart("y"), obj::y)
            popItemWidth()
        }
        is Vector3 -> {
            pushMultiItemsWidths(3)
            inputFloat(id.addPart("x"), obj::x)
            popItemWidth()
            sameLine()
            inputFloat(id.addPart("y"), obj::y)
            popItemWidth()
            sameLine()
            inputFloat(id.addPart("z"), obj::z)
            popItemWidth()
        }
        else -> {
            throw IllegalArgumentException("obj is not GdxPrimitive: ${if (obj == null) null else obj::class}")
        }
    }
}

fun ImGui.allPropertiesGdx(
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
        javaClass.isGdxPrimitiveType -> {
            propertyGdxPrimitiveType(id, property, obj, name, type, style)
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
                        allPropertiesGdx(id = id.addPart(propName), name = propName, property = (prop as KProperty1<Any, Any?>).getProperty0(obj), style = style)
                    }
                    treePop()
                }
            }
        }
    }
}
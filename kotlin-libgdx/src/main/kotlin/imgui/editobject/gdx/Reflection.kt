package imgui.editobject.gdx

inline val <T : Any> Class<T>.isGdxPrimitiveType: Boolean
    get() {
        return when (name) {
            "com.badlogic.gdx.math.Vector2",
            "com.badlogic.gdx.math.Vector3" -> true
            else -> false
        }
    }
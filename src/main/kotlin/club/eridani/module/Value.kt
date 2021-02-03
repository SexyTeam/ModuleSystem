package club.eridani.module

import kotlin.reflect.KProperty


open class Value<T>(val name: String, val default: T, var value: T = default, var visible: () -> Boolean = { true }) {
    operator fun getValue(thisRef: Any, property: KProperty<*>): T {
        return value
    }

    operator fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        this.value = value
    }

    infix fun visible(check: () -> Boolean): Value<T> {
        visible = check
        return this
    }

    open fun fromString(input: String) {

    }
}

fun <T> Module.value(name: String, default: T) = Value(name, default).apply { this@value.values[name] = (this) }

open class StringValue(name: String, default: String) : Value<String>(name, default) {
    override fun fromString(input: String) {
        value = input
    }
}

fun Module.string(name: String, default: String) =
    StringValue(name, default).apply { this@string.values[name] = (this) }

open class NumberValue<N : Number>(name: String, default: N, min: N?, max: N?) : Value<N>(name, default) {
    lateinit var min: N
    lateinit var max: N

    init {
        if (min != null) {
            this.min = min
        }
        if (max != null) {
            this.max = max
        }
    }

    infix fun min(value: N) = this.apply { min = value }
    infix fun max(value: N) = this.apply { max = value }

    override fun fromString(input: String) {
        when (value) {
            is Int -> value = input.toInt() as N
            is Long -> value = input.toLong() as N
            is Float -> value = input.toFloat() as N
            is Double -> value = input.toDouble() as N
        }
    }
}

fun <N : Number> Module.number(name: String, default: N, min: N? = null, max: N? = null) =
    NumberValue(name, default, min, max).apply { this@number.values[name] = (this) }

open class ModeValue<E : Enum<E>>(name: String, default: E, var modes: Array<E>) : Value<E>(name, default) {
    fun next(): E {
        val index = modes.indexOf(value)
        return if (index == modes.size - 1) {
            modes.first()
        } else {
            modes[index + 1]
        }
    }

    override fun fromString(input: String) {
        value = modes.find { it.name.equals(input, true) }!!
    }
}

fun <E : Enum<E>> Module.mode(name: String, modes: Array<E>, default: E = modes.first()) =
    ModeValue(name, default, modes).apply { this@mode.values[name] = (this) }
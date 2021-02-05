package club.eridani.module



open class Module(open val name: String) {
    val values = linkedMapOf<String, Value<*>>()

    fun toConfig() =  values.values.joinToString("\n") { "$name.${it.name} = ${it.value.toString()}" }

    fun fromConfig(config: String) {
        config.split("\n").map {
           it.split(" = ")
        }.forEach {
            val (signature, value) = it
            val (moduleName, valueName) = signature.split(".")
            if (moduleName == name) {
                values[valueName]?.fromString(value)
            }
        }
    }


    infix fun <T> String.valueOf(default: T) = valueOf(this, default)
    infix fun String.textOf(default: String) = textOf(this, default)
    infix fun <N : Number> String.numberOf(default: N) = numberOf(this, default)
    infix fun <M : Enum<M>> String.modeOf(modes :Array<M>) = modeOf(this, modes)
}

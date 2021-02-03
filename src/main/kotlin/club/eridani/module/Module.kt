package club.eridani.module



open class Module(open val name: String) {
    val values = mutableMapOf<String, Value<*>>()

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
}
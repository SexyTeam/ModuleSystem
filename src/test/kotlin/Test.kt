import club.eridani.module.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyModuleConfig(@Serializable(with = AttackRange::class)   val attackRange: FloatRangeValue, var ignoreFriend: Boolean, ) : ModuleConfig(false) {
    private class AttackRange : FloatRangeSerializer(0f, 15f)
}

fun main() {
    val modules = mutableListOf<Module<out MyModuleConfig>>()
    modules += myModule
    println(myModule.config)
    println(myModule.configString)
    println(myModule.config.attackRange.max)
    myModule.importConfig("{\n" +
            "    \"enable\": false,\n" +
            "    \"attackRange\": 1,\n" +
            "    \"ignoreFriend\": true\n" +
            "}")
    println(myModule.config.attackRange.max)
//    println(PrettyPrintJson.encodeToString(MyConfig.serializer(),
//        MyConfig("Good Module System", myModule.config)))

    myModule.config.ignoreFriend = false
    println(myModule.configString)
}

@Serializable
data class MyConfig(
    @SerialName("custom_name") val customName: String,
    @SerialName("my_module") val myModule: MyModuleConfig,
)

val myModule = module("TestModule", "MISC", MyModuleConfig(FloatRangeValue(3f, 0f, 15f), true), MyModuleConfig.serializer()) {
    key = 32

    onEnable {
        println("Enable")
    }

    onDisable {
        println("Disable")
    }

}



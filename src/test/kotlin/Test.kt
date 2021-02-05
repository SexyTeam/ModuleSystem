import club.eridani.module.Module
import club.eridani.module.modeOf
import club.eridani.module.numberOf

class MyModule : Module("Test") {
    enum class AttackMode { CPS, CPT }
    enum class EatMode { Click, Fast }

    var range by numberOf("Range", 3.0f) min 1.0f max 10f
    var attackMode by modeOf("AttackMode", AttackMode.values())
    var cps by numberOf("CPS", 8) min 1 max 20 visible { attackMode == AttackMode.CPS }
    var cpt by numberOf("CPT", 1) min 1 max 50 visible { attackMode == AttackMode.CPT }

    var slot by "Slot" numberOf 1 min 0 max 10
    var eatMode by "EatMode" modeOf EatMode.values()
    var debugMessage by "DebugMessage" textOf "Default Message"
}

fun main() {
    val module = MyModule()
    println(module.toConfig())
    module.fromConfig("Test.Range = 11.0\n" +
            "Test.AttackMode = CPS\n" +
            "Test.CPS = 8\n" +
            "Test.CPT = 1\n" +
            "NONE.NOTHING = 1")

    println(module.range)
    module.range = 20f
    println(module.range)
    println(module.attackMode)
}
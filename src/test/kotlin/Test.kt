import club.eridani.module.Module
import club.eridani.module.mode
import club.eridani.module.number

class MyModule : Module("Test") {
    enum class AttackMode {
        CPS, CPT
    }

    var range by number("Range", 3.0f) min 1.0f max 10f
    var attackMode by mode("AttackMode", AttackMode.values())
    var cps by number("CPS", 8) min 1 max 20 visible { attackMode == AttackMode.CPS }
    var cpt by number("CPT", 1) min 1 max 50 visible { attackMode == AttackMode.CPT }
}

fun main() {
    val module = MyModule()
    println(module.toConfig())
    module.fromConfig("Test.Range = 3.5\n" +
            "Test.AttackMode = CPS\n" +
            "Test.CPS = 8\n" +
            "Test.CPT = 1\n" +
            "NONE.NOTHING = 1")

    println(module.range)
}
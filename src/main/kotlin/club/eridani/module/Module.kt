package club.eridani.module

import io.xiaoshen.eventsystem.Event
import io.xiaoshen.eventsystem.Priority
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


open class Module<T : ModuleConfig>(
    val name: String,
    val category: String,
    var key: Int = 0,
    var config: T,
    private val configSerializer: KSerializer<T>,
) {
    var enable
        get() = config.enable
        set(value) {
            config.enable = value
        }

    fun toggle() = runBlocking {
        enable = !enable
        coroutineScope {
            if (enable) {
                onEnable.forEach { launch { it.invoke(this@Module) } }
            } else {
                onDisable.forEach { launch { it.invoke(this@Module) } }
            }
        }
    }


    private val onEnable = mutableListOf<suspend Module<T>.() -> Unit>()
    private val onDisable = mutableListOf<suspend Module<T>.() -> Unit>()
    fun onEnable(handler: suspend Module<T>.() -> Unit) = onEnable.add(handler)
    fun onDisable(handler: suspend Module<T>.() -> Unit) = onDisable.add(handler)

    open fun <E : Event<R>, R> handle(eClass: E, priority: Priority = Priority.MEDIUM, b: R.() -> Unit) {
        eClass.handle(priority, block = {
            if (this@Module.enable) {
                b.invoke(this)
            }
        })
    }

    val configString get() = PrettyPrintJson.encodeToString(configSerializer, config)
    fun importConfig(importConfigString: String) {
        runCatching {
            config = Json.decodeFromString(configSerializer, importConfigString)
        }.onFailure {
            it.printStackTrace()
        }
    }

}


fun <T : ModuleConfig> module(
    name: String,
    category: String,
    defaultConfig: T,
    configSerializer: KSerializer<T>,
    applyBlock: Module<T>.() -> Unit,
) = Module<T>(name, category, config = defaultConfig, configSerializer = configSerializer).apply(applyBlock)

val PrettyPrintJson = Json { prettyPrint = true }
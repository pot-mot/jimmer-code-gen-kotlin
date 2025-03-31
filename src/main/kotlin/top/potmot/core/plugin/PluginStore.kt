package top.potmot.core.plugin

import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import top.potmot.core.plugin.generate.GeneratePlugin
import java.io.File
import java.net.URLClassLoader
import java.util.concurrent.ConcurrentHashMap
import java.util.jar.JarFile

@Component
@ConfigurationProperties(prefix = "jimmer-code-gen-plugin")
class PluginConfig {
    lateinit var items: List<String>
}

@Component
class PluginStore(
    @Autowired
    private val pluginConfig: PluginConfig
) {
    val logger: Logger = org.slf4j.LoggerFactory.getLogger(PluginStore::class.java)

    private val classLoader = URLClassLoader(arrayOf(), this::class.java.classLoader)

    private val plugins = ConcurrentHashMap<String, JimmerCodeGenPlugin>()

    val generatePlugins: Map<String, GeneratePlugin>
        get() {
            val result = mutableMapOf<String, GeneratePlugin>()
            for ((key, plugin) in plugins) {
                if (plugin is GeneratePlugin) {
                    result[key] = plugin
                }
            }
            return result
        }

    init {
        pluginConfig.items.forEach { name ->
            loadPlugin(name)
        }
    }

    private fun loadPlugin(name: String) {
        if (plugins.contains(name)) {
            logger.warn("Plugin {} is already loaded", name)
            return
        }

        try {
            val file = File("plugins/$name.jar")
            val jarUrl = file.toURI().toURL()
            val jarFile = JarFile(file)
            val manifest = jarFile.manifest

            // 创建专用类加载器（可避免不同插件的类冲突）
            val pluginClassLoader = URLClassLoader(arrayOf(jarUrl), classLoader)

            // 插件实现类在 MANIFEST 中指定（推荐方式）
            val pluginClassName = manifest.mainAttributes.getValue("Plugin-Class")

            val pluginClass = pluginClassLoader.loadClass(pluginClassName)
            val plugin = pluginClass.getDeclaredConstructor().newInstance() as JimmerCodeGenPlugin

            plugins[name] = plugin
            logger.info("Loaded plugin: {}", name)
        } catch (e: Throwable) {
            logger.warn("Loaded plugin Fail: {}", name)
            e.printStackTrace()
        }
    }
}
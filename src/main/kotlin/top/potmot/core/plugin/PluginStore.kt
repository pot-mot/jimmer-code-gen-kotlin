package top.potmot.core.plugin

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.io.File
import java.net.URLClassLoader
import java.util.concurrent.ConcurrentHashMap
import java.util.jar.JarFile
import java.util.jar.Manifest

@Component
@ConfigurationProperties(prefix = "jimmer-code-gen-plugin")
class PluginConfig {
    lateinit var items: List<String>
}

@Component
class PluginStore(
    private val pluginConfig: PluginConfig
) {
    val logger = org.slf4j.LoggerFactory.getLogger(PluginStore::class.java)

    private val classLoader = URLClassLoader(arrayOf(), this::class.java.classLoader)

    private val plugins = ConcurrentHashMap<String, CodeGenPlugin>()

    init {
        pluginConfig.items.forEach { name ->
            loadPlugin(name)
        }
    }

    private fun loadPlugin(name: String) {
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
            val plugin = pluginClass.getDeclaredConstructor().newInstance() as CodeGenPlugin

            plugins[name] = plugin
            logger.info("Loaded plugin: {}", name)
        } catch (e: Exception) {
            logger.error("Loaded plugin Fail: {}", name)
            e.printStackTrace()
        }
    }
}
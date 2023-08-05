package top.potmot.util

import org.apache.velocity.app.Velocity

/**
 * VelocityEngine工厂
 */
object VelocityInitializer {
    private const val UTF8 = "UTF-8"

    /**
     * 初始化vm方法
     */
    fun init() {
        try {
            val p = java.util.Properties()
            // 加载classpath目录下的vm文件
            p.setProperty(
                "resource.loader.file.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader"
            )
            // 定义字符集
            p.setProperty(Velocity.INPUT_ENCODING, UTF8)
            // 初始化Velocity引擎，指定配置Properties
            Velocity.init(p)
        } catch (e: java.lang.Exception) {
            throw java.lang.RuntimeException(e)
        }
    }
}

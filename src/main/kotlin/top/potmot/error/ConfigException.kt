package top.potmot.error

class ConfigException(message: String) : RuntimeException("配置出错：$message")

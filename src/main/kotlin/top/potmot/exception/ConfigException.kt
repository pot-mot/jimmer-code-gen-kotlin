package top.potmot.exception

class ConfigException(message: String) : RuntimeException("配置出错：$message")

package top.potmot.error

class ConfigException(message: String) : RuntimeException("Config parse fail: $message")

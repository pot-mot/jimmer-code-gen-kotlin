package top.potmot.error

class ConfigException(message: String) : Exception("Config parse fail: $message")

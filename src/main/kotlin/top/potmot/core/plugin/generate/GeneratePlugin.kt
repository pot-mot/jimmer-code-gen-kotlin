package top.potmot.core.plugin.generate

import top.potmot.core.plugin.JimmerCodeGenPlugin
import top.potmot.entity.dto.GenerateFile

interface GeneratePlugin : JimmerCodeGenPlugin {
    fun generate(context: GenerateContext): List<GenerateFile>
}
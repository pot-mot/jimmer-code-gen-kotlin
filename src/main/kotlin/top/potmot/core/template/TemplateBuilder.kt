package top.potmot.core.template

/**
 * 模版创建者
 */
class TemplateBuilder {
    private val stringBuilder = StringBuilder()
    private var indentation = 0
    private var indentationLength = 4

    constructor()

    constructor(
        indentation: Int
    ) {
        this.indentation = indentation
    }

    constructor(
        indentation: Int,
        indentationLength: Int,
    ) {
        this.indentation = indentation
        this.indentationLength = indentationLength
    }

    private fun appendIndentation() {
        repeat(indentation) {
            stringBuilder.append(" ")
        }
    }

    fun increaseIndentation() {
        indentation += indentationLength
    }

    fun decreaseIndentation() {
        indentation -= indentationLength
        if (indentation < 0) {
            indentation = 0
        }
    }

    fun separate() {
        appendIndentation()
        stringBuilder.appendLine()
    }

    fun append(string: String) {
        stringBuilder.append(string)
    }

    fun line(string: String?) {
        if (string.isNullOrBlank()) return

        appendIndentation()
        stringBuilder.appendLine(string)
    }

    fun lines(stringArray: Array<String>?) {
        stringArray?.forEach {
            line(it)
        }
    }

    fun lines(stringList: List<String>?) {
        stringList?.forEach {
            line(it)
        }
    }

    fun lines(string: String?) {
        string?.split("\n")?.forEach {
            line(it)
        }
    }

    fun joinParts(stringList: List<String>?, separate: String? = null) {
        stringList?.forEachIndexed { index, it ->
            lines(it)

            if (index < stringList.size - 1) {
                if (!separate.isNullOrBlank()) {
                    line(separate)
                } else {
                    separate()
                }
            }
        }
    }

    fun toBlockLines(string: String?, wrapLength: Int = 40, separator: String = " "): List<String>? {
        if (string.isNullOrBlank()) return null

        val list = mutableListOf<String>()

        val words = string.split(separator)

        var currentLine = ""

        // 遍历每个单词，并将它们添加到当前行
        for (word in words) {
            // 如果当前行加上当前单词的长度超过了wrapLength，则将当前行添加到列表中，并开始一个新行
            if (currentLine.length + word.length > wrapLength) {
                list += currentLine.trim()
                currentLine = ""
            }

            // 将当前单词添加到当前行，并添加一个空格
            currentLine += word
        }

        // 添加最后一行到列表中
        if (currentLine.isNotBlank()) {
            list += currentLine.trim()
        }

        return list
    }

    fun block(string: String?, wrapLength: Int = 40, separator: String = " ") {
        toBlockLines(string, wrapLength, separator)?.forEach {
            appendIndentation()
            stringBuilder.appendLine(it)
        }
    }

    fun isEmpty(): Boolean {
        return stringBuilder.isEmpty()
    }

    fun isBlank(): Boolean {
        return stringBuilder.isBlank()
    }

    fun isNotBlank(): Boolean {
        return stringBuilder.isNotBlank()
    }

    fun isEmptyOrBlank(): Boolean {
        return stringBuilder.isEmpty() || stringBuilder.isBlank()
    }

    fun build(): String {
        return stringBuilder.toString()
    }

    fun lineCount(): Int {
        return stringBuilder.lineSequence().count()
    }

    fun toLines(): List<String> {
        return stringBuilder.toString().split("\n")
    }
}

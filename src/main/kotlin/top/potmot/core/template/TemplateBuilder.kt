package top.potmot.core.template

import javax.swing.JPopupMenu.Separator

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

    fun lines(stringList: List<String>, format: (String) -> String = { it }) {
        stringList.forEach {
            line(format(it))
        }
    }

    fun lines(string: String?, lineSeparate: String = "\n", format: (String) -> String = { it }) {
        string?.split(lineSeparate)?.forEach {
            line(format(it))
        }
    }

    fun joinParts(stringList: List<String>?, separate: String? = null, format: (String) -> String = { it }) {
        stringList?.forEachIndexed { index, it ->
            lines(format(it))

            if (index < stringList.size - 1) {
                if (!separate.isNullOrBlank()) {
                    line(separate)
                } else {
                    separate()
                }
            }
        }
    }

    private fun toBlockLines(
        string: String?,
        lineLength: Int = 40,
        paragraphSeparator: String = "\n",
        wordSeparator: String = " "
    ): List<String>? {
        if (string.isNullOrBlank()) return null

        val list = mutableListOf<String>()

        val paragraphs = string.split(paragraphSeparator)

        paragraphs.forEach { paragraph ->
            val words = paragraph.split(wordSeparator)

            var currentLine = ""

            // 遍历每个单词，并将它们添加到当前行
            for (word in words) {
                // 如果当前行加上当前单词的长度超过了 lineLength，则将当前行添加到列表中，并开始一个新行
                if (currentLine.length + word.length > lineLength) {
                    list += currentLine.trim()
                    currentLine = ""
                }

                // 将当前单词添加到当前行，并添加一个分隔
                currentLine += word
                currentLine += wordSeparator
            }

            // 添加最后一行到列表中
            if (currentLine.isNotBlank()) {
                list += currentLine.trim()
            }
        }

        return list
    }

    fun block(
        string: String?,
        lineLength: Int = 40,
        paragraphSeparator: String = "\n",
        wordSeparator: String = " ",
        format: (String) -> String = { it }
    ) {
        toBlockLines(string, lineLength, paragraphSeparator, wordSeparator)?.forEach {
            line(format(it))
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

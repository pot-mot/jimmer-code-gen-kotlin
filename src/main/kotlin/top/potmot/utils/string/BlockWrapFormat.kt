package top.potmot.utils.string

fun String.toBlockLines(
    lineLength: Int = 40,
    paragraphSeparator: String = "\n",
    wordSeparator: String = " "
): List<String> {
    if (isNullOrBlank()) return emptyList()

    val list = mutableListOf<String>()

    val paragraphs = split(paragraphSeparator)

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

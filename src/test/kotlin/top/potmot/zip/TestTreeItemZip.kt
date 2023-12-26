package top.potmot.zip

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.utils.zip.TreeItem
import top.potmot.utils.zip.toZipByteArray
import java.io.ByteArrayInputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

@SpringBootTest
@ActiveProfiles("test-kotlin", "mysql")
class TestTreeItemZip {
    @Test
    fun testToZipByteArray() {
        val tree = createSampleTree()

        val zipByteArray = tree.toZipByteArray()

        val zipInputStream = ZipInputStream(ByteArrayInputStream(zipByteArray))

        val result = mutableListOf<String>()

        // 遍历 ZipInputStream 中的条目
        var entry: ZipEntry? = zipInputStream.nextEntry

        while (entry != null) {
            result += entry.name
            entry = zipInputStream.nextEntry
        }

        assertEquals("child1/key1,child2/key2", result.joinToString(","))
    }

    // 创建一个示例树结构
    private fun createSampleTree(): TreeItem<String> {
        val child1 = TreeItem("child1", listOf("key1" to "value1"))
        val child2 = TreeItem("child2", listOf("key2" to "value2"))
        val child3 = TreeItem<String>("child3")

        val subChild1 = TreeItem<String>("subChild1")
        val subChild2 = TreeItem<String>("subChild2")

        val subSubChild1 = TreeItem<String>("subSubChild1")
        val subSubChild2 = TreeItem<String>("subSubChild2")

        child1.children = listOf(subChild1, subChild2)
        subChild1.children = listOf(subSubChild1, subSubChild2)

        return TreeItem("root", children = listOf(child1, child2, child3))
    }
}

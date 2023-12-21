package top.potmot.association

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.config.GenConfig
import top.potmot.core.database.match.includeTableNamePkColumnMatch
import top.potmot.core.database.match.pkSuffixColumnMatch
import top.potmot.core.database.match.simplePkColumnMatch
import top.potmot.core.database.match.suffixMatch
import top.potmot.enumeration.AssociationType
import top.potmot.enumeration.GenLanguage
import top.potmot.enumeration.TableType
import top.potmot.model.dto.GenColumnMatchView
import top.potmot.service.AssociationService

@SpringBootTest
@ActiveProfiles("test-kotlin", "mysql")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class AssociationMatchTest(
    @Autowired val associationService: AssociationService,
) {
    @Test
    @Order(1)
    fun showConfig() {
        assertEquals("_", GenConfig.separator)
        assertEquals(GenLanguage.KOTLIN, GenConfig.language)
    }

    @Test
    @Order(2)
    fun simplePkTest() {
        // 创建测试数据
        val table1 = GenColumnMatchView.TargetOf_table(1, "table1", "", TableType.TABLE)
        val table2 = GenColumnMatchView.TargetOf_table(2, "table2", "", TableType.TABLE)

        val column1 = GenColumnMatchView(1, "id", "", 0, "", true, false, table1)
        val column2 = GenColumnMatchView(2, "table2_id", "", 0, "", false, false, table1)

        val column3 = GenColumnMatchView(3, "id", "", 0, "", true, false, table2)
        val column4 = GenColumnMatchView(4, "table1_id", "", 0, "", false, false, table2)

        assert(simplePkColumnMatch(column4, column1)!!.associationType == AssociationType.MANY_TO_ONE)
        assert(simplePkColumnMatch(column2, column3)!!.associationType == AssociationType.MANY_TO_ONE)

        val columns = listOf(column1, column2, column3, column4)

        val matchAssociations = associationService.matchColumns(columns, simplePkColumnMatch)
        matchAssociations.forEach { println(it) }
        assertEquals(2, matchAssociations.size)
    }

    @Test
    @Order(3)
    fun includeTableNamePkTest() {
        // 创建测试数据
        val table1 = GenColumnMatchView.TargetOf_table(1, "table1", "", TableType.TABLE)
        val table2 = GenColumnMatchView.TargetOf_table(2, "table2", "", TableType.TABLE)

        val column1 = GenColumnMatchView(1, "table1_id", "", 0, "", true, false, table1)
        val column2 = GenColumnMatchView(2, "table2_id", "", 0, "", false, false, table1)

        val column3 = GenColumnMatchView(3, "table2_id", "", 0, "", true, false, table2)
        val column4 = GenColumnMatchView(4, "table1_id", "", 0, "", false, false, table2)

        assert(includeTableNamePkColumnMatch(column4, column1)!!.associationType == AssociationType.MANY_TO_ONE)
        assert(includeTableNamePkColumnMatch(column2, column3)!!.associationType == AssociationType.MANY_TO_ONE)

        val columns = listOf(column1, column2, column3, column4)

        val matchAssociations = associationService.matchColumns(columns, includeTableNamePkColumnMatch)
        matchAssociations.forEach { println(it) }
        assertEquals(2, matchAssociations.size)
    }

    @Test
    @Order(4)
    fun suffixPkTest() {
        assertEquals(2, suffixMatch(listOf("table1", "id"), listOf("table1", "id")).size)
        assertEquals(2, suffixMatch(listOf("gen", "table1", "id"), listOf("table1", "id")).size)
        assertEquals(2, suffixMatch(listOf("table1", "id"), listOf("gen", "table1", "id")).size)
        assertEquals(3, suffixMatch(listOf("gen", "table1", "id"), listOf("gen", "table1", "id")).size)


        // 创建测试数据
        val table1 = GenColumnMatchView.TargetOf_table(1, "item", "", TableType.TABLE)
        val table2 = GenColumnMatchView.TargetOf_table(2, "item_group", "", TableType.TABLE)

        val column1 = GenColumnMatchView(1, "id", "", 0, "", true, false, table1)
        val column2 = GenColumnMatchView(2, "group_id", "", 0, "", false, false, table1)
        val column3 = GenColumnMatchView(3, "group_name", "", 0, "", false, false, table1)

        val column4 = GenColumnMatchView(4, "id", "", 0, "", true, false, table2)
        val column5 = GenColumnMatchView(5, "name", "", 0, "", false, false, table2)

        assert(pkSuffixColumnMatch(column2, column4)!!.associationType == AssociationType.MANY_TO_ONE)

        val columns = listOf(column1, column2, column3, column4, column5)

        val matchAssociations = associationService.matchColumns(columns, pkSuffixColumnMatch)
        matchAssociations.forEach { println(it) }
        assertEquals(1, matchAssociations.size)
    }
}

package top.potmot.association

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import top.potmot.enum.TableType
import top.potmot.model.dto.GenColumnMatchView
import top.potmot.service.AssociationService
import top.potmot.util.association.includeTableNamePkColumnMatch
import top.potmot.util.association.simplePkColumnMatch
import top.potmot.util.association.suffixMatch
import top.potmot.util.association.suffixPkColumnMatch

@SpringBootTest
class  AssociationScanTest(
   @Autowired val associationService: AssociationService
) {
    @Test
    fun simplePkTest() {
        // 创建测试数据
        val table1 = GenColumnMatchView.TargetOf_table.create(1, "table1", "", TableType.TABLE)
        val table2 = GenColumnMatchView.TargetOf_table.create(2, "table2", "", TableType.TABLE)

        val column1 = GenColumnMatchView(1, "id", "", 0, "", true, false, table1)
        val column2 = GenColumnMatchView(2, "table2_id", "", 0, "", false, false, table1)

        val column3 = GenColumnMatchView(3, "id", "", 0, "", true, false, table2)
        val column4 = GenColumnMatchView(4, "table1_id", "", 0, "", false, false, table2)

        assert(simplePkColumnMatch(column4, column1))
        assert(simplePkColumnMatch(column2, column3))

        val columns = listOf(column1, column2, column3, column4)

        val matchAssociations = associationService.matchColumns(columns, simplePkColumnMatch)
        matchAssociations.forEach { println(it) }
        assertEquals(2, matchAssociations.size)
    }

    @Test
    fun includeTableNamePkTest() {
        // 创建测试数据
        val table1 = GenColumnMatchView.TargetOf_table.create(1, "table1", "", TableType.TABLE)
        val table2 = GenColumnMatchView.TargetOf_table.create(2, "table2", "", TableType.TABLE)

        val column1 = GenColumnMatchView(1, "table1_id", "", 0, "", true, false, table1)
        val column2 = GenColumnMatchView(2, "table2_id", "", 0, "", false, false, table1)

        val column3 = GenColumnMatchView(3, "table2_id", "", 0, "", true, false, table2)
        val column4 = GenColumnMatchView(4, "table1_id", "", 0, "", false, false, table2)

        assert(includeTableNamePkColumnMatch(column4, column1))
        assert(includeTableNamePkColumnMatch(column2, column3))

        val columns = listOf(column1, column2, column3, column4)

        val matchAssociations = associationService.matchColumns(columns, includeTableNamePkColumnMatch)
        matchAssociations.forEach { println(it) }
        assertEquals(2, matchAssociations.size)
    }

    @Test
    fun suffixPkTest() {
        assertEquals(2, suffixMatch("table1_id", "table1_id").size)
        assertEquals(2, suffixMatch("gen_table1_id", "table1_id").size)
        assertEquals(2, suffixMatch("table1_id", "gen_table1_id").size)
        assertEquals(3, suffixMatch("gen_table1_id", "gen_table1_id").size)


        // 创建测试数据
        val table1 = GenColumnMatchView.TargetOf_table.create(1, "table1", "", TableType.TABLE)
        val table2 = GenColumnMatchView.TargetOf_table.create(2, "table2", "", TableType.TABLE)

        val column1 = GenColumnMatchView(1, "id", "", 0, "", true, false, table1)
        val column2 = GenColumnMatchView(2, "table1_id", "", 0, "", false, false, table1)
        val column3 = GenColumnMatchView(3, "table2_id", "", 0, "", false, false, table1)

        val column4 = GenColumnMatchView(4, "id", "", 0, "", true, false, table2)
        val column5 = GenColumnMatchView(5, "table2_id", "", 0, "", false, false, table2)
        val column6 = GenColumnMatchView(6, "table1_id", "", 0, "", false, false, table2)

        assert(suffixPkColumnMatch(column6, column1))
        assert(suffixPkColumnMatch(column3, column4))

        val columns = listOf(column1, column2, column3, column4, column5, column6)

        val matchAssociations = associationService.matchColumns(columns, suffixPkColumnMatch)
        matchAssociations.forEach { println(it) }
        assertEquals(2, matchAssociations.size)
    }
}

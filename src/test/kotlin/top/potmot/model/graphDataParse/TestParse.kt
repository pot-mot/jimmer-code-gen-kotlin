package top.potmot.model.graphDataParse

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.core.database.load.parseGraphData

@SpringBootTest
@ActiveProfiles("test-kotlin", "h2")
class TestParse {
    @Test
    fun testAssociationsGraphDataParse() {
        val graphData = parseGraphData(1, GRAPH_DATA)

        assertEquals(9, graphData.tables.size)
        assertEquals(5, graphData.associations.size)

        assertEquals(
            "GraphData(" +
                    "tables=[" +
                    "GenTableModelInput(name=tree_node, comment=, type=TABLE, remark=, modelId=1, columns=[" +
                    "TargetOf_columns(name=id, typeCode=-5, overwriteByRaw=false, rawType=BIGINT, typeNotNull=true, dataSize=0, numericPrecision=0, defaultValue=null, comment=, partOfPk=true, autoIncrement=true, businessKey=false, logicalDelete=false, orderKey=1, remark=, enum=null), " +
                    "TargetOf_columns(name=parent_id, typeCode=-5, overwriteByRaw=false, rawType=BIGINT, typeNotNull=false, dataSize=0, numericPrecision=0, defaultValue=null, comment=, partOfPk=false, autoIncrement=false, businessKey=false, logicalDelete=false, orderKey=2, remark=, enum=null)], indexes=[]), " +
                    "GenTableModelInput(name=o_o_target, comment=, type=TABLE, remark=, modelId=1, columns=[" +
                    "TargetOf_columns(name=id, typeCode=-5, overwriteByRaw=false, rawType=BIGINT, typeNotNull=true, dataSize=0, numericPrecision=0, defaultValue=null, comment=, partOfPk=true, autoIncrement=true, businessKey=false, logicalDelete=false, orderKey=1, remark=, enum=null)], indexes=[]), " +
                    "GenTableModelInput(name=o_m_target, comment=, type=TABLE, remark=, modelId=1, columns=[" +
                    "TargetOf_columns(name=id, typeCode=-5, overwriteByRaw=false, rawType=BIGINT, typeNotNull=true, dataSize=0, numericPrecision=0, defaultValue=null, comment=, partOfPk=true, autoIncrement=true, businessKey=false, logicalDelete=false, orderKey=1, remark=, enum=null), " +
                    "TargetOf_columns(name=source_id, typeCode=-5, overwriteByRaw=false, rawType=BIGINT, typeNotNull=true, dataSize=0, numericPrecision=0, defaultValue=null, comment=, partOfPk=false, autoIncrement=false, businessKey=false, logicalDelete=false, orderKey=2, remark=, enum=null)], indexes=[]), " +
                    "GenTableModelInput(name=o_o_source, comment=, type=TABLE, remark=, modelId=1, columns=[" +
                    "TargetOf_columns(name=id, typeCode=-5, overwriteByRaw=false, rawType=BIGINT, typeNotNull=true, dataSize=0, numericPrecision=0, defaultValue=null, comment=, partOfPk=true, autoIncrement=true, businessKey=false, logicalDelete=false, orderKey=1, remark=, enum=null), " +
                    "TargetOf_columns(name=target_id, typeCode=-5, overwriteByRaw=false, rawType=BIGINT, typeNotNull=true, dataSize=0, numericPrecision=0, defaultValue=null, comment=, partOfPk=false, autoIncrement=false, businessKey=false, logicalDelete=false, orderKey=2, remark=, enum=null)], indexes=[]), " +
                    "GenTableModelInput(name=o_m_source, comment=, type=TABLE, remark=, modelId=1, columns=[" +
                    "TargetOf_columns(name=id, typeCode=-5, overwriteByRaw=false, rawType=BIGINT, typeNotNull=true, dataSize=0, numericPrecision=0, defaultValue=null, comment=, partOfPk=true, autoIncrement=true, businessKey=false, logicalDelete=false, orderKey=1, remark=, enum=null)], indexes=[]), " +
                    "GenTableModelInput(name=m_n_source, comment=, type=TABLE, remark=, modelId=1, columns=[" +
                    "TargetOf_columns(name=id, typeCode=-5, overwriteByRaw=false, rawType=BIGINT, typeNotNull=true, dataSize=0, numericPrecision=0, defaultValue=null, comment=, partOfPk=true, autoIncrement=true, businessKey=false, logicalDelete=false, orderKey=1, remark=, enum=null)], indexes=[]), " +
                    "GenTableModelInput(name=m_o_target, comment=, type=TABLE, remark=, modelId=1, columns=[" +
                    "TargetOf_columns(name=id, typeCode=-5, overwriteByRaw=false, rawType=BIGINT, typeNotNull=true, dataSize=0, numericPrecision=0, defaultValue=null, comment=, partOfPk=true, autoIncrement=true, businessKey=false, logicalDelete=false, orderKey=1, remark=, enum=null)], indexes=[]), " +
                    "GenTableModelInput(name=m_o_source, comment=, type=TABLE, remark=, modelId=1, columns=[" +
                    "TargetOf_columns(name=id, typeCode=-5, overwriteByRaw=false, rawType=BIGINT, typeNotNull=true, dataSize=0, numericPrecision=0, defaultValue=null, comment=, partOfPk=true, autoIncrement=true, businessKey=false, logicalDelete=false, orderKey=1, remark=, enum=null), " +
                    "TargetOf_columns(name=source_id, typeCode=-5, overwriteByRaw=false, rawType=BIGINT, typeNotNull=true, dataSize=0, numericPrecision=0, defaultValue=null, comment=, partOfPk=false, autoIncrement=false, businessKey=false, logicalDelete=false, orderKey=2, remark=, enum=null)], indexes=[]), " +
                    "GenTableModelInput(name=m_n_target, comment=, type=TABLE, remark=, modelId=1, columns=[" +
                    "TargetOf_columns(name=id, typeCode=-5, overwriteByRaw=false, rawType=BIGINT, typeNotNull=true, dataSize=0, numericPrecision=0, defaultValue=null, comment=, partOfPk=true, autoIncrement=true, businessKey=false, logicalDelete=false, orderKey=1, remark=, enum=null)], indexes=[])" +
                    "], " +
                    "associations=[" +
                    "GenAssociationModelInput(modelId=1, name=fk_tree_node_parent, type=MANY_TO_ONE, dissociateAction=null, updateAction=, deleteAction=, fake=false, sourceTable=TargetOf_sourceTable(modelId=1, name=tree_node, comment=), targetTable=TargetOf_targetTable(modelId=1, name=tree_node, comment=), columnReferences=[" +
                    "TargetOf_columnReferences(sourceColumn=TargetOf_sourceColumn_2(name=parent_id, comment=, typeCode=-5, rawType=BIGINT), targetColumn=TargetOf_targetColumn_2(name=id, comment=, typeCode=-5, rawType=BIGINT))]), " +
                    "GenAssociationModelInput(modelId=1, name=fk_one_to_one, type=ONE_TO_ONE, dissociateAction=null, updateAction=, deleteAction=, fake=false, sourceTable=TargetOf_sourceTable(modelId=1, name=o_o_source, comment=), targetTable=TargetOf_targetTable(modelId=1, name=o_o_target, comment=), columnReferences=[" +
                    "TargetOf_columnReferences(sourceColumn=TargetOf_sourceColumn_2(name=target_id, comment=, typeCode=-5, rawType=BIGINT), targetColumn=TargetOf_targetColumn_2(name=id, comment=, typeCode=-5, rawType=BIGINT))]), " +
                    "GenAssociationModelInput(modelId=1, name=fk_one_to_many, type=ONE_TO_MANY, dissociateAction=null, updateAction=, deleteAction=, fake=false, sourceTable=TargetOf_sourceTable(modelId=1, name=o_m_source, comment=), targetTable=TargetOf_targetTable(modelId=1, name=o_m_target, comment=), columnReferences=[" +
                    "TargetOf_columnReferences(sourceColumn=TargetOf_sourceColumn_2(name=id, comment=, typeCode=-5, rawType=BIGINT), targetColumn=TargetOf_targetColumn_2(name=source_id, comment=, typeCode=-5, rawType=BIGINT))]), " +
                    "GenAssociationModelInput(modelId=1, name=fk_many_to_one, type=MANY_TO_ONE, dissociateAction=null, updateAction=, deleteAction=, fake=false, sourceTable=TargetOf_sourceTable(modelId=1, name=m_o_source, comment=), targetTable=TargetOf_targetTable(modelId=1, name=m_o_target, comment=), columnReferences=[" +
                    "TargetOf_columnReferences(sourceColumn=TargetOf_sourceColumn_2(name=source_id, comment=, typeCode=-5, rawType=BIGINT), targetColumn=TargetOf_targetColumn_2(name=id, comment=, typeCode=-5, rawType=BIGINT))]), " +
                    "GenAssociationModelInput(modelId=1, name=many_to_many_mapping, type=MANY_TO_MANY, dissociateAction=null, updateAction=, deleteAction=, fake=false, sourceTable=TargetOf_sourceTable(modelId=1, name=m_n_source, comment=), targetTable=TargetOf_targetTable(modelId=1, name=m_n_target, comment=), columnReferences=[" +
                    "TargetOf_columnReferences(sourceColumn=TargetOf_sourceColumn_2(name=id, comment=, typeCode=-5, rawType=BIGINT), targetColumn=TargetOf_targetColumn_2(name=id, comment=, typeCode=-5, rawType=BIGINT))])" +
                    "])",
            graphData.toString()
        )
    }
}

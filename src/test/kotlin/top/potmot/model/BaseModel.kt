package top.potmot.model

import com.fasterxml.jackson.module.kotlin.readValue
import top.potmot.model.dto.GenModelInput
import top.potmot.utils.json.commonObjectMapper

fun createBaseModel(
    graphData: String,
    enumJsons: List<String> = emptyList()
) =
    commonObjectMapper.readValue<GenModelInput>(
        baseModel
    ).copy(
        graphData = graphData,
        enums = enumJsons.map { json -> commonObjectMapper.readValue<GenModelInput.TargetOf_enums>(json) }
    )

private const val baseModel = """
{
    "remark": "",
    "name": "test",
    "graphData": "",
    "syncConvertEntity": true,
    "language": "KOTLIN",
    "dataSourceType": "MySQL",
    "author": "",
    "packagePath": "top.potmot",
    "tablePath": "",
    "databaseNamingStrategy": "UPPER_CASE",
    "realFk": true,
    "idViewProperty": true,
    "logicalDeletedAnnotation": "@LogicalDeleted(\"true\")",
    "tableAnnotation": true,
    "columnAnnotation": true,
    "joinTableAnnotation": true,
    "joinColumnAnnotation": true,
    "tableNamePrefixes": "",
    "tableNameSuffixes": "",
    "tableCommentPrefixes": "",
    "tableCommentSuffixes": "",
    "columnNamePrefixes": "",
    "columnNameSuffixes": "",
    "columnCommentPrefixes": "",
    "columnCommentSuffixes": "",
    "enums": []
}
"""

package top.potmot.model

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.babyfish.jimmer.jackson.ImmutableModule
import top.potmot.model.dto.GenModelInput

private val mapper = jacksonObjectMapper()
    .registerModule(ImmutableModule())
    .registerModule(JavaTimeModule())

fun createBaseModel(
    graphData: String,
    enumJsons: List<String> = emptyList()
) =
    mapper.readValue<GenModelInput>(
        baseModel
    ).let {
        it.graphData = graphData
        it.enums = enumJsons.map { json -> mapper.readValue<GenModelInput.TargetOf_enums>(json) }
        it
    }

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
    "lowerCaseName": false,
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

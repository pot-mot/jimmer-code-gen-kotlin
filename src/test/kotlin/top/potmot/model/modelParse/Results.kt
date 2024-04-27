package top.potmot.model.modelParse

const val PARSE_RESULT = """
{
    "tables" : [
        {
            "name" : "tree_node",
            "comment" : "",
            "type" : "TABLE",
            "remark" : "",
            "modelId" : 1,
            "superTables" : [ ],
            "columns" : [
                {
                    "name" : "id",
                    "typeCode" : -5,
                    "overwriteByRaw" : false,
                    "rawType" : "BIGINT",
                    "typeNotNull" : true,
                    "dataSize" : 0,
                    "numericPrecision" : 0,
                    "defaultValue" : null,
                    "comment" : "",
                    "partOfPk" : true,
                    "autoIncrement" : true,
                    "businessKey" : false,
                    "logicalDelete" : false,
                    "orderKey" : 1,
                    "remark" : "",
                    "enum" : null
                },
                {
                    "name" : "parent_id",
                    "typeCode" : -5,
                    "overwriteByRaw" : false,
                    "rawType" : "BIGINT",
                    "typeNotNull" : false,
                    "dataSize" : 0,
                    "numericPrecision" : 0,
                    "defaultValue" : null,
                    "comment" : "",
                    "partOfPk" : false,
                    "autoIncrement" : false,
                    "businessKey" : false,
                    "logicalDelete" : false,
                    "orderKey" : 2,
                    "remark" : "",
                    "enum" : null
                }
            ],
            "indexes" : [ ]
        },
        {
            "name" : "o_o_target",
            "comment" : "",
            "type" : "TABLE",
            "remark" : "",
            "modelId" : 1,
            "superTables" : [ ],
            "columns" : [
                {
                    "name" : "id",
                    "typeCode" : -5,
                    "overwriteByRaw" : false,
                    "rawType" : "BIGINT",
                    "typeNotNull" : true,
                    "dataSize" : 0,
                    "numericPrecision" : 0,
                    "defaultValue" : null,
                    "comment" : "",
                    "partOfPk" : true,
                    "autoIncrement" : true,
                    "businessKey" : false,
                    "logicalDelete" : false,
                    "orderKey" : 1,
                    "remark" : "",
                    "enum" : null
                }
            ],
            "indexes" : [ ]
        },
        {
            "name" : "o_m_target",
            "comment" : "",
            "type" : "TABLE",
            "remark" : "",
            "modelId" : 1,
            "superTables" : [ ],
            "columns" : [
                {
                    "name" : "id",
                    "typeCode" : -5,
                    "overwriteByRaw" : false,
                    "rawType" : "BIGINT",
                    "typeNotNull" : true,
                    "dataSize" : 0,
                    "numericPrecision" : 0,
                    "defaultValue" : null,
                    "comment" : "",
                    "partOfPk" : true,
                    "autoIncrement" : true,
                    "businessKey" : false,
                    "logicalDelete" : false,
                    "orderKey" : 1,
                    "remark" : "",
                    "enum" : null
                },
                {
                    "name" : "source_id",
                    "typeCode" : -5,
                    "overwriteByRaw" : false,
                    "rawType" : "BIGINT",
                    "typeNotNull" : true,
                    "dataSize" : 0,
                    "numericPrecision" : 0,
                    "defaultValue" : null,
                    "comment" : "",
                    "partOfPk" : false,
                    "autoIncrement" : false,
                    "businessKey" : false,
                    "logicalDelete" : false,
                    "orderKey" : 2,
                    "remark" : "",
                    "enum" : null
                }
            ],
            "indexes" : [ ]
        },
        {
            "name" : "o_o_source",
            "comment" : "",
            "type" : "TABLE",
            "remark" : "",
            "modelId" : 1,
            "superTables" : [ ],
            "columns" : [
                {
                    "name" : "id",
                    "typeCode" : -5,
                    "overwriteByRaw" : false,
                    "rawType" : "BIGINT",
                    "typeNotNull" : true,
                    "dataSize" : 0,
                    "numericPrecision" : 0,
                    "defaultValue" : null,
                    "comment" : "",
                    "partOfPk" : true,
                    "autoIncrement" : true,
                    "businessKey" : false,
                    "logicalDelete" : false,
                    "orderKey" : 1,
                    "remark" : "",
                    "enum" : null
                },
                {
                    "name" : "target_id",
                    "typeCode" : -5,
                    "overwriteByRaw" : false,
                    "rawType" : "BIGINT",
                    "typeNotNull" : true,
                    "dataSize" : 0,
                    "numericPrecision" : 0,
                    "defaultValue" : null,
                    "comment" : "",
                    "partOfPk" : false,
                    "autoIncrement" : false,
                    "businessKey" : false,
                    "logicalDelete" : false,
                    "orderKey" : 2,
                    "remark" : "",
                    "enum" : null
                }
            ],
            "indexes" : [ ]
        },
        {
            "name" : "o_m_source",
            "comment" : "",
            "type" : "TABLE",
            "remark" : "",
            "modelId" : 1,
            "superTables" : [ ],
            "columns" : [
                {
                    "name" : "id",
                    "typeCode" : -5,
                    "overwriteByRaw" : false,
                    "rawType" : "BIGINT",
                    "typeNotNull" : true,
                    "dataSize" : 0,
                    "numericPrecision" : 0,
                    "defaultValue" : null,
                    "comment" : "",
                    "partOfPk" : true,
                    "autoIncrement" : true,
                    "businessKey" : false,
                    "logicalDelete" : false,
                    "orderKey" : 1,
                    "remark" : "",
                    "enum" : null
                }
            ],
            "indexes" : [ ]
        },
        {
            "name" : "m_n_source",
            "comment" : "",
            "type" : "TABLE",
            "remark" : "",
            "modelId" : 1,
            "superTables" : [ ],
            "columns" : [
                {
                    "name" : "id",
                    "typeCode" : -5,
                    "overwriteByRaw" : false,
                    "rawType" : "BIGINT",
                    "typeNotNull" : true,
                    "dataSize" : 0,
                    "numericPrecision" : 0,
                    "defaultValue" : null,
                    "comment" : "",
                    "partOfPk" : true,
                    "autoIncrement" : true,
                    "businessKey" : false,
                    "logicalDelete" : false,
                    "orderKey" : 1,
                    "remark" : "",
                    "enum" : null
                }
            ],
            "indexes" : [ ]
        },
        {
            "name" : "m_o_target",
            "comment" : "",
            "type" : "TABLE",
            "remark" : "",
            "modelId" : 1,
            "superTables" : [ ],
            "columns" : [
                {
                    "name" : "id",
                    "typeCode" : -5,
                    "overwriteByRaw" : false,
                    "rawType" : "BIGINT",
                    "typeNotNull" : true,
                    "dataSize" : 0,
                    "numericPrecision" : 0,
                    "defaultValue" : null,
                    "comment" : "",
                    "partOfPk" : true,
                    "autoIncrement" : true,
                    "businessKey" : false,
                    "logicalDelete" : false,
                    "orderKey" : 1,
                    "remark" : "",
                    "enum" : null
                }
            ],
            "indexes" : [ ]
        },
        {
            "name" : "m_o_source",
            "comment" : "",
            "type" : "TABLE",
            "remark" : "",
            "modelId" : 1,
            "superTables" : [ ],
            "columns" : [
                {
                    "name" : "id",
                    "typeCode" : -5,
                    "overwriteByRaw" : false,
                    "rawType" : "BIGINT",
                    "typeNotNull" : true,
                    "dataSize" : 0,
                    "numericPrecision" : 0,
                    "defaultValue" : null,
                    "comment" : "",
                    "partOfPk" : true,
                    "autoIncrement" : true,
                    "businessKey" : false,
                    "logicalDelete" : false,
                    "orderKey" : 1,
                    "remark" : "",
                    "enum" : null
                },
                {
                    "name" : "source_id",
                    "typeCode" : -5,
                    "overwriteByRaw" : false,
                    "rawType" : "BIGINT",
                    "typeNotNull" : true,
                    "dataSize" : 0,
                    "numericPrecision" : 0,
                    "defaultValue" : null,
                    "comment" : "",
                    "partOfPk" : false,
                    "autoIncrement" : false,
                    "businessKey" : false,
                    "logicalDelete" : false,
                    "orderKey" : 2,
                    "remark" : "",
                    "enum" : null
                }
            ],
            "indexes" : [ ]
        },
        {
            "name" : "m_n_target",
            "comment" : "",
            "type" : "TABLE",
            "remark" : "",
            "modelId" : 1,
            "superTables" : [ ],
            "columns" : [
                {
                    "name" : "id",
                    "typeCode" : -5,
                    "overwriteByRaw" : false,
                    "rawType" : "BIGINT",
                    "typeNotNull" : true,
                    "dataSize" : 0,
                    "numericPrecision" : 0,
                    "defaultValue" : null,
                    "comment" : "",
                    "partOfPk" : true,
                    "autoIncrement" : true,
                    "businessKey" : false,
                    "logicalDelete" : false,
                    "orderKey" : 1,
                    "remark" : "",
                    "enum" : null
                }
            ],
            "indexes" : [ ]
        }
    ],
    "associations" : [
        {
            "modelId" : 1,
            "name" : "fk_tree_node_parent",
            "type" : "MANY_TO_ONE",
            "dissociateAction" : null,
            "updateAction" : "",
            "deleteAction" : "",
            "fake" : false,
            "sourceTableName" : "tree_node",
            "targetTableName" : "tree_node",
            "columnReferences" : [
                {
                    "sourceColumnName" : "parent_id",
                    "targetColumnName" : "id"
                }
            ]
        },
        {
            "modelId" : 1,
            "name" : "fk_one_to_one",
            "type" : "ONE_TO_ONE",
            "dissociateAction" : null,
            "updateAction" : "",
            "deleteAction" : "",
            "fake" : false,
            "sourceTableName" : "o_o_source",
            "targetTableName" : "o_o_target",
            "columnReferences" : [
                {
                    "sourceColumnName" : "target_id",
                    "targetColumnName" : "id"
                }
            ]
        },
        {
            "modelId" : 1,
            "name" : "fk_one_to_many",
            "type" : "ONE_TO_MANY",
            "dissociateAction" : null,
            "updateAction" : "",
            "deleteAction" : "",
            "fake" : false,
            "sourceTableName" : "o_m_source",
            "targetTableName" : "o_m_target",
            "columnReferences" : [
                {
                    "sourceColumnName" : "id",
                    "targetColumnName" : "source_id"
                }
            ]
        },
        {
            "modelId" : 1,
            "name" : "fk_many_to_one",
            "type" : "MANY_TO_ONE",
            "dissociateAction" : null,
            "updateAction" : "",
            "deleteAction" : "",
            "fake" : false,
            "sourceTableName" : "m_o_source",
            "targetTableName" : "m_o_target",
            "columnReferences" : [
                {
                    "sourceColumnName" : "source_id",
                    "targetColumnName" : "id"
                }
            ]
        },
        {
            "modelId" : 1,
            "name" : "many_to_many_mapping",
            "type" : "MANY_TO_MANY",
            "dissociateAction" : null,
            "updateAction" : "",
            "deleteAction" : "",
            "fake" : false,
            "sourceTableName" : "m_n_source",
            "targetTableName" : "m_n_target",
            "columnReferences" : [
                {
                    "sourceColumnName" : "id",
                    "targetColumnName" : "id"
                }
            ]
        }
    ]
}
"""

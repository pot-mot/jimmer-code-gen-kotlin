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
            "sourceTable" : {
                "modelId" : 1,
                "name" : "tree_node",
                "comment" : ""
            },
            "targetTable" : {
                "modelId" : 1,
                "name" : "tree_node",
                "comment" : ""
            },
            "columnReferences" : [
                {
                    "sourceColumn" : {
                        "name" : "parent_id",
                        "comment" : "",
                        "typeCode" : -5,
                        "rawType" : "BIGINT"
                    },
                    "targetColumn" : {
                        "name" : "id",
                        "comment" : "",
                        "typeCode" : -5,
                        "rawType" : "BIGINT"
                    }
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
            "sourceTable" : {
                "modelId" : 1,
                "name" : "o_o_source",
                "comment" : ""
            },
            "targetTable" : {
                "modelId" : 1,
                "name" : "o_o_target",
                "comment" : ""
            },
            "columnReferences" : [
                {
                    "sourceColumn" : {
                        "name" : "target_id",
                        "comment" : "",
                        "typeCode" : -5,
                        "rawType" : "BIGINT"
                    },
                    "targetColumn" : {
                        "name" : "id",
                        "comment" : "",
                        "typeCode" : -5,
                        "rawType" : "BIGINT"
                    }
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
            "sourceTable" : {
                "modelId" : 1,
                "name" : "o_m_source",
                "comment" : ""
            },
            "targetTable" : {
                "modelId" : 1,
                "name" : "o_m_target",
                "comment" : ""
            },
            "columnReferences" : [
                {
                    "sourceColumn" : {
                        "name" : "id",
                        "comment" : "",
                        "typeCode" : -5,
                        "rawType" : "BIGINT"
                    },
                    "targetColumn" : {
                        "name" : "source_id",
                        "comment" : "",
                        "typeCode" : -5,
                        "rawType" : "BIGINT"
                    }
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
            "sourceTable" : {
                "modelId" : 1,
                "name" : "m_o_source",
                "comment" : ""
            },
            "targetTable" : {
                "modelId" : 1,
                "name" : "m_o_target",
                "comment" : ""
            },
            "columnReferences" : [
                {
                    "sourceColumn" : {
                        "name" : "source_id",
                        "comment" : "",
                        "typeCode" : -5,
                        "rawType" : "BIGINT"
                    },
                    "targetColumn" : {
                        "name" : "id",
                        "comment" : "",
                        "typeCode" : -5,
                        "rawType" : "BIGINT"
                    }
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
            "sourceTable" : {
                "modelId" : 1,
                "name" : "m_n_source",
                "comment" : ""
            },
            "targetTable" : {
                "modelId" : 1,
                "name" : "m_n_target",
                "comment" : ""
            },
            "columnReferences" : [
                {
                    "sourceColumn" : {
                        "name" : "id",
                        "comment" : "",
                        "typeCode" : -5,
                        "rawType" : "BIGINT"
                    },
                    "targetColumn" : {
                        "name" : "id",
                        "comment" : "",
                        "typeCode" : -5,
                        "rawType" : "BIGINT"
                    }
                }
            ]
        }
    ]
}
"""

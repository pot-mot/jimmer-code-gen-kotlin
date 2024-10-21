package top.potmot.business.single

const val entityData = """
{
    "createdTime": "2024-10-13T14:31:29.734295",
    "modifiedTime": "2024-10-16T12:02:02.927149",
    "id": 392,
    "packagePath": "com.example.entity",
    "table": {
        "id": 738,
        "name": "condition_match",
        "type": "TABLE"
    },
    "superEntities": [],
    "name": "ConditionMatch",
    "comment": "条件匹配",
    "author": "potmot",
    "remark": "",
    "idProperties": [
        {
            "createdTime": "2024-10-13T14:31:29.734295",
            "modifiedTime": "2024-10-16T12:02:02.927149",
            "id": 1379,
            "column": {
                "id": 1339,
                "name": "id"
            },
            "entity": {
                "id": 392
            },
            "name": "id",
            "comment": "ID",
            "type": "kotlin.Int",
            "typeTable": null,
            "listType": false,
            "typeNotNull": true,
            "idProperty": true,
            "idGenerationAnnotation": "@GeneratedValue(strategy = GenerationType.IDENTITY)",
            "keyProperty": false,
            "logicalDelete": false,
            "idView": false,
            "idViewTarget": null,
            "associationType": null,
            "mappedBy": null,
            "inputNotNull": null,
            "joinColumnMetas": null,
            "joinTableMeta": null,
            "dissociateAnnotation": null,
            "otherAnnotation": null,
            "orderKey": 0,
            "remark": "",
            "enum": null
        }
    ],
    "properties": [
        {
            "createdTime": "2024-10-13T14:31:29.734295",
            "modifiedTime": "2024-10-16T12:02:02.927149",
            "id": 1379,
            "column": {
                "id": 1339,
                "name": "id"
            },
            "entity": {
                "id": 392
            },
            "name": "id",
            "comment": "ID",
            "type": "kotlin.Int",
            "typeTable": null,
            "listType": false,
            "typeNotNull": true,
            "idProperty": true,
            "idGenerationAnnotation": "@GeneratedValue(strategy = GenerationType.IDENTITY)",
            "keyProperty": false,
            "logicalDelete": false,
            "idView": false,
            "idViewTarget": null,
            "associationType": null,
            "mappedBy": null,
            "inputNotNull": null,
            "joinColumnMetas": null,
            "joinTableMeta": null,
            "dissociateAnnotation": null,
            "otherAnnotation": null,
            "orderKey": 0,
            "remark": "",
            "enum": null
        },
        {
            "createdTime": "2024-10-13T14:50:14.592448",
            "modifiedTime": "2024-10-16T12:02:02.927149",
            "id": 1447,
            "column": {
                "id": 1340,
                "name": "user_id"
            },
            "entity": {
                "id": 392
            },
            "name": "user",
            "comment": "用户",
            "type": "SysUser",
            "typeTable": {
                "entity": {
                    "id": 402,
                    "packagePath": "com.example.enum",
                    "name": "SysUser",
                    "comment": "用户",
                    "idProperties": [
                        {
                            "id": 387,
                            "name": "id",
                            "type": "kotlin.Int",
                            "typeNotNull": true
                        }
                    ]
                }
            },
            "listType": false,
            "typeNotNull": true,
            "idProperty": false,
            "idGenerationAnnotation": null,
            "keyProperty": false,
            "logicalDelete": false,
            "idView": false,
            "idViewTarget": null,
            "associationType": "MANY_TO_ONE",
            "mappedBy": null,
            "inputNotNull": null,
            "joinColumnMetas": [
                {
                    "foreignKeyType": "REAL",
                    "joinColumnName": "USER_ID",
                    "referencedColumnName": "ID"
                }
            ],
            "joinTableMeta": null,
            "dissociateAnnotation": null,
            "otherAnnotation": null,
            "orderKey": 1,
            "remark": "",
            "enum": null
        },
        {
            "createdTime": "2024-10-13T14:31:29.734295",
            "modifiedTime": "2024-10-16T12:02:02.927149",
            "id": 1380,
            "column": {
                "id": 1340,
                "name": "user_id"
            },
            "entity": {
                "id": 392
            },
            "name": "userId",
            "comment": "用户 ID View",
            "type": "kotlin.Int",
            "typeTable": null,
            "listType": false,
            "typeNotNull": true,
            "idProperty": false,
            "idGenerationAnnotation": null,
            "keyProperty": false,
            "logicalDelete": false,
            "idView": true,
            "idViewTarget": "user",
            "associationType": "MANY_TO_ONE",
            "mappedBy": null,
            "inputNotNull": null,
            "joinColumnMetas": null,
            "joinTableMeta": null,
            "dissociateAnnotation": null,
            "otherAnnotation": null,
            "orderKey": 2,
            "remark": "",
            "enum": null
        },
        {
            "createdTime": "2024-10-13T14:50:14.592448",
            "modifiedTime": "2024-10-16T12:02:02.927149",
            "id": 1449,
            "column": {
                "id": 1342,
                "name": "condition_id"
            },
            "entity": {
                "id": 392
            },
            "name": "condition",
            "comment": "条件",
            "type": "PolicyCondition",
            "typeTable": {
                "entity": {
                    "id": 387,
                    "packagePath": "com.example.enum",
                    "name": "PolicyCondition",
                    "comment": "条件",
                    "idProperties": [
                        {
                            "id": 387,
                            "name": "id",
                            "type": "kotlin.Int",
                            "typeNotNull": true
                        }
                    ]
                }
            },
            "listType": false,
            "typeNotNull": true,
            "idProperty": false,
            "idGenerationAnnotation": null,
            "keyProperty": false,
            "logicalDelete": false,
            "idView": false,
            "idViewTarget": null,
            "associationType": "MANY_TO_ONE",
            "mappedBy": null,
            "inputNotNull": null,
            "joinColumnMetas": [
                {
                    "foreignKeyType": "REAL",
                    "joinColumnName": "CONDITION_ID",
                    "referencedColumnName": "ID"
                }
            ],
            "joinTableMeta": null,
            "dissociateAnnotation": null,
            "otherAnnotation": null,
            "orderKey": 3,
            "remark": "",
            "enum": null
        },
        {
            "createdTime": "2024-10-13T14:31:29.734295",
            "modifiedTime": "2024-10-16T12:02:02.927149",
            "id": 1382,
            "column": {
                "id": 1342,
                "name": "condition_id"
            },
            "entity": {
                "id": 392
            },
            "name": "conditionId",
            "comment": "条件 ID View",
            "type": "kotlin.Int",
            "typeTable": null,
            "listType": false,
            "typeNotNull": true,
            "idProperty": false,
            "idGenerationAnnotation": null,
            "keyProperty": false,
            "logicalDelete": false,
            "idView": true,
            "idViewTarget": "condition",
            "associationType": "MANY_TO_ONE",
            "mappedBy": null,
            "inputNotNull": null,
            "joinColumnMetas": null,
            "joinTableMeta": null,
            "dissociateAnnotation": null,
            "otherAnnotation": null,
            "orderKey": 4,
            "remark": "",
            "enum": null
        },
        {
            "createdTime": "2024-10-13T15:08:45.056208",
            "modifiedTime": "2024-10-16T12:02:02.927149",
            "id": 1512,
            "column": {
                "id": 1424,
                "name": "status"
            },
            "entity": {
                "id": 392
            },
            "name": "status",
            "comment": "匹配状态",
            "type": "kotlin.String",
            "typeTable": null,
            "listType": false,
            "typeNotNull": true,
            "idProperty": false,
            "idGenerationAnnotation": null,
            "keyProperty": false,
            "logicalDelete": false,
            "idView": false,
            "idViewTarget": null,
            "associationType": null,
            "mappedBy": null,
            "inputNotNull": null,
            "joinColumnMetas": null,
            "joinTableMeta": null,
            "dissociateAnnotation": null,
            "otherAnnotation": null,
            "orderKey": 5,
            "remark": "",
            "enum": {
                "id": 62,
                "packagePath": "com.example.enum",
                "name": "MatchStatus",
                "comment": ""
            }
        },
        {
            "createdTime": "2024-10-13T14:50:14.592448",
            "modifiedTime": "2024-10-16T12:02:02.927149",
            "id": 1450,
            "column": {
                "id": 1416,
                "name": "date"
            },
            "entity": {
                "id": 392
            },
            "name": "date",
            "comment": "匹配日期",
            "type": "java.time.LocalDateTime",
            "typeTable": null,
            "listType": false,
            "typeNotNull": true,
            "idProperty": false,
            "idGenerationAnnotation": null,
            "keyProperty": false,
            "logicalDelete": false,
            "idView": false,
            "idViewTarget": null,
            "associationType": null,
            "mappedBy": null,
            "inputNotNull": null,
            "joinColumnMetas": null,
            "joinTableMeta": null,
            "dissociateAnnotation": null,
            "otherAnnotation": null,
            "orderKey": 6,
            "remark": "",
            "enum": null
        },
        {
            "createdTime": "2024-10-14T20:45:27.365278",
            "modifiedTime": "2024-10-16T12:02:02.927149",
            "id": 1768,
            "column": {
                "id": 1472,
                "name": "description"
            },
            "entity": {
                "id": 392
            },
            "name": "description",
            "comment": "结果描述",
            "type": "kotlin.String",
            "typeTable": null,
            "listType": false,
            "typeNotNull": true,
            "idProperty": false,
            "idGenerationAnnotation": null,
            "keyProperty": false,
            "logicalDelete": false,
            "idView": false,
            "idViewTarget": null,
            "associationType": null,
            "mappedBy": null,
            "inputNotNull": null,
            "joinColumnMetas": null,
            "joinTableMeta": null,
            "dissociateAnnotation": null,
            "otherAnnotation": null,
            "orderKey": 7,
            "remark": "",
            "enum": null
        }
    ]
}
"""
package top.potmot.model.superTable

const val GRAPH_DATA = """
{
    "json": {
        "cells": [
            {
                "shape": "TABLE_NODE",
                "data": {
                    "table": {
                        "name": "base_entity",
                        "comment": "",
                        "remark": "",
                        "type": "SUPER_TABLE",
                        "superTables": [],
                        "columns": [
                            {
                                "orderKey": 1,
                                "name": "create_user_id",
                                "comment": "",
                                "typeCode": -5,
                                "overwriteByRaw": false,
                                "rawType": "BIGINT",
                                "typeNotNull": true,
                                "dataSize": 0,
                                "numericPrecision": 0,
                                "partOfPk": false,
                                "autoIncrement": false,
                                "remark": "",
                                "logicalDelete": false,
                                "businessKey": false
                            },
                            {
                                "orderKey": 2,
                                "name": "modify_user_id",
                                "comment": "",
                                "typeCode": -5,
                                "overwriteByRaw": false,
                                "rawType": "BIGINT",
                                "typeNotNull": true,
                                "dataSize": 0,
                                "numericPrecision": 0,
                                "partOfPk": false,
                                "autoIncrement": false,
                                "remark": "",
                                "logicalDelete": false,
                                "businessKey": false
                            }
                        ],
                        "indexes": []
                    }
                }
            },
            {
                "shape": "TABLE_NODE",
                "data": {
                    "table": {
                        "name": "user",
                        "comment": "",
                        "remark": "",
                        "type": "TABLE",
                        "superTables": [
                            {
                                "name": "base_entity"
                            }
                        ],
                        "columns": [
                            {
                                "orderKey": 1,
                                "name": "id",
                                "comment": "",
                                "typeCode": -5,
                                "overwriteByRaw": false,
                                "rawType": "BIGINT",
                                "typeNotNull": true,
                                "dataSize": 0,
                                "numericPrecision": 0,
                                "partOfPk": true,
                                "autoIncrement": false,
                                "remark": "",
                                "logicalDelete": false,
                                "businessKey": false
                            },
                            {
                                "orderKey": 2,
                                "name": "name",
                                "comment": "",
                                "typeCode": 12,
                                "overwriteByRaw": false,
                                "rawType": "VARCHAR",
                                "typeNotNull": true,
                                "dataSize": 0,
                                "numericPrecision": 0,
                                "partOfPk": false,
                                "autoIncrement": false,
                                "remark": "",
                                "logicalDelete": false,
                                "businessKey": false
                            }
                        ],
                        "indexes": []
                    }
                }
            },
            {
                "shape": "TABLE_NODE",
                "data": {
                    "table": {
                        "name": "resource",
                        "comment": "",
                        "remark": "",
                        "type": "TABLE",
                        "superTables": [
                            {
                                "name": "base_entity"
                            }
                        ],
                        "columns": [
                            {
                                "orderKey": 1,
                                "name": "id",
                                "comment": "",
                                "typeCode": -5,
                                "overwriteByRaw": false,
                                "rawType": "BIGINT",
                                "typeNotNull": true,
                                "dataSize": 0,
                                "numericPrecision": 0,
                                "partOfPk": true,
                                "autoIncrement": false,
                                "remark": "",
                                "logicalDelete": false,
                                "businessKey": false
                            },
                            {
                                "orderKey": 2,
                                "name": "name",
                                "comment": "",
                                "typeCode": 12,
                                "overwriteByRaw": false,
                                "rawType": "VARCHAR",
                                "typeNotNull": true,
                                "dataSize": 0,
                                "numericPrecision": 0,
                                "partOfPk": false,
                                "autoIncrement": false,
                                "remark": "",
                                "logicalDelete": false,
                                "businessKey": false
                            },
                            {
                                "orderKey": 3,
                                "name": "user_id",
                                "comment": "",
                                "typeCode": -5,
                                "overwriteByRaw": false,
                                "rawType": "BIGINT",
                                "typeNotNull": true,
                                "dataSize": 0,
                                "numericPrecision": 0,
                                "partOfPk": false,
                                "autoIncrement": false,
                                "remark": "",
                                "logicalDelete": false,
                                "businessKey": false
                            }
                        ],
                        "indexes": []
                    }
                }
            },
            {
                "shape": "ASSOCIATION_EDGE",
                "data": {
                    "association": {
                        "type": "MANY_TO_ONE",
                        "fake": false,
                        "name": "fk_base_create_user",
                        "sourceTableName": "base_entity",
                        "targetTableName": "user",
                        "columnReferences": [
                            {
                                "sourceColumnName": "create_user_id",
                                "targetColumnName": "id"
                            }
                        ],
                        "updateAction": "",
                        "deleteAction": ""
                    }
                }
            },
            {
                "shape": "ASSOCIATION_EDGE",
                "data": {
                    "association": {
                        "type": "MANY_TO_ONE",
                        "fake": false,
                        "name": "fk_base_modify_user",
                        "sourceTableName": "base_entity",
                        "targetTableName": "user",
                        "columnReferences": [
                            {
                                "sourceColumnName": "modify_user_id",
                                "targetColumnName": "id"
                            }
                        ],
                        "updateAction": "",
                        "deleteAction": ""
                    }
                }
            },
            {
                "shape": "ASSOCIATION_EDGE",
                "data": {
                    "association": {
                        "type": "MANY_TO_ONE",
                        "fake": false,
                        "name": "fk_resource_user",
                        "sourceTableName": "resource",
                        "targetTableName": "user",
                        "columnReferences": [
                            {
                                "sourceColumnName": "user_id",
                                "targetColumnName": "id"
                            }
                        ],
                        "updateAction": "",
                        "deleteAction": ""
                    }
                }
            }
        ]
    },
    "zoom": 0.65,
    "transform": "matrix(0.65,0,0,0.65,-83.27111949580353,24.007823542914537)"
}
"""

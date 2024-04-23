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
                        "sourceTable": {
                            "name": "base_entity",
                            "comment": ""
                        },
                        "targetTable": {
                            "name": "user",
                            "comment": ""
                        },
                        "columnReferences": [
                            {
                                "sourceColumn": {
                                    "name": "create_user_id",
                                    "comment": "",
                                    "rawType": "BIGINT",
                                    "typeCode": -5
                                },
                                "targetColumn": {
                                    "name": "id",
                                    "comment": "",
                                    "rawType": "BIGINT",
                                    "typeCode": -5
                                }
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
                        "sourceTable": {
                            "name": "base_entity",
                            "comment": ""
                        },
                        "targetTable": {
                            "name": "user",
                            "comment": ""
                        },
                        "columnReferences": [
                            {
                                "sourceColumn": {
                                    "name": "modify_user_id",
                                    "comment": "",
                                    "rawType": "BIGINT",
                                    "typeCode": -5
                                },
                                "targetColumn": {
                                    "name": "id",
                                    "comment": "",
                                    "rawType": "BIGINT",
                                    "typeCode": -5
                                }
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
                        "sourceTable": {
                            "name": "resource",
                            "comment": ""
                        },
                        "targetTable": {
                            "name": "user",
                            "comment": ""
                        },
                        "columnReferences": [
                            {
                                "sourceColumn": {
                                    "name": "user_id",
                                    "comment": "",
                                    "rawType": "BIGINT",
                                    "typeCode": -5
                                },
                                "targetColumn": {
                                    "name": "id",
                                    "comment": "",
                                    "rawType": "BIGINT",
                                    "typeCode": -5
                                }
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

package top.potmot.model.superTable

const val GRAPH_DATA = """
{
    "json": {
        "cells": [
            {
                "shape": "TABLE_NODE",
                "data": {
                    "table": {
                        "name": "m_o_source",
                        "comment": "",
                        "remark": "",
                        "type": "TABLE",
                        "superTables": [
                            {
                                "name": "base_entity1"
                            },
                            {
                                "name": "base_entity2"
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
                                "autoIncrement": true,
                                "remark": "",
                                "logicalDelete": false,
                                "businessKey": false
                            },
                            {
                                "orderKey": 2,
                                "name": "source_id",
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
                        "name": "m_o_target",
                        "comment": "",
                        "remark": "",
                        "type": "TABLE",
                        "superTables": [
                            {
                                "name": "base_entity1"
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
                                "autoIncrement": true,
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
                        "updateAction": "",
                        "deleteAction": "",
                        "name": "fk_many_to_one",
                        "sourceTable": {
                            "name": "m_o_source",
                            "comment": ""
                        },
                        "targetTable": {
                            "name": "m_o_target",
                            "comment": ""
                        },
                        "columnReferences": [
                            {
                                "sourceColumn": {
                                    "name": "source_id",
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
                        ]
                    }
                }
            },
            {
                "shape": "TABLE_NODE",
                "data": {
                    "table": {
                        "name": "base_entity1",
                        "comment": "",
                        "remark": "",
                        "type": "SUPER_TABLE",
                        "columns": [
                            {
                                "orderKey": 1,
                                "name": "create_time",
                                "comment": "",
                                "typeCode": 93,
                                "overwriteByRaw": false,
                                "rawType": "TIMESTAMP",
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
                        "name": "base_entity2",
                        "comment": "",
                        "remark": "",
                        "type": "SUPER_TABLE",
                        "columns": [
                            {
                                "orderKey": 1,
                                "name": "update_time",
                                "comment": "",
                                "typeCode": 93,
                                "overwriteByRaw": false,
                                "rawType": "TIMESTAMP",
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
            }
        ]
    },
    "zoom": 4,
    "transform": "matrix(2.1122448144521115,0,0,2.1122448144521115,-1278.0767645837177,175.372451204267)"
}
"""

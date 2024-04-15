package top.potmot.model.enums

const val ENUM_TABLE = """
{
    "json": {
        "cells": [
            {
                "shape": "TABLE_NODE",
                "data": {
                    "table": {
                        "name": "enum_table",
                        "comment": "",
                        "remark": "",
                        "type": "TABLE",
                        "superTables": [],
                        "columns": [
                            {
                                "orderKey": 1,
                                "name": "enum_common",
                                "comment": "",
                                "typeCode": 12,
                                "overwriteByRaw": false,
                                "rawType": "VARCHAR",
                                "typeNotNull": false,
                                "dataSize": 500,
                                "numericPrecision": 0,
                                "partOfPk": false,
                                "autoIncrement": false,
                                "remark": "",
                                "logicalDelete": false,
                                "businessKey": false,
                                "enum": {
                                    "name": "EnumCommon"
                                }
                            },
                            {
                                "orderKey": 2,
                                "name": "enum_ordinal",
                                "comment": "",
                                "typeCode": -5,
                                "overwriteByRaw": false,
                                "rawType": "BIGINT",
                                "typeNotNull": true,
                                "dataSize": 11,
                                "numericPrecision": 0,
                                "partOfPk": false,
                                "autoIncrement": false,
                                "remark": "",
                                "logicalDelete": false,
                                "businessKey": false,
                                "enum": {
                                    "name": "EnumOrdinal"
                                }
                            },
                            {
                                "orderKey": 3,
                                "name": "enum_name",
                                "comment": "",
                                "typeCode": 12,
                                "overwriteByRaw": false,
                                "rawType": "VARCHAR",
                                "typeNotNull": true,
                                "dataSize": 500,
                                "numericPrecision": 0,
                                "partOfPk": false,
                                "autoIncrement": false,
                                "remark": "",
                                "logicalDelete": false,
                                "businessKey": false,
                                "enum": {
                                    "name": "EnumName"
                                }
                            }
                        ],
                        "indexes": []
                    }
                },
                "zIndex": 1
            }
        ]
    },
    "zoom": 0.7000000000000001,
    "transform": "matrix(0.7000000000000001,0,0,0.7000000000000001,51.84998917324185,202.49998082433396)"
}
"""

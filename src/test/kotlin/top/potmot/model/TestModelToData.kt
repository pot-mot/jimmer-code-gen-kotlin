package top.potmot.model

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.model.extension.valueToData

@SpringBootTest
@ActiveProfiles("test-kotlin", "mysql")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TestModelToData {
    @Test
    @Order(1)
    fun testEmptyData() {
        println(valueToData(1, emptyValue))
    }

    @Test
    @Order(2)
    fun testValueData() {
        println(valueToData(1, modelValue))
    }

    private val emptyValue = """
        {}
    """.trimIndent()

    private val modelValue = """
        {
            "json": {
                "cells": [
                    {
                        "position": {
                            "x": 9138.6396484375,
                            "y": 3911.762939453125
                        },
                        "size": {
                            "width": 285,
                            "height": 394
                        },
                        "view": "vue-shape-view",
                        "shape": "model",
                        "id": "97e63e91-b085-4d6a-8e8c-5e0bb0de4dd4",
                        "data": {
                            "table": {
                                "comment": "生成数据源",
                                "name": "gen_data_source",
                                "orderKey": 0,
                                "remark": "",
                                "type": "TABLE",
                                "columns": [
                                    {
                                        "autoIncrement": true,
                                        "comment": "ID",
                                        "defaultValue": null,
                                        "displaySize": 19,
                                        "name": "id",
                                        "numericPrecision": 0,
                                        "orderKey": 0,
                                        "partOfFk": false,
                                        "partOfPk": true,
                                        "partOfUniqueIdx": true,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "数据库类型",
                                        "defaultValue": null,
                                        "displaySize": 500,
                                        "name": "type",
                                        "numericPrecision": 0,
                                        "orderKey": 1,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "名称",
                                        "defaultValue": null,
                                        "displaySize": 500,
                                        "name": "name",
                                        "numericPrecision": 0,
                                        "orderKey": 2,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "主机",
                                        "defaultValue": null,
                                        "displaySize": 500,
                                        "name": "host",
                                        "numericPrecision": 0,
                                        "orderKey": 3,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "端口",
                                        "defaultValue": null,
                                        "displaySize": 500,
                                        "name": "port",
                                        "numericPrecision": 0,
                                        "orderKey": 4,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "链接后缀",
                                        "defaultValue": null,
                                        "displaySize": 500,
                                        "name": "url_suffix",
                                        "numericPrecision": 0,
                                        "orderKey": 5,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "用户名",
                                        "defaultValue": null,
                                        "displaySize": 500,
                                        "name": "username",
                                        "numericPrecision": 0,
                                        "orderKey": 6,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "密码",
                                        "defaultValue": null,
                                        "displaySize": 500,
                                        "name": "password",
                                        "numericPrecision": 0,
                                        "orderKey": 7,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "自定排序",
                                        "defaultValue": "0",
                                        "displaySize": 19,
                                        "name": "order_key",
                                        "numericPrecision": 0,
                                        "orderKey": 8,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "创建时间",
                                        "defaultValue": "CURRENT_TIMESTAMP",
                                        "displaySize": 19,
                                        "name": "created_time",
                                        "numericPrecision": 0,
                                        "orderKey": 9,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "DATETIME",
                                        "typeCode": 93,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "修改时间",
                                        "defaultValue": "CURRENT_TIMESTAMP",
                                        "displaySize": 19,
                                        "name": "modified_time",
                                        "numericPrecision": 0,
                                        "orderKey": 10,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "DATETIME",
                                        "typeCode": 93,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "备注",
                                        "defaultValue": "",
                                        "displaySize": 500,
                                        "name": "remark",
                                        "numericPrecision": 0,
                                        "orderKey": 11,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    }
                                ]
                            },
                            "wrapper": {
                                "__v_isShallow": false,
                                "__v_isRef": true,
                                "_rawValue": {},
                                "_value": {}
                            }
                        },
                        "ports": {
                            "groups": {
                                "COLUMN_PORT_GROUP": {
                                    "position": "COLUMN_PORT",
                                    "markup": [
                                        {
                                            "tagName": "rect",
                                            "selector": "COLUMN_PORT_SELECTOR"
                                        }
                                    ],
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "magnet": true,
                                            "fill": "rgba(0,0,0,0)",
                                            "height": 30,
                                            "width": 200
                                        }
                                    }
                                }
                            },
                            "items": [
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": true,
                                            "comment": "ID",
                                            "defaultValue": null,
                                            "displaySize": 19,
                                            "name": "id",
                                            "numericPrecision": 0,
                                            "orderKey": 0,
                                            "partOfFk": false,
                                            "partOfPk": true,
                                            "partOfUniqueIdx": true,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "eeb0f173-178d-4ed3-a13b-e63f309d6eda",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "数据库类型",
                                            "defaultValue": null,
                                            "displaySize": 500,
                                            "name": "type",
                                            "numericPrecision": 0,
                                            "orderKey": 1,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "5a5dc5a7-ee54-4b22-8875-e11abe0034df",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "名称",
                                            "defaultValue": null,
                                            "displaySize": 500,
                                            "name": "name",
                                            "numericPrecision": 0,
                                            "orderKey": 2,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "110f73e2-cef0-45c8-959b-54bf91e80089",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "主机",
                                            "defaultValue": null,
                                            "displaySize": 500,
                                            "name": "host",
                                            "numericPrecision": 0,
                                            "orderKey": 3,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "d4d3bfc9-b382-464c-8be6-a4371faaf152",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "端口",
                                            "defaultValue": null,
                                            "displaySize": 500,
                                            "name": "port",
                                            "numericPrecision": 0,
                                            "orderKey": 4,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "dd9668ee-4899-46e7-8055-50c042006067",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "链接后缀",
                                            "defaultValue": null,
                                            "displaySize": 500,
                                            "name": "url_suffix",
                                            "numericPrecision": 0,
                                            "orderKey": 5,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "6895ac2a-25a8-4532-b271-5da707d2fe73",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "用户名",
                                            "defaultValue": null,
                                            "displaySize": 500,
                                            "name": "username",
                                            "numericPrecision": 0,
                                            "orderKey": 6,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "1f464cc9-6da3-4d2a-a1c0-47c5c67b47f9",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "密码",
                                            "defaultValue": null,
                                            "displaySize": 500,
                                            "name": "password",
                                            "numericPrecision": 0,
                                            "orderKey": 7,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "aa2e3a49-4ba7-4d05-a33f-8e74eaf4eeb6",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "自定排序",
                                            "defaultValue": "0",
                                            "displaySize": 19,
                                            "name": "order_key",
                                            "numericPrecision": 0,
                                            "orderKey": 8,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "04a179b5-9db7-4c7d-afa9-2ad033fd8dc1",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "创建时间",
                                            "defaultValue": "CURRENT_TIMESTAMP",
                                            "displaySize": 19,
                                            "name": "created_time",
                                            "numericPrecision": 0,
                                            "orderKey": 9,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "DATETIME",
                                            "typeCode": 93,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "5dd3e313-ed8d-49a6-b122-59766181fd7c",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "修改时间",
                                            "defaultValue": "CURRENT_TIMESTAMP",
                                            "displaySize": 19,
                                            "name": "modified_time",
                                            "numericPrecision": 0,
                                            "orderKey": 10,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "DATETIME",
                                            "typeCode": 93,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "79bd3d15-1e81-4e56-9ad2-9412232aa3cf",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "备注",
                                            "defaultValue": "",
                                            "displaySize": 500,
                                            "name": "remark",
                                            "numericPrecision": 0,
                                            "orderKey": 11,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "45f723f8-78bb-48af-b0f5-e3a596d9bd54",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                }
                            ]
                        },
                        "zIndex": 30
                    },
                    {
                        "position": {
                            "x": 9138.6396484375,
                            "y": 4405.762939453125
                        },
                        "size": {
                            "width": 291,
                            "height": 214
                        },
                        "view": "vue-shape-view",
                        "shape": "model",
                        "id": "ec6f4d80-4718-4700-965e-cecbd87c8bf4",
                        "data": {
                            "table": {
                                "comment": "生成模型",
                                "name": "gen_model",
                                "orderKey": 0,
                                "remark": "",
                                "type": "TABLE",
                                "columns": [
                                    {
                                        "autoIncrement": true,
                                        "comment": "ID",
                                        "defaultValue": null,
                                        "displaySize": 19,
                                        "name": "id",
                                        "numericPrecision": 0,
                                        "orderKey": 0,
                                        "partOfFk": false,
                                        "partOfPk": true,
                                        "partOfUniqueIdx": true,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "名称",
                                        "defaultValue": null,
                                        "displaySize": 500,
                                        "name": "name",
                                        "numericPrecision": 0,
                                        "orderKey": 1,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "模型 JSON 数据",
                                        "defaultValue": null,
                                        "displaySize": 2147483647,
                                        "name": "value",
                                        "numericPrecision": 0,
                                        "orderKey": 2,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "LONGTEXT",
                                        "typeCode": -1,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "创建时间",
                                        "defaultValue": "CURRENT_TIMESTAMP",
                                        "displaySize": 19,
                                        "name": "created_time",
                                        "numericPrecision": 0,
                                        "orderKey": 3,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "DATETIME",
                                        "typeCode": 93,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "修改时间",
                                        "defaultValue": "CURRENT_TIMESTAMP",
                                        "displaySize": 19,
                                        "name": "modified_time",
                                        "numericPrecision": 0,
                                        "orderKey": 4,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "DATETIME",
                                        "typeCode": 93,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "备注",
                                        "defaultValue": "",
                                        "displaySize": 500,
                                        "name": "remark",
                                        "numericPrecision": 0,
                                        "orderKey": 5,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    }
                                ]
                            },
                            "wrapper": {
                                "__v_isShallow": false,
                                "__v_isRef": true,
                                "_rawValue": {},
                                "_value": {}
                            }
                        },
                        "ports": {
                            "groups": {
                                "COLUMN_PORT_GROUP": {
                                    "position": "COLUMN_PORT",
                                    "markup": [
                                        {
                                            "tagName": "rect",
                                            "selector": "COLUMN_PORT_SELECTOR"
                                        }
                                    ],
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "magnet": true,
                                            "fill": "rgba(0,0,0,0)",
                                            "height": 30,
                                            "width": 200
                                        }
                                    }
                                }
                            },
                            "items": [
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": true,
                                            "comment": "ID",
                                            "defaultValue": null,
                                            "displaySize": 19,
                                            "name": "id",
                                            "numericPrecision": 0,
                                            "orderKey": 0,
                                            "partOfFk": false,
                                            "partOfPk": true,
                                            "partOfUniqueIdx": true,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "b436dcf2-7bb8-4a72-93a6-677de8d7bcbe",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 291
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "名称",
                                            "defaultValue": null,
                                            "displaySize": 500,
                                            "name": "name",
                                            "numericPrecision": 0,
                                            "orderKey": 1,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "03284939-3a28-41a0-9702-174e3d320387",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 291
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "模型 JSON 数据",
                                            "defaultValue": null,
                                            "displaySize": 2147483647,
                                            "name": "value",
                                            "numericPrecision": 0,
                                            "orderKey": 2,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "LONGTEXT",
                                            "typeCode": -1,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "5f313c1d-70cb-49f9-9fee-e24ea523d51b",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 291
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "创建时间",
                                            "defaultValue": "CURRENT_TIMESTAMP",
                                            "displaySize": 19,
                                            "name": "created_time",
                                            "numericPrecision": 0,
                                            "orderKey": 3,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "DATETIME",
                                            "typeCode": 93,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "a63b76d7-9055-472c-9347-12cc5e8fd004",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 291
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "修改时间",
                                            "defaultValue": "CURRENT_TIMESTAMP",
                                            "displaySize": 19,
                                            "name": "modified_time",
                                            "numericPrecision": 0,
                                            "orderKey": 4,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "DATETIME",
                                            "typeCode": 93,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "1a541205-173b-488a-8602-76e9bddde698",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 291
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "备注",
                                            "defaultValue": "",
                                            "displaySize": 500,
                                            "name": "remark",
                                            "numericPrecision": 0,
                                            "orderKey": 5,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "f1f3e8db-9c95-4eaa-a7c0-530b7c1a268a",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 291
                                        }
                                    }
                                }
                            ]
                        },
                        "zIndex": 32
                    },
                    {
                        "position": {
                            "x": 9138.6396484375,
                            "y": 4719.762939453125
                        },
                        "size": {
                            "width": 285,
                            "height": 244
                        },
                        "view": "vue-shape-view",
                        "shape": "model",
                        "id": "a538e2be-44a5-49de-b08f-cbadeb2ec1aa",
                        "data": {
                            "table": {
                                "comment": "生成包",
                                "name": "gen_package",
                                "orderKey": 0,
                                "remark": "",
                                "type": "TABLE",
                                "columns": [
                                    {
                                        "autoIncrement": true,
                                        "comment": "ID",
                                        "defaultValue": null,
                                        "displaySize": 19,
                                        "name": "id",
                                        "numericPrecision": 0,
                                        "orderKey": 0,
                                        "partOfFk": false,
                                        "partOfPk": true,
                                        "partOfUniqueIdx": true,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "父包",
                                        "defaultValue": null,
                                        "displaySize": 19,
                                        "name": "parent_id",
                                        "numericPrecision": 0,
                                        "orderKey": 1,
                                        "partOfFk": true,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": false,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "包名称",
                                        "defaultValue": null,
                                        "displaySize": 500,
                                        "name": "name",
                                        "numericPrecision": 0,
                                        "orderKey": 2,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "自定排序",
                                        "defaultValue": "0",
                                        "displaySize": 19,
                                        "name": "order_key",
                                        "numericPrecision": 0,
                                        "orderKey": 3,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "创建时间",
                                        "defaultValue": "CURRENT_TIMESTAMP",
                                        "displaySize": 19,
                                        "name": "created_time",
                                        "numericPrecision": 0,
                                        "orderKey": 4,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "DATETIME",
                                        "typeCode": 93,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "修改时间",
                                        "defaultValue": "CURRENT_TIMESTAMP",
                                        "displaySize": 19,
                                        "name": "modified_time",
                                        "numericPrecision": 0,
                                        "orderKey": 5,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "DATETIME",
                                        "typeCode": 93,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "备注",
                                        "defaultValue": "",
                                        "displaySize": 500,
                                        "name": "remark",
                                        "numericPrecision": 0,
                                        "orderKey": 6,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    }
                                ]
                            },
                            "wrapper": {
                                "__v_isShallow": false,
                                "__v_isRef": true,
                                "_rawValue": {},
                                "_value": {}
                            }
                        },
                        "ports": {
                            "groups": {
                                "COLUMN_PORT_GROUP": {
                                    "position": "COLUMN_PORT",
                                    "markup": [
                                        {
                                            "tagName": "rect",
                                            "selector": "COLUMN_PORT_SELECTOR"
                                        }
                                    ],
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "magnet": true,
                                            "fill": "rgba(0,0,0,0)",
                                            "height": 30,
                                            "width": 200
                                        }
                                    }
                                }
                            },
                            "items": [
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": true,
                                            "comment": "ID",
                                            "defaultValue": null,
                                            "displaySize": 19,
                                            "name": "id",
                                            "numericPrecision": 0,
                                            "orderKey": 0,
                                            "partOfFk": false,
                                            "partOfPk": true,
                                            "partOfUniqueIdx": true,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "9baeca5d-5955-462d-9669-fba15a7b42d5",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "父包",
                                            "defaultValue": null,
                                            "displaySize": 19,
                                            "name": "parent_id",
                                            "numericPrecision": 0,
                                            "orderKey": 1,
                                            "partOfFk": true,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": false,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "7ee2608d-0fdc-48de-aa6f-06132f1064fa",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "包名称",
                                            "defaultValue": null,
                                            "displaySize": 500,
                                            "name": "name",
                                            "numericPrecision": 0,
                                            "orderKey": 2,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "bb4fb120-1857-441c-b864-d5c58fbcf5e4",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "自定排序",
                                            "defaultValue": "0",
                                            "displaySize": 19,
                                            "name": "order_key",
                                            "numericPrecision": 0,
                                            "orderKey": 3,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "d8d28837-6d62-4b89-a2f1-d9e740e2fb3b",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "创建时间",
                                            "defaultValue": "CURRENT_TIMESTAMP",
                                            "displaySize": 19,
                                            "name": "created_time",
                                            "numericPrecision": 0,
                                            "orderKey": 4,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "DATETIME",
                                            "typeCode": 93,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "caf885f6-294c-4270-8456-20614836f051",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "修改时间",
                                            "defaultValue": "CURRENT_TIMESTAMP",
                                            "displaySize": 19,
                                            "name": "modified_time",
                                            "numericPrecision": 0,
                                            "orderKey": 5,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "DATETIME",
                                            "typeCode": 93,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "1ed9d42b-809f-44e4-b058-14a57ccf5883",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "备注",
                                            "defaultValue": "",
                                            "displaySize": 500,
                                            "name": "remark",
                                            "numericPrecision": 0,
                                            "orderKey": 6,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "fdfc5dd8-1973-4c03-9143-102bf73530c0",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                }
                            ]
                        },
                        "zIndex": 35,
                        "children": [
                            "7128ad7c-0675-4ec7-8a88-91620f4e443e"
                        ]
                    },
                    {
                        "position": {
                            "x": 11702.6396484375,
                            "y": 3911.762939453125
                        },
                        "size": {
                            "width": 326,
                            "height": 274
                        },
                        "view": "vue-shape-view",
                        "shape": "model",
                        "id": "08467857-9008-43ad-987c-10ccd9eeaaca",
                        "data": {
                            "table": {
                                "comment": "列到属性类型映射",
                                "name": "gen_type_mapping",
                                "orderKey": 0,
                                "remark": "",
                                "type": "TABLE",
                                "columns": [
                                    {
                                        "autoIncrement": true,
                                        "comment": "ID",
                                        "defaultValue": null,
                                        "displaySize": 19,
                                        "name": "id",
                                        "numericPrecision": 0,
                                        "orderKey": 0,
                                        "partOfFk": false,
                                        "partOfPk": true,
                                        "partOfUniqueIdx": true,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "类型表达式",
                                        "defaultValue": null,
                                        "displaySize": 500,
                                        "name": "type_expression",
                                        "numericPrecision": 0,
                                        "orderKey": 1,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "是否正则",
                                        "defaultValue": "0",
                                        "displaySize": 1,
                                        "name": "regex",
                                        "numericPrecision": 0,
                                        "orderKey": 2,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIT",
                                        "typeCode": -7,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "属性类型",
                                        "defaultValue": null,
                                        "displaySize": 500,
                                        "name": "property_type",
                                        "numericPrecision": 0,
                                        "orderKey": 3,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "自定排序",
                                        "defaultValue": "0",
                                        "displaySize": 19,
                                        "name": "order_key",
                                        "numericPrecision": 0,
                                        "orderKey": 4,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "创建时间",
                                        "defaultValue": "CURRENT_TIMESTAMP",
                                        "displaySize": 19,
                                        "name": "created_time",
                                        "numericPrecision": 0,
                                        "orderKey": 5,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "DATETIME",
                                        "typeCode": 93,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "修改时间",
                                        "defaultValue": "CURRENT_TIMESTAMP",
                                        "displaySize": 19,
                                        "name": "modified_time",
                                        "numericPrecision": 0,
                                        "orderKey": 6,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "DATETIME",
                                        "typeCode": 93,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "备注",
                                        "defaultValue": "",
                                        "displaySize": 500,
                                        "name": "remark",
                                        "numericPrecision": 0,
                                        "orderKey": 7,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    }
                                ]
                            },
                            "wrapper": {
                                "__v_isShallow": false,
                                "__v_isRef": true,
                                "_rawValue": {},
                                "_value": {}
                            }
                        },
                        "ports": {
                            "groups": {
                                "COLUMN_PORT_GROUP": {
                                    "position": "COLUMN_PORT",
                                    "markup": [
                                        {
                                            "tagName": "rect",
                                            "selector": "COLUMN_PORT_SELECTOR"
                                        }
                                    ],
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "magnet": true,
                                            "fill": "rgba(0,0,0,0)",
                                            "height": 30,
                                            "width": 200
                                        }
                                    }
                                }
                            },
                            "items": [
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": true,
                                            "comment": "ID",
                                            "defaultValue": null,
                                            "displaySize": 19,
                                            "name": "id",
                                            "numericPrecision": 0,
                                            "orderKey": 0,
                                            "partOfFk": false,
                                            "partOfPk": true,
                                            "partOfUniqueIdx": true,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "4a229c4c-b699-4d8c-9e41-ce368affb58c",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 326
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "类型表达式",
                                            "defaultValue": null,
                                            "displaySize": 500,
                                            "name": "type_expression",
                                            "numericPrecision": 0,
                                            "orderKey": 1,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "9ffbd215-1d6b-4198-b6df-4487db16bd11",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 326
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "是否正则",
                                            "defaultValue": "0",
                                            "displaySize": 1,
                                            "name": "regex",
                                            "numericPrecision": 0,
                                            "orderKey": 2,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIT",
                                            "typeCode": -7,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "d8f92084-d113-4e74-9979-1ae2011e0aa5",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 326
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "属性类型",
                                            "defaultValue": null,
                                            "displaySize": 500,
                                            "name": "property_type",
                                            "numericPrecision": 0,
                                            "orderKey": 3,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "5d8eee65-e59b-420f-b29a-6b7eb0b9d627",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 326
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "自定排序",
                                            "defaultValue": "0",
                                            "displaySize": 19,
                                            "name": "order_key",
                                            "numericPrecision": 0,
                                            "orderKey": 4,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "88a924da-4f94-4396-9c94-27a4180a3d84",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 326
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "创建时间",
                                            "defaultValue": "CURRENT_TIMESTAMP",
                                            "displaySize": 19,
                                            "name": "created_time",
                                            "numericPrecision": 0,
                                            "orderKey": 5,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "DATETIME",
                                            "typeCode": 93,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "d4330e32-f23a-4aa3-8be4-faa0fa2758b9",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 326
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "修改时间",
                                            "defaultValue": "CURRENT_TIMESTAMP",
                                            "displaySize": 19,
                                            "name": "modified_time",
                                            "numericPrecision": 0,
                                            "orderKey": 6,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "DATETIME",
                                            "typeCode": 93,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "ed95a121-53aa-415c-b24a-bc7b2956aec5",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 326
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "备注",
                                            "defaultValue": "",
                                            "displaySize": 500,
                                            "name": "remark",
                                            "numericPrecision": 0,
                                            "orderKey": 7,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "32ddd0bf-e445-42d4-93d4-2684d3733e2e",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 326
                                        }
                                    }
                                }
                            ]
                        },
                        "zIndex": 39
                    },
                    {
                        "position": {
                            "x": 9629.6396484375,
                            "y": 3911.762939453125
                        },
                        "size": {
                            "width": 285,
                            "height": 304
                        },
                        "view": "vue-shape-view",
                        "shape": "model",
                        "id": "726f2031-4ef0-4938-b93b-ca45c85c04cc",
                        "data": {
                            "table": {
                                "comment": "生成枚举",
                                "name": "gen_enum",
                                "orderKey": 0,
                                "remark": "",
                                "type": "TABLE",
                                "columns": [
                                    {
                                        "autoIncrement": true,
                                        "comment": "ID",
                                        "defaultValue": null,
                                        "displaySize": 19,
                                        "name": "id",
                                        "numericPrecision": 0,
                                        "orderKey": 0,
                                        "partOfFk": false,
                                        "partOfPk": true,
                                        "partOfUniqueIdx": true,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "所属包",
                                        "defaultValue": null,
                                        "displaySize": 19,
                                        "name": "package_id",
                                        "numericPrecision": 0,
                                        "orderKey": 1,
                                        "partOfFk": true,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": false,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "枚举名",
                                        "defaultValue": null,
                                        "displaySize": 500,
                                        "name": "name",
                                        "numericPrecision": 0,
                                        "orderKey": 2,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "枚举注释",
                                        "defaultValue": null,
                                        "displaySize": 500,
                                        "name": "comment",
                                        "numericPrecision": 0,
                                        "orderKey": 3,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "枚举类型",
                                        "defaultValue": null,
                                        "displaySize": 7,
                                        "name": "enum_type",
                                        "numericPrecision": 0,
                                        "orderKey": 4,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "ENUM",
                                        "typeCode": 1,
                                        "typeNotNull": false,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "自定排序",
                                        "defaultValue": "0",
                                        "displaySize": 19,
                                        "name": "order_key",
                                        "numericPrecision": 0,
                                        "orderKey": 5,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "创建时间",
                                        "defaultValue": "CURRENT_TIMESTAMP",
                                        "displaySize": 19,
                                        "name": "created_time",
                                        "numericPrecision": 0,
                                        "orderKey": 6,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "DATETIME",
                                        "typeCode": 93,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "修改时间",
                                        "defaultValue": "CURRENT_TIMESTAMP",
                                        "displaySize": 19,
                                        "name": "modified_time",
                                        "numericPrecision": 0,
                                        "orderKey": 7,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "DATETIME",
                                        "typeCode": 93,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "备注",
                                        "defaultValue": "",
                                        "displaySize": 500,
                                        "name": "remark",
                                        "numericPrecision": 0,
                                        "orderKey": 8,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    }
                                ]
                            },
                            "wrapper": {
                                "__v_isShallow": false,
                                "__v_isRef": true,
                                "_rawValue": {},
                                "_value": {}
                            }
                        },
                        "ports": {
                            "groups": {
                                "COLUMN_PORT_GROUP": {
                                    "position": "COLUMN_PORT",
                                    "markup": [
                                        {
                                            "tagName": "rect",
                                            "selector": "COLUMN_PORT_SELECTOR"
                                        }
                                    ],
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "magnet": true,
                                            "fill": "rgba(0,0,0,0)",
                                            "height": 30,
                                            "width": 200
                                        }
                                    }
                                }
                            },
                            "items": [
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": true,
                                            "comment": "ID",
                                            "defaultValue": null,
                                            "displaySize": 19,
                                            "name": "id",
                                            "numericPrecision": 0,
                                            "orderKey": 0,
                                            "partOfFk": false,
                                            "partOfPk": true,
                                            "partOfUniqueIdx": true,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "210a646b-e829-4a6b-8a1f-675088087bd6",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "所属包",
                                            "defaultValue": null,
                                            "displaySize": 19,
                                            "name": "package_id",
                                            "numericPrecision": 0,
                                            "orderKey": 1,
                                            "partOfFk": true,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": false,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "19cb6e0b-7fd3-4522-becc-694d62da3617",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "枚举名",
                                            "defaultValue": null,
                                            "displaySize": 500,
                                            "name": "name",
                                            "numericPrecision": 0,
                                            "orderKey": 2,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "9374d870-bd93-41de-a48d-99bb5a85cf40",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "枚举注释",
                                            "defaultValue": null,
                                            "displaySize": 500,
                                            "name": "comment",
                                            "numericPrecision": 0,
                                            "orderKey": 3,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "0e0f7d44-342b-4a00-b077-9a8a868ca8da",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "枚举类型",
                                            "defaultValue": null,
                                            "displaySize": 7,
                                            "name": "enum_type",
                                            "numericPrecision": 0,
                                            "orderKey": 4,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "ENUM",
                                            "typeCode": 1,
                                            "typeNotNull": false,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "1fa70d35-97e0-4eab-89db-0df6bc36e3fa",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "自定排序",
                                            "defaultValue": "0",
                                            "displaySize": 19,
                                            "name": "order_key",
                                            "numericPrecision": 0,
                                            "orderKey": 5,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "600c2067-6b52-44cd-94a2-e3ca05a961a8",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "创建时间",
                                            "defaultValue": "CURRENT_TIMESTAMP",
                                            "displaySize": 19,
                                            "name": "created_time",
                                            "numericPrecision": 0,
                                            "orderKey": 6,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "DATETIME",
                                            "typeCode": 93,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "7e8f9dbf-f3cd-45ec-af34-1533f7c052be",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "修改时间",
                                            "defaultValue": "CURRENT_TIMESTAMP",
                                            "displaySize": 19,
                                            "name": "modified_time",
                                            "numericPrecision": 0,
                                            "orderKey": 7,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "DATETIME",
                                            "typeCode": 93,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "f214ede3-75d3-42a4-aef8-f8c674e35af4",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "备注",
                                            "defaultValue": "",
                                            "displaySize": 500,
                                            "name": "remark",
                                            "numericPrecision": 0,
                                            "orderKey": 8,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "08da6df7-5875-4290-ba11-5002325ecbcd",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                }
                            ]
                        },
                        "zIndex": 40
                    },
                    {
                        "position": {
                            "x": 9629.6396484375,
                            "y": 4315.762939453125
                        },
                        "size": {
                            "width": 298,
                            "height": 244
                        },
                        "view": "vue-shape-view",
                        "shape": "model",
                        "id": "92a329a3-384d-48cd-81db-4a985d697f41",
                        "data": {
                            "table": {
                                "comment": "生成数据架构",
                                "name": "gen_schema",
                                "orderKey": 0,
                                "remark": "",
                                "type": "TABLE",
                                "columns": [
                                    {
                                        "autoIncrement": true,
                                        "comment": "ID",
                                        "defaultValue": null,
                                        "displaySize": 19,
                                        "name": "id",
                                        "numericPrecision": 0,
                                        "orderKey": 0,
                                        "partOfFk": false,
                                        "partOfPk": true,
                                        "partOfUniqueIdx": true,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "数据源 ID",
                                        "defaultValue": null,
                                        "displaySize": 19,
                                        "name": "data_source_id",
                                        "numericPrecision": 0,
                                        "orderKey": 1,
                                        "partOfFk": true,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "名称",
                                        "defaultValue": null,
                                        "displaySize": 500,
                                        "name": "name",
                                        "numericPrecision": 0,
                                        "orderKey": 2,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "自定排序",
                                        "defaultValue": "0",
                                        "displaySize": 19,
                                        "name": "order_key",
                                        "numericPrecision": 0,
                                        "orderKey": 3,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "创建时间",
                                        "defaultValue": "CURRENT_TIMESTAMP",
                                        "displaySize": 19,
                                        "name": "created_time",
                                        "numericPrecision": 0,
                                        "orderKey": 4,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "DATETIME",
                                        "typeCode": 93,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "修改时间",
                                        "defaultValue": "CURRENT_TIMESTAMP",
                                        "displaySize": 19,
                                        "name": "modified_time",
                                        "numericPrecision": 0,
                                        "orderKey": 5,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "DATETIME",
                                        "typeCode": 93,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "备注",
                                        "defaultValue": "",
                                        "displaySize": 500,
                                        "name": "remark",
                                        "numericPrecision": 0,
                                        "orderKey": 6,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    }
                                ]
                            },
                            "wrapper": {
                                "__v_isShallow": false,
                                "__v_isRef": true,
                                "_rawValue": {},
                                "_value": {}
                            }
                        },
                        "ports": {
                            "groups": {
                                "COLUMN_PORT_GROUP": {
                                    "position": "COLUMN_PORT",
                                    "markup": [
                                        {
                                            "tagName": "rect",
                                            "selector": "COLUMN_PORT_SELECTOR"
                                        }
                                    ],
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "magnet": true,
                                            "fill": "rgba(0,0,0,0)",
                                            "height": 30,
                                            "width": 200
                                        }
                                    }
                                }
                            },
                            "items": [
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": true,
                                            "comment": "ID",
                                            "defaultValue": null,
                                            "displaySize": 19,
                                            "name": "id",
                                            "numericPrecision": 0,
                                            "orderKey": 0,
                                            "partOfFk": false,
                                            "partOfPk": true,
                                            "partOfUniqueIdx": true,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "ec249065-eb81-4d7e-992f-f6f44fb99723",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 298
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "数据源 ID",
                                            "defaultValue": null,
                                            "displaySize": 19,
                                            "name": "data_source_id",
                                            "numericPrecision": 0,
                                            "orderKey": 1,
                                            "partOfFk": true,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "15029b79-196f-45c0-a1b4-f889a2ac2606",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 298
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "名称",
                                            "defaultValue": null,
                                            "displaySize": 500,
                                            "name": "name",
                                            "numericPrecision": 0,
                                            "orderKey": 2,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "b198fe2f-eef1-4d9f-916c-a6113888371b",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 298
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "自定排序",
                                            "defaultValue": "0",
                                            "displaySize": 19,
                                            "name": "order_key",
                                            "numericPrecision": 0,
                                            "orderKey": 3,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "f46b71c8-5ade-4afe-862a-d6df16ecfd4c",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 298
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "创建时间",
                                            "defaultValue": "CURRENT_TIMESTAMP",
                                            "displaySize": 19,
                                            "name": "created_time",
                                            "numericPrecision": 0,
                                            "orderKey": 4,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "DATETIME",
                                            "typeCode": 93,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "ab6de73f-5321-44c1-a7e8-4705cd61e57f",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 298
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "修改时间",
                                            "defaultValue": "CURRENT_TIMESTAMP",
                                            "displaySize": 19,
                                            "name": "modified_time",
                                            "numericPrecision": 0,
                                            "orderKey": 5,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "DATETIME",
                                            "typeCode": 93,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "89c6760e-64a4-40c4-a7a5-6623c3fb0b57",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 298
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "备注",
                                            "defaultValue": "",
                                            "displaySize": 500,
                                            "name": "remark",
                                            "numericPrecision": 0,
                                            "orderKey": 6,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "2ad22645-1d42-49e9-b760-b4a9c12d02e7",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 298
                                        }
                                    }
                                }
                            ]
                        },
                        "zIndex": 45
                    },
                    {
                        "position": {
                            "x": 10127.6396484375,
                            "y": 3911.762939453125
                        },
                        "size": {
                            "width": 285,
                            "height": 274
                        },
                        "view": "vue-shape-view",
                        "shape": "model",
                        "id": "2bde67bf-1ef8-4c15-98eb-744e1ff96ce8",
                        "data": {
                            "table": {
                                "comment": "生成枚举元素",
                                "name": "gen_enum_item",
                                "orderKey": 0,
                                "remark": "",
                                "type": "TABLE",
                                "columns": [
                                    {
                                        "autoIncrement": true,
                                        "comment": "ID",
                                        "defaultValue": null,
                                        "displaySize": 19,
                                        "name": "id",
                                        "numericPrecision": 0,
                                        "orderKey": 0,
                                        "partOfFk": false,
                                        "partOfPk": true,
                                        "partOfUniqueIdx": true,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "对应枚举 ID",
                                        "defaultValue": null,
                                        "displaySize": 19,
                                        "name": "enum_id",
                                        "numericPrecision": 0,
                                        "orderKey": 1,
                                        "partOfFk": true,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "元素名",
                                        "defaultValue": null,
                                        "displaySize": 500,
                                        "name": "name",
                                        "numericPrecision": 0,
                                        "orderKey": 2,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "元素值",
                                        "defaultValue": null,
                                        "displaySize": 500,
                                        "name": "value",
                                        "numericPrecision": 0,
                                        "orderKey": 3,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "元素注释",
                                        "defaultValue": "",
                                        "displaySize": 500,
                                        "name": "comment",
                                        "numericPrecision": 0,
                                        "orderKey": 4,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "创建时间",
                                        "defaultValue": "CURRENT_TIMESTAMP",
                                        "displaySize": 19,
                                        "name": "created_time",
                                        "numericPrecision": 0,
                                        "orderKey": 5,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "DATETIME",
                                        "typeCode": 93,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "修改时间",
                                        "defaultValue": "CURRENT_TIMESTAMP",
                                        "displaySize": 19,
                                        "name": "modified_time",
                                        "numericPrecision": 0,
                                        "orderKey": 6,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "DATETIME",
                                        "typeCode": 93,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "备注",
                                        "defaultValue": "",
                                        "displaySize": 500,
                                        "name": "remark",
                                        "numericPrecision": 0,
                                        "orderKey": 7,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    }
                                ]
                            },
                            "wrapper": {
                                "__v_isShallow": false,
                                "__v_isRef": true,
                                "_rawValue": {},
                                "_value": {}
                            }
                        },
                        "ports": {
                            "groups": {
                                "COLUMN_PORT_GROUP": {
                                    "position": "COLUMN_PORT",
                                    "markup": [
                                        {
                                            "tagName": "rect",
                                            "selector": "COLUMN_PORT_SELECTOR"
                                        }
                                    ],
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "magnet": true,
                                            "fill": "rgba(0,0,0,0)",
                                            "height": 30,
                                            "width": 200
                                        }
                                    }
                                }
                            },
                            "items": [
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": true,
                                            "comment": "ID",
                                            "defaultValue": null,
                                            "displaySize": 19,
                                            "name": "id",
                                            "numericPrecision": 0,
                                            "orderKey": 0,
                                            "partOfFk": false,
                                            "partOfPk": true,
                                            "partOfUniqueIdx": true,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "5c9cd22a-b2ba-43b9-9a26-c03d47528eca",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "对应枚举 ID",
                                            "defaultValue": null,
                                            "displaySize": 19,
                                            "name": "enum_id",
                                            "numericPrecision": 0,
                                            "orderKey": 1,
                                            "partOfFk": true,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "1354f6c0-a135-4f97-905c-ce72473cf688",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "元素名",
                                            "defaultValue": null,
                                            "displaySize": 500,
                                            "name": "name",
                                            "numericPrecision": 0,
                                            "orderKey": 2,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "a99dcc64-2965-4302-b028-dde4bce17931",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "元素值",
                                            "defaultValue": null,
                                            "displaySize": 500,
                                            "name": "value",
                                            "numericPrecision": 0,
                                            "orderKey": 3,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "7fea9509-a9c1-4df8-a4ec-1c9cd43250fb",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "元素注释",
                                            "defaultValue": "",
                                            "displaySize": 500,
                                            "name": "comment",
                                            "numericPrecision": 0,
                                            "orderKey": 4,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "6aac5e62-9a60-4553-ab27-5e59218a2ac9",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "创建时间",
                                            "defaultValue": "CURRENT_TIMESTAMP",
                                            "displaySize": 19,
                                            "name": "created_time",
                                            "numericPrecision": 0,
                                            "orderKey": 5,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "DATETIME",
                                            "typeCode": 93,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "28af2073-e540-428d-a869-eff35fe5737d",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "修改时间",
                                            "defaultValue": "CURRENT_TIMESTAMP",
                                            "displaySize": 19,
                                            "name": "modified_time",
                                            "numericPrecision": 0,
                                            "orderKey": 6,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "DATETIME",
                                            "typeCode": 93,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "1f0383eb-7366-4f1d-aaa0-6ba7be2ff39c",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "备注",
                                            "defaultValue": "",
                                            "displaySize": 500,
                                            "name": "remark",
                                            "numericPrecision": 0,
                                            "orderKey": 7,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "e881e039-e4f1-4f2b-899a-1eb00364afbf",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                }
                            ]
                        },
                        "zIndex": 48
                    },
                    {
                        "position": {
                            "x": 10127.6396484375,
                            "y": 4285.762939453125
                        },
                        "size": {
                            "width": 285,
                            "height": 334
                        },
                        "view": "vue-shape-view",
                        "shape": "model",
                        "id": "fe9d3326-8b41-4c58-b223-dfb8ab46be47",
                        "data": {
                            "table": {
                                "comment": "生成表",
                                "name": "gen_table",
                                "orderKey": 0,
                                "remark": "",
                                "type": "TABLE",
                                "columns": [
                                    {
                                        "autoIncrement": true,
                                        "comment": "ID",
                                        "defaultValue": null,
                                        "displaySize": 19,
                                        "name": "id",
                                        "numericPrecision": 0,
                                        "orderKey": 0,
                                        "partOfFk": false,
                                        "partOfPk": true,
                                        "partOfUniqueIdx": true,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "模型",
                                        "defaultValue": null,
                                        "displaySize": 19,
                                        "name": "model_id",
                                        "numericPrecision": 0,
                                        "orderKey": 1,
                                        "partOfFk": true,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": false,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "数据架构",
                                        "defaultValue": null,
                                        "displaySize": 19,
                                        "name": "schema_id",
                                        "numericPrecision": 0,
                                        "orderKey": 2,
                                        "partOfFk": true,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": false,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "表名称",
                                        "defaultValue": null,
                                        "displaySize": 500,
                                        "name": "name",
                                        "numericPrecision": 0,
                                        "orderKey": 3,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "表注释",
                                        "defaultValue": null,
                                        "displaySize": 500,
                                        "name": "comment",
                                        "numericPrecision": 0,
                                        "orderKey": 4,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "表种类",
                                        "defaultValue": null,
                                        "displaySize": 500,
                                        "name": "type",
                                        "numericPrecision": 0,
                                        "orderKey": 5,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "自定排序",
                                        "defaultValue": "0",
                                        "displaySize": 19,
                                        "name": "order_key",
                                        "numericPrecision": 0,
                                        "orderKey": 6,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "创建时间",
                                        "defaultValue": "CURRENT_TIMESTAMP",
                                        "displaySize": 19,
                                        "name": "created_time",
                                        "numericPrecision": 0,
                                        "orderKey": 7,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "DATETIME",
                                        "typeCode": 93,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "修改时间",
                                        "defaultValue": "CURRENT_TIMESTAMP",
                                        "displaySize": 19,
                                        "name": "modified_time",
                                        "numericPrecision": 0,
                                        "orderKey": 8,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "DATETIME",
                                        "typeCode": 93,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "备注",
                                        "defaultValue": "",
                                        "displaySize": 500,
                                        "name": "remark",
                                        "numericPrecision": 0,
                                        "orderKey": 9,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    }
                                ]
                            },
                            "wrapper": {
                                "__v_isShallow": false,
                                "__v_isRef": true,
                                "_rawValue": {},
                                "_value": {}
                            }
                        },
                        "ports": {
                            "groups": {
                                "COLUMN_PORT_GROUP": {
                                    "position": "COLUMN_PORT",
                                    "markup": [
                                        {
                                            "tagName": "rect",
                                            "selector": "COLUMN_PORT_SELECTOR"
                                        }
                                    ],
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "magnet": true,
                                            "fill": "rgba(0,0,0,0)",
                                            "height": 30,
                                            "width": 200
                                        }
                                    }
                                }
                            },
                            "items": [
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": true,
                                            "comment": "ID",
                                            "defaultValue": null,
                                            "displaySize": 19,
                                            "name": "id",
                                            "numericPrecision": 0,
                                            "orderKey": 0,
                                            "partOfFk": false,
                                            "partOfPk": true,
                                            "partOfUniqueIdx": true,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "96f5db81-5abb-4df7-a870-d074bd0dcd49",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "模型",
                                            "defaultValue": null,
                                            "displaySize": 19,
                                            "name": "model_id",
                                            "numericPrecision": 0,
                                            "orderKey": 1,
                                            "partOfFk": true,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": false,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "15135935-8b5d-4ba7-9edf-2d1cfebf301c",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "数据架构",
                                            "defaultValue": null,
                                            "displaySize": 19,
                                            "name": "schema_id",
                                            "numericPrecision": 0,
                                            "orderKey": 2,
                                            "partOfFk": true,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": false,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "6dc78be2-b8da-44b7-a827-0d78aa8a03f9",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "表名称",
                                            "defaultValue": null,
                                            "displaySize": 500,
                                            "name": "name",
                                            "numericPrecision": 0,
                                            "orderKey": 3,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "dde95b1d-3c06-4063-bc61-514eb6ddee11",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "表注释",
                                            "defaultValue": null,
                                            "displaySize": 500,
                                            "name": "comment",
                                            "numericPrecision": 0,
                                            "orderKey": 4,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "34209c25-efad-4daa-a7f1-d2f4dac3360c",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "表种类",
                                            "defaultValue": null,
                                            "displaySize": 500,
                                            "name": "type",
                                            "numericPrecision": 0,
                                            "orderKey": 5,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "fdb310c6-6702-4d98-b630-df56d795d19f",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "自定排序",
                                            "defaultValue": "0",
                                            "displaySize": 19,
                                            "name": "order_key",
                                            "numericPrecision": 0,
                                            "orderKey": 6,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "2ca26c02-ffa7-4d0f-9c9c-a0edb83b8212",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "创建时间",
                                            "defaultValue": "CURRENT_TIMESTAMP",
                                            "displaySize": 19,
                                            "name": "created_time",
                                            "numericPrecision": 0,
                                            "orderKey": 7,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "DATETIME",
                                            "typeCode": 93,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "4d5cfafe-c875-4969-850f-254741b7af53",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "修改时间",
                                            "defaultValue": "CURRENT_TIMESTAMP",
                                            "displaySize": 19,
                                            "name": "modified_time",
                                            "numericPrecision": 0,
                                            "orderKey": 8,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "DATETIME",
                                            "typeCode": 93,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "541be583-a053-46ad-b2ab-13ea12d3dc21",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "备注",
                                            "defaultValue": "",
                                            "displaySize": 500,
                                            "name": "remark",
                                            "numericPrecision": 0,
                                            "orderKey": 9,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "f5d7e722-3e2b-4331-b73b-858270f20f38",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                }
                            ]
                        },
                        "zIndex": 50
                    },
                    {
                        "position": {
                            "x": 10612.6396484375,
                            "y": 3911.762939453125
                        },
                        "size": {
                            "width": 343,
                            "height": 664
                        },
                        "view": "vue-shape-view",
                        "shape": "model",
                        "id": "b020de7f-6cbb-4433-b911-a36670eebd0b",
                        "data": {
                            "table": {
                                "comment": "生成列",
                                "name": "gen_column",
                                "orderKey": 0,
                                "remark": "",
                                "type": "TABLE",
                                "columns": [
                                    {
                                        "autoIncrement": true,
                                        "comment": "ID",
                                        "defaultValue": null,
                                        "displaySize": 19,
                                        "name": "id",
                                        "numericPrecision": 0,
                                        "orderKey": 0,
                                        "partOfFk": false,
                                        "partOfPk": true,
                                        "partOfUniqueIdx": true,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "归属表",
                                        "defaultValue": null,
                                        "displaySize": 19,
                                        "name": "table_id",
                                        "numericPrecision": 0,
                                        "orderKey": 1,
                                        "partOfFk": true,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "列在表中顺序",
                                        "defaultValue": null,
                                        "displaySize": 19,
                                        "name": "order_key",
                                        "numericPrecision": 0,
                                        "orderKey": 2,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "列名称",
                                        "defaultValue": null,
                                        "displaySize": 500,
                                        "name": "name",
                                        "numericPrecision": 0,
                                        "orderKey": 3,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "列对应 JDBCType 码值",
                                        "defaultValue": null,
                                        "displaySize": 10,
                                        "name": "type_code",
                                        "numericPrecision": 0,
                                        "orderKey": 4,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "INT",
                                        "typeCode": 4,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "列类型",
                                        "defaultValue": null,
                                        "displaySize": 500,
                                        "name": "type",
                                        "numericPrecision": 0,
                                        "orderKey": 5,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "列展示长度",
                                        "defaultValue": "0",
                                        "displaySize": 19,
                                        "name": "display_size",
                                        "numericPrecision": 0,
                                        "orderKey": 6,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "列精度",
                                        "defaultValue": "0",
                                        "displaySize": 19,
                                        "name": "numeric_precision",
                                        "numericPrecision": 0,
                                        "orderKey": 7,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "列默认值",
                                        "defaultValue": null,
                                        "displaySize": 500,
                                        "name": "default_value",
                                        "numericPrecision": 0,
                                        "orderKey": 8,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": false,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "列注释",
                                        "defaultValue": null,
                                        "displaySize": 500,
                                        "name": "comment",
                                        "numericPrecision": 0,
                                        "orderKey": 9,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "是否主键",
                                        "defaultValue": "0",
                                        "displaySize": 1,
                                        "name": "part_of_pk",
                                        "numericPrecision": 0,
                                        "orderKey": 10,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIT",
                                        "typeCode": -7,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "是否自增",
                                        "defaultValue": "0",
                                        "displaySize": 1,
                                        "name": "auto_increment",
                                        "numericPrecision": 0,
                                        "orderKey": 11,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIT",
                                        "typeCode": -7,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "是否外键",
                                        "defaultValue": "0",
                                        "displaySize": 1,
                                        "name": "part_of_fk",
                                        "numericPrecision": 0,
                                        "orderKey": 12,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIT",
                                        "typeCode": -7,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "是否唯一索引",
                                        "defaultValue": "0",
                                        "displaySize": 1,
                                        "name": "part_of_unique_idx",
                                        "numericPrecision": 0,
                                        "orderKey": 13,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIT",
                                        "typeCode": -7,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "是否非空",
                                        "defaultValue": "0",
                                        "displaySize": 1,
                                        "name": "type_not_null",
                                        "numericPrecision": 0,
                                        "orderKey": 14,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIT",
                                        "typeCode": -7,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "是否为业务键",
                                        "defaultValue": "0",
                                        "displaySize": 1,
                                        "name": "business_key",
                                        "numericPrecision": 0,
                                        "orderKey": 15,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIT",
                                        "typeCode": -7,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "是否为逻辑删除",
                                        "defaultValue": "0",
                                        "displaySize": 1,
                                        "name": "logical_delete",
                                        "numericPrecision": 0,
                                        "orderKey": 16,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIT",
                                        "typeCode": -7,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "对应枚举 ID",
                                        "defaultValue": null,
                                        "displaySize": 19,
                                        "name": "enum_id",
                                        "numericPrecision": 0,
                                        "orderKey": 17,
                                        "partOfFk": true,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": false,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "创建时间",
                                        "defaultValue": "CURRENT_TIMESTAMP",
                                        "displaySize": 19,
                                        "name": "created_time",
                                        "numericPrecision": 0,
                                        "orderKey": 18,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "DATETIME",
                                        "typeCode": 93,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "修改时间",
                                        "defaultValue": "CURRENT_TIMESTAMP",
                                        "displaySize": 19,
                                        "name": "modified_time",
                                        "numericPrecision": 0,
                                        "orderKey": 19,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "DATETIME",
                                        "typeCode": 93,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "备注",
                                        "defaultValue": "",
                                        "displaySize": 500,
                                        "name": "remark",
                                        "numericPrecision": 0,
                                        "orderKey": 20,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    }
                                ]
                            },
                            "wrapper": {
                                "__v_isShallow": false,
                                "__v_isRef": true,
                                "_rawValue": {},
                                "_value": {}
                            }
                        },
                        "ports": {
                            "groups": {
                                "COLUMN_PORT_GROUP": {
                                    "position": "COLUMN_PORT",
                                    "markup": [
                                        {
                                            "tagName": "rect",
                                            "selector": "COLUMN_PORT_SELECTOR"
                                        }
                                    ],
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "magnet": true,
                                            "fill": "rgba(0,0,0,0)",
                                            "height": 30,
                                            "width": 200
                                        }
                                    }
                                }
                            },
                            "items": [
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": true,
                                            "comment": "ID",
                                            "defaultValue": null,
                                            "displaySize": 19,
                                            "name": "id",
                                            "numericPrecision": 0,
                                            "orderKey": 0,
                                            "partOfFk": false,
                                            "partOfPk": true,
                                            "partOfUniqueIdx": true,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "10f0ce55-5edb-4265-89f1-59a05d85279e",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 343
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "归属表",
                                            "defaultValue": null,
                                            "displaySize": 19,
                                            "name": "table_id",
                                            "numericPrecision": 0,
                                            "orderKey": 1,
                                            "partOfFk": true,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "7c31bbd6-1d83-4bac-84bb-d801917304c9",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 343
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "列在表中顺序",
                                            "defaultValue": null,
                                            "displaySize": 19,
                                            "name": "order_key",
                                            "numericPrecision": 0,
                                            "orderKey": 2,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "efa6e603-771e-4dc8-8097-b162146ff7c3",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 343
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "列名称",
                                            "defaultValue": null,
                                            "displaySize": 500,
                                            "name": "name",
                                            "numericPrecision": 0,
                                            "orderKey": 3,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "d2f98223-6db5-41cc-9335-1b66d899081f",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 343
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "列对应 JDBCType 码值",
                                            "defaultValue": null,
                                            "displaySize": 10,
                                            "name": "type_code",
                                            "numericPrecision": 0,
                                            "orderKey": 4,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "INT",
                                            "typeCode": 4,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "bc359a15-673d-436f-887d-38be94fc0fb8",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 343
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "列类型",
                                            "defaultValue": null,
                                            "displaySize": 500,
                                            "name": "type",
                                            "numericPrecision": 0,
                                            "orderKey": 5,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "db2e140d-87f4-45f6-87d0-08d8430fcb74",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 343
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "列展示长度",
                                            "defaultValue": "0",
                                            "displaySize": 19,
                                            "name": "display_size",
                                            "numericPrecision": 0,
                                            "orderKey": 6,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "829c7106-df30-40a3-b143-bdca9d40c4ff",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 343
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "列精度",
                                            "defaultValue": "0",
                                            "displaySize": 19,
                                            "name": "numeric_precision",
                                            "numericPrecision": 0,
                                            "orderKey": 7,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "2f23074c-5869-4867-9cb0-3e1c9b146f6b",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 343
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "列默认值",
                                            "defaultValue": null,
                                            "displaySize": 500,
                                            "name": "default_value",
                                            "numericPrecision": 0,
                                            "orderKey": 8,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": false,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "7aafe443-5a47-4b69-a597-c0956c2272de",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 343
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "列注释",
                                            "defaultValue": null,
                                            "displaySize": 500,
                                            "name": "comment",
                                            "numericPrecision": 0,
                                            "orderKey": 9,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "0c36e541-ac71-4476-9030-f3cebf9b781d",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 343
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "是否主键",
                                            "defaultValue": "0",
                                            "displaySize": 1,
                                            "name": "part_of_pk",
                                            "numericPrecision": 0,
                                            "orderKey": 10,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIT",
                                            "typeCode": -7,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "f4fdbeaf-dbd9-4e33-a7e7-dd928cf156ff",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 343
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "是否自增",
                                            "defaultValue": "0",
                                            "displaySize": 1,
                                            "name": "auto_increment",
                                            "numericPrecision": 0,
                                            "orderKey": 11,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIT",
                                            "typeCode": -7,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "e1ddfc82-b340-4f5d-8f72-1a954360ec67",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 343
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "是否外键",
                                            "defaultValue": "0",
                                            "displaySize": 1,
                                            "name": "part_of_fk",
                                            "numericPrecision": 0,
                                            "orderKey": 12,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIT",
                                            "typeCode": -7,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "73337442-3381-498c-9829-570ee6b37d24",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 343
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "是否唯一索引",
                                            "defaultValue": "0",
                                            "displaySize": 1,
                                            "name": "part_of_unique_idx",
                                            "numericPrecision": 0,
                                            "orderKey": 13,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIT",
                                            "typeCode": -7,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "de7b0b8e-8fa9-487d-9567-3f6cdd0fed8d",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 343
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "是否非空",
                                            "defaultValue": "0",
                                            "displaySize": 1,
                                            "name": "type_not_null",
                                            "numericPrecision": 0,
                                            "orderKey": 14,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIT",
                                            "typeCode": -7,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "42827aaa-2514-4c8c-80fb-300601c9be91",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 343
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "是否为业务键",
                                            "defaultValue": "0",
                                            "displaySize": 1,
                                            "name": "business_key",
                                            "numericPrecision": 0,
                                            "orderKey": 15,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIT",
                                            "typeCode": -7,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "a991d32c-2ef0-46bc-ba68-bcd03ed10d4d",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 343
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "是否为逻辑删除",
                                            "defaultValue": "0",
                                            "displaySize": 1,
                                            "name": "logical_delete",
                                            "numericPrecision": 0,
                                            "orderKey": 16,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIT",
                                            "typeCode": -7,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "bccda524-ce36-4c9f-9c55-9645ddbe9778",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 343
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "对应枚举 ID",
                                            "defaultValue": null,
                                            "displaySize": 19,
                                            "name": "enum_id",
                                            "numericPrecision": 0,
                                            "orderKey": 17,
                                            "partOfFk": true,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": false,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "57589ff7-4290-4c1b-b351-546e6a50db65",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 343
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "创建时间",
                                            "defaultValue": "CURRENT_TIMESTAMP",
                                            "displaySize": 19,
                                            "name": "created_time",
                                            "numericPrecision": 0,
                                            "orderKey": 18,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "DATETIME",
                                            "typeCode": 93,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "67dbd1d4-e306-4e61-908a-3381be83c58b",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 343
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "修改时间",
                                            "defaultValue": "CURRENT_TIMESTAMP",
                                            "displaySize": 19,
                                            "name": "modified_time",
                                            "numericPrecision": 0,
                                            "orderKey": 19,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "DATETIME",
                                            "typeCode": 93,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "8c93db13-9f9e-45c1-b846-f478d956b2a4",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 343
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "备注",
                                            "defaultValue": "",
                                            "displaySize": 500,
                                            "name": "remark",
                                            "numericPrecision": 0,
                                            "orderKey": 20,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "8251c2b7-ec21-4e2b-94be-9901311dba74",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 343
                                        }
                                    }
                                }
                            ]
                        },
                        "zIndex": 56
                    },
                    {
                        "position": {
                            "x": 10612.6396484375,
                            "y": 4675.762939453125
                        },
                        "size": {
                            "width": 285,
                            "height": 334
                        },
                        "view": "vue-shape-view",
                        "shape": "model",
                        "id": "020ce872-345f-4c33-8d9b-441ae58af661",
                        "data": {
                            "table": {
                                "comment": "生成实体",
                                "name": "gen_entity",
                                "orderKey": 0,
                                "remark": "",
                                "type": "TABLE",
                                "columns": [
                                    {
                                        "autoIncrement": true,
                                        "comment": "ID",
                                        "defaultValue": null,
                                        "displaySize": 19,
                                        "name": "id",
                                        "numericPrecision": 0,
                                        "orderKey": 0,
                                        "partOfFk": false,
                                        "partOfPk": true,
                                        "partOfUniqueIdx": true,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "所属包",
                                        "defaultValue": null,
                                        "displaySize": 19,
                                        "name": "package_id",
                                        "numericPrecision": 0,
                                        "orderKey": 1,
                                        "partOfFk": true,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": false,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "对应表",
                                        "defaultValue": null,
                                        "displaySize": 19,
                                        "name": "table_id",
                                        "numericPrecision": 0,
                                        "orderKey": 2,
                                        "partOfFk": true,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": true,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "类名称",
                                        "defaultValue": null,
                                        "displaySize": 500,
                                        "name": "name",
                                        "numericPrecision": 0,
                                        "orderKey": 3,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "类注释",
                                        "defaultValue": null,
                                        "displaySize": 500,
                                        "name": "comment",
                                        "numericPrecision": 0,
                                        "orderKey": 4,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "作者",
                                        "defaultValue": "",
                                        "displaySize": 500,
                                        "name": "author",
                                        "numericPrecision": 0,
                                        "orderKey": 5,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "自定排序",
                                        "defaultValue": "0",
                                        "displaySize": 19,
                                        "name": "order_key",
                                        "numericPrecision": 0,
                                        "orderKey": 6,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "创建时间",
                                        "defaultValue": "CURRENT_TIMESTAMP",
                                        "displaySize": 19,
                                        "name": "created_time",
                                        "numericPrecision": 0,
                                        "orderKey": 7,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "DATETIME",
                                        "typeCode": 93,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "修改时间",
                                        "defaultValue": "CURRENT_TIMESTAMP",
                                        "displaySize": 19,
                                        "name": "modified_time",
                                        "numericPrecision": 0,
                                        "orderKey": 8,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "DATETIME",
                                        "typeCode": 93,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "备注",
                                        "defaultValue": "",
                                        "displaySize": 500,
                                        "name": "remark",
                                        "numericPrecision": 0,
                                        "orderKey": 9,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    }
                                ]
                            },
                            "wrapper": {
                                "__v_isShallow": false,
                                "__v_isRef": true,
                                "_rawValue": {},
                                "_value": {}
                            }
                        },
                        "ports": {
                            "groups": {
                                "COLUMN_PORT_GROUP": {
                                    "position": "COLUMN_PORT",
                                    "markup": [
                                        {
                                            "tagName": "rect",
                                            "selector": "COLUMN_PORT_SELECTOR"
                                        }
                                    ],
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "magnet": true,
                                            "fill": "rgba(0,0,0,0)",
                                            "height": 30,
                                            "width": 200
                                        }
                                    }
                                }
                            },
                            "items": [
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": true,
                                            "comment": "ID",
                                            "defaultValue": null,
                                            "displaySize": 19,
                                            "name": "id",
                                            "numericPrecision": 0,
                                            "orderKey": 0,
                                            "partOfFk": false,
                                            "partOfPk": true,
                                            "partOfUniqueIdx": true,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "5b9a48ad-4085-4d0a-b6e6-0d54f27f84c8",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "所属包",
                                            "defaultValue": null,
                                            "displaySize": 19,
                                            "name": "package_id",
                                            "numericPrecision": 0,
                                            "orderKey": 1,
                                            "partOfFk": true,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": false,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "b3328fe8-86f6-4d07-9c59-a9984de4e336",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "对应表",
                                            "defaultValue": null,
                                            "displaySize": 19,
                                            "name": "table_id",
                                            "numericPrecision": 0,
                                            "orderKey": 2,
                                            "partOfFk": true,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": true,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "ba57dad2-f10d-408a-a102-bb6c1b53e784",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "类名称",
                                            "defaultValue": null,
                                            "displaySize": 500,
                                            "name": "name",
                                            "numericPrecision": 0,
                                            "orderKey": 3,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "7f741c26-a27f-4c86-8841-b10e54893215",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "类注释",
                                            "defaultValue": null,
                                            "displaySize": 500,
                                            "name": "comment",
                                            "numericPrecision": 0,
                                            "orderKey": 4,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "2b0c62ef-6474-4a1f-ac96-6d179e293295",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "作者",
                                            "defaultValue": "",
                                            "displaySize": 500,
                                            "name": "author",
                                            "numericPrecision": 0,
                                            "orderKey": 5,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "4a54eb42-e0ad-492d-a965-36052bdf4250",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "自定排序",
                                            "defaultValue": "0",
                                            "displaySize": 19,
                                            "name": "order_key",
                                            "numericPrecision": 0,
                                            "orderKey": 6,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "0dd7c154-20c8-4694-af1b-edf3934c418a",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "创建时间",
                                            "defaultValue": "CURRENT_TIMESTAMP",
                                            "displaySize": 19,
                                            "name": "created_time",
                                            "numericPrecision": 0,
                                            "orderKey": 7,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "DATETIME",
                                            "typeCode": 93,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "5d37dbd5-e01b-4299-adc6-6c176d4577aa",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "修改时间",
                                            "defaultValue": "CURRENT_TIMESTAMP",
                                            "displaySize": 19,
                                            "name": "modified_time",
                                            "numericPrecision": 0,
                                            "orderKey": 8,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "DATETIME",
                                            "typeCode": 93,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "f33a4b56-81e4-4ba0-8803-6a8c9eeda737",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "备注",
                                            "defaultValue": "",
                                            "displaySize": 500,
                                            "name": "remark",
                                            "numericPrecision": 0,
                                            "orderKey": 9,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "f37b80d6-52d5-432f-9f54-264a7665ed9a",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 285
                                        }
                                    }
                                }
                            ]
                        },
                        "zIndex": 62
                    },
                    {
                        "position": {
                            "x": 11155.6396484375,
                            "y": 3911.762939453125
                        },
                        "size": {
                            "width": 306,
                            "height": 394
                        },
                        "view": "vue-shape-view",
                        "shape": "model",
                        "id": "464e99e9-b5c8-449b-947b-381ac0eecd08",
                        "data": {
                            "table": {
                                "comment": "生成关联",
                                "name": "gen_association",
                                "orderKey": 0,
                                "remark": "",
                                "type": "TABLE",
                                "columns": [
                                    {
                                        "autoIncrement": true,
                                        "comment": "ID",
                                        "defaultValue": null,
                                        "displaySize": 19,
                                        "name": "id",
                                        "numericPrecision": 0,
                                        "orderKey": 0,
                                        "partOfFk": false,
                                        "partOfPk": true,
                                        "partOfUniqueIdx": true,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "模型",
                                        "defaultValue": null,
                                        "displaySize": 19,
                                        "name": "model_id",
                                        "numericPrecision": 0,
                                        "orderKey": 1,
                                        "partOfFk": true,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": false,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "关联注释",
                                        "defaultValue": "",
                                        "displaySize": 500,
                                        "name": "comment",
                                        "numericPrecision": 0,
                                        "orderKey": 2,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "主列",
                                        "defaultValue": null,
                                        "displaySize": 19,
                                        "name": "source_column_id",
                                        "numericPrecision": 0,
                                        "orderKey": 3,
                                        "partOfFk": true,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "从列",
                                        "defaultValue": null,
                                        "displaySize": 19,
                                        "name": "target_column_id",
                                        "numericPrecision": 0,
                                        "orderKey": 4,
                                        "partOfFk": true,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "关联类型",
                                        "defaultValue": null,
                                        "displaySize": 12,
                                        "name": "association_type",
                                        "numericPrecision": 0,
                                        "orderKey": 5,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "ENUM",
                                        "typeCode": 1,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "脱钩行为",
                                        "defaultValue": null,
                                        "displaySize": 8,
                                        "name": "dissociate_action",
                                        "numericPrecision": 0,
                                        "orderKey": 6,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "ENUM",
                                        "typeCode": 1,
                                        "typeNotNull": false,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "是否伪外键",
                                        "defaultValue": "1",
                                        "displaySize": 1,
                                        "name": "fake",
                                        "numericPrecision": 0,
                                        "orderKey": 7,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIT",
                                        "typeCode": -7,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "自定排序",
                                        "defaultValue": "0",
                                        "displaySize": 19,
                                        "name": "order_key",
                                        "numericPrecision": 0,
                                        "orderKey": 8,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "创建时间",
                                        "defaultValue": "CURRENT_TIMESTAMP",
                                        "displaySize": 19,
                                        "name": "created_time",
                                        "numericPrecision": 0,
                                        "orderKey": 9,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "DATETIME",
                                        "typeCode": 93,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "修改时间",
                                        "defaultValue": "CURRENT_TIMESTAMP",
                                        "displaySize": 19,
                                        "name": "modified_time",
                                        "numericPrecision": 0,
                                        "orderKey": 10,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "DATETIME",
                                        "typeCode": 93,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "备注",
                                        "defaultValue": "",
                                        "displaySize": 500,
                                        "name": "remark",
                                        "numericPrecision": 0,
                                        "orderKey": 11,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    }
                                ]
                            },
                            "wrapper": {
                                "__v_isShallow": false,
                                "__v_isRef": true,
                                "_rawValue": {},
                                "_value": {}
                            }
                        },
                        "ports": {
                            "groups": {
                                "COLUMN_PORT_GROUP": {
                                    "position": "COLUMN_PORT",
                                    "markup": [
                                        {
                                            "tagName": "rect",
                                            "selector": "COLUMN_PORT_SELECTOR"
                                        }
                                    ],
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "magnet": true,
                                            "fill": "rgba(0,0,0,0)",
                                            "height": 30,
                                            "width": 200
                                        }
                                    }
                                }
                            },
                            "items": [
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": true,
                                            "comment": "ID",
                                            "defaultValue": null,
                                            "displaySize": 19,
                                            "name": "id",
                                            "numericPrecision": 0,
                                            "orderKey": 0,
                                            "partOfFk": false,
                                            "partOfPk": true,
                                            "partOfUniqueIdx": true,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "1f77c07f-fdf3-4cfd-9521-76b1ff86bd86",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 306
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "模型",
                                            "defaultValue": null,
                                            "displaySize": 19,
                                            "name": "model_id",
                                            "numericPrecision": 0,
                                            "orderKey": 1,
                                            "partOfFk": true,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": false,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "2727b136-c99a-40cb-9970-49df21307b29",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 306
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "关联注释",
                                            "defaultValue": "",
                                            "displaySize": 500,
                                            "name": "comment",
                                            "numericPrecision": 0,
                                            "orderKey": 2,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "c693924b-5553-4a23-a803-2fce03be8c72",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 306
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "主列",
                                            "defaultValue": null,
                                            "displaySize": 19,
                                            "name": "source_column_id",
                                            "numericPrecision": 0,
                                            "orderKey": 3,
                                            "partOfFk": true,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "a340e8c1-6142-4fe2-a17f-7510b4806f63",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 306
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "从列",
                                            "defaultValue": null,
                                            "displaySize": 19,
                                            "name": "target_column_id",
                                            "numericPrecision": 0,
                                            "orderKey": 4,
                                            "partOfFk": true,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "4a68f4ea-1666-4679-88e2-c057c5139eab",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 306
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "关联类型",
                                            "defaultValue": null,
                                            "displaySize": 12,
                                            "name": "association_type",
                                            "numericPrecision": 0,
                                            "orderKey": 5,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "ENUM",
                                            "typeCode": 1,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "65035933-2676-4fd1-9cfb-5040b4a1da55",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 306
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "脱钩行为",
                                            "defaultValue": null,
                                            "displaySize": 8,
                                            "name": "dissociate_action",
                                            "numericPrecision": 0,
                                            "orderKey": 6,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "ENUM",
                                            "typeCode": 1,
                                            "typeNotNull": false,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "0467b3d0-cf3f-4b59-a9d0-fd08b8faf3f6",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 306
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "是否伪外键",
                                            "defaultValue": "1",
                                            "displaySize": 1,
                                            "name": "fake",
                                            "numericPrecision": 0,
                                            "orderKey": 7,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIT",
                                            "typeCode": -7,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "de6d155f-9ab5-4b27-bcb5-0ac6afca57c9",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 306
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "自定排序",
                                            "defaultValue": "0",
                                            "displaySize": 19,
                                            "name": "order_key",
                                            "numericPrecision": 0,
                                            "orderKey": 8,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "cf21b11c-593a-411c-b50e-ef53b864ee6d",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 306
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "创建时间",
                                            "defaultValue": "CURRENT_TIMESTAMP",
                                            "displaySize": 19,
                                            "name": "created_time",
                                            "numericPrecision": 0,
                                            "orderKey": 9,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "DATETIME",
                                            "typeCode": 93,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "51d8919e-1ac9-45d7-9161-71a950118129",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 306
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "修改时间",
                                            "defaultValue": "CURRENT_TIMESTAMP",
                                            "displaySize": 19,
                                            "name": "modified_time",
                                            "numericPrecision": 0,
                                            "orderKey": 10,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "DATETIME",
                                            "typeCode": 93,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "04f4484b-319b-4002-a3c5-786ac479983d",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 306
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "备注",
                                            "defaultValue": "",
                                            "displaySize": 500,
                                            "name": "remark",
                                            "numericPrecision": 0,
                                            "orderKey": 11,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "71cb6c20-0f68-4358-bdbe-ad1ef1f716fe",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 306
                                        }
                                    }
                                }
                            ]
                        },
                        "zIndex": 66
                    },
                    {
                        "position": {
                            "x": 11155.6396484375,
                            "y": 4405.762939453125
                        },
                        "size": {
                            "width": 347,
                            "height": 724
                        },
                        "view": "vue-shape-view",
                        "shape": "model",
                        "id": "c5fae437-0e8e-4e58-a29f-0e18de2c1e68",
                        "data": {
                            "table": {
                                "comment": "生成属性",
                                "name": "gen_property",
                                "orderKey": 0,
                                "remark": "",
                                "type": "TABLE",
                                "columns": [
                                    {
                                        "autoIncrement": true,
                                        "comment": "ID",
                                        "defaultValue": null,
                                        "displaySize": 19,
                                        "name": "id",
                                        "numericPrecision": 0,
                                        "orderKey": 0,
                                        "partOfFk": false,
                                        "partOfPk": true,
                                        "partOfUniqueIdx": true,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "归属实体",
                                        "defaultValue": null,
                                        "displaySize": 19,
                                        "name": "entity_id",
                                        "numericPrecision": 0,
                                        "orderKey": 1,
                                        "partOfFk": true,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "对应列",
                                        "defaultValue": null,
                                        "displaySize": 19,
                                        "name": "column_id",
                                        "numericPrecision": 0,
                                        "orderKey": 2,
                                        "partOfFk": true,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": false,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "属性名",
                                        "defaultValue": null,
                                        "displaySize": 500,
                                        "name": "name",
                                        "numericPrecision": 0,
                                        "orderKey": 3,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "属性注释",
                                        "defaultValue": null,
                                        "displaySize": 500,
                                        "name": "comment",
                                        "numericPrecision": 0,
                                        "orderKey": 4,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "属性类型",
                                        "defaultValue": null,
                                        "displaySize": 500,
                                        "name": "type",
                                        "numericPrecision": 0,
                                        "orderKey": 5,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "类型对应表",
                                        "defaultValue": null,
                                        "displaySize": 19,
                                        "name": "type_table_id",
                                        "numericPrecision": 0,
                                        "orderKey": 6,
                                        "partOfFk": true,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": false,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "是否列表",
                                        "defaultValue": "0",
                                        "displaySize": 1,
                                        "name": "list_type",
                                        "numericPrecision": 0,
                                        "orderKey": 7,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIT",
                                        "typeCode": -7,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "是否非空",
                                        "defaultValue": "0",
                                        "displaySize": 1,
                                        "name": "type_not_null",
                                        "numericPrecision": 0,
                                        "orderKey": 8,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIT",
                                        "typeCode": -7,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "是否Id",
                                        "defaultValue": "0",
                                        "displaySize": 1,
                                        "name": "id_property",
                                        "numericPrecision": 0,
                                        "orderKey": 9,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIT",
                                        "typeCode": -7,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "Id 生成类型",
                                        "defaultValue": null,
                                        "displaySize": 8,
                                        "name": "id_generation_type",
                                        "numericPrecision": 0,
                                        "orderKey": 10,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "ENUM",
                                        "typeCode": 1,
                                        "typeNotNull": false,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "是否为业务键属性",
                                        "defaultValue": "0",
                                        "displaySize": 1,
                                        "name": "key_property",
                                        "numericPrecision": 0,
                                        "orderKey": 11,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIT",
                                        "typeCode": -7,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "是否为逻辑删除属性",
                                        "defaultValue": "0",
                                        "displaySize": 1,
                                        "name": "logical_delete",
                                        "numericPrecision": 0,
                                        "orderKey": 12,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIT",
                                        "typeCode": -7,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "是否为 ID 视图属性",
                                        "defaultValue": "0",
                                        "displaySize": 1,
                                        "name": "id_view",
                                        "numericPrecision": 0,
                                        "orderKey": 13,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIT",
                                        "typeCode": -7,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "ID 视图注释",
                                        "defaultValue": null,
                                        "displaySize": 500,
                                        "name": "id_view_annotation",
                                        "numericPrecision": 0,
                                        "orderKey": 14,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": false,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "关联类型",
                                        "defaultValue": null,
                                        "displaySize": 12,
                                        "name": "association_type",
                                        "numericPrecision": 0,
                                        "orderKey": 15,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "ENUM",
                                        "typeCode": 1,
                                        "typeNotNull": false,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "关联注释",
                                        "defaultValue": null,
                                        "displaySize": 500,
                                        "name": "association_annotation",
                                        "numericPrecision": 0,
                                        "orderKey": 16,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": false,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "脱钩注释",
                                        "defaultValue": null,
                                        "displaySize": 500,
                                        "name": "dissociate_annotation",
                                        "numericPrecision": 0,
                                        "orderKey": 17,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": false,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "其他注释",
                                        "defaultValue": null,
                                        "displaySize": 500,
                                        "name": "other_annotation",
                                        "numericPrecision": 0,
                                        "orderKey": 18,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": false,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "对应枚举 ID",
                                        "defaultValue": null,
                                        "displaySize": 19,
                                        "name": "enum_id",
                                        "numericPrecision": 0,
                                        "orderKey": 19,
                                        "partOfFk": true,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "BIGINT",
                                        "typeCode": -5,
                                        "typeNotNull": false,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "创建时间",
                                        "defaultValue": "CURRENT_TIMESTAMP",
                                        "displaySize": 19,
                                        "name": "created_time",
                                        "numericPrecision": 0,
                                        "orderKey": 20,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "DATETIME",
                                        "typeCode": 93,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "修改时间",
                                        "defaultValue": "CURRENT_TIMESTAMP",
                                        "displaySize": 19,
                                        "name": "modified_time",
                                        "numericPrecision": 0,
                                        "orderKey": 21,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "DATETIME",
                                        "typeCode": 93,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    },
                                    {
                                        "autoIncrement": false,
                                        "comment": "备注",
                                        "defaultValue": "",
                                        "displaySize": 500,
                                        "name": "remark",
                                        "numericPrecision": 0,
                                        "orderKey": 22,
                                        "partOfFk": false,
                                        "partOfPk": false,
                                        "partOfUniqueIdx": false,
                                        "remark": "",
                                        "type": "VARCHAR",
                                        "typeCode": 12,
                                        "typeNotNull": true,
                                        "logicalDelete": false,
                                        "businessKey": false,
                                        "enumId": null
                                    }
                                ]
                            },
                            "wrapper": {
                                "__v_isShallow": false,
                                "__v_isRef": true,
                                "_rawValue": {},
                                "_value": {}
                            }
                        },
                        "ports": {
                            "groups": {
                                "COLUMN_PORT_GROUP": {
                                    "position": "COLUMN_PORT",
                                    "markup": [
                                        {
                                            "tagName": "rect",
                                            "selector": "COLUMN_PORT_SELECTOR"
                                        }
                                    ],
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "magnet": true,
                                            "fill": "rgba(0,0,0,0)",
                                            "height": 30,
                                            "width": 200
                                        }
                                    }
                                }
                            },
                            "items": [
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": true,
                                            "comment": "ID",
                                            "defaultValue": null,
                                            "displaySize": 19,
                                            "name": "id",
                                            "numericPrecision": 0,
                                            "orderKey": 0,
                                            "partOfFk": false,
                                            "partOfPk": true,
                                            "partOfUniqueIdx": true,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "5eee106c-0a15-4212-b8d3-5cba24b0c4cc",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 347
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "归属实体",
                                            "defaultValue": null,
                                            "displaySize": 19,
                                            "name": "entity_id",
                                            "numericPrecision": 0,
                                            "orderKey": 1,
                                            "partOfFk": true,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "6b6c3222-47de-462b-a5b7-f297d5927609",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 347
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "对应列",
                                            "defaultValue": null,
                                            "displaySize": 19,
                                            "name": "column_id",
                                            "numericPrecision": 0,
                                            "orderKey": 2,
                                            "partOfFk": true,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": false,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "4de08c93-4745-440c-80c9-c48bb1df1b9d",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 347
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "属性名",
                                            "defaultValue": null,
                                            "displaySize": 500,
                                            "name": "name",
                                            "numericPrecision": 0,
                                            "orderKey": 3,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "64e11e3d-a717-4afc-a638-5814373574b7",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 347
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "属性注释",
                                            "defaultValue": null,
                                            "displaySize": 500,
                                            "name": "comment",
                                            "numericPrecision": 0,
                                            "orderKey": 4,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "9f535559-212d-447f-929d-02ff7db9bd2c",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 347
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "属性类型",
                                            "defaultValue": null,
                                            "displaySize": 500,
                                            "name": "type",
                                            "numericPrecision": 0,
                                            "orderKey": 5,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "9b30ef98-36c3-4f77-9de0-3f8daed09a83",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 347
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "类型对应表",
                                            "defaultValue": null,
                                            "displaySize": 19,
                                            "name": "type_table_id",
                                            "numericPrecision": 0,
                                            "orderKey": 6,
                                            "partOfFk": true,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": false,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "5ebf3f0a-751e-476c-b1ce-bed97126e4dc",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 347
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "是否列表",
                                            "defaultValue": "0",
                                            "displaySize": 1,
                                            "name": "list_type",
                                            "numericPrecision": 0,
                                            "orderKey": 7,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIT",
                                            "typeCode": -7,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "84875d73-5f95-44ee-a8ee-c628d52dd8df",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 347
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "是否非空",
                                            "defaultValue": "0",
                                            "displaySize": 1,
                                            "name": "type_not_null",
                                            "numericPrecision": 0,
                                            "orderKey": 8,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIT",
                                            "typeCode": -7,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "50100f74-bb9f-4a98-b78c-56de5370c3ba",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 347
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "是否Id",
                                            "defaultValue": "0",
                                            "displaySize": 1,
                                            "name": "id_property",
                                            "numericPrecision": 0,
                                            "orderKey": 9,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIT",
                                            "typeCode": -7,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "f4fda122-f759-4fec-aa9b-eb27f6fd04da",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 347
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "Id 生成类型",
                                            "defaultValue": null,
                                            "displaySize": 8,
                                            "name": "id_generation_type",
                                            "numericPrecision": 0,
                                            "orderKey": 10,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "ENUM",
                                            "typeCode": 1,
                                            "typeNotNull": false,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "1f5b2342-8095-4382-937b-9bb3c74afb81",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 347
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "是否为业务键属性",
                                            "defaultValue": "0",
                                            "displaySize": 1,
                                            "name": "key_property",
                                            "numericPrecision": 0,
                                            "orderKey": 11,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIT",
                                            "typeCode": -7,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "df3105dd-e645-46d2-82e0-52c4cf3e3a6f",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 347
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "是否为逻辑删除属性",
                                            "defaultValue": "0",
                                            "displaySize": 1,
                                            "name": "logical_delete",
                                            "numericPrecision": 0,
                                            "orderKey": 12,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIT",
                                            "typeCode": -7,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "583f413f-fb5b-4295-9f1e-80e8c88a4363",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 347
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "是否为 ID 视图属性",
                                            "defaultValue": "0",
                                            "displaySize": 1,
                                            "name": "id_view",
                                            "numericPrecision": 0,
                                            "orderKey": 13,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIT",
                                            "typeCode": -7,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "b5c20431-5829-4a51-85bf-40d4ae2dd69f",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 347
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "ID 视图注释",
                                            "defaultValue": null,
                                            "displaySize": 500,
                                            "name": "id_view_annotation",
                                            "numericPrecision": 0,
                                            "orderKey": 14,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": false,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "0ad00b1e-c984-49c8-a56a-7a02042fbbd3",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 347
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "关联类型",
                                            "defaultValue": null,
                                            "displaySize": 12,
                                            "name": "association_type",
                                            "numericPrecision": 0,
                                            "orderKey": 15,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "ENUM",
                                            "typeCode": 1,
                                            "typeNotNull": false,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "4cb707f6-a848-4860-98b7-02910d2f054f",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 347
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "关联注释",
                                            "defaultValue": null,
                                            "displaySize": 500,
                                            "name": "association_annotation",
                                            "numericPrecision": 0,
                                            "orderKey": 16,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": false,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "24aa8c95-2491-48e6-b9ea-f00f35d5b7d9",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 347
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "脱钩注释",
                                            "defaultValue": null,
                                            "displaySize": 500,
                                            "name": "dissociate_annotation",
                                            "numericPrecision": 0,
                                            "orderKey": 17,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": false,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "724e1ee3-268e-4e93-a4fc-c615926ea0e2",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 347
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "其他注释",
                                            "defaultValue": null,
                                            "displaySize": 500,
                                            "name": "other_annotation",
                                            "numericPrecision": 0,
                                            "orderKey": 18,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": false,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "e723603e-40d2-49fd-9b78-07f19171e5df",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 347
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "对应枚举 ID",
                                            "defaultValue": null,
                                            "displaySize": 19,
                                            "name": "enum_id",
                                            "numericPrecision": 0,
                                            "orderKey": 19,
                                            "partOfFk": true,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "BIGINT",
                                            "typeCode": -5,
                                            "typeNotNull": false,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "69afb4a1-e4cf-4942-95e7-ac6cd9fa3b5b",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 347
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "创建时间",
                                            "defaultValue": "CURRENT_TIMESTAMP",
                                            "displaySize": 19,
                                            "name": "created_time",
                                            "numericPrecision": 0,
                                            "orderKey": 20,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "DATETIME",
                                            "typeCode": 93,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "2e87d656-8ef1-4bba-beff-c4ad31ad8f42",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 347
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "修改时间",
                                            "defaultValue": "CURRENT_TIMESTAMP",
                                            "displaySize": 19,
                                            "name": "modified_time",
                                            "numericPrecision": 0,
                                            "orderKey": 21,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "DATETIME",
                                            "typeCode": 93,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "f830f49f-482c-4c68-8fdb-5e06adc316db",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 347
                                        }
                                    }
                                },
                                {
                                    "group": "COLUMN_PORT_GROUP",
                                    "data": {
                                        "column": {
                                            "autoIncrement": false,
                                            "comment": "备注",
                                            "defaultValue": "",
                                            "displaySize": 500,
                                            "name": "remark",
                                            "numericPrecision": 0,
                                            "orderKey": 22,
                                            "partOfFk": false,
                                            "partOfPk": false,
                                            "partOfUniqueIdx": false,
                                            "remark": "",
                                            "type": "VARCHAR",
                                            "typeCode": 12,
                                            "typeNotNull": true,
                                            "logicalDelete": false,
                                            "businessKey": false,
                                            "enumId": null
                                        }
                                    },
                                    "id": "c5c1c889-a5cf-4112-9634-136e34f4a5ab",
                                    "attrs": {
                                        "COLUMN_PORT_SELECTOR": {
                                            "width": 347
                                        }
                                    }
                                }
                            ]
                        },
                        "zIndex": 70
                    },
                    {
                        "shape": "edge",
                        "attrs": {
                            "line": {
                                "stroke": "var(--common-color)",
                                "strokeWidth": 1
                            }
                        },
                        "id": "de9543d8-a3fb-4240-b618-643e6ed360e6",
                        "labels": [
                            {
                                "markup": [
                                    {
                                        "tagName": "text",
                                        "selector": "ASSOCIATION_LABEL_TEXT_SELECTOR"
                                    }
                                ],
                                "attrs": {
                                    "ASSOCIATION_LABEL_TEXT_SELECTOR": {
                                        "text": "MANY_TO_ONE"
                                    }
                                }
                            }
                        ],
                        "data": {
                            "selectFlag": false,
                            "association": {
                                "sourceColumn": {
                                    "name": "model_id",
                                    "comment": "模型",
                                    "type": "BIGINT",
                                    "typeCode": -5,
                                    "table": {
                                        "name": "gen_association",
                                        "comment": "生成关联"
                                    }
                                },
                                "targetColumn": {
                                    "name": "id",
                                    "comment": "ID",
                                    "type": "BIGINT",
                                    "typeCode": -5,
                                    "table": {
                                        "name": "gen_model",
                                        "comment": "生成模型"
                                    }
                                },
                                "associationType": "MANY_TO_ONE",
                                "fake": true,
                                "comment": ""
                            }
                        },
                        "source": {
                            "cell": "464e99e9-b5c8-449b-947b-381ac0eecd08",
                            "port": "2727b136-c99a-40cb-9970-49df21307b29"
                        },
                        "target": {
                            "cell": "ec6f4d80-4718-4700-965e-cecbd87c8bf4",
                            "port": "b436dcf2-7bb8-4a72-93a6-677de8d7bcbe"
                        },
                        "router": {
                            "name": "er",
                            "args": {
                                "direction": "H"
                            }
                        },
                        "zIndex": 75
                    },
                    {
                        "shape": "edge",
                        "attrs": {
                            "line": {
                                "stroke": "var(--common-color)",
                                "strokeWidth": 1
                            }
                        },
                        "id": "7bb14818-4e0e-4428-bd1c-d7ffe2eaf6a4",
                        "labels": [
                            {
                                "markup": [
                                    {
                                        "tagName": "text",
                                        "selector": "ASSOCIATION_LABEL_TEXT_SELECTOR"
                                    }
                                ],
                                "attrs": {
                                    "ASSOCIATION_LABEL_TEXT_SELECTOR": {
                                        "text": "MANY_TO_ONE"
                                    }
                                }
                            }
                        ],
                        "data": {
                            "selectFlag": false,
                            "association": {
                                "sourceColumn": {
                                    "name": "model_id",
                                    "comment": "模型",
                                    "type": "BIGINT",
                                    "typeCode": -5,
                                    "table": {
                                        "name": "gen_table",
                                        "comment": "生成表"
                                    }
                                },
                                "targetColumn": {
                                    "name": "id",
                                    "comment": "ID",
                                    "type": "BIGINT",
                                    "typeCode": -5,
                                    "table": {
                                        "name": "gen_model",
                                        "comment": "生成模型"
                                    }
                                },
                                "associationType": "MANY_TO_ONE",
                                "fake": true,
                                "comment": ""
                            }
                        },
                        "source": {
                            "cell": "fe9d3326-8b41-4c58-b223-dfb8ab46be47",
                            "port": "15135935-8b5d-4ba7-9edf-2d1cfebf301c"
                        },
                        "target": {
                            "cell": "ec6f4d80-4718-4700-965e-cecbd87c8bf4",
                            "port": "b436dcf2-7bb8-4a72-93a6-677de8d7bcbe"
                        },
                        "router": {
                            "name": "er",
                            "args": {
                                "direction": "H"
                            }
                        },
                        "zIndex": 76
                    },
                    {
                        "shape": "edge",
                        "attrs": {
                            "line": {
                                "stroke": "var(--common-color)",
                                "strokeWidth": 1
                            }
                        },
                        "id": "7128ad7c-0675-4ec7-8a88-91620f4e443e",
                        "labels": [
                            {
                                "markup": [
                                    {
                                        "tagName": "text",
                                        "selector": "ASSOCIATION_LABEL_TEXT_SELECTOR"
                                    }
                                ],
                                "attrs": {
                                    "ASSOCIATION_LABEL_TEXT_SELECTOR": {
                                        "text": "MANY_TO_ONE"
                                    }
                                }
                            }
                        ],
                        "data": {
                            "selectFlag": false,
                            "association": {
                                "sourceColumn": {
                                    "name": "parent_id",
                                    "comment": "父包",
                                    "type": "BIGINT",
                                    "typeCode": -5,
                                    "table": {
                                        "name": "gen_package",
                                        "comment": "生成包"
                                    }
                                },
                                "targetColumn": {
                                    "name": "id",
                                    "comment": "ID",
                                    "type": "BIGINT",
                                    "typeCode": -5,
                                    "table": {
                                        "name": "gen_package",
                                        "comment": "生成包"
                                    }
                                },
                                "associationType": "MANY_TO_ONE",
                                "fake": true,
                                "comment": ""
                            }
                        },
                        "source": {
                            "cell": "a538e2be-44a5-49de-b08f-cbadeb2ec1aa",
                            "port": "7ee2608d-0fdc-48de-aa6f-06132f1064fa"
                        },
                        "target": {
                            "cell": "a538e2be-44a5-49de-b08f-cbadeb2ec1aa",
                            "port": "9baeca5d-5955-462d-9669-fba15a7b42d5"
                        },
                        "router": {
                            "name": "orth",
                            "args": {
                                "padding": 20
                            }
                        },
                        "zIndex": 77,
                        "parent": "a538e2be-44a5-49de-b08f-cbadeb2ec1aa"
                    },
                    {
                        "shape": "edge",
                        "attrs": {
                            "line": {
                                "stroke": "var(--common-color)",
                                "strokeWidth": 1
                            }
                        },
                        "id": "9d7c9e14-6138-401c-b6ea-fbd9764d15af",
                        "labels": [
                            {
                                "markup": [
                                    {
                                        "tagName": "text",
                                        "selector": "ASSOCIATION_LABEL_TEXT_SELECTOR"
                                    }
                                ],
                                "attrs": {
                                    "ASSOCIATION_LABEL_TEXT_SELECTOR": {
                                        "text": "MANY_TO_ONE"
                                    }
                                }
                            }
                        ],
                        "data": {
                            "selectFlag": false,
                            "association": {
                                "sourceColumn": {
                                    "name": "package_id",
                                    "comment": "所属包",
                                    "type": "BIGINT",
                                    "typeCode": -5,
                                    "table": {
                                        "name": "gen_entity",
                                        "comment": "生成实体"
                                    }
                                },
                                "targetColumn": {
                                    "name": "id",
                                    "comment": "ID",
                                    "type": "BIGINT",
                                    "typeCode": -5,
                                    "table": {
                                        "name": "gen_package",
                                        "comment": "生成包"
                                    }
                                },
                                "associationType": "MANY_TO_ONE",
                                "fake": true,
                                "comment": ""
                            }
                        },
                        "source": {
                            "cell": "020ce872-345f-4c33-8d9b-441ae58af661",
                            "port": "b3328fe8-86f6-4d07-9c59-a9984de4e336"
                        },
                        "target": {
                            "cell": "a538e2be-44a5-49de-b08f-cbadeb2ec1aa",
                            "port": "9baeca5d-5955-462d-9669-fba15a7b42d5"
                        },
                        "router": {
                            "name": "er",
                            "args": {
                                "direction": "H"
                            }
                        },
                        "zIndex": 78
                    },
                    {
                        "shape": "edge",
                        "attrs": {
                            "line": {
                                "stroke": "var(--common-color)",
                                "strokeWidth": 1
                            }
                        },
                        "id": "ef2d0c47-7f7f-4b87-9a10-c99252d25570",
                        "labels": [
                            {
                                "markup": [
                                    {
                                        "tagName": "text",
                                        "selector": "ASSOCIATION_LABEL_TEXT_SELECTOR"
                                    }
                                ],
                                "attrs": {
                                    "ASSOCIATION_LABEL_TEXT_SELECTOR": {
                                        "text": "MANY_TO_ONE"
                                    }
                                }
                            }
                        ],
                        "data": {
                            "selectFlag": false,
                            "association": {
                                "sourceColumn": {
                                    "name": "schema_id",
                                    "comment": "数据架构",
                                    "type": "BIGINT",
                                    "typeCode": -5,
                                    "table": {
                                        "name": "gen_table",
                                        "comment": "生成表"
                                    }
                                },
                                "targetColumn": {
                                    "name": "id",
                                    "comment": "ID",
                                    "type": "BIGINT",
                                    "typeCode": -5,
                                    "table": {
                                        "name": "gen_schema",
                                        "comment": "生成数据架构"
                                    }
                                },
                                "associationType": "MANY_TO_ONE",
                                "fake": true,
                                "comment": ""
                            }
                        },
                        "source": {
                            "cell": "fe9d3326-8b41-4c58-b223-dfb8ab46be47",
                            "port": "6dc78be2-b8da-44b7-a827-0d78aa8a03f9"
                        },
                        "target": {
                            "cell": "92a329a3-384d-48cd-81db-4a985d697f41",
                            "port": "ec249065-eb81-4d7e-992f-f6f44fb99723"
                        },
                        "router": {
                            "name": "er",
                            "args": {
                                "direction": "H"
                            }
                        },
                        "zIndex": 79
                    },
                    {
                        "shape": "edge",
                        "attrs": {
                            "line": {
                                "stroke": "var(--common-color)",
                                "strokeWidth": 1
                            }
                        },
                        "id": "8376326d-77d0-4a34-a9a8-e637f775a938",
                        "labels": [
                            {
                                "markup": [
                                    {
                                        "tagName": "text",
                                        "selector": "ASSOCIATION_LABEL_TEXT_SELECTOR"
                                    }
                                ],
                                "attrs": {
                                    "ASSOCIATION_LABEL_TEXT_SELECTOR": {
                                        "text": "MANY_TO_ONE"
                                    }
                                }
                            }
                        ],
                        "data": {
                            "selectFlag": false,
                            "association": {
                                "sourceColumn": {
                                    "name": "table_id",
                                    "comment": "归属表",
                                    "type": "BIGINT",
                                    "typeCode": -5,
                                    "table": {
                                        "name": "gen_column",
                                        "comment": "生成列"
                                    }
                                },
                                "targetColumn": {
                                    "name": "id",
                                    "comment": "ID",
                                    "type": "BIGINT",
                                    "typeCode": -5,
                                    "table": {
                                        "name": "gen_table",
                                        "comment": "生成表"
                                    }
                                },
                                "associationType": "MANY_TO_ONE",
                                "fake": true,
                                "comment": ""
                            }
                        },
                        "source": {
                            "cell": "b020de7f-6cbb-4433-b911-a36670eebd0b",
                            "port": "7c31bbd6-1d83-4bac-84bb-d801917304c9"
                        },
                        "target": {
                            "cell": "fe9d3326-8b41-4c58-b223-dfb8ab46be47",
                            "port": "96f5db81-5abb-4df7-a870-d074bd0dcd49"
                        },
                        "router": {
                            "name": "er",
                            "args": {
                                "direction": "H"
                            }
                        },
                        "zIndex": 80
                    },
                    {
                        "shape": "edge",
                        "attrs": {
                            "line": {
                                "stroke": "var(--common-color)",
                                "strokeWidth": 1
                            }
                        },
                        "id": "ae3f26c9-3a15-4ce0-939c-b7e3a9745110",
                        "labels": [
                            {
                                "markup": [
                                    {
                                        "tagName": "text",
                                        "selector": "ASSOCIATION_LABEL_TEXT_SELECTOR"
                                    }
                                ],
                                "attrs": {
                                    "ASSOCIATION_LABEL_TEXT_SELECTOR": {
                                        "text": "MANY_TO_ONE"
                                    }
                                }
                            }
                        ],
                        "data": {
                            "selectFlag": false,
                            "association": {
                                "sourceColumn": {
                                    "name": "table_id",
                                    "comment": "对应表",
                                    "type": "BIGINT",
                                    "typeCode": -5,
                                    "table": {
                                        "name": "gen_entity",
                                        "comment": "生成实体"
                                    }
                                },
                                "targetColumn": {
                                    "name": "id",
                                    "comment": "ID",
                                    "type": "BIGINT",
                                    "typeCode": -5,
                                    "table": {
                                        "name": "gen_table",
                                        "comment": "生成表"
                                    }
                                },
                                "associationType": "MANY_TO_ONE",
                                "fake": true,
                                "comment": ""
                            }
                        },
                        "source": {
                            "cell": "020ce872-345f-4c33-8d9b-441ae58af661",
                            "port": "ba57dad2-f10d-408a-a102-bb6c1b53e784"
                        },
                        "target": {
                            "cell": "fe9d3326-8b41-4c58-b223-dfb8ab46be47",
                            "port": "96f5db81-5abb-4df7-a870-d074bd0dcd49"
                        },
                        "router": {
                            "name": "er",
                            "args": {
                                "direction": "H"
                            }
                        },
                        "zIndex": 81
                    },
                    {
                        "shape": "edge",
                        "attrs": {
                            "line": {
                                "stroke": "var(--common-color)",
                                "strokeWidth": 1
                            }
                        },
                        "id": "550df994-c446-431d-84da-36a489823987",
                        "labels": [
                            {
                                "markup": [
                                    {
                                        "tagName": "text",
                                        "selector": "ASSOCIATION_LABEL_TEXT_SELECTOR"
                                    }
                                ],
                                "attrs": {
                                    "ASSOCIATION_LABEL_TEXT_SELECTOR": {
                                        "text": "MANY_TO_ONE"
                                    }
                                }
                            }
                        ],
                        "data": {
                            "selectFlag": false,
                            "association": {
                                "sourceColumn": {
                                    "name": "type_table_id",
                                    "comment": "类型对应表",
                                    "type": "BIGINT",
                                    "typeCode": -5,
                                    "table": {
                                        "name": "gen_property",
                                        "comment": "生成属性"
                                    }
                                },
                                "targetColumn": {
                                    "name": "id",
                                    "comment": "ID",
                                    "type": "BIGINT",
                                    "typeCode": -5,
                                    "table": {
                                        "name": "gen_table",
                                        "comment": "生成表"
                                    }
                                },
                                "associationType": "MANY_TO_ONE",
                                "fake": true,
                                "comment": ""
                            }
                        },
                        "source": {
                            "cell": "c5fae437-0e8e-4e58-a29f-0e18de2c1e68",
                            "port": "5ebf3f0a-751e-476c-b1ce-bed97126e4dc"
                        },
                        "target": {
                            "cell": "fe9d3326-8b41-4c58-b223-dfb8ab46be47",
                            "port": "96f5db81-5abb-4df7-a870-d074bd0dcd49"
                        },
                        "router": {
                            "name": "er",
                            "args": {
                                "direction": "H"
                            }
                        },
                        "zIndex": 82
                    },
                    {
                        "shape": "edge",
                        "attrs": {
                            "line": {
                                "stroke": "var(--common-color)",
                                "strokeWidth": 1
                            }
                        },
                        "id": "96cf5183-8087-49f7-96be-7984ea7001c2",
                        "labels": [
                            {
                                "markup": [
                                    {
                                        "tagName": "text",
                                        "selector": "ASSOCIATION_LABEL_TEXT_SELECTOR"
                                    }
                                ],
                                "attrs": {
                                    "ASSOCIATION_LABEL_TEXT_SELECTOR": {
                                        "text": "MANY_TO_ONE"
                                    }
                                }
                            }
                        ],
                        "data": {
                            "selectFlag": false,
                            "association": {
                                "sourceColumn": {
                                    "name": "source_column_id",
                                    "comment": "主列",
                                    "type": "BIGINT",
                                    "typeCode": -5,
                                    "table": {
                                        "name": "gen_association",
                                        "comment": "生成关联"
                                    }
                                },
                                "targetColumn": {
                                    "name": "id",
                                    "comment": "ID",
                                    "type": "BIGINT",
                                    "typeCode": -5,
                                    "table": {
                                        "name": "gen_column",
                                        "comment": "生成列"
                                    }
                                },
                                "associationType": "MANY_TO_ONE",
                                "fake": true,
                                "comment": ""
                            }
                        },
                        "source": {
                            "cell": "464e99e9-b5c8-449b-947b-381ac0eecd08",
                            "port": "a340e8c1-6142-4fe2-a17f-7510b4806f63"
                        },
                        "target": {
                            "cell": "b020de7f-6cbb-4433-b911-a36670eebd0b",
                            "port": "10f0ce55-5edb-4265-89f1-59a05d85279e"
                        },
                        "router": {
                            "name": "er",
                            "args": {
                                "direction": "H"
                            }
                        },
                        "zIndex": 83
                    },
                    {
                        "shape": "edge",
                        "attrs": {
                            "line": {
                                "stroke": "var(--common-color)",
                                "strokeWidth": 1
                            }
                        },
                        "id": "4b8b653b-dd11-4337-85b5-273472732067",
                        "labels": [
                            {
                                "markup": [
                                    {
                                        "tagName": "text",
                                        "selector": "ASSOCIATION_LABEL_TEXT_SELECTOR"
                                    }
                                ],
                                "attrs": {
                                    "ASSOCIATION_LABEL_TEXT_SELECTOR": {
                                        "text": "MANY_TO_ONE"
                                    }
                                }
                            }
                        ],
                        "data": {
                            "selectFlag": false,
                            "association": {
                                "sourceColumn": {
                                    "name": "target_column_id",
                                    "comment": "从列",
                                    "type": "BIGINT",
                                    "typeCode": -5,
                                    "table": {
                                        "name": "gen_association",
                                        "comment": "生成关联"
                                    }
                                },
                                "targetColumn": {
                                    "name": "id",
                                    "comment": "ID",
                                    "type": "BIGINT",
                                    "typeCode": -5,
                                    "table": {
                                        "name": "gen_column",
                                        "comment": "生成列"
                                    }
                                },
                                "associationType": "MANY_TO_ONE",
                                "fake": true,
                                "comment": ""
                            }
                        },
                        "source": {
                            "cell": "464e99e9-b5c8-449b-947b-381ac0eecd08",
                            "port": "4a68f4ea-1666-4679-88e2-c057c5139eab"
                        },
                        "target": {
                            "cell": "b020de7f-6cbb-4433-b911-a36670eebd0b",
                            "port": "10f0ce55-5edb-4265-89f1-59a05d85279e"
                        },
                        "router": {
                            "name": "er",
                            "args": {
                                "direction": "H"
                            }
                        },
                        "zIndex": 84
                    },
                    {
                        "shape": "edge",
                        "attrs": {
                            "line": {
                                "stroke": "var(--common-color)",
                                "strokeWidth": 1
                            }
                        },
                        "id": "bc724117-02ae-42ab-aa89-fe9a02a8154d",
                        "labels": [
                            {
                                "markup": [
                                    {
                                        "tagName": "text",
                                        "selector": "ASSOCIATION_LABEL_TEXT_SELECTOR"
                                    }
                                ],
                                "attrs": {
                                    "ASSOCIATION_LABEL_TEXT_SELECTOR": {
                                        "text": "MANY_TO_ONE"
                                    }
                                }
                            }
                        ],
                        "data": {
                            "selectFlag": false,
                            "association": {
                                "sourceColumn": {
                                    "name": "column_id",
                                    "comment": "对应列",
                                    "type": "BIGINT",
                                    "typeCode": -5,
                                    "table": {
                                        "name": "gen_property",
                                        "comment": "生成属性"
                                    }
                                },
                                "targetColumn": {
                                    "name": "id",
                                    "comment": "ID",
                                    "type": "BIGINT",
                                    "typeCode": -5,
                                    "table": {
                                        "name": "gen_column",
                                        "comment": "生成列"
                                    }
                                },
                                "associationType": "MANY_TO_ONE",
                                "fake": true,
                                "comment": ""
                            }
                        },
                        "source": {
                            "cell": "c5fae437-0e8e-4e58-a29f-0e18de2c1e68",
                            "port": "4de08c93-4745-440c-80c9-c48bb1df1b9d"
                        },
                        "target": {
                            "cell": "b020de7f-6cbb-4433-b911-a36670eebd0b",
                            "port": "10f0ce55-5edb-4265-89f1-59a05d85279e"
                        },
                        "router": {
                            "name": "er",
                            "args": {
                                "direction": "H"
                            }
                        },
                        "zIndex": 85
                    },
                    {
                        "shape": "edge",
                        "attrs": {
                            "line": {
                                "stroke": "var(--common-color)",
                                "strokeWidth": 1
                            }
                        },
                        "id": "e403b80f-58ad-4546-ae14-91dcc14a2351",
                        "labels": [
                            {
                                "markup": [
                                    {
                                        "tagName": "text",
                                        "selector": "ASSOCIATION_LABEL_TEXT_SELECTOR"
                                    }
                                ],
                                "attrs": {
                                    "ASSOCIATION_LABEL_TEXT_SELECTOR": {
                                        "text": "MANY_TO_ONE"
                                    }
                                }
                            }
                        ],
                        "data": {
                            "selectFlag": false,
                            "association": {
                                "sourceColumn": {
                                    "name": "entity_id",
                                    "comment": "归属实体",
                                    "type": "BIGINT",
                                    "typeCode": -5,
                                    "table": {
                                        "name": "gen_property",
                                        "comment": "生成属性"
                                    }
                                },
                                "targetColumn": {
                                    "name": "id",
                                    "comment": "ID",
                                    "type": "BIGINT",
                                    "typeCode": -5,
                                    "table": {
                                        "name": "gen_entity",
                                        "comment": "生成实体"
                                    }
                                },
                                "associationType": "MANY_TO_ONE",
                                "fake": true,
                                "comment": ""
                            }
                        },
                        "source": {
                            "cell": "c5fae437-0e8e-4e58-a29f-0e18de2c1e68",
                            "port": "6b6c3222-47de-462b-a5b7-f297d5927609"
                        },
                        "target": {
                            "cell": "020ce872-345f-4c33-8d9b-441ae58af661",
                            "port": "5b9a48ad-4085-4d0a-b6e6-0d54f27f84c8"
                        },
                        "router": {
                            "name": "er",
                            "args": {
                                "direction": "H"
                            }
                        },
                        "zIndex": 86
                    },
                    {
                        "shape": "edge",
                        "attrs": {
                            "line": {
                                "stroke": "var(--common-color)",
                                "strokeWidth": 1
                            }
                        },
                        "id": "914231e5-2047-4386-88b4-cd69df97e46b",
                        "labels": [
                            {
                                "markup": [
                                    {
                                        "tagName": "text",
                                        "selector": "ASSOCIATION_LABEL_TEXT_SELECTOR"
                                    }
                                ],
                                "attrs": {
                                    "ASSOCIATION_LABEL_TEXT_SELECTOR": {
                                        "text": "MANY_TO_ONE"
                                    }
                                }
                            }
                        ],
                        "data": {
                            "selectFlag": false,
                            "association": {
                                "sourceColumn": {
                                    "name": "data_source_id",
                                    "comment": "数据源 ID",
                                    "type": "BIGINT",
                                    "typeCode": -5,
                                    "table": {
                                        "name": "gen_schema",
                                        "comment": "生成数据架构"
                                    }
                                },
                                "targetColumn": {
                                    "name": "id",
                                    "comment": "ID",
                                    "type": "BIGINT",
                                    "typeCode": -5,
                                    "table": {
                                        "name": "gen_data_source",
                                        "comment": "生成数据源"
                                    }
                                },
                                "associationType": "MANY_TO_ONE",
                                "fake": true,
                                "comment": ""
                            }
                        },
                        "source": {
                            "cell": "92a329a3-384d-48cd-81db-4a985d697f41",
                            "port": "15029b79-196f-45c0-a1b4-f889a2ac2606"
                        },
                        "target": {
                            "cell": "97e63e91-b085-4d6a-8e8c-5e0bb0de4dd4",
                            "port": "eeb0f173-178d-4ed3-a13b-e63f309d6eda"
                        },
                        "router": {
                            "name": "er",
                            "args": {
                                "direction": "H"
                            }
                        },
                        "zIndex": 87
                    },
                    {
                        "shape": "edge",
                        "attrs": {
                            "line": {
                                "stroke": "var(--common-color)",
                                "strokeWidth": 1
                            }
                        },
                        "id": "477adf9d-8b5a-4419-a49e-df961b7ac233",
                        "labels": [
                            {
                                "markup": [
                                    {
                                        "tagName": "text",
                                        "selector": "ASSOCIATION_LABEL_TEXT_SELECTOR"
                                    }
                                ],
                                "attrs": {
                                    "ASSOCIATION_LABEL_TEXT_SELECTOR": {
                                        "text": "MANY_TO_ONE"
                                    }
                                }
                            }
                        ],
                        "data": {
                            "selectFlag": false,
                            "association": {
                                "sourceColumn": {
                                    "name": "package_id",
                                    "comment": "所属包",
                                    "type": "BIGINT",
                                    "typeCode": -5,
                                    "table": {
                                        "name": "gen_enum",
                                        "comment": "生成枚举"
                                    }
                                },
                                "targetColumn": {
                                    "name": "id",
                                    "comment": "ID",
                                    "type": "BIGINT",
                                    "typeCode": -5,
                                    "table": {
                                        "name": "gen_package",
                                        "comment": "生成包"
                                    }
                                },
                                "associationType": "MANY_TO_ONE",
                                "fake": true,
                                "comment": ""
                            }
                        },
                        "source": {
                            "cell": "726f2031-4ef0-4938-b93b-ca45c85c04cc",
                            "port": "19cb6e0b-7fd3-4522-becc-694d62da3617"
                        },
                        "target": {
                            "cell": "a538e2be-44a5-49de-b08f-cbadeb2ec1aa",
                            "port": "9baeca5d-5955-462d-9669-fba15a7b42d5"
                        },
                        "router": {
                            "name": "er",
                            "args": {
                                "direction": "H"
                            }
                        },
                        "zIndex": 88
                    },
                    {
                        "shape": "edge",
                        "attrs": {
                            "line": {
                                "stroke": "var(--common-color)",
                                "strokeWidth": 1
                            }
                        },
                        "id": "bc24303d-8d3b-4b12-a785-e8ffa316f73b",
                        "labels": [
                            {
                                "markup": [
                                    {
                                        "tagName": "text",
                                        "selector": "ASSOCIATION_LABEL_TEXT_SELECTOR"
                                    }
                                ],
                                "attrs": {
                                    "ASSOCIATION_LABEL_TEXT_SELECTOR": {
                                        "text": "MANY_TO_ONE"
                                    }
                                }
                            }
                        ],
                        "data": {
                            "selectFlag": false,
                            "association": {
                                "sourceColumn": {
                                    "name": "enum_id",
                                    "comment": "对应枚举 ID",
                                    "type": "BIGINT",
                                    "typeCode": -5,
                                    "table": {
                                        "name": "gen_column",
                                        "comment": "生成列"
                                    }
                                },
                                "targetColumn": {
                                    "name": "id",
                                    "comment": "ID",
                                    "type": "BIGINT",
                                    "typeCode": -5,
                                    "table": {
                                        "name": "gen_enum",
                                        "comment": "生成枚举"
                                    }
                                },
                                "associationType": "MANY_TO_ONE",
                                "fake": true,
                                "comment": ""
                            }
                        },
                        "source": {
                            "cell": "b020de7f-6cbb-4433-b911-a36670eebd0b",
                            "port": "57589ff7-4290-4c1b-b351-546e6a50db65"
                        },
                        "target": {
                            "cell": "726f2031-4ef0-4938-b93b-ca45c85c04cc",
                            "port": "210a646b-e829-4a6b-8a1f-675088087bd6"
                        },
                        "router": {
                            "name": "er",
                            "args": {
                                "direction": "H"
                            }
                        },
                        "zIndex": 89
                    },
                    {
                        "shape": "edge",
                        "attrs": {
                            "line": {
                                "stroke": "var(--common-color)",
                                "strokeWidth": 1
                            }
                        },
                        "id": "895100f7-0af6-4c47-91e2-d815dfece7cc",
                        "labels": [
                            {
                                "markup": [
                                    {
                                        "tagName": "text",
                                        "selector": "ASSOCIATION_LABEL_TEXT_SELECTOR"
                                    }
                                ],
                                "attrs": {
                                    "ASSOCIATION_LABEL_TEXT_SELECTOR": {
                                        "text": "MANY_TO_ONE"
                                    }
                                }
                            }
                        ],
                        "data": {
                            "selectFlag": false,
                            "association": {
                                "sourceColumn": {
                                    "name": "enum_id",
                                    "comment": "对应枚举 ID",
                                    "type": "BIGINT",
                                    "typeCode": -5,
                                    "table": {
                                        "name": "gen_property",
                                        "comment": "生成属性"
                                    }
                                },
                                "targetColumn": {
                                    "name": "id",
                                    "comment": "ID",
                                    "type": "BIGINT",
                                    "typeCode": -5,
                                    "table": {
                                        "name": "gen_enum",
                                        "comment": "生成枚举"
                                    }
                                },
                                "associationType": "MANY_TO_ONE",
                                "fake": true,
                                "comment": ""
                            }
                        },
                        "source": {
                            "cell": "c5fae437-0e8e-4e58-a29f-0e18de2c1e68",
                            "port": "69afb4a1-e4cf-4942-95e7-ac6cd9fa3b5b"
                        },
                        "target": {
                            "cell": "726f2031-4ef0-4938-b93b-ca45c85c04cc",
                            "port": "210a646b-e829-4a6b-8a1f-675088087bd6"
                        },
                        "router": {
                            "name": "er",
                            "args": {
                                "direction": "H"
                            }
                        },
                        "zIndex": 90
                    },
                    {
                        "shape": "edge",
                        "attrs": {
                            "line": {
                                "stroke": "var(--common-color)",
                                "strokeWidth": 1
                            }
                        },
                        "id": "bf90a9ec-1c51-4f26-bdb7-7015f30e4e25",
                        "labels": [
                            {
                                "markup": [
                                    {
                                        "tagName": "text",
                                        "selector": "ASSOCIATION_LABEL_TEXT_SELECTOR"
                                    }
                                ],
                                "attrs": {
                                    "ASSOCIATION_LABEL_TEXT_SELECTOR": {
                                        "text": "MANY_TO_ONE"
                                    }
                                }
                            }
                        ],
                        "data": {
                            "selectFlag": false,
                            "association": {
                                "sourceColumn": {
                                    "name": "enum_id",
                                    "comment": "对应枚举 ID",
                                    "type": "BIGINT",
                                    "typeCode": -5,
                                    "table": {
                                        "name": "gen_enum_item",
                                        "comment": "生成枚举元素"
                                    }
                                },
                                "targetColumn": {
                                    "name": "id",
                                    "comment": "ID",
                                    "type": "BIGINT",
                                    "typeCode": -5,
                                    "table": {
                                        "name": "gen_enum",
                                        "comment": "生成枚举"
                                    }
                                },
                                "associationType": "MANY_TO_ONE",
                                "fake": true,
                                "comment": ""
                            }
                        },
                        "source": {
                            "cell": "2bde67bf-1ef8-4c15-98eb-744e1ff96ce8",
                            "port": "1354f6c0-a135-4f97-905c-ce72473cf688"
                        },
                        "target": {
                            "cell": "726f2031-4ef0-4938-b93b-ca45c85c04cc",
                            "port": "210a646b-e829-4a6b-8a1f-675088087bd6"
                        },
                        "router": {
                            "name": "er",
                            "args": {
                                "direction": "H"
                            }
                        },
                        "zIndex": 91
                    }
                ]
            },
            "zoom": 0.34463669113222034,
            "transform": "matrix(0.34463669113222034,0,0,0.34463669113222034,-3049.5105485732756,-1120.520780846295)"
        }
    """.trimIndent()
}

package top.potmot.model

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.extension.valueToData

@SpringBootTest
@ActiveProfiles("test-kotlin", "mysql")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class TestModelToData {
    @Test
    @Order(1)
    fun testValueData() {
        println(valueToData(modelValue))
    }

    private val modelValue = """
{
    "json": {
        "cells": [
            {
                "position": {
                    "x": 3132.17578125,
                    "y": 2636.684326171875
                },
                "size": {
                    "width": 291,
                    "height": 394
                },
                "view": "vue-shape-view",
                "shape": "model",
                "id": "1b85abb0-66fc-41a8-baca-4c2fc7c48783",
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
                                "defaultValue": "nextval('jimmer_code_gen.gen_data_source_id_seq'::regclass)",
                                "displaySize": 19,
                                "name": "id",
                                "numericPrecision": 0,
                                "orderKey": 0,
                                "partOfFk": false,
                                "partOfPk": true,
                                "partOfUniqueIdx": true,
                                "remark": "",
                                "type": "bigserial",
                                "typeCode": -5,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "数据库类型",
                                "defaultValue": null,
                                "displaySize": 2147483647,
                                "name": "type",
                                "numericPrecision": 0,
                                "orderKey": 1,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "名称",
                                "defaultValue": null,
                                "displaySize": 2147483647,
                                "name": "name",
                                "numericPrecision": 0,
                                "orderKey": 2,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "主机",
                                "defaultValue": null,
                                "displaySize": 2147483647,
                                "name": "host",
                                "numericPrecision": 0,
                                "orderKey": 3,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "端口",
                                "defaultValue": null,
                                "displaySize": 2147483647,
                                "name": "port",
                                "numericPrecision": 0,
                                "orderKey": 4,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "链接后缀",
                                "defaultValue": null,
                                "displaySize": 2147483647,
                                "name": "url_suffix",
                                "numericPrecision": 0,
                                "orderKey": 5,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "用户名",
                                "defaultValue": null,
                                "displaySize": 2147483647,
                                "name": "username",
                                "numericPrecision": 0,
                                "orderKey": 6,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "密码",
                                "defaultValue": null,
                                "displaySize": 2147483647,
                                "name": "password",
                                "numericPrecision": 0,
                                "orderKey": 7,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
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
                                "type": "int8",
                                "typeCode": -5,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "创建时间",
                                "defaultValue": "CURRENT_TIMESTAMP",
                                "displaySize": 35,
                                "name": "created_time",
                                "numericPrecision": 6,
                                "orderKey": 9,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "timestamptz",
                                "typeCode": 93,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "修改时间",
                                "defaultValue": "CURRENT_TIMESTAMP",
                                "displaySize": 35,
                                "name": "modified_time",
                                "numericPrecision": 6,
                                "orderKey": 10,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "timestamptz",
                                "typeCode": 93,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "备注",
                                "defaultValue": "''::text",
                                "displaySize": 2147483647,
                                "name": "remark",
                                "numericPrecision": 0,
                                "orderKey": 11,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
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
                                    "defaultValue": "nextval('jimmer_code_gen.gen_data_source_id_seq'::regclass)",
                                    "displaySize": 19,
                                    "name": "id",
                                    "numericPrecision": 0,
                                    "orderKey": 0,
                                    "partOfFk": false,
                                    "partOfPk": true,
                                    "partOfUniqueIdx": true,
                                    "remark": "",
                                    "type": "bigserial",
                                    "typeCode": -5,
                                    "typeNotNull": true
                                }
                            },
                            "id": "52366d52-4d2d-4ed3-a631-b4473469e526",
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
                                    "comment": "数据库类型",
                                    "defaultValue": null,
                                    "displaySize": 2147483647,
                                    "name": "type",
                                    "numericPrecision": 0,
                                    "orderKey": 1,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "2c9bd886-976b-46cd-ad84-ff7ba7103ad7",
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
                                    "displaySize": 2147483647,
                                    "name": "name",
                                    "numericPrecision": 0,
                                    "orderKey": 2,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "1c7c1f3e-32ac-4163-8895-3ddd1078e8e4",
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
                                    "comment": "主机",
                                    "defaultValue": null,
                                    "displaySize": 2147483647,
                                    "name": "host",
                                    "numericPrecision": 0,
                                    "orderKey": 3,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "c7501629-e28a-42d6-81b2-d41cfb425e2c",
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
                                    "comment": "端口",
                                    "defaultValue": null,
                                    "displaySize": 2147483647,
                                    "name": "port",
                                    "numericPrecision": 0,
                                    "orderKey": 4,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "a96c73a0-2a3a-4dfb-bf2e-750c39803e4e",
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
                                    "comment": "链接后缀",
                                    "defaultValue": null,
                                    "displaySize": 2147483647,
                                    "name": "url_suffix",
                                    "numericPrecision": 0,
                                    "orderKey": 5,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "c72b7618-6d1e-409d-bff8-98af33499275",
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
                                    "comment": "用户名",
                                    "defaultValue": null,
                                    "displaySize": 2147483647,
                                    "name": "username",
                                    "numericPrecision": 0,
                                    "orderKey": 6,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "72afa30c-03e8-4aff-b6cc-6c454fddda0f",
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
                                    "comment": "密码",
                                    "defaultValue": null,
                                    "displaySize": 2147483647,
                                    "name": "password",
                                    "numericPrecision": 0,
                                    "orderKey": 7,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "10432d4b-10e9-4538-9df0-0a8d87542640",
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
                                    "type": "int8",
                                    "typeCode": -5,
                                    "typeNotNull": true
                                }
                            },
                            "id": "ac199628-d6fe-4b31-b8c6-e5d3f4b6ad74",
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
                                    "displaySize": 35,
                                    "name": "created_time",
                                    "numericPrecision": 6,
                                    "orderKey": 9,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "timestamptz",
                                    "typeCode": 93,
                                    "typeNotNull": true
                                }
                            },
                            "id": "7c102f46-52a2-4e0e-85ec-d7f2f244ed6b",
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
                                    "displaySize": 35,
                                    "name": "modified_time",
                                    "numericPrecision": 6,
                                    "orderKey": 10,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "timestamptz",
                                    "typeCode": 93,
                                    "typeNotNull": true
                                }
                            },
                            "id": "337ca675-59c6-4b75-94ed-c5b3aa6f5130",
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
                                    "defaultValue": "''::text",
                                    "displaySize": 2147483647,
                                    "name": "remark",
                                    "numericPrecision": 0,
                                    "orderKey": 11,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "39861f7e-6e51-44ce-993e-03bb72817b5d",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 291
                                }
                            }
                        }
                    ]
                },
                "zIndex": 1
            },
            {
                "position": {
                    "x": 3680.17578125,
                    "y": 1932.684326171875
                },
                "size": {
                    "width": 291,
                    "height": 304
                },
                "view": "vue-shape-view",
                "shape": "model",
                "id": "593fa2f6-9716-472f-bb73-2ce0e87a0459",
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
                                "defaultValue": "nextval('jimmer_code_gen.gen_enum_id_seq'::regclass)",
                                "displaySize": 19,
                                "name": "id",
                                "numericPrecision": 0,
                                "orderKey": 0,
                                "partOfFk": false,
                                "partOfPk": true,
                                "partOfUniqueIdx": true,
                                "remark": "",
                                "type": "bigserial",
                                "typeCode": -5,
                                "typeNotNull": true
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
                                "type": "int8",
                                "typeCode": -5,
                                "typeNotNull": false
                            },
                            {
                                "autoIncrement": false,
                                "comment": "枚举名",
                                "defaultValue": null,
                                "displaySize": 2147483647,
                                "name": "name",
                                "numericPrecision": 0,
                                "orderKey": 2,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "枚举注释",
                                "defaultValue": null,
                                "displaySize": 2147483647,
                                "name": "comment",
                                "numericPrecision": 0,
                                "orderKey": 3,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "枚举类型",
                                "defaultValue": null,
                                "displaySize": 2147483647,
                                "name": "enum_type",
                                "numericPrecision": 0,
                                "orderKey": 4,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": false
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
                                "type": "int8",
                                "typeCode": -5,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "创建时间",
                                "defaultValue": "CURRENT_TIMESTAMP",
                                "displaySize": 35,
                                "name": "created_time",
                                "numericPrecision": 6,
                                "orderKey": 6,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "timestamptz",
                                "typeCode": 93,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "修改时间",
                                "defaultValue": "CURRENT_TIMESTAMP",
                                "displaySize": 35,
                                "name": "modified_time",
                                "numericPrecision": 6,
                                "orderKey": 7,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "timestamptz",
                                "typeCode": 93,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "备注",
                                "defaultValue": "''::text",
                                "displaySize": 2147483647,
                                "name": "remark",
                                "numericPrecision": 0,
                                "orderKey": 8,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
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
                                    "defaultValue": "nextval('jimmer_code_gen.gen_enum_id_seq'::regclass)",
                                    "displaySize": 19,
                                    "name": "id",
                                    "numericPrecision": 0,
                                    "orderKey": 0,
                                    "partOfFk": false,
                                    "partOfPk": true,
                                    "partOfUniqueIdx": true,
                                    "remark": "",
                                    "type": "bigserial",
                                    "typeCode": -5,
                                    "typeNotNull": true
                                }
                            },
                            "id": "e325e483-60ca-4a73-95ce-678c562f1bc8",
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
                                    "type": "int8",
                                    "typeCode": -5,
                                    "typeNotNull": false
                                }
                            },
                            "id": "179ea106-cf9e-42c7-8072-c6a465c8ecbe",
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
                                    "comment": "枚举名",
                                    "defaultValue": null,
                                    "displaySize": 2147483647,
                                    "name": "name",
                                    "numericPrecision": 0,
                                    "orderKey": 2,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "01234fc5-eb18-4b44-af7f-24087d2fe52f",
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
                                    "comment": "枚举注释",
                                    "defaultValue": null,
                                    "displaySize": 2147483647,
                                    "name": "comment",
                                    "numericPrecision": 0,
                                    "orderKey": 3,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "e23e1b92-4abb-48f1-84a2-fb07f4837280",
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
                                    "comment": "枚举类型",
                                    "defaultValue": null,
                                    "displaySize": 2147483647,
                                    "name": "enum_type",
                                    "numericPrecision": 0,
                                    "orderKey": 4,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": false
                                }
                            },
                            "id": "f9ae7450-8e7a-47e1-88da-23061cb9f593",
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
                                    "type": "int8",
                                    "typeCode": -5,
                                    "typeNotNull": true
                                }
                            },
                            "id": "8ad8c0c0-9a9a-4770-9d80-f25c16787513",
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
                                    "displaySize": 35,
                                    "name": "created_time",
                                    "numericPrecision": 6,
                                    "orderKey": 6,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "timestamptz",
                                    "typeCode": 93,
                                    "typeNotNull": true
                                }
                            },
                            "id": "4913148c-a47c-47a8-afdb-5d02adcdbe14",
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
                                    "displaySize": 35,
                                    "name": "modified_time",
                                    "numericPrecision": 6,
                                    "orderKey": 7,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "timestamptz",
                                    "typeCode": 93,
                                    "typeNotNull": true
                                }
                            },
                            "id": "35f46b42-d53d-4db0-bd48-b4f9bc4e1fdb",
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
                                    "defaultValue": "''::text",
                                    "displaySize": 2147483647,
                                    "name": "remark",
                                    "numericPrecision": 0,
                                    "orderKey": 8,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "d8da0909-1028-499a-be52-6e54dfaae785",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 291
                                }
                            }
                        }
                    ]
                },
                "zIndex": 5
            },
            {
                "position": {
                    "x": 3680.17578125,
                    "y": 2336.684326171875
                },
                "size": {
                    "width": 291,
                    "height": 274
                },
                "view": "vue-shape-view",
                "shape": "model",
                "id": "f48f128b-e595-476b-bd53-7fd4740082e0",
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
                                "defaultValue": "nextval('jimmer_code_gen.gen_enum_item_id_seq'::regclass)",
                                "displaySize": 19,
                                "name": "id",
                                "numericPrecision": 0,
                                "orderKey": 0,
                                "partOfFk": false,
                                "partOfPk": true,
                                "partOfUniqueIdx": true,
                                "remark": "",
                                "type": "bigserial",
                                "typeCode": -5,
                                "typeNotNull": true
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
                                "type": "int8",
                                "typeCode": -5,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "元素名",
                                "defaultValue": null,
                                "displaySize": 2147483647,
                                "name": "name",
                                "numericPrecision": 0,
                                "orderKey": 2,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "元素值",
                                "defaultValue": null,
                                "displaySize": 2147483647,
                                "name": "value",
                                "numericPrecision": 0,
                                "orderKey": 3,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "元素注释",
                                "defaultValue": "''::text",
                                "displaySize": 2147483647,
                                "name": "comment",
                                "numericPrecision": 0,
                                "orderKey": 4,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "创建时间",
                                "defaultValue": "CURRENT_TIMESTAMP",
                                "displaySize": 35,
                                "name": "created_time",
                                "numericPrecision": 6,
                                "orderKey": 5,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "timestamptz",
                                "typeCode": 93,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "修改时间",
                                "defaultValue": "CURRENT_TIMESTAMP",
                                "displaySize": 35,
                                "name": "modified_time",
                                "numericPrecision": 6,
                                "orderKey": 6,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "timestamptz",
                                "typeCode": 93,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "备注",
                                "defaultValue": "''::text",
                                "displaySize": 2147483647,
                                "name": "remark",
                                "numericPrecision": 0,
                                "orderKey": 7,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
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
                                    "defaultValue": "nextval('jimmer_code_gen.gen_enum_item_id_seq'::regclass)",
                                    "displaySize": 19,
                                    "name": "id",
                                    "numericPrecision": 0,
                                    "orderKey": 0,
                                    "partOfFk": false,
                                    "partOfPk": true,
                                    "partOfUniqueIdx": true,
                                    "remark": "",
                                    "type": "bigserial",
                                    "typeCode": -5,
                                    "typeNotNull": true
                                }
                            },
                            "id": "077581e7-cf50-455f-b08c-0f44dc3f12b0",
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
                                    "type": "int8",
                                    "typeCode": -5,
                                    "typeNotNull": true
                                }
                            },
                            "id": "0228722f-bf7a-4633-8ec5-cbbb96242594",
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
                                    "comment": "元素名",
                                    "defaultValue": null,
                                    "displaySize": 2147483647,
                                    "name": "name",
                                    "numericPrecision": 0,
                                    "orderKey": 2,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "15db0ce2-5457-4a8f-9fbc-d7a40378c34c",
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
                                    "comment": "元素值",
                                    "defaultValue": null,
                                    "displaySize": 2147483647,
                                    "name": "value",
                                    "numericPrecision": 0,
                                    "orderKey": 3,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "9c034c81-1304-41c0-b067-c06212abfc76",
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
                                    "comment": "元素注释",
                                    "defaultValue": "''::text",
                                    "displaySize": 2147483647,
                                    "name": "comment",
                                    "numericPrecision": 0,
                                    "orderKey": 4,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "4848d3ae-c5e8-4148-9b86-a899849d676b",
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
                                    "displaySize": 35,
                                    "name": "created_time",
                                    "numericPrecision": 6,
                                    "orderKey": 5,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "timestamptz",
                                    "typeCode": 93,
                                    "typeNotNull": true
                                }
                            },
                            "id": "8233ccc0-879b-4cb1-bc83-b256d60966c1",
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
                                    "displaySize": 35,
                                    "name": "modified_time",
                                    "numericPrecision": 6,
                                    "orderKey": 6,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "timestamptz",
                                    "typeCode": 93,
                                    "typeNotNull": true
                                }
                            },
                            "id": "0f32ceb6-a50c-4cb1-a01f-e83c0cc4527d",
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
                                    "defaultValue": "''::text",
                                    "displaySize": 2147483647,
                                    "name": "remark",
                                    "numericPrecision": 0,
                                    "orderKey": 7,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "ccb88de6-2ddc-447a-8993-20344df39c55",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 291
                                }
                            }
                        }
                    ]
                },
                "zIndex": 7
            },
            {
                "position": {
                    "x": 4724.17578125,
                    "y": 1842.684326171875
                },
                "size": {
                    "width": 291,
                    "height": 304
                },
                "view": "vue-shape-view",
                "shape": "model",
                "id": "8f28a79e-92bf-491c-be16-3173bc4684cd",
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
                                "defaultValue": "nextval('jimmer_code_gen.gen_table_id_seq'::regclass)",
                                "displaySize": 19,
                                "name": "id",
                                "numericPrecision": 0,
                                "orderKey": 0,
                                "partOfFk": false,
                                "partOfPk": true,
                                "partOfUniqueIdx": true,
                                "remark": "",
                                "type": "bigserial",
                                "typeCode": -5,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "数据架构",
                                "defaultValue": null,
                                "displaySize": 19,
                                "name": "schema_id",
                                "numericPrecision": 0,
                                "orderKey": 1,
                                "partOfFk": true,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "int8",
                                "typeCode": -5,
                                "typeNotNull": false
                            },
                            {
                                "autoIncrement": false,
                                "comment": "表名称",
                                "defaultValue": null,
                                "displaySize": 2147483647,
                                "name": "name",
                                "numericPrecision": 0,
                                "orderKey": 2,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "表注释",
                                "defaultValue": null,
                                "displaySize": 2147483647,
                                "name": "comment",
                                "numericPrecision": 0,
                                "orderKey": 3,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "表种类",
                                "defaultValue": null,
                                "displaySize": 2147483647,
                                "name": "type",
                                "numericPrecision": 0,
                                "orderKey": 4,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
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
                                "type": "int8",
                                "typeCode": -5,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "创建时间",
                                "defaultValue": "CURRENT_TIMESTAMP",
                                "displaySize": 35,
                                "name": "created_time",
                                "numericPrecision": 6,
                                "orderKey": 6,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "timestamptz",
                                "typeCode": 93,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "修改时间",
                                "defaultValue": "CURRENT_TIMESTAMP",
                                "displaySize": 35,
                                "name": "modified_time",
                                "numericPrecision": 6,
                                "orderKey": 7,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "timestamptz",
                                "typeCode": 93,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "备注",
                                "defaultValue": "''::text",
                                "displaySize": 2147483647,
                                "name": "remark",
                                "numericPrecision": 0,
                                "orderKey": 8,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
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
                                    "defaultValue": "nextval('jimmer_code_gen.gen_table_id_seq'::regclass)",
                                    "displaySize": 19,
                                    "name": "id",
                                    "numericPrecision": 0,
                                    "orderKey": 0,
                                    "partOfFk": false,
                                    "partOfPk": true,
                                    "partOfUniqueIdx": true,
                                    "remark": "",
                                    "type": "bigserial",
                                    "typeCode": -5,
                                    "typeNotNull": true
                                }
                            },
                            "id": "f0705c5f-51a0-4568-b0f2-8cc8af774526",
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
                                    "comment": "数据架构",
                                    "defaultValue": null,
                                    "displaySize": 19,
                                    "name": "schema_id",
                                    "numericPrecision": 0,
                                    "orderKey": 1,
                                    "partOfFk": true,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "int8",
                                    "typeCode": -5,
                                    "typeNotNull": false
                                }
                            },
                            "id": "7ce99919-62b9-47c7-bb1e-144f39035c09",
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
                                    "comment": "表名称",
                                    "defaultValue": null,
                                    "displaySize": 2147483647,
                                    "name": "name",
                                    "numericPrecision": 0,
                                    "orderKey": 2,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "3b66610f-3105-48df-bb68-b7f9333633b3",
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
                                    "comment": "表注释",
                                    "defaultValue": null,
                                    "displaySize": 2147483647,
                                    "name": "comment",
                                    "numericPrecision": 0,
                                    "orderKey": 3,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "6bd5ed8a-2b9e-447d-932c-6c1635d89d40",
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
                                    "comment": "表种类",
                                    "defaultValue": null,
                                    "displaySize": 2147483647,
                                    "name": "type",
                                    "numericPrecision": 0,
                                    "orderKey": 4,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "701b7b20-9299-47a6-8989-e0a6f3020db8",
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
                                    "type": "int8",
                                    "typeCode": -5,
                                    "typeNotNull": true
                                }
                            },
                            "id": "e18eb45e-c69f-42cd-8c3e-95cff8722059",
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
                                    "displaySize": 35,
                                    "name": "created_time",
                                    "numericPrecision": 6,
                                    "orderKey": 6,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "timestamptz",
                                    "typeCode": 93,
                                    "typeNotNull": true
                                }
                            },
                            "id": "682daba8-54a8-410d-91fe-ceb307d21274",
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
                                    "displaySize": 35,
                                    "name": "modified_time",
                                    "numericPrecision": 6,
                                    "orderKey": 7,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "timestamptz",
                                    "typeCode": 93,
                                    "typeNotNull": true
                                }
                            },
                            "id": "d7780fc3-a9a4-4d9c-8c33-508953265d96",
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
                                    "defaultValue": "''::text",
                                    "displaySize": 2147483647,
                                    "name": "remark",
                                    "numericPrecision": 0,
                                    "orderKey": 8,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "04ef85c1-10a3-4098-ace1-c4160342c477",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 291
                                }
                            }
                        }
                    ]
                },
                "zIndex": 8
            },
            {
                "position": {
                    "x": 3132.17578125,
                    "y": 1962.684326171875
                },
                "size": {
                    "width": 348,
                    "height": 574
                },
                "view": "vue-shape-view",
                "shape": "model",
                "id": "30be191a-89c3-4020-8b89-e146db387e86",
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
                                "defaultValue": "nextval('jimmer_code_gen.gen_column_id_seq'::regclass)",
                                "displaySize": 19,
                                "name": "id",
                                "numericPrecision": 0,
                                "orderKey": 0,
                                "partOfFk": false,
                                "partOfPk": true,
                                "partOfUniqueIdx": true,
                                "remark": "",
                                "type": "bigserial",
                                "typeCode": -5,
                                "typeNotNull": true
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
                                "type": "int8",
                                "typeCode": -5,
                                "typeNotNull": true
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
                                "type": "int8",
                                "typeCode": -5,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "列名称",
                                "defaultValue": null,
                                "displaySize": 2147483647,
                                "name": "name",
                                "numericPrecision": 0,
                                "orderKey": 3,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
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
                                "type": "int4",
                                "typeCode": 4,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "列类型",
                                "defaultValue": null,
                                "displaySize": 2147483647,
                                "name": "type",
                                "numericPrecision": 0,
                                "orderKey": 5,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
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
                                "type": "int8",
                                "typeCode": -5,
                                "typeNotNull": true
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
                                "type": "int8",
                                "typeCode": -5,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "列默认值",
                                "defaultValue": null,
                                "displaySize": 2147483647,
                                "name": "default_value",
                                "numericPrecision": 0,
                                "orderKey": 8,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": false
                            },
                            {
                                "autoIncrement": false,
                                "comment": "列注释",
                                "defaultValue": null,
                                "displaySize": 2147483647,
                                "name": "comment",
                                "numericPrecision": 0,
                                "orderKey": 9,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "是否主键",
                                "defaultValue": "false",
                                "displaySize": 1,
                                "name": "part_of_pk",
                                "numericPrecision": 0,
                                "orderKey": 10,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "bool",
                                "typeCode": -7,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "是否自增",
                                "defaultValue": "false",
                                "displaySize": 1,
                                "name": "auto_increment",
                                "numericPrecision": 0,
                                "orderKey": 11,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "bool",
                                "typeCode": -7,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "是否外键",
                                "defaultValue": "false",
                                "displaySize": 1,
                                "name": "part_of_fk",
                                "numericPrecision": 0,
                                "orderKey": 12,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "bool",
                                "typeCode": -7,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "是否唯一索引",
                                "defaultValue": "false",
                                "displaySize": 1,
                                "name": "part_of_unique_idx",
                                "numericPrecision": 0,
                                "orderKey": 13,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "bool",
                                "typeCode": -7,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "是否非空",
                                "defaultValue": "false",
                                "displaySize": 1,
                                "name": "type_not_null",
                                "numericPrecision": 0,
                                "orderKey": 14,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "bool",
                                "typeCode": -7,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "创建时间",
                                "defaultValue": "CURRENT_TIMESTAMP",
                                "displaySize": 35,
                                "name": "created_time",
                                "numericPrecision": 6,
                                "orderKey": 15,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "timestamptz",
                                "typeCode": 93,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "修改时间",
                                "defaultValue": "CURRENT_TIMESTAMP",
                                "displaySize": 35,
                                "name": "modified_time",
                                "numericPrecision": 6,
                                "orderKey": 16,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "timestamptz",
                                "typeCode": 93,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "备注",
                                "defaultValue": "''::text",
                                "displaySize": 2147483647,
                                "name": "remark",
                                "numericPrecision": 0,
                                "orderKey": 17,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
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
                                    "defaultValue": "nextval('jimmer_code_gen.gen_column_id_seq'::regclass)",
                                    "displaySize": 19,
                                    "name": "id",
                                    "numericPrecision": 0,
                                    "orderKey": 0,
                                    "partOfFk": false,
                                    "partOfPk": true,
                                    "partOfUniqueIdx": true,
                                    "remark": "",
                                    "type": "bigserial",
                                    "typeCode": -5,
                                    "typeNotNull": true
                                }
                            },
                            "id": "5239aa56-d43b-4908-ad44-15bf128a7def",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 348
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
                                    "type": "int8",
                                    "typeCode": -5,
                                    "typeNotNull": true
                                }
                            },
                            "id": "f208be2e-acc3-4f3d-a888-d78e1baa6b5d",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 348
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
                                    "type": "int8",
                                    "typeCode": -5,
                                    "typeNotNull": true
                                }
                            },
                            "id": "a88ba868-7be9-4468-adf8-550b022e154d",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 348
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
                                    "displaySize": 2147483647,
                                    "name": "name",
                                    "numericPrecision": 0,
                                    "orderKey": 3,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "96a02987-4c0a-45a2-b537-6faafb27aff6",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 348
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
                                    "type": "int4",
                                    "typeCode": 4,
                                    "typeNotNull": true
                                }
                            },
                            "id": "c6ab48f9-11a7-45e8-9ac8-7f7d98a361c5",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 348
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
                                    "displaySize": 2147483647,
                                    "name": "type",
                                    "numericPrecision": 0,
                                    "orderKey": 5,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "57efc050-af8f-4711-a017-01252343d962",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 348
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
                                    "type": "int8",
                                    "typeCode": -5,
                                    "typeNotNull": true
                                }
                            },
                            "id": "3717681d-9f3f-4c95-b81c-68b9d587e537",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 348
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
                                    "type": "int8",
                                    "typeCode": -5,
                                    "typeNotNull": true
                                }
                            },
                            "id": "3979cb9a-c784-40f8-84cf-f1755f7b61a6",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 348
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
                                    "displaySize": 2147483647,
                                    "name": "default_value",
                                    "numericPrecision": 0,
                                    "orderKey": 8,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": false
                                }
                            },
                            "id": "92aeb970-3a85-450e-ae9c-f37a1a6e9e60",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 348
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
                                    "displaySize": 2147483647,
                                    "name": "comment",
                                    "numericPrecision": 0,
                                    "orderKey": 9,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "d749f788-1367-4ab7-afcd-9ec2c9d8e605",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 348
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "data": {
                                "column": {
                                    "autoIncrement": false,
                                    "comment": "是否主键",
                                    "defaultValue": "false",
                                    "displaySize": 1,
                                    "name": "part_of_pk",
                                    "numericPrecision": 0,
                                    "orderKey": 10,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "bool",
                                    "typeCode": -7,
                                    "typeNotNull": true
                                }
                            },
                            "id": "e65199c1-1245-426c-94ff-5d99018fca15",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 348
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "data": {
                                "column": {
                                    "autoIncrement": false,
                                    "comment": "是否自增",
                                    "defaultValue": "false",
                                    "displaySize": 1,
                                    "name": "auto_increment",
                                    "numericPrecision": 0,
                                    "orderKey": 11,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "bool",
                                    "typeCode": -7,
                                    "typeNotNull": true
                                }
                            },
                            "id": "40c9e7e4-1f06-45d0-90ed-4f436fe963a0",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 348
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "data": {
                                "column": {
                                    "autoIncrement": false,
                                    "comment": "是否外键",
                                    "defaultValue": "false",
                                    "displaySize": 1,
                                    "name": "part_of_fk",
                                    "numericPrecision": 0,
                                    "orderKey": 12,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "bool",
                                    "typeCode": -7,
                                    "typeNotNull": true
                                }
                            },
                            "id": "25364552-dfbc-4433-8f32-de743a93bbd0",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 348
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "data": {
                                "column": {
                                    "autoIncrement": false,
                                    "comment": "是否唯一索引",
                                    "defaultValue": "false",
                                    "displaySize": 1,
                                    "name": "part_of_unique_idx",
                                    "numericPrecision": 0,
                                    "orderKey": 13,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "bool",
                                    "typeCode": -7,
                                    "typeNotNull": true
                                }
                            },
                            "id": "c1da734f-3108-4e26-93b2-213820e6fa32",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 348
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "data": {
                                "column": {
                                    "autoIncrement": false,
                                    "comment": "是否非空",
                                    "defaultValue": "false",
                                    "displaySize": 1,
                                    "name": "type_not_null",
                                    "numericPrecision": 0,
                                    "orderKey": 14,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "bool",
                                    "typeCode": -7,
                                    "typeNotNull": true
                                }
                            },
                            "id": "deb89cc0-5811-48a2-a925-c84741e354aa",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 348
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
                                    "displaySize": 35,
                                    "name": "created_time",
                                    "numericPrecision": 6,
                                    "orderKey": 15,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "timestamptz",
                                    "typeCode": 93,
                                    "typeNotNull": true
                                }
                            },
                            "id": "8f8980f1-7692-4c89-b14f-f659afe6d6e2",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 348
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
                                    "displaySize": 35,
                                    "name": "modified_time",
                                    "numericPrecision": 6,
                                    "orderKey": 16,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "timestamptz",
                                    "typeCode": 93,
                                    "typeNotNull": true
                                }
                            },
                            "id": "f7d630cd-9ad6-414b-88e9-e2964befddec",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 348
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "data": {
                                "column": {
                                    "autoIncrement": false,
                                    "comment": "备注",
                                    "defaultValue": "''::text",
                                    "displaySize": 2147483647,
                                    "name": "remark",
                                    "numericPrecision": 0,
                                    "orderKey": 17,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "1d1e9883-dda5-49a6-a73b-17e4a257c6f1",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 348
                                }
                            }
                        }
                    ]
                },
                "zIndex": 9
            },
            {
                "position": {
                    "x": 4724.17578125,
                    "y": 2246.684326171875
                },
                "size": {
                    "width": 326,
                    "height": 274
                },
                "view": "vue-shape-view",
                "shape": "model",
                "id": "3a777e43-e088-4b41-828f-f4998adc7009",
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
                                "defaultValue": "nextval('jimmer_code_gen.gen_type_mapping_id_seq'::regclass)",
                                "displaySize": 19,
                                "name": "id",
                                "numericPrecision": 0,
                                "orderKey": 0,
                                "partOfFk": false,
                                "partOfPk": true,
                                "partOfUniqueIdx": true,
                                "remark": "",
                                "type": "bigserial",
                                "typeCode": -5,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "类型表达式",
                                "defaultValue": null,
                                "displaySize": 2147483647,
                                "name": "type_expression",
                                "numericPrecision": 0,
                                "orderKey": 1,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "是否正则",
                                "defaultValue": "false",
                                "displaySize": 1,
                                "name": "regex",
                                "numericPrecision": 0,
                                "orderKey": 2,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "bool",
                                "typeCode": -7,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "属性类型",
                                "defaultValue": null,
                                "displaySize": 2147483647,
                                "name": "property_type",
                                "numericPrecision": 0,
                                "orderKey": 3,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
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
                                "type": "int8",
                                "typeCode": -5,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "创建时间",
                                "defaultValue": "CURRENT_TIMESTAMP",
                                "displaySize": 35,
                                "name": "created_time",
                                "numericPrecision": 6,
                                "orderKey": 5,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "timestamptz",
                                "typeCode": 93,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "修改时间",
                                "defaultValue": "CURRENT_TIMESTAMP",
                                "displaySize": 35,
                                "name": "modified_time",
                                "numericPrecision": 6,
                                "orderKey": 6,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "timestamptz",
                                "typeCode": 93,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "备注",
                                "defaultValue": "''::text",
                                "displaySize": 2147483647,
                                "name": "remark",
                                "numericPrecision": 0,
                                "orderKey": 7,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
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
                                    "defaultValue": "nextval('jimmer_code_gen.gen_type_mapping_id_seq'::regclass)",
                                    "displaySize": 19,
                                    "name": "id",
                                    "numericPrecision": 0,
                                    "orderKey": 0,
                                    "partOfFk": false,
                                    "partOfPk": true,
                                    "partOfUniqueIdx": true,
                                    "remark": "",
                                    "type": "bigserial",
                                    "typeCode": -5,
                                    "typeNotNull": true
                                }
                            },
                            "id": "2e32e7d8-5f82-462f-9e91-f702befa8a82",
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
                                    "displaySize": 2147483647,
                                    "name": "type_expression",
                                    "numericPrecision": 0,
                                    "orderKey": 1,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "a5d02b88-7586-4e8d-8643-adbf0e7de07e",
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
                                    "defaultValue": "false",
                                    "displaySize": 1,
                                    "name": "regex",
                                    "numericPrecision": 0,
                                    "orderKey": 2,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "bool",
                                    "typeCode": -7,
                                    "typeNotNull": true
                                }
                            },
                            "id": "43747092-35d6-4f63-a33a-d84550fed052",
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
                                    "displaySize": 2147483647,
                                    "name": "property_type",
                                    "numericPrecision": 0,
                                    "orderKey": 3,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "739fa023-c1a5-4caa-98cd-f9b9a2264970",
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
                                    "type": "int8",
                                    "typeCode": -5,
                                    "typeNotNull": true
                                }
                            },
                            "id": "31d2e298-2878-45f2-9476-1892be8aafef",
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
                                    "displaySize": 35,
                                    "name": "created_time",
                                    "numericPrecision": 6,
                                    "orderKey": 5,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "timestamptz",
                                    "typeCode": 93,
                                    "typeNotNull": true
                                }
                            },
                            "id": "ed1fd69d-b3b5-4f0e-a61a-69be2d450a0c",
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
                                    "displaySize": 35,
                                    "name": "modified_time",
                                    "numericPrecision": 6,
                                    "orderKey": 6,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "timestamptz",
                                    "typeCode": 93,
                                    "typeNotNull": true
                                }
                            },
                            "id": "50c3aaf0-0aa6-44b7-954e-e4dc9d8bd13c",
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
                                    "defaultValue": "''::text",
                                    "displaySize": 2147483647,
                                    "name": "remark",
                                    "numericPrecision": 0,
                                    "orderKey": 7,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "86f65056-e832-4685-8a37-65bb40daf0c7",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 326
                                }
                            }
                        }
                    ]
                },
                "zIndex": 15
            },
            {
                "position": {
                    "x": 4171.17578125,
                    "y": 2156.684326171875
                },
                "size": {
                    "width": 353,
                    "height": 724
                },
                "view": "vue-shape-view",
                "shape": "model",
                "id": "4771fc93-14c8-402f-ba42-57447c7bd6be",
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
                                "defaultValue": "nextval('jimmer_code_gen.gen_property_id_seq'::regclass)",
                                "displaySize": 19,
                                "name": "id",
                                "numericPrecision": 0,
                                "orderKey": 0,
                                "partOfFk": false,
                                "partOfPk": true,
                                "partOfUniqueIdx": true,
                                "remark": "",
                                "type": "bigserial",
                                "typeCode": -5,
                                "typeNotNull": true
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
                                "type": "int8",
                                "typeCode": -5,
                                "typeNotNull": true
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
                                "type": "int8",
                                "typeCode": -5,
                                "typeNotNull": false
                            },
                            {
                                "autoIncrement": false,
                                "comment": "属性名",
                                "defaultValue": null,
                                "displaySize": 2147483647,
                                "name": "name",
                                "numericPrecision": 0,
                                "orderKey": 3,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "属性注释",
                                "defaultValue": null,
                                "displaySize": 2147483647,
                                "name": "comment",
                                "numericPrecision": 0,
                                "orderKey": 4,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "属性类型",
                                "defaultValue": null,
                                "displaySize": 2147483647,
                                "name": "type",
                                "numericPrecision": 0,
                                "orderKey": 5,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
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
                                "type": "int8",
                                "typeCode": -5,
                                "typeNotNull": false
                            },
                            {
                                "autoIncrement": false,
                                "comment": "是否列表",
                                "defaultValue": "false",
                                "displaySize": 1,
                                "name": "list_type",
                                "numericPrecision": 0,
                                "orderKey": 7,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "bool",
                                "typeCode": -7,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "是否非空",
                                "defaultValue": "false",
                                "displaySize": 1,
                                "name": "type_not_null",
                                "numericPrecision": 0,
                                "orderKey": 8,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "bool",
                                "typeCode": -7,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "是否Id",
                                "defaultValue": "false",
                                "displaySize": 1,
                                "name": "id_property",
                                "numericPrecision": 0,
                                "orderKey": 9,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "bool",
                                "typeCode": -7,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "Id 生成类型",
                                "defaultValue": null,
                                "displaySize": 2147483647,
                                "name": "id_generation_type",
                                "numericPrecision": 0,
                                "orderKey": 10,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": false
                            },
                            {
                                "autoIncrement": false,
                                "comment": "是否为业务键属性",
                                "defaultValue": "false",
                                "displaySize": 1,
                                "name": "key_property",
                                "numericPrecision": 0,
                                "orderKey": 11,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "bool",
                                "typeCode": -7,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "是否为逻辑删除属性",
                                "defaultValue": "false",
                                "displaySize": 1,
                                "name": "logical_delete",
                                "numericPrecision": 0,
                                "orderKey": 12,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "bool",
                                "typeCode": -7,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "是否为 ID 视图属性",
                                "defaultValue": "false",
                                "displaySize": 1,
                                "name": "id_view",
                                "numericPrecision": 0,
                                "orderKey": 13,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "bool",
                                "typeCode": -7,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "ID 视图注释",
                                "defaultValue": null,
                                "displaySize": 2147483647,
                                "name": "id_view_annotation",
                                "numericPrecision": 0,
                                "orderKey": 14,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": false
                            },
                            {
                                "autoIncrement": false,
                                "comment": "关联类型",
                                "defaultValue": null,
                                "displaySize": 2147483647,
                                "name": "association_type",
                                "numericPrecision": 0,
                                "orderKey": 15,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": false
                            },
                            {
                                "autoIncrement": false,
                                "comment": "关联注释",
                                "defaultValue": null,
                                "displaySize": 2147483647,
                                "name": "association_annotation",
                                "numericPrecision": 0,
                                "orderKey": 16,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": false
                            },
                            {
                                "autoIncrement": false,
                                "comment": "脱钩注释",
                                "defaultValue": null,
                                "displaySize": 2147483647,
                                "name": "dissociate_annotation",
                                "numericPrecision": 0,
                                "orderKey": 17,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": false
                            },
                            {
                                "autoIncrement": false,
                                "comment": "其他注释",
                                "defaultValue": null,
                                "displaySize": 2147483647,
                                "name": "other_annotation",
                                "numericPrecision": 0,
                                "orderKey": 18,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": false
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
                                "type": "int8",
                                "typeCode": -5,
                                "typeNotNull": false
                            },
                            {
                                "autoIncrement": false,
                                "comment": "创建时间",
                                "defaultValue": "CURRENT_TIMESTAMP",
                                "displaySize": 35,
                                "name": "created_time",
                                "numericPrecision": 6,
                                "orderKey": 20,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "timestamptz",
                                "typeCode": 93,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "修改时间",
                                "defaultValue": "CURRENT_TIMESTAMP",
                                "displaySize": 35,
                                "name": "modified_time",
                                "numericPrecision": 6,
                                "orderKey": 21,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "timestamptz",
                                "typeCode": 93,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "备注",
                                "defaultValue": "''::text",
                                "displaySize": 2147483647,
                                "name": "remark",
                                "numericPrecision": 0,
                                "orderKey": 22,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
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
                                    "defaultValue": "nextval('jimmer_code_gen.gen_property_id_seq'::regclass)",
                                    "displaySize": 19,
                                    "name": "id",
                                    "numericPrecision": 0,
                                    "orderKey": 0,
                                    "partOfFk": false,
                                    "partOfPk": true,
                                    "partOfUniqueIdx": true,
                                    "remark": "",
                                    "type": "bigserial",
                                    "typeCode": -5,
                                    "typeNotNull": true
                                }
                            },
                            "id": "75ef2592-b057-492b-944c-295110f962c5",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 353
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
                                    "type": "int8",
                                    "typeCode": -5,
                                    "typeNotNull": true
                                }
                            },
                            "id": "ed72e999-9b58-4637-b2d7-88e9a63b28be",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 353
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
                                    "type": "int8",
                                    "typeCode": -5,
                                    "typeNotNull": false
                                }
                            },
                            "id": "b442f29a-b324-4865-a94e-a947fc6c545c",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 353
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
                                    "displaySize": 2147483647,
                                    "name": "name",
                                    "numericPrecision": 0,
                                    "orderKey": 3,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "2eb099a7-28ad-48e6-82e9-53f9dbc2717c",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 353
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
                                    "displaySize": 2147483647,
                                    "name": "comment",
                                    "numericPrecision": 0,
                                    "orderKey": 4,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "0f321b2c-7d59-4314-832a-3896cd9826f4",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 353
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
                                    "displaySize": 2147483647,
                                    "name": "type",
                                    "numericPrecision": 0,
                                    "orderKey": 5,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "74970276-b610-4120-849f-a1e12148acb4",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 353
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
                                    "type": "int8",
                                    "typeCode": -5,
                                    "typeNotNull": false
                                }
                            },
                            "id": "95efb49a-2d90-4f37-9c76-c585b701d047",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 353
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "data": {
                                "column": {
                                    "autoIncrement": false,
                                    "comment": "是否列表",
                                    "defaultValue": "false",
                                    "displaySize": 1,
                                    "name": "list_type",
                                    "numericPrecision": 0,
                                    "orderKey": 7,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "bool",
                                    "typeCode": -7,
                                    "typeNotNull": true
                                }
                            },
                            "id": "70d3f561-2b32-4132-afab-de6d722fd091",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 353
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "data": {
                                "column": {
                                    "autoIncrement": false,
                                    "comment": "是否非空",
                                    "defaultValue": "false",
                                    "displaySize": 1,
                                    "name": "type_not_null",
                                    "numericPrecision": 0,
                                    "orderKey": 8,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "bool",
                                    "typeCode": -7,
                                    "typeNotNull": true
                                }
                            },
                            "id": "60412def-ca5f-44dd-8241-63acb4958480",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 353
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "data": {
                                "column": {
                                    "autoIncrement": false,
                                    "comment": "是否Id",
                                    "defaultValue": "false",
                                    "displaySize": 1,
                                    "name": "id_property",
                                    "numericPrecision": 0,
                                    "orderKey": 9,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "bool",
                                    "typeCode": -7,
                                    "typeNotNull": true
                                }
                            },
                            "id": "a2a646fb-872e-4b48-8046-2939ae7a5416",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 353
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
                                    "displaySize": 2147483647,
                                    "name": "id_generation_type",
                                    "numericPrecision": 0,
                                    "orderKey": 10,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": false
                                }
                            },
                            "id": "70f6afeb-fa1d-4886-b6e6-c6af3bb26acf",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 353
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "data": {
                                "column": {
                                    "autoIncrement": false,
                                    "comment": "是否为业务键属性",
                                    "defaultValue": "false",
                                    "displaySize": 1,
                                    "name": "key_property",
                                    "numericPrecision": 0,
                                    "orderKey": 11,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "bool",
                                    "typeCode": -7,
                                    "typeNotNull": true
                                }
                            },
                            "id": "aec9da0a-2e9f-4ce7-a303-49c7e0c55cd4",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 353
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "data": {
                                "column": {
                                    "autoIncrement": false,
                                    "comment": "是否为逻辑删除属性",
                                    "defaultValue": "false",
                                    "displaySize": 1,
                                    "name": "logical_delete",
                                    "numericPrecision": 0,
                                    "orderKey": 12,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "bool",
                                    "typeCode": -7,
                                    "typeNotNull": true
                                }
                            },
                            "id": "ff246ae6-0f9e-4297-95e2-26353c09aa55",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 353
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "data": {
                                "column": {
                                    "autoIncrement": false,
                                    "comment": "是否为 ID 视图属性",
                                    "defaultValue": "false",
                                    "displaySize": 1,
                                    "name": "id_view",
                                    "numericPrecision": 0,
                                    "orderKey": 13,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "bool",
                                    "typeCode": -7,
                                    "typeNotNull": true
                                }
                            },
                            "id": "bba28607-670f-405e-a172-d60312d50766",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 353
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
                                    "displaySize": 2147483647,
                                    "name": "id_view_annotation",
                                    "numericPrecision": 0,
                                    "orderKey": 14,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": false
                                }
                            },
                            "id": "66067b89-d253-46bd-9143-ebdac8418c8a",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 353
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
                                    "displaySize": 2147483647,
                                    "name": "association_type",
                                    "numericPrecision": 0,
                                    "orderKey": 15,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": false
                                }
                            },
                            "id": "590a7885-f4ef-47be-a135-7e432060ceb2",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 353
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
                                    "displaySize": 2147483647,
                                    "name": "association_annotation",
                                    "numericPrecision": 0,
                                    "orderKey": 16,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": false
                                }
                            },
                            "id": "96451af8-9f49-4c53-a96a-1b5e729f37b6",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 353
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
                                    "displaySize": 2147483647,
                                    "name": "dissociate_annotation",
                                    "numericPrecision": 0,
                                    "orderKey": 17,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": false
                                }
                            },
                            "id": "05c2f3b0-e93e-4b4d-9d4f-80567f925eb4",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 353
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
                                    "displaySize": 2147483647,
                                    "name": "other_annotation",
                                    "numericPrecision": 0,
                                    "orderKey": 18,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": false
                                }
                            },
                            "id": "fd3ce8da-e334-4904-8ef8-b13237da508f",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 353
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
                                    "type": "int8",
                                    "typeCode": -5,
                                    "typeNotNull": false
                                }
                            },
                            "id": "8c776b18-9c3f-446a-8040-2ba8755f813d",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 353
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
                                    "displaySize": 35,
                                    "name": "created_time",
                                    "numericPrecision": 6,
                                    "orderKey": 20,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "timestamptz",
                                    "typeCode": 93,
                                    "typeNotNull": true
                                }
                            },
                            "id": "8e9a721b-d9d4-4af0-8be2-118d3f6f7e94",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 353
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
                                    "displaySize": 35,
                                    "name": "modified_time",
                                    "numericPrecision": 6,
                                    "orderKey": 21,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "timestamptz",
                                    "typeCode": 93,
                                    "typeNotNull": true
                                }
                            },
                            "id": "8b500267-8a57-4435-a4dc-79cd5d509663",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 353
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "data": {
                                "column": {
                                    "autoIncrement": false,
                                    "comment": "备注",
                                    "defaultValue": "''::text",
                                    "displaySize": 2147483647,
                                    "name": "remark",
                                    "numericPrecision": 0,
                                    "orderKey": 22,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "a7a94213-feb5-478e-bfda-e3b43caa2726",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 353
                                }
                            }
                        }
                    ]
                },
                "zIndex": 16
            },
            {
                "position": {
                    "x": 4171.17578125,
                    "y": 1812.684326171875
                },
                "size": {
                    "width": 291,
                    "height": 244
                },
                "view": "vue-shape-view",
                "shape": "model",
                "id": "7e9a4363-c828-4b38-a200-4ed5ea07659d",
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
                                "defaultValue": "nextval('jimmer_code_gen.gen_package_id_seq'::regclass)",
                                "displaySize": 19,
                                "name": "id",
                                "numericPrecision": 0,
                                "orderKey": 0,
                                "partOfFk": false,
                                "partOfPk": true,
                                "partOfUniqueIdx": true,
                                "remark": "",
                                "type": "bigserial",
                                "typeCode": -5,
                                "typeNotNull": true
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
                                "type": "int8",
                                "typeCode": -5,
                                "typeNotNull": false
                            },
                            {
                                "autoIncrement": false,
                                "comment": "包名称",
                                "defaultValue": null,
                                "displaySize": 2147483647,
                                "name": "name",
                                "numericPrecision": 0,
                                "orderKey": 2,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
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
                                "type": "int8",
                                "typeCode": -5,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "创建时间",
                                "defaultValue": "CURRENT_TIMESTAMP",
                                "displaySize": 35,
                                "name": "created_time",
                                "numericPrecision": 6,
                                "orderKey": 4,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "timestamptz",
                                "typeCode": 93,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "修改时间",
                                "defaultValue": "CURRENT_TIMESTAMP",
                                "displaySize": 35,
                                "name": "modified_time",
                                "numericPrecision": 6,
                                "orderKey": 5,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "timestamptz",
                                "typeCode": 93,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "备注",
                                "defaultValue": "''::text",
                                "displaySize": 2147483647,
                                "name": "remark",
                                "numericPrecision": 0,
                                "orderKey": 6,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
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
                                    "defaultValue": "nextval('jimmer_code_gen.gen_package_id_seq'::regclass)",
                                    "displaySize": 19,
                                    "name": "id",
                                    "numericPrecision": 0,
                                    "orderKey": 0,
                                    "partOfFk": false,
                                    "partOfPk": true,
                                    "partOfUniqueIdx": true,
                                    "remark": "",
                                    "type": "bigserial",
                                    "typeCode": -5,
                                    "typeNotNull": true
                                }
                            },
                            "id": "451b9c2e-9e90-4c88-906a-498a8718be52",
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
                                    "type": "int8",
                                    "typeCode": -5,
                                    "typeNotNull": false
                                }
                            },
                            "id": "3822e228-f665-4a3b-b26c-d442a582fe92",
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
                                    "comment": "包名称",
                                    "defaultValue": null,
                                    "displaySize": 2147483647,
                                    "name": "name",
                                    "numericPrecision": 0,
                                    "orderKey": 2,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "5d1a597a-9441-4d41-adad-cbfed37f2f38",
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
                                    "type": "int8",
                                    "typeCode": -5,
                                    "typeNotNull": true
                                }
                            },
                            "id": "fe0d6188-1ba9-4294-abcd-29aaf838d001",
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
                                    "displaySize": 35,
                                    "name": "created_time",
                                    "numericPrecision": 6,
                                    "orderKey": 4,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "timestamptz",
                                    "typeCode": 93,
                                    "typeNotNull": true
                                }
                            },
                            "id": "4de20c27-30dd-4e2c-a4ad-67ae4e3091af",
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
                                    "displaySize": 35,
                                    "name": "modified_time",
                                    "numericPrecision": 6,
                                    "orderKey": 5,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "timestamptz",
                                    "typeCode": 93,
                                    "typeNotNull": true
                                }
                            },
                            "id": "95a99f20-5982-4ed7-bd23-a56e8cd83ef1",
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
                                    "defaultValue": "''::text",
                                    "displaySize": 2147483647,
                                    "name": "remark",
                                    "numericPrecision": 0,
                                    "orderKey": 6,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "8e39f80a-aba5-43b6-937e-4d38f1abc55b",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 291
                                }
                            }
                        }
                    ]
                },
                "zIndex": 17
            },
            {
                "position": {
                    "x": 3132.17578125,
                    "y": 1498.684326171875
                },
                "size": {
                    "width": 311,
                    "height": 364
                },
                "view": "vue-shape-view",
                "shape": "model",
                "id": "f72d6012-9372-44cb-ac37-9ef0781dfd45",
                "data": {
                    "table": {
                        "name": "gen_association",
                        "comment": "生成关联",
                        "remark": "",
                        "orderKey": 0,
                        "type": "TABLE",
                        "columns": [
                            {
                                "autoIncrement": true,
                                "comment": "ID",
                                "defaultValue": "nextval('jimmer_code_gen.gen_association_id_seq'::regclass)",
                                "displaySize": 19,
                                "name": "id",
                                "numericPrecision": 0,
                                "orderKey": 0,
                                "partOfFk": false,
                                "partOfPk": true,
                                "partOfUniqueIdx": true,
                                "remark": "",
                                "type": "bigserial",
                                "typeCode": -5,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "关联注释",
                                "defaultValue": "''::text",
                                "displaySize": 2147483647,
                                "name": "comment",
                                "numericPrecision": 0,
                                "orderKey": 1,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "主列",
                                "defaultValue": null,
                                "displaySize": 19,
                                "name": "source_column_id",
                                "numericPrecision": 0,
                                "orderKey": 2,
                                "partOfFk": true,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "int8",
                                "typeCode": -5,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "从列",
                                "defaultValue": null,
                                "displaySize": 19,
                                "name": "target_column_id",
                                "numericPrecision": 0,
                                "orderKey": 3,
                                "partOfFk": true,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "int8",
                                "typeCode": -5,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "关联类型",
                                "defaultValue": null,
                                "displaySize": 2147483647,
                                "name": "association_type",
                                "numericPrecision": 0,
                                "orderKey": 4,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "脱钩行为",
                                "defaultValue": null,
                                "displaySize": 2147483647,
                                "name": "dissociate_action",
                                "numericPrecision": 0,
                                "orderKey": 5,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": false
                            },
                            {
                                "autoIncrement": false,
                                "comment": "是否伪外键",
                                "defaultValue": "true",
                                "displaySize": 1,
                                "name": "fake",
                                "numericPrecision": 0,
                                "orderKey": 6,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "bool",
                                "typeCode": -7,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "自定排序",
                                "defaultValue": "0",
                                "displaySize": 19,
                                "name": "order_key",
                                "numericPrecision": 0,
                                "orderKey": 7,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "int8",
                                "typeCode": -5,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "创建时间",
                                "defaultValue": "CURRENT_TIMESTAMP",
                                "displaySize": 35,
                                "name": "created_time",
                                "numericPrecision": 6,
                                "orderKey": 8,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "timestamptz",
                                "typeCode": 93,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "修改时间",
                                "defaultValue": "CURRENT_TIMESTAMP",
                                "displaySize": 35,
                                "name": "modified_time",
                                "numericPrecision": 6,
                                "orderKey": 9,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "timestamptz",
                                "typeCode": 93,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "备注",
                                "defaultValue": "''::text",
                                "displaySize": 2147483647,
                                "name": "remark",
                                "numericPrecision": 0,
                                "orderKey": 10,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
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
                                    "defaultValue": "nextval('jimmer_code_gen.gen_association_id_seq'::regclass)",
                                    "displaySize": 19,
                                    "name": "id",
                                    "numericPrecision": 0,
                                    "orderKey": 0,
                                    "partOfFk": false,
                                    "partOfPk": true,
                                    "partOfUniqueIdx": true,
                                    "remark": "",
                                    "type": "bigserial",
                                    "typeCode": -5,
                                    "typeNotNull": true
                                }
                            },
                            "id": "ba00edfa-c730-4718-9074-793bb21e6f36",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 311
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "data": {
                                "column": {
                                    "autoIncrement": false,
                                    "comment": "关联注释",
                                    "defaultValue": "''::text",
                                    "displaySize": 2147483647,
                                    "name": "comment",
                                    "numericPrecision": 0,
                                    "orderKey": 1,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "0b322232-a6cc-454b-9245-ba259f525f91",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 311
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
                                    "orderKey": 2,
                                    "partOfFk": true,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "int8",
                                    "typeCode": -5,
                                    "typeNotNull": true
                                }
                            },
                            "id": "6d58884f-e5da-4126-933e-ddf1ab379195",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 311
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
                                    "orderKey": 3,
                                    "partOfFk": true,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "int8",
                                    "typeCode": -5,
                                    "typeNotNull": true
                                }
                            },
                            "id": "5a5b3abc-4513-4e7e-92d3-401ca1ac14d5",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 311
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
                                    "displaySize": 2147483647,
                                    "name": "association_type",
                                    "numericPrecision": 0,
                                    "orderKey": 4,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "38a7cbe3-1d35-4539-b63a-2c6cbce70aa3",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 311
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
                                    "displaySize": 2147483647,
                                    "name": "dissociate_action",
                                    "numericPrecision": 0,
                                    "orderKey": 5,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": false
                                }
                            },
                            "id": "201196b4-3540-499d-9773-8f6c02f1a4bf",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 311
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "data": {
                                "column": {
                                    "autoIncrement": false,
                                    "comment": "是否伪外键",
                                    "defaultValue": "true",
                                    "displaySize": 1,
                                    "name": "fake",
                                    "numericPrecision": 0,
                                    "orderKey": 6,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "bool",
                                    "typeCode": -7,
                                    "typeNotNull": true
                                }
                            },
                            "id": "1ab18d04-d3f5-43fe-b66b-ad18f353b129",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 311
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
                                    "orderKey": 7,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "int8",
                                    "typeCode": -5,
                                    "typeNotNull": true
                                }
                            },
                            "id": "ee64f51f-98be-4622-87ec-33e32be34e05",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 311
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
                                    "displaySize": 35,
                                    "name": "created_time",
                                    "numericPrecision": 6,
                                    "orderKey": 8,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "timestamptz",
                                    "typeCode": 93,
                                    "typeNotNull": true
                                }
                            },
                            "id": "12bc3890-a475-462e-8d8f-08943c51b137",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 311
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
                                    "displaySize": 35,
                                    "name": "modified_time",
                                    "numericPrecision": 6,
                                    "orderKey": 9,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "timestamptz",
                                    "typeCode": 93,
                                    "typeNotNull": true
                                }
                            },
                            "id": "fe1fd340-303b-459d-9ee2-009b40f1e249",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 311
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "data": {
                                "column": {
                                    "autoIncrement": false,
                                    "comment": "备注",
                                    "defaultValue": "''::text",
                                    "displaySize": 2147483647,
                                    "name": "remark",
                                    "numericPrecision": 0,
                                    "orderKey": 10,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "5eb4f371-562f-451c-9583-dd9c597ec514",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 311
                                }
                            }
                        }
                    ]
                },
                "zIndex": 19
            },
            {
                "position": {
                    "x": 4724.17578125,
                    "y": 1498.684326171875
                },
                "size": {
                    "width": 303,
                    "height": 244
                },
                "view": "vue-shape-view",
                "shape": "model",
                "id": "c49eb9af-aeb4-43e4-b730-b1dfec04d8e1",
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
                                "defaultValue": "nextval('jimmer_code_gen.gen_schema_id_seq'::regclass)",
                                "displaySize": 19,
                                "name": "id",
                                "numericPrecision": 0,
                                "orderKey": 0,
                                "partOfFk": false,
                                "partOfPk": true,
                                "partOfUniqueIdx": true,
                                "remark": "",
                                "type": "bigserial",
                                "typeCode": -5,
                                "typeNotNull": true
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
                                "type": "int8",
                                "typeCode": -5,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "名称",
                                "defaultValue": null,
                                "displaySize": 2147483647,
                                "name": "name",
                                "numericPrecision": 0,
                                "orderKey": 2,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
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
                                "type": "int8",
                                "typeCode": -5,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "创建时间",
                                "defaultValue": "CURRENT_TIMESTAMP",
                                "displaySize": 35,
                                "name": "created_time",
                                "numericPrecision": 6,
                                "orderKey": 4,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "timestamptz",
                                "typeCode": 93,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "修改时间",
                                "defaultValue": "CURRENT_TIMESTAMP",
                                "displaySize": 35,
                                "name": "modified_time",
                                "numericPrecision": 6,
                                "orderKey": 5,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "timestamptz",
                                "typeCode": 93,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "备注",
                                "defaultValue": "''::text",
                                "displaySize": 2147483647,
                                "name": "remark",
                                "numericPrecision": 0,
                                "orderKey": 6,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
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
                                    "defaultValue": "nextval('jimmer_code_gen.gen_schema_id_seq'::regclass)",
                                    "displaySize": 19,
                                    "name": "id",
                                    "numericPrecision": 0,
                                    "orderKey": 0,
                                    "partOfFk": false,
                                    "partOfPk": true,
                                    "partOfUniqueIdx": true,
                                    "remark": "",
                                    "type": "bigserial",
                                    "typeCode": -5,
                                    "typeNotNull": true
                                }
                            },
                            "id": "b76a96dc-c862-46d2-add4-975a934136f0",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 303
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
                                    "type": "int8",
                                    "typeCode": -5,
                                    "typeNotNull": true
                                }
                            },
                            "id": "afcab7d6-4d4b-40cd-935d-7000e1381df7",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 303
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
                                    "displaySize": 2147483647,
                                    "name": "name",
                                    "numericPrecision": 0,
                                    "orderKey": 2,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "09535fd6-52dd-4259-b9ce-c1bd19dadb83",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 303
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
                                    "type": "int8",
                                    "typeCode": -5,
                                    "typeNotNull": true
                                }
                            },
                            "id": "7039649c-f69f-4291-ad86-b98bc7bda32e",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 303
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
                                    "displaySize": 35,
                                    "name": "created_time",
                                    "numericPrecision": 6,
                                    "orderKey": 4,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "timestamptz",
                                    "typeCode": 93,
                                    "typeNotNull": true
                                }
                            },
                            "id": "235d22d7-c136-4c30-980b-ff5d39e8e433",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 303
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
                                    "displaySize": 35,
                                    "name": "modified_time",
                                    "numericPrecision": 6,
                                    "orderKey": 5,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "timestamptz",
                                    "typeCode": 93,
                                    "typeNotNull": true
                                }
                            },
                            "id": "488e4331-2f73-4cea-97ad-749f69a96e8b",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 303
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "data": {
                                "column": {
                                    "autoIncrement": false,
                                    "comment": "备注",
                                    "defaultValue": "''::text",
                                    "displaySize": 2147483647,
                                    "name": "remark",
                                    "numericPrecision": 0,
                                    "orderKey": 6,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "dc407485-c90e-42a2-a326-c7a320a01ee5",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 303
                                }
                            }
                        }
                    ]
                },
                "zIndex": 20
            },
            {
                "position": {
                    "x": 4171.17578125,
                    "y": 1498.684326171875
                },
                "size": {
                    "width": 291,
                    "height": 214
                },
                "view": "vue-shape-view",
                "shape": "model",
                "id": "d67199c9-8de4-446b-ab2f-2027f5ccb4f5",
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
                                "defaultValue": "nextval('jimmer_code_gen.gen_model_id_seq'::regclass)",
                                "displaySize": 19,
                                "name": "id",
                                "numericPrecision": 0,
                                "orderKey": 0,
                                "partOfFk": false,
                                "partOfPk": true,
                                "partOfUniqueIdx": true,
                                "remark": "",
                                "type": "bigserial",
                                "typeCode": -5,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "名称",
                                "defaultValue": null,
                                "displaySize": 2147483647,
                                "name": "name",
                                "numericPrecision": 0,
                                "orderKey": 1,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
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
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "创建时间",
                                "defaultValue": "CURRENT_TIMESTAMP",
                                "displaySize": 35,
                                "name": "created_time",
                                "numericPrecision": 6,
                                "orderKey": 3,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "timestamptz",
                                "typeCode": 93,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "修改时间",
                                "defaultValue": "CURRENT_TIMESTAMP",
                                "displaySize": 35,
                                "name": "modified_time",
                                "numericPrecision": 6,
                                "orderKey": 4,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "timestamptz",
                                "typeCode": 93,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "备注",
                                "defaultValue": null,
                                "displaySize": 2147483647,
                                "name": "remark",
                                "numericPrecision": 0,
                                "orderKey": 5,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
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
                                    "defaultValue": "nextval('jimmer_code_gen.gen_model_id_seq'::regclass)",
                                    "displaySize": 19,
                                    "name": "id",
                                    "numericPrecision": 0,
                                    "orderKey": 0,
                                    "partOfFk": false,
                                    "partOfPk": true,
                                    "partOfUniqueIdx": true,
                                    "remark": "",
                                    "type": "bigserial",
                                    "typeCode": -5,
                                    "typeNotNull": true
                                }
                            },
                            "id": "8feb5688-171c-4924-bbe0-b163e4290b1a",
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
                                    "displaySize": 2147483647,
                                    "name": "name",
                                    "numericPrecision": 0,
                                    "orderKey": 1,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "cb0266fc-50bd-4333-b14f-796780b924a0",
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
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "28021eb8-c575-47e9-bc26-c07ee78b1b66",
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
                                    "displaySize": 35,
                                    "name": "created_time",
                                    "numericPrecision": 6,
                                    "orderKey": 3,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "timestamptz",
                                    "typeCode": 93,
                                    "typeNotNull": true
                                }
                            },
                            "id": "c3d376ce-f5ae-444d-94e9-f1d022a29ae0",
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
                                    "displaySize": 35,
                                    "name": "modified_time",
                                    "numericPrecision": 6,
                                    "orderKey": 4,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "timestamptz",
                                    "typeCode": 93,
                                    "typeNotNull": true
                                }
                            },
                            "id": "f1afe7c2-328a-42e9-8ea4-22c911063715",
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
                                    "defaultValue": null,
                                    "displaySize": 2147483647,
                                    "name": "remark",
                                    "numericPrecision": 0,
                                    "orderKey": 5,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "b5f3176b-1192-4ca1-8eeb-61368a1e3adb",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 291
                                }
                            }
                        }
                    ]
                },
                "zIndex": 21
            },
            {
                "position": {
                    "x": 3680.17578125,
                    "y": 1498.684326171875
                },
                "size": {
                    "width": 291,
                    "height": 334
                },
                "view": "vue-shape-view",
                "shape": "model",
                "id": "397968c5-49e6-4e68-a053-27be682c9afe",
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
                                "defaultValue": "nextval('jimmer_code_gen.gen_entity_id_seq'::regclass)",
                                "displaySize": 19,
                                "name": "id",
                                "numericPrecision": 0,
                                "orderKey": 0,
                                "partOfFk": false,
                                "partOfPk": true,
                                "partOfUniqueIdx": true,
                                "remark": "",
                                "type": "bigserial",
                                "typeCode": -5,
                                "typeNotNull": true
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
                                "type": "int8",
                                "typeCode": -5,
                                "typeNotNull": false
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
                                "type": "int8",
                                "typeCode": -5,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "类名称",
                                "defaultValue": null,
                                "displaySize": 2147483647,
                                "name": "name",
                                "numericPrecision": 0,
                                "orderKey": 3,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "类注释",
                                "defaultValue": null,
                                "displaySize": 2147483647,
                                "name": "comment",
                                "numericPrecision": 0,
                                "orderKey": 4,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "作者",
                                "defaultValue": "''::text",
                                "displaySize": 2147483647,
                                "name": "author",
                                "numericPrecision": 0,
                                "orderKey": 5,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
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
                                "type": "int8",
                                "typeCode": -5,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "创建时间",
                                "defaultValue": "CURRENT_TIMESTAMP",
                                "displaySize": 35,
                                "name": "created_time",
                                "numericPrecision": 6,
                                "orderKey": 7,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "timestamptz",
                                "typeCode": 93,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "修改时间",
                                "defaultValue": "CURRENT_TIMESTAMP",
                                "displaySize": 35,
                                "name": "modified_time",
                                "numericPrecision": 6,
                                "orderKey": 8,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "timestamptz",
                                "typeCode": 93,
                                "typeNotNull": true
                            },
                            {
                                "autoIncrement": false,
                                "comment": "备注",
                                "defaultValue": "''::text",
                                "displaySize": 2147483647,
                                "name": "remark",
                                "numericPrecision": 0,
                                "orderKey": 9,
                                "partOfFk": false,
                                "partOfPk": false,
                                "partOfUniqueIdx": false,
                                "remark": "",
                                "type": "text",
                                "typeCode": 12,
                                "typeNotNull": true
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
                                    "defaultValue": "nextval('jimmer_code_gen.gen_entity_id_seq'::regclass)",
                                    "displaySize": 19,
                                    "name": "id",
                                    "numericPrecision": 0,
                                    "orderKey": 0,
                                    "partOfFk": false,
                                    "partOfPk": true,
                                    "partOfUniqueIdx": true,
                                    "remark": "",
                                    "type": "bigserial",
                                    "typeCode": -5,
                                    "typeNotNull": true
                                }
                            },
                            "id": "de1d1fff-5bc5-462d-9eed-ff1018b23300",
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
                                    "type": "int8",
                                    "typeCode": -5,
                                    "typeNotNull": false
                                }
                            },
                            "id": "a255e1ad-4cdf-46ab-bf8b-219c80da9515",
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
                                    "type": "int8",
                                    "typeCode": -5,
                                    "typeNotNull": true
                                }
                            },
                            "id": "c8b476e1-78ea-4c61-866f-13193c1a46ec",
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
                                    "comment": "类名称",
                                    "defaultValue": null,
                                    "displaySize": 2147483647,
                                    "name": "name",
                                    "numericPrecision": 0,
                                    "orderKey": 3,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "dcc4f075-3c3d-4452-9e76-e78721164be4",
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
                                    "comment": "类注释",
                                    "defaultValue": null,
                                    "displaySize": 2147483647,
                                    "name": "comment",
                                    "numericPrecision": 0,
                                    "orderKey": 4,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "292d1e2e-74f7-49e2-8570-233430e92e94",
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
                                    "comment": "作者",
                                    "defaultValue": "''::text",
                                    "displaySize": 2147483647,
                                    "name": "author",
                                    "numericPrecision": 0,
                                    "orderKey": 5,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "f7433591-e9bf-4ad8-98f8-b1623cd590df",
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
                                    "type": "int8",
                                    "typeCode": -5,
                                    "typeNotNull": true
                                }
                            },
                            "id": "6bee5e2d-e7f2-47e4-be6a-719f94345910",
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
                                    "displaySize": 35,
                                    "name": "created_time",
                                    "numericPrecision": 6,
                                    "orderKey": 7,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "timestamptz",
                                    "typeCode": 93,
                                    "typeNotNull": true
                                }
                            },
                            "id": "27aa90a5-ed94-4c14-9a16-4fb508f4ec47",
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
                                    "displaySize": 35,
                                    "name": "modified_time",
                                    "numericPrecision": 6,
                                    "orderKey": 8,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "timestamptz",
                                    "typeCode": 93,
                                    "typeNotNull": true
                                }
                            },
                            "id": "7af413ee-b7e2-44e2-8b3d-f9221027b188",
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
                                    "defaultValue": "''::text",
                                    "displaySize": 2147483647,
                                    "name": "remark",
                                    "numericPrecision": 0,
                                    "orderKey": 9,
                                    "partOfFk": false,
                                    "partOfPk": false,
                                    "partOfUniqueIdx": false,
                                    "remark": "",
                                    "type": "text",
                                    "typeCode": 12,
                                    "typeNotNull": true
                                }
                            },
                            "id": "ef9efb23-be5a-457b-bf5c-48ce4b5d236c",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 291
                                }
                            }
                        }
                    ]
                },
                "zIndex": 22
            }
        ]
    },
    "zoom": 0.4406005055587108,
    "transform": "matrix(0.4406005055587108,0,0,0.4406005055587108,-1204.5741175483035,-560.3210590422163)"
}
"""
}

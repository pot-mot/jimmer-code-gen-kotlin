package top.potmot.model.associations.reversedOneToOne

const val ONE_TO_ONE = """
{
    "json": {
        "cells": [
            {
                "position": {
                    "x": 964.4228515625,
                    "y": 74.73188781738281
                },
                "size": {
                    "width": 147,
                    "height": 94
                },
                "view": "vue-shape-view",
                "shape": "TABLE_NODE",
                "id": "6746a457-8691-4b42-92bc-e8664ee9eaf5",
                "data": {
                    "table": {
                        "name": "user_detail",
                        "comment": "",
                        "remark": "",
                        "type": "TABLE",
                        "superTables": [],
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
                },
                "zIndex": 71,
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
                            "id": "ff1a461d-e712-4696-96f6-3c9175ef6fe6",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 147
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "id": "7e764e60-26c9-4ad4-927b-b98547255abd",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 147
                                }
                            }
                        }
                    ]
                }
            },
            {
                "position": {
                    "x": 652.4228515625,
                    "y": 74.73188781738281
                },
                "size": {
                    "width": 112,
                    "height": 64
                },
                "view": "vue-shape-view",
                "shape": "TABLE_NODE",
                "id": "538e3f0c-97ed-4e61-b1d5-c8d62f66d760",
                "data": {
                    "table": {
                        "name": "user",
                        "comment": "",
                        "remark": "",
                        "type": "TABLE",
                        "superTables": [],
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
                },
                "zIndex": 73,
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
                            "id": "8427117e-6b0f-479e-a7f5-bb042f4aed7c",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 112
                                }
                            }
                        }
                    ]
                }
            },
            {
                "shape": "ASSOCIATION_EDGE",
                "router": {
                    "name": "er",
                    "args": {
                        "direction": "H"
                    }
                },
                "id": "83ede068-469f-4110-a867-ee868e6f327b",
                "zIndex": 74,
                "data": {
                    "association": {
                        "type": "ONE_TO_ONE",
                        "fake": false,
                        "name": "fk_detail_user",
                        "sourceTableName": "user",
                        "targetTableName": "user_detail",
                        "columnReferences": [
                            {
                                "sourceColumnName": "id",
                                "targetColumnName": "user_id"
                            }
                        ],
                        "updateAction": "",
                        "deleteAction": ""
                    }
                },
                "source": {
                    "cell": "6746a457-8691-4b42-92bc-e8664ee9eaf5",
                    "port": "7e764e60-26c9-4ad4-927b-b98547255abd"
                },
                "target": {
                    "cell": "538e3f0c-97ed-4e61-b1d5-c8d62f66d760",
                    "port": "8427117e-6b0f-479e-a7f5-bb042f4aed7c"
                }
            }
        ]
    },
    "zoom": 2.2549019607843137,
    "transform": "matrix(2.2549019607843137,0,0,2.2549019607843137,-1371.1495672487745,158.0065274706074)"
}
"""

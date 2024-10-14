package top.potmot.model.idViewType

const val GRAPH_DATA = """
{
    "json": {
        "cells": [
            {
                "position": {
                    "x": 667.3515625,
                    "y": 168
                },
                "size": {
                    "width": 172,
                    "height": 100
                },
                "view": "vue-shape-view",
                "shape": "TABLE_NODE",
                "id": "71f6bf4c-4f9c-4f1c-99f5-274ec3d606b3",
                "data": {
                    "table": {
                        "name": "source",
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
                                "defaultValue": null,
                                "partOfPk": true,
                                "autoIncrement": true,
                                "remark": "",
                                "logicalDelete": false,
                                "businessKey": false
                            },
                            {
                                "orderKey": 2,
                                "name": "target_id",
                                "comment": "",
                                "typeCode": 4,
                                "overwriteByRaw": false,
                                "rawType": "INTEGER",
                                "typeNotNull": true,
                                "dataSize": 0,
                                "numericPrecision": 0,
                                "defaultValue": null,
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
                            "id": "a8b522b3-1ffe-4864-a686-8161e7ac3401",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 172
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "id": "a4f2a428-c434-463f-9ed2-78f2166e4ac1",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 172
                                }
                            }
                        }
                    ]
                },
                "zIndex": 36
            },
            {
                "position": {
                    "x": 341.3515625,
                    "y": 168
                },
                "size": {
                    "width": 126,
                    "height": 70
                },
                "view": "vue-shape-view",
                "shape": "TABLE_NODE",
                "id": "4da8195c-a6b4-4993-a37a-644f07aea80b",
                "data": {
                    "table": {
                        "name": "target",
                        "comment": "",
                        "remark": "",
                        "type": "TABLE",
                        "superTables": [],
                        "columns": [
                            {
                                "orderKey": 1,
                                "name": "id",
                                "comment": "",
                                "typeCode": 4,
                                "overwriteByRaw": false,
                                "rawType": "INTEGER",
                                "typeNotNull": true,
                                "dataSize": 0,
                                "numericPrecision": 0,
                                "defaultValue": null,
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
                            "id": "22679496-569e-4cb8-b9fd-7b7ed3fe564e",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 126
                                }
                            }
                        }
                    ]
                },
                "zIndex": 42
            },
            {
                "shape": "ASSOCIATION_EDGE",
                "router": {
                    "name": "er",
                    "args": {
                        "direction": "H"
                    }
                },
                "id": "0f5265cb-5f5a-41fd-872f-ee35aaeafba9",
                "data": {
                    "association": {
                        "name": "fk_source_target_id",
                        "sourceTableName": "source",
                        "targetTableName": "target",
                        "columnReferences": [
                            {
                                "sourceColumnName": "target_id",
                                "targetColumnName": "id"
                            }
                        ],
                        "type": "ONE_TO_ONE",
                        "fake": false,
                        "updateAction": "",
                        "deleteAction": ""
                    }
                },
                "source": {
                    "cell": "71f6bf4c-4f9c-4f1c-99f5-274ec3d606b3",
                    "port": "a4f2a428-c434-463f-9ed2-78f2166e4ac1"
                },
                "target": {
                    "cell": "4da8195c-a6b4-4993-a37a-644f07aea80b",
                    "port": "22679496-569e-4cb8-b9fd-7b7ed3fe564e"
                },
                "zIndex": 43
            }
        ]
    },
    "zoom": 2.078313201134966,
    "transform": "matrix(2.078313201134966,0,0,2.078313201134966,-609.435445654404,-20.572277847422583)"
}
"""
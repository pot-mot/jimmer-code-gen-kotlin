package top.potmot.model.superTable

const val GRAPH_DATA = """
{
    "json": {
        "cells": [
            {
                "position": {
                    "x": 755,
                    "y": 373
                },
                "size": {
                    "width": 196,
                    "height": 94
                },
                "view": "vue-shape-view",
                "shape": "TABLE_NODE",
                "id": "b6e5e11f-cd8c-496a-b94e-1603ec056c3d",
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
                            "id": "386b69c7-7833-4973-82f7-0ab630afb561",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 196
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "id": "92e30bca-7af6-490a-86d7-fdbf321592fd",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 196
                                }
                            }
                        }
                    ]
                },
                "zIndex": 11
            },
            {
                "position": {
                    "x": 398,
                    "y": 373
                },
                "size": {
                    "width": 157,
                    "height": 94
                },
                "view": "vue-shape-view",
                "shape": "TABLE_NODE",
                "id": "2ae71afb-9fd4-46ce-b01f-7f33ab9f46f0",
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
                            "id": "27c9e8c3-2e85-426e-b0af-67bba99ac4ba",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 157
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "id": "9d9dce45-9aa5-46ca-b73c-9ba7dcae8b1f",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 157
                                }
                            }
                        }
                    ]
                },
                "zIndex": 14
            },
            {
                "shape": "ASSOCIATION_EDGE",
                "router": {
                    "name": "er",
                    "args": {
                        "direction": "H"
                    }
                },
                "id": "beb49dd2-d1ac-4382-9290-bb0b0d6b7cf8",
                "source": {
                    "cell": "b6e5e11f-cd8c-496a-b94e-1603ec056c3d",
                    "port": "386b69c7-7833-4973-82f7-0ab630afb561"
                },
                "zIndex": 15,
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
                },
                "target": {
                    "cell": "2ae71afb-9fd4-46ce-b01f-7f33ab9f46f0",
                    "port": "27c9e8c3-2e85-426e-b0af-67bba99ac4ba"
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
                "id": "120ad16b-d5c5-4ef7-b7eb-e041a2861079",
                "source": {
                    "cell": "b6e5e11f-cd8c-496a-b94e-1603ec056c3d",
                    "port": "92e30bca-7af6-490a-86d7-fdbf321592fd"
                },
                "zIndex": 16,
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
                },
                "target": {
                    "cell": "2ae71afb-9fd4-46ce-b01f-7f33ab9f46f0",
                    "port": "27c9e8c3-2e85-426e-b0af-67bba99ac4ba"
                }
            }
        ]
    },
    "zoom": 0.65,
    "transform": "matrix(0.65,0,0,0.65,-83.27111949580353,24.007823542914537)"
}
"""

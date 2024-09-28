package top.potmot.model.superTable

const val GRAPH_DATA = """
{
    "json": {
        "cells": [
            {
                "position": {
                    "x": 1533.703125,
                    "y": 424
                },
                "size": {
                    "width": 315,
                    "height": 100
                },
                "view": "vue-shape-view",
                "shape": "TABLE_NODE",
                "id": "6a560466-61a5-4aef-a9a7-f1d3016a5dd4",
                "data": {
                    "table": {
                        "name": "SYS_USER",
                        "comment": "用户",
                        "remark": "",
                        "type": "TABLE",
                        "superTables": [
                            {
                                "name": "BASE_ENTITY"
                            }
                        ],
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
                                "partOfPk": true,
                                "autoIncrement": true,
                                "remark": "",
                                "logicalDelete": false,
                                "businessKey": false
                            },
                            {
                                "orderKey": 2,
                                "name": "name",
                                "comment": "名称",
                                "typeCode": 12,
                                "overwriteByRaw": false,
                                "rawType": "VARCHAR",
                                "typeNotNull": true,
                                "dataSize": 255,
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
                "zIndex": 184,
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
                            "id": "ac3c9b28-7e0a-4331-bb4b-5cdc6e34a014",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 315
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "id": "11e6c942-173a-4e55-a4af-424bfaf69379",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 315
                                }
                            }
                        }
                    ]
                }
            },
            {
                "position": {
                    "x": 2073.703125,
                    "y": 244
                },
                "size": {
                    "width": 247,
                    "height": 100
                },
                "view": "vue-shape-view",
                "shape": "TABLE_NODE",
                "id": "01dd0890-00eb-46f0-9b92-5d7315335800",
                "data": {
                    "table": {
                        "name": "BASE_ENTITY",
                        "comment": "基础实体",
                        "remark": "",
                        "type": "SUPER_TABLE",
                        "superTables": [],
                        "columns": [
                            {
                                "orderKey": 1,
                                "name": "created_by",
                                "comment": "",
                                "typeCode": 4,
                                "overwriteByRaw": false,
                                "rawType": "INTEGER",
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
                                "name": "modified_by",
                                "comment": "",
                                "typeCode": 4,
                                "overwriteByRaw": false,
                                "rawType": "INTEGER",
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
                "zIndex": 205,
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
                            "id": "84a64729-c2a2-420d-81ba-07c5f8100bd5",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 247
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "id": "a87142ad-a704-4f37-bbfa-d011521c9dd2",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 247
                                }
                            }
                        }
                    ]
                }
            },
            {
                "position": {
                    "x": 2030,
                    "y": 560
                },
                "size": {
                    "width": 342,
                    "height": 131
                },
                "view": "vue-shape-view",
                "shape": "TABLE_NODE",
                "id": "cc4e2d69-86e0-43be-a939-3bad1790c64e",
                "data": {
                    "table": {
                        "name": "BIZ_PROJECT",
                        "comment": "项目",
                        "remark": "",
                        "type": "TABLE",
                        "superTables": [
                            {
                                "name": "BASE_ENTITY"
                            }
                        ],
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
                                "partOfPk": true,
                                "autoIncrement": true,
                                "remark": "",
                                "logicalDelete": false,
                                "businessKey": false
                            },
                            {
                                "orderKey": 2,
                                "name": "user",
                                "comment": "用户",
                                "typeCode": 4,
                                "overwriteByRaw": false,
                                "rawType": "INTEGER",
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
                                "name": "name",
                                "comment": "名称",
                                "typeCode": 12,
                                "overwriteByRaw": false,
                                "rawType": "VARCHAR",
                                "typeNotNull": true,
                                "dataSize": 255,
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
                "zIndex": 220,
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
                            "id": "274fe553-9d63-4c66-b9d7-0f336e539cc0",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 342
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "id": "56516e59-d46f-409f-8678-7a9ac831ab19",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 342
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "id": "5e2b3fb8-5ff9-4c9e-b6d8-e8ceb7420923",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 342
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
                "id": "658e5a86-a71e-432f-816a-146e2854bcd9",
                "data": {
                    "association": {
                        "name": "fk_biz_project_sys_user",
                        "sourceTableName": "BIZ_PROJECT",
                        "targetTableName": "SYS_USER",
                        "columnReferences": [
                            {
                                "sourceColumnName": "user",
                                "targetColumnName": "id"
                            }
                        ],
                        "type": "MANY_TO_ONE",
                        "fake": false,
                        "updateAction": "",
                        "deleteAction": ""
                    }
                },
                "source": {
                    "cell": "cc4e2d69-86e0-43be-a939-3bad1790c64e",
                    "port": "56516e59-d46f-409f-8678-7a9ac831ab19"
                },
                "target": {
                    "cell": "6a560466-61a5-4aef-a9a7-f1d3016a5dd4",
                    "port": "ac3c9b28-7e0a-4331-bb4b-5cdc6e34a014"
                },
                "zIndex": 221
            },
            {
                "shape": "ASSOCIATION_EDGE",
                "router": {
                    "name": "er",
                    "args": {
                        "direction": "H"
                    }
                },
                "id": "7c2f4910-979e-450c-8911-a7716da66127",
                "data": {
                    "association": {
                        "name": "fk_{SOURCE}_created_by",
                        "sourceTableName": "BASE_ENTITY",
                        "targetTableName": "SYS_USER",
                        "columnReferences": [
                            {
                                "sourceColumnName": "created_by",
                                "targetColumnName": "id"
                            }
                        ],
                        "type": "MANY_TO_ONE",
                        "fake": false,
                        "updateAction": "",
                        "deleteAction": ""
                    }
                },
                "source": {
                    "cell": "01dd0890-00eb-46f0-9b92-5d7315335800",
                    "port": "84a64729-c2a2-420d-81ba-07c5f8100bd5"
                },
                "target": {
                    "cell": "6a560466-61a5-4aef-a9a7-f1d3016a5dd4",
                    "port": "ac3c9b28-7e0a-4331-bb4b-5cdc6e34a014"
                },
                "zIndex": 222
            },
            {
                "shape": "ASSOCIATION_EDGE",
                "router": {
                    "name": "er",
                    "args": {
                        "direction": "H"
                    }
                },
                "id": "f6621fee-6fb3-4c27-9057-4976f0251cce",
                "data": {
                    "association": {
                        "name": "fk_{SOURCE}_modified_by",
                        "sourceTableName": "BASE_ENTITY",
                        "targetTableName": "SYS_USER",
                        "columnReferences": [
                            {
                                "sourceColumnName": "modified_by",
                                "targetColumnName": "id"
                            }
                        ],
                        "type": "MANY_TO_ONE",
                        "fake": false,
                        "updateAction": "",
                        "deleteAction": ""
                    }
                },
                "source": {
                    "cell": "01dd0890-00eb-46f0-9b92-5d7315335800",
                    "port": "a87142ad-a704-4f37-bbfa-d011521c9dd2"
                },
                "target": {
                    "cell": "6a560466-61a5-4aef-a9a7-f1d3016a5dd4",
                    "port": "ac3c9b28-7e0a-4331-bb4b-5cdc6e34a014"
                },
                "zIndex": 223
            }
        ]
    },
    "zoom": 0.7,
    "transform": "matrix(0.7,0,0,0.7,-759.978685891044,150.65882493821357)"
}
"""
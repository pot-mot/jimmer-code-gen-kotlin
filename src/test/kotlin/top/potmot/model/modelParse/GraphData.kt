package top.potmot.model.modelParse

const val GRAPH_DATA = """
{
    "json": {
        "cells": [
            {
                "position": {
                    "x": 1912,
                    "y": 920
                },
                "size": {
                    "width": 159,
                    "height": 94
                },
                "view": "vue-shape-view",
                "shape": "TABLE_NODE",
                "id": "7e722ed4-d8aa-4a3e-bab5-76abc86037f8",
                "data": {
                    "table": {
                        "name": "tree_node",
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
                                "name": "parent_id",
                                "comment": "",
                                "typeCode": -5,
                                "overwriteByRaw": false,
                                "rawType": "BIGINT",
                                "typeNotNull": false,
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
                "zIndex": 542,
                "children": [
                    "4d3c57a5-8583-400a-b3c4-53fbc226e7b9"
                ],
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
                            "id": "5c88eaae-c14e-4465-a854-3887fac3a533",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 159
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "id": "f29143a4-0349-4e84-9b93-2bde69b865f6",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 159
                                }
                            }
                        }
                    ]
                }
            },
            {
                "position": {
                    "x": 1188,
                    "y": 1442
                },
                "size": {
                    "width": 124,
                    "height": 64
                },
                "view": "vue-shape-view",
                "shape": "TABLE_NODE",
                "id": "c8d4e971-d561-4b05-b179-0ac750f07092",
                "data": {
                    "table": {
                        "name": "o_o_target",
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
                "zIndex": 546,
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
                            "id": "32280c8a-fea2-4b2d-b752-fab1e9b0acae",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 124
                                }
                            }
                        }
                    ]
                }
            },
            {
                "position": {
                    "x": 1188,
                    "y": 1248
                },
                "size": {
                    "width": 162,
                    "height": 94
                },
                "view": "vue-shape-view",
                "shape": "TABLE_NODE",
                "id": "e5742b10-aafd-4f20-823d-c6142f4b67d5",
                "data": {
                    "table": {
                        "name": "o_m_target",
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
                },
                "zIndex": 550,
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
                            "id": "ceb54d62-1b2b-4d11-b4f0-f2538a856db5",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 162
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "id": "3f001db9-e772-47ab-8858-118491fa7a4f",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 162
                                }
                            }
                        }
                    ]
                }
            },
            {
                "position": {
                    "x": 1550,
                    "y": 1442
                },
                "size": {
                    "width": 156,
                    "height": 94
                },
                "view": "vue-shape-view",
                "shape": "TABLE_NODE",
                "id": "d9556a29-b408-4f5c-afca-7e492a233746",
                "data": {
                    "table": {
                        "name": "o_o_source",
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
                                "name": "target_id",
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
                "zIndex": 554,
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
                            "id": "0a79ec54-d716-415d-84d8-8073e5ef64d4",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 156
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "id": "803b81ab-aabd-4886-a496-4fa5cef4ce97",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 156
                                }
                            }
                        }
                    ]
                }
            },
            {
                "position": {
                    "x": 1550,
                    "y": 1278
                },
                "size": {
                    "width": 135,
                    "height": 64
                },
                "view": "vue-shape-view",
                "shape": "TABLE_NODE",
                "id": "de33b66d-49ea-45bc-885a-aff36a5e6460",
                "data": {
                    "table": {
                        "name": "o_m_source",
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
                "zIndex": 558,
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
                            "id": "a2b152dd-a062-44bc-b700-a59e39004d0a",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 135
                                }
                            }
                        }
                    ]
                }
            },
            {
                "position": {
                    "x": 1550,
                    "y": 920
                },
                "size": {
                    "width": 135,
                    "height": 64
                },
                "view": "vue-shape-view",
                "shape": "TABLE_NODE",
                "id": "ef35c16d-7143-429d-88b7-148cfc049c4e",
                "data": {
                    "table": {
                        "name": "m_n_source",
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
                "zIndex": 562,
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
                            "id": "d220f961-aa73-4bce-bf21-72b2c91d57ef",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 135
                                }
                            }
                        }
                    ]
                }
            },
            {
                "position": {
                    "x": 1188,
                    "y": 1084
                },
                "size": {
                    "width": 128,
                    "height": 64
                },
                "view": "vue-shape-view",
                "shape": "TABLE_NODE",
                "id": "5a99db99-4f1d-4794-86f7-16eeb6844c7a",
                "data": {
                    "table": {
                        "name": "m_o_target",
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
                "zIndex": 566,
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
                            "id": "578368b9-5b63-4658-9d61-58d8b4777ba9",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 128
                                }
                            }
                        }
                    ]
                }
            },
            {
                "position": {
                    "x": 1550,
                    "y": 1084
                },
                "size": {
                    "width": 162,
                    "height": 94
                },
                "view": "vue-shape-view",
                "shape": "TABLE_NODE",
                "id": "dbe97e09-d3d5-4af2-b6a2-40f30aa9607d",
                "data": {
                    "table": {
                        "name": "m_o_source",
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
                },
                "zIndex": 570,
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
                            "id": "29701c04-188f-4264-ad9b-89a02b7235aa",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 162
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "id": "18c4be80-de43-4f02-8644-f88eb6d683e8",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 162
                                }
                            }
                        }
                    ]
                }
            },
            {
                "position": {
                    "x": 1188,
                    "y": 920
                },
                "size": {
                    "width": 128,
                    "height": 64
                },
                "view": "vue-shape-view",
                "shape": "TABLE_NODE",
                "id": "14c0c1ee-98fb-48d8-bc84-7557a0e50db9",
                "data": {
                    "table": {
                        "name": "m_n_target",
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
                "zIndex": 574,
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
                            "id": "6e691389-4c1f-4dac-8ae1-e035498dd2a2",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 128
                                }
                            }
                        }
                    ]
                }
            },
            {
                "shape": "ASSOCIATION_EDGE",
                "router": {
                    "name": "orth",
                    "args": {
                        "direction": "H",
                        "padding": 20
                    }
                },
                "id": "4d3c57a5-8583-400a-b3c4-53fbc226e7b9",
                "data": {
                    "association": {
                        "type": "MANY_TO_ONE",
                        "fake": false,
                        "updateAction": "",
                        "deleteAction": "",
                        "name": "fk_tree_node_parent",
                        "sourceTable": {
                            "name": "tree_node",
                            "comment": ""
                        },
                        "targetTable": {
                            "name": "tree_node",
                            "comment": ""
                        },
                        "columnReferences": [
                            {
                                "sourceColumn": {
                                    "name": "parent_id",
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
                },
                "zIndex": 576,
                "parent": "7e722ed4-d8aa-4a3e-bab5-76abc86037f8",
                "source": {
                    "cell": "7e722ed4-d8aa-4a3e-bab5-76abc86037f8",
                    "port": "f29143a4-0349-4e84-9b93-2bde69b865f6"
                },
                "target": {
                    "cell": "7e722ed4-d8aa-4a3e-bab5-76abc86037f8",
                    "port": "5c88eaae-c14e-4465-a854-3887fac3a533"
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
                "id": "ab5bcbb0-e962-4057-a432-0ab96b564856",
                "data": {
                    "association": {
                        "type": "ONE_TO_ONE",
                        "fake": false,
                        "updateAction": "",
                        "deleteAction": "",
                        "name": "fk_one_to_one",
                        "sourceTable": {
                            "name": "o_o_source",
                            "comment": ""
                        },
                        "targetTable": {
                            "name": "o_o_target",
                            "comment": ""
                        },
                        "columnReferences": [
                            {
                                "sourceColumn": {
                                    "name": "target_id",
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
                },
                "zIndex": 577,
                "source": {
                    "cell": "d9556a29-b408-4f5c-afca-7e492a233746",
                    "port": "803b81ab-aabd-4886-a496-4fa5cef4ce97"
                },
                "target": {
                    "cell": "c8d4e971-d561-4b05-b179-0ac750f07092",
                    "port": "32280c8a-fea2-4b2d-b752-fab1e9b0acae"
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
                "id": "29140926-182f-4c0a-b801-cc2860a932ca",
                "data": {
                    "association": {
                        "type": "ONE_TO_MANY",
                        "fake": false,
                        "updateAction": "",
                        "deleteAction": "",
                        "name": "fk_one_to_many",
                        "sourceTable": {
                            "name": "o_m_source",
                            "comment": ""
                        },
                        "targetTable": {
                            "name": "o_m_target",
                            "comment": ""
                        },
                        "columnReferences": [
                            {
                                "sourceColumn": {
                                    "name": "id",
                                    "comment": "",
                                    "rawType": "BIGINT",
                                    "typeCode": -5
                                },
                                "targetColumn": {
                                    "name": "source_id",
                                    "comment": "",
                                    "rawType": "BIGINT",
                                    "typeCode": -5
                                }
                            }
                        ]
                    }
                },
                "zIndex": 578,
                "source": {
                    "cell": "de33b66d-49ea-45bc-885a-aff36a5e6460",
                    "port": "a2b152dd-a062-44bc-b700-a59e39004d0a"
                },
                "target": {
                    "cell": "e5742b10-aafd-4f20-823d-c6142f4b67d5",
                    "port": "3f001db9-e772-47ab-8858-118491fa7a4f"
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
                "id": "4d5f6aa6-b235-47dc-9cfd-0c07ec9aa4a7",
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
                },
                "zIndex": 579,
                "source": {
                    "cell": "dbe97e09-d3d5-4af2-b6a2-40f30aa9607d",
                    "port": "18c4be80-de43-4f02-8644-f88eb6d683e8"
                },
                "target": {
                    "cell": "5a99db99-4f1d-4794-86f7-16eeb6844c7a",
                    "port": "578368b9-5b63-4658-9d61-58d8b4777ba9"
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
                "id": "83177816-33ae-4ed8-acec-4186205d3f7b",
                "data": {
                    "association": {
                        "type": "MANY_TO_MANY",
                        "fake": false,
                        "updateAction": "",
                        "deleteAction": "",
                        "name": "many_to_many_mapping",
                        "sourceTable": {
                            "name": "m_n_source",
                            "comment": ""
                        },
                        "targetTable": {
                            "name": "m_n_target",
                            "comment": ""
                        },
                        "columnReferences": [
                            {
                                "sourceColumn": {
                                    "name": "id",
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
                },
                "zIndex": 580,
                "source": {
                    "cell": "ef35c16d-7143-429d-88b7-148cfc049c4e",
                    "port": "d220f961-aa73-4bce-bf21-72b2c91d57ef"
                },
                "target": {
                    "cell": "14c0c1ee-98fb-48d8-bc84-7557a0e50db9",
                    "port": "6e691389-4c1f-4dac-8ae1-e035498dd2a2"
                }
            }
        ]
    },
    "zoom": 1.0795454422466852,
    "transform": "matrix(1.0795454422466852,0,0,1.0795454422466852,-1141.6192981409736,-893.1818030789295)"
}
"""

package top.potmot.modelLoad

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.babyfish.jimmer.jackson.ImmutableModule
import top.potmot.model.dto.GenModelInput
import top.potmot.util.escape

private val mapper = jacksonObjectMapper()
    .registerModule(ImmutableModule())
    .registerModule(JavaTimeModule())

fun getBaseModel() = mapper.readValue<GenModelInput>(
    baseModel
)

val graphData = """
{
    "json": {
        "cells": [
            {
                "position": {
                    "x": 2812,
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
                "zIndex": 440,
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
                "zIndex": 442,
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
                "zIndex": 446,
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
                "zIndex": 448,
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
                "zIndex": 452,
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
                        "name": "fk_tree_node_tree_node",
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
                "zIndex": 459,
                "parent": "7e722ed4-d8aa-4a3e-bab5-76abc86037f8",
                "labels": [
                    {
                        "markup": [
                            {
                                "tagName": "rect",
                                "selector": "body"
                            },
                            {
                                "tagName": "text",
                                "selector": "ASSOCIATION_LABEL_TEXT_SELECTOR"
                            }
                        ],
                        "attrs": {
                            "ASSOCIATION_LABEL_TEXT_SELECTOR": {
                                "text": "MANY_TO_ONE",
                                "fill": "var(--common-color)",
                                "fontWeight": "normal"
                            },
                            "body": {
                                "ref": "ASSOCIATION_LABEL_TEXT_SELECTOR",
                                "fill": "#f5f5f5"
                            }
                        }
                    }
                ],
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
                "zIndex": 465,
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
                        "name": "m_n_source_m_n_target_mapping",
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
                "zIndex": 466,
                "labels": [
                    {
                        "markup": [
                            {
                                "tagName": "rect",
                                "selector": "body"
                            },
                            {
                                "tagName": "text",
                                "selector": "ASSOCIATION_LABEL_TEXT_SELECTOR"
                            }
                        ],
                        "attrs": {
                            "ASSOCIATION_LABEL_TEXT_SELECTOR": {
                                "text": "MANY_TO_MANY",
                                "fill": "var(--common-color)",
                                "fontWeight": "normal"
                            },
                            "body": {
                                "ref": "ASSOCIATION_LABEL_TEXT_SELECTOR",
                                "fill": "#f5f5f5"
                            }
                        }
                    }
                ],
                "source": {
                    "cell": "ef35c16d-7143-429d-88b7-148cfc049c4e",
                    "port": "d220f961-aa73-4bce-bf21-72b2c91d57ef"
                },
                "target": {
                    "cell": "14c0c1ee-98fb-48d8-bc84-7557a0e50db9",
                    "port": "6e691389-4c1f-4dac-8ae1-e035498dd2a2"
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
                "zIndex": 467,
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
                        "name": "fk_m_o_source_m_o_target",
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
                "zIndex": 468,
                "labels": [
                    {
                        "markup": [
                            {
                                "tagName": "rect",
                                "selector": "body"
                            },
                            {
                                "tagName": "text",
                                "selector": "ASSOCIATION_LABEL_TEXT_SELECTOR"
                            }
                        ],
                        "attrs": {
                            "ASSOCIATION_LABEL_TEXT_SELECTOR": {
                                "text": "MANY_TO_ONE",
                                "fill": "var(--common-color)",
                                "fontWeight": "normal"
                            },
                            "body": {
                                "ref": "ASSOCIATION_LABEL_TEXT_SELECTOR",
                                "fill": "#f5f5f5"
                            }
                        }
                    }
                ],
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
                "zIndex": 469,
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
                        "name": "fk_o_m_target_o_m_source",
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
                "zIndex": 470,
                "labels": [
                    {
                        "markup": [
                            {
                                "tagName": "rect",
                                "selector": "body"
                            },
                            {
                                "tagName": "text",
                                "selector": "ASSOCIATION_LABEL_TEXT_SELECTOR"
                            }
                        ],
                        "attrs": {
                            "ASSOCIATION_LABEL_TEXT_SELECTOR": {
                                "text": "ONE_TO_MANY",
                                "fill": "var(--common-color)",
                                "fontWeight": "normal"
                            },
                            "body": {
                                "ref": "ASSOCIATION_LABEL_TEXT_SELECTOR",
                                "fill": "#f5f5f5"
                            }
                        }
                    }
                ],
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
                "zIndex": 473,
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
                        "name": "fk_o_o_source_o_o_target",
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
                "zIndex": 474,
                "labels": [
                    {
                        "markup": [
                            {
                                "tagName": "rect",
                                "selector": "body"
                            },
                            {
                                "tagName": "text",
                                "selector": "ASSOCIATION_LABEL_TEXT_SELECTOR"
                            }
                        ],
                        "attrs": {
                            "ASSOCIATION_LABEL_TEXT_SELECTOR": {
                                "text": "ONE_TO_ONE",
                                "fill": "var(--common-color)",
                                "fontWeight": "normal"
                            },
                            "body": {
                                "ref": "ASSOCIATION_LABEL_TEXT_SELECTOR",
                                "fill": "#f5f5f5"
                            }
                        }
                    }
                ],
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
                "position": {
                    "x": 2354,
                    "y": 920
                },
                "size": {
                    "width": 258,
                    "height": 154
                },
                "view": "vue-shape-view",
                "shape": "TABLE_NODE",
                "id": "fe62345e-4ab5-422a-8305-6fee37ff534f",
                "data": {
                    "table": {
                        "name": "enum_table",
                        "comment": "",
                        "remark": "",
                        "type": "TABLE",
                        "columns": [
                            {
                                "orderKey": 1,
                                "name": "id",
                                "comment": "ID",
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
                                "name": "common_enum",
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
                                "businessKey": false,
                                "enum": {
                                    "name": "EnumCommon"
                                }
                            },
                            {
                                "orderKey": 3,
                                "name": "ordinal_enum",
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
                                "businessKey": false,
                                "enum": {
                                    "name": "EnumOrdinal"
                                }
                            },
                            {
                                "orderKey": 4,
                                "name": "name_enum",
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
                                "businessKey": false,
                                "enum": {
                                    "name": "EnumName"
                                }
                            }
                        ],
                        "indexes": []
                    }
                },
                "zIndex": 475,
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
                            "id": "e24c0b06-d75c-4359-902d-b807a0dc017c",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 258
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "id": "93a3d2cd-b45c-4ece-bfd5-58771424ff10",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 258
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "id": "de9658cc-94cd-414f-a6bf-d496c5a4cb0b",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 258
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "id": "9ecfe2eb-2a85-4e90-a4db-91c9f67a7192",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 258
                                }
                            }
                        }
                    ]
                }
            },
            {
                "position": {
                    "x": 1912,
                    "y": 920
                },
                "size": {
                    "width": 242,
                    "height": 94
                },
                "view": "vue-shape-view",
                "shape": "TABLE_NODE",
                "id": "580c165d-a0b8-4feb-b935-1b35e72f4c5d",
                "data": {
                    "table": {
                        "name": "comment_table",
                        "comment": "注释测试",
                        "remark": "",
                        "type": "TABLE",
                        "columns": [
                            {
                                "orderKey": 1,
                                "name": "id",
                                "comment": "ID",
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
                                "name": "name",
                                "comment": "名称",
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
                "zIndex": 476,
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
                            "id": "f5065b44-ec65-421e-83de-b4c4b1ee30c2",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 242
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "id": "473dd205-d89c-48a9-8e9d-68790ede0a20",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 242
                                }
                            }
                        }
                    ]
                }
            }
        ]
    },
    "zoom": 0.65,
    "transform": "matrix(0.65,0,0,0.65,-813.6412664442696,-345.7481366408449)"
}
"""

val baseModel = """
{
    "remark": "",
    "name": "test",
    "graphData": "${graphData.escape()}",
    "syncConvertEntity": true,
    "language": "KOTLIN",
    "dataSourceType": "MySQL",
    "author": "",
    "packagePath": "top.potmot",
    "tablePath": "",
    "lowerCaseName": false,
    "realFk": true,
    "idViewProperty": true,
    "logicalDeletedAnnotation": "@LogicalDeleted(\"true\")",
    "tableAnnotation": true,
    "columnAnnotation": true,
    "joinTableAnnotation": true,
    "joinColumnAnnotation": true,
    "tableNamePrefixes": "t_,gen_",
    "tableNameSuffixes": "",
    "tableCommentPrefixes": "",
    "tableCommentSuffixes": "表",
    "columnNamePrefixes": "",
    "columnNameSuffixes": "",
    "columnCommentPrefixes": "",
    "columnCommentSuffixes": "",
    "enums": [
        {
            "remark": "",
            "packagePath": "top.potmot",
            "name": "EnumCommon",
            "comment": "",
            "enumType": null,
            "modelId": 2,
            "items": [
                {
                    "remark": "",
                    "name": "item1",
                    "mappedValue": "",
                    "comment": "",
                    "orderKey": 0
                },
                {
                    "remark": "",
                    "name": "item2",
                    "mappedValue": "",
                    "comment": "",
                    "orderKey": 1
                }
            ]
        },
        {
            "remark": "",
            "packagePath": "top.potmot",
            "name": "EnumOrdinal",
            "comment": "",
            "enumType": "ORDINAL",
            "modelId": 2,
            "items": [
                {
                    "remark": "",
                    "name": "item1",
                    "mappedValue": "0",
                    "comment": "",
                    "orderKey": 0
                },
                {
                    "remark": "",
                    "name": "item2",
                    "mappedValue": "1",
                    "comment": "",
                    "orderKey": 1
                }
            ]
        },
        {
            "remark": "",
            "packagePath": "top.potmot",
            "name": "EnumName",
            "comment": "",
            "enumType": "NAME",
            "modelId": 2,
            "items": [
                {
                    "remark": "",
                    "name": "item1",
                    "mappedValue": "item1",
                    "comment": "",
                    "orderKey": 0
                },
                {
                    "remark": "",
                    "name": "item2",
                    "mappedValue": "item2",
                    "comment": "",
                    "orderKey": 1
                }
            ]
        }
    ]
}
"""

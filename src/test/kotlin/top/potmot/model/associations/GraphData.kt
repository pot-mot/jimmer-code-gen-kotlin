package top.potmot.model.associations

const val MANY_TO_ONE = """
{
    "json": {
        "cells": [
            {
                "position": {
                    "x": 980.4228515625,
                    "y": 74.73188781738281
                },
                "size": {
                    "width": 162,
                    "height": 94
                },
                "view": "vue-shape-view",
                "shape": "TABLE_NODE",
                "id": "6746a457-8691-4b42-92bc-e8664ee9eaf5",
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
                            "id": "726ef03a-a705-4721-9add-0eae97a15e0f",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 162
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "id": "922cdc79-9390-427d-b9a6-8f4c21d039d8",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 162
                                }
                            }
                        }
                    ]
                },
                "zIndex": 10
            },
            {
                "position": {
                    "x": 652.4228515625,
                    "y": 74.73188781738281
                },
                "size": {
                    "width": 128,
                    "height": 64
                },
                "view": "vue-shape-view",
                "shape": "TABLE_NODE",
                "id": "538e3f0c-97ed-4e61-b1d5-c8d62f66d760",
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
                            "id": "3c2129d5-c517-4ebc-a89d-5e12bc2a6d07",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 128
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
                "id": "c8550878-a614-4ee2-bdcd-c987e649f47a",
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
                "source": {
                    "cell": "6746a457-8691-4b42-92bc-e8664ee9eaf5",
                    "port": "922cdc79-9390-427d-b9a6-8f4c21d039d8"
                },
                "target": {
                    "cell": "538e3f0c-97ed-4e61-b1d5-c8d62f66d760",
                    "port": "3c2129d5-c517-4ebc-a89d-5e12bc2a6d07"
                },
                "zIndex": 15
            }
        ]
    },
    "zoom": 2.1122448144521115,
    "transform": "matrix(2.1122448144521115,0,0,2.1122448144521115,-1278.0767645837177,175.372451204267)"
}
"""

const val ONE_TO_MANY = """
{
    "json": {
        "cells": [
            {
                "position": {
                    "x": 791.7777099609375,
                    "y": 16.867149353027344
                },
                "size": {
                    "width": 162,
                    "height": 94
                },
                "view": "vue-shape-view",
                "shape": "TABLE_NODE",
                "id": "e6483225-1acd-4fac-8bb2-d3beed2df70e",
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
                            "id": "5001f77b-2b3c-4eb2-8964-80a376bad2cd",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 162
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "id": "7930aad2-b8b2-4c8f-bca3-257fa6689e20",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 162
                                }
                            }
                        }
                    ]
                },
                "zIndex": 6
            },
            {
                "position": {
                    "x": 1153.7777099609375,
                    "y": 16.867149353027344
                },
                "size": {
                    "width": 135,
                    "height": 64
                },
                "view": "vue-shape-view",
                "shape": "TABLE_NODE",
                "id": "bd9ae1d0-edc7-453c-8d7f-319081f85d60",
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
                            "id": "cdb42cc5-e411-4bc6-a71b-8f30f71af146",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 135
                                }
                            }
                        }
                    ]
                },
                "zIndex": 10
            },
            {
                "shape": "ASSOCIATION_EDGE",
                "router": {
                    "name": "er",
                    "args": {
                        "direction": "H"
                    }
                },
                "id": "9333a02d-4f9f-48b5-83e5-213c8b27b160",
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
                "source": {
                    "cell": "bd9ae1d0-edc7-453c-8d7f-319081f85d60",
                    "port": "cdb42cc5-e411-4bc6-a71b-8f30f71af146"
                },
                "target": {
                    "cell": "e6483225-1acd-4fac-8bb2-d3beed2df70e",
                    "port": "7930aad2-b8b2-4c8f-bca3-257fa6689e20"
                },
                "zIndex": 11
            }
        ]
    },
    "zoom": 2.082494844246044,
    "transform": "matrix(2.082494844246044,0,0,2.082494844246044,-1548.8729675777336,299.49699075562853)"
}
"""

const val ONE_TO_ONE = """
{
    "json": {
        "cells": [
            {
                "position": {
                    "x": 873.579345703125,
                    "y": -19.92657470703125
                },
                "size": {
                    "width": 124,
                    "height": 64
                },
                "view": "vue-shape-view",
                "shape": "TABLE_NODE",
                "id": "7b8c0931-ad45-4e0c-9bad-057094a442b3",
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
                            "id": "27b1d762-ca90-4d6e-b736-75b30646bcc9",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 124
                                }
                            }
                        }
                    ]
                },
                "zIndex": 6
            },
            {
                "position": {
                    "x": 1197.579345703125,
                    "y": -19.92657470703125
                },
                "size": {
                    "width": 156,
                    "height": 94
                },
                "view": "vue-shape-view",
                "shape": "TABLE_NODE",
                "id": "0f536d5e-f171-4670-9540-b0e1b429ae91",
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
                            "id": "839f1f88-577d-4657-a113-16d8bc7e52de",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 156
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "id": "4db367e7-4777-44a0-a5bf-9dfd89d9a64b",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 156
                                }
                            }
                        }
                    ]
                },
                "zIndex": 10
            },
            {
                "shape": "ASSOCIATION_EDGE",
                "router": {
                    "name": "er",
                    "args": {
                        "direction": "H"
                    }
                },
                "id": "01c974b6-673f-4190-83f0-4a6969a412c8",
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
                "source": {
                    "cell": "0f536d5e-f171-4670-9540-b0e1b429ae91",
                    "port": "4db367e7-4777-44a0-a5bf-9dfd89d9a64b"
                },
                "target": {
                    "cell": "7b8c0931-ad45-4e0c-9bad-057094a442b3",
                    "port": "27b1d762-ca90-4d6e-b736-75b30646bcc9"
                },
                "zIndex": 11
            }
        ]
    },
    "zoom": 2.1562498490519064,
    "transform": "matrix(2.1562498490519064,0,0,2.1562498490519064,-1783.655296079684,374.1229307987181)"
}
"""

const val MANY_TO_MANY = """
{
    "json": {
        "cells": [
            {
                "position": {
                    "x": 856.3515625,
                    "y": 187
                },
                "size": {
                    "width": 135,
                    "height": 64
                },
                "view": "vue-shape-view",
                "shape": "TABLE_NODE",
                "id": "33f2592f-82ee-4e98-836f-9eb8309b5127",
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
                            "id": "7fecd199-2597-4b49-b159-05a9e5e00c52",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 135
                                }
                            }
                        }
                    ]
                },
                "zIndex": 6
            },
            {
                "position": {
                    "x": 528.3515625,
                    "y": 187
                },
                "size": {
                    "width": 128,
                    "height": 64
                },
                "view": "vue-shape-view",
                "shape": "TABLE_NODE",
                "id": "2544d2ba-a935-4b72-95dd-37f607c9e310",
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
                            "id": "0a9b37e7-d0c3-471a-a61f-d1f3d8908661",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 128
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
                "id": "cce7a866-faca-4a95-9136-5cab8ecfe32e",
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
                "source": {
                    "cell": "33f2592f-82ee-4e98-836f-9eb8309b5127",
                    "port": "7fecd199-2597-4b49-b159-05a9e5e00c52"
                },
                "target": {
                    "cell": "2544d2ba-a935-4b72-95dd-37f607c9e310",
                    "port": "0a9b37e7-d0c3-471a-a61f-d1f3d8908661"
                },
                "zIndex": 15
            }
        ]
    },
    "zoom": 2.2354211663066956,
    "transform": "matrix(2.2354211663066956,0,0,2.2354211663066956,-1081.088266063715,-57.057235421166354)"
}
"""

const val TREE_NODE = """
{
    "json": {
        "cells": [
            {
                "position": {
                    "x": 699.8411865234375,
                    "y": 161.06907653808594
                },
                "size": {
                    "width": 159,
                    "height": 94
                },
                "view": "vue-shape-view",
                "shape": "TABLE_NODE",
                "id": "5c94ffef-332e-47a8-ba1e-7d3a577e9713",
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
                            "id": "c63127e6-082b-486b-b4ef-be72e6c2669a",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 159
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "id": "2dbf52dc-b7e9-4ce0-bd02-15adc00dbb03",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 159
                                }
                            }
                        }
                    ]
                },
                "zIndex": 9,
                "children": [
                    "9be7b724-e966-4675-a05b-842de652c1d3"
                ]
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
                "id": "9be7b724-e966-4675-a05b-842de652c1d3",
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
                "source": {
                    "cell": "5c94ffef-332e-47a8-ba1e-7d3a577e9713",
                    "port": "2dbf52dc-b7e9-4ce0-bd02-15adc00dbb03"
                },
                "target": {
                    "cell": "5c94ffef-332e-47a8-ba1e-7d3a577e9713",
                    "port": "c63127e6-082b-486b-b4ef-be72e6c2669a"
                },
                "zIndex": 10,
                "parent": "5c94ffef-332e-47a8-ba1e-7d3a577e9713"
            }
        ]
    },
    "zoom": 2.2354211663066956,
    "transform": "matrix(2.2354211663066956,0,0,2.2354211663066956,-1081.088266063715,-57.057235421166354)"
}
"""

const val ASSOCIATIONS = """
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

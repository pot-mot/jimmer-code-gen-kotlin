package top.potmot.model.associations.real

const val MANY_TO_ONE = """
{
    "json": {
        "cells": [
            {
                "position": {
                    "x": 964.4228515625,
                    "y": 74.73188781738281
                },
                "size": {
                    "width": 152,
                    "height": 94
                },
                "view": "vue-shape-view",
                "shape": "TABLE_NODE",
                "id": "6746a457-8691-4b42-92bc-e8664ee9eaf5",
                "data": {
                    "table": {
                        "name": "order_detail",
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
                                "name": "order_id",
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
                "zIndex": 58,
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
                            "id": "8e4b3d17-cca9-4870-9e75-07e3c3048dfc",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 152
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "id": "00973152-189b-46e6-903c-2bfce9a73adc",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 152
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
                        "name": "order",
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
                "zIndex": 66,
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
                            "id": "ba6e98a2-002c-4092-a648-6c0aa7718ad9",
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
                "id": "07d47d51-8b6b-48e7-9ed3-063a610046e7",
                "data": {
                    "association": {
                        "type": "MANY_TO_ONE",
                        "fake": false,
                        "name": "fk_detail_order",
                        "sourceTableName": "order_detail",
                        "targetTableName": "order",
                        "columnReferences": [
                            {
                                "sourceColumnName": "order_id",
                                "targetColumnName": "id"
                            }
                        ],
                        "updateAction": "",
                        "deleteAction": ""
                    }
                },
                "source": {
                    "cell": "6746a457-8691-4b42-92bc-e8664ee9eaf5",
                    "port": "00973152-189b-46e6-903c-2bfce9a73adc"
                },
                "target": {
                    "cell": "538e3f0c-97ed-4e61-b1d5-c8d62f66d760",
                    "port": "ba6e98a2-002c-4092-a648-6c0aa7718ad9"
                },
                "zIndex": 67
            }
        ]
    },
    "zoom": 2.230603448275862,
    "transform": "matrix(2.230603448275862,0,0,2.230603448275862,-1355.2966624292835,160.9644312694155)"
}
"""

const val ONE_TO_MANY = """
{
    "json": {
        "cells": [
            {
                "position": {
                    "x": 1004.4228515625,
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
                        "name": "order",
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
                "zIndex": 66,
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
                            "id": "ba6e98a2-002c-4092-a648-6c0aa7718ad9",
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
                "position": {
                    "x": 652.4228515625,
                    "y": 74.73188781738281
                },
                "size": {
                    "width": 152,
                    "height": 94
                },
                "view": "vue-shape-view",
                "shape": "TABLE_NODE",
                "id": "6746a457-8691-4b42-92bc-e8664ee9eaf5",
                "data": {
                    "table": {
                        "name": "order_detail",
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
                                "name": "order_id",
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
                "zIndex": 68,
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
                            "id": "8e4b3d17-cca9-4870-9e75-07e3c3048dfc",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 152
                                }
                            }
                        },
                        {
                            "group": "COLUMN_PORT_GROUP",
                            "id": "00973152-189b-46e6-903c-2bfce9a73adc",
                            "attrs": {
                                "COLUMN_PORT_SELECTOR": {
                                    "width": 152
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
                "id": "c26252f7-8def-4646-8fd6-35de485ee79c",
                "source": {
                    "cell": "538e3f0c-97ed-4e61-b1d5-c8d62f66d760",
                    "port": "ba6e98a2-002c-4092-a648-6c0aa7718ad9"
                },
                "zIndex": 69,
                "data": {
                    "association": {
                        "type": "ONE_TO_MANY",
                        "fake": false,
                        "name": "fk_order_detail",
                        "sourceTableName": "order",
                        "targetTableName": "order_detail",
                        "columnReferences": [
                            {
                                "sourceColumnName": "id",
                                "targetColumnName": "order_id"
                            }
                        ],
                        "updateAction": "",
                        "deleteAction": ""
                    }
                },
                "target": {
                    "cell": "6746a457-8691-4b42-92bc-e8664ee9eaf5",
                    "port": "00973152-189b-46e6-903c-2bfce9a73adc"
                }
            }
        ]
    },
    "zoom": 2.230603448275862,
    "transform": "matrix(2.230603448275862,0,0,2.230603448275862,-1355.2966624292835,160.9644312694155)"
}
"""

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
                        "sourceTableName": "user_detail",
                        "targetTableName": "user",
                        "columnReferences": [
                            {
                                "sourceColumnName": "user_id",
                                "targetColumnName": "id"
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

const val MANY_TO_MANY = """
{
    "json": {
        "cells": [
            {
                "position": {
                    "x": 528.3515625,
                    "y": 187
                },
                "size": {
                    "width": 112,
                    "height": 64
                },
                "view": "vue-shape-view",
                "shape": "TABLE_NODE",
                "id": "33f2592f-82ee-4e98-836f-9eb8309b5127",
                "data": {
                    "table": {
                        "name": "student",
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
                "zIndex": 39,
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
                            "id": "85a60ddb-5c68-447f-8f3a-04db73260acf",
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
                "position": {
                    "x": 840.3515625,
                    "y": 187
                },
                "size": {
                    "width": 112,
                    "height": 64
                },
                "view": "vue-shape-view",
                "shape": "TABLE_NODE",
                "id": "2544d2ba-a935-4b72-95dd-37f607c9e310",
                "data": {
                    "table": {
                        "name": "course",
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
                "zIndex": 45,
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
                            "id": "72907f5a-1f47-4b77-9d99-5164dc8923e3",
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
                "id": "ca71e028-4456-4507-9eae-4c0ef0b2f752",
                "data": {
                    "association": {
                        "type": "MANY_TO_MANY",
                        "fake": false,
                        "name": "course_student_mapping",
                        "sourceTableName": "course",
                        "targetTableName": "student",
                        "columnReferences": [
                            {
                                "sourceColumnName": "id",
                                "targetColumnName": "id"
                            }
                        ],
                        "updateAction": "",
                        "deleteAction": ""
                    }
                },
                "source": {
                    "cell": "2544d2ba-a935-4b72-95dd-37f607c9e310",
                    "port": "72907f5a-1f47-4b77-9d99-5164dc8923e3"
                },
                "target": {
                    "cell": "33f2592f-82ee-4e98-836f-9eb8309b5127",
                    "port": "85a60ddb-5c68-447f-8f3a-04db73260acf"
                },
                "zIndex": 46
            }
        ]
    },
    "zoom": 2.4410377358490565,
    "transform": "matrix(2.4410377358490565,0,0,2.4410377358490565,-1189.7261018573113,-102.08726415094338)"
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
                        "sourceTableName": "tree_node",
                        "targetTableName": "tree_node",
                        "columnReferences": [
                            {
                                "sourceColumnName": "parent_id",
                                "targetColumnName": "id"
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

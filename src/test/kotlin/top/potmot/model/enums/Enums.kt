package top.potmot.model.enums

const val COMMON_ENUM = """
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
}
"""

const val ORDINAL_ENUM = """
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
}
"""

const val NAME_ENUM = """
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
"""

val ENUMS = listOf(
    COMMON_ENUM,
    ORDINAL_ENUM,
    NAME_ENUM
)

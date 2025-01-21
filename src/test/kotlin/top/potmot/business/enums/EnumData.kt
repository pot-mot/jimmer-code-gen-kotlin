package top.potmot.business.enums

import com.fasterxml.jackson.module.kotlin.readValue
import top.potmot.entity.GenEnum
import top.potmot.entity.dto.GenEnumGenerateView
import top.potmot.utils.json.commonObjectMapper

private const val enumJson = """
{
    "id": 1,
    "packagePath": "com.example.enum",
    "name": "EnumTest",
    "comment": "测试",
    "enumType": null,
    "remark": "",
    "items": [
        {
            "id": 1,
            "name": "Item1",
            "mappedValue": "",
            "comment": "选项1",
            "remark": "",
            "defaultItem": true,
            "orderKey": 1
        },
        {
            "id": 2,
            "name": "Item2",
            "mappedValue": "",
            "comment": "选项2",
            "remark": "",
            "defaultItem": false,
            "orderKey": 2
        },
        {
            "id": 3,
            "name": "Item3",
            "mappedValue": "",
            "comment": "选项3",
            "remark": "",
            "defaultItem": false,
            "orderKey": 3
        }
    ]
}
"""

val enumData = GenEnumGenerateView(
    commonObjectMapper.readValue<GenEnum>(enumJson)
)
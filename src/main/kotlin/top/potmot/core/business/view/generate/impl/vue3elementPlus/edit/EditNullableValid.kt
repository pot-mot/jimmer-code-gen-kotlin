package top.potmot.core.business.view.generate.impl.vue3elementPlus.edit

import top.potmot.core.business.meta.AssociationProperty
import top.potmot.core.business.meta.PropertyBusiness
import top.potmot.core.business.meta.SubEntityBusiness
import top.potmot.utils.string.StringIndentScopeBuilder

interface EditNullableValid {
    private fun Iterable<PropertyBusiness>.editNullableValid(
        builder: StringIndentScopeBuilder,
        data: String,
        messageList: String,
        commentPath: String = "",
        propertyProducer: (entity: SubEntityBusiness) -> Iterable<PropertyBusiness>,
    ) = forEach { it.editNullableValid(builder, data, messageList, commentPath, propertyProducer) }

    private fun PropertyBusiness.editNullableValid(
        builder: StringIndentScopeBuilder,
        data: String,
        messageList: String,
        commentPath: String = "",
        propertyProducer: (entity: SubEntityBusiness) -> Iterable<PropertyBusiness>,
    ) {
        val needValidateNotUndefined = editNullable && typeNotNull
        val currentComment = commentPath + comment

        if (needValidateNotUndefined)
            builder.apply {
                scope("if ($data.$name === undefined) {", "}") {
                    line("$messageList.push(\"${currentComment}不可为空\")")
                }
            }

        if (this is AssociationProperty && isLongAssociation)
            builder.apply {
                if (listType) {
                    if (!needValidateNotUndefined) {
                        scope("for (const item of $data.$name) {", "}") {
                            propertyProducer(typeEntityBusiness).forEach {
                                it.editNullableValid(builder, "item", messageList, currentComment, propertyProducer)
                            }
                        }
                    } else {
                        scope("if ($data.$name === undefined) {", "}") {
                            line("$messageList.push(\"${currentComment}不可为空\")")
                        }
                        scope(" else {", "}") {
                            line("for (const item of $data.$name) {")
                            propertyProducer(typeEntityBusiness).forEach {
                                it.editNullableValid(builder, "item", messageList, currentComment, propertyProducer)
                            }
                            line("}")
                        }
                    }
                } else {
                    if (!needValidateNotUndefined) {
                        line("const $name = $data.$name")
                        propertyProducer(typeEntityBusiness).forEach {
                            it.editNullableValid(builder, name, messageList, currentComment, propertyProducer)
                        }
                    } else {
                        scope("if ($data.$name === undefined) {", "}") {
                            line("$messageList.push(\"${currentComment}不可为空\")")
                        }
                        scope(" else {", "}") {
                            line("const $name = $data.$name")
                            propertyProducer(typeEntityBusiness).forEach {
                                it.editNullableValid(builder, name, messageList, currentComment, propertyProducer)
                            }
                        }
                    }
                }
            }
    }

    fun Iterable<PropertyBusiness>.editNullableValid(
        builder: StringIndentScopeBuilder,
        dataType: String,
        submitType: String,
        listType: Boolean = false,
        validateDataForSubmit: String = "validate${dataType}ForSubmit",
        assertDataTypeAsSubmitType: String = "assert${dataType}AsSubmitType",
    ) {
        builder.apply {
            val inputType = if (listType) "Array<$dataType>" else dataType
            val outputType = if (listType) "Array<$submitType>" else submitType

            val getUnmatchedMessageList = "getUnmatchedMessageList"

            scope("const $getUnmatchedMessageList = (data: $inputType): Array<string> => {", "}") {
                line("const messageList: Array<string> = []")
                line()
                if (listType) {
                    scope("for (const item of data) {", "}") {
                        editNullableValid(this, "item", "messageList", "") {
                            it.subEditNoIdProperties
                        }
                    }
                } else {
                    editNullableValid(this, "data", "messageList", "") {
                        it.subEditNoIdProperties
                    }
                }
                line()
                line("return messageList")
            }
            line()

            scope("export const $validateDataForSubmit = (data: $inputType): boolean => {", "}") {
                line("const messageList = $getUnmatchedMessageList(data)")
                line("return messageList.length === 0")
            }
            line()

            scope("export const $assertDataTypeAsSubmitType = (data: $inputType): $outputType => {", "}") {
                line("const messageList = $getUnmatchedMessageList(data)")
                scope("if (messageList.length === 0) {", "}") {
                    line("return data as $outputType")
                }
                scope(" else {", "}") {
                    line("messageList.forEach(message => sendMessage(message, \"error\"))")
                    line("throw messageList")
                }
            }
        }
    }
}
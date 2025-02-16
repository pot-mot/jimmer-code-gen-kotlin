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
                line("if ($data.$name === undefined) {")
                scope {
                    line("$messageList.push(\"${currentComment}不可为空\")")
                }
                line("}")
            }

        if (this is AssociationProperty && isLongAssociation)
            builder.apply {
                if (listType) {
                    if (!needValidateNotUndefined) {
                        line("for (const item of $data.$name) {")
                        scope {
                            propertyProducer(typeEntityBusiness).forEach {
                                it.editNullableValid(builder, "item", messageList, currentComment, propertyProducer)
                            }
                        }
                        line("}")
                    } else {
                        line("if ($data.$name === undefined) {")
                        scope {
                            line("$messageList.push(\"${currentComment}不可为空\")")
                        }
                        line("} else {")
                        scope {
                            line("for (const item of $data.$name) {")
                            propertyProducer(typeEntityBusiness).forEach {
                                it.editNullableValid(builder, "item", messageList, currentComment, propertyProducer)
                            }
                            line("}")
                        }
                        line("}")
                    }
                } else {
                    if (!needValidateNotUndefined) {
                        line("const $name = $data.$name")
                        propertyProducer(typeEntityBusiness).forEach {
                            it.editNullableValid(builder, name, messageList, currentComment, propertyProducer)
                        }
                    } else {
                        line("if ($data.$name === undefined) {")
                        scope {
                            line("$messageList.push(\"${currentComment}不可为空\")")
                        }
                        line("} else {")
                        scope {
                            line("const $name = $data.$name")
                            propertyProducer(typeEntityBusiness).forEach {
                                it.editNullableValid(builder, name, messageList, currentComment, propertyProducer)
                            }
                        }
                        line("}")
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

            line("const $getUnmatchedMessageList = (data: $inputType): Array<string> => {")
            scope {
                line("const messageList: Array<string> = []")
                line()
                if (listType) {
                    line("for (const item of data) {")
                    scope {
                        editNullableValid(this, "item", "messageList", "") {
                            it.subEditNoIdProperties
                        }
                    }
                    line("}")
                } else {
                    editNullableValid(this, "data", "messageList", "") {
                        it.subEditNoIdProperties
                    }
                }
                line()
                line("return messageList")
            }
            line("}")
            line()

            line("export const $validateDataForSubmit = (data: $inputType): boolean => {")
            scope {
                line("const messageList = $getUnmatchedMessageList(data)")
                line("return messageList.length === 0")
            }
            line("}")
            line()

            line("export const $assertDataTypeAsSubmitType = (data: $inputType): $outputType => {")
            scope {
                line("const messageList = $getUnmatchedMessageList(data)")
                line("if (messageList.length === 0) {")
                scope {
                    line("return data as $outputType")
                }
                line("} else {")
                scope {
                    line("messageList.forEach(message => sendMessage(message, \"error\"))")
                    line("throw messageList")
                }
                line("}")
            }
            line("}")
        }
    }
}
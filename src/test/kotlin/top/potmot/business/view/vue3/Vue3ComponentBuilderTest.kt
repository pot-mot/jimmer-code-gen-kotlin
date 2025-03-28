package top.potmot.business.view.vue3

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import top.potmot.core.common.typescript.CodeBlock
import top.potmot.core.common.typescript.Import
import top.potmot.core.common.typescript.ConstVariable
import top.potmot.core.common.typescript.ImportDefault
import top.potmot.core.common.vue3.TagElement
import top.potmot.core.common.typescript.LetVariable
import top.potmot.core.common.style.StyleClass
import top.potmot.core.common.typescript.Function
import top.potmot.core.common.typescript.FunctionArg
import top.potmot.core.common.typescript.ImportType
import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator
import top.potmot.core.common.typescript.stringify
import top.potmot.core.common.vue3.Component
import top.potmot.core.common.vue3.Event
import top.potmot.core.common.vue3.EventArg
import top.potmot.core.common.vue3.EventBind
import top.potmot.core.common.vue3.VModel
import top.potmot.core.common.vue3.ModelProp
import top.potmot.core.common.vue3.Prop
import top.potmot.core.common.vue3.Slot
import top.potmot.core.common.vue3.SlotProp
import top.potmot.core.common.vue3.TextElement
import top.potmot.core.common.vue3.classProp
import top.potmot.core.common.vue3.styleProp
import top.potmot.error.GenerateException
import top.potmot.utils.string.trimBlankLine

class Vue3ComponentBuilderTest {
    val builder = Vue3ElementPlusViewGenerator.componentBuilder

    @Test
    fun `test stringify with common and type only imports`() {
        val importItems = listOf(
            Import("path1", listOf("item1", "item2")),
            ImportType("path1", listOf("ItemType1", "ItemType2")),
            ImportDefault("path2", "defaultItem")
        )

        val expected = listOf(
            "import {item1, item2} from \"path1\"",
            "import type {ItemType1, ItemType2} from \"path1\"",
            "import defaultItem from \"path2\""
        )

        assertEquals(expected, importItems.stringify(builder.indent, builder.wrapThreshold))
    }

    @Test
    fun `test stringify with multiple default imports for the same path should throw exception`() {
        val importItems = listOf(
            ImportDefault("path1", "defaultItem1"),
            ImportDefault("path1", "defaultItem2")
        )

        assertThrows<GenerateException> {
            importItems.stringify(builder.indent, builder.wrapThreshold)
        }
    }

    @Test
    fun `test stringifyModels`() {
        val models = listOf(
            ModelProp("model1", "string", true),
            ModelProp("model2", "number", false)
        )

        val expected = """
        const model1 = defineModel<string>(
            "model1",
            {
                required: true
            }
        )

        const model2 = defineModel<number>(
            "model2",
            {
                required: false
            }
        )
        """.trimIndent()

        builder.apply {
            assertEquals(expected, models.stringifyModels().joinToString("\n\n"))
        }
    }

    @Test
    fun `test stringifyProps`() {
        val props = listOf(
            Prop("prop1", "string"),
            Prop("prop2", "number", false, "0"),
            Prop("prop3", "boolean", false, "false")
        )

        val expected = """
withDefaults(defineProps<{
    prop1: string,
    prop2?: number | undefined,
    prop3?: boolean | undefined
}>(), {
    prop2: 0,
    prop3: false,
})
           """.trimBlankLine()

        builder.apply {
            assertEquals(expected, props.stringifyProps())
        }
    }

    @Test
    fun `test stringifyEmits`() {
        val emits = listOf(
            Event("event1", listOf(EventArg("arg1", "string"))),
            Event("event2", listOf(EventArg("arg2", "number")))
        )

        val expected = """
defineEmits<{
    (event: "event1", arg1: string): void,
    (event: "event2", arg2: number): void
}>()
           """.trimBlankLine()

        builder.apply {
            assertEquals(expected, emits.stringifyEmits())
        }
    }

    @Test
    fun `test stringifySlots`() {
        val slots = listOf(
            Slot("slot1", listOf(SlotProp("prop1", "string"))),
            Slot("slot2", listOf(SlotProp("prop2", "number")))
        )

        val expected = """
defineSlots<{
    slot1(props: {prop1: string}): any,
    slot2(props: {prop2: number}): any
}>()
           """.trimBlankLine()

        builder.apply {
            assertEquals(expected, slots.stringifySlots())
        }
    }

    @Test
    fun `test stringifyCodes`() {
        val codeBlocks = listOf(
            ConstVariable("const1", "string", "\"value1\""),
            LetVariable("let1", "number", "0"),
            Function(name = "func1", args = listOf(FunctionArg("arg1", "string")), body = listOf(CodeBlock("console.log(arg1)"))),
            CodeBlock("console.log('Hello, World!')")
        )

        val expected = """
const const1: string = "value1"
let let1: number = 0
const func1 = (arg1: string): void => {
    console.log(arg1)
}
console.log('Hello, World!')
           """.trimBlankLine()

        builder.apply {
            assertEquals(expected, codeBlocks.stringify(builder.indent, builder.wrapThreshold))
        }
    }

    @Test
    fun `test stringifyElements`() {
        val elements = listOf(
            TagElement("input", directives = listOf(VModel("model1")), props = listOf(classProp("container"))),
            TagElement("button", events = listOf(EventBind("click", "handleClick"))),
            TagElement("button", events = listOf(EventBind("click", "handleClick")), children = listOf(
                TagElement("span", children = listOf(TextElement("Click Me!")))
            ))
        )

        val expected = """
<input v-model="model1" class="container"/>
<button @click="handleClick"/>
<button @click="handleClick">
    <span>Click Me!</span>
</button>
           """.trimBlankLine()

        builder.apply {
            assertEquals(expected, elements.stringifyElements())
        }
    }

    @Test
    fun `test stringifyStyleClass`() {
        val styleClasses = listOf(
            StyleClass(".container", mapOf("display" to "flex", "justify-content" to "center")),
            StyleClass(".button", mapOf("background-color" to "blue", "color" to "white"))
        )

        val expected = """
.container {
    display: flex;
    justify-content: center;
}

.button {
    background-color: blue;
    color: white;
}
           """.trimBlankLine()

        builder.apply {
            assertEquals(expected, styleClasses.stringifyStyleClass())
        }
    }

    @Test
    fun `test build complete Vue component`() {
        val vueComponentPart = Component(
            imports = listOf(
                Import("path1", listOf("item1", "item2")),
                ImportType("path1", listOf("ItemType1", "ItemType2")),
                ImportDefault("path2", "defaultItem")
            ),
            models = listOf(
                ModelProp("model1", "string", true),
                ModelProp("model2", "number", false)
            ),
            props = listOf(
                Prop("prop1", "string", true, null),
                Prop("prop2", "number", false, "0"),
                Prop("prop3", "boolean", false, "false")
            ),
            emits = listOf(
                Event("event1", listOf(EventArg("arg1", "string"))),
                Event("event2", listOf(EventArg("arg2", "number")))
            ),
            slots = listOf(
                Slot("slot1", listOf(SlotProp("prop1", "string"))),
                Slot("slot2", listOf(SlotProp("prop2", "number")))
            ),
            script = listOf(
                ConstVariable("const1", "string", "\"value1\""),
                LetVariable("let1", "number", "0"),
                Function(async = true, name = "func1", args = listOf(FunctionArg("arg1", "string")), returnType = "boolean", body = listOf(
                    CodeBlock("return false")
                )),
                CodeBlock("console.log('Hello, World!')")
            ),
            template = listOf(
                TagElement("div", props = listOf(classProp("container"), styleProp("color" to "red")), children = listOf(
                    TagElement("input", directives = listOf(VModel("model1")))
                )),
                TagElement("button", events = listOf(EventBind("click", "handleClick")))
            ),
            style = listOf(
                StyleClass(".container", mapOf("display" to "flex", "justify-content" to "center")),
                StyleClass(".button", mapOf("background-color" to "blue", "color" to "white"))
            )
        )

        val expected = """
<script setup lang="ts">
import {item1, item2} from "path1"
import type {ItemType1, ItemType2} from "path1"
import defaultItem from "path2"

const model1 = defineModel<string>(
    "model1",
    {
        required: true
    }
)

const model2 = defineModel<number>(
    "model2",
    {
        required: false
    }
)

withDefaults(defineProps<{
    prop1: string,
    prop2?: number | undefined,
    prop3?: boolean | undefined
}>(), {
    prop2: 0,
    prop3: false,
})

defineEmits<{
    (event: "event1", arg1: string): void,
    (event: "event2", arg2: number): void
}>()

defineSlots<{
    slot1(props: {prop1: string}): any,
    slot2(props: {prop2: number}): any
}>()

const const1: string = "value1"
let let1: number = 0
const func1 = async (arg1: string): Promise<boolean> => {
    return false
}
console.log('Hello, World!')
</script>

<template>
    <div class="container" style="color: red;">
        <input v-model="model1"/>
    </div>
    <button @click="handleClick"/>
</template>

<style scoped>
.container {
    display: flex;
    justify-content: center;
}

.button {
    background-color: blue;
    color: white;
}
</style>
        """.trimBlankLine()

        assertEquals(expected, builder.build(vueComponentPart).trim())
    }
}

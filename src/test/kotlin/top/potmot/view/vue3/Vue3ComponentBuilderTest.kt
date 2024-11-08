package top.potmot.view.vue3

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import top.potmot.core.business.view.generate.meta.typescript.CodeBlock
import top.potmot.core.business.view.generate.meta.typescript.Import
import top.potmot.core.business.view.generate.meta.typescript.ConstVariable
import top.potmot.core.business.view.generate.meta.typescript.ImportDefault
import top.potmot.core.business.view.generate.meta.vue3.Element
import top.potmot.core.business.view.generate.meta.typescript.LetVariable
import top.potmot.core.business.view.generate.meta.style.StyleClass
import top.potmot.core.business.view.generate.meta.typescript.Function
import top.potmot.core.business.view.generate.meta.typescript.FunctionArg
import top.potmot.core.business.view.generate.meta.typescript.ImportType
import top.potmot.core.business.view.generate.builder.vue3.Vue3ComponentBuilder
import top.potmot.core.business.view.generate.meta.vue3.Component
import top.potmot.core.business.view.generate.meta.vue3.Event
import top.potmot.core.business.view.generate.meta.vue3.EventArg
import top.potmot.core.business.view.generate.meta.vue3.EventBind
import top.potmot.core.business.view.generate.meta.vue3.VModel
import top.potmot.core.business.view.generate.meta.vue3.ModelProp
import top.potmot.core.business.view.generate.meta.vue3.Prop
import top.potmot.core.business.view.generate.meta.vue3.PropBind
import top.potmot.core.business.view.generate.meta.vue3.Slot
import top.potmot.core.business.view.generate.meta.vue3.SlotProp
import top.potmot.error.GenerateException
import top.potmot.utils.string.trimBlankLine

class Vue3ComponentBuilderTest {
    private val builder = Vue3ComponentBuilder()

    @Test
    fun `test stringifyImports with common and type only imports`() {
        val importItems = listOf(
            Import("path1", listOf("item1", "item2")),
            ImportType("path1", listOf("ItemType1", "ItemType2")),
            ImportDefault("path2", "defaultItem")
        )

        val expected = listOf(
            "import { item1, item2 } from \"path1\"",
            "import type { ItemType1, ItemType2 } from \"path1\"",
            "import defaultItem from \"path2\""
        )

        builder.apply {
            assertEquals(expected, importItems.stringifyImports())
        }
    }

    @Test
    fun `test stringifyImports with multiple default imports for the same path should throw exception`() {
        val importItems = listOf(
            ImportDefault("path1", "defaultItem1"),
            ImportDefault("path1", "defaultItem2")
        )

        builder.apply {
            assertThrows<GenerateException> {
                importItems.stringifyImports()
            }
        }
    }

    @Test
    fun `test stringifyModels`() {
        val models = listOf(
            ModelProp("model1", "string", true),
            ModelProp("model2", "number", false)
        )

        val expected = listOf(
            "const model1 = defineModels<string>({required: true})",
            "const model2 = defineModels<number>({required: false})"
        ).map { it.trimBlankLine() }

        builder.apply {
            assertEquals(expected, models.stringifyModels())
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
    prop3: false
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
            Function(false, "func1", listOf(FunctionArg("arg1", "string")), null, listOf(CodeBlock("console.log(arg1)"))),
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
            assertEquals(expected, codeBlocks.stringifyCodes())
        }
    }

    @Test
    fun `test stringifyElements`() {
        val elements = listOf(
            Element("div", directives = listOf(VModel("model1")), props = listOf(PropBind("class", "container", isLiteral = true))),
            Element("button", events = listOf(EventBind("click", "handleClick"))),
            Element("button", events = listOf(EventBind("click", "handleClick")), children = listOf(
                Element("span", "Click Me!")
            ))
        )

        val expected = """
<div
    v-model="model1"
    class="container"
/>
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
                Function(true, "func1", listOf(FunctionArg("arg1", "string")), "boolean", listOf(CodeBlock("return false"))),
                CodeBlock("console.log('Hello, World!')")
            ),
            template = listOf(
                Element("div", directives = listOf(VModel("model1")), props = listOf(PropBind("class", "container", isLiteral = true))),
                Element("button", events = listOf(EventBind("click", "handleClick")))
            ),
            style = listOf(
                StyleClass(".container", mapOf("display" to "flex", "justify-content" to "center")),
                StyleClass(".button", mapOf("background-color" to "blue", "color" to "white"))
            )
        )

        val expected = """
<script setup lang="ts">
import { item1, item2 } from "path1"
import type { ItemType1, ItemType2 } from "path1"
import defaultItem from "path2"

const model1 = defineModels<string>({required: true})
const model2 = defineModels<number>({required: false})

const props = withDefaults(defineProps<{
    prop1: string,
    prop2?: number | undefined,
    prop3?: boolean | undefined
}>(), {
    prop2: 0,
    prop3: false
})

const emits = defineEmits<{
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
    <div
        v-model="model1"
        class="container"
    />
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

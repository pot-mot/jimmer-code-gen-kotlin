package top.potmot.view.vue3

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import top.potmot.core.business.view.generate.builder.vue3.CommonBlock
import top.potmot.core.business.view.generate.builder.vue3.CommonImport
import top.potmot.core.business.view.generate.builder.vue3.ConstVariable
import top.potmot.core.business.view.generate.builder.vue3.DefaultImport
import top.potmot.core.business.view.generate.builder.vue3.Vue3TemplateElement
import top.potmot.core.business.view.generate.builder.vue3.LetVariable
import top.potmot.core.business.view.generate.builder.vue3.StyleClass
import top.potmot.core.business.view.generate.builder.vue3.TsFunction
import top.potmot.core.business.view.generate.builder.vue3.TsFunctionArg
import top.potmot.core.business.view.generate.builder.vue3.TypeOnlyImport
import top.potmot.core.business.view.generate.builder.vue3.Vue3ComponentBuilder
import top.potmot.core.business.view.generate.builder.vue3.Vue3ComponentPart
import top.potmot.core.business.view.generate.builder.vue3.Vue3Emit
import top.potmot.core.business.view.generate.builder.vue3.Vue3EmitArg
import top.potmot.core.business.view.generate.builder.vue3.Vue3EventBind
import top.potmot.core.business.view.generate.builder.vue3.VModel
import top.potmot.core.business.view.generate.builder.vue3.Vue3ModelProp
import top.potmot.core.business.view.generate.builder.vue3.Vue3Prop
import top.potmot.core.business.view.generate.builder.vue3.Vue3PropBind
import top.potmot.core.business.view.generate.builder.vue3.Vue3Slot
import top.potmot.core.business.view.generate.builder.vue3.Vue3SlotProp
import top.potmot.error.GenerateException
import top.potmot.utils.string.trimBlankLine

class Vue3ComponentBuilderTest {
    private val builder = Vue3ComponentBuilder()

    @Test
    fun `test stringifyImports with common and type only imports`() {
        val importItems = listOf(
            CommonImport("path1", listOf("item1", "item2")),
            TypeOnlyImport("path1", listOf("ItemType1", "ItemType2")),
            DefaultImport("path2", "defaultItem")
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
            DefaultImport("path1", "defaultItem1"),
            DefaultImport("path1", "defaultItem2")
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
            Vue3ModelProp("model1", "string", true),
            Vue3ModelProp("model2", "number", false)
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
            Vue3Prop("prop1", "string"),
            Vue3Prop("prop2", "number", false, "0"),
            Vue3Prop("prop3", "boolean", false, "false")
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
            Vue3Emit("event1", listOf(Vue3EmitArg("arg1", "string"))),
            Vue3Emit("event2", listOf(Vue3EmitArg("arg2", "number")))
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
            Vue3Slot("slot1", listOf(Vue3SlotProp("prop1", "string"))),
            Vue3Slot("slot2", listOf(Vue3SlotProp("prop2", "number")))
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
            TsFunction("func1", listOf(TsFunctionArg("arg1", "string")), "void", "console.log(arg1);"),
            CommonBlock("console.log('Hello, World!');")
        )

        val expected = """
const const1: string = "value1";
let let1: number = 0;
const func1 = (arg1: string): void => {
    console.log(arg1);
}
console.log('Hello, World!');
           """.trimBlankLine()

        builder.apply {
            assertEquals(expected, codeBlocks.stringifyCodes())
        }
    }

    @Test
    fun `test stringifyElements`() {
        val elements = listOf(
            Vue3TemplateElement("div", directives = listOf(VModel("model1")), props = listOf(Vue3PropBind("class", "container", isLiteral = true))),
            Vue3TemplateElement("button", events = listOf(Vue3EventBind("click", "handleClick"))),
            Vue3TemplateElement("button", events = listOf(Vue3EventBind("click", "handleClick")), children = listOf(
                Vue3TemplateElement("span", "Click Me!")
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
        val vueComponentPart = Vue3ComponentPart(
            importItems = listOf(
                CommonImport("path1", listOf("item1", "item2")),
                TypeOnlyImport("path1", listOf("ItemType1", "ItemType2")),
                DefaultImport("path2", "defaultItem")
            ),
            models = listOf(
                Vue3ModelProp("model1", "string", true),
                Vue3ModelProp("model2", "number", false)
            ),
            props = listOf(
                Vue3Prop("prop1", "string", true, null),
                Vue3Prop("prop2", "number", false, "0"),
                Vue3Prop("prop3", "boolean", false, "false")
            ),
            emits = listOf(
                Vue3Emit("event1", listOf(Vue3EmitArg("arg1", "string"))),
                Vue3Emit("event2", listOf(Vue3EmitArg("arg2", "number")))
            ),
            slots = listOf(
                Vue3Slot("slot1", listOf(Vue3SlotProp("prop1", "string"))),
                Vue3Slot("slot2", listOf(Vue3SlotProp("prop2", "number")))
            ),
            script = listOf(
                ConstVariable("const1", "string", "\"value1\""),
                LetVariable("let1", "number", "0"),
                TsFunction("func1", listOf(TsFunctionArg("arg1", "string")), "void", "console.log(arg1);"),
                CommonBlock("console.log('Hello, World!');")
            ),
            template = listOf(
                Vue3TemplateElement("div", directives = listOf(VModel("model1")), props = listOf(Vue3PropBind("class", "container", isLiteral = true))),
                Vue3TemplateElement("button", events = listOf(Vue3EventBind("click", "handleClick")))
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

const const1: string = "value1";
let let1: number = 0;
const func1 = (arg1: string): void => {
    console.log(arg1);
}
console.log('Hello, World!');
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

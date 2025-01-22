package top.potmot.core.business.view.generate.impl.vue3elementPlus.select

import top.potmot.core.business.meta.EntityBusiness
import top.potmot.core.business.type.typeStrToTypeScriptType
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.treeSelect
import top.potmot.core.business.view.generate.meta.typescript.CodeBlock
import top.potmot.core.business.view.generate.meta.typescript.Import
import top.potmot.core.business.view.generate.meta.typescript.ImportType
import top.potmot.core.business.view.generate.meta.typescript.emptyLineCode
import top.potmot.core.business.view.generate.meta.vue3.Component
import top.potmot.core.business.view.generate.meta.vue3.ModelProp
import top.potmot.core.business.view.generate.meta.vue3.Prop
import top.potmot.core.business.view.generate.staticPath
import top.potmot.error.ModelException

interface IdTreeSelect : IdSelect {
    @Throws(ModelException.TreeEntityCannotFoundParentProperty::class)
    fun createIdTreeSelect(entity: EntityBusiness, multiple: Boolean): Component {
        val idProperty = entity.idProperty
        val idName = idProperty.name
        val idType = typeStrToTypeScriptType(idProperty.type, idProperty.typeNotNull)

        val optionView = entity.dto.optionView

        val modelValue = "modelValue"
        val options = "options"
        val excludeIds = "excludeIds"

        val modelValueType = modelValueType(
            modelValue, multiple, idType
        )

        val parentId = entity.parentIdProperty.name

        val treeOptions = CodeBlock(
            """
            type TreeNode = {
                id: $idType,
                label: string,
                disabled: boolean,
                children: Array<TreeNode>
            }
            
            const treeOptions = computed<Array<TreeNode>>(() => {
                const items = props.$options
                const idNodeMap = new Map<number, TreeNode>
                const roots: Array<TreeNode> = []
            
                for (const item of items) {
                    const node: TreeNode = {
                        id: item.$idName,
                        label: ${createLabelExpression("item", entity.optionLabelProperties, idProperty)},
                        disabled: props.$excludeIds.includes(item.$idName),
                        children: []
                    }
                    idNodeMap.set(item.$idName, node)
                }
            
                for (const item of items) {
                    const node = idNodeMap.get(item.$idName)
                    if (!node) continue
            
                    const parentNode = item.$parentId ? idNodeMap.get(item.$parentId) : undefined
                    if (parentNode) {
                        if (parentNode.disabled) {
                            node.disabled = true
                        }
                        parentNode.children.push(node);
                    } else {
                        roots.push(node);
                    }
                }
                
                return roots
            })
            """.trimIndent()
        )

        val filterNodeMethod = CodeBlock(
            """
            const filterNodeMethod = (value: string | undefined, data: TreeNode) => {
                return !value || data.label.includes(value)
            }
            """.trimIndent()
        )

        val keepModelValueLegal = keepModelValueLegal(
            modelValue, multiple, options, idName, idType
        )

        val treeSelectElement = treeSelect(
            data = "treeOptions",
            labelProp = "label",
            childrenProp = "children",
            nodeKey = "id",
            modelValue = modelValue,
            comment = entity.comment,
            filterable = true,
            filterNodeMethod = "filterNodeMethod",
            clearable = true,
            multiple = multiple,
        )

        return Component(
            imports = listOf(
                Import("vue", "computed", "watch"),
                ImportType("element-plus", "ElTreeSelect"),
                ImportType(staticPath, optionView)
            ),
            models = listOf(
                ModelProp(modelValue, modelValueType)
            ),
            props = listOf(
                Prop(options, "Array<$optionView>"),
                Prop(
                    excludeIds, "Array<$idType>", required = false,
                    defaultValue = "return []", defaultAsFunction = true
                )
            ),
            script = listOf(
                keepModelValueLegal,
                emptyLineCode,
                treeOptions,
                emptyLineCode,
                filterNodeMethod,
            ),
            template = listOf(
                treeSelectElement
            )
        )
    }
}

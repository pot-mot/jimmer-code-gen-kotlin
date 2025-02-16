package top.potmot.core.business.view.generate.impl.vue3elementPlus.idSelect

import top.potmot.core.business.meta.SubEntityBusiness
import top.potmot.core.business.type.typeStrToTypeScriptType
import top.potmot.core.business.view.generate.impl.vue3elementPlus.ElementPlusComponents.Companion.treeSelect
import top.potmot.core.business.view.generate.meta.typescript.CodeBlock
import top.potmot.core.business.view.generate.meta.typescript.Import
import top.potmot.core.business.view.generate.meta.typescript.ImportType
import top.potmot.core.business.view.generate.meta.typescript.emptyLineCode
import top.potmot.core.business.view.generate.meta.vue3.Component
import top.potmot.core.business.view.generate.meta.vue3.EventBind
import top.potmot.core.business.view.generate.meta.vue3.ModelProp
import top.potmot.core.business.view.generate.meta.vue3.Prop
import top.potmot.core.business.view.generate.meta.vue3.PropBind
import top.potmot.core.business.view.generate.staticPath
import top.potmot.core.business.view.generate.utilPath
import top.potmot.error.ModelException

interface IdTreeSelect : IdSelect {
    @Throws(ModelException.TreeEntityCannotFoundParentProperty::class)
    fun createIdTreeSelect(entity: SubEntityBusiness, multiple: Boolean): Component {
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
                if (props.$options.state !== 'loaded') return []
                if (props.$options.data === undefined) return []

                const items = props.$options.data
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

        val loadIfNot = loadIfNot()
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
        ).merge {
            props += PropBind("loading", "options.state === 'loading'")
            events += EventBind("focus.once", "loadIfNot")
        }

        return Component(
            imports = listOf(
                Import("vue", "computed", "onBeforeMount", "watch"),
                ImportType("element-plus", "ElTreeSelect"),
                ImportType("$utilPath/lazyOptions", "LazyOptions"),
                ImportType(staticPath, optionView)
            ),
            models = listOf(
                ModelProp(modelValue, modelValueType)
            ),
            props = listOf(
                Prop(options, "LazyOptions<$optionView>"),
                Prop(
                    excludeIds, "Array<$idType>", required = false,
                    default = "return []", defaultAsFunction = true
                )
            ),
            script = listOf(
                loadIfNot,
                emptyLineCode,
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

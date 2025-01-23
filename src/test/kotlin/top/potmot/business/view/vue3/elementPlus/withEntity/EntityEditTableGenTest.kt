//package top.potmot.business.view.vue3.elementPlus.withEntity
//
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.Test
//import top.potmot.core.business.view.generate.impl.vue3elementPlus.Vue3ElementPlusViewGenerator
//import top.potmot.business.testEntityBusiness
//
//class EntityEditTableGenTest {
//    private val index = "${'$'}index"
//
//    private val generator = Vue3ElementPlusViewGenerator
//
//    @Test
//    fun `test editTableRules`() {
//        assertEquals(
//            """
//import type {Ref} from "vue"
//import type {FormRules} from "element-plus"
//import type {EntityUpdateInput} from "@/api/__generated/model/static"
//
//export const useRules = (_: Ref<Array<EntityUpdateInput>>): FormRules<EntityUpdateInput> => {
//    return {
//        enumProperty: [
//            {required: true, message: "enumProperty不能为空", trigger: "blur"},
//            {type: "enum", enum: ["item1"], message: "enumProperty必须是item1", trigger: "blur"},
//        ],
//        enumNullableProperty: [
//            {type: "enum", enum: ["item1"], message: "enumNullableProperty必须是item1", trigger: "blur"},
//        ],
//        toOnePropertyId: [
//            {required: true, message: "toOneProperty不能为空", trigger: "blur"},
//        ],
//        toOneNullablePropertyId: [
//        ],
//    }
//}
//            """.trimIndent(),
//            generator.editTableFiles(testEntityBusiness).first { it.name == "EntityEditTableRules.ts" }.content.trim()
//        )
//    }
//
//    @Test
//    fun `test editTable`() {
//        assertEquals(
//            """
//<script setup lang="ts">
//import {ref} from "vue"
//import type {FormInstance} from "element-plus"
//import type {FormExpose} from "@/components/form/FormExpose"
//import type {EntityAddFormType, ToOneEntityOptionView} from "@/api/__generated/model/static"
//import {createDefaultEntity} from "@/components/entity/createDefaultEntity"
//import {useRules} from "@/rules/entity/EntityEditTableRules"
//import {usePageSizeStore} from "@/stores/pageSizeStore"
//import {Plus, Delete} from "@element-plus/icons-vue"
//import {deleteConfirm} from "@/utils/confirm"
//import EnumSelect from "@/components/enums/enum/EnumSelect.vue"
//import EnumNullableSelect from "@/components/enums/enum/EnumNullableSelect.vue"
//import ToOneEntityIdSelect from "@/components/toOneEntity/ToOneEntityIdSelect.vue"
//
//const rows = defineModel<Array<EntityAddFormType>>({
//    required: true
//})
//
//const props = withDefaults(defineProps<{
//    idColumn?: boolean | undefined,
//    indexColumn?: boolean | undefined,
//    multiSelect?: boolean | undefined,
//    withOperations?: boolean | undefined,
//    submitLoading?: boolean | undefined,
//    toOnePropertyIdOptions: Array<ToOneEntityOptionView>,
//    toOneNullablePropertyIdOptions: Array<ToOneEntityOptionView>
//}>(), {
//    idColumn: false,
//    indexColumn: false,
//    multiSelect: true,
//    withOperations: false,
//    submitLoading: false,
//})
//
//const emits = defineEmits<{
//    (
//        event: "submit",
//        rows: Array<EntityAddFormType>
//    ): void,
//    (event: "cancel"): void
//}>()
//
//defineSlots<{
//    operations(props: {
//        handleSubmit: () => Promise<void>,
//        handleCancel: () => void
//    }): any
//}>()
//
//const formRef = ref<FormInstance>()
//const rules = useRules(rows)
//
//const pageSizeStore = usePageSizeStore()
//
//// 校验
//const handleValidate = async (): Promise<boolean> => {
//    return await formRef.value?.validate().catch(() => false) ?? false
//}
//
//// 提交
//const handleSubmit = async (): Promise<void> => {
//    if (props.submitLoading) return
//
//    const validResult = await handleValidate()
//    if (validResult) {
//        emits("submit", rows.value)
//    }
//}
//
//// 取消
//const handleCancel = (): void => {
//    emits("cancel")
//}
//
//// 多选
//const selection = ref<Array<EntityAddFormType>>([])
//
//const handleSelectionChange = (newSelection: Array<EntityAddFormType>): void => {
//    selection.value = newSelection
//}
//
//// 新增
//const handleAdd = (): void => {
//    rows.value.push(createDefaultEntity())
//}
//
//// 删除
//const handleBatchDelete = async (): Promise<void> => {
//    const result = await deleteConfirm("这些comment")
//    if (!result) return
//    rows.value = rows.value.filter(it => !selection.value.includes(it))
//}
//
//const handleSingleDelete = async (index: number): Promise<void> => {
//    const result = await deleteConfirm("该comment")
//    if (!result) return
//    rows.value = rows.value.filter((_, i) => i !== index)
//}
//
//defineExpose<FormExpose>({
//    validate: handleValidate
//})
//</script>
//
//<template>
//    <el-form
//        :model="rows"
//        ref="formRef"
//        :rules="rules"
//        @submit.prevent
//    >
//        <div>
//            <el-button
//                type="primary"
//                :icon="Plus"
//                @click="handleAdd"
//            >
//                新增
//            </el-button>
//            <el-button
//                type="danger"
//                :icon="Delete"
//                :disabled="selection.length === 0"
//                @click="handleBatchDelete"
//            >
//                删除
//            </el-button>
//        </div>
//
//        <el-table
//            :data="rows"
//            row-key="id"
//            border
//            stripe
//            @selection-change="handleSelectionChange"
//        >
//            <el-table-column
//                v-if="idColumn"
//                prop="id"
//                label="ID"
//                :fixed="pageSizeStore.isSmall ? undefined : 'left'"
//            />
//            <el-table-column
//                v-if="indexColumn"
//                type="index"
//                :fixed="pageSizeStore.isSmall ? undefined : 'left'"
//            />
//            <el-table-column
//                v-if="multiSelect"
//                type="selection"
//                :fixed="pageSizeStore.isSmall ? undefined : 'left'"
//            />
//            <el-table-column prop="enumProperty" label="enumProperty">
//                <template #default="scope">
//                    <el-form-item
//                        :prop="[scope.$index, 'enumProperty']"
//                        label="enumProperty"
//                        :rule="rules.enumProperty"
//                    >
//                        <EnumSelect v-model="scope.row.enumProperty"/>
//                    </el-form-item>
//                </template>
//            </el-table-column>
//            <el-table-column
//                prop="enumNullableProperty"
//                label="enumNullableProperty"
//            >
//                <template #default="scope">
//                    <el-form-item
//                        :prop="[scope.$index, 'enumNullableProperty']"
//                        label="enumNullableProperty"
//                        :rule="rules.enumNullableProperty"
//                    >
//                        <EnumNullableSelect v-model="scope.row.enumNullableProperty"/>
//                    </el-form-item>
//                </template>
//            </el-table-column>
//            <el-table-column
//                prop="toOnePropertyId"
//                label="toOneProperty"
//            >
//                <template #default="scope">
//                    <el-form-item
//                        :prop="[scope.$index, 'toOnePropertyId']"
//                        label="toOneProperty"
//                        :rule="rules.toOnePropertyId"
//                    >
//                        <ToOneEntityIdSelect
//                            v-model="scope.row.toOnePropertyId"
//                            :options="toOnePropertyIdOptions"
//                        />
//                    </el-form-item>
//                </template>
//            </el-table-column>
//            <el-table-column
//                prop="toOneNullablePropertyId"
//                label="toOneNullableProperty"
//            >
//                <template #default="scope">
//                    <el-form-item
//                        :prop="[scope.$index, 'toOneNullablePropertyId']"
//                        label="toOneNullableProperty"
//                        :rule="rules.toOneNullablePropertyId"
//                    >
//                        <ToOneEntityIdSelect
//                            v-model="scope.row.toOneNullablePropertyId"
//                            :options="toOneNullablePropertyIdOptions"
//                        />
//                    </el-form-item>
//                </template>
//            </el-table-column>
//            <el-table-column
//                label="操作"
//                :fixed="pageSizeStore.isSmall ? undefined : 'right'"
//            >
//                <template #default="scope">
//                    <el-button
//                        type="danger"
//                        :icon="Delete"
//                        link
//                        @click="handleSingleDelete(scope.$index)"
//                    />
//                </template>
//            </el-table-column>
//        </el-table>
//
//        <slot
//            v-if="withOperations"
//            name="operations"
//            :handleSubmit="handleSubmit"
//            :handleCancel="handleCancel"
//        >
//            <div class="form-operations">
//                <el-button type="warning" @click="handleCancel">
//                    取消
//                </el-button>
//                <el-button
//                    type="primary"
//                    :loading="submitLoading"
//                    @click="handleSubmit"
//                >
//                    提交
//                </el-button>
//            </div>
//        </slot>
//    </el-form>
//</template>
//            """.trimIndent(),
//            generator.editTableFiles(testEntityBusiness).first { it.name == "EntityEditTable.vue" }.content.trim()
//        )
//    }
//}
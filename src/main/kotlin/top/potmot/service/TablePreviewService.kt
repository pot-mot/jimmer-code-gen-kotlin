package top.potmot.service

import top.potmot.model.GenTable

interface TablePreviewService {
    fun previewTables(tablePattern: String? = null): List<GenTable>
}

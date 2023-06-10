package top.potmot.service.generating

import org.apache.commons.lang3.StringUtils
import org.apache.velocity.Template
import org.apache.velocity.VelocityContext
import org.apache.velocity.app.Velocity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import top.potmot.constant.Constants
import top.potmot.dao.GenTableRepository
import top.potmot.model.GenTable
import top.potmot.util.VelocityInitializer
import top.potmot.util.VelocityUtils
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.StringWriter
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

/**
 * 表生成业务类
 *
 * @author potmot
 */
@Service
class GenService (
    @Autowired val tableRepository: GenTableRepository
) {
    /**
     * 预览代码
     *
     * @param tableId 表编号
     * @return 预览数据列表
     */
    fun previewCode(tableId: Long): Map<String, String> {
        val dataMap: MutableMap<String, String> = LinkedHashMap()
        // 查询表信息
        val table: GenTable = tableRepository.findById(tableId).get()
        VelocityInitializer.init()
        val context: VelocityContext = VelocityUtils.prepareContext(table)

        // 获取模板列表
        val templates: List<String> = VelocityUtils.templateNameList
        for (template in templates) {
            // 渲染模板
            val sw = StringWriter()
            val tpl: Template = Velocity.getTemplate(template, Constants.UTF8)
            tpl.merge(context, sw)
            dataMap[template] = sw.toString()
        }
        return dataMap
    }

    /**
     * 生成代码（下载方式）
     */
    fun downloadCode(tableId: Long): ByteArray {
        val outputStream = ByteArrayOutputStream()
        val zip = ZipOutputStream(outputStream)
        generatorCode(tableId, zip)
        zip.close()
        return outputStream.toByteArray()
    }

    /**
     * 根据表和模版获取对应文本
     */
    fun getTemplateString(context: VelocityContext, templateName: String): String {
        val template = Velocity.getTemplate(templateName, Constants.UTF8)
        template.merge(context, StringWriter())
        return template.toString()
    }

    /**
     * 生成代码（自定义路径）
     */
    fun generatorCode(tableId: Long) {
        // 查询表信息
        val table: GenTable = tableRepository.findById(tableId).get()
        VelocityInitializer.init()
        val context = VelocityUtils.prepareContext(table)

        // 生成表
        for (templateName in VelocityUtils.templateNameList) {
            File(getGenPath(table, templateName))
                .writeText(getTemplateString(context, templateName))
        }
    }

    /**
     * 批量生成代码（下载方式）
     */
    fun downloadCode(tableIds: Array<Long>): ByteArray {
        val outputStream = ByteArrayOutputStream()
        val zip = ZipOutputStream(outputStream)
        for (tableId in tableIds) {
            generatorCode(tableId, zip)
        }
        zip.close()
        return outputStream.toByteArray()
    }

    /**
     * 查询表信息并生成代码
     */
    private fun generatorCode(tableId: Long, zip: ZipOutputStream) {
        // 查询表信息
        val table: GenTable = tableRepository.findById(tableId).get()
        VelocityInitializer.init()
        val context: VelocityContext = VelocityUtils.prepareContext(table)

        for (templateName in VelocityUtils.templateNameList) {
            // 渲染模板
            val template = getTemplateString(context, templateName)
            zip.putNextEntry(ZipEntry(VelocityUtils.getFileName(templateName, table)))
            zip.write(template.toByteArray())
            zip.flush()
            zip.closeEntry()
        }
    }

    companion object {
        /**
         * 获取代码生成地址
         *
         * @param table    业务表信息
         * @param template 模板文件路径
         * @return 生成地址
         */
        fun getGenPath(table: GenTable, template: String): String {
            val genPath: String = table.genPath
            return if (StringUtils.equals(genPath, "/")) {
                System.getProperty("user.dir") + File.separator + "src" + File.separator + VelocityUtils.getFileName(
                    template,
                    table
                )
            } else genPath + File.separator + VelocityUtils.getFileName(template, table)
        }
    }
}
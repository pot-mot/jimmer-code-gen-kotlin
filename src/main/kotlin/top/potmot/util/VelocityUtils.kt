//package top.potmot.util
//
//import org.apache.commons.lang3.StringUtils
//import org.apache.velocity.VelocityContext
//import top.potmot.config.GenConfig
//import top.potmot.constant.GenConstants
//import top.potmot.model.GenTable
//import top.potmot.model.GenTableColumn
//
///**
// * 模板处理工具类
// */
//object VelocityUtils {
//    /**
//     * 项目空间路径
//     */
//    private const val PROJECT_PATH = "main/java"
//
//    /**
//     * 设置模板变量信息
//     *
//     * @return 模板列表
//     */
//    fun prepareContext(genTable: GenTable): VelocityContext {
//        val moduleName = genTable.moduleName
//        val packageName = genTable.packageName
//        val functionName = genTable.functionName
//        val velocityContext = VelocityContext()
//        velocityContext.put("tableName", genTable.tableName)
//        velocityContext.put(
//            "functionName",
//            if (StringUtils.isNotEmpty(functionName)) functionName else "【请填写功能名称】"
//        )
//        velocityContext.put("ClassName", genTable.className)
//        velocityContext.put("className", StringUtils.uncapitalize(genTable.className))
//        velocityContext.put("moduleName", genTable.moduleName)
//        velocityContext.put("packageName", packageName)
//        velocityContext.put("author", genTable.author)
//        velocityContext.put("importList", getImportList(genTable))
//        velocityContext.put("columns", genTable.columns)
//        velocityContext.put("table", genTable)
//        velocityContext.put("dicts", getDicts(genTable))
//        return velocityContext
//    }
//
//    val templateNameList: List<String>
//        /**
//         * 获取模板信息
//         *
//         * @return 模板列表
//         */
//        get() {
//            val templateNames: MutableList<String> = ArrayList()
//            val fileType = when (GenConfig.language) {
//                "kotlin" -> "kt"
//                "java" -> "java"
//                else -> ""
//            }
//            templateNames.add("vm/${GenConfig.language}/jimmer-entity.${fileType}.vm")
//            templateNames.add("vm/${GenConfig.language}/jimmer-entity-input.${fileType}.vm")
//            templateNames.add("vm/${GenConfig.language}/jimmer-repostory.${fileType}.vm")
//            return templateNames
//        }
//
//    /**
//     * 获取文件名
//     */
//    // TODO 格式化文件名
//    fun getFileName(template: String, genTable: GenTable): String {
//        // 文件名称
//        var fileName = ""
//        // 包路径
//        val packageName = genTable.packageName
//        // 模块名
//        val moduleName = genTable.moduleName
//        // 大写类名
//        val className = genTable.className
//        val javaPath = PROJECT_PATH + "/" + StringUtils.replace(packageName, ".", "/")
//        if (template.contains("model.java.vm")) {
//            fileName = String.format("{}/model/{}.java", javaPath, className)
//        } else if (template.contains("service.java.vm")) {
//            fileName = String.format("{}/service/I{}Service.java", javaPath, className)
//        } else if (template.contains("serviceImpl.java.vm")) {
//            fileName = String.format("{}/service/impl/{}ServiceImpl.java", javaPath, className)
//        } else if (template.contains("controller.java.vm")) {
//            fileName = String.format("{}/controller/{}Controller.java", javaPath, className)
//        }
//        return fileName
//    }
//
//    /**
//     * TODO 根据列类型获取导入包
//     *
//     * @param genTable 业务表对象
//     * @return 返回需要导入的包列表
//     */
//    fun getImportList(genTable: GenTable): HashSet<String> {
//        val columns = genTable.columns
//        val importList = HashSet<String>()
//
//        importList.add("org.babyfish.jimmer.sql.Entity")
//        for (column in columns) {
//            if (column.pk == "1") {
//                importList.add("org.babyfish.jimmer.sql.Id")
//                importList.add("org.babyfish.jimmer.sql.GeneratedValue")
//                importList.add("org.babyfish.jimmer.sql.GenerationType")
//            }
//        }
//        importList.add("java.util.List")
//        importList.add("java.util.Date")
//        importList.add("java.math.BigDecimal")
//        return importList
//    }
//
//    /**
//     * TODO 根据列类型获取字典组
//     *
//     * @param genTable 业务表对象
//     * @return 返回字典组
//     */
//    fun getDicts(genTable: GenTable): String {
//        val columns = genTable.columns
//        val dicts: MutableSet<String> = HashSet()
//        addDicts(dicts, columns)
//        return StringUtils.join(dicts, ", ")
//    }
//
//    /**
//     * 添加字典列表
//     *
//     * @param dicts   字典列表
//     * @param columns 列集合
//     */
//    fun addDicts(dicts: MutableSet<String>, columns: List<GenTableColumn>) {
//        for (column in columns) {
//            if (StringUtils.isNotEmpty(column.dictType) && StringUtils.equalsAny(
//                    column.htmlType,
//                    *arrayOf(GenConstants.HTML_SELECT, GenConstants.HTML_RADIO, GenConstants.HTML_CHECKBOX)
//                )
//            ) {
//                dicts.add("'" + column.dictType + "'")
//            }
//        }
//    }
//}

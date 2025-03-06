package top.potmot.core.business.test.generate.impl.java

import top.potmot.core.business.meta.RootEntityBusiness
import top.potmot.core.business.test.generate.TestGenerator
import top.potmot.enumeration.GenLanguage

private const val sqlClient = "sqlClient"

object JavaTestGenerator: TestGenerator {
    override val suffix = GenLanguage.JAVA.suffix

    override fun stringifyTest(entity: RootEntityBusiness): String {
        // TODO
        return ""
    }
}
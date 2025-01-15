package top.potmot.business.view.rules

import top.potmot.core.business.view.generate.meta.rules.RequiredRule

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import top.potmot.core.business.view.generate.meta.rules.ArrayRule
import top.potmot.core.business.view.generate.meta.rules.BooleanRule
import top.potmot.core.business.view.generate.meta.rules.DateRule
import top.potmot.core.business.view.generate.meta.rules.EnumRule
import top.potmot.core.business.view.generate.meta.rules.IntRule
import top.potmot.core.business.view.generate.meta.rules.IntSizeRule
import top.potmot.core.business.view.generate.meta.rules.NumberRule
import top.potmot.core.business.view.generate.meta.rules.NumberSizeRule
import top.potmot.core.business.view.generate.meta.rules.PatternRule
import top.potmot.core.business.view.generate.meta.rules.StringLengthRule

class RulesTest {
    @Test
    fun testRequiredRule() {
        val rule = RequiredRule("用户名")
        assertEquals("{required: true, message: \"用户名不能为空\", trigger: \"blur\"}", rule.stringify())
    }

    @Test
    fun testArrayRule() {
        val rule = ArrayRule(comment = "用户列表")
        assertEquals("{type: \"array\", message: \"用户列表必须是列表\", trigger: \"blur\"}", rule.stringify())
    }

    @Test
    fun testEnumRule() {
        val rule = EnumRule("性别", listOf("男", "女"))
        assertEquals("{type: \"enum\", enum: [\"男\", \"女\"], message: \"性别必须是男/女\", trigger: \"blur\"}", rule.stringify())
    }

    @Test
    fun testStringLengthRule() {
        val rule1 = StringLengthRule("用户名", 5, 10)
        assertEquals("{type: \"string\", min: 5, max: 10, message: \"用户名长度必须在5-10之间\", trigger: \"blur\"}", rule1.stringify())

        val rule2 = StringLengthRule("用户名", 5, null)
        assertEquals("{type: \"string\", min: 5, message: \"用户名长度必须大于5\", trigger: \"blur\"}", rule2.stringify())

        val rule3 = StringLengthRule("用户名", null, 10)
        assertEquals("{type: \"string\", max: 10, message: \"用户名长度必须小于10\", trigger: \"blur\"}", rule3.stringify())

        val rule4 = StringLengthRule("用户名", null, null)
        assertEquals("{type: \"string\", message: \"用户名必须是字符串\", trigger: \"blur\"}", rule4.stringify())
    }

    @Test
    fun testBooleanRule() {
        val rule = BooleanRule("开关")
        assertEquals("{type: \"boolean\", message: \"开关必须是开启或关闭\", trigger: \"blur\"}", rule.stringify())
    }

    @Test
    fun testDateRule() {
        val rule = DateRule("出生日期")
        assertEquals("{type: \"date\", message: \"出生日期必须是日期\", trigger: \"blur\"}", rule.stringify())
    }

    @Test
    fun testIntRule() {
        val rule = IntRule("年龄")
        assertEquals("{type: \"integer\", message: \"年龄必须是整数\", trigger: \"blur\"}", rule.stringify())
    }

    @Test
    fun testNumberRule() {
        val rule = NumberRule("金额")
        assertEquals("{type: \"number\", message: \"金额必须是数字\", trigger: \"blur\"}", rule.stringify())
    }

    @Test
    fun testIntSizeRule() {
        val rule1 = IntSizeRule("年龄", "18.0", "60.0")
        assertEquals("{type: \"integer\", min: 18.0, max: 60.0, message: \"年龄必须在18.0-60.0之间\", trigger: \"blur\"}", rule1.stringify())

        val rule2 = IntSizeRule("年龄", "18.0", null)
        assertEquals("{type: \"integer\", min: 18.0, message: \"年龄必须大于18.0\", trigger: \"blur\"}", rule2.stringify())

        val rule3 = IntSizeRule("年龄", null, "60.0")
        assertEquals("{type: \"integer\", max: 60.0, message: \"年龄必须小于60.0\", trigger: \"blur\"}", rule3.stringify())

        val rule4 = IntSizeRule("年龄", null, null)
        assertEquals("{type: \"integer\", message: \"年龄必须是整数\", trigger: \"blur\"}", rule4.stringify())
    }

    @Test
    fun testNumberSizeRule() {
        val rule1 = NumberSizeRule("金额", "100.0", "1000.0")
        assertEquals("{type: \"number\", min: 100.0, max: 1000.0, message: \"金额必须在100.0-1000.0之间\", trigger: \"blur\"}", rule1.stringify())

        val rule2 = NumberSizeRule("金额", "100.0", null)
        assertEquals("{type: \"number\", min: 100.0, message: \"金额必须大于100.0\", trigger: \"blur\"}", rule2.stringify())

        val rule3 = NumberSizeRule("金额", null, "1000.0")
        assertEquals("{type: \"number\", max: 1000.0, message: \"金额必须小于1000.0\", trigger: \"blur\"}", rule3.stringify())

        val rule4 = NumberSizeRule("金额", null, null)
        assertEquals("{type: \"number\", message: \"金额必须是数字\", trigger: \"blur\"}", rule4.stringify())
    }

    @Test
    fun testPatternRule() {
        val rule = PatternRule("[0-9]{2}:[0-9]{2}:[0-9]{2}", "时间格式不正确")
        assertEquals("{pattern: /[0-9]{2}:[0-9]{2}:[0-9]{2}/, message: \"时间格式不正确\", trigger: \"blur\"}", rule.stringify())
    }
}

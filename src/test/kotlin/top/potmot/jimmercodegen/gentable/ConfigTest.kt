package top.potmot.jimmercodegen.gentable;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.potmot.jimmercodegen.config.GenConfig

@SpringBootTest
public class ConfigTest {
    @Test
    fun getValue() {
        println("${GenConfig.packageName} ${GenConfig.author} ${GenConfig.autoRemovePre}, ${GenConfig.tablePrefix}")
    }

}

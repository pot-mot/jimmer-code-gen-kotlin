package top.potmot.jimmercodegen.config;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.potmot.config.GenConfig

@SpringBootTest
public class ConfigTest {
    @Test
    fun getValue() {
        println("${GenConfig.packageName} ${GenConfig.author} ${GenConfig.autoRemovePre}, ${GenConfig.tablePrefix}")
    }

}

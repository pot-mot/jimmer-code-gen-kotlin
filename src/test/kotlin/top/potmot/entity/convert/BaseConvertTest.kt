package top.potmot.entity.convert

import org.babyfish.jimmer.kt.unload
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import top.potmot.entity.GenEntity
import top.potmot.entity.GenProperty
import top.potmot.entity.copy
import top.potmot.entity.dto.GenEntityDetailView
import top.potmot.service.ConvertService
import top.potmot.utils.json.prettyObjectWriter

@SpringBootTest
@ActiveProfiles("test", "h2", "hide-sql")
abstract class BaseConvertTest {
    @Autowired
    lateinit var sqlClient: KSqlClient

    @Autowired
    lateinit var convertService: ConvertService

    val GenEntityDetailView.result
        get() = prettyObjectWriter.writeValueAsString(
            toEntity {
                unload(this, GenEntity::id)
                unload(this, GenEntity::modifiedTime)
                unload(this, GenEntity::createdTime)
                properties = properties.map {
                    it.copy {
                        unload(this, GenProperty::id)
                        unload(this, GenProperty::modifiedTime)
                        unload(this, GenProperty::createdTime)
                        unload(this, GenProperty::column)
                    }
                }
            }
        )
}
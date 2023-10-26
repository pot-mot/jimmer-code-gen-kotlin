package top.potmot.service

import org.babyfish.jimmer.ImmutableObjects
import org.babyfish.jimmer.kt.new
import org.babyfish.jimmer.spring.cfg.JimmerProperties
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.isNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import top.potmot.model.GenEntity
import top.potmot.model.GenPackage
import top.potmot.model.by
import top.potmot.model.dto.GenPackageInput
import top.potmot.model.dto.GenPackageTreeView
import top.potmot.model.id
import top.potmot.model.packageId
import top.potmot.model.parentId

@RestController
@RequestMapping("/package")
class PackageService(
    @Autowired val sqlClient: KSqlClient,
    private val jimmerProperties: JimmerProperties
) {
    @GetMapping
    fun list(): List<GenPackageTreeView> {
        return sqlClient.createQuery(GenPackage::class) {
            where(table.parentId.isNull())
            select(table.fetch(GenPackageTreeView::class))
        }.execute()
    }

    @PostMapping
    fun createPackage(
        @RequestParam path: String,
        @RequestParam(required = false) parentId: Long?
    ): Long? {
        val names = path.split(".")
        if (path.isBlank() || names.isEmpty()) return null

        var currentPackage: GenPackage = new(GenPackage::class).by {
            name = names[0]
            this.parentId = parentId
        }

        for (i in 1 until names.size) {
            val temp = new(GenPackage::class).by {
                name = names[i]
            }
            currentPackage = new(GenPackage::class).by(temp) {
                parent = currentPackage
            }
        }

        return sqlClient.insert(currentPackage).modifiedEntity.id
    }

    @PutMapping
    fun update(
        @RequestBody input: GenPackageInput
    ): Long {
        return sqlClient.update(input).modifiedEntity.id
    }

    @PutMapping("/{packageId}/package/{id}")
    fun movePackage(
        @PathVariable id: Long,
        @PathVariable packageId: Long
    ): Int {
        return sqlClient.createUpdate(GenPackage::class) {
            where(table.id eq id)
            set(table.parentId, packageId)
        }.execute()
    }

    @PutMapping("/{packageId}/entity/{id}")
    fun moveEntity(
        @PathVariable id: Long,
        @PathVariable packageId: Long
    ): Int {
        return sqlClient.createUpdate(GenEntity::class) {
            where(table.id eq id)
            set(table.packageId, packageId)
        }.execute()
    }

    @PutMapping("/{packageId}/enum/{id}")
    fun moveEnum(
        @PathVariable id: Long,
        @PathVariable packageId: Long
    ): Int {
        return sqlClient.createUpdate(GenEntity::class) {
            where(table.id eq id)
            set(table.packageId, packageId)
        }.execute()
    }
}

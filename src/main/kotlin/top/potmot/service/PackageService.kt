package top.potmot.service

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.isNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import top.potmot.model.GenEntity
import top.potmot.model.GenPackage
import top.potmot.model.dto.GenPackageInput
import top.potmot.model.dto.GenPackageTreeView
import top.potmot.model.id
import top.potmot.model.packageId
import top.potmot.model.parentId
import top.potmot.model.unsafeInput.GenPackagePathInput

@RestController
@RequestMapping("/package")
@Transactional
class PackageService(
    @Autowired val sqlClient: KSqlClient,
) {
    @GetMapping
    fun list(): List<GenPackageTreeView> {
        return sqlClient.createQuery(GenPackage::class) {
            where(table.parentId.isNull())
            select(table.fetch(GenPackageTreeView::class))
        }.execute()
    }

    @PostMapping
    fun create(
        @RequestBody pathInput: GenPackagePathInput
    ): Long? {
        return pathInput.toEntity()?.let {
            sqlClient.insert(it).modifiedEntity.id
        }
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

    @DeleteMapping("{ids}")
    fun delete(@PathVariable ids: List<Long>): Int {
        return sqlClient.deleteByIds(GenPackage::class, ids).totalAffectedRowCount
    }
}

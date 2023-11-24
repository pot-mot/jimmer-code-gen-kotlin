package top.potmot.model.unsafeInput

import org.babyfish.jimmer.kt.new
import top.potmot.model.GenPackage
import top.potmot.model.by

data class GenPackagePathInput(
    val path: String,
    val parentId: Long?
) {
    fun toEntity(): GenPackage? {
        val names = path.split(".")
        if (path.isBlank() || names.isEmpty()) return null

        var currentPackage: GenPackage = new(GenPackage::class).by {
            name = names[0]
            this.parentId = this@GenPackagePathInput.parentId
        }

        for (i in 1 until names.size) {
            if (names[i].isBlank()) continue
            val temp = new(GenPackage::class).by {
                name = names[i]
            }
            currentPackage = new(GenPackage::class).by(temp) {
                parent = currentPackage
            }
        }

        return currentPackage
    }
}

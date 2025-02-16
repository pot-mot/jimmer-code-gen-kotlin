package top.potmot.core.business.view.generate.impl.vue3elementPlus

import top.potmot.core.business.meta.LazyEnumSelect
import top.potmot.core.business.meta.LazyEnumView
import top.potmot.core.business.meta.LazyGenerated
import top.potmot.core.business.meta.LazyIdSelect
import top.potmot.core.business.meta.LazyShortViewTable
import top.potmot.core.business.meta.LazySubEdit
import top.potmot.core.business.meta.LazySubView
import top.potmot.core.business.view.generate.impl.vue3elementPlus.edit.SubEditFormGen
import top.potmot.core.business.view.generate.impl.vue3elementPlus.edit.SubEditTableGen
import top.potmot.core.business.view.generate.impl.vue3elementPlus.enumSelect.EnumSelectGen
import top.potmot.core.business.view.generate.impl.vue3elementPlus.enumView.EnumViewGen
import top.potmot.core.business.view.generate.impl.vue3elementPlus.idSelect.IdSelectGen
import top.potmot.core.business.view.generate.impl.vue3elementPlus.view.ViewFormGen
import top.potmot.core.business.view.generate.impl.vue3elementPlus.view.ViewTableGen
import top.potmot.entity.dto.GenerateFile

interface LazyGenerator :
    IdSelectGen,
    SubEditFormGen,
    SubEditTableGen,
    ViewFormGen,
    ViewTableGen,
    EnumSelectGen,
    EnumViewGen {
    fun generateLazy(
        lazyItems: Iterable<LazyGenerated>,
        generatedKeySet: MutableSet<String>,
    ): List<GenerateFile> {
        val files = mutableListOf<GenerateFile>()
        val subLazyItems = mutableListOf<LazyGenerated>()

        lazyItems.distinct()
            .filter { it.key !in generatedKeySet }
            .forEach {
                when (it) {
                    is LazyIdSelect -> {
                        files += idSelectFile(it.entity, it.multiple)
                    }

                    is LazySubEdit -> {
                        val result =
                            if (it.multiple) subEditTableFiles(it.entity) else subEditFormFiles(it.entity, it.nullable)
                        files += result.files
                        subLazyItems += result.lazyItems
                    }

                    is LazyEnumSelect -> {
                        val (file, lazyView) = enumSelectFile(it)
                        files += file
                        subLazyItems += lazyView
                    }

                    is LazyEnumView -> {
                        files += enumViewFile(it)
                    }

                    is LazySubView -> {
                        val result =
                            if (it.multiple) viewTableFiles(it.entity) else viewFormFiles(it.entity, it.nullable)

                        files += result.files
                        subLazyItems += result.lazyItems
                    }

                    is LazyShortViewTable -> {
                        val result = viewTableFiles(it.entity, it.properties)

                        files += result.files
                        subLazyItems += result.lazyItems
                    }
                }

                generatedKeySet += it.key
            }

        if (subLazyItems.isNotEmpty()) {
            files += generateLazy(subLazyItems, generatedKeySet)
        }

        return files
    }
}
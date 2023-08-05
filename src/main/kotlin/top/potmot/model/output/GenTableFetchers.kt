package top.potmot.model.output

import org.babyfish.jimmer.sql.kt.fetcher.newFetcher
import top.potmot.model.GenTable
import top.potmot.model.by

object GenTableFetchers {
    val SIMPLE_FETCHER = newFetcher(GenTable::class).by {
        allTableFields()

    }

    val COLUMN_FETCHER = newFetcher(GenTable::class).by {
        allTableFields()
        columns {
            allTableFields()
        }
    }

    val TABLE_FETCHER = newFetcher(GenTable::class).by {
        tableName()
        tableComment()
        tableType()
    }

    val TABLE_COLUMN_FETCHER = newFetcher(GenTable::class).by(TABLE_FETCHER) {
        columns {
            columnSort()
            columnName()
            columnType()
            columnDisplaySize()
            columnPrecision()
            columnDefault()
            columnComment()
            isPk()
            isAutoIncrement()
            isUnique()
            isNotNull()
            isVirtualColumn()
        }
    }

    val CLASS_FETCHER = newFetcher(GenTable::class).by {
        className()
        classComment()
        packageName()
        moduleName()
        functionName()
        author()
        genPath()
        isAdd()
        isEdit()
        isList()
        isQuery()
        orderKey()
    }

    val CLASS_COLUMN_FETCHER = newFetcher(GenTable::class).by(CLASS_FETCHER) {
        columns {
            fieldName()
            fieldType()
            fieldComment()
            isList()
            listSort()
            isAdd()
            addSort()
            isAddRequired()
            isEdit()
            editSort()
            isEditRequired()
            readOnly()
            isQuery()
            querySort()
            queryType()
            dictType()
            isSort()
            sortDirection()
            isLogicalDelete()
        }
    }
}

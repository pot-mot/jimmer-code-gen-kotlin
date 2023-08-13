package top.potmot.model.base

interface PageQuery: BaseQuery {
    val page: PageQueryParam?
}

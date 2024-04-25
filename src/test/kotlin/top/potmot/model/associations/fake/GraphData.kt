package top.potmot.model.associations.fake

fun String.toFake() = replace("\"fake\": false,", "\"fake\": true,")

val MANY_TO_ONE = top.potmot.model.associations.real.MANY_TO_ONE.toFake()

val ONE_TO_MANY = top.potmot.model.associations.real.ONE_TO_MANY.toFake()

val ONE_TO_ONE = top.potmot.model.associations.real.ONE_TO_ONE.toFake()

val MANY_TO_MANY = top.potmot.model.associations.real.MANY_TO_MANY.toFake()

val TREE_NODE = top.potmot.model.associations.real.TREE_NODE.toFake()

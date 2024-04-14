package top.potmot.model.query.param

import java.time.LocalDateTime

class TimeRangeQueryParam(
    override val start: LocalDateTime? = null,
    override val end: LocalDateTime? = null
) : RangeQueryParam<LocalDateTime?>

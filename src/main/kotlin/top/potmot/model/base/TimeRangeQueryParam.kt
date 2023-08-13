package top.potmot.model.base

import java.time.LocalDateTime

class TimeRangeQueryParam(
    override val start: LocalDateTime,
    override val end: LocalDateTime
) : RangeQueryParam<LocalDateTime>

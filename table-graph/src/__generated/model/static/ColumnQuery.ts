import type { TimeRangeQueryParam } from './';

export interface ColumnQuery {
    
    readonly createdTime?: TimeRangeQueryParam;
    
    readonly ids?: ReadonlyArray<number>;
    
    readonly modifiedTime?: TimeRangeQueryParam;
}

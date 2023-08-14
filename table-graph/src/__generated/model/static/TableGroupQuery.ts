import type { TimeRangeQueryParam } from './';

export interface TableGroupQuery {
    
    readonly createdTime?: TimeRangeQueryParam;
    
    readonly ids?: ReadonlyArray<number>;
    
    readonly modifiedTime?: TimeRangeQueryParam;
}

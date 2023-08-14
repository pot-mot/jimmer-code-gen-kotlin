import type { TimeRangeQueryParam } from './';

export interface TableQuery {
    
    readonly createdTime?: TimeRangeQueryParam;
    
    readonly ids?: ReadonlyArray<number>;
    
    readonly modifiedTime?: TimeRangeQueryParam;
}

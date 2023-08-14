import type { TimeRangeQueryParam } from './';

export interface EntityQuery {
    
    readonly createdTime?: TimeRangeQueryParam;
    
    readonly ids?: ReadonlyArray<number>;
    
    readonly modifiedTime?: TimeRangeQueryParam;
}

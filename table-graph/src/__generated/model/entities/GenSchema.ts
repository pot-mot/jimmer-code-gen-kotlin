import type { GenDataSource, GenTable } from './';

export interface GenSchema {
    
    id: number;
    
    dataSourceId: number;
    
    dataSource: GenDataSource;
    
    name: string;
    
    orderKey: number;
    
    tables: GenTable[];
}

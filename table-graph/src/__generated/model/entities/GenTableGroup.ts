import type { GenTable } from './';

export interface GenTableGroup {
    
    createdTime: string;
    
    modifiedTime: string;
    
    remark: string;
    
    id: number;
    
    parentId?: number;
    
    parent?: GenTableGroup;
    
    children: GenTableGroup[];
    
    tables: GenTable[];
    
    groupName: string;
    
    orderKey: number;
}

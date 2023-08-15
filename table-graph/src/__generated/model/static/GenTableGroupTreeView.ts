import type { TableType } from '../enums';

export interface GenTableGroupTreeView {
    
    children?: GenTableGroupTreeView_TargetOf_children[];
    
    createdTime: string;
    
    groupName: string;
    
    id?: number;
    
    modifiedTime: string;
    
    orderKey: number;
    
    remark: string;
    
    tables: GenTableGroupTreeView_TargetOf_tables[];
}

export interface GenTableGroupTreeView_TargetOf_children {
    
    children?: GenTableGroupTreeView_TargetOf_children[];
    
    createdTime: string;
    
    groupName: string;
    
    id?: number;
    
    modifiedTime: string;
    
    orderKey: number;
    
    remark: string;
    
    tables: GenTableGroupTreeView_TargetOf_children_TargetOf_tables[];
}

export interface GenTableGroupTreeView_TargetOf_children_TargetOf_tables {
    
    createdTime: string;
    
    id?: number;
    
    modifiedTime: string;
    
    orderKey: number;
    
    remark: string;
    
    tableComment: string;
    
    tableName: string;
    
    tableType: TableType;
}

export interface GenTableGroupTreeView_TargetOf_tables {
    
    createdTime: string;
    
    id?: number;
    
    modifiedTime: string;
    
    orderKey: number;
    
    remark: string;
    
    tableComment: string;
    
    tableName: string;
    
    tableType: TableType;
}

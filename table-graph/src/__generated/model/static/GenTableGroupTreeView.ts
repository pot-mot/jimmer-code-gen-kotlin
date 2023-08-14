import type { TableType } from '../enums';

export interface GenTableGroupTreeView {
    
    readonly children?: ReadonlyArray<GenTableGroupTreeView_TargetOf_children>;
    
    readonly createdTime: string;
    
    readonly groupName: string;
    
    readonly id?: number;
    
    readonly modifiedTime: string;
    
    readonly orderKey: number;
    
    readonly remark: string;
    
    readonly tables: ReadonlyArray<GenTableGroupTreeView_TargetOf_tables>;
}

export interface GenTableGroupTreeView_TargetOf_children {
    
    readonly children?: ReadonlyArray<GenTableGroupTreeView_TargetOf_children>;
    
    readonly createdTime: string;
    
    readonly groupName: string;
    
    readonly id?: number;
    
    readonly modifiedTime: string;
    
    readonly orderKey: number;
    
    readonly remark: string;
    
    readonly tables: ReadonlyArray<GenTableGroupTreeView_TargetOf_children_TargetOf_tables>;
}

export interface GenTableGroupTreeView_TargetOf_children_TargetOf_tables {
    
    readonly createdTime: string;
    
    readonly id?: number;
    
    readonly modifiedTime: string;
    
    readonly orderKey: number;
    
    readonly remark: string;
    
    readonly tableComment: string;
    
    readonly tableName: string;
    
    readonly tableType: TableType;
}

export interface GenTableGroupTreeView_TargetOf_tables {
    
    readonly createdTime: string;
    
    readonly id?: number;
    
    readonly modifiedTime: string;
    
    readonly orderKey: number;
    
    readonly remark: string;
    
    readonly tableComment: string;
    
    readonly tableName: string;
    
    readonly tableType: TableType;
}

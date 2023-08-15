import type { TableType } from '../enums';

export interface GenTableColumnsView {
    
    columns: GenTableColumnsView_TargetOf_columns[];
    
    createdTime: string;
    
    groupId: number;
    
    id?: number;
    
    modifiedTime: string;
    
    orderKey: number;
    
    remark: string;
    
    schemaId: number;
    
    tableComment: string;
    
    tableName: string;
    
    tableType: TableType;
}

export interface GenTableColumnsView_TargetOf_columns {
    
    autoIncrement: boolean;
    
    columnComment: string;
    
    columnDefault?: string;
    
    columnDisplaySize: number;
    
    columnName: string;
    
    columnPrecision: number;
    
    columnSort: number;
    
    columnType: string;
    
    columnTypeCode: number;
    
    createdTime: string;
    
    fk: boolean;
    
    id?: number;
    
    modifiedTime: string;
    
    notNull: boolean;
    
    pk: boolean;
    
    remark: string;
    
    tableId?: number;
    
    unique: boolean;
}

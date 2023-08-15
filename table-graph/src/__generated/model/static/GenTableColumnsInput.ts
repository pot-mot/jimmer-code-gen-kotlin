import type { TableType } from '../enums';

export interface GenTableColumnsInput {
    
    columns: GenTableColumnsInput_TargetOf_columns[];
    
    groupId: number;
    
    id?: number;
    
    orderKey: number;
    
    remark: string;
    
    schemaId: number;
    
    tableComment: string;
    
    tableName: string;
    
    tableType: TableType;
}

export interface GenTableColumnsInput_TargetOf_columns {
    
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
    
    unique: boolean;
}

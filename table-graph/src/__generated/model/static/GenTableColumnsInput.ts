import type { TableType } from '../enums';

export interface GenTableColumnsInput {
    
    readonly columns: ReadonlyArray<GenTableColumnsInput_TargetOf_columns>;
    
    readonly id?: number;
    
    readonly orderKey: number;
    
    readonly remark: string;
    
    readonly tableComment: string;
    
    readonly tableName: string;
    
    readonly tableType: TableType;
}

export interface GenTableColumnsInput_TargetOf_columns {
    
    readonly autoIncrement: boolean;
    
    readonly columnComment: string;
    
    readonly columnDefault?: string;
    
    readonly columnDisplaySize: number;
    
    readonly columnName: string;
    
    readonly columnPrecision: number;
    
    readonly columnSort: number;
    
    readonly columnType: string;
    
    readonly columnTypeCode: number;
    
    readonly createdTime: string;
    
    readonly fk: boolean;
    
    readonly id?: number;
    
    readonly modifiedTime: string;
    
    readonly notNull: boolean;
    
    readonly pk: boolean;
    
    readonly remark: string;
    
    readonly unique: boolean;
    
    readonly virtualColumn: boolean;
}

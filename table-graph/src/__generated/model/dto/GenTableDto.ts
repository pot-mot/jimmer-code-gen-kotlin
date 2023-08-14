import type { TableType } from '../enums';

export type GenTableDto = {
    'DEFAULT': {
        readonly createdTime: string, 
        readonly modifiedTime: string, 
        readonly remark: string, 
        readonly id: number, 
        readonly group?: {readonly id: number}, 
        readonly tableName: string, 
        readonly tableComment: string, 
        readonly tableType: TableType, 
        readonly orderKey: number
    }
}
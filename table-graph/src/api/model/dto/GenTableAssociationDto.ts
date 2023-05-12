export type GenTableAssociationDto = {
    'DEFAULT': {
        readonly createdTime: string, 
        readonly modifiedTime: string, 
        readonly remark: string, 
        readonly id: number, 
        readonly tableAssociationName: string, 
        readonly sourceTable: {readonly id: number}, 
        readonly sourceColumn: {readonly id: number}, 
        readonly targetTable: {readonly id: number}, 
        readonly targetColumn: {readonly id: number}, 
        readonly associationCategory: string
    }
}
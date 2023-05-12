export type GenTableDto = {
    'DEFAULT': {
        readonly createdTime: string, 
        readonly modifiedTime: string, 
        readonly remark: string, 
        readonly id: number, 
        readonly tableName: string, 
        readonly tableComment: string, 
        readonly className: string, 
        readonly packageName: string, 
        readonly moduleName: string, 
        readonly businessName: string, 
        readonly functionName: string, 
        readonly author: string, 
        readonly genType: string, 
        readonly genPath: string, 
        readonly options: string
    }
}
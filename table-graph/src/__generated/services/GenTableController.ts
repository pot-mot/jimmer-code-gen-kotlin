import type { Executor } from '../';
import type { GenTableDto } from '../model/dto';

export class GenTableController {
    
    constructor(private executor: Executor) {}
    
    async get(options: GenTableControllerOptions['get']): Promise<
        GenTableDto['GenTableFetchers/COLUMN_FETCHER']
    > {
        let _uri = '/genTable/';
        _uri += encodeURIComponent(options.id);
        return (await this.executor({uri: _uri, method: 'GET'})) as GenTableDto['GenTableFetchers/COLUMN_FETCHER']
    }
    
    async getClass(options: GenTableControllerOptions['getClass']): Promise<
        GenTableDto['GenTableFetchers/CLASS_COLUMN_FETCHER']
    > {
        let _uri = '/genTable/class/';
        _uri += encodeURIComponent(options.id);
        return (await this.executor({uri: _uri, method: 'GET'})) as GenTableDto['GenTableFetchers/CLASS_COLUMN_FETCHER']
    }
    
    async getTable(options: GenTableControllerOptions['getTable']): Promise<
        GenTableDto['GenTableFetchers/TABLE_COLUMN_FETCHER']
    > {
        let _uri = '/genTable/table/';
        _uri += encodeURIComponent(options.id);
        return (await this.executor({uri: _uri, method: 'GET'})) as GenTableDto['GenTableFetchers/TABLE_COLUMN_FETCHER']
    }
    
    async list(): Promise<
        ReadonlyArray<GenTableDto['GenTableFetchers/SIMPLE_FETCHER']>
    > {
        let _uri = '/genTable/list';
        return (await this.executor({uri: _uri, method: 'GET'})) as ReadonlyArray<GenTableDto['GenTableFetchers/SIMPLE_FETCHER']>
    }
    
    async listClasses(): Promise<
        ReadonlyArray<GenTableDto['GenTableFetchers/CLASS_FETCHER']>
    > {
        let _uri = '/genTable/class/list';
        return (await this.executor({uri: _uri, method: 'GET'})) as ReadonlyArray<GenTableDto['GenTableFetchers/CLASS_FETCHER']>
    }
    
    async listTables(): Promise<
        ReadonlyArray<GenTableDto['GenTableFetchers/TABLE_FETCHER']>
    > {
        let _uri = '/genTable/table/list';
        return (await this.executor({uri: _uri, method: 'GET'})) as ReadonlyArray<GenTableDto['GenTableFetchers/TABLE_FETCHER']>
    }
}

export type GenTableControllerOptions = {
    'get': {readonly id: number},
    'getClass': {readonly id: number},
    'getTable': {readonly id: number},
    'list': {},
    'listClasses': {},
    'listTables': {}
}
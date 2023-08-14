import type { Executor } from '../';
import type { GenTableGroupCommonInput, GenTableGroupCommonView, GenTableGroupMoveInput, GenTableGroupTreeView, TableGroupQuery } from '../model/static';

export class TableGroupController {
    
    constructor(private executor: Executor) {}
    
    async create(options: TableGroupControllerOptions['create']): Promise<
        GenTableGroupCommonView
    > {
        let _uri = '/table/group/';
        return (await this.executor({uri: _uri, method: 'POST', body: options.body})) as GenTableGroupCommonView
    }
    
    async delete(options: TableGroupControllerOptions['delete']): Promise<number> {
        let _uri = '/table/group/';
        return (await this.executor({uri: _uri, method: 'DELETE', body: options.body})) as number
    }
    
    async edit(options: TableGroupControllerOptions['edit']): Promise<
        GenTableGroupCommonView
    > {
        let _uri = '/table/group/';
        return (await this.executor({uri: _uri, method: 'PUT', body: options.body})) as GenTableGroupCommonView
    }
    
    async getTrees(options: TableGroupControllerOptions['getTrees']): Promise<
        ReadonlyArray<GenTableGroupTreeView>
    > {
        let _uri = '/table/group/tree';
        let _separator = _uri.indexOf('?') === -1 ? '?' : '&';
        let _value: any = undefined;
        _value = options.groupIds?.join(',');
        if (_value !== undefined && _value !== null) {
            _uri += _separator
            _uri += 'groupIds='
            _uri += encodeURIComponent(_value);
            _separator = '&';
        }
        return (await this.executor({uri: _uri, method: 'GET'})) as ReadonlyArray<GenTableGroupTreeView>
    }
    
    async move(options: TableGroupControllerOptions['move']): Promise<
        GenTableGroupCommonView
    > {
        let _uri = '/table/group/move';
        return (await this.executor({uri: _uri, method: 'PUT', body: options.body})) as GenTableGroupCommonView
    }
    
    async query(options: TableGroupControllerOptions['query']): Promise<
        ReadonlyArray<GenTableGroupCommonView>
    > {
        let _uri = '/table/group/query';
        return (await this.executor({uri: _uri, method: 'POST', body: options.body})) as ReadonlyArray<GenTableGroupCommonView>
    }
}

export type TableGroupControllerOptions = {
    'create': {readonly body: GenTableGroupCommonInput},
    'delete': {readonly body: ReadonlyArray<number>},
    'edit': {readonly body: GenTableGroupCommonInput},
    'getTrees': {readonly groupIds?: ReadonlyArray<number>},
    'move': {readonly body: GenTableGroupMoveInput},
    'query': {readonly body: TableGroupQuery}
}
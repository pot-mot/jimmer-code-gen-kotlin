import type { Executor } from '../';
import type { AssociationQuery, GenAssociationCommonInput, GenAssociationCommonView, GenAssociationPreviewView, Optional } from '../model/static';

export class AssociationController {
    
    constructor(private executor: Executor) {}
    
    async delete(options: AssociationControllerOptions['delete']): Promise<number> {
        let _uri = '/association/';
        return (await this.executor({uri: _uri, method: 'DELETE', body: options.body})) as number
    }
    
    async query(options: AssociationControllerOptions['query']): Promise<
        ReadonlyArray<GenAssociationCommonView>
    > {
        let _uri = '/association/query';
        return (await this.executor({uri: _uri, method: 'POST', body: options.body})) as ReadonlyArray<GenAssociationCommonView>
    }
    
    async save(options: AssociationControllerOptions['save']): Promise<
        ReadonlyArray<Optional<GenAssociationCommonView>>
    > {
        let _uri = '/association/save';
        return (await this.executor({uri: _uri, method: 'PUT', body: options.body})) as ReadonlyArray<Optional<GenAssociationCommonView>>
    }
    
    async select(options: AssociationControllerOptions['select']): Promise<
        ReadonlyArray<GenAssociationPreviewView>
    > {
        let _uri = '/association/select';
        let _separator = _uri.indexOf('?') === -1 ? '?' : '&';
        let _value: any = undefined;
        _value = options.tableIds.join(',');
        if (_value !== undefined && _value !== null) {
            _uri += _separator
            _uri += 'tableIds='
            _uri += encodeURIComponent(_value);
            _separator = '&';
        }
        return (await this.executor({uri: _uri, method: 'GET'})) as ReadonlyArray<GenAssociationPreviewView>
    }
}

export type AssociationControllerOptions = {
    'delete': {readonly body: ReadonlyArray<number>},
    'query': {readonly body: AssociationQuery},
    'save': {readonly body: ReadonlyArray<GenAssociationCommonInput>},
    'select': {readonly tableIds: ReadonlyArray<number>}
}
import type { Executor } from './';

import { AssociationController, EntityController, TableController, TableGroupController } from './services';

export class Api {
    
    readonly associationController: AssociationController;
    
    readonly entityController: EntityController;
    
    readonly tableController: TableController;
    
    readonly tableGroupController: TableGroupController;
    
    constructor(executor: Executor) {
        this.associationController = new AssociationController(executor);
        this.entityController = new EntityController(executor);
        this.tableController = new TableController(executor);
        this.tableGroupController = new TableGroupController(executor);
    }
}
import type { Executor } from './';

import { ReturnService } from './services';

export class Api {
    
    readonly returnService: ReturnService;
    
    constructor(executor: Executor) {
        this.returnService = new ReturnService(executor);
    }
}
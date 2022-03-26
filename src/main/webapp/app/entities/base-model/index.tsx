import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BaseModel from './base-model';
import BaseModelDetail from './base-model-detail';
import BaseModelUpdate from './base-model-update';
import BaseModelDeleteDialog from './base-model-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BaseModelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BaseModelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BaseModelDetail} />
      <ErrorBoundaryRoute path={match.url} component={BaseModel} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BaseModelDeleteDialog} />
  </>
);

export default Routes;

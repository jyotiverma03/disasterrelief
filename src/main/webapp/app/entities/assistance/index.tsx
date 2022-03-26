import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Assistance from './assistance';
import AssistanceDetail from './assistance-detail';
import AssistanceUpdate from './assistance-update';
import AssistanceDeleteDialog from './assistance-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AssistanceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AssistanceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AssistanceDetail} />
      <ErrorBoundaryRoute path={match.url} component={Assistance} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AssistanceDeleteDialog} />
  </>
);

export default Routes;

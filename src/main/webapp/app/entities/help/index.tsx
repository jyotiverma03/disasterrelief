import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Help from './help';
import HelpDetail from './help-detail';
import HelpUpdate from './help-update';
import HelpDeleteDialog from './help-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={HelpUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={HelpUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={HelpDetail} />
      <ErrorBoundaryRoute path={match.url} component={Help} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={HelpDeleteDialog} />
  </>
);

export default Routes;

import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UserReward from './user-reward';
import UserRewardDetail from './user-reward-detail';
import UserRewardUpdate from './user-reward-update';
import UserRewardDeleteDialog from './user-reward-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={UserRewardUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={UserRewardUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={UserRewardDetail} />
      <ErrorBoundaryRoute path={match.url} component={UserReward} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={UserRewardDeleteDialog} />
  </>
);

export default Routes;

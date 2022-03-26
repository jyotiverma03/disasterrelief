import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale from './locale';
import authentication from './authentication';
import applicationProfile from './application-profile';

import administration from 'app/modules/administration/administration.reducer';
import userManagement from 'app/modules/administration/user-management/user-management.reducer';
import register from 'app/modules/account/register/register.reducer';
import activate from 'app/modules/account/activate/activate.reducer';
import password from 'app/modules/account/password/password.reducer';
import settings from 'app/modules/account/settings/settings.reducer';
import passwordReset from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import baseModel from 'app/entities/base-model/base-model.reducer';
// prettier-ignore
import assistance from 'app/entities/assistance/assistance.reducer';
// prettier-ignore
import address from 'app/entities/address/address.reducer';
// prettier-ignore
import userReward from 'app/entities/user-reward/user-reward.reducer';
// prettier-ignore
import event from 'app/entities/event/event.reducer';
// prettier-ignore
import notification from 'app/entities/notification/notification.reducer';
// prettier-ignore
import help from 'app/entities/help/help.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const rootReducer = {
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  baseModel,
  assistance,
  address,
  userReward,
  event,
  notification,
  help,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
};

export default rootReducer;

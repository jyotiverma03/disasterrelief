import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './user-reward.reducer';
import { IUserReward } from 'app/shared/model/user-reward.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { BadgeLevel } from 'app/shared/model/enumerations/badge-level.model';

export const UserRewardUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const userRewardEntity = useAppSelector(state => state.userReward.entity);
  const loading = useAppSelector(state => state.userReward.loading);
  const updating = useAppSelector(state => state.userReward.updating);
  const updateSuccess = useAppSelector(state => state.userReward.updateSuccess);
  const badgeLevelValues = Object.keys(BadgeLevel);
  const handleClose = () => {
    props.history.push('/user-reward' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...userRewardEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          badgeLevel: 'SILVER',
          ...userRewardEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="disasterreliefApp.userReward.home.createOrEditLabel" data-cy="UserRewardCreateUpdateHeading">
            <Translate contentKey="disasterreliefApp.userReward.home.createOrEditLabel">Create or edit a UserReward</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="user-reward-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('disasterreliefApp.userReward.rating')}
                id="user-reward-rating"
                name="rating"
                data-cy="rating"
                type="text"
              />
              <ValidatedField
                label={translate('disasterreliefApp.userReward.userId')}
                id="user-reward-userId"
                name="userId"
                data-cy="userId"
                type="text"
              />
              <ValidatedField
                label={translate('disasterreliefApp.userReward.badgeLevel')}
                id="user-reward-badgeLevel"
                name="badgeLevel"
                data-cy="badgeLevel"
                type="select"
              >
                {badgeLevelValues.map(badgeLevel => (
                  <option value={badgeLevel} key={badgeLevel}>
                    {translate('disasterreliefApp.BadgeLevel.' + badgeLevel)}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/user-reward" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default UserRewardUpdate;

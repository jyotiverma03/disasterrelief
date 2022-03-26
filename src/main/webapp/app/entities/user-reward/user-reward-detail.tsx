import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './user-reward.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const UserRewardDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const userRewardEntity = useAppSelector(state => state.userReward.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userRewardDetailsHeading">
          <Translate contentKey="disasterreliefApp.userReward.detail.title">UserReward</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{userRewardEntity.id}</dd>
          <dt>
            <span id="rating">
              <Translate contentKey="disasterreliefApp.userReward.rating">Rating</Translate>
            </span>
          </dt>
          <dd>{userRewardEntity.rating}</dd>
          <dt>
            <span id="userId">
              <Translate contentKey="disasterreliefApp.userReward.userId">User Id</Translate>
            </span>
          </dt>
          <dd>{userRewardEntity.userId}</dd>
          <dt>
            <span id="badgeLevel">
              <Translate contentKey="disasterreliefApp.userReward.badgeLevel">Badge Level</Translate>
            </span>
          </dt>
          <dd>{userRewardEntity.badgeLevel}</dd>
        </dl>
        <Button tag={Link} to="/user-reward" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-reward/${userRewardEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default UserRewardDetail;

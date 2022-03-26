import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './base-model.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BaseModelDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const baseModelEntity = useAppSelector(state => state.baseModel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="baseModelDetailsHeading">
          <Translate contentKey="disasterreliefApp.baseModel.detail.title">BaseModel</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="disasterreliefApp.baseModel.id">Id</Translate>
            </span>
          </dt>
          <dd>{baseModelEntity.id}</dd>
          <dt>
            <span id="create">
              <Translate contentKey="disasterreliefApp.baseModel.create">Create</Translate>
            </span>
          </dt>
          <dd>{baseModelEntity.create}</dd>
          <dt>
            <span id="modified">
              <Translate contentKey="disasterreliefApp.baseModel.modified">Modified</Translate>
            </span>
          </dt>
          <dd>{baseModelEntity.modified}</dd>
          <dt>
            <span id="deleted">
              <Translate contentKey="disasterreliefApp.baseModel.deleted">Deleted</Translate>
            </span>
          </dt>
          <dd>{baseModelEntity.deleted}</dd>
          <dt>
            <span id="updated">
              <Translate contentKey="disasterreliefApp.baseModel.updated">Updated</Translate>
            </span>
          </dt>
          <dd>{baseModelEntity.updated ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/base-model" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/base-model/${baseModelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BaseModelDetail;

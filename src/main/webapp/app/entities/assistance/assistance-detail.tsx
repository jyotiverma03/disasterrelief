import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './assistance.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AssistanceDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const assistanceEntity = useAppSelector(state => state.assistance.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="assistanceDetailsHeading">
          <Translate contentKey="disasterreliefApp.assistance.detail.title">Assistance</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{assistanceEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="disasterreliefApp.assistance.name">Name</Translate>
            </span>
          </dt>
          <dd>{assistanceEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="disasterreliefApp.assistance.description">Description</Translate>
            </span>
          </dt>
          <dd>{assistanceEntity.description}</dd>
          <dt>
            <Translate contentKey="disasterreliefApp.assistance.assistance">Assistance</Translate>
          </dt>
          <dd>{assistanceEntity.assistance ? assistanceEntity.assistance.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/assistance" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/assistance/${assistanceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AssistanceDetail;

import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './help.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const HelpDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const helpEntity = useAppSelector(state => state.help.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="helpDetailsHeading">
          <Translate contentKey="disasterreliefApp.help.detail.title">Help</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{helpEntity.id}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="disasterreliefApp.help.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{helpEntity.firstName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="disasterreliefApp.help.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{helpEntity.lastName}</dd>
          <dt>
            <span id="mobileNo">
              <Translate contentKey="disasterreliefApp.help.mobileNo">Mobile No</Translate>
            </span>
          </dt>
          <dd>{helpEntity.mobileNo}</dd>
          <dt>
            <Translate contentKey="disasterreliefApp.help.helps">Helps</Translate>
          </dt>
          <dd>{helpEntity.helps ? helpEntity.helps.id : ''}</dd>
          <dt>
            <Translate contentKey="disasterreliefApp.help.helps">Helps</Translate>
          </dt>
          <dd>{helpEntity.helps ? helpEntity.helps.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/help" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/help/${helpEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default HelpDetail;

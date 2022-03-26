import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './address.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AddressDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const addressEntity = useAppSelector(state => state.address.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="addressDetailsHeading">
          <Translate contentKey="disasterreliefApp.address.detail.title">Address</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{addressEntity.id}</dd>
          <dt>
            <span id="street1">
              <Translate contentKey="disasterreliefApp.address.street1">Street 1</Translate>
            </span>
          </dt>
          <dd>{addressEntity.street1}</dd>
          <dt>
            <span id="street2">
              <Translate contentKey="disasterreliefApp.address.street2">Street 2</Translate>
            </span>
          </dt>
          <dd>{addressEntity.street2}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="disasterreliefApp.address.city">City</Translate>
            </span>
          </dt>
          <dd>{addressEntity.city}</dd>
          <dt>
            <span id="state">
              <Translate contentKey="disasterreliefApp.address.state">State</Translate>
            </span>
          </dt>
          <dd>{addressEntity.state}</dd>
          <dt>
            <span id="pinCode">
              <Translate contentKey="disasterreliefApp.address.pinCode">Pin Code</Translate>
            </span>
          </dt>
          <dd>{addressEntity.pinCode}</dd>
          <dt>
            <span id="country">
              <Translate contentKey="disasterreliefApp.address.country">Country</Translate>
            </span>
          </dt>
          <dd>{addressEntity.country}</dd>
          <dt>
            <span id="latitude">
              <Translate contentKey="disasterreliefApp.address.latitude">Latitude</Translate>
            </span>
          </dt>
          <dd>{addressEntity.latitude}</dd>
          <dt>
            <span id="longitude">
              <Translate contentKey="disasterreliefApp.address.longitude">Longitude</Translate>
            </span>
          </dt>
          <dd>{addressEntity.longitude}</dd>
        </dl>
        <Button tag={Link} to="/address" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/address/${addressEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AddressDetail;

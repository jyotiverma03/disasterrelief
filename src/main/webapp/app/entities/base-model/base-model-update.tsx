import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './base-model.reducer';
import { IBaseModel } from 'app/shared/model/base-model.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BaseModelUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const baseModelEntity = useAppSelector(state => state.baseModel.entity);
  const loading = useAppSelector(state => state.baseModel.loading);
  const updating = useAppSelector(state => state.baseModel.updating);
  const updateSuccess = useAppSelector(state => state.baseModel.updateSuccess);
  const handleClose = () => {
    props.history.push('/base-model' + props.location.search);
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
      ...baseModelEntity,
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
          ...baseModelEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="disasterreliefApp.baseModel.home.createOrEditLabel" data-cy="BaseModelCreateUpdateHeading">
            <Translate contentKey="disasterreliefApp.baseModel.home.createOrEditLabel">Create or edit a BaseModel</Translate>
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
                  id="base-model-id"
                  label={translate('disasterreliefApp.baseModel.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('disasterreliefApp.baseModel.create')}
                id="base-model-create"
                name="create"
                data-cy="create"
                type="text"
              />
              <ValidatedField
                label={translate('disasterreliefApp.baseModel.modified')}
                id="base-model-modified"
                name="modified"
                data-cy="modified"
                type="text"
              />
              <ValidatedField
                label={translate('disasterreliefApp.baseModel.deleted')}
                id="base-model-deleted"
                name="deleted"
                data-cy="deleted"
                type="text"
              />
              <ValidatedField
                label={translate('disasterreliefApp.baseModel.updated')}
                id="base-model-updated"
                name="updated"
                data-cy="updated"
                check
                type="checkbox"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/base-model" replace color="info">
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

export default BaseModelUpdate;

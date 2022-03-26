package com.clover.disasterrelief.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.clover.disasterrelief.IntegrationTest;
import com.clover.disasterrelief.domain.BaseModel;
import com.clover.disasterrelief.repository.BaseModelRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link BaseModelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BaseModelResourceIT {

    private static final Long DEFAULT_CREATE = 1L;
    private static final Long UPDATED_CREATE = 2L;

    private static final Long DEFAULT_MODIFIED = 1L;
    private static final Long UPDATED_MODIFIED = 2L;

    private static final Long DEFAULT_DELETED = 1L;
    private static final Long UPDATED_DELETED = 2L;

    private static final Boolean DEFAULT_UPDATED = false;
    private static final Boolean UPDATED_UPDATED = true;

    private static final String ENTITY_API_URL = "/api/base-models";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private BaseModelRepository baseModelRepository;

    @Autowired
    private MockMvc restBaseModelMockMvc;

    private BaseModel baseModel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BaseModel createEntity() {
        BaseModel baseModel = new BaseModel()
            .create(DEFAULT_CREATE)
            .modified(DEFAULT_MODIFIED)
            .deleted(DEFAULT_DELETED)
            .updated(DEFAULT_UPDATED);
        return baseModel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BaseModel createUpdatedEntity() {
        BaseModel baseModel = new BaseModel()
            .create(UPDATED_CREATE)
            .modified(UPDATED_MODIFIED)
            .deleted(UPDATED_DELETED)
            .updated(UPDATED_UPDATED);
        return baseModel;
    }

    @BeforeEach
    public void initTest() {
        baseModelRepository.deleteAll();
        baseModel = createEntity();
    }

    @Test
    void createBaseModel() throws Exception {
        int databaseSizeBeforeCreate = baseModelRepository.findAll().size();
        // Create the BaseModel
        restBaseModelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(baseModel)))
            .andExpect(status().isCreated());

        // Validate the BaseModel in the database
        List<BaseModel> baseModelList = baseModelRepository.findAll();
        assertThat(baseModelList).hasSize(databaseSizeBeforeCreate + 1);
        BaseModel testBaseModel = baseModelList.get(baseModelList.size() - 1);
        assertThat(testBaseModel.getCreate()).isEqualTo(DEFAULT_CREATE);
        assertThat(testBaseModel.getModified()).isEqualTo(DEFAULT_MODIFIED);
        assertThat(testBaseModel.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testBaseModel.getUpdated()).isEqualTo(DEFAULT_UPDATED);
    }

    @Test
    void createBaseModelWithExistingId() throws Exception {
        // Create the BaseModel with an existing ID
        baseModel.setId("existing_id");

        int databaseSizeBeforeCreate = baseModelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBaseModelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(baseModel)))
            .andExpect(status().isBadRequest());

        // Validate the BaseModel in the database
        List<BaseModel> baseModelList = baseModelRepository.findAll();
        assertThat(baseModelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllBaseModels() throws Exception {
        // Initialize the database
        baseModel.setId(UUID.randomUUID().toString());
        baseModelRepository.save(baseModel);

        // Get all the baseModelList
        restBaseModelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(baseModel.getId())))
            .andExpect(jsonPath("$.[*].create").value(hasItem(DEFAULT_CREATE.intValue())))
            .andExpect(jsonPath("$.[*].modified").value(hasItem(DEFAULT_MODIFIED.intValue())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.intValue())))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.booleanValue())));
    }

    @Test
    void getBaseModel() throws Exception {
        // Initialize the database
        baseModel.setId(UUID.randomUUID().toString());
        baseModelRepository.save(baseModel);

        // Get the baseModel
        restBaseModelMockMvc
            .perform(get(ENTITY_API_URL_ID, baseModel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(baseModel.getId()))
            .andExpect(jsonPath("$.create").value(DEFAULT_CREATE.intValue()))
            .andExpect(jsonPath("$.modified").value(DEFAULT_MODIFIED.intValue()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.intValue()))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.booleanValue()));
    }

    @Test
    void getNonExistingBaseModel() throws Exception {
        // Get the baseModel
        restBaseModelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewBaseModel() throws Exception {
        // Initialize the database
        baseModel.setId(UUID.randomUUID().toString());
        baseModelRepository.save(baseModel);

        int databaseSizeBeforeUpdate = baseModelRepository.findAll().size();

        // Update the baseModel
        BaseModel updatedBaseModel = baseModelRepository.findById(baseModel.getId()).get();
        updatedBaseModel.create(UPDATED_CREATE).modified(UPDATED_MODIFIED).deleted(UPDATED_DELETED).updated(UPDATED_UPDATED);

        restBaseModelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBaseModel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBaseModel))
            )
            .andExpect(status().isOk());

        // Validate the BaseModel in the database
        List<BaseModel> baseModelList = baseModelRepository.findAll();
        assertThat(baseModelList).hasSize(databaseSizeBeforeUpdate);
        BaseModel testBaseModel = baseModelList.get(baseModelList.size() - 1);
        assertThat(testBaseModel.getCreate()).isEqualTo(UPDATED_CREATE);
        assertThat(testBaseModel.getModified()).isEqualTo(UPDATED_MODIFIED);
        assertThat(testBaseModel.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testBaseModel.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    void putNonExistingBaseModel() throws Exception {
        int databaseSizeBeforeUpdate = baseModelRepository.findAll().size();
        baseModel.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBaseModelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, baseModel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(baseModel))
            )
            .andExpect(status().isBadRequest());

        // Validate the BaseModel in the database
        List<BaseModel> baseModelList = baseModelRepository.findAll();
        assertThat(baseModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchBaseModel() throws Exception {
        int databaseSizeBeforeUpdate = baseModelRepository.findAll().size();
        baseModel.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBaseModelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(baseModel))
            )
            .andExpect(status().isBadRequest());

        // Validate the BaseModel in the database
        List<BaseModel> baseModelList = baseModelRepository.findAll();
        assertThat(baseModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamBaseModel() throws Exception {
        int databaseSizeBeforeUpdate = baseModelRepository.findAll().size();
        baseModel.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBaseModelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(baseModel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BaseModel in the database
        List<BaseModel> baseModelList = baseModelRepository.findAll();
        assertThat(baseModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateBaseModelWithPatch() throws Exception {
        // Initialize the database
        baseModel.setId(UUID.randomUUID().toString());
        baseModelRepository.save(baseModel);

        int databaseSizeBeforeUpdate = baseModelRepository.findAll().size();

        // Update the baseModel using partial update
        BaseModel partialUpdatedBaseModel = new BaseModel();
        partialUpdatedBaseModel.setId(baseModel.getId());

        partialUpdatedBaseModel.modified(UPDATED_MODIFIED).updated(UPDATED_UPDATED);

        restBaseModelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBaseModel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBaseModel))
            )
            .andExpect(status().isOk());

        // Validate the BaseModel in the database
        List<BaseModel> baseModelList = baseModelRepository.findAll();
        assertThat(baseModelList).hasSize(databaseSizeBeforeUpdate);
        BaseModel testBaseModel = baseModelList.get(baseModelList.size() - 1);
        assertThat(testBaseModel.getCreate()).isEqualTo(DEFAULT_CREATE);
        assertThat(testBaseModel.getModified()).isEqualTo(UPDATED_MODIFIED);
        assertThat(testBaseModel.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testBaseModel.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    void fullUpdateBaseModelWithPatch() throws Exception {
        // Initialize the database
        baseModel.setId(UUID.randomUUID().toString());
        baseModelRepository.save(baseModel);

        int databaseSizeBeforeUpdate = baseModelRepository.findAll().size();

        // Update the baseModel using partial update
        BaseModel partialUpdatedBaseModel = new BaseModel();
        partialUpdatedBaseModel.setId(baseModel.getId());

        partialUpdatedBaseModel.create(UPDATED_CREATE).modified(UPDATED_MODIFIED).deleted(UPDATED_DELETED).updated(UPDATED_UPDATED);

        restBaseModelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBaseModel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBaseModel))
            )
            .andExpect(status().isOk());

        // Validate the BaseModel in the database
        List<BaseModel> baseModelList = baseModelRepository.findAll();
        assertThat(baseModelList).hasSize(databaseSizeBeforeUpdate);
        BaseModel testBaseModel = baseModelList.get(baseModelList.size() - 1);
        assertThat(testBaseModel.getCreate()).isEqualTo(UPDATED_CREATE);
        assertThat(testBaseModel.getModified()).isEqualTo(UPDATED_MODIFIED);
        assertThat(testBaseModel.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testBaseModel.getUpdated()).isEqualTo(UPDATED_UPDATED);
    }

    @Test
    void patchNonExistingBaseModel() throws Exception {
        int databaseSizeBeforeUpdate = baseModelRepository.findAll().size();
        baseModel.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBaseModelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, baseModel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(baseModel))
            )
            .andExpect(status().isBadRequest());

        // Validate the BaseModel in the database
        List<BaseModel> baseModelList = baseModelRepository.findAll();
        assertThat(baseModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchBaseModel() throws Exception {
        int databaseSizeBeforeUpdate = baseModelRepository.findAll().size();
        baseModel.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBaseModelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(baseModel))
            )
            .andExpect(status().isBadRequest());

        // Validate the BaseModel in the database
        List<BaseModel> baseModelList = baseModelRepository.findAll();
        assertThat(baseModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamBaseModel() throws Exception {
        int databaseSizeBeforeUpdate = baseModelRepository.findAll().size();
        baseModel.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBaseModelMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(baseModel))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BaseModel in the database
        List<BaseModel> baseModelList = baseModelRepository.findAll();
        assertThat(baseModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteBaseModel() throws Exception {
        // Initialize the database
        baseModel.setId(UUID.randomUUID().toString());
        baseModelRepository.save(baseModel);

        int databaseSizeBeforeDelete = baseModelRepository.findAll().size();

        // Delete the baseModel
        restBaseModelMockMvc
            .perform(delete(ENTITY_API_URL_ID, baseModel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BaseModel> baseModelList = baseModelRepository.findAll();
        assertThat(baseModelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

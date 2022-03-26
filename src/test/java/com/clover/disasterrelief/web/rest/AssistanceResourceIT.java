package com.clover.disasterrelief.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.clover.disasterrelief.IntegrationTest;
import com.clover.disasterrelief.domain.Assistance;
import com.clover.disasterrelief.repository.AssistanceRepository;
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
 * Integration tests for the {@link AssistanceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AssistanceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/assistances";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private AssistanceRepository assistanceRepository;

    @Autowired
    private MockMvc restAssistanceMockMvc;

    private Assistance assistance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Assistance createEntity() {
        Assistance assistance = new Assistance().name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
        return assistance;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Assistance createUpdatedEntity() {
        Assistance assistance = new Assistance().name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        return assistance;
    }

    @BeforeEach
    public void initTest() {
        assistanceRepository.deleteAll();
        assistance = createEntity();
    }

    @Test
    void createAssistance() throws Exception {
        int databaseSizeBeforeCreate = assistanceRepository.findAll().size();
        // Create the Assistance
        restAssistanceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assistance)))
            .andExpect(status().isCreated());

        // Validate the Assistance in the database
        List<Assistance> assistanceList = assistanceRepository.findAll();
        assertThat(assistanceList).hasSize(databaseSizeBeforeCreate + 1);
        Assistance testAssistance = assistanceList.get(assistanceList.size() - 1);
        assertThat(testAssistance.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAssistance.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    void createAssistanceWithExistingId() throws Exception {
        // Create the Assistance with an existing ID
        assistance.setId("existing_id");

        int databaseSizeBeforeCreate = assistanceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssistanceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assistance)))
            .andExpect(status().isBadRequest());

        // Validate the Assistance in the database
        List<Assistance> assistanceList = assistanceRepository.findAll();
        assertThat(assistanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllAssistances() throws Exception {
        // Initialize the database
        assistanceRepository.save(assistance);

        // Get all the assistanceList
        restAssistanceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assistance.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    void getAssistance() throws Exception {
        // Initialize the database
        assistanceRepository.save(assistance);

        // Get the assistance
        restAssistanceMockMvc
            .perform(get(ENTITY_API_URL_ID, assistance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(assistance.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    void getNonExistingAssistance() throws Exception {
        // Get the assistance
        restAssistanceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewAssistance() throws Exception {
        // Initialize the database
        assistanceRepository.save(assistance);

        int databaseSizeBeforeUpdate = assistanceRepository.findAll().size();

        // Update the assistance
        Assistance updatedAssistance = assistanceRepository.findById(assistance.getId()).get();
        updatedAssistance.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restAssistanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAssistance.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAssistance))
            )
            .andExpect(status().isOk());

        // Validate the Assistance in the database
        List<Assistance> assistanceList = assistanceRepository.findAll();
        assertThat(assistanceList).hasSize(databaseSizeBeforeUpdate);
        Assistance testAssistance = assistanceList.get(assistanceList.size() - 1);
        assertThat(testAssistance.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAssistance.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void putNonExistingAssistance() throws Exception {
        int databaseSizeBeforeUpdate = assistanceRepository.findAll().size();
        assistance.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssistanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assistance.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assistance))
            )
            .andExpect(status().isBadRequest());

        // Validate the Assistance in the database
        List<Assistance> assistanceList = assistanceRepository.findAll();
        assertThat(assistanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchAssistance() throws Exception {
        int databaseSizeBeforeUpdate = assistanceRepository.findAll().size();
        assistance.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssistanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assistance))
            )
            .andExpect(status().isBadRequest());

        // Validate the Assistance in the database
        List<Assistance> assistanceList = assistanceRepository.findAll();
        assertThat(assistanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamAssistance() throws Exception {
        int databaseSizeBeforeUpdate = assistanceRepository.findAll().size();
        assistance.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssistanceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assistance)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Assistance in the database
        List<Assistance> assistanceList = assistanceRepository.findAll();
        assertThat(assistanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateAssistanceWithPatch() throws Exception {
        // Initialize the database
        assistanceRepository.save(assistance);

        int databaseSizeBeforeUpdate = assistanceRepository.findAll().size();

        // Update the assistance using partial update
        Assistance partialUpdatedAssistance = new Assistance();
        partialUpdatedAssistance.setId(assistance.getId());

        restAssistanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssistance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAssistance))
            )
            .andExpect(status().isOk());

        // Validate the Assistance in the database
        List<Assistance> assistanceList = assistanceRepository.findAll();
        assertThat(assistanceList).hasSize(databaseSizeBeforeUpdate);
        Assistance testAssistance = assistanceList.get(assistanceList.size() - 1);
        assertThat(testAssistance.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAssistance.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    void fullUpdateAssistanceWithPatch() throws Exception {
        // Initialize the database
        assistanceRepository.save(assistance);

        int databaseSizeBeforeUpdate = assistanceRepository.findAll().size();

        // Update the assistance using partial update
        Assistance partialUpdatedAssistance = new Assistance();
        partialUpdatedAssistance.setId(assistance.getId());

        partialUpdatedAssistance.name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restAssistanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssistance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAssistance))
            )
            .andExpect(status().isOk());

        // Validate the Assistance in the database
        List<Assistance> assistanceList = assistanceRepository.findAll();
        assertThat(assistanceList).hasSize(databaseSizeBeforeUpdate);
        Assistance testAssistance = assistanceList.get(assistanceList.size() - 1);
        assertThat(testAssistance.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAssistance.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void patchNonExistingAssistance() throws Exception {
        int databaseSizeBeforeUpdate = assistanceRepository.findAll().size();
        assistance.setId(UUID.randomUUID().toString());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssistanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, assistance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assistance))
            )
            .andExpect(status().isBadRequest());

        // Validate the Assistance in the database
        List<Assistance> assistanceList = assistanceRepository.findAll();
        assertThat(assistanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchAssistance() throws Exception {
        int databaseSizeBeforeUpdate = assistanceRepository.findAll().size();
        assistance.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssistanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assistance))
            )
            .andExpect(status().isBadRequest());

        // Validate the Assistance in the database
        List<Assistance> assistanceList = assistanceRepository.findAll();
        assertThat(assistanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamAssistance() throws Exception {
        int databaseSizeBeforeUpdate = assistanceRepository.findAll().size();
        assistance.setId(UUID.randomUUID().toString());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssistanceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(assistance))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Assistance in the database
        List<Assistance> assistanceList = assistanceRepository.findAll();
        assertThat(assistanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteAssistance() throws Exception {
        // Initialize the database
        assistanceRepository.save(assistance);

        int databaseSizeBeforeDelete = assistanceRepository.findAll().size();

        // Delete the assistance
        restAssistanceMockMvc
            .perform(delete(ENTITY_API_URL_ID, assistance.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Assistance> assistanceList = assistanceRepository.findAll();
        assertThat(assistanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

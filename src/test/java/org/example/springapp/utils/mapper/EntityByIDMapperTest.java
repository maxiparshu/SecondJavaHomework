package org.example.springapp.utils.mapper;

import lombok.Builder;
import org.example.springapp.exception.ResourceNotFoundException;
import org.example.springapp.service.CRUDService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EntityByIDMapperTest {

    @Mock
    private TestService service;

    @Test
    void testFetchByIdsAllFound() throws ResourceNotFoundException {
        List<Long> ids = List.of(1L, 2L, 3L);
        for (Long id : ids) {
            when(service.getByID(id)).thenReturn(new TestEntity(id));
        }

        List<TestEntity> entities = EntityByIDMapper.fetchByIds(ids, service, "TestEntity");
        assertEquals(3, entities.size());
        assertTrue(entities.stream().allMatch(e -> ids.contains(e.id())));
    }

    @Test
    void testFetchByIdsPartialFailure() {
        List<Long> ids = List.of(1L, 2L, 3L);
        when(service.getByID(1L)).thenReturn(new TestEntity(1L));
        when(service.getByID(2L)).thenThrow(new ResourceNotFoundException("Not found"));
        when(service.getByID(3L)).thenReturn(new TestEntity(3L));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                EntityByIDMapper.fetchByIds(ids, service, "TestEntity"));

        assertTrue(exception.getMessage().contains("TestEntitys with these IDs don't exist: [2]"));
    }

    @Test
    void testFetchByIdsAllNotFound() {
        List<Long> ids = List.of(1L, 2L);
        for (Long id : ids) {
            when(service.getByID(id)).thenThrow(new ResourceNotFoundException("Not found"));
        }

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                EntityByIDMapper.fetchByIds(ids, service, "TestEntity"));

        assertTrue(exception.getMessage().contains("TestEntitys with these IDs don't exist: [1, 2]"));
    }

    @Builder
    private record TestEntity(Long id) {
    }

    private static class TestService implements CRUDService<TestEntity> {
        @Override
        public TestEntity create(TestEntity entity) {
            return null;
        }

        @Override
        public List<TestEntity> read() {
            return null;
        }

        @Override
        public TestEntity getByID(Long id) {
            return TestEntity.builder().id(id).build();
        }


        @Override
        public void update(TestEntity entity) {

        }

        @Override
        public void delete(Long id) {

        }
    }

}
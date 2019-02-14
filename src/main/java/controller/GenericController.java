package controller;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GenericController<Entity> {

    ResponseEntity getAllEntities();
    ResponseEntity getEntityById(String id);
    ResponseEntity createEntity(Entity entity);
    ResponseEntity deleteEntityById(String id);
    ResponseEntity deleteAllEntities();
}

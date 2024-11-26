package com.tecsup.practica.semana15.model.daos;

import com.tecsup.practica.semana15.model.documents.Curso;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;


public interface CursoDao extends ReactiveMongoRepository<Curso ,String> {
}

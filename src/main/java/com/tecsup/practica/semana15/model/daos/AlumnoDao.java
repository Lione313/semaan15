package com.tecsup.practica.semana15.model.daos;

import com.tecsup.practica.semana15.model.documents.Alumno;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;


public interface AlumnoDao extends ReactiveMongoRepository<Alumno ,String> {
}

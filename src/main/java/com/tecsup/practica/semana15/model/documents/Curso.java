package com.tecsup.practica.semana15.model.documents;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cursos")

public record Curso(String id, String nombre, Integer creditos){

}

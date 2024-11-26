package com.tecsup.practica.semana15;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.tecsup.practica.semana15.model.documents.Curso;
import com.tecsup.practica.semana15.model.documents.Alumno;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class Semana15Application implements CommandLineRunner {

    @Autowired
    private ReactiveMongoTemplate template; // Inyectamos ReactiveMongoTemplate

    public static void main(String[] args) {
        SpringApplication.run(Semana15Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Borrar la colección "cursos"
        template.dropCollection(Curso.class).subscribe(
                success -> System.out.println("Colección 'cursos' eliminada"),
                error -> System.err.println("Error eliminando colección: " + error.getMessage())
        );

        // Borrar la colección "alumnos"
        template.dropCollection(Alumno.class).subscribe(
                success -> System.out.println("Colección 'alumnos' eliminada"),
                error -> System.err.println("Error eliminando colección: " + error.getMessage())
        );

        // Insertar cursos
        Flux<Curso> cursos = Flux.just(
                new Curso("C01", "Java 17", 4),
                new Curso("C02", "SPRING BOOT", 5),
                new Curso("C03", "Jenkins", 3),
                new Curso("C04", "Kubernetes", 4),
                new Curso("C05", "Kafka", 3),
                new Curso("C06", "Microservicios", 4),
                new Curso("C07", "Angular", 5),
                new Curso("C08", "TypeScript", 4),
                new Curso("C09", "HTML", 4),
                new Curso("C10", "CSS 3", 1)
        );

        // Insertar alumnos
        Flux<Alumno> alumnos = Flux.just(
                new Alumno("A01", "Juan", "Pérez", 20),
                new Alumno("A02", "María", "García", 22),
                new Alumno("A03", "Pedro", "Ramírez", 21),
                new Alumno("A04", "Ana", "Torres", 19),
                new Alumno("A05", "Luis", "Martínez", 23),
                new Alumno("A06", "Sofía", "López", 20),
                new Alumno("A07", "Carlos", "Gómez", 21),
                new Alumno("A08", "Marta", "Jiménez", 22),
                new Alumno("A09", "José", "Castro", 19),
                new Alumno("A10", "Lucía", "Ruiz", 20)
        );

        // Insertar cursos en la base de datos
        cursos.flatMap(template::insert)
                .subscribe(
                        curso -> System.out.println("Curso insertado: " + curso),
                        error -> System.err.println("Error insertando curso: " + error.getMessage()),
                        () -> System.out.println("Todos los cursos han sido insertados")
                );

        // Insertar alumnos en la base de datos
        alumnos.flatMap(template::insert)
                .subscribe(
                        alumno -> System.out.println("Alumno insertado: " + alumno),
                        error -> System.err.println("Error insertando alumno: " + error.getMessage()),
                        () -> System.out.println("Todos los alumnos han sido insertados")
                );
    }
}

package com.tecsup.practica.semana15.controllers;

import com.tecsup.practica.semana15.model.daos.AlumnoDao;
import com.tecsup.practica.semana15.model.documents.Alumno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Controller
public class AlumnoController {

    @Autowired
    private AlumnoDao dao;

    private static final Logger log = LoggerFactory.getLogger(AlumnoController.class);

    @GetMapping({"/listar-alumnos", "/"})
    public String listarAlumnos(Model model) {

        Flux<Alumno> alumnos = dao.findAll().map(alumno -> {
            // Transformar el nombre a mayúsculas
            return new Alumno(alumno.id(), alumno.nombre().toUpperCase(), alumno.apellido(), alumno.edad());
        });

        alumnos.subscribe(alumno -> log.info(alumno.nombre()));

        model.addAttribute("alumnos", alumnos);
        model.addAttribute("titulo", "Listado de alumnos");
        return "listar-alumnos";
    }

    @GetMapping("/listar-alumnos-datadriver")
    public String listarAlumnosDataDriver(Model model) {

        Flux<Alumno> alumnos = dao.findAll().map(alumno -> {
            // Transformar el nombre a mayúsculas
            return new Alumno(alumno.id(), alumno.nombre().toUpperCase(), alumno.apellido(), alumno.edad());
        }).delayElements(Duration.ofSeconds(2));

        alumnos.subscribe(alumno -> log.info(alumno.nombre()));

        model.addAttribute("alumnos", new ReactiveDataDriverContextVariable(alumnos, 2));
        model.addAttribute("titulo", "Listado de alumnos");
        return "listar-alumnos";
    }

    @GetMapping("/listar-alumnos-full")
    public String listarAlumnosFull(Model model) {

        Flux<Alumno> alumnos = dao.findAll().map(alumno -> {
            // Transformar el nombre a mayúsculas
            return new Alumno(alumno.id(), alumno.nombre().toUpperCase(), alumno.apellido(), alumno.edad());
        }).repeat(5000);

        model.addAttribute("alumnos", alumnos);
        model.addAttribute("titulo", "Listado de alumnos");
        return "listar-alumnos";
    }

    @GetMapping("/listar-alumnos-chunked")
    public String listarAlumnosChunked(Model model) {

        Flux<Alumno> alumnos = dao.findAll().map(alumno -> {
            // Transformar el nombre a mayúsculas
            return new Alumno(alumno.id(), alumno.nombre().toUpperCase(), alumno.apellido(), alumno.edad());
        }).repeat(5000);

        model.addAttribute("alumnos", alumnos);
        model.addAttribute("titulo", "Listado de alumnos");
        return "listar-chunked-alumnos";
    }
}

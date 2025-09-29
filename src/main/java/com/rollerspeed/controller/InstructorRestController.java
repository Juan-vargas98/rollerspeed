package com.rollerspeed.controller;

import com.rollerspeed.model.Instructor;
import com.rollerspeed.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instructores")
public class InstructorRestController {

    @Autowired
    private InstructorRepository instructorRepository;

    // Listar todos los instructores
    @GetMapping
    public List<Instructor> listar() {
        return instructorRepository.findAll();
    }

    // Crear un nuevo instructor
    @PostMapping
    public Instructor crear(@RequestBody Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    // Actualizar un instructor existente
    @PutMapping("/{id}")
    public Instructor actualizar(@PathVariable Long id, @RequestBody Instructor instructor) {
        Instructor existente = instructorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Instructor no encontrado con id: " + id));

        // Actualizar campos (ajusta según tu modelo)
        existente.setNombre(instructor.getNombre());
        existente.setCorreo(instructor.getCorreo());
        // agrega más campos si los tienes

        return instructorRepository.save(existente);
    }

    // Eliminar un instructor
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        instructorRepository.deleteById(id);
    }
}

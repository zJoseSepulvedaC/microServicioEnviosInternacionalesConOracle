package com.example.microservicio_envios.service;

import com.example.microservicio_envios.model.envio;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class envioservice {

    private final List<envio> envios = new ArrayList<>();

    public envioservice() {
        envios.add(new envio(1L, "Carlos Díaz", "Chile", "En tránsito", "Lima"));
        envios.add(new envio(2L, "Laura Méndez", "España", "Pendiente", "Buenos Aires"));
        envios.add(new envio(3L, "John Smith", "EE.UU.", "Entregado", "Nueva York"));
    }

    public List<envio> obtenerTodos() {
        return envios;
    }

    public Optional<envio> obtenerPorId(Long id) {
        return envios.stream().filter(e -> e.getId().equals(id)).findFirst();
    }

    public String consultarUbicacion(Long id) {
        Optional<envio> encontrado = obtenerPorId(id);
        return encontrado.map(envio::getUbicacionActual)
                         .orElse("Envío no encontrado");
    }

    public String actualizarEnvio(Long id, String estado, String ubicacion) {
        Optional<envio> encontrado = obtenerPorId(id);

        if (encontrado.isPresent()) {
            envio e = encontrado.get();
            if (estado != null && !estado.isBlank()) {
                e.setEstado(estado);
            }
            if (ubicacion != null && !ubicacion.isBlank()) {
                e.setUbicacionActual(ubicacion);
            }
            return "Datos actualizados correctamente";
        } else {
            return "Envío no encontrado";
        }
    }
    
    public String registrarEnvio(String destinatario, String paisDestino, String estado, String ubicacionActual) {
        if (destinatario == null || paisDestino == null || estado == null || ubicacionActual == null ||
            destinatario.isBlank() || paisDestino.isBlank() || estado.isBlank() || ubicacionActual.isBlank()) {
            return "Todos los campos son obligatorios.";
        }
    
        Long nuevoId = envios.size() + 1L;
        envio nuevoEnvio = new envio(nuevoId, destinatario, paisDestino, estado, ubicacionActual);
        envios.add(nuevoEnvio);
        return "Envío registrado exitosamente con ID " + nuevoId;
    }
    
    public String eliminarEnvio(Long id) {
        Optional<envio> encontrado = obtenerPorId(id);
        if (encontrado.isPresent()) {
            envios.removeIf(e -> e.getId().equals(id));
            return "Envío eliminado correctamente.";
        } else {
            return "Envío no encontrado.";
        }
    }
    

}

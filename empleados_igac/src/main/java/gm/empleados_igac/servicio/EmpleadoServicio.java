package gm.empleados_igac.servicio;

import gm.empleados_igac.modelo.Empleado;
import gm.empleados_igac.repositorio.EmpleadoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoServicio implements IEmpleadoServicio {

    @Autowired
    private EmpleadoRepositorio empleadoRepositorio;

    @Override
    public List<Empleado> listarLibros() {
        return empleadoRepositorio.findAll();
    }

    @Override
    public Empleado buscarLibroPorId(Integer idLibro) {
        Empleado empleado = empleadoRepositorio.findById(idLibro).orElse(null);
        return empleado;
    }

    @Override
    public void guardarEmpleado(Empleado empleado) {
        empleadoRepositorio.save(empleado);
    }

    @Override
    public void eliminarEmpleado(Empleado empleado) {
        empleadoRepositorio.delete(empleado);
    }
}
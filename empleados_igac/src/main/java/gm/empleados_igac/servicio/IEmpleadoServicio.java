package gm.empleados_igac.servicio;

import gm.empleados_igac.modelo.Empleado;
import java.util.List;


public interface IEmpleadoServicio {
    public List<Empleado> listarLibros();

    public Empleado buscarLibroPorId(Integer idLibro);

    public void guardarEmpleado(Empleado empleado);

    public void eliminarEmpleado(Empleado empleado);
}
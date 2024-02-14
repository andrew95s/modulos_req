package gm.empleados_igac.vista;

import gm.empleados_igac.modelo.Empleado;
import gm.empleados_igac.servicio.EmpleadoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Component
public class EmpleadoForm extends JFrame {
    EmpleadoServicio empleadoServicio;
    private JPanel panel;
    private JTable tablaEmpleados;
    private JTextField idTexto;
    private JTextField empleadoTexto;
    private JTextField tipoTramiteTexto;
    private JTextField cantidadTexto;
    private JTextField radicadoTexto;
    private JButton agregarButton;
    private JButton modificarButton;
    private JButton eliminarButton;
    private JLabel empleadoLabel;
    private DefaultTableModel tablaModeloEmpleados;

    @Autowired
    public EmpleadoForm(EmpleadoServicio empleadoServicio) {
        this.empleadoServicio = empleadoServicio;
        iniciarForma();
        agregarButton.addActionListener(e -> agregarEmpleado());
        tablaEmpleados.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarEmpleadoSeleccionado();
            }
        });
        modificarButton.addActionListener(e-> modificarEmpleado());
        eliminarButton.addActionListener(e-> eliminarEmpleado());
    }

    private void iniciarForma() {
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(900, 700);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension tamanioPantalla = toolkit.getScreenSize();
        int x = (tamanioPantalla.width - getWidth() / 2);
        int y = (tamanioPantalla.height = getHeight() / 2);
        setLocation(x, y);
    }

    private void agregarEmpleado() {
        // Leer los valores del formulario
        if (empleadoTexto.getText().equals("")) {
            mostrarMensaje("Proporciona el nombre del Empleado");
            empleadoTexto.requestFocusInWindow();
            return;
        }
        var nombreEmpleado = empleadoTexto.getText();
        var tipoTramite = tipoTramiteTexto.getText();
        var cantidad = Double.parseDouble(cantidadTexto.getText());
        var radicado = Integer.parseInt(radicadoTexto.getText());
        // Crear el objeto Empleado
        var empleado = new Empleado(null, nombreEmpleado, tipoTramite, cantidad, radicado);

        this.empleadoServicio.guardarEmpleado(empleado);
        mostrarMensaje("Se agrego el Empleado...");
        limpiarFomulario();
        listarEmpleados();
    }

    private void cargarEmpleadoSeleccionado() {
        // Los indices de las columnas inician en 0
        var renglon = tablaEmpleados.getSelectedRow();
        if (renglon != -1) { //Regresa -1 si no se selecciono ningun registro
            String idEmpleado =
                    tablaEmpleados.getModel().getValueAt(renglon, 0).toString();
            idTexto.setText(idEmpleado);
            String nombreEmpleado =
                    tablaEmpleados.getModel().getValueAt(renglon, 1).toString();
            empleadoTexto.setText(nombreEmpleado);
            String tipoTramite =
                    tablaEmpleados.getModel().getValueAt(renglon, 2).toString();
            tipoTramiteTexto.setText(tipoTramite);
            String radicado =
                    tablaEmpleados.getModel().getValueAt(renglon, 3).toString();
            cantidadTexto.setText(radicado);
            String cantidad =
                    tablaEmpleados.getModel().getValueAt(renglon, 4).toString();
            radicadoTexto.setText(cantidad);
        }
    }

    private void modificarEmpleado(){
        if(this.idTexto.getText().equals("")){
            mostrarMensaje("Debe seleccionar un registro...");
        }
        else{
            // Verificamos que nombre del Empleado no sea nulo
            if(empleadoTexto.getText().equals("")){
                mostrarMensaje("Proporciona el nombre del Empleado...");
                empleadoTexto.requestFocusInWindow();
                return;
            }
            // Llenamos el objeto Empleado a actualizar
            int idEmpleado = Integer.parseInt(idTexto.getText());
            var nombreEmpleado = empleadoTexto.getText();
            var tipoTramite = tipoTramiteTexto.getText();
            var cantidad = Double.parseDouble(cantidadTexto.getText());
            var radicado = Integer.parseInt(radicadoTexto.getText());
            var empleado =
                    new Empleado(idEmpleado, nombreEmpleado, tipoTramite, cantidad, radicado);
            empleadoServicio.guardarEmpleado(empleado);
            mostrarMensaje("Se modifico el Empleado...");
            limpiarFomulario();
            listarEmpleados();
        }
    }

    private void eliminarEmpleado(){
        var renglon = tablaEmpleados.getSelectedRow();
        if(renglon != -1){
            String idEmpleado =
                    tablaEmpleados.getModel().getValueAt(renglon, 0).toString();
            var empleado = new Empleado();
            empleado.setIdEmpleado(Integer.parseInt(idEmpleado));
            empleadoServicio.eliminarEmpleado(empleado);
            mostrarMensaje("Empleado " + idEmpleado + " eliminado.");
            limpiarFomulario();
            listarEmpleados();
        }
        else{
            mostrarMensaje("No se ha seleccionado ningun Empleado a eliminar...");
        }
    }

    private void limpiarFomulario() {
        empleadoTexto.setText("");
        tipoTramiteTexto.setText("");
        cantidadTexto.setText("");
        radicadoTexto.setText("");
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        // Creamos el elemento idTexto oculto
        idTexto = new JTextField("");
        idTexto.setVisible(false);

        this.tablaModeloEmpleados = new DefaultTableModel(0, 5){
            @Override
            public boolean isCellEditable(int row, int column){return false;}
        };

        String[] cabeceros = {"Id", "Empleado", "Tipo Tramite", "Cantidad", "Radicado"};
        this.tablaModeloEmpleados.setColumnIdentifiers(cabeceros);
        // Intanciar el objeto JTable
        this.tablaEmpleados = new JTable(tablaModeloEmpleados);
        // Evitar que se seleccionen varios registros
        tablaEmpleados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listarEmpleados();
    }

    private void listarEmpleados() {
        // Limpiar la tabla
        tablaModeloEmpleados.setRowCount(0);
        // Obtener los Empleados
        var empleados = empleadoServicio.listarLibros();
        empleados.forEach((empleado) -> {
            Object[] renglonLibro = {
                    empleado.getIdEmpleado(),
                    empleado.getNombreEmpleado(),
                    empleado.getTipoTramite(),
                    empleado.getCantidad(),
                    empleado.getRadicado()
            };
            this.tablaModeloEmpleados.addRow(renglonLibro);
        });
    }

}
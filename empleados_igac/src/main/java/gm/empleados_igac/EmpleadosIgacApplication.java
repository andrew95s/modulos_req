package gm.empleados_igac;
import gm.empleados_igac.vista.EmpleadoForm;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import java.awt.*;
@SpringBootApplication
public class EmpleadosIgacApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext contextoSpring =
				new SpringApplicationBuilder(EmpleadosIgacApplication.class)
						.headless(false)
						.web(WebApplicationType.NONE)
						.run(args);
		// Ejecutamos el codigo para cargar el formulario
		EventQueue.invokeLater(()->{
			// Obtenemos el objeto form a traves de Spring
			EmpleadoForm empleadoForm = contextoSpring.getBean(EmpleadoForm.class);
			empleadoForm.setVisible(true);
		});
	}


}






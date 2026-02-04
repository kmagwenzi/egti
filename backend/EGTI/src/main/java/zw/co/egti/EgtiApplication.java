package zw.co.egti;

import java.io.File;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zw.co.egti.controllers.ApplicationController;

@SpringBootApplication
public class EgtiApplication {

	public static void main(String[] args) {
                boolean status = new File(ApplicationController.uploadDirectory).mkdir();
		
                System.out.println("status = " + status);
                
                System.out.println("ApplicationController.uploadDirectory" + ApplicationController.uploadDirectory);
                
                SpringApplication.run(EgtiApplication.class, args);
	}

}
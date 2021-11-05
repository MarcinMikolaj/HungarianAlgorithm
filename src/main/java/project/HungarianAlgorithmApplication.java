package project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import project.algorithms.Permutation;

@SpringBootApplication
public class HungarianAlgorithmApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(HungarianAlgorithmApplication.class, args);
		Service service = context.getBean(Service.class);
		service.test1();	
		context.close();
		
		//int[] t = {1,2,3};
		//Permutation p = new Permutation();
		//p.printAllRecursive(3, t, 'a');
	}

}

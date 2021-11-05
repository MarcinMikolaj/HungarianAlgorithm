package project;

import org.springframework.stereotype.Component;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import project.algorithms.HungarianAlgorithm;
import project.matrix.MatrixManager;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class Service {
	
	private HungarianAlgorithm hungarianAlgorithm;
	private MatrixManager matrixManager;
	
	@Autowired
	public Service(HungarianAlgorithm hungarianAlgorithm, MatrixManager matrixManager) {
		this.hungarianAlgorithm = hungarianAlgorithm;
		this.matrixManager = matrixManager;
		
	}
	
	public void test() {
		
		int[][] matrix = matrixManager.genrateRandom(4, 100, 0);
		hungarianAlgorithm.algorithm(matrix);
			
	}
	
	public void test1() {
		
		try {
			int[][] matrix = matrixManager.generateWithFile("test2.txt");
			hungarianAlgorithm.algorithm(matrix);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}

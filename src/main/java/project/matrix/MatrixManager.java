package project.matrix;

import org.springframework.stereotype.Component;

import project.pojo.ConsoleColors;

import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class MatrixManager {
	
	private static final Random random = new Random();

	//Zwraca utworzoną macierz utworzoną na podctawie wylosowanych liczb z przedziału
	public int[][] genrateRandom(int n, int max, int min){
		
		if(max < min)
			throw new IllegalArgumentException("The maximum value: " + max + " cannot be less than the minimum value: " + min);
		
		int[][] matrix = new int[n][n];
		
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) { 
				matrix[i][j] = random.nextInt((max-min)+1)+min;
			}
		}
		
		return matrix;
	}
	
	
	//Umożliwia utworzenie macierzy na podctawie przkazanego pliku
	public int[][] generateWithFile(String fileName) throws IOException{
			
		    int p = 0;
		    int m = 0;
			
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String readOneLineFromFile = bufferedReader.readLine();
			
			//Utworzenie macierzy
			for(int i=0;i<2;i++) {
				if(i==0)
					p =  Integer.parseInt(readOneLineFromFile);
				
				if(i==1)
					m =  Integer.parseInt(readOneLineFromFile);
					
				readOneLineFromFile = bufferedReader.readLine();
			}
				
			int[][] matrix = new int[p][m];
			
			//Wczytywanie wartości z pliku do macierzy
			for(int i=0;i<p;i++) {
				for(int j=0;j<m;j++) { 
					if(readOneLineFromFile != null){
						matrix[i][j] = Integer.parseInt(readOneLineFromFile); 
						readOneLineFromFile = bufferedReader.readLine();	
					}else {
						throw new IOException("Nie udało sie wczytać danych do macierzy");
					}
					
				}
			}
			

			bufferedReader.close();	
			return matrix;
	}
	
	
	
	//Umożliwia wydrukowanie przekazanej macierzy w konsoli, przedctawiając ją w czytelniej formie
	public void printMatrix(int[][] matrix) {
		
		int length = matrix.length;
		
		if(matrix == null || length <=0)
			throw new IllegalArgumentException("the matrix passed is invalid ");
			
		System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "--------MACIERZ--------" + ConsoleColors.RESET);
		
		for(int columns = 0;columns<=length;columns++) {
			if(columns==0)
				System.out.print(ConsoleColors.GREEN_BACKGROUND + "P/M" + ConsoleColors.RESET + " " );
			else
				System.out.print(ConsoleColors.PURPLE_BACKGROUND + "M" + columns + "   " + ConsoleColors.RESET);		
		}
		
		for(int i=0;i<length;i++) {
			System.out.print("\n");
			for(int j=0;j<length;j++) {
				
				if(j==0) {
					System.out.print(ConsoleColors.BLUE_BACKGROUND + "P" + i + ConsoleColors.RESET + "  ");
				}
				
				System.out.print(ConsoleColors.GREEN_UNDERLINED + matrix[i][j] + " ");
				addSpace(matrix[i][j]);				
			}
		}
	}
	
	//Dodaje spacje w zależności ile cyfer zawiera dana liczba.
	//np na 83=2x, 2=1x, 2343=4x
	private boolean addSpace(int value) {
		
		int c = String.valueOf(value).length();
		
		if(value== 0) {
			System.out.print("   ");
		    return false;
	}
		
		if(value>=100 && value<=1000) {
			System.out.print(" ");
		    return false;
	}
		

		if(value>=10 && value<=100) {
			System.out.print("  ");
		    return false;
	}
		
	
		if(value>=1 && value<=10) {
			System.out.print("   ");
			return false;
	}
	
			
		
		return true;	
	}
	

	public int[][] getClone(int[][] matrix){
		
		int n = matrix.length;
	    int[][] clone = new int[n][n];
		
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {		
				clone[i][j] = matrix[i][j];			
			}	
		}	
		return clone;
	}
	
	
}

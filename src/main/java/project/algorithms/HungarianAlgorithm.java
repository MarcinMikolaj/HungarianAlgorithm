package project.algorithms;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.List;
import java.util.ArrayList;

import project.matrix.MatrixManager;
import project.pojo.ConsoleColors;
import project.pojo.NW;
import project.pojo.WK;
import project.NWComparator;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class HungarianAlgorithm {
	
	private MatrixManager matrixManager;
	
	@Autowired
	public HungarianAlgorithm(MatrixManager matrixManager) {
		this.matrixManager = matrixManager;
	}
	
	//Zwraca wartość funkcji celu oraz wyświetla macierz w postaci zero jedynkowej po 
	//wykonaniu algorytmu węgierskiego
	public int algorithm(int[][] matrix){
		
		int n = matrix.length;		
		int[][] copy = matrixManager.getClone(matrix);
								
		if(matrix == null || n <= 0)
			throw new IllegalArgumentException("the given argument is an invalid matrix");
		
		
		System.out.println(ConsoleColors.BLACK_BRIGHT + "(Macierz przekazana do algorytmu)" + ConsoleColors.RESET);
		matrixManager.printMatrix(matrix);
		System.out.println(" ");
		
		
		subtractOnEachLine(matrix);		
		System.out.println(ConsoleColors.BLACK_BRIGHT + "(Macierz po odjęciu najmniejszej wartości dla wierszy)" + ConsoleColors.RESET);
		matrixManager.printMatrix(matrix);
		System.out.println(" ");
		
		
		subtractInEachColumn(matrix);	
		System.out.println(ConsoleColors.BLACK_BRIGHT + "(Macierz po odjęciu najmniejszej wartości dla kolumn)" + ConsoleColors.RESET);
		matrixManager.printMatrix(matrix);
		System.out.println(" ");
		
		WK[] independentZeros = findingIndependentZeros(matrix);
		System.out.println(ConsoleColors.BLACK_BRIGHT + "\n" + "Liczba znalezionych zer niezależnych:" + size(independentZeros) + ConsoleColors.RESET);
		
		if(size(independentZeros) < n) {
			increasingTheNumberOfIndependentZeros(matrix, independentZeros);
			independentZeros = findingIndependentZeros(matrix);
			System.out.println(ConsoleColors.BLACK_BRIGHT + "\n" + "Liczba znalezionych zer niezależnych:" + size(independentZeros) + ConsoleColors.RESET);
		}
		
		createZeroOneMatrix(matrix, independentZeros);
		System.out.println(ConsoleColors.BLACK_BRIGHT + "(Utworzenie macierzy zero jedynkowej)" + ConsoleColors.RESET);
		matrixManager.printMatrix(matrix);
	
		
		int x = determineTheValue(copy, matrix);
		System.out.println("\n\n" + ConsoleColors.RED_BOLD + "Wartość funkcji celu wynosi = " + x + ConsoleColors.RESET);
			
		test(matrix);
		
		return x;
	}
	
	
	//Umożliwia znalezienie wszystkich zer niezaleznych i zwraca tablice z obiektem wskazującym
	//na wiersz i komurkę pod którą ukryte jest zero
	//atrybut show umożliwia wyświetlenie wyniku w konsoli
	private WK[] findingIndependentZeros(int[][] matrix) {
				
		int n = matrix.length;
		int c = 0;	
		boolean key = true;
	
		WK[] independentZeros = new WK[n*2];
		
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {		
				
				if(matrix[i][j] == 0) {
					 key = true;
						
					if(isEmpty(independentZeros) == false) {
						for(int k=0;k<c;k++) {
							
							if(independentZeros[k].getW() == i || independentZeros[k].getK() == j) {								
								key = false;
								break;		
							}			
						}
						
						if(key == true) {	
							independentZeros[c] = new WK(i,j);						
							c++;
						}					
					}
					else {
						independentZeros[c] =new WK(i,j);						
						c++;
					}
				}
							
			}	
		}	
		
		
		return independentZeros;
			
	}
	
	
	public WK[] test(int[][] matrix) {
		
		int n = matrix.length;
		int c = 0;
		WK[] independentZeros = new WK[100];
		
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {			
				if(matrix[i][j] == 0) {
					independentZeros[c] = new WK(j,j);
					c++;
				}
			}	
		}
		
		int[] t = {1,2,3};
		Permutation<WK> p = new Permutation<WK>();
		p.printAllRecursive(5, independentZeros, 'a');
		
		
			
		//Collections.shuffle(independentZeros);
		
		return null;
		
	}
	
	
    //Zwiększa liczbę zer niezależnych w macierzy
	private int[][] increasingTheNumberOfIndependentZeros(int[][] matrix, WK[] independentZeros) {
		
		int min = 10000;
		int l = matrix.length;
		NW[] k = new NW[l*2];
		int independentZerosRegister = size(independentZeros);
		
		int zero = 0;
		
	        //Wyznaczanie liczby zer w wierszach
			for(int i=0;i<l;i++) {	
					
				for(int j=0;j<l;j++) {
					if(matrix[i][j] == 0)
						zero++;
			    }
				k[i] = new NW("W", i, zero);		
				zero = 0;
			}
			
			
			//Wyznaczanie liczby zer w kolumnach
			for(int i=0;i<l;i++) {	
				
				for(int j=0;j<l;j++) {
					if(matrix[j][i] == 0)
						zero++;
			    }	
				k[i+l] = new NW("K", i, zero);		
				zero = 0;
			}
				
			Arrays.sort(k, new NWComparator());
			
			System.out.println(ConsoleColors.BLACK_BRIGHT + "Wyznaczeni kandydaci do skreślenia:" + ConsoleColors.RESET);
			printArray(k);
           
			
			NW[] skreslone = new NW[independentZerosRegister];
			for(int c=0; c<independentZerosRegister;c++) {
				skreslone[c] = k[c];
			}
			
			
			System.out.println(ConsoleColors.BLACK_BRIGHT + "Wiersze i kolumny skreślone" + ConsoleColors.RESET);
			printArray(skreslone);
			
			
			//Wyznaczanie wartości minimalnej
			for(int i=0;i<l;i++) {
				for(int j=0;j<l;j++) {		
					boolean key = true;
					
					for(int w=0; w< skreslone.length;w++) {
						if(i == skreslone[w].getRowOrColumnNumber() && skreslone[w].getName().equals("W")) {
							key = false;
							break;
						}
					}
					
					for(int w=0; w< skreslone.length;w++) {
						if(j == skreslone[w].getRowOrColumnNumber() && skreslone[w].getName().equals("K")) {
							key = false;
							break;
						}
					}
					
					if(min > matrix[i][j] && key == true) {
						min = matrix[i][j];
					}
						
				
					}	
			}//FOR WYZNACZANIE WAROŚCI MINIMALNEJ
			
			
			System.out.println("Wyznaczona wartość minimalna:" + min);
			
			System.out.println(ConsoleColors.BLACK_BRIGHT + "(Po odjęciu wartości minimalnej dla nieskreslonych kolumn, wierszy i dodaniu wartości dla podwójnie skreślonych)" + ConsoleColors.RESET);
			int[][] m = subtract(matrix, min, skreslone);
			m = addValueForDoubleStrikethrough(m, min, skreslone);
			matrixManager.printMatrix(m);
					
	   return matrix; 	
	}
	
	
	
	//Umozliwia odjęcie danej wartości dla nie wykluczonych komurek macierzy
	//Przekazywane argumenty to macierz na której wykonane mają być działania,
	//wartość która zostanie wykorzystana przy odejmowaniu,
	//tablica zawierająca wiersze i kolumny które mają być wykluczone
	private int[][] subtract(int[][] matrix, int value, NW[] skreslone) {
		
		int n = matrix.length;
		boolean key = true;
		
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {
				key = true;
				
				for(int s=0; s<skreslone.length; s++) {
					
					if(i == skreslone[s].getRowOrColumnNumber() && skreslone[s].getName().equals("W")) {
						key = false;
						break;
					}
					
					if(j == skreslone[s].getRowOrColumnNumber() && skreslone[s].getName().equals("K")) {
						key = false;
						break;
					}		
				}
				
				if(key == true)
					matrix[i][j] = matrix[i][j] - value;						
			}
		}
			
				
		return matrix;
	}
	
	
	
	//Funkcja umożliwia dodanie określonej wartości dla skreślonych podwójnie
	//komórek macierzy.
	//Przekazywane argumenty to macierz na której wykonane mają być działania,
    //wartość która zostanie wykorzystana przy dodawaniu,
	//tablica zawierająca wiersze i kolumny wskazujące na skreślenia
	private int[][] addValueForDoubleStrikethrough(int[][] matrix, int value, NW[] skreslone){
		
		int n = matrix.length;
		boolean key1 = false;
		boolean key2 = false;
		
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {
				
				key1 = false;
				key2 = false;
					
				for(int s=0; s<skreslone.length; s++) {
					
					if(i == skreslone[s].getRowOrColumnNumber() && skreslone[s].getName().equals("W")) {
						key2 = true;							
					}
					
					
					if(j == skreslone[s].getRowOrColumnNumber() && skreslone[s].getName().equals("K")) {				
						key1 = true;	
						
					}
					
				}
				
				if(key1 == true && key2 == true)				
					matrix[i][j] = matrix[i][j] + value;	
									
			}
		}
		
		return matrix;
	}
	
	//Funkacja umożliwia utworzenie macierzy zero/jedynkowej w opraciu o
	//przekazaną macierz oraz tablice wskazującą na komórki z zerami niezależnymi
	private int[][] createZeroOneMatrix(int[][] matrix, WK[] independentZeros){
		
		int n = matrix.length;
		boolean isOne = false;
		
		if(n != size(independentZeros)) {
			System.out.println("error: wywołanie metody dla niepoprawnych argumentów (createZeroOneMatrix)");		
			return null;
		}
		
		for(int i=0;i<n;i++) {	
			for(int j=0;j<n;j++) {
				
				isOne = false;
				
				for(int c=0; c<size(independentZeros); c++) {
					if(i == independentZeros[c].getW() && j == independentZeros[c].getK()) {
						isOne = true;
						break;
					}
				}
				
				if(isOne)
					matrix[i][j] = 1;
				else
					matrix[i][j] = 0;			
			}					
		}	
		return matrix;
	}
	

	//Zwraca wartość minimalną dla każdego wiersza danej macierzy
	private int[] getTheMinimumValueFromTtheRows(int[][] matrix) {
		
		int n = matrix.length;
		int[] min = new int[n];
		
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {
				
				if(j==0)
					min[i] = matrix[i][j];
				
				if(min[i] > matrix[i][j])
					min[i] = matrix[i][j];
				}	
		}
		return min;
	}
	
	
	//Zwraca wartość minimalną dla każdej kolumny danej macierzy
	private int[] getTheMinimumValueFromTtheColumn(int[][] matrix) {
		
		int n = matrix.length;
		int[] min = new int[n];
		
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {
				
				if(j==0)
					min[i] = matrix[j][i];
				
				if(min[i] > matrix[j][i])
					min[i] = matrix[j][i];
				}	
		}
		
		return min;
		
	}
	
	//Umożliwia odjęcie określonej wartości dla każdej komórki w każdej kolumnie
	private int[][] subtractInEachColumn(int[][] matrix){
			
		int[] theMinimumValueFromTtheColumns = getTheMinimumValueFromTtheColumn(matrix);;
		int[][] result = matrix;
		int n = matrix.length;
		
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {		
				result[j][i] = result[j][i] - theMinimumValueFromTtheColumns[i];							
			}	
		}	
		return result;
	}
	
	
	//Umożliwia odjęcie określonej wartości dla każdej komórki w każdym wierszu
	private int[][] subtractOnEachLine (int[][] matrix){
		
		int[] theMinimumValueFromTtheRows  = getTheMinimumValueFromTtheRows(matrix);
		int[][] result = matrix;
		int n = matrix.length;
		
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {		
				result[i][j] = matrix[i][j] - theMinimumValueFromTtheRows[i];							
			}
			
		}
		return result;
	}
	
	//Umożliwia zestawinie ze sobą macierzy kosztów z wyznaczoną macierzą zero jedynkową wskazującą na zera niezależne
	//Zwraca wartość funkcji celu dla macierzy
	private int determineTheValue(int[][] matrix, int[][] result) {
		
		int n = matrix.length;
		int x = 0;
		
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {		
				if(result[i][j] == 1) 
					x = x + matrix[i][j];						
			}	
		}	
		return x;
	}
	
	
	public void printArray(int[] array) {
		for(int i=0;i<array.length;i++) {
			System.out.println(array[i]);
		}
	}
	
	public void printArray(WK[] array) {
		for(int i=0;i<array.length;i++) {
			System.out.println(array[i]);
		}
	}
	
	private void printArray(NW[] array) {
		for(int i=0;i<array.length;i++) {
			System.out.println(array[i]);
		}
	}
	
	//Sprawdza czy tablica zawiera przynajmniej jeden element
	private boolean isEmpty(WK[] array) {

		for(int i=0;i<array.length; i++) {
			if(array[i] != null) {
				return false;
			}		
		}
		return true;
	}
	
	//Zwaraca rozmiar tablicy nie zliczając elementów null
	private int size(WK[] array) {	
		int counter = 0;	
		for(int i=0; i<array.length;i++) {
			if(array[i] != null)
				counter++;
				
		}
		return counter;
	}
		
}

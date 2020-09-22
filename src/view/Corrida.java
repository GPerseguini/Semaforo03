package view;
import java.util.concurrent.Semaphore;
import controller.ThreadCarro;
import controller.Piloto;

public class Corrida {
	public static void main (String[] args){
		Semaphore[] semaforoEquipe = new Semaphore[7];
		Semaphore semaforo = new Semaphore(5);
		int carro=0;
		Piloto[] piloto = new Piloto[14];
		for (int i=0;i<7;i++){

			semaforoEquipe[i]= new Semaphore(1);	
			piloto[i] = new Piloto();
			piloto[i].setCarro(carro);
			piloto[i].setEquipe(i);
			Thread thread = new ThreadCarro (piloto,carro,i, semaforoEquipe,semaforo);
			thread.start();
			carro++;
			
			semaforoEquipe[i]= new Semaphore(1);	
			piloto[i+7] = new Piloto();
			piloto[i+7].setCarro(carro);
			piloto[i+7].setEquipe(i);
			Thread thread2 = new ThreadCarro (piloto,carro,i, semaforoEquipe,semaforo);
			thread2.start();
			carro++;
		}

		
	} 

}


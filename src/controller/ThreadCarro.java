package controller;

import controller.Piloto;

import java.util.Random;
import java.util.concurrent.Semaphore;
public class ThreadCarro extends Thread {
	private Piloto[] piloto;
	private Semaphore[] semaforoEquipe;
	private Semaphore semaforo;
	private int carro;
	private int equipe;
	private double[] tempo;
	private Piloto aux;
	private static int contCarro;
	public ThreadCarro (Piloto[] piloto,int carro,int equipe, Semaphore[] semaforoEquipe, Semaphore semaforo){
		this.piloto = piloto;
		this.semaforoEquipe = semaforoEquipe;
		this.semaforo = semaforo;
		this.carro = carro;
		this.equipe = equipe;
		tempo = new double [14];
		contCarro = 0;
		
	}
	public void run(){
		try{
			semaforoEquipe[equipe].acquire();
			semaforo.acquire();
			fazerVolta();
			grid();
		}catch (InterruptedException e) {
			
		}finally{
			semaforo.release();
			semaforoEquipe[equipe].release();
		}	
	}
	public void fazerVolta(){
		int pista = 1000;
		double melhorTempo=0;
		int distanciaPercorrida=0,velocidade;
		double tempo,tempoInicial, tempoFinal;
		Random random = new Random();
		for (int volta=0;volta<3;volta++){
			tempo = 0;
			distanciaPercorrida=0;
			velocidade = random.nextInt(100)+50;
			tempoInicial = System.nanoTime();
			while (distanciaPercorrida < pista){
				distanciaPercorrida+= velocidade;
				try {
					Thread.sleep(velocidade*10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			tempoFinal = System.nanoTime();
			tempo = tempoFinal - tempoInicial;
			tempo = tempo / Math.pow(10, 9);
			System.out.println("O carro "+piloto[carro].getCarro()+" da equipe 0"+piloto[carro].getEquipe()+" terminou a "+volta+" volta em "+tempo+" segundos.");
			melhorTempo = piloto[carro].getTempo();
			if (melhorTempo == 0 || tempo < melhorTempo){
				melhorTempo = tempo;
				piloto[carro].setTempo(tempo);
			}
		}
	}
	public void grid(){
		contCarro++;
		if (contCarro >= 14){
		for (int i=0;i<piloto.length;i++){
			for (int j=0;j<piloto.length-1 ;j++){
				if (piloto[j].getTempo() > piloto[j+1].getTempo()){
					aux = piloto[j];
					piloto[j] = piloto[j+1];
					piloto[j+1] = aux;
				}
			}
		}
		for (int i=0;i<piloto.length;i++){
			System.out.println((i+1)+"˚Posição: Carro "+piloto[i].getCarro()+" da equipe 0"+piloto[i].getEquipe()+ " tempo:"+piloto[i].getTempo());
		}
	}
	}
}
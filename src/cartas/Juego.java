package cartas;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Juego {

	public static void main(String[] args) {
		
		char[] palo = { '♣', '♠', '♥', '♦' };
		// char[] palo = { "\u2661".charAt(0), "\u2662".charAt(0), "\u2664".charAt(0), "\u2667".charAt(0) };
		char[] valor = { '1', '2', '3', '4', '5', '6', '7', '8', '9', 'J', 'Q', 'K' };
		double puntosJugador = 0, puntosMaquina = 0;
		String color = "";

		Deque<Carta> baraja = new LinkedList<Carta>();

		for (char p : palo) {
			for (char v : valor) {
				if (p == '♥' || p == '♦')
					color = Carta.ROJO;
				else
					color = Carta.NEGRO;
				baraja.add(new Carta(v, p, color));
			}
		}

		Collections.shuffle((List<?>) baraja);

		System.out.println((new i2ascii("black.jpg")).ConvertToAscii());

		puntosJugador = jugador(baraja);

		System.out.println("\n\nAhora me toca a mi.... preparate a perder");
		puntosMaquina = maquina(puntosJugador, baraja);

		if (puntosJugador == 0) {
			System.out.println("Has perdido!\n");
		} else if (puntosMaquina == 0) {
			System.out.println("Has ganado !!!\n");
		} else if (puntosJugador > puntosMaquina) {
			System.out.println("Has ganado !!!\n");
		} else {
			System.out.println("Has perdido!\n");
		}

		System.out.println((new i2ascii("gameover.jpg")).ConvertToAscii());
		emitirSonido("game-over.wav");

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public static double maquina(double puntosJugador, Deque<Carta> baraja) {
		double puntosMaquina = 0;
		List<Carta> mano = new LinkedList<Carta>();

		while (puntosMaquina < puntosJugador) {
			System.out.println("Quiero otra carta \u261D");
			mano.add(baraja.pop());
			puntosMaquina = mano.stream().collect(Collectors.summingDouble(Carta::getValorJuego));
			for (Carta c : mano)
				System.out.print(c + "  ");

			System.out.println("Puntos: " + puntosMaquina);

			if (puntosMaquina > 21) {
				System.out.println("Me he pasado \u2639");
				emitirSonido("fallo.wav");
			} else {
				emitirSonido("acierto.wav");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
		if (puntosMaquina <= 21) {
			System.out.println("Me planto \u263A");

		}

		return (puntosMaquina > 21) ? 0 : puntosMaquina;
	}

	public static double jugador(Deque<Carta> baraja) {
		List<Carta> mano = new ArrayList<Carta>();
		double puntos = 0;
		char opcion;
		Scanner entrada = new Scanner(System.in);
		System.out.println("Quieres carta? [s/n]");
		opcion = entrada.nextLine().charAt(0);

		while (puntos < 21 && opcion != 'n' && opcion != 'N') {

			mano.add(baraja.pop());
			puntos = mano.stream().collect(Collectors.summingDouble(Carta::getValorJuego));
			for (Carta c : mano)
				System.out.print(c + "  ");

			System.out.println("Puntos: " + puntos);

			if (puntos > 21) {
				System.out.println("Te has pasado \u2639");
				emitirSonido("fallo.wav");
			} else {
				emitirSonido("acierto.wav");
				System.out.println("Quieres otra carta? [s/n]");
				opcion = entrada.nextLine().charAt(0);
			}
		}
		if (opcion == 'n')
			System.out.println("La siguiente carta era ..." + baraja.peek());

		entrada.close();
		return (puntos > 21) ? 0 : puntos;
	}

	public static void emitirSonido(String f) {

		Clip sonido;

		try {
			sonido = AudioSystem.getClip(null);

			AudioInputStream ais;
			ais = AudioSystem.getAudioInputStream(Juego.class.getResource("/sonidos/"+f));

			sonido.open(ais);
			sonido.start();

		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}

}

class Carta implements Comparable<Object> {

	public static final String ROJO = "\u001b[1;91m";
	public static final String NEGRO = "\u001b[1;30m";
	public static final String DEFAULT = "\u001b[0m";

	private char valor;
	private char palo;
	private String color;

	public Carta(char valor, char palo, String color) {
		super();
		this.valor = valor;
		this.palo = palo;
		this.color = color;
	}

	public float getValorJuego() {
		switch (valor) {
		case ('J'):
		case ('Q'):
		case ('K'):
			return 0.5f;
		default:
			return Float.valueOf(String.valueOf(valor));
		}

	}

	public char getValor() {
		return valor;
	}

	public char getPalo() {
		return palo;
	}

	public void setValor(char valor) {
		this.valor = valor;
	}

	public void setPalo(char palo) {
		this.palo = palo;
	}

	@Override
	public String toString() {
		return color + valor + " " + palo + DEFAULT;
	}

	@Override
	public int compareTo(Object o) {

		int p = ((Carta) o).getPalo() - palo;
		if (p != 0)
			return p;
		else
			return ((Carta) o).getValor() - valor;
	}

}

package br.eti.clairton.cdi.test;

public abstract class Bean {

	public void run() {
		System.out.println("test " + getClass());
	}

}

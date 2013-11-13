package br.com.android.montamega;

import java.io.Serializable;

public class Mega implements Serializable {
	private int id;
	private String mega;

	public Mega(int id, String mega) {
		this.id = id;
		this.mega = mega;
	}

	public Mega() {
	}

	public Mega(String mega) {
		this.mega = mega;
	}

	public String getMega() {
		return mega;
	}

	public void setMega(String mega) {
		this.mega = mega;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}

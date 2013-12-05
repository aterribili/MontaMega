package br.com.android.montamega.mega;

import java.io.Serializable;

public class Mega implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer lastResult;
	private String mega;
	private String date;

	public Mega() {

	}

	public Mega(Integer lastResult, String mega, String date) {
		super();
		this.lastResult = lastResult;
		this.mega = mega;
		this.date = date;
	}

	public Mega(Integer id, Integer lastResult, String mega, String date) {
		super();
		this.id = id;
		this.lastResult = lastResult;
		this.mega = mega;
		this.date = date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLastResult() {
		return lastResult;
	}

	public void setLastResult(Integer lastResult) {
		this.lastResult = lastResult;
	}

	public String getMega() {
		return mega;
	}

	public void setMega(String mega) {
		this.mega = mega;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return this.mega;
	}
}

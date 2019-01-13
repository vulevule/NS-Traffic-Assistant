package com.team9.dto;

import java.io.Serializable;

public class ReportDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// mesecne
	private int numOfStudentMonthTicket;
	private int numOfHandycapMonthTicket;
	private int numOfSeniorMonthTicket;
	private int numOfRegularMonthTicket;

	// godisnje
	private int numOfStudentYearTicket;
	private int numOfHandycapYearTicket;
	private int numOfSeniorYearTicket;
	private int numOfRegularYearTicket;

	// single
	private int numOfStudentSingleTicket;
	private int numOfHandycapSingleTicket;
	private int numOfSeniorSingleTicket;
	private int numOfRegularSingleTicket;

	// dnevne
	private int numOfStudentDailyTicket;
	private int numOfHandycapDailyTicket;
	private int numOfSeniorDailyTicket;
	private int numOfRegularDailyTicket;

	// broj kupljenih karti za odredjeni prevoz
	private int numOfBusTicket;
	private int numOfMetroTicket;
	private int numOfTramTicket;

	// zarada
	private double money;

	public ReportDto() {
	}

	public ReportDto(int numOfStudentMonthTicket, int numOfHandycapMonthTicket, int numOfSeniorMonthTicket,
			int numOfRegularMonthTicket, int numOfStudentYearTicket, int numOfHandycapYearTicket,
			int numOfSeniorYearTicket, int numOfRegularYearTicket, int numOfStudentSingleTicket,
			int numOfHandycapSingleTicket, int numOfSeniorSingleTicket, int numOfRegularSingleTicket,
			int numOfStudentDailyTicket, int numOfHandycapDailyTicket, int numOfSeniorDailyTicket,
			int numOfRegularDailyTicket, int numOfBusTicket, int numOfMetroTicket, int numOfTramTicket, double money) {
		this();
		this.numOfStudentMonthTicket = numOfStudentMonthTicket;
		this.numOfHandycapMonthTicket = numOfHandycapMonthTicket;
		this.numOfSeniorMonthTicket = numOfSeniorMonthTicket;
		this.numOfRegularMonthTicket = numOfRegularMonthTicket;
		this.numOfStudentYearTicket = numOfStudentYearTicket;
		this.numOfHandycapYearTicket = numOfHandycapYearTicket;
		this.numOfSeniorYearTicket = numOfSeniorYearTicket;
		this.numOfRegularYearTicket = numOfRegularYearTicket;
		this.numOfStudentSingleTicket = numOfStudentSingleTicket;
		this.numOfHandycapSingleTicket = numOfHandycapSingleTicket;
		this.numOfSeniorSingleTicket = numOfSeniorSingleTicket;
		this.numOfRegularSingleTicket = numOfRegularSingleTicket;
		this.numOfStudentDailyTicket = numOfStudentDailyTicket;
		this.numOfHandycapDailyTicket = numOfHandycapDailyTicket;
		this.numOfSeniorDailyTicket = numOfSeniorDailyTicket;
		this.numOfRegularDailyTicket = numOfRegularDailyTicket;
		this.numOfBusTicket = numOfBusTicket;
		this.numOfMetroTicket = numOfMetroTicket;
		this.numOfTramTicket = numOfTramTicket;
		this.money = money;
	}

	public int getNumOfStudentMonthTicket() {
		return numOfStudentMonthTicket;
	}

	public void setNumOfStudentMonthTicket(int numOfStudentMonthTicket) {
		this.numOfStudentMonthTicket = numOfStudentMonthTicket;
	}

	public int getNumOfHandycapMonthTicket() {
		return numOfHandycapMonthTicket;
	}

	public void setNumOfHandycapMonthTicket(int numOfHandycapMonthTicket) {
		this.numOfHandycapMonthTicket = numOfHandycapMonthTicket;
	}

	public int getNumOfSeniorMonthTicket() {
		return numOfSeniorMonthTicket;
	}

	public void setNumOfSeniorMonthTicket(int numOfSeniorMonthTicket) {
		this.numOfSeniorMonthTicket = numOfSeniorMonthTicket;
	}

	public int getNumOfRegularMonthTicket() {
		return numOfRegularMonthTicket;
	}

	public void setNumOfRegularMonthTicket(int numOfRegularMonthTicket) {
		this.numOfRegularMonthTicket = numOfRegularMonthTicket;
	}

	public int getNumOfStudentYearTicket() {
		return numOfStudentYearTicket;
	}

	public void setNumOfStudentYearTicket(int numOfStudentYearTicket) {
		this.numOfStudentYearTicket = numOfStudentYearTicket;
	}

	public int getNumOfHandycapYearTicket() {
		return numOfHandycapYearTicket;
	}

	public void setNumOfHandycapYearTicket(int numOfHandycapYearTicket) {
		this.numOfHandycapYearTicket = numOfHandycapYearTicket;
	}

	public int getNumOfSeniorYearTicket() {
		return numOfSeniorYearTicket;
	}

	public void setNumOfSeniorYearTicket(int numOfSeniorYearTicket) {
		this.numOfSeniorYearTicket = numOfSeniorYearTicket;
	}

	public int getNumOfRegularYearTicket() {
		return numOfRegularYearTicket;
	}

	public void setNumOfRegularYearTicket(int numOfRegularYearTicket) {
		this.numOfRegularYearTicket = numOfRegularYearTicket;
	}

	public int getNumOfStudentSingleTicket() {
		return numOfStudentSingleTicket;
	}

	public void setNumOfStudentSingleTicket(int numOfStudentSingleTicket) {
		this.numOfStudentSingleTicket = numOfStudentSingleTicket;
	}

	public int getNumOfHandycapSingleTicket() {
		return numOfHandycapSingleTicket;
	}

	public void setNumOfHandycapSingleTicket(int numOfHandycapSingleTicket) {
		this.numOfHandycapSingleTicket = numOfHandycapSingleTicket;
	}

	public int getNumOfSeniorSingleTicket() {
		return numOfSeniorSingleTicket;
	}

	public void setNumOfSeniorSingleTicket(int numOfSeniorSingleTicket) {
		this.numOfSeniorSingleTicket = numOfSeniorSingleTicket;
	}

	public int getNumOfRegularSingleTicket() {
		return numOfRegularSingleTicket;
	}

	public void setNumOfRegularSingleTicket(int numOfRegularSingleTicket) {
		this.numOfRegularSingleTicket = numOfRegularSingleTicket;
	}

	public int getNumOfStudentDailyTicket() {
		return numOfStudentDailyTicket;
	}

	public void setNumOfStudentDailyTicket(int numOfStudentDailyTicket) {
		this.numOfStudentDailyTicket = numOfStudentDailyTicket;
	}

	public int getNumOfHandycapDailyTicket() {
		return numOfHandycapDailyTicket;
	}

	public void setNumOfHandycapDailyTicket(int numOfHandycapDailyTicket) {
		this.numOfHandycapDailyTicket = numOfHandycapDailyTicket;
	}

	public int getNumOfSeniorDailyTicket() {
		return numOfSeniorDailyTicket;
	}

	public void setNumOfSeniorDailyTicket(int numOfSeniorDailyTicket) {
		this.numOfSeniorDailyTicket = numOfSeniorDailyTicket;
	}

	public int getNumOfRegularDailyTicket() {
		return numOfRegularDailyTicket;
	}

	public void setNumOfRegularDailyTicket(int numOfRegularDailyTicket) {
		this.numOfRegularDailyTicket = numOfRegularDailyTicket;
	}

	public int getNumOfBusTicket() {
		return numOfBusTicket;
	}

	public void setNumOfBusTicket(int numOfBusTicket) {
		this.numOfBusTicket = numOfBusTicket;
	}

	public int getNumOfMetroTicket() {
		return numOfMetroTicket;
	}

	public void setNumOfMetroTicket(int numOfMetroTicket) {
		this.numOfMetroTicket = numOfMetroTicket;
	}

	public int getNumOfTramTicket() {
		return numOfTramTicket;
	}

	public void setNumOfTramTicket(int numOfTramTicket) {
		this.numOfTramTicket = numOfTramTicket;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

}

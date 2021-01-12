package com.example.infocovid_proyecto.models;

public class Ciudad {

    private String DEPARTAMENTO;
    private int PR;
    private int PCR;
    private int TOTAL;
    private int FALLECIDOS;
    private String LETALIDAD;

    public String getDEPARTAMENTO() {
        return DEPARTAMENTO;
    }

    public void setDEPARTAMENTO(String DEPARTAMENTO) {
        this.DEPARTAMENTO = DEPARTAMENTO;
    }

    public int getPR() {
        return PR;
    }

    public void setPR(int PR) {
        this.PR = PR;
    }

    public int getPCR() {
        return PCR;
    }

    public void setPCR(int PCR) {
        this.PCR = PCR;
    }

    public int getTOTAL() {
        return TOTAL;
    }

    public void setTOTAL(int TOTAL) {
        this.TOTAL = TOTAL;
    }

    public int getFALLECIDOS() {
        return FALLECIDOS;
    }

    public void setFALLECIDOS(int FALLECIDOS) {
        this.FALLECIDOS = FALLECIDOS;
    }

    public String getLETALIDAD() {
        return LETALIDAD;
    }

    public void setLETALIDAD(String LETALIDAD) {
        this.LETALIDAD = LETALIDAD;
    }


}

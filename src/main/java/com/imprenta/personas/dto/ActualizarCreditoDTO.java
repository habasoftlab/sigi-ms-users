package com.imprenta.personas.dto;

import java.math.BigDecimal;

public class ActualizarCreditoDTO {
    private BigDecimal limiteCredito;
    private Integer diasCredito;
    private Boolean creditoHabilitado;

    // Genera los Getters y Setters
    public BigDecimal getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(BigDecimal limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public Integer getDiasCredito() {
        return diasCredito;
    }

    public void setDiasCredito(Integer diasCredito) {
        this.diasCredito = diasCredito;
    }

    public Boolean getCreditoHabilitado() {
        return creditoHabilitado;
    }

    public void setCreditoHabilitado(Boolean creditoHabilitado) {
        this.creditoHabilitado = creditoHabilitado;
    }
}
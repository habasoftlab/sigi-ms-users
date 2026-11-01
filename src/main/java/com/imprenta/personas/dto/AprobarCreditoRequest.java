package com.imprenta.personas.dto;

import java.math.BigDecimal;

public class AprobarCreditoRequest {
    private Integer diasCredito;
    private BigDecimal limiteCredito;

    public Integer getDiasCredito() {
        return diasCredito;
    }

    public void setDiasCredito(Integer diasCredito) {
        this.diasCredito = diasCredito;
    }

    public BigDecimal getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(BigDecimal limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

}

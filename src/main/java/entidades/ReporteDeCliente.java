package entidades;

import java.math.BigDecimal;

public class ReporteDeCliente {
	 private BigDecimal totalSaldos;
	 private int prestamosCompletos;
	 private int prestamosIncompletos;
	 private BigDecimal cuotasPagadas;
	 private BigDecimal cuotasVencidas;
	 private BigDecimal cuotasPorVencer;
	 private BigDecimal ingresos;
	 private BigDecimal egresos;
	 
	 public ReporteDeCliente() {
		 this.totalSaldos=BigDecimal.ZERO;
		 this.prestamosCompletos=0;
		 this.prestamosIncompletos=0;
		 this.cuotasPagadas= BigDecimal.ZERO;
		 this.cuotasVencidas= BigDecimal.ZERO;
		 this.cuotasPorVencer= BigDecimal.ZERO;
		 this.ingresos = BigDecimal.ZERO;
	     this.egresos = BigDecimal.ZERO;		 
	 }

	 
	public ReporteDeCliente(BigDecimal totalSaldos, int prestamosCompletos, int prestamosIncompletos,
			BigDecimal cuotasPagadas, BigDecimal cuotasVencidas, BigDecimal cuotasPorVencer, BigDecimal ingresos,
			BigDecimal egresos) {
		this.totalSaldos = totalSaldos;
		this.prestamosCompletos = prestamosCompletos;
		this.prestamosIncompletos = prestamosIncompletos;
		this.cuotasPagadas = cuotasPagadas;
		this.cuotasVencidas = cuotasVencidas;
		this.cuotasPorVencer = cuotasPorVencer;
		this.ingresos = ingresos;
		this.egresos = egresos;
	}


	public BigDecimal getTotalSaldos() {
		return totalSaldos;
	}


	public void setTotalSaldos(BigDecimal totalSaldos) {
		this.totalSaldos = totalSaldos;
	}


	public int getPrestamosCompletos() {
		return prestamosCompletos;
	}


	public void setPrestamosCompletos(int prestamosCompletos) {
		this.prestamosCompletos = prestamosCompletos;
	}


	public int getPrestamosIncompletos() {
		return prestamosIncompletos;
	}


	public void setPrestamosIncompletos(int prestamosIncompletos) {
		this.prestamosIncompletos = prestamosIncompletos;
	}


	public BigDecimal getCuotasPagadas() {
		return cuotasPagadas;
	}


	public void setCuotasPagadas(BigDecimal cuotasPagadas) {
		this.cuotasPagadas = cuotasPagadas;
	}


	public BigDecimal getCuotasVencidas() {
		return cuotasVencidas;
	}


	public void setCuotasVencidas(BigDecimal cuotasVencidas) {
		this.cuotasVencidas = cuotasVencidas;
	}


	public BigDecimal getCuotasPorVencer() {
		return cuotasPorVencer;
	}


	public void setCuotasPorVencer(BigDecimal cuotasPorVencer) {
		this.cuotasPorVencer = cuotasPorVencer;
	}


	public BigDecimal getIngresos() {
		return ingresos;
	}


	public void setIngresos(BigDecimal ingresos) {
		this.ingresos = ingresos;
	}


	public BigDecimal getEgresos() {
		return egresos;
	}


	public void setEgresos(BigDecimal egresos) {
		this.egresos = egresos;
	}
	
	
	 
	 
	 
}

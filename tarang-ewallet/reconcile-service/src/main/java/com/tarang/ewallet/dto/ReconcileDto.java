package com.tarang.ewallet.dto;

import com.tarang.ewallet.model.Reconcile;

public class ReconcileDto extends Reconcile{

	private static final long serialVersionUID = 1L;

	private Long processStatus;
	
	public ReconcileDto(){
	}

	public Long getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(Long processStatus) {
		this.processStatus = processStatus;
	}
	
}

package me.kyle.Communal;

public enum CTSTransferMode {
	Init(Integer.class),
	DataSet(int[].class),
	ModeUpdate(ClientMode.class),
	;
	
	Class<?> type;
	
	private CTSTransferMode(Class<?> type) {
		this.type = type;
	}
	
	public Class<?> GetType(){
		return type;
	}
}

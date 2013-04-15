package me.kyle.Communal;


public abstract class CastMode {
	
	public static STCTransferMode clientToTransfer(ClientMode mode){
		if(mode.equals(ClientMode.GenerateNumbers)){
			return STCTransferMode.GenerateNumbers;
		} else if(mode.equals(ClientMode.ReturnData)){
			return STCTransferMode.ReturnData;
		} else if(mode.equals(ClientMode.Sleep)){
			return STCTransferMode.Sleep;
		} else {
			return null;
		}
	}
	
	public static ClientMode transferToClient(STCTransferMode mode){
		if(mode.equals(STCTransferMode.GenerateNumbers)){
			return ClientMode.GenerateNumbers;
		} else if(mode.equals(STCTransferMode.ReturnData)){
			return ClientMode.ReturnData;
		} else if(mode.equals(STCTransferMode.Sleep)){
			return ClientMode.Sleep;
		} else {
			return null;
		}
	}
}

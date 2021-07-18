public class ManageMulti {
	private MultiModel multiModel;
	private int status;	

	//status用の定数
	
	public static final int WAIT = 0;
	public static final int NOW_ON_PLAY = 1;
	public static final int OVER = 3;

	ManageMulti(MultiModel multiModel, int status) {
		this.multiModel = multiModel;
		this.status = status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return this.status;
	}

	public void setMultiModel(MultiModel multiModel) {
		this.multiModel = multiModel;
	}

	public MultiModel getMultiModel() {
		return this.multiModel;
	}
}


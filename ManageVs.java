public class ManageVs {
	private SingleModel singleModel;
	private int status;	

	//status用の定数
	
	public static final int WAIT = 0;
	public static final int NOW_ON_PLAY = 1;
	public static final int OVER = 3;

	ManageVs(SingleModel singleModel, int status) {
		this.singleModel = singleModel;
		this.status = status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return this.status;
	}

	public void setSingleModel(SingleModel singleModel) {
		this.singleModel = singleModel;
	}

	public SingleModel getSingleModel() {
		return this.singleModel;
	}
}


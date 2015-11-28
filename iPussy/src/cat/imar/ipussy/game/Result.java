package cat.imar.ipussy.game;

import java.io.Serializable;

import cat.imar.ipussy.R;
import cat.imar.ipussy.model.PussyModel;

public class Result implements Serializable {

	/**
	 * Serial id.
	 */
	private static final long serialVersionUID = 2635668351352353492L;

	private Integer idModel;

	private int nameModelPath;
	
	private int imgModelPath;
	private int imgModelPathClose;

	private int resultPath;

	private int resultDescriptionPath;

	private int resultMessageLowerPath;

	private int imgCalcesResultPath;
	
	private int puntuacioPath;

	public Result() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Result(final PussyModel pussyModel, final Integer puntuation) {
		super();
		setIdModel(pussyModel.getId());
		setNameModelPath(pussyModel.getName());
		setImgModelPath(pussyModel.getPathImageEyes());
		setImgModelPathClose(pussyModel.getPathImageEyesClose());
		switch (puntuation) {
		case 1:
			setResultPath(R.string.result_vaginner_label);
			setResultDescriptionPath(R.string.result_vaginner);
			setResultMessageLowerPath(R.string.result_vaginner_end);
			setImgCalcesResultPath(R.drawable.img_calces_1);
			setPuntuacioPath(R.drawable.img_puntuacio_11);
			break;
		case 2:
			setResultPath(R.string.result_advance_label);
			setResultDescriptionPath(R.string.result_advance);
			setResultMessageLowerPath(R.string.result_advance_end);
			setImgCalcesResultPath(R.drawable.img_calces_2);
			setPuntuacioPath(R.drawable.img_puntuacio_21);
			break;
		case 3:
			setResultPath(R.string.result_sexmachine_label);
			setResultDescriptionPath(R.string.result_sexmachine);
			setResultMessageLowerPath(R.string.result_sexmachine_end);
			setImgCalcesResultPath(R.drawable.img_calces_3);
			setPuntuacioPath(R.drawable.img_puntuacio_31);
			break;
		default:
			break;
		}

	}

	public Integer getIdModel() {
		return idModel;
	}

	public void setIdModel(Integer idModel) {
		this.idModel = idModel;
	}

	public int getNameModelPath() {
		return nameModelPath;
	}

	public void setNameModelPath(int nameModelPath) {
		this.nameModelPath = nameModelPath;
	}

	public int getResultPath() {
		return resultPath;
	}

	public void setResultPath(int resultPath) {
		this.resultPath = resultPath;
	}

	public int getResultDescriptionPath() {
		return resultDescriptionPath;
	}

	public void setResultDescriptionPath(int resultDescriptionPath) {
		this.resultDescriptionPath = resultDescriptionPath;
	}

	public int getResultMessageLowerPath() {
		return resultMessageLowerPath;
	}

	public void setResultMessageLowerPath(int resultMessageLowerPath) {
		this.resultMessageLowerPath = resultMessageLowerPath;
	}

	public int getImgCalcesResultPath() {
		return imgCalcesResultPath;
	}

	public void setImgCalcesResultPath(int imgCalcesResultPath) {
		this.imgCalcesResultPath = imgCalcesResultPath;
	}

	public int getImgModelPath() {
		return imgModelPath;
	}

	public void setImgModelPath(int imgModelPath) {
		this.imgModelPath = imgModelPath;
	}

	public int getPuntuacioPath() {
		return puntuacioPath;
	}

	public void setPuntuacioPath(int puntuacioPath) {
		this.puntuacioPath = puntuacioPath;
	}

	public int getImgModelPathClose() {
		return imgModelPathClose;
	}

	public void setImgModelPathClose(int imgModelPathClose) {
		this.imgModelPathClose = imgModelPathClose;
	}
	
	
	
	

}

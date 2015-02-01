package cat.imar.ipussy.model;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "detail_models")
public class DetailPussyModel implements Serializable {

	/**
	 * Setial Id.
	 */
	private static final long serialVersionUID = -665616658394330681L;
	public static final String ID = "_id";
	public static final String ID_PUSSY = "id_pussy";
	public static final String SECOND = "second";
	public static final String MODE = "mode";
	public static final String TABLE_NAME = "detail_models";

	@DatabaseField(columnName = ID, generatedId = false, id = true)
	private Integer id;

	@DatabaseField(foreign = true, columnName = ID_PUSSY)
	private PussyModel pussyModel;

	@DatabaseField(columnName = SECOND)
	private Integer second;

	@DatabaseField(columnName = MODE)
	private Integer mode;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSecond() {
		return second;
	}

	public void setSecond(Integer second) {
		this.second = second;
	}

	public Integer getMode() {
		return mode;
	}

	public void setMode(Integer mode) {
		this.mode = mode;
	}

	public PussyModel getPussyModel() {
		return pussyModel;
	}

	public void setPussyModel(PussyModel pussyModel) {
		this.pussyModel = pussyModel;
	}
	
	public boolean equals(DetailPussyModel o) {
		return o.getId().equals(this.getId());
	}

}

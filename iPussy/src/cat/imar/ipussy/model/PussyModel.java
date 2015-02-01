package cat.imar.ipussy.model;

import java.io.Serializable;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author iestopa
 *
 */
@DatabaseTable(tableName="models")
public class PussyModel implements Serializable {

	/**
	 * Serial Id.
	 */
	private static final long serialVersionUID = 6585646623418956914L;
	public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String RESULT = "result";
    public static final String ENABLED = "enabled";
    public static final String PATH_IMAGE_EYES = "image_eyes";
    public static final String PATH_IMAGE_EYES_CLOSE = "image_eyes_close";
    public static final String TABLE_NAME = "models";
    
    
    @DatabaseField(columnName = ID, id=true)
	private Integer id;
    
    @DatabaseField(columnName = NAME)
	private int name;
    
    @DatabaseField(columnName = DESCRIPTION)
	private int description;
    
    @DatabaseField(columnName = PATH_IMAGE_EYES)
	private int pathImageEyes;
    
    @DatabaseField(columnName = PATH_IMAGE_EYES_CLOSE)
	private int pathImageEyesClose;
    
	private List<DetailPussyModel> detallPussyModelList;
	
	@DatabaseField(columnName = RESULT)
	private int result;
	
	@DatabaseField(columnName = ENABLED)
	private boolean enabled;
	
	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}

	public int getDescription() {
		return description;
	}

	public void setDescription(int description) {
		this.description = description;
	}

	public int getPathImageEyes() {
		return pathImageEyes;
	}

	public void setPathImageEyes(int pathImageEyes) {
		this.pathImageEyes = pathImageEyes;
	}

	public int getPathImageEyesClose() {
		return pathImageEyesClose;
	}

	public void setPathImageEyesClose(int pathImageEyesClose) {
		this.pathImageEyesClose = pathImageEyesClose;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<DetailPussyModel> getDetallPussyModelList() {
		return detallPussyModelList;
	}

	public void setDetallPussyModelList(List<DetailPussyModel> detallPussyModelList) {
		this.detallPussyModelList = detallPussyModelList;
	}
	
	public boolean equals(PussyModel o) {
		return o.getId().equals(this.getId());
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}

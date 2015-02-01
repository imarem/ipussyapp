package cat.imar.ipussy.model;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import cat.imar.ipussy.R;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DBHelper extends OrmLiteSqliteOpenHelper {
	private static final String DATABASE_NAME = "ipussy.db";
	private static final int DATABASE_VERSION = 1;

	private Dao<PussyModel, Integer> pussyModelDao = null;
	private Dao<DetailPussyModel, Integer> detailPussyModelDao = null;

	// the DAO object we use to access the SimpleData table
	private static final AtomicInteger usageCounter = new AtomicInteger(0);

	// we do this so there is only one helper
	private static DBHelper helper = null;

	private DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * Get the helper, possibly constructing it if necessary. For each call to
	 * this method, there should be 1 and only 1 call to {@link #close()}.
	 */
	public static synchronized DBHelper getHelper(Context context) {
		if (helper == null) {
			helper = new DBHelper(context);
		}
		usageCounter.incrementAndGet();
		return helper;
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, PussyModel.class);
			TableUtils.createTable(connectionSource, DetailPussyModel.class);
			loadData();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int oldVersion, int newVersion) {
		try {
			TableUtils.dropTable(connectionSource, PussyModel.class, true);
			TableUtils
					.dropTable(connectionSource, DetailPussyModel.class, true);
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Dao<PussyModel, Integer> getPussyModelDao() throws SQLException {
		if (pussyModelDao == null) {
			pussyModelDao = getDao(PussyModel.class);
		}
		return pussyModelDao;
	}

	public Dao<DetailPussyModel, Integer> getDetailPussyModelDao()
			throws SQLException {
		if (detailPussyModelDao == null) {
			detailPussyModelDao = getDao(DetailPussyModel.class);
		}
		return detailPussyModelDao;
	}

	private void loadData() {
		PussyModel pm1 = createPussyModel(1, R.string.name1,
				R.string.descripcio1, R.drawable.girl1, R.drawable.girl1b, true);
		final Integer[] detailList1 = new Integer[] { 1, 1, 1, 1, 1, 1, 2, 2,
				2, 2, 2, 2, 3, 3, 3, 3, 3, 3 };
		for (int i = 0; i < detailList1.length; i++) {
			createDetailPussyModel(i + 1, pm1, detailList1[i], i + 1);
		}

		PussyModel pm2 = createPussyModel(2, R.string.name2,
				R.string.descripcio2, R.drawable.girl2, R.drawable.girl2b, true);
		final Integer[] detailList2 = new Integer[] { 1, 1, 1, 2, 2, 2, 2,
				2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 };
		for (int i = 0; i < detailList2.length; i++) {
			createDetailPussyModel(i + 1 + detailList1.length, pm2,
					detailList2[i], i + 1);
		}

		//TODO cambiar ojos disabled
		PussyModel pm3 = createPussyModel(3, R.string.name3,
				R.string.descripcio3, R.drawable.girl3, R.drawable.girl3b, false);
		final Integer[] detailList3 = new Integer[] { 1, 2, 2, 2, 3, 3, 3,
				3, 3, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3 };
		for (int i = 0; i < detailList3.length; i++) {
			createDetailPussyModel(i + 1 + detailList2.length
					+ detailList1.length, pm3, detailList3[i], i + 1);
		}

		PussyModel pm4 = createPussyModel(4, R.string.name4,
				R.string.descripcio4, R.drawable.girl4, R.drawable.girl4b, false);
		final Integer[] detailList4 = new Integer[] { 1, 1, 3, 3, 3, 2, 2, 2,
				2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 };
		for (int i = 0; i < detailList4.length; i++) {
			createDetailPussyModel(i + 1 + detailList3.length
					+ detailList2.length + detailList1.length, pm4,
					detailList4[i], i + 1);
		}

		PussyModel pm5 = createPussyModel(5, R.string.name5,
				R.string.descripcio5, R.drawable.girl5, R.drawable.girl5b, false);
		final Integer[] detailList5 = new Integer[] { 1, 1, 1, 1, 1, 1, 1, 1,
				2, 2, 3, 3, 3, 3, 3, 2, 2, 2, 1, 1 };
		for (int i = 0; i < detailList5.length; i++) {
			createDetailPussyModel(i + 1 + detailList4.length
					+ detailList3.length + detailList2.length
					+ detailList1.length, pm5, detailList5[i], i + 1);
		}
	}

	@Override
	public void close() {
		if (usageCounter.decrementAndGet() == 0) {
			super.close();
			pussyModelDao = null;
			detailPussyModelDao = null;
			helper = null;
		}

	}

	private PussyModel createPussyModel(int id, int name, int description,
			int pathImageEyes, int pathImageEyesClose, boolean isEnabled) {
		PussyModel pussyModel = null;
		Dao<PussyModel, Integer> dao;
		try {
			dao = getPussyModelDao();
			pussyModel = new PussyModel();
			pussyModel.setId(id);
			pussyModel.setName(name);
			pussyModel.setDescription(description);
			pussyModel.setPathImageEyes(pathImageEyes);
			pussyModel.setPathImageEyesClose(pathImageEyesClose);
			pussyModel.setResult(0);
			pussyModel.setEnabled(isEnabled);
			dao.create(pussyModel);

		} catch (SQLException e) {
			// Log.e("", "Error creando usuario");
		}
		return pussyModel;
	}

	private DetailPussyModel createDetailPussyModel(int id,
			PussyModel pussyModel, Integer mode, Integer second) {
		Dao<DetailPussyModel, Integer> dao;
		DetailPussyModel detailPussyModel = null;
		try {
			dao = getDetailPussyModelDao();
			detailPussyModel = new DetailPussyModel();
			detailPussyModel.setId(id);
			detailPussyModel.setPussyModel(pussyModel);
			detailPussyModel.setMode(mode);
			detailPussyModel.setSecond(second);
			dao.create(detailPussyModel);
		} catch (SQLException e) {
			// Log.e("", "Error creando usuario");
		}
		return detailPussyModel;
	}

	public PussyModel getPussyModelById(final Integer idPussyModel) {
		Dao<PussyModel, Integer> dao;
		Dao<DetailPussyModel, Integer> daoDetail;
		PussyModel pussyModel = null;
		try {
			dao = getPussyModelDao();
			pussyModel = dao.queryForId(idPussyModel);
			if (pussyModel == null) {
				Log.d("", "No s'ha trobat el model amb id: " + idPussyModel);
			} else {
				daoDetail = getDetailPussyModelDao();
				List<DetailPussyModel> detallPussyList = daoDetail.queryForEq(
						DetailPussyModel.ID_PUSSY, idPussyModel);
				pussyModel.setDetallPussyModelList(detallPussyList);
				Log.d("",
						"Recuperado usuario con id = 1: "
								+ pussyModel.getName());
			}
		} catch (SQLException e) {
			// Log.e("", "Error creando usuario");
		}
		return pussyModel;
	}

	public PussyModel updatePussyModelByIdAndResult(final int result,
			final PussyModel pussyModel) {
		Dao<PussyModel, Integer> dao;
		if (result > pussyModel.getResult()) {		
			try {
				pussyModel.setResult(result);
				dao = getPussyModelDao();
				dao.update(pussyModel);

				// si el resultat ha estat 3 i no estem jugant amb la primera
				// xati mirem si desbloqueja
				if (result == 3) {
					boolean enabledNext = true;
					// si es la primera o la segona xati mirem resultats anteriors per si es
					// pot desbloquejar la 3
					if(pussyModel.getId().equals(1)){
						// mirem la següent aviam si s'ha
						PussyModel pussyModel2 = getPussyModelById(2);
						if (pussyModel2.getResult() != 3) {
							enabledNext = false;
						}
					}else if (pussyModel.getId().equals(2)){
						// mirem l'anterior aviam si s'ha
						PussyModel pussyModel1 = getPussyModelById(1);
						if (pussyModel1.getResult() != 3) {
							enabledNext = false;
						}
					}				
				
					Integer id = null;
					if(pussyModel.getId().equals(1) || pussyModel.getId().equals(2)){
						id = 2;
					}else{
						id = pussyModel.getId();
					}
					// si no es tracta de la 1 ni la 2 sempre desbolquejem la
					// següent si ha tret un 3 (sex machine)
					PussyModel seguent = getPussyModelById(id + 1);
					if (seguent != null && enabledNext) {
						seguent.setEnabled(true);
						dao.update(seguent);
					}
				}

			} catch (SQLException e) {
				// Log.e("", "Error creando usuario");
			}
		}
		return pussyModel;
	}

}

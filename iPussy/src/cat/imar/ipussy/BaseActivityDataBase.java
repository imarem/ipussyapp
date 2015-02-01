/**
 * 
 */
package cat.imar.ipussy;

import cat.imar.ipussy.model.DBHelper;

import com.actionbarsherlock.app.SherlockActivity;

/**
 * @author iestopa
 *
 */
public class BaseActivityDataBase extends SherlockActivity {

	private DBHelper mDBHelper = null;
	
	/**
	 * You'll need this in your class to get the helper from the manager once per class.
	 */
	public DBHelper getHelper() {
		if (mDBHelper == null) {
			mDBHelper = DBHelper.getHelper(this);
		}
		return mDBHelper;
	}
 
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDBHelper != null) {
        	mDBHelper.close();
            mDBHelper = null;
        }
    }
    
}

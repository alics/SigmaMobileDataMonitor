package com.zohaltech.app.sigma.classes;

import android.util.Log;

import com.zohaltech.app.sigma.util.IabHelper;
import com.zohaltech.app.sigma.util.IabResult;
import com.zohaltech.app.sigma.util.Inventory;

public class Payment {

    private static final String  PAY_LOAD    = "SIGMA_ANDROID_APP";
    private static final String  TAG         = "SIGMA_TAG";
    private static final String  SKU_PREMIUM = "PREMIUM";
    private static final int     RC_REQUEST  = 10001;
    private static       boolean mIsPremium  = false;
    private static IabHelper mHelper;

    private static IabHelper.QueryInventoryFinishedListener mGotInventoryListener;

    static {
        mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
            public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
                Log.i(TAG, "Query inventory finished.");
                if (result.isFailure()) {
                    Log.i(TAG, "Failed to query inventory: " + result);
                    return;
                } else {
                    Log.i(TAG, "Query inventory was successful.");
                    // does the user have the premium upgrade?
                    mIsPremium = inventory.hasPurchase(SKU_PREMIUM);

                    // update UI accordingly
                    //App.settings.edit().putBoolean(App.IS_PREMIUM_PREF_KEY, mIsPremium).commit();
                    //App.isPremium = mIsPremium;

                    Log.i(TAG, "User is " + (mIsPremium ? "PREMIUM" : "NOT PREMIUM"));
                }

                Log.i(TAG, "Initial inventory query finished; enabling main UI.");
            }
        };
    }

    public static void pay() {
        if (!LicenseManager.getLicenseStatus()) {

            String base64EncodedPublicKey = "MIHNMA0GCSqGSIb3DQEBAQUAA4G7ADCBtwKBrwDEZZ4jpPHrnb/I+16h4DRUB0u13UCme31/0Jz+b4enw1xUmMmvlduzZUTruV1fM8bH14gEBMvOIt+g7u0AhejrfYXavhE/R3JrNq+TuZTrKuO73MpO1aaeiuYmsuA1wcN7fP5akffu/1HSqAj4s0F7fArJRZeiGnEb7ApTBFlGLg/o4groUZZwF3f1abFEcC9wM+HfGuiUZdKDTKkT3XyVzPcsvnrjMODiPeFsTH8CAwEAAQ==";
            mHelper = new IabHelper(App.currentActivity, base64EncodedPublicKey);
            Log.d(TAG, "Starting setup.");
            mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
                public void onIabSetupFinished(IabResult result) {
                    Log.d(TAG, "Setup finished.");

                    if (!result.isSuccess()) {
                        // Oh noes, there was a problem.
                        Log.d(TAG, "Problem setting up In-app Billing: " + result);
                    }
                    // Hooray, IAB is fully set up!
                    mHelper.queryInventoryAsync(mGotInventoryListener);
                }
            });
        }
    }
}

package com.zohaltech.app.sigma.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.classes.App;
import com.zohaltech.app.sigma.classes.LicenseManager;
import com.zohaltech.app.sigma.util.IabHelper;
import com.zohaltech.app.sigma.util.IabResult;
import com.zohaltech.app.sigma.util.Inventory;
import com.zohaltech.app.sigma.util.Purchase;

import widgets.MyToast;

public abstract class BazaarPaymentActivity extends EnhancedActivity {

    private final String PAY_LOAD    = "SIGMA_ANDROID_APP";
    private final String TAG         = "SIGMA_TAG";
    private final String SKU_PREMIUM = "PREMIUM";
    private final int    RC_REQUEST  = 10001;
    private ProgressDialog progressDialog;
    private boolean mIsPremium = false;
    private IabHelper mHelper;

    private IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.i(TAG, "Query inventory finished.");
            if (result.isFailure()) {
                Log.i(TAG, "Failed to query inventory: " + result);
                complain("خطا در خرید از بازار");
                return;
            } else {
                Log.i(TAG, "Query inventory was successful.");
                // does the user have the premium upgrade?
                mIsPremium = inventory.hasPurchase(SKU_PREMIUM);

                // update UI accordingly
                if (mIsPremium) {
                    LicenseManager.registerLicense();
                    updateUiToPremiumVersion();
                    setWaitScreen(false);
                }

                Log.i(TAG, "User is " + (mIsPremium ? "PREMIUM" : "NOT PREMIUM"));
            }

            Log.i(TAG, "Initial inventory query finished; enabling main UI.");
        }
    };

    private IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            if (result.isFailure()) {
                Log.e("PAYMENT", "Error purchasing: " + result);
                complain("خطا در خرید از بازار");
            } else if (purchase.getSku().equals(SKU_PREMIUM)) {
                if (!verifyDeveloperPayload(purchase)) {
                    Log.e("PAYMENT", "Error purchasing. Authenticity verification failed.");
                    complain("خطا در ورود به حساب کاربری بازار");
                } else {
                    // give user access to premium content and update the UI
                    LicenseManager.registerLicense();
                    MyToast.show("شما با موفقیت به نسخه کامل ارتقا یافتید", Toast.LENGTH_LONG);
                    updateUiToPremiumVersion();
                }
            }
            setWaitScreen(false);
        }
    };

    @Override
    void onCreated() {
        if (LicenseManager.getLicenseStatus() != LicenseManager.Status.REGISTERED) {
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

    @Override
    void onToolbarCreated() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        } else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) {
            mHelper.dispose();
        }
        mHelper = null;
    }

    private boolean verifyDeveloperPayload(Purchase p) {
        if (p.getDeveloperPayload().equals(PAY_LOAD)) {
            return true;
        }
        return false;
    }

    private void complain(String message) {
        MyToast.show(message, Toast.LENGTH_LONG, R.drawable.ic_warning_white);
    }
    void pay() {
        setWaitScreen(true);
        mHelper.launchPurchaseFlow(App.currentActivity, SKU_PREMIUM, RC_REQUEST, mPurchaseFinishedListener, PAY_LOAD);
    }

    private void setWaitScreen(boolean wait) {
        if (wait) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("لطفاً کمی صبر کنید...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        } else {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        }
    }

    abstract void updateUiToPremiumVersion();
}

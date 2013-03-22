package gark.tap.cockroach;

import gark.tap.cockroach.mathengine.MathEngine;
import gark.tap.cockroach.mathengine.Utils;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.Toast;

import com.gark.android.billing.util.IabHelper;
import com.gark.android.billing.util.IabResult;
import com.gark.android.billing.util.Inventory;
import com.gark.android.billing.util.Purchase;
import com.google.analytics.tracking.android.EasyTracker;

public class GameActivity extends MainActivity {
	private MathEngine mMathEngine;

	private static final String TAG = "Cockroach TAG";
	// private static final String SKU_ADS = "android.test.purchased";
	private static final String SKU_ADS = "roach_rush_remove_ads";

	static final int RC_REQUEST = 100001;
	private boolean isAdsVisible;

	private IabHelper mHelper;

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {
		super.onCreateScene(pOnCreateSceneCallback);
		Config.SPEED = 100f;

		// final Typeface typeface = Typeface.createFromAsset(getAssets(),
		// "font/america1.ttf");
		// final Typeface typeface = Typeface.createFromAsset(getAssets(),
		// "font/Futurr.ttf");
		final Typeface typeface = Typeface.createFromAsset(getAssets(), "font/ThreeDee.ttf");

		Utils.setTypeface(typeface);
		mMathEngine = new MathEngine(this);

		String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgXwE2EOX+Wqt/V2RiA0MzcgjSKs+kETKqv+QGNsYPui8vjeEQ7BK8NhRxMuB0fQIVEMogoRMz3dRhXwlJPTCn4nk75O4sbbqrL1LSZhFmbqISICrRHmf0IJRT9K6MtSxYaYw7FfYaAKjWZRwId3w/emoEeif3/Ul7hHVTUQgUD3ZBYRBvKj3I4pjuOBwor6XhwsTTpV7ykmBQq4Z1QGZJhOsE8XbukYdPo2XZcTi9IInh1tQ4Evo/Uu2sbhfsrjH7oIvBZiVYUudfckR5zjHHs2Y8tLffseDXRZ1yxkyQ5Eck1pll8glo5m2Ls+u+1hVhnJR9dOKzdaRmtHzAqywyQIDAQAB";

		// Some sanity checks to see if the developer (that's you!) really
		// followed the
		// instructions to run this sample (don't put these checks on your app!)
		if (base64EncodedPublicKey.contains("CONSTRUCT_YOUR")) {
			throw new RuntimeException("Please put your app's public key in MainActivity.java. See README.");
		}
		if (getPackageName().startsWith("com.example")) {
			throw new RuntimeException("Please change the sample's package name! See README.");
		}

		mHelper = new IabHelper(this, base64EncodedPublicKey);

		// TODO
		// enable debug logging (for a production application, you should set
		// this to false).
		mHelper.enableDebugLogging(false);
		mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
			public void onIabSetupFinished(IabResult result) {
				Log.d(TAG, "Setup finished.");

				if (!result.isSuccess()) {
					// Oh noes, there was a problem.
					// complain("Problem setting up in-app billing: " + result);
					return;
				}

				// Hooray, IAB is fully set up. Now, let's get an inventory of
				// stuff we own.
				// Log.d(TAG, "Setup successful. Querying inventory.");
				mHelper.queryInventoryAsync(mGotInventoryListener);
			}
		});
	}

	// Listener that's called when we finish querying the items and
	// subscriptions we own
	IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
		public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
			Log.d(TAG, "Query inventory finished.");
			if (result.isFailure()) {
				return;
			}

			Log.d(TAG, "Query inventory was successful.");

			// retrieve info
			Purchase adsPurchase = inventory.getPurchase(SKU_ADS);
			if (adsPurchase != null && verifyDeveloperPayload(adsPurchase)) {
				Log.d(TAG, "We have gas. Consuming it.");

				// TODO reset
				// mHelper.consumeAsync(inventory.getPurchase(SKU_ADS),
				// mConsumeFinishedListener);

				if (adsPurchase.getSku().equals(SKU_ADS)) {
					setAdsVisible(false);
				}
				return;
			}

			setAdsVisible(true);
		}
	};

	public boolean isAdsVisible() {
		return isAdsVisible;
	}

	public void setAdsVisible(boolean isAdsVisible) {
		this.isAdsVisible = isAdsVisible;
	}

	public void disableAds() {

		try {
			String payload = SKU_ADS;
			mHelper.launchPurchaseFlow(this, SKU_ADS, RC_REQUEST, mPurchaseFinishedListener, payload);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);

		// Pass on the activity result to the helper for handling
		if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
			// not handled, so handle it ourselves (here's where you'd
			// perform any handling of activity results not related to in-app
			// billing...
			super.onActivityResult(requestCode, resultCode, data);
		} else {
			Log.d(TAG, "onActivityResult handled by IABUtil.");
		}
	}

	// Callback for when a purchase is finished
	IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
		public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
			Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);
			if (result.isFailure()) {
				return;
			}
			if (!verifyDeveloperPayload(purchase)) {
				return;
			}

			Log.d(TAG, "Purchase successful.");

			if (purchase.getSku().equals(SKU_ADS)) {

				if (mMathEngine != null && mMathEngine.getStartManager() != null) {
					mMathEngine.getStartManager().setRemoveAdsVisibility(false);
					setAdsVisible(false);
				}

			}

		}
	};

	/** Verifies the developer payload of a purchase. */
	boolean verifyDeveloperPayload(Purchase p) {
		// String payload = p.getDeveloperPayload();
		// Log.e("ffffffffffffffffffffffff", "" + payload);

		/*
		 * verify that the developer payload of the purchase is correct. It will
		 * be the same one that you sent when initiating the purchase.
		 * 
		 * WARNING: Locally generating a random string when starting a purchase
		 * and verifying it here might seem like a good approach, but this will
		 * fail in the case where the user purchases an item on one device and
		 * then uses your app on a different device, because on the other device
		 * you will not have access to the random string you originally
		 * generated.
		 * 
		 * So a good developer payload has these characteristics:
		 * 
		 * 1. If two different users purchase an item, the payload is different
		 * between them, so that one user's purchase can't be replayed to
		 * another user.
		 * 
		 * 2. The payload must be such that you can verify it even when the app
		 * wasn't the one who initiated the purchase flow (so that items
		 * purchased by the user on one device work on other devices owned by
		 * the user).
		 * 
		 * Using your own server to store and verify developer payloads across
		 * app installations is recommended.
		 */

		return true;
	}

	// Called when consumption is complete
	IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
		public void onConsumeFinished(Purchase purchase, IabResult result) {
			Log.d(TAG, "Consumption finished. Purchase: " + purchase + ", result: " + result);

			if (result.isSuccess()) {
				Toast.makeText(getApplicationContext(), " Consumed ", Toast.LENGTH_SHORT).show();
			}
			Log.d(TAG, "End consumption flow.");
		}
	};

	@Override
	public void onBackPressed() {
		if (mMathEngine.isGameState()) {
			try {
				mMathEngine.stop(true);
				mMathEngine.getTextManager().clearAllView();
				mMathEngine.getGameOverManager().clearAllView();
				mMathEngine.initStartScreen();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			super.onBackPressed();
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		// very important:
		Log.d(TAG, "Destroying helper.");
		if (mHelper != null)
			mHelper.dispose();
		mHelper = null;
	}

	@Override
	protected void onPause() {
		try {
			if (mMathEngine.isGameState()) {
				mMathEngine.pause();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.onPause();
	}

	@Override
	protected void onStart() {
		super.onStart();
		EasyTracker.getInstance().activityStart(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		EasyTracker.getInstance().activityStop(this);
	}
}

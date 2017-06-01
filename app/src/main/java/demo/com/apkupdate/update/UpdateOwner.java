package demo.com.apkupdate.update;

import android.content.Context;
import android.content.Intent;

import demo.com.apkupdate.view.CommonDialog;

/**
 * Created by puming on 2017/2/22.
 */

public class UpdateOwner {
    private static UpdateOwner mUpdateOwner;

    private UpdateOwner() {
    }

    public static UpdateOwner getInstance() {
        if (mUpdateOwner == null) {
            synchronized (UpdateOwner.class) {
                if (mUpdateOwner == null) {
                    mUpdateOwner = new UpdateOwner();
                }
            }
        }
        return mUpdateOwner;
    }

    private int getLocalAPKVersion() {
        return -1;
    }

    public void checkVersion(Context context, String url) {
        int newVersion = 0;
        // TODO: 2017/2/22 请求服务器，检测是否有新版本。
        int localAPKVersion = getLocalAPKVersion();
        if (localAPKVersion < newVersion) {
            showAPKUpdateDialog(context, url);
        }
    }

    /**
     * 显示用户选择更新APK的对话框，当且仅当检测出有新版本才主动显示。
     *
     * @param context
     */
    private void showAPKUpdateDialog(final Context context, final String url) {
        final CommonDialog dialog = new CommonDialog(context);
        dialog.setTitle("ApkUpdate");
        dialog.setContent("发现新版本，请及时更新");
        dialog.setLeftBtnText("立即更新");
        dialog.setRightBtnText("稍后再说");
        dialog.setOnYesClickListener(new CommonDialog.OnYesClickListener() {
            @Override
            public void yesClick() {
                dialog.dismiss();
                Intent startIntent = new Intent(context, UpdateService.class);
                startIntent.putExtra("apkUrl", url);
                context.startService(startIntent);
            }
        });

        dialog.setOnNoClickListener(new CommonDialog.OnNoClickListener() {
            @Override
            public void noClick() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}

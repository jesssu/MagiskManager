package com.topjohnwu.magisk.asyncs;

import android.app.Activity;

import com.topjohnwu.magisk.utils.Shell;
import com.topjohnwu.magisk.utils.Utils;

public class GetBootBlocks extends RootTask<Void, Void, Void> {

    public GetBootBlocks(Activity context) {
        super(context);
    }

    @Override
    protected Void doInRoot(Void... params) {
        magiskManager.blockList = Shell.su(
                "find /dev/block -type b -maxdepth 1 | grep -v -E \"loop|ram|dm-0\""
        );
        if (magiskManager.bootBlock == null) {
            magiskManager.bootBlock = Utils.detectBootImage();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        magiskManager.blockDetectionDone.trigger();
        super.onPostExecute(v);
    }
}

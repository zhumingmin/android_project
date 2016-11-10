package cn.minxing.view; 
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

public class ProgressWaitDialog extends AlertDialog{
int layot_id = 0;
public ProgressWaitDialog(Context context, int theme,int layoutID) {
    super(context, theme); 
    WindowManager.LayoutParams lp=this.getWindow().getAttributes();
    lp.alpha=0.5f;
    this.getWindow().setAttributes(lp);
    lp.dimAmount=0.5f; 
    this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    layot_id = layoutID;
}

public ProgressWaitDialog(Context context) {
    super(context);
}

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState); 
    setContentView(layot_id); 
}
}
package com.dj.collection;

import android.Manifest;
import android.content.Intent;
import android.os.Environment;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.anim.AnimatedVectorActivity;
import com.anim.DataChangeActivity;
import com.bd.scan.BankScanActivity;
import com.bigimage.BigImageActivity;
import com.coordinator.CoordinatorActivity;
import com.detail.DetailActivity;
import com.dj.asm.ASMTestActivity;
import com.dj.bugly.BuglyActivity;
import com.dj.ca.CustomAnnotationsActivity;
import com.dj.collection.bean.AppInfoBean;
import com.dj.collection.network.HttpUtils;
import com.dj.collection.network.NetworkHelper;
import com.dj.collection.network.listener.OnDownloadListener;
import com.dj.collection.network.listener.ResponseListener;
import com.dj.collection.utils.Utils;
import com.dj.cpu.CPUFrameworkHelper;
import com.dj.cpu.CpuTypeActivity;
import com.dj.customclock.CustomClockActivity;
import com.dj.dagger.MyDaggerActivity;
import com.dj.download.DownloadActivity;
import com.dj.event.DispatchEventActivity;
import com.dj.logutil.LogUtils;
import com.dj.mvvm.MvvmActivity;
import com.dj.photoview.PhotoViewActivity;
import com.dj.room.RoomActivity;
import com.dj.scroll.message.ScrollMessageActivity;
import com.dj.sk.SocketActivity;
import com.dj.websocket.WebSocketActivity;
import com.dj.ws.WSActivity;
import com.dj.zip.ZipActivity;
import com.dynamicso.DynamicsoActivity;
import com.dynamsoft.CameraPicShowActivity;
import com.eventbus.EventBusMainActivity;
import com.eventbus.event.MessageEvent;
import com.eventbus.event.ToastEvent;
import com.finger.FingerMainActivity;
import com.live.LiveRoomActivity;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.pdf.PdfActivity;
import com.rxjava.RxJavaMainActivity;
import com.service.ServiceActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{
    private static final String TAG = MainActivity.class.getName();
    private String[] perms = {Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_PHONE_STATE};
    @BindView(R.id.textView)
    TextView textView;

    @BindView(R.id.webview)
    WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Logger.addLogAdapter(new AndroidLogAdapter());
        EventBus.getDefault().register(this);

//        webview.loadUrl("http://crm2.qq.com/page/portalpage/wpa.php?uin=800000152&f=2&ty=2");
//        webview.loadUrl("http://wpa.b.qq.com/cgi/wpa.php?ln=2&uin=800000152");
//        webview.loadUrl("http://crm2.qq.com/page/portalpage/wpa.php?uin=40012345");

//        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?ln=2&chat_type=wpa&uin=800000152")));

        //启用支持javascript
//        WebSettings settings = webview.getSettings();
//        settings.setJavaScriptEnabled(true);
//        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
//        webview.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                // TODO Auto-generated method stub
//                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
//                view.loadUrl(url);
//                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
//                return true;
//            }
//        });


        checkAppInfo();
        requestPermissions();
    }

    @OnClick(R.id.jumpBtn)
    protected void jump(){
//        Intent intent= new Intent();
//        intent.setAction("android.intent.action.VIEW");
//        Uri content_url = Uri.parse("http://gdown.baidu.com/data/wisegame/43e76ce22df64c52/QQ_730.apk");
//        intent.setData(content_url);
//        startActivity(intent);
        startActivity(new Intent(MainActivity.this, EventBusMainActivity.class));
    }

    @OnClick(R.id.jumpRxJavaBtn)
    protected void jumpRxJava(){
        startActivity(new Intent(MainActivity.this, RxJavaMainActivity.class));
    }

    @OnClick(R.id.jumpAnimBtn)
    protected void jumpAnim(){
        startActivity(new Intent(MainActivity.this, AnimatedVectorActivity.class));
    }

    @OnClick(R.id.jumpDataChangeBtn)
    protected void jumpDataChange(){
        startActivity(new Intent(MainActivity.this, DataChangeActivity.class));
    }

    @OnClick(R.id.downloadBtn)
    protected void download(){
        downloadSo();
    }

    @OnClick(R.id.soBtn)
    protected void so(){
        startActivity(new Intent(MainActivity.this, DynamicsoActivity.class));
    }

    @OnClick(R.id.cpuBtn)
    protected void cpu(){
        startActivity(new Intent(MainActivity.this, CpuTypeActivity.class));
    }

    @OnClick(R.id.coordinatorBtn)
    protected void coordinator(){
        startActivity(new Intent(MainActivity.this, CoordinatorActivity.class));
    }
    @OnClick(R.id.buglyBtn)
    protected void bugly(){
        startActivity(new Intent(MainActivity.this, BuglyActivity.class));
    }

    @OnClick(R.id.zipBtn)
    protected void zip(){
        startActivity(new Intent(MainActivity.this, ZipActivity.class));
    }

    @OnClick(R.id.scrollBigImage)
    protected void scrollBigImage(){
        startActivity(new Intent(MainActivity.this, DetailActivity.class));
    }

    @OnClick(R.id.retrofit)
    protected void retrofit(){
        startActivity(new Intent(MainActivity.this, RetrofitActivity.class));
    }

    @OnClick(R.id.scrollBigImage2)
    protected void scrollBigImage2(){
        startActivity(new Intent(MainActivity.this, BigImageActivity.class));
    }

    @OnClick(R.id.mvvm)
    protected void mvvm(){
        startActivity(new Intent(MainActivity.this, MvvmActivity.class));
    }

    @OnClick(R.id.finger)
    protected void finger(){
        startActivity(new Intent(MainActivity.this, FingerMainActivity.class));
    }

    @OnClick(R.id.bankScan)
    protected void bankScan(){
        startActivity(new Intent(MainActivity.this, BankScanActivity.class));
    }

    @OnClick(R.id.pdf)
    protected void pdf(){
        startActivity(new Intent(MainActivity.this, PdfActivity.class));
    }

    @OnClick(R.id.aliLive)
    protected void aliLive(){
        startActivity(new Intent(MainActivity.this, LiveRoomActivity.class));
    }

    @OnClick(R.id.socket)
    protected void socket(){
        startActivity(new Intent(MainActivity.this, SocketActivity.class));
    }

    @OnClick(R.id.dagger)
    protected void dagger(){
        startActivity(new Intent(MainActivity.this, MyDaggerActivity.class));
    }

    @OnClick(R.id.camera)
    protected void camera(){
        startActivity(new Intent(MainActivity.this, CameraPicShowActivity.class));
    }

    @OnClick(R.id.service)
    protected void service(){
        startActivity(new Intent(MainActivity.this, ServiceActivity.class));
    }

    @OnClick(R.id.clock)
    protected void clock(){
        startActivity(new Intent(MainActivity.this, CustomClockActivity.class));
    }
    @OnClick(R.id.dispatchEvent)
    protected void dispatchEvent(){
        startActivity(new Intent(MainActivity.this, DispatchEventActivity.class));
    }
    @OnClick(R.id.asyncTaskDownload)
    protected void asyncTaskDownload(){
        startActivity(new Intent(MainActivity.this, DownloadActivity.class));
    }

    @OnClick(R.id.roomUse)
    protected void roomUse(){
        startActivity(new Intent(MainActivity.this, RoomActivity.class));
    }

    @OnClick(R.id.webservice)
    protected void webservice(){
        startActivity(new Intent(MainActivity.this, WSActivity.class));
    }

    @OnClick(R.id.websocket)
    protected void websocket(){
        startActivity(new Intent(MainActivity.this, WebSocketActivity.class));
    }

    @OnClick(R.id.photoview)
    protected void photoview(){
        startActivity(new Intent(MainActivity.this, PhotoViewActivity.class));
    }

    @OnClick(R.id.annotation)
    protected void annotation(){
        startActivity(new Intent(MainActivity.this, CustomAnnotationsActivity.class));
    }

    @OnClick(R.id.scrollMessage)
    protected void scrollMessage(){
        startActivity(new Intent(MainActivity.this, ScrollMessageActivity.class));
    }

    @OnClick(R.id.asm)
    protected void asm(){
        startActivity(new Intent(MainActivity.this, ASMTestActivity.class));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.e("onDestroy");
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onMessageEvent(MessageEvent event){
        textView.setText(event.message);
    }

    @Subscribe
    public void onMessageEvent(ToastEvent event){
        Toast.makeText(MainActivity.this,event.message,Toast.LENGTH_SHORT).show();
    }

    public void downloadSo(){
        Logger.e("要下载的so库cpu类型为："+CPUFrameworkHelper.getFileNameByCpuType());
        HttpUtils.getInstance().download("http://10.119.25.198:8080/upload/"+ CPUFrameworkHelper.getFileNameByCpuType() +"/libdynamic.so", "/DJ/download", new OnDownloadListener() {
            @Override
            public void onDownloadSuccess() {
                Utils.toastShow(MainActivity.this,"DownloadSuccess");
                Utils.loadSoFile(MainActivity.this,Environment.getExternalStorageDirectory().getAbsolutePath()+"/DJ/download/libdynamic.so");
            }

            @Override
            public void onDownloading(int progress) {
                textView.setText(""+progress);
            }

            @Override
            public void onDownloadFailed() {
                Utils.toastShow(MainActivity.this,"DownloadFailed");
            }
        });
    }

    private ResponseListener<AppInfoBean> responseListener = new ResponseListener<AppInfoBean>() {
        @Override
        public void onStart() {

        }

        @Override
        public void onSuccess(AppInfoBean data) {
            LogUtils.e(data.toString());
            AllenVersionChecker
                    .getInstance()
                    .downloadOnly(
                            UIData.create().setDownloadUrl(data.getUrl()).setContent(data.getContext()).setTitle("客户端更新")
                    )
                    .executeMission(MainActivity.this);
        }

        @Override
        public void onFailed(Throwable e) {
            LogUtils.e(e.getMessage());
        }

        @Override
        public void onFinish() {

        }
    };

    //检查客户端更新
    private void checkAppInfo(){
        NetworkHelper.getInstance().checkAppUpdate("1",responseListener);
    }

    //权限申请
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void requestPermissions(){
        EasyPermissions.requestPermissions(this, "拍照、定位、电话、存储权限申请",
                123, perms);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        //申请权限成功
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        //申请权限失败
    }
}

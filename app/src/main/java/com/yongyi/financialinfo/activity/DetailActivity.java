
package com.yongyi.financialinfo.activity;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yongyi.financialinfo.BuildConfig;
import com.yongyi.financialinfo.R;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.net.URISyntaxException;
import java.util.UUID;

public class DetailActivity extends Activity {

    private String mUrl;

    private MyWebViewClient mWebViewClient;

    private WebView detail;
//    private ImageView placeHolder;

    private JSCallback mJSCallback;
    public static final String SHOW_PLACEHOLDER = "SHOW_PLACEHOLDER";
    private ValueCallback<Uri> uploadMessage;
    private ValueCallback<Uri[]> uploadMessageAboveL;
    private String mCurrentPhotoPath;
    private String mLastPhothPath;
    private Thread mThread;
    private static final int REQUEST_CODE_ALBUM = 0x01;
    private static final int REQUEST_CODE_CAMERA = 0x02;
    private static final int REQUEST_CODE_PERMISSION_CAMERA = 0x03;
    private boolean mShowProgress = true;
    Context mContext;
    private ProgressBar mProgressBar;
    private RelativeLayout mContainerInitialProgress;
    private TextView mTextProgress;
    private boolean mIsFirstLoad = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mContext=this;
        mProgressBar = findViewById(R.id.progressBar);
        mContainerInitialProgress = findViewById(R.id.containerInitialProgress);
        mTextProgress = findViewById(R.id.textProgress);
        detail = findViewById(R.id.webView);

        mUrl = getIntent().getStringExtra("URL");
//        mUrl = "https://ct.ydtbzb.com/";
        init();
    }

    private void init() {
        initWebView();
        detail.loadUrl(mUrl);
    }

    @Override
    public void onBackPressed() {
        if (detail.canGoBack()) {
            detail.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    private void initWebView() {
        detail.setLongClickable(true);
        detail.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        detail.clearHistory();
        detail.clearCache(true);
        detail.clearFormData();
        WebSettings webSettings = detail.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setAllowFileAccess(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);//启用硬件加速
        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setDatabaseEnabled(true);
        webSettings.setBlockNetworkImage(false);// 解决图片不显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//网页加速
            // chromium, enable hardware acceleration
            detail.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            detail.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        }
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath() + "/webcache";
        webSettings.setAppCachePath(appCachePath);
        CookieManager.getInstance().setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(detail, true);
        }
        detail.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        detail.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        detail.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return false;
            }
            //For Android  >= 5.0
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {

                uploadMessageAboveL = filePathCallback;
                uploadPicture();
                return true;
            }


            //For Android  >= 4.1
            public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
                uploadMessage = valueCallback;
                uploadPicture();
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress != 100) {
                    if (mShowProgress) {
                        mProgressBar.setVisibility(View.VISIBLE);
                        mProgressBar.setProgress(newProgress);
                        if (mIsFirstLoad) {
                            mContainerInitialProgress.setVisibility(View.VISIBLE);
                            mTextProgress.setText(newProgress + "%");
                        }
                    }
                } else {
                    mProgressBar.setVisibility(View.GONE);
                    mIsFirstLoad = false;
                    mContainerInitialProgress.setVisibility(View.GONE);
                }
            }
        });
        if (mWebViewClient == null) {
            mWebViewClient = new MyWebViewClient();
        }
        detail.setWebViewClient(mWebViewClient);
        mJSCallback = new JSCallback(this);
        detail.addJavascriptInterface(mJSCallback, "JavaScriptInterface");
    }
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            takePhoto();
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults == null && grantResults.length == 0) {
            return;
        }

        if (requestCode == REQUEST_CODE_PERMISSION_CAMERA) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhoto();
            } else {
                // Permission Denied
                new AlertDialog.Builder(mContext)
                        .setTitle("无法拍照")
                        .setMessage("您未授予拍照权限")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent localIntent = new Intent();
                                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                localIntent.setData(Uri.fromParts("package", getPackageName(), null));
                                startActivity(localIntent);
                            }
                        }).create().show();
            }

        }
    }

    /**
     * 选择相机或者相册
     */
    public void uploadPicture() {

        AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
        builder.setTitle("请选择图片上传方式");

        //取消对话框
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

                //一定要返回null,否则<input type='file'>
                if (uploadMessage != null) {
                    uploadMessage.onReceiveValue(null);
                    uploadMessage = null;
                }
                if (uploadMessageAboveL != null) {
                    uploadMessageAboveL.onReceiveValue(null);
                    uploadMessageAboveL = null;

                }
            }
        });


        builder.setPositiveButton("相机", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                if(!TextUtils.isEmpty(mLastPhothPath)){
                    //上一张拍照的图片删除
                    mThread = new Thread(new Runnable() {
                        @Override
                        public void run() {

                            File file = new File(mLastPhothPath);
                            if(file!= null){
                                file.delete();
                            }
                            mHandler.sendEmptyMessage(1);

                        }
                    });

                    mThread.start();


                }else{

                    //请求拍照权限
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        takePhoto();
                    } else {
                        ActivityCompat.requestPermissions(DetailActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_PERMISSION_CAMERA);
                    }
                }







            }
        });
        builder.setNegativeButton("相册", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                chooseAlbumPic();


            }
        });

        builder.create().show();

    }

    /**
     * 拍照
     */
    private void takePhoto() {

        StringBuilder fileName = new StringBuilder();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileName.append(UUID.randomUUID()).append("_upload.png");
        File tempFile = new File(mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName.toString());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".fileProvider", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        } else {
            Uri uri = Uri.fromFile(tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }

        mCurrentPhotoPath = tempFile.getAbsolutePath();
        startActivityForResult(intent, REQUEST_CODE_CAMERA);


    }

    /**
     * 选择相册照片
     */
    private void chooseAlbumPic() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "Image Chooser"), REQUEST_CODE_ALBUM);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ALBUM || requestCode == REQUEST_CODE_CAMERA) {

            if (uploadMessage == null && uploadMessageAboveL == null) {
                return;
            }

            //取消拍照或者图片选择时
            if (resultCode != RESULT_OK) {
                //一定要返回null,否则<input file> 就是没有反应
                if (uploadMessage != null) {
                    uploadMessage.onReceiveValue(null);
                    uploadMessage = null;
                }
                if (uploadMessageAboveL != null) {
                    uploadMessageAboveL.onReceiveValue(null);
                    uploadMessageAboveL = null;

                }
            }

            //拍照成功和选取照片时
            if (resultCode == RESULT_OK) {
                Uri imageUri = null;

                switch (requestCode) {
                    case REQUEST_CODE_ALBUM:

                        if (data != null) {
                            imageUri = data.getData();
                        }

                        break;
                    case REQUEST_CODE_CAMERA:

                        if (!TextUtils.isEmpty(mCurrentPhotoPath)) {
                            File file = new File(mCurrentPhotoPath);
                            Uri localUri = Uri.fromFile(file);
                            Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri);
                            sendBroadcast(localIntent);
                            imageUri = Uri.fromFile(file);
                            mLastPhothPath = mCurrentPhotoPath;
                        }
                        break;
                }


                //上传文件
                if (uploadMessage != null) {
                    uploadMessage.onReceiveValue(imageUri);
                    uploadMessage = null;
                }
                if (uploadMessageAboveL != null) {
                    uploadMessageAboveL.onReceiveValue(new Uri[]{imageUri});
                    uploadMessageAboveL = null;

                }

            }

        }
    }

    private class JSCallback {
        private Context context;

        JSCallback(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void showToast(String message) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public void openWebPage(String web) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(web));
            startActivity(intent);
        }

        /**
         * 清除缓存
         */
        @JavascriptInterface
        public void clearAllUIWebViewData() {
            detail.post(new Runnable() {
                @Override
                public void run() {
                    detail.clearCache(true);
                    detail.clearFormData();
                }
            });
        }

        /**
         * 后退
         */
        @JavascriptInterface
        public void jumpToBack() {
                detail.post(new Runnable() {
                    @Override
                    public void run() {
                        if (detail.canGoBack()){
                            detail.goBack();
                        }
                    }
                });
        }

        /**
         * 返回首页
         */
        @JavascriptInterface
        public void jumpToHome() {
            detail.post(new Runnable() {
                @Override
                public void run() {
                    detail.loadUrl(mUrl);
                }
            });
        }

        /**
         * 刷新
         */
        @JavascriptInterface
        public void refreshThePage() {
            detail.post(new Runnable() {
                @Override
                public void run() {
                    detail.reload();
                }
            });
        }

        /**
         * 设置StatusBar的背景色和图标颜色
         *
         * @param colorHex         背景色hex值: #FF0000, #FFFFFF...
         * @param isLightStatusBar 是否是浅色状态栏，如果是则会显示深色图标
         */
        @JavascriptInterface
        public void setStatusBarColor(final String colorHex, final boolean isLightStatusBar) {
            detail.post(new Runnable() {
                @Override
                public void run() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Window window = getWindow();
                        window.setStatusBarColor(Color.parseColor(colorHex));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                            View decorView = getWindow().getDecorView();
                            if (decorView != null) {
                                int vis = decorView.getSystemUiVisibility();
                                if (isLightStatusBar) {
                                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                                } else {
                                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                                }
                                decorView.setSystemUiVisibility(vis);
                            }
                        }
                    }
                }
            });
        }

        /**
         * 通过scheme打开app
         */
        @JavascriptInterface
        public void nativeSchemeApp(String s) {
            Uri uri = Uri.parse(s);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (intent.resolveActivity(getPackageManager()) != null) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                Toast.makeText(context, "请先安装app", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openAlipay(WebView webView, String url) {
        try {
            Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "请先安装支付宝", Toast.LENGTH_SHORT).show();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    class MyWebViewClient extends WebViewClient {

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
//            if (View.VISIBLE == placeHolder.getVisibility()) {
//                placeHolder.setVisibility(View.GONE);
//            }
            super.onPageFinished(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("alipays://platformapi") || url.contains("alipay://platformapi")) {
                openAlipay(view, url);
                return true;
            } else if (url.startsWith("weixin://wap/pay?")) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                return true;
            }
            return false;
        }
    }

}

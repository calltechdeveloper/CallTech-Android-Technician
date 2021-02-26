package com.calltechservice.ui.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.MimeTypeFilter;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import com.calltechservice.BaseFragment;
import com.calltechservice.R;
import com.facebook.common.file.FileUtils;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DocumentsFragment extends BaseFragment {

    String cvFile;
    String cvType;
    String policeClearanceFile;
    String policeClearanceType;
    String driversLicenceFile;
    String driversLicenceType;
    private static final int PICKFILE_REQUEST_CODE_CV = 1;
    private static final int PICKFILE_REQUEST_CODE_PC = 2;
    private static final int PICKFILE_REQUEST_CODE_DL = 3;
    private Button submitButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.documents_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] mimeTypes = {
                "application/pdf",
                /*"image/gif",*/
                "image/jpeg",
                "image/png"
        };

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);

        Button cvButton = view.findViewById(R.id.cvButton);
        cvButton.setOnClickListener(v -> startActivityForResult(intent, PICKFILE_REQUEST_CODE_CV));

        Button policeClearanceButton = view.findViewById(R.id.policeClearanceButton);
        policeClearanceButton.setOnClickListener(v -> startActivityForResult(intent, PICKFILE_REQUEST_CODE_PC));

        Button driversLicenceButton = view.findViewById(R.id.driversLicenceButton);
        driversLicenceButton.setOnClickListener(v -> startActivityForResult(intent, PICKFILE_REQUEST_CODE_DL));

        submitButton = view.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(v -> {
            String userId = userPref.getUser().getUserId();
            apiService.uploadIndividualDocs(userId, cvFile, cvType, policeClearanceFile, policeClearanceType, driversLicenceFile, driversLicenceType)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(this::showProgressDialog)
                    .doOnCompleted(this::hideProgressDialog)
                    .subscribe(commonResponse -> {
                        if (commonResponse.getStatus() == 1 && commonResponse.getMessage() != null) {
                            Snackbar.make(view, commonResponse.getMessage(), Snackbar.LENGTH_INDEFINITE).show();
                        } else {
                            String message = "An unexpected error has occured";
                            if (commonResponse.getMessage() != null) {
                                message = commonResponse.getMessage();
                            }
                            Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
                                    .setTextColor(ContextCompat.getColor(view.getContext(), R.color.color_headtext));
                            snackbar.setAction("Ok", (v2) -> snackbar.dismiss()).show();
                            final FrameLayout snackBarView = (FrameLayout) snackbar.getView();
                            /*final FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackBarView.getChildAt(0).getLayoutParams();
                            params.setMargins(params.leftMargin,
                                    params.topMargin,
                                    params.rightMargin,
                                    params.bottomMargin + getNavigationBarHeight(view.getContext()));

                            snackBarView.getChildAt(0).setLayoutParams(params);*/
                            TextView textView = snackBarView.findViewById(com.google.android.material.R.id.snackbar_text);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                textView.setBreakStrategy(Layout.BREAK_STRATEGY_SIMPLE);
                            }
                            textView.setMaxLines(8);
                            hideProgressDialog();
                        }
                    }, throwable -> {
                        hideProgressDialog();
                        if (throwable instanceof ConnectException) {
                            utils.simpleAlert(requireActivity(), requireActivity().getString(R.string.error), requireActivity().getString(R.string.check_network_connection));
                        } else {
                            utils.simpleAlert(requireActivity(), requireActivity().getString(R.string.error), throwable.getMessage());
                        }
                    });
        });
        submitButton.setBackgroundResource(R.drawable.docs_button);
        submitButton.setEnabled(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == Activity.RESULT_OK) {
                Uri selectedFileUri = data.getData();
                setChosenFile(selectedFileUri, requestCode);
            }
        }
    }

    private void setChosenFile(Uri fileUri, int requestCode) {
        try {
            switch (requestCode) {
                case PICKFILE_REQUEST_CODE_PC:
                    policeClearanceFile = base64EncodeFileData(fileUri);
                    policeClearanceType = getMimeType(fileUri);
                    break;
                case PICKFILE_REQUEST_CODE_DL:
                    driversLicenceFile = base64EncodeFileData(fileUri);
                    driversLicenceType = getMimeType(fileUri);
                    break;
                case PICKFILE_REQUEST_CODE_CV:
                default:
                    cvFile = base64EncodeFileData(fileUri);
                    cvType = getMimeType(fileUri);
                    break;
            }
            checkButtonState();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkButtonState() {
        if (policeClearanceFile != null && driversLicenceFile != null && cvFile != null) {
            submitButton.setEnabled(true);
            submitButton.setBackgroundResource(R.color.colorPrimary);
        }
    }

    private String base64EncodeFileData(Uri uri) {
        try {
            InputStream iStream = requireContext().getContentResolver().openInputStream(uri);
            if (iStream != null) {
                byte[] inputData = getBytes(iStream);
                return Base64.encodeToString(inputData, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    public String getMimeType(Uri uri) {
        String extension;

        //Check uri format to avoid null
        if (uri.getScheme() != null && uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //If scheme is a content
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(requireContext().getContentResolver().getType(uri));
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());
        }

        return extension;
    }

    private int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }
}

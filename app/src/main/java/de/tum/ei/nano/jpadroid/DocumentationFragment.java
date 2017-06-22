package de.tum.ei.nano.jpadroid;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DocumentationFragment extends Fragment {

    public DocumentationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_documentation, container, false);

        // Loading Dialog for Typesetting
        // ProgressDialog.show(MainActivity.getMainContext(), "Loading", "Wait while typesetting the documentation...");

        WebView docWebView = (WebView) view.findViewById(R.id.webView_documentation);
        docWebView.setBackgroundColor(Color.TRANSPARENT);
        WebSettings webSettings = docWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        docWebView.loadUrl("file:///android_asset/index.html");

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            //Restore the fragment's state here
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save the fragment's state here
    }

}

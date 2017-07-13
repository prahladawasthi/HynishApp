package cfapps.io.hynish;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HynishFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HynishFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HynishFragment extends Fragment {

    private WebView webView;
    private View mContentView;
    private ProgressBar progressBar;
    String url = "https://hynish.cfapps.io/";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HynishFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HynishFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HynishFragment newInstance(String param1, String param2) {
        HynishFragment fragment = new HynishFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_hynish, container, false);

        if (!Utils.checkInternetConnection(getContext())) {
            Toast.makeText(getContext(), "No Internet!", Toast.LENGTH_SHORT).show();
        } else {

            progressBar = (ProgressBar) mContentView.findViewById(R.id.progressBar2);

            webView = (WebView) mContentView.findViewById(R.id.hynish_webview);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setUseWideViewPort(true);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setDisplayZoomControls(false);
            webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

            webView.loadUrl(url);

            webView.setWebViewClient(new CustomWebViewClient());
            webView.setWebChromeClient(new WebChromeClient() {
                @Override
                public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                    //Required functionality here
                    return super.onJsAlert(view, url, message, result);
                }
            });

            webView.setOnKeyListener(new View.OnKeyListener()

            {
                public boolean onKey (View v,int keyCode, KeyEvent event){
                    if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
                        webView.goBack();
                        return true;
                    }
                    return false;
                }
            });
        }

        return mContentView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
/*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class CustomWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (!Utils.checkInternetConnection(getContext())) {
                Toast.makeText(getContext(), "No Internet!", Toast.LENGTH_SHORT).show();
            } else if (url.endsWith(".pdf")) {
                progressBar.setVisibility(View.VISIBLE);
                String googleDocs = "https://docs.google.com/viewer?url=";
                view.loadUrl(googleDocs + url);

            } else if (url.endsWith(".jpg") || url.endsWith(".jpeg")) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                return true;
            } else {
                progressBar.setVisibility(View.VISIBLE);
                view.loadUrl(url);
            }

            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            progressBar.setVisibility(View.GONE);
            /*//hide loading image
            getActivity().findViewById(R.id.progressBar2).setVisibility(View.GONE);
            //show webview
            getActivity().findViewById(R.id.hynish_webview).setVisibility(View.VISIBLE);*/
        }
    }
}

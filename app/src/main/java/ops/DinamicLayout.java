package ops;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import base.NumberSeparatorTextWatcher;
import base.network.EndPoint;
import base.network.FormCascadingJson;
import base.network.FormJson;
import base.network.FormJson.CallbackForm.Data;
import base.network.FormJson.CallbackForm.Data.Content;
import base.network.NetworkClient;
import base.network.NetworkConnection;
import base.network.SetDataJson;
import base.sqlite.ContentModel;
import base.sqlite.DataModel;
import base.sqlite.FieldModel;
import base.sqlite.FormData;
import base.sqlite.ParameterModel;
import base.sqlite.SQLiteConfigKu;
import base.sqlite.Userdata;
import base.utils.JSONConverter;
import id.co.smmf.mobile.R;
import ops.screen.CameraActivity;
import ops.screen.FormActivity;
import ops.screen.MainActivityDashboard;
import ops.screen.camera.MainActivityCamera;
import ops.screen.fragment.FullEntry;
import ops.screen.fragment.FullEntryList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static base.network.FormCascadingJson.*;
import static base.network.FormCascadingJson.CallbackForm.*;

public class DinamicLayout extends LinearLayout {
    private static String staticArray;
    private Boolean isValid = false;
    private Activity context;
    private Integer index = 0;
    private JSONArray jsonArray, content, array, spinnerArray, arrayObject, multipleArray, resultObject;
    private JSONObject object, objectResult, jsonResult, /*eachObject,*/ data;
    private CardView cardView;
    private EditText editText;
    private TextView labelText, titleText, currencyText;
    private LinearLayout linearLayout, layer1, layer2, dummy, linearContent;
    private RelativeLayout relativeLayout;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button button;
    private Spinner spinner;
    private TextInputLayout textInputLayout;
    private TextInputEditText textInputEditText;
    private ImageView imageView;
    private String codeSpinner, valueSpinner, strSpinner, codeRadio, valueRadio, strRadio, strArray = "", result = "";
    private DecimalFormat decimalFormat;
    private List<EditText> editTexts = new ArrayList<>();
    private List<TextInputEditText> textInputEditTexts = new ArrayList<>();
    private List<TextInputEditText> textInputEditTextDates = new ArrayList<>();
    private List<TextInputLayout> textInputLayouts = new ArrayList<>();
    private List<RadioButton> radioButtons = new ArrayList<>();
    private List<RadioGroup> radioGroups = new ArrayList<>();
    private List<RelativeLayout> relativeLayouts = new ArrayList<>();
    private List<LinearLayout> layers1 = new ArrayList<>(), layers2 = new ArrayList<>(), linearLayouts = new ArrayList<>(), linearContents = new ArrayList<>();
    private List<TextView> textViews = new ArrayList<>(), titleTexts = new ArrayList<>();
    private List<CardView> cardViews = new ArrayList<>();
    private List<Spinner> spinners = new ArrayList<>();
    private List<Button> buttons = new ArrayList<>();
    private List<ImageView> imageviews = new ArrayList<>();
    private ScrollView scrollView;
    private LinearLayout topLayout;
    private String formName = "", form = "";
    private FormData formData;
    public ArrayList<FieldModel> fieldArrayList;
    public ArrayList<FieldModel> fieldArrayListtemp;
    public ArrayList<ContentModel> finalListContent;
    public boolean isfirstload = true;
    public boolean finished = false;
    public Integer countitem = 0;

    FormActivity formActivity;
    private static ProgressDialog progressDialog;


    private EndPoint endPoint;
    private NetworkConnection networkConnection;
    private Userdata userdata;
    private Dialog dialog;
    private SQLiteConfigKu sqLiteConfigKu;
    private ArrayList<ParameterModel> listspinner;
    public String itemChoose,regno,tc,tablename,sectionname, type, datalevel, newdata,imageId, nama;

    public DinamicLayout(Activity context,
                         ArrayList<FieldModel> fieldArrayListtemp,
                         String datalevel,
                         String section,
                         String regno,
                         String tc,
                         String type,
                         String table_name,
                         String formName,
                         String newdata,
                         String imageid,
                         String namanasabah
    ) {
        super(context);
        this.context = context;
        this.sectionname = section;
        this.regno= regno;
        this.tc = tc;
        this.type = type;
        this.tablename = table_name;
        this.formName = formName;
        this.datalevel = datalevel;
        this.newdata = newdata;
        this.fieldArrayList = fieldArrayListtemp;
        this.imageId = imageid;
        this.nama = namanasabah;
        Log.e("INIT","FORM " + fieldArrayList.size());
        Log.e("INIT","FORM " + fieldArrayListtemp.size());
        init();

    }

    private void init() {
        Log.e("INIT","FORM ");
        formData = new FormData(this.context);
        this.decimalFormat = new DecimalFormat("#");
        this.decimalFormat.setMaximumFractionDigits(0);

        sqLiteConfigKu = new SQLiteConfigKu(getContext());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(sqLiteConfigKu.getServer())
                .addConverterFactory(GsonConverterFactory.create())
                .client(NetworkClient.getUnsafeOkHttpClient())
                .build();
        endPoint = retrofit.create(EndPoint.class);

        networkConnection = new NetworkConnection(getContext());
        userdata = new Userdata(getContext());
        finalListContent = new ArrayList<>();
        isfirstload = true;
        formActivity = new FormActivity();


    }

    public Integer countSpinner(String section){
        Integer countspinner = 0;
        try {
            JSONObject jsnobject = new JSONObject(section);
            JSONArray jsonArray = jsnobject.getJSONArray("field");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject explrObject = jsonArray.getJSONObject(i);
                if((explrObject.getString("fieldType").equalsIgnoreCase("dropdown"))) {
                    countspinner++;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
        }
        return  countspinner;
    }

    public void create(String section){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.progress_bar).setCancelable(false);
        }
        dialog = builder.create();
        dialog.show();
//        formActivity.showprogress(context, true);

        Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                dialog.cancel();
            }
        };

        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, countitem * 1500);

//        progress.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialog) {
////                theLayout.setVisibility(View.GONE);
//            }
//        });

        try {
            JSONObject jsnobject = new JSONObject(section);
            JSONArray jsonArray = jsnobject.getJSONArray("field");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject explrObject = jsonArray.getJSONObject(i);
                Log.e("FIELD " + i, String.valueOf(explrObject) );
                createViewForm(explrObject);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
        }
//        if(!isfirstload)
//        form.dismissprogress(context);

//        closeProgressDialog();
//        Log.e("HOOO ","COUNTITEM " + countitem);
    }


    private void createViewForm(final JSONObject explrObject) {
        try {
            scrollView.requestFocus();
            layer1 = new LinearLayout(this.context);
            layer1.setTag("layer");
            layer1.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            layer1.setOrientation(VERTICAL);
            topLayout.addView(layer1);
            layers1.add(layer1);

            titleText = new TextView(this.context);
            titleText.setTag("title");
            titleText.setText(explrObject.getString("fieldLabel"));
            titleText.setTypeface(Typeface.DEFAULT_BOLD);
            titleText.setPadding(10, 0, 0, 10);
            titleText.setTextSize(16f);
            titleText.setTextColor(getResources().getColor(R.color.black));

            cardView = new CardView(this.context);
            cardView.setTag("cardview");
            cardView.setLayoutParams(new CardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            cardView.setPreventCornerOverlap(true);
            cardView.setUseCompatPadding(true);
            cardView.setContentPadding(15, 15, 15, 0);
            cardView.setRadius(2);
            cardView.requestLayout();

            layer2 = new LinearLayout(this.context);
            layer2.setTag("title");
            layer2.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1));
            layer2.setOrientation(VERTICAL);

            labelText = new TextView(this.context);
            labelText.setText(explrObject.getString("fieldLabel"));
            labelText.setTextColor(this.context.getResources().getColor(R.color.textHint));

            layer1.addView(cardView);
            layer2.addView(titleText);
            cardView.addView(layer2);
            cardViews.add(cardView);
            titleTexts.add(titleText);

            linearLayout = new LinearLayout(this.context);
            linearLayout.setTag(explrObject.getString("fieldId"));
            linearLayout.setOrientation(VERTICAL);
            linearLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1));
            linearLayout.setPadding(10, 0, 10, 0);
            layer2.addView(linearLayout);
            linearLayouts.add(linearLayout);


            if (explrObject.getString("fieldType").equalsIgnoreCase("dropdown")) {
                spinner = new Spinner(this.context);
                Log.e("FIELD NAME ", " hasReference :" + explrObject.getString("fieldId"));
//                spinner.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                spinner.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
//                spinner.setPadding(5,0,0,5);
                spinner.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1) {{
                    gravity = Gravity.LEFT;
                }});

                ArrayList<ContentModel> listContent = new ArrayList<>();
                ArrayList<String> list = new ArrayList<>();
                if (explrObject.getString("referenceType").equalsIgnoreCase("reference")) {
                    String item = formData.select(explrObject.getString("referenceName"));
                    spinner.setTag(explrObject.getString("fieldId"));
                    try {
                        JSONObject jsnobject1 = new JSONObject(item);
                        JSONArray jsonArray = jsnobject1.getJSONArray("content");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject explrObjectdesc = jsonArray.getJSONObject(i);
                            ContentModel contentmodel = new ContentModel();
                            contentmodel.setDataDesc(explrObjectdesc.getString("dataDesc"));
                            contentmodel.setDataId(explrObjectdesc.getString("dataId"));
                            listContent.add(contentmodel);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (explrObject.getString("referenceType").equalsIgnoreCase("cascading")) {
                    spinner.setTag(explrObject.getString("fieldId"));
                    String fieldvaluename = "", fieldvalue = "", value = "", fieldid = "";
                    JSONArray jsonArrayreferences = explrObject.getJSONArray("referenceParameter");
                    ArrayList referenceParameter = new ArrayList<ParameterModel>();
                    for (int i = 0; i < jsonArrayreferences.length(); i++) {
                        JSONObject objectparameter = jsonArrayreferences.getJSONObject(i);
                        fieldvaluename = objectparameter.getString("parameterName");
                        fieldvalue = objectparameter.getString("parameterFieldValue");
//                            fieldid = objectparameter.getString("parameterId");
//                        Log.e("CASCADING NAME ", " : " + fieldvalue);
//                        Log.e("CASCADING HAA ", " : " + fieldvaluename);
                        ParameterModel param = new ParameterModel();
//                            for(ParameterModel parammodel : listspinnerparam){
                        for (FieldModel model : fieldArrayList) {
                            if (fieldvalue.equalsIgnoreCase(model.getFieldName())) {
                                value = model.getFieldValue() == null || model.getFieldValue().equalsIgnoreCase("") ? "null" : model.getFieldValue();
//                                Log.e("ADDA", " ISI TEXTBOX : " + model.getFieldValue());
                            }
                        }
//                            }
                        param.setParameterName(fieldvaluename);
                        param.setParameterValue(value);
                        referenceParameter.add(param);
                    }
                    listContent = getCascadingdata(referenceParameter, explrObject.getString("fieldName"));

                }
                for (ContentModel contentModel : listContent) {
                    list.add(contentModel.getDataDesc().toUpperCase());
                }
//                list.notify();
                spinner.setAdapter(new ArrayAdapter<String>(this.context, android.R.layout.simple_spinner_item,
                        list) {

                    @NonNull
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {

                        View v = super.getView(position, convertView, parent);
                        v.setPadding(v.getPaddingLeft(),15, 0, 15);
                        ((TextView) v).setGravity(Gravity.LEFT);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            ((TextView) v).setTextAlignment(TEXT_ALIGNMENT_TEXT_START);
                            itemChoose = ((TextView) v).getText().toString();
                        }
                        return v;
                    }

                    @Override
                    public View getDropDownView(int position, View convertView, ViewGroup parent) {

                        View v = super.getDropDownView(position, convertView, parent);

                        ((TextView) v).setGravity(Gravity.CENTER);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            ((TextView) v).setTextAlignment(TEXT_ALIGNMENT_TEXT_START);
                        }

                        return v;

                    }

                    {
                        setDropDownViewResource(android.R.layout
                                .simple_spinner_dropdown_item);
                    }
                });


                for (ContentModel model : listContent) {
                    ContentModel modelcontent = new ContentModel();
                    modelcontent.setDataId(model.getDataId());
                    modelcontent.setDataDesc(model.getDataDesc().toUpperCase());
                    modelcontent.setIndexData(listContent.indexOf(model));
                    finalListContent.add(modelcontent);
                }

/*
                for (FieldModel fieldmodel : fieldArrayList){
                    if(spinner.getTag().toString().equalsIgnoreCase(fieldmodel.getFieldId().toString())){
                        for (ContentModel model : finalListContent){
                            if (fieldmodel.getFieldValue().toString().equalsIgnoreCase(model.getDataId().toString())) {
                                spinner.setSelection(model.getIndexData());

                            }
                        }

                    }
                }
*/

                layer2.addView(spinner);
                spinners.add(spinner);


//                Object id = null;
//                spinner.setOnItemSelectedListener(new MyOnItemSelectedListener(fieldArrayList, explrObject,formName));

                Log.e("WOOOO ", "BOOLEANISFIRST " +isfirstload);
                if(spinners.size() == countitem) {
                    isfirstload = false;
                    Log.e("WAA ", "GET SPINNER " + spinner.getTag());
//                    if (explrObject.getString("referenceType").equalsIgnoreCase("cascading")) {
                        for (final Spinner spinnerclick : spinners) {

                            spinnerclick.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    Log.e("WAA ", "GET SPINNER " + spinnerclick.getTag());

                                    try {
                                        String section = formData.select(formName);
                                        ArrayList referenceParameter = new ArrayList<ParameterModel>();
                                        String fieldvaluename = "", fieldvalue = "", value = "", fieldid = "", fieldIdToUpdate = "", fieldNametoUpdate = "";
                                        JSONObject jsnobject = new JSONObject(section);
                                        JSONArray jsonArray = jsnobject.getJSONArray("field");
                                        for (int loop = 0; loop < jsonArray.length(); loop++) {
                                            JSONObject explrObject = jsonArray.getJSONObject(loop);
                                            JSONArray jsonArrayreferences = explrObject.getJSONArray("referenceParameter");
                                            for (int index = 0; index < jsonArrayreferences.length(); index++) {
                                                JSONObject objectparameter = jsonArrayreferences.getJSONObject(index);
                                                fieldvaluename = objectparameter.getString("parameterName");
                                                fieldvalue = objectparameter.getString("parameterFieldValue");
                                                fieldid = objectparameter.getString("parameterId");
                                                Log.e("HAAAAAAAA PARAMEETER " ," : "+ fieldvaluename);
                                                ParameterModel param = new ParameterModel();
                                                if (fieldid.equalsIgnoreCase(spinnerclick.getTag().toString())) {
                                                    fieldIdToUpdate = explrObject.getString("fieldId");
                                                    fieldNametoUpdate = explrObject.getString("fieldName");

                                                    if (finalListContent.size() > 0) {
                                                        for (ContentModel model : finalListContent) {
                                                            if (spinnerclick.getSelectedItem().toString().equalsIgnoreCase(model.getDataDesc().toUpperCase())) {
                                                                param.setParameterValue(model.getDataId() == null ? "null" : model.getDataId());
                                                            }
                                                        }
                                                        param.setParameterName(objectparameter.getString("parameterName"));
                                                        referenceParameter.add(param);
                                                    }
                                                }else{
                                                    for (FieldModel model : fieldArrayList) {
                                                        if (fieldvalue.equalsIgnoreCase(model.getFieldName())) {
                                                            value = model.getFieldValue() == null || model.getFieldValue().equalsIgnoreCase("") ? "null" : model.getFieldValue();
                                                        }
                                                    }
                                                    param.setParameterName(fieldvaluename);
                                                    param.setParameterValue(value);
                                                    referenceParameter.add(param);
                                                }
                                            }
                                            if (explrObject.getString("referenceType").equalsIgnoreCase("cascading")) {
                                                if (spinnerclick.getSelectedItemPosition() != 0) {
                                                    setCascadingData(referenceParameter, fieldIdToUpdate, fieldNametoUpdate);
                                                }
                                            }
                                        }
                                        Log.e("WAA ", " LAS " + spinnerclick.getTag());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                }
                            });

                        }
//                    }
                }

                for (FieldModel fieldmodel : fieldArrayList){
                    if(spinner.getTag().toString().equalsIgnoreCase(fieldmodel.getFieldId().toString())){
                        for (ContentModel model : finalListContent){
                            if (fieldmodel.getFieldValue().toString().equalsIgnoreCase(model.getDataId().toString())) {
                                spinner.setSelection(model.getIndexData());

                            }
                        }
                    }
                }

                try {
                    Field popup = Spinner.class.getDeclaredField("mPopup");
                    popup.setAccessible(true);
                    ListPopupWindow popupWindow = (ListPopupWindow) popup.get(spinner);
                    popupWindow.setHeight(600);
                } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) { }
            }else if(explrObject.getString("fieldType").equalsIgnoreCase("textbox")){
                Log.e("INI"," fieldArrayListtemp : "+fieldArrayList.size());
                textInputEditText = new TextInputEditText(this.context);
                textInputEditText.setBackground(null);
                textInputEditText.setTag(explrObject.getString("fieldId"));
                textInputEditText.setHint(explrObject.getString("fieldLabel"));
                textInputEditText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(Integer.valueOf(explrObject.getString("fieldDataLength"))) });
                if(explrObject.getString("fieldRule").equalsIgnoreCase("decimal")) {
                    textInputEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
//                    textInputEditText.addTextChangedListener(new NumberSeparatorTextWatcher(textInputEditText));
                }else if(explrObject.getString("fieldRule").equalsIgnoreCase("numericonly")) {
                    textInputEditText.setInputType(InputType.TYPE_CLASS_NUMBER );
                }else{
                    textInputEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                }
                textInputEditText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                textInputEditText.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));
                textInputEditTexts.add(textInputEditText);
                layer2.addView(textInputEditText);

//             e
                for(FieldModel model : fieldArrayList){
//                    if (explrObject.getString("fieldId").equalsIgnoreCase(model.getFieldId())) {
//                        Log.e("ADDA", " ISI TEXTBOX : " + model.getFieldValue());
                    for(TextInputEditText text : textInputEditTexts)
                        if(model.getFieldId().equalsIgnoreCase(text.getTag().toString()))
                            textInputEditText.setText(model.getFieldValue().toUpperCase());
//                        textInputEditTexts.add(textInputEditText);
//                    }
                }

            }else if(explrObject.getString("fieldType").equalsIgnoreCase("label")){

                textInputEditText = new TextInputEditText(this.context);
                textInputEditText.setTag(explrObject.getString("fieldId"));
                textInputEditText.setHint(explrObject.getString("fieldLabel"));
                textInputEditText.setInputType(EditorInfo.TYPE_CLASS_TEXT);
                textInputEditText.setBackground(null);
                textInputEditText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                textInputEditText.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));

                for(FieldModel model : fieldArrayList){
                    if (explrObject.getString("fieldId").equalsIgnoreCase(model.getFieldId())) {
                        Log.e("ADDA", " ISI LABEL : " + model.getFieldValue());
                        textInputEditText.setText( model.getFieldValue().toUpperCase());
                        textInputEditText.setEnabled(false);
                    }
                }
                textInputEditTexts.add(textInputEditText);
                layer2.addView(textInputEditText);
            }else if(explrObject.getString("fieldType").equalsIgnoreCase("upload file")){
                button = new Button(this.context);
                button.setTag(explrObject.getString("fieldId"));
                button.setText("pilih file".toUpperCase());
                button.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);
                button.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1) {{
                    gravity = Gravity.CENTER;
                }});
                buttons.add(button);
                button.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e(" HALLO ","ID NYA : " + itemChoose + " "+ tc + " " + regno);
                        Intent intentuserdata = new Intent(getContext(), CameraActivity.class);
                        intentuserdata.putExtra("SECTION_NAME",sectionname);
                        intentuserdata.putExtra("REGNO" ,regno);
                        intentuserdata.putExtra("TC",tc);
                        intentuserdata.putExtra("TYPE",type);
                        intentuserdata.putExtra("TABLE_NAME",tablename);
                        intentuserdata.putExtra("FORM_NAME",formName);
                        intentuserdata.putExtra("UPLOAD_TYPE","surveyDocument");
                        intentuserdata.putExtra("IMAGEID",imageId);
                        for(FieldModel model : fieldArrayList){
                            if(model.getFieldName().equalsIgnoreCase("DOC_CODE")) {
                                intentuserdata.putExtra("DOC_CODE", model.getFieldValue());
                                Log.e("DOC_CODE APALAH", " : " + model.getFieldValue());
                            }
////                            Log.e("DOC NAME APALAH22"," : " + model.getFieldName());

                            if(model.getFieldName().equalsIgnoreCase("DOC_ID"))
                                intentuserdata.putExtra("DOC_ID",model.getFieldValue());
//
                        }
                        intentuserdata.putExtra("UPLOAD_TYPE","surveyDocument");
                        context.startActivity(intentuserdata);
                    }
                });

                for(FieldModel model : fieldArrayList){

                    Log.e("ADDA", " ISI UPLOAD FILE : " + model.getFieldValue());
                    if(!model.getFieldValue().equalsIgnoreCase("")) {
                        for (Button button : buttons)
                            if (model.getFieldId().equalsIgnoreCase(button.getTag().toString())) {
                                imageView = new ImageView(this.context);
                                imageView.setTag(explrObject.getString("fieldId") + "imgView");
                                imageView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));
                                layer2.addView(imageView);
                                imageviews.add(imageView);
                                URL newurl = new URL( model.getFieldValue());
                                Picasso.with(getContext()).load(model.getFieldValue()).placeholder(R.drawable.ic_person_white_24dp)// Place holder image from drawable folder
                                        .error(R.drawable.ic_person_white_24dp).resize(400, 400).centerCrop()
                                        .into(imageView);
                            }
                    }
                }

                layer2.addView(button);
            }else if(explrObject.getString("fieldType").equalsIgnoreCase("textarea")){
                textInputEditText = new TextInputEditText(this.context);
                textInputEditText.setTag(explrObject.getString("fieldId"));
                textInputEditText.setText(explrObject.getString("fieldLabel"));
                textInputEditText.setInputType(EditorInfo.TYPE_CLASS_TEXT);
                textInputEditText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                textInputEditText.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));

                for(FieldModel model : fieldArrayList){
                    if (explrObject.getString("fieldId").equalsIgnoreCase(model.getFieldId())) {
                        textInputEditText.setText( model.getFieldValue());
//                        textInputEditText.setEnabled(false);
                    }
                }
                textInputEditTexts.add(textInputEditText);
                layer2.addView(textInputEditText);
            }else if(explrObject.getString("fieldType").equalsIgnoreCase("date")) {

                textInputEditText = new TextInputEditText(this.context);
                textInputEditText.setInputType(EditorInfo.TYPE_CLASS_TEXT);
                textInputEditText.setTag(explrObject.getString("fieldId"));
                textInputEditText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                textInputEditText.setBackground(null);
                textInputEditText.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));

                for (FieldModel model : fieldArrayList) {
                    if (explrObject.getString("fieldId").equalsIgnoreCase(model.getFieldId())) {
                        Log.e("24 ADDA", " ISI DATE: " + explrObject.getString("fieldId"));
                        textInputEditText.setText(model.getFieldValue());
//                        textInputEditText.setEnabled(false);
//                        textInputEditTextDates.add(textInputEditText);
                    }
                }
                textInputEditTextDates.add(textInputEditText);
                layer2.addView(textInputEditText);
                for (final TextInputEditText text : textInputEditTextDates) {

                    final Calendar myCalendar = Calendar.getInstance();
                    final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear,
                                              int dayOfMonth) {
                            // TODO Auto-generated method stub
                            myCalendar.set(Calendar.YEAR, year);
                            myCalendar.set(Calendar.MONTH, monthOfYear);
                            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            String myFormat = "dd-MM-yyyy"; //In which you need put here
                            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                            text.setText(sdf.format(myCalendar.getTime()));
                            Log.e("MASUK", "MASUK TAG" + text.getTag());
                            Log.e("MASUK", "MASUK ID" + sdf.format(myCalendar.getTime()));
                        }

                    };


                    text.setOnFocusChangeListener(new OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View view, boolean b) {
                            if(!b){

                            }else {
                                new DatePickerDialog(getContext(), date, myCalendar
                                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                                String myFormat = "dd-MM-yyyy"; //In which you need put here
                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                                Log.e("MASUK ON FOCUS", "MASUK TAG" + text.getTag());
                                Log.e("MASUK ON FOCUS", "MASUK ID" + sdf.format(myCalendar.getTime()));
                            }
                        }
                    });
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void createItem(JSONObject object) {
        try {
            scrollView.requestFocus();
        } catch (Exception e){
            e.printStackTrace();
        }

        try {
            layer1 = new LinearLayout(this.context);
            layer1.setTag(object.getString("data"));
            layer1.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            layer1.setOrientation(VERTICAL);
            topLayout.addView(layer1);
            layers1.add(layer1);

            titleText = new TextView(this.context);
            titleText.setTag(object.getString("data"));
            titleText.setText(object.getString("data").replace("_", " "));
            titleText.setPadding(0, 0, 0, 10);
            titleText.setTextSize(16f);
            titleText.setTextColor(getResources().getColor(R.color.black));

            cardView = new CardView(this.context);
            cardView.setTag(object.getString("data"));
            cardView.setLayoutParams(new CardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            cardView.setPreventCornerOverlap(true);
            cardView.setUseCompatPadding(true);
            cardView.setContentPadding(15, 15, 15, 15);
            cardView.setRadius(2);
            cardView.requestLayout();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void getTextInput(String section, final String regno, final String tc, final String form_name, final String table_name, boolean send){
        fieldArrayListtemp = new ArrayList<>();
        try {
            JSONObject jsnobject = new JSONObject(section);
            JSONArray jsonArray = jsnobject.getJSONArray("field");
            finished = false;
            for (int i = 0; i < jsonArray.length() && !finished; i++) {
                JSONObject explrObject = jsonArray.getJSONObject(i);
                setValue(explrObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(send && !finished)
            sendData(regno, tc, table_name, form_name);
        if(!send) {
            new MaterialDialog.Builder(getContext())
                    .content("Delete Data ?")
                    .positiveText(R.string.buttonYes)
                    .negativeText(R.string.buttonNo)
                    .callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            dialog.dismiss();
                            deleteData(regno, tc, table_name, form_name);
                        }
                        public void onNegative(MaterialDialog dialog) {
                            dialog.dismiss();
                        }
                    })
                    .cancelable(true)
                    .show();

        }
    }

    public void setValue(JSONObject explrObject){

        try {
            if (explrObject.getString("fieldType").equalsIgnoreCase("dropdown")){
                for(Spinner spinner : spinners) {
                   if(spinner.getTag()!= null){
                    if (spinner.getTag().toString().equalsIgnoreCase(explrObject.getString("fieldId"))) {
                        FieldModel model = new FieldModel();
//                            Log.e("11 SPINNER "," TAG " + spinner.getTag());
//                            Log.e("22 SPINNER "," TAG " + explrObject.getString("fieldId"));
//                            Log.e("33 SPINNER "," TAG " + explrObject.getString("fieldName"));
//                            Log.e("44 SPINNER "," TAG " + spinner.getSelectedItem());
                        model.setFieldName(explrObject.getString("fieldName"));
                        for (ContentModel contentModel : finalListContent) {
                            Log.e("44SPINNER ", " TAG " + spinner.getTag());
                            if(explrObject.getString("fieldMandatory").equalsIgnoreCase("1") && spinner.getSelectedItemPosition()==0) {
                                dialogMessage(getResources().getString(R.string.errorMandatoryPhoto) + " " + explrObject.getString("fieldLabel"));
                                finished = true;
                                break;
                            }
                            if (spinner.getSelectedItem() != null &&
                                    contentModel.getDataDesc() != null) {
                                if (spinner.getSelectedItem().toString().equalsIgnoreCase(contentModel.getDataDesc().toUpperCase())) {
                                    model.setFieldValue(contentModel.getDataId() == null ? "null" : contentModel.getDataId());
                                }
                            } else {
                                model.setFieldValue("null");
                            }
                        }
                        fieldArrayListtemp.add(model);
                    }
                }
                }
            }else if(explrObject.getString("fieldType").equalsIgnoreCase("textarea")) {
                for (TextInputEditText text : textInputEditTexts) {
                    if (text.getTag().toString().equalsIgnoreCase(explrObject.getString("fieldId"))) {
                        FieldModel model = new FieldModel();
                        model.setFieldName(explrObject.getString("fieldName"));
                        if(explrObject.getString("fieldMandatory").equalsIgnoreCase("1") && text.getText().toString().equalsIgnoreCase("")){
                            dialogMessage(getResources().getString(R.string.errorMandatoryPhoto) +" "+ explrObject.getString("fieldLabel"));
                            finished = true;
                            break ;
                        }
                        model.setFieldValue(text.getText().length() > 0 ? text.getText().toString() :"null" );
                        fieldArrayListtemp.add(model);
                    }
                }
            }else if(explrObject.getString("fieldType").equalsIgnoreCase("textbox")){
                for (TextInputEditText text : textInputEditTexts) {
                    if (text.getTag().toString().equalsIgnoreCase(explrObject.getString("fieldId"))) {
                        FieldModel model = new FieldModel();
                        model.setFieldName(explrObject.getString("fieldName"));
                        if(explrObject.getString("fieldMandatory").equalsIgnoreCase("1") && text.getText().toString().equalsIgnoreCase("")){
                            dialogMessage(getResources().getString(R.string.errorMandatoryPhoto) +" "+ explrObject.getString("fieldLabel"));
                            finished = true;
                            break ;
                        }
                        if(text.getText().length() > Integer.valueOf(explrObject.getString("fieldDataLength"))){
                            dialog(R.string.errorMaxNama);
                            finished = true;
                            break ;
                        }
                        model.setFieldValue(text.getText().length() > 0 ? text.getText().toString().replace(",", "") :"null");
                        fieldArrayListtemp.add(model);
                    }
                }
            }else if(explrObject.getString("fieldType").equalsIgnoreCase("date")){
                for(TextInputEditText text : textInputEditTextDates){
                    if(text.getTag().toString().equalsIgnoreCase(explrObject.getString("fieldId"))){
                        FieldModel model = new FieldModel();
                        model.setFieldName(explrObject.getString("fieldName"));
                        if(explrObject.getString("fieldMandatory").equalsIgnoreCase("1") && text.getText().toString().equalsIgnoreCase("")){
                            dialogMessage(getResources().getString(R.string.errorMandatoryPhoto) +" "+ explrObject.getString("fieldLabel"));
                            finished = true;
                            break ;
                        }
                        model.setFieldValue(text.getText().length() > 0 ? text.getText().toString() :"null");
                        fieldArrayListtemp.add(model);
                    }
                }
            }else if(explrObject.getString("fieldType").equalsIgnoreCase("label")){
                for(TextInputEditText text : textInputEditTexts){
                    if(text.getTag().toString().equalsIgnoreCase(explrObject.getString("fieldId"))){
                        FieldModel model = new FieldModel();
                        model.setFieldName(explrObject.getString("fieldName"));
                        model.setFieldValue(text.getText().length() > 0 ? text.getText().toString() : "null");
                        fieldArrayListtemp.add(model);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendData(String regno, String tc, String table_name, String form_name){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.progress_bar).setCancelable(false);
        }
        dialog = builder.create();
        dialog.show();
        if (!networkConnection.isNetworkConnected()) {
            dialog.dismiss();
            dialog(R.string.errorNoInternetConnection);
        } else {

            for(FieldModel model : fieldArrayListtemp){
                dialog.dismiss();
            }
            final SetDataJson.SetDataRequest request = new SetDataJson().new SetDataRequest();
            request.setRegno(regno);
            request.setUserid(userdata.select().getUserid());
            request.setTc(tc);
            request.setFormName(form_name);
            request.setTableName(table_name);
            request.setField(fieldArrayListtemp);


            Call<SetDataJson.SetDataCallback> callBack = endPoint.setData(userdata.select().getAccesstoken(), request);
            callBack.enqueue(new Callback<SetDataJson.SetDataCallback>() {
                @Override
                public void onResponse(Call<SetDataJson.SetDataCallback> call, Response<SetDataJson.SetDataCallback> response) {
                    Log.e("dialogSukses ","HORE ");
                    if (response.isSuccessful()) {
                        dialog.dismiss();
                        if (response.body().getStatus().equalsIgnoreCase("1")) {
                            dialogsuksessubmit(response.body().getMessage());
                        } else if(response.body().getStatus().equalsIgnoreCase("0")){
                            dialogMessage(response.body().getMessage());
                        }
                    } else{
                        Log.e("dialog gagal dua ","HORE ");
                        dialogMessage(response.body().getMessage());

                    }
                }

                @Override
                public void onFailure(Call<SetDataJson.SetDataCallback> call, Throwable t) {
                    dialog.dismiss();
                    dialog(R.string.errorBackend);
                }
            });
        }



    }

    public void deleteData(String regno, String tc, String table_name, String form_name){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.progress_bar).setCancelable(false);
        }
        dialog = builder.create();
        dialog.show();
        if (!networkConnection.isNetworkConnected()) {
            dialog.dismiss();
            dialog(R.string.errorNoInternetConnection);
        } else {

            for(FieldModel model : fieldArrayListtemp){
                dialog.dismiss();
            }
            final SetDataJson.SetDataRequest request = new SetDataJson().new SetDataRequest();
            request.setRegno(regno);
            request.setUserid(userdata.select().getUserid());
            request.setTc(tc);
            request.setFormName(form_name);
            request.setTableName(table_name);
            request.setField(fieldArrayListtemp);


            Call<SetDataJson.SetDataCallback> callBack = endPoint.deleteData(userdata.select().getAccesstoken(), request);
            callBack.enqueue(new Callback<SetDataJson.SetDataCallback>() {
                @Override
                public void onResponse(Call<SetDataJson.SetDataCallback> call, Response<SetDataJson.SetDataCallback> response) {
                    Log.e("dialogSukses ","HORE ");
                    if (response.isSuccessful()) {
                        dialog.dismiss();
                        if (response.body().getStatus().equalsIgnoreCase("1")) {
                            dialogsuksessubmit(response.body().getMessage());
                        } else if(response.body().getStatus().equalsIgnoreCase("0")){
                            dialogMessage(response.body().getMessage());
                        }
                    } else{
                        Log.e("dialog gagal dua ","HORE ");
                        dialogMessage(response.body().getMessage());

                    }
                }

                @Override
                public void onFailure(Call<SetDataJson.SetDataCallback> call, Throwable t) {
                    dialog.dismiss();
                    dialog(R.string.errorBackend);
                }
            });
        }



    }


    public ArrayList<FieldModel> getFieldArrayList(){
        ArrayList<FieldModel> fieldModelArrayList = new ArrayList<>();
        fieldModelArrayList = fieldArrayListtemp;
        return fieldModelArrayList;
    }

    public  ArrayList<FieldModel> fieldArrayListtemp(){
        ArrayList<FieldModel> fieldArrayListtemp = new ArrayList<FieldModel>();

        return  fieldArrayListtemp;
    }

    public void setTopLayout(LinearLayout view){
        this.topLayout = view;
    }

    public void setScrollView(ScrollView view){
        this.scrollView = view;
    }


    public void setData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            try {
                createItem(array.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public void setCascadingData(ArrayList<ParameterModel> listspinnerparam, final String fieldId, String field) {
        ArrayList<ContentModel> list = new ArrayList<>();
        ArrayList<String> listString = new ArrayList<>();
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            builder.setView(R.layout.progress_bar).setCancelable(false);
//        }
//        dialog = builder.create();
//        dialog.show();
//        showProgressDialog();
        Log.e("MASUK","PROGRESSBARR");
        if (!networkConnection.isNetworkConnected()) {
            dialog.dismiss();
            dialog(R.string.errorNoInternetConnection);
        } else {
            try {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                final FormCascadingJson.RequestForm requestForm = new FormCascadingJson().new RequestForm();
                requestForm.setType("cascading");
                requestForm.setField(field);
                requestForm.setParameter(listspinnerparam);
                Call<FormCascadingJson.CallbackForm> callbackFormCall = endPoint.getDataMasterCascading(userdata.select().getAccesstoken(), requestForm);
                Response<FormCascadingJson.CallbackForm> response = callbackFormCall.execute();
                if (response.isSuccessful()) {
                    Log.e("PROGRESSBARR ", " PROGRESSBARR ");
//                    FormActivity form = new FormActivity();
//                    form.dismissprogress(context);
//                    dialog.dismiss();
                    if(response.body().getData() != null){
                        for (FormCascadingJson.CallbackForm.DataCascading data : response.body().getData()) {
                            //                        ArrayList<String> listdalam = null;
                            for (FormCascadingJson.CallbackForm.DataCascading.Content content : data.getContent()) {
                                listString.add(content.getDataDesc().toUpperCase());
                                ContentModel paramodel = new ContentModel();
                                paramodel.setDataId(content.getDataId());
                                paramodel.setDataDesc(content.getDataDesc().toUpperCase());
                                list.add(paramodel);
                            }
                        }
                    }else{
                        Log.e("DATALIST ", " KOSONG ");
                    }

                } else{
                    Log.e("FAILED  ", "" );
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        updateSpinnerItem(fieldId,list, listString);
    }


    public ArrayList<ContentModel> getCascadingdata(ArrayList<ParameterModel> listspinnerparam, String field) {
        Log.e("YAA MASUK ", "getcascading ");
        ArrayList<ContentModel> list = new ArrayList<>();
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            builder.setView(R.layout.progress_bar).setCancelable(false);
//        }
//        dialog = builder.create();
//        dialog.show();
//        showProgressDialog();
        if (!networkConnection.isNetworkConnected()) {
            dialog.dismiss();
            dialog(R.string.errorNoInternetConnection);
        } else {
            try {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                final FormCascadingJson.RequestForm requestForm = new FormCascadingJson().new RequestForm();
                requestForm.setType("cascading");
                requestForm.setField(field);
                requestForm.setParameter(listspinnerparam);
                Call<FormCascadingJson.CallbackForm> callbackFormCall = endPoint.getDataMasterCascading(userdata.select().getAccesstoken(), requestForm);
                Response<FormCascadingJson.CallbackForm> response = callbackFormCall.execute();
                if (response.isSuccessful()) {
                    for (FormCascadingJson.CallbackForm.DataCascading data : response.body().getData()) {
//                        ArrayList<String> listdalam = null;
                        for (FormCascadingJson.CallbackForm.DataCascading.Content content : data.getContent()) {
                            ContentModel contentmodel = new ContentModel();
                            contentmodel.setDataDesc(content.getDataDesc().toUpperCase());
                            contentmodel.setDataId(content.getDataId());
                            list.add(contentmodel);
                        }
                    }
//                     updateSpinnerItem(fieldId, list);
                    Log.e("DATALIST ", " FIeld isi : " + list.size());
                } else{
                    Log.e("FAILED  ", " YAA  : " );
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    protected void dialogsuksessubmit(String rString) {
        MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                .title(rString).icon(getResources().getDrawable(R.mipmap.ic_launcher))
                .positiveText(R.string.buttonClose)
                .positiveColor(getResources().getColor(R.color.black))
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {

//                        intent.putExtra("SECTION_NAME", sectionname);
                        Log.e("HIII ", " : " + regno);
                        Log.e("HUUU ", " : " + tc + " HO " + formName + " HUOIA " +type);

                        if(datalevel.equalsIgnoreCase("listfield")) {
                            Intent intent = new Intent(context, FullEntryList.class);
                            intent.putExtra("REGNO", regno);
                            intent.putExtra("TC", tc);
                            intent.putExtra("TYPE", type);
                            intent.putExtra("FORM_NAME", formName);
                            intent.putExtra("TABLE_NAME", tablename);
                            intent.putExtra("NAMA_NASABAH", nama);
                            context.startActivity(intent);
                        }else {
                            if(newdata != null && newdata.equalsIgnoreCase("1")) {
                                Intent i = new Intent(context, MainActivityDashboard.class);
                                i.putExtra("FLAG_SUBMIT", "1");
                                context.startActivity(i);
                            }else{
                                Intent intent = new Intent(context, FullEntry.class);
                                intent.putExtra("REGNO", regno);
                                intent.putExtra("TC", tc);
                                intent.putExtra("TYPE", type);
                                intent.putExtra("FORM_NAME", formName);
                                intent.putExtra("TABLE_NAME", tablename);
                                context.startActivity(intent);
                            }
                        }
                        dialog.dismiss();
                    }
                })
                .cancelable(true)
                .build();
        dialog.show();
    }

    protected void dialog(int rString) {
        MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                .title(rString).icon(getResources().getDrawable(R.mipmap.ic_launcher))
                .positiveText(R.string.buttonClose)
                .positiveColor(getResources().getColor(R.color.black))
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .cancelable(true)
                .build();
        dialog.show();
    }
    public void showProgressDialog(){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(R.string.dialogMessageLoading));
        progressDialog.isIndeterminate();
        progressDialog.setCancelable(true);
        progressDialog.show();
    }
    public void closeProgressDialog(){
        progressDialog.dismiss();
    }

    protected void dialogMessage(String rString) {
        MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                .title(rString).icon(getResources().getDrawable(R.mipmap.ic_launcher))
                .positiveText(R.string.buttonClose)
                .positiveColor(getResources().getColor(R.color.black))
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .cancelable(true)
                .build();
        dialog.show();
    }
    public void updateSpinnerItem(String fieldid, ArrayList<ContentModel> list, ArrayList<String> liststringluar){
        ArrayList<String> listring = new ArrayList<>();
        int i = 0;
        for(ContentModel contentModel : list){
            ContentModel contentModeha = new ContentModel();
            listring.add(contentModel.getDataDesc().toUpperCase());
            contentModeha.setDataId(contentModel.getDataId());
            contentModeha.setDataDesc(contentModel.getDataDesc().toUpperCase());
            contentModeha.setIndexData(i);
            finalListContent.add(contentModeha);
            i++;

        }
        for(Spinner spinneritem : spinners) {
            Log.e("CASCADINGTAG ", " ADALAH " + spinneritem.getTag());
            Log.e("CASCADINGTAG ", " HUHHHH " + fieldid);
            if (spinneritem.getTag() != null){
                if (spinneritem.getTag().toString().equalsIgnoreCase(fieldid)) {
                    if (liststringluar != null) {
                        spinneritem.setAdapter(new ArrayAdapter<String>(this.context, android.R.layout.simple_spinner_item,
                                liststringluar) {

                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                View v = super.getView(position, convertView, parent);
                                v.setPadding(v.getPaddingLeft(), v.getPaddingTop(), 0, v.getPaddingBottom());
                                ((TextView) v).setGravity(Gravity.LEFT);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                    ((TextView) v).setTextAlignment(TEXT_ALIGNMENT_CENTER);
                                    Log.e("CASCADING ", " ITEM " + ((TextView) v).getText());
                                    itemChoose = ((TextView) v).getText().toString();
                                }
                                return v;
                            }

                            @Override
                            public View getDropDownView(int position, View convertView, ViewGroup parent) {

                                View v = super.getDropDownView(position, convertView, parent);
                                ((TextView) v).setGravity(Gravity.LEFT);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                    ((TextView) v).setTextAlignment(TEXT_ALIGNMENT_CENTER);
                                }

                                return v;

                            }

                            {
                                setDropDownViewResource(android.R.layout
                                        .simple_spinner_dropdown_item);
                            }
                        });

                        for (FieldModel fieldmodel : fieldArrayList) {
                            if (spinneritem.getTag().toString().equalsIgnoreCase(fieldmodel.getFieldId().toString())) {
                                for (ContentModel model : finalListContent) {
                                    if (fieldmodel.getFieldValue().toString().equalsIgnoreCase(model.getDataId().toString())) {
                                        Log.e("SPINNER ", " SIZE " + spinneritem.getAdapter().getCount());
                                        if (model.getIndexData() < liststringluar.size())
                                            spinneritem.setSelection(model.getIndexData());
                                        else {
                                            spinneritem.setSelection(0);
                                        }

                                    }
                                }

                            }
                        }
                    }
                }
        }
        }

    }


}
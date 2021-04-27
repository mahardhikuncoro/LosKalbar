package ops;


import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import base.sqlite.FieldModel;
import base.sqlite.FormData;
import base.sqlite.ParameterModel;
//import id.co.smma.data.androidjob.ListData;

public class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
    private String formname;
    FormData formData;

    public MyOnItemSelectedListener(ArrayList<FieldModel> fieldArrayList,  JSONObject jsnobject, String formname) {
        this.formname = formname;
//        formData = new FormData(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.e("YUYUUYU", "YTUYTUY" + adapterView.getTag());

        /*try {
            String section = formData.select(formname);
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
                    Log.e("WAA ", "GET API " + spinnerclick.getTag());
                    if (fieldid.equalsIgnoreCase(spinnerclick.getTag().toString())) {
                        fieldIdToUpdate = explrObject.getString("fieldId");
                        fieldNametoUpdate = explrObject.getString("fieldName");

                        if (finalListContent.size() > 0) {
                            ParameterModel param = new ParameterModel();
                            for (ContentModel model : finalListContent) {
                                if (spinnerclick.getSelectedItem().toString().equalsIgnoreCase(model.getDataDesc())) {
                                    param.setParameterValue(model.getDataId() == null ? "null" : model.getDataId());
                                }
                            }
                            param.setParameterName(objectparameter.getString("parameterName"));
                            referenceParameter.add(param);
                        }
                    }

                }
                if (explrObject.getString("referenceType").equalsIgnoreCase("cascading")) {
                    if (spinnerclick.getSelectedItemPosition() != 0) {
                        setCascadingData(referenceParameter, fieldIdToUpdate, fieldNametoUpdate);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }*/


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
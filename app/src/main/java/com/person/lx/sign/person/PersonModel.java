package com.person.lx.sign.person;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.person.lx.sign.bean.SignLogBean;
import com.person.lx.sign.consts.Consts;

public class PersonModel implements PersonContract.model {

    @Override
    public void initDataModel(String token, String companyId, final initCallBack callBack) {
        OkGo.<String>post(Consts.url+"app/person/info")
                .tag(this)
                .headers("Token",token)
                .params("companyId",companyId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String data = response.body();
                        JsonParser parse =new JsonParser();  //创建json解析器
                        JsonObject json = (JsonObject) parse.parse(data);

                        if (json.get("code").getAsString().equals(Consts.SUCCESS_CODE)){
                            JsonObject info = json.get("result").getAsJsonObject();

                            callBack.success(info.get("img").toString(),info.get("username").getAsString());

                        }else {
                            callBack.fail(json.get("msg").getAsString());
                        }
                    }
                });
    }
}

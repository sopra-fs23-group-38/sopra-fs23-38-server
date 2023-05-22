package ch.uzh.ifi.hase.soprafs23.controller;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.tmt.v20180321.TmtClient;
import com.tencentcloudapi.tmt.v20180321.models.TextTranslateRequest;
import com.tencentcloudapi.tmt.v20180321.models.TextTranslateResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;


@RestController
@RequestMapping("/translator")
public class TranslatorController {
    @PostMapping("/")
    public String translate(@RequestParam("content") String content, @RequestParam("targetLanguage") String targetLanguage) {
        try {
            Credential cred = new Credential("AKIDiJhvtcbMjDQN3WxCSWLyb1Rgktrerdyg", "c9pX4YWP3buDJAkBONlu1xiVSzHkx9E4");
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("tmt.tencentcloudapi.com");
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            TmtClient client = new TmtClient(cred, "ap-chengdu", clientProfile);
            TextTranslateRequest req = new TextTranslateRequest();
            req.setSourceText(content);
            req.setSource("auto");
            req.setTarget(targetLanguage);
            req.setProjectId(0L);
            TextTranslateResponse resp = client.TextTranslate(req);
            HashMap<String, String> map = new HashMap<>();
            resp.toMap(map, "");
            return map.getOrDefault("TargetText", "");
        }
        catch (TencentCloudSDKException e) {
            return "";
        }
    }
}


package com.begr.vlsmfront.proxies;

import com.begr.vlsmfront.model.request.NetworkDetailRequestModel;
import com.begr.vlsmfront.model.response.SubnetResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;


@FeignClient(name = "vlsm-api", url = "${vlsm.api.url}")
public interface VlsmProxy {

    @PostMapping(value = "/", consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    List<SubnetResponseModel> sendRequest(@Valid NetworkDetailRequestModel networkDetailRequest);
}

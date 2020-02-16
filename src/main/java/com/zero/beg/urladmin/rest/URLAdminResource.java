package com.zero.beg.urladmin.rest;

import com.zero.beg.urladmin.common.model.ResponseVO;
import com.zero.beg.urladmin.model.URLAdminPO;
import com.zero.beg.urladmin.service.URLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("categroies")
public class URLAdminResource {

    @Autowired
    URLService urlService;

    @GET
    @CrossOrigin("*")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseVO<URLAdminPO> getURLAdminData() {
        ResponseVO<URLAdminPO> response = new ResponseVO<>();
        response.setSuccess(true);
        response.setMsg("Load Url Success");
        URLAdminPO urlAdminPO = urlService.getURLAdmin();
        response.setData(urlAdminPO);
//        System.out.println(urlAdminPO);
        return response;
    }

}

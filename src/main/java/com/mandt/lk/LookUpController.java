package com.mandt.lk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mandt.lk.model.LookUpResponse;
import com.mandt.lk.util.Utility;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;

@RestController

@Api(value="/eligibility",description="eligibility Look Up",produces ="application/json")
@RequestMapping(value = "/eligibility")
public class LookUpController {

    public void setUtility(Utility utility) {
        this.utility = utility;
    }

    @Autowired
    private Utility utility;

    /**
     *
     * @param accounts
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/lookUp", method = RequestMethod.GET,headers="Accept=application/json")
    @ResponseBody

    @ApiOperation(value="get Eligibility",response=LookUpResponse.class)
    @ApiResponses(value={
            @ApiResponse(code=200,message="LookUpResponse Details Retrieved",response=LookUpResponse.class),
            @ApiResponse(code=500,message="Internal Server Error - Validation on the input parameters failed"),
            @ApiResponse(code=404,message="s=Entitlement not found"),
            @ApiResponse(code=400,message="s=Entitlement not found - Mandatory fields missing")
    })
    public ArrayList<LookUpResponse> lookUpEligibility(@RequestParam(value="accounts", required=true) List<String> accounts) throws Exception {
        ArrayList<LookUpResponse> response = new ArrayList<>();
        for (String id:accounts) {
            validate(id);
            response.add( new LookUpResponse(utility, id) );
        }

        return response;
    }

    /**
     *
     * @param id
     * @throws Exception
     */
    private void validate(String id) throws Exception {
        if(!utility.validate(id).equals(utility.TRUE)){
            throw new Exception(utility.validate(id));
        }
    }
}

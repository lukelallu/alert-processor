package com.mandt.lk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.mandt.lk.model.ELIGIBILITY;
import com.mandt.lk.model.Entitlement;
import com.mandt.lk.model.LookUpResponse;
import com.mandt.lk.util.Util;
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

    @Autowired
    public LookUpController(Util utility) {
        this.utility = utility;
    }


    private final Util utility;

    /**
     *
     * @param accounts
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/wire", method = RequestMethod.GET,headers="Accept=application/json")
    @ResponseBody

    @ApiOperation(value="get Eligibility",response=LookUpResponse.class)
    @ApiResponses(value={
            @ApiResponse(code=200,message="LookUpResponse Details Retrieved",response=LookUpResponse.class),
            @ApiResponse(code=500,message="Internal Server Error - Validation on the input parameters failed"),
            @ApiResponse(code=404,message="s=Entitlement not found"),
            @ApiResponse(code=400,message="s=Entitlement not found - Mandatory fields missing")
    })
    public List<LookUpResponse> lookUpEligibility(@RequestParam(value="accounts", required=true) List<String> accounts) throws Exception {
        List<LookUpResponse> response = new ArrayList<>();
        for (String id:accounts) {
            validate(id);
            response.add( new LookUpResponse(id,new Entitlement( getEligibility( id) )));
        }

        return response;
    }

    /**
     *
     * @param id
     * @throws Exception
     */
    private void validate(String id) throws Exception {
        if(!utility.validate(id).equals(Utility.TRUE)){
            throw new Exception(utility.validate(id));
        }
    }


    /**
     *
     * @param accountNo
     * @return
     * @throws IOException
     */
    private String getEligibility(String accountNo) throws IOException {

        String eligibility;
        switch (getEligibilityFromMiniFile(accountNo)) {
            case "Y":
                eligibility = ELIGIBILITY.EXPANDED.getValue();
                break;
            case "R":
                eligibility = ELIGIBILITY.EXPANDED_REMIT.getValue();
                break;
            case "N":
                eligibility = ELIGIBILITY.STANDARD_DETAILS.getValue();
                break;
            case " ":
                eligibility = ELIGIBILITY.SUMMARY_ONLY.getValue();
                break;
            default:
                eligibility = ELIGIBILITY.NA.getValue();
                break;
        }
        return eligibility;
    }

    /**
     * @param accountNo
     * @return
     * @throws IOException
     */
    private String getEligibilityFromMiniFile(String accountNo) throws IOException {
        Scanner sc = null;
        try {
            sc = new Scanner(utility.getFile(), "UTF-8");
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] sArray = line.split("\\s{2,}");
                if (sArray[0].equals(accountNo)) {
                    if (sArray.length == 1) {
                        return "";
                    }
                    return sArray[1];
                }
            }
            // note that Scanner suppresses exceptions
            if (sc.ioException() != null) {
                throw sc.ioException();
            }
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
        return "NA";
    }
}
